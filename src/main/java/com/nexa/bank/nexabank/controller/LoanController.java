package com.nexa.bank.nexabank.controller;

import com.nexa.bank.nexabank.model.Account;
import com.nexa.bank.nexabank.model.Loan;
import com.nexa.bank.nexabank.model.LoanStatus;
import com.nexa.bank.nexabank.repository.AccountRepository;
import com.nexa.bank.nexabank.repository.LoanRepository;
import com.nexa.bank.nexabank.service.LoanService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.util.List;

@Controller
@RequestMapping("/loan")
public class LoanController {

    @Autowired
    private AccountRepository accountRepo;

    @Autowired
    private LoanRepository loanRepo;

    // ===============================
    // SHOW LOAN APPLICATION FORM
    // ===============================
    @GetMapping("/apply")
    public String showLoanForm(@RequestParam String accountNumber, Model model) {

        Account acc = accountRepo.findByAccountNumber(accountNumber).orElse(null);

        if (acc == null) {
            model.addAttribute("error", "Account not found!");
            return "error";
        }

        model.addAttribute("account", acc);

        return "loan-apply-form";
    }

    @PostMapping("/submit-application")
    public String submitLoanApplication(
            @RequestParam String accountNumber,
            @RequestParam String loanType,
            @RequestParam BigDecimal amount,
            @RequestParam int tenure,
            @RequestParam(required = false) BigDecimal income,
            @RequestParam String pan,
            @RequestParam String address,
            @RequestParam String employment,
            @RequestParam(required = false) Double interestRate,   // <-- IMPORTANT
            Model model
    ) {

        Account acc = accountRepo.findByAccountNumber(accountNumber).orElse(null);

        if (acc == null) {
            model.addAttribute("error", "Account not found!");
            return "error";
        }

        // If interestRate is missing, calculate from credit score
        if (interestRate == null) {
            int creditScore = acc.getCreditScore();

            if (creditScore >= 750) interestRate = 9.0;
            else if (creditScore >= 600) interestRate = 12.0;
            else interestRate = 15.0;
        }

        Loan loan = new Loan();
        loan.setAccount(acc);
        loan.setAccountNumber(accountNumber);
        loan.setLoanType(loanType);
        loan.setAmount(amount);
        loan.setTenure(tenure);

        if (income == null) income = BigDecimal.ZERO;
        loan.setIncome(income);
        loan.setEmployment(employment);
        loan.setAddress(address);
        loan.setPan(pan);

        loan.setInterestRate(interestRate);
        loan.setStatus(LoanStatus.PENDING);
        loan.setCreatedAt(LocalDate.now());

        // ===== EMI calculation =====
        BigDecimal monthlyRate = BigDecimal.valueOf(interestRate)
                .divide(BigDecimal.valueOf(12), 10, RoundingMode.HALF_UP)
                .divide(BigDecimal.valueOf(100), 10, RoundingMode.HALF_UP);

        BigDecimal numerator = amount.multiply(monthlyRate)
                .multiply((BigDecimal.ONE.add(monthlyRate)).pow(tenure));

        BigDecimal denominator = ((BigDecimal.ONE.add(monthlyRate)).pow(tenure))
                .subtract(BigDecimal.ONE);

        BigDecimal emi = numerator.divide(denominator, 2, RoundingMode.HALF_UP);

        loan.setEmiAmount(emi);
        loan.setRemainingBalance(amount);

        loanRepo.save(loan);

        model.addAttribute("loan", loan);
        return "loan-receipt";
    }


    // LOAD LOAN APPLY FORM (from loan-selection page)
    @GetMapping("/apply-form")
    public String showLoanForm2(@RequestParam String type,
                                @RequestParam String accountNumber,
                                Model model) {

        model.addAttribute("loanType", type);
        model.addAttribute("accountNumber", accountNumber);

        return "loan-apply-form";
    }

    // ===============================
    // LOAN SELECTION PAGE
    // ===============================
    @GetMapping("/selection")
    public String showLoanSelection(@RequestParam String accountNumber, Model model) {

        // (Optional) Load account, in case you need holder name, etc.
        Account acc = accountRepo.findByAccountNumber(accountNumber).orElse(null);
        if (acc == null) {
            model.addAttribute("error", "Account not found!");
            return "error";
        }

        model.addAttribute("accountNumber", accountNumber);
        model.addAttribute("holderName", acc.getHolderName());

        return "loan-selection";   // will render loan-selection.html
    }
    @Autowired
    private LoanService loanService;

    @GetMapping("/history")
    public String loanHistory(@RequestParam String accountNumber, Model model) {

        List<Loan> list = loanService.getLoansByAccount(accountNumber);

        model.addAttribute("loanHistory", list);
        model.addAttribute("accountNumber", accountNumber);

        return "loan-history";
    }


}

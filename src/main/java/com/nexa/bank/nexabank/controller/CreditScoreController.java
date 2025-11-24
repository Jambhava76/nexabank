package com.nexa.bank.nexabank.controller;

import com.nexa.bank.nexabank.model.Account;
import com.nexa.bank.nexabank.repository.AccountRepository;
import com.nexa.bank.nexabank.service.CreditScoreService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class CreditScoreController {

    private final AccountRepository accountRepo;
    private final CreditScoreService creditService;

    public CreditScoreController(AccountRepository accountRepo, CreditScoreService creditService) {
        this.accountRepo = accountRepo;
        this.creditService = creditService;
    }

    @GetMapping("/credit-score")
    public String viewScore(@RequestParam String accountNumber, Model model) {

        Account acc = accountRepo.findByAccountNumber(accountNumber)
                .orElseThrow(() -> new RuntimeException("Account not found"));

        int score = creditService.updateAndGetScore(accountNumber);

        model.addAttribute("score", score);
        model.addAttribute("holderName", acc.getHolderName());
        model.addAttribute("accountNumber", acc.getAccountNumber());

        return "credit-score";
    }
}

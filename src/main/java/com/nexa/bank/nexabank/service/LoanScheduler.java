package com.nexa.bank.nexabank.service;

import com.nexa.bank.nexabank.model.Loan;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

@Service
public class LoanScheduler {

    private final LoanService loanService;

    public LoanScheduler(LoanService loanService) {
        this.loanService = loanService;
    }

    // Run every day at 1 AM
    @Scheduled(cron = "0 0 1 * * *")
    @Transactional
    public void processMonthlyEmis() {

        LocalDate today = LocalDate.now();

        // all disbursed & not closed loans
        List<Loan> activeLoans = loanService.findApprovedNotClosedLoans();

        for (Loan loan : activeLoans) {
            if (loan.getNextEmiDate() == null) continue;
            if (!today.equals(loan.getNextEmiDate())) continue;

            loanService.collectEmi(loan);
        }
    }
}

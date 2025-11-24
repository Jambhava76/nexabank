package com.nexa.bank.nexabank.service;

import com.nexa.bank.nexabank.model.Account;
import com.nexa.bank.nexabank.repository.AccountRepository;
import org.springframework.stereotype.Service;

@Service
public class CreditScoreService {

    private final AccountRepository accountRepo;

    public CreditScoreService(AccountRepository accountRepo) {
        this.accountRepo = accountRepo;
    }

    public int calculateScore(Account acc) {

        int score = 600; // base

        // ✔ Balance impact
        if (acc.getBalance().intValue() > 50000) score += 50;
        if (acc.getBalance().intValue() > 100000) score += 70;

        // ✔ Account age impact
        score += 20; // simple bonus

        // ✔ Penalty if card frozen (simulating misuse)
        if (acc.isCardFrozen()) score -= 40;

        // limit score between 300–900
        return Math.min(900, Math.max(300, score));
    }

    public int updateAndGetScore(String accountNumber) {
        Account acc = accountRepo.findByAccountNumber(accountNumber)
                .orElseThrow(() -> new RuntimeException("Account not found"));

        int newScore = calculateScore(acc);

        acc.setCreditScore(newScore);
        accountRepo.save(acc);

        return newScore;
    }
}

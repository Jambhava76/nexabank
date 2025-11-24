package com.nexa.bank.nexabank.service;

import com.nexa.bank.nexabank.repository.AccountRepository;
import com.nexa.bank.nexabank.repository.TransactionRepository;
import org.springframework.stereotype.Service;

@Service
public class AdminDashboardService {

    private final AccountRepository accountRepo;
    private final TransactionRepository txRepo;

    public AdminDashboardService(AccountRepository accountRepo, TransactionRepository txRepo) {
        this.accountRepo = accountRepo;
        this.txRepo = txRepo;
    }

    public Long getTotalAccounts() {
        return accountRepo.countTotalAccounts();
    }

    public Long getActiveAccounts() {
        return accountRepo.countActiveAccounts();
    }

    public Long getFrozenAccounts() {
        return accountRepo.countFrozenAccounts();
    }

    public Long getTransactionCount() {
        return txRepo.countAllTransactions();
    }
}

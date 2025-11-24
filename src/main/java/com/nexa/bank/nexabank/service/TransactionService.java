package com.nexa.bank.nexabank.service;

import com.nexa.bank.nexabank.model.Account;
import com.nexa.bank.nexabank.model.Transaction;
import com.nexa.bank.nexabank.repository.AccountRepository;
import com.nexa.bank.nexabank.repository.TransactionRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
public class TransactionService {

    private final AccountRepository accountRepo;
    private final TransactionRepository txRepo;

    public TransactionService(AccountRepository accountRepo, TransactionRepository txRepo) {
        this.accountRepo = accountRepo;
        this.txRepo = txRepo;
    }

    private String generateTxId() {
        return "TXN-" + UUID.randomUUID().toString().substring(0, 8).toUpperCase();
    }

    // ----------------------------------------------------
    //                     DEPOSIT
    // ----------------------------------------------------
    @Transactional
    public Transaction deposit(String accountNumber, BigDecimal amount) {

        Account acc = accountRepo.findByAccountNumber(accountNumber)
                .orElseThrow(() -> new RuntimeException("Account not found"));

        BigDecimal newBalance = acc.getBalance().add(amount);

        acc.setBalance(newBalance);
        accountRepo.save(acc);

        Transaction tx = new Transaction();
        tx.setTransactionId(generateTxId());
        tx.setType("DEPOSIT");
        tx.setAccountNumber(accountNumber);
        tx.setAmount(amount);
        tx.setBalanceAfter(newBalance);

        // ✅ Required fields
        tx.setCreatedAt(LocalDateTime.now());
        tx.setTransactionTime(LocalDateTime.now());

        return txRepo.save(tx);
    }

    // ----------------------------------------------------
    //                     WITHDRAW
    // ----------------------------------------------------
    @Transactional
    public Transaction withdraw(String accountNumber, BigDecimal amount, String reason) {

        Account acc = accountRepo.findByAccountNumber(accountNumber)
                .orElseThrow(() -> new RuntimeException("Account not found"));

        if (acc.getBalance().compareTo(amount) < 0) {
            throw new RuntimeException("Insufficient balance!");
        }

        if (acc.isCardFrozen()) {
            throw new RuntimeException("Your card is frozen. Unfreeze to continue.");
        }

        BigDecimal newBalance = acc.getBalance().subtract(amount);

        acc.setBalance(newBalance);
        accountRepo.save(acc);

        Transaction tx = new Transaction();
        tx.setTransactionId(generateTxId());
        tx.setType("WITHDRAW");
        tx.setAccountNumber(accountNumber);
        tx.setAmount(amount);
        tx.setReason(reason);
        tx.setBalanceAfter(newBalance);

        // ✅ Required fields
        tx.setCreatedAt(LocalDateTime.now());
        tx.setTransactionTime(LocalDateTime.now());

        return txRepo.save(tx);
    }

    // ----------------------------------------------------
    //                     TRANSFER
    // ----------------------------------------------------
    @Transactional
    public Transaction transfer(String fromAccountNumber,
                                String toAccountNumber,
                                BigDecimal amount,
                                String remarks) {

        if (fromAccountNumber.equals(toAccountNumber)) {
            throw new RuntimeException("Cannot transfer to the same account");
        }

        if (amount.compareTo(BigDecimal.ZERO) <= 0) {
            throw new RuntimeException("Transfer amount must be greater than zero");
        }

        Account fromAcc = accountRepo.findByAccountNumber(fromAccountNumber)
                .orElseThrow(() -> new RuntimeException("Sender account not found"));

        Account toAcc = accountRepo.findByAccountNumber(toAccountNumber)
                .orElseThrow(() -> new RuntimeException("Receiver account not found"));

        if (fromAcc.isCardFrozen()) {
            throw new RuntimeException("Your card is frozen. Cannot transfer money.");
        }

        if (fromAcc.getBalance().compareTo(amount) < 0) {
            throw new RuntimeException("Insufficient balance!");
        }

        BigDecimal newFromBalance = fromAcc.getBalance().subtract(amount);
        BigDecimal newToBalance = toAcc.getBalance().add(amount);

        fromAcc.setBalance(newFromBalance);
        toAcc.setBalance(newToBalance);

        accountRepo.save(fromAcc);
        accountRepo.save(toAcc);

        // -------------------------
        // CREATE TRANSACTION ENTRY
        // -------------------------
        Transaction tx = new Transaction();
        tx.setTransactionId(generateTxId());
        tx.setType("TRANSFER");

        tx.setAccountNumber(fromAccountNumber);  // primary account
        tx.setSenderAccountNumber(fromAccountNumber);
        tx.setReceiverAccountNumber(toAccountNumber);

        tx.setAmount(amount);
        tx.setReason(remarks);
        tx.setBalanceAfter(newFromBalance);

        // ✅ Required fields
        tx.setCreatedAt(LocalDateTime.now());
        tx.setTransactionTime(LocalDateTime.now());

        return txRepo.save(tx);
    }

    public List<Transaction> getAllTransactions(String accountNumber) {
        return txRepo.getAllTransactionsForAccount(accountNumber);
    }
}

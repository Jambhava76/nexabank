package com.nexa.bank.nexabank.service;

import com.nexa.bank.nexabank.model.Transaction;
import com.nexa.bank.nexabank.repository.TransactionRepository;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.*;

@Service
public class BalanceHistoryService {

    private final TransactionRepository txRepo;

    public BalanceHistoryService(TransactionRepository txRepo) {
        this.txRepo = txRepo;
    }

    public List<Map<String, Object>> getLast10DaysHistory(String accountNumber, BigDecimal currentBalance) {

        List<Transaction> txList = txRepo.getAllTransactionsForAccount(accountNumber);
        txList.sort(Comparator.comparing(Transaction::getCreatedAt).reversed()); // newest first

        List<Map<String, Object>> history = new ArrayList<>();

        BigDecimal runningBalance = currentBalance;

        for (int i = 0; i < 10; i++) {

            LocalDate day = LocalDate.now().minusDays(i);

            BigDecimal closingBalance = runningBalance; // today’s closing balance

            for (Transaction tx : txList) {

                if (tx.getCreatedAt().toLocalDate().isEqual(day)) {

                    BigDecimal amt = tx.getAmount();

                    switch (tx.getType()) {

                        case "DEPOSIT":
                            // to go backwards in time, reverse deposit
                            runningBalance = runningBalance.subtract(amt);
                            break;

                        case "WITHDRAW":
                            // reverse withdraw
                            runningBalance = runningBalance.add(amt);
                            break;

                        case "TRANSFER":
                            if (accountNumber.equals(tx.getSenderAccountNumber())) {
                                // sender → reverse: add back the money
                                runningBalance = runningBalance.add(amt);
                            }
                            if (accountNumber.equals(tx.getReceiverAccountNumber())) {
                                // receiver → reverse: subtract the money
                                runningBalance = runningBalance.subtract(amt);
                            }
                            break;
                    }
                }
            }

            // Never allow negative due to rounding or mistakes – realistic minimum = 0
            if (closingBalance.compareTo(BigDecimal.ZERO) < 0) {
                closingBalance = BigDecimal.ZERO;
            }

            Map<String, Object> row = new HashMap<>();
            row.put("date", day);
            row.put("balance", closingBalance);

            history.add(row);
        }

        return history;
    }

}

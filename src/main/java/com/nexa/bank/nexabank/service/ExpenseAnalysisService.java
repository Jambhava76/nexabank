package com.nexa.bank.nexabank.service;

import com.nexa.bank.nexabank.model.Transaction;
import com.nexa.bank.nexabank.repository.TransactionRepository;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.*;

@Service
public class ExpenseAnalysisService {

    private final TransactionRepository txRepo;

    public ExpenseAnalysisService(TransactionRepository txRepo) {
        this.txRepo = txRepo;
    }

    public Map<String, BigDecimal> getExpenseSummary(String accountNumber) {

        List<Transaction> txList = txRepo.getAllTransactionsForAccount(accountNumber);

        Map<String, BigDecimal> summary = new HashMap<>();

        summary.put("Deposits", BigDecimal.ZERO);
        summary.put("Withdrawals", BigDecimal.ZERO);
        summary.put("Transfers Sent", BigDecimal.ZERO);
        summary.put("Transfers Received", BigDecimal.ZERO);

        for (Transaction tx : txList) {

            String type = tx.getType();
            String sender = tx.getSenderAccountNumber();
            String receiver = tx.getReceiverAccountNumber();
            BigDecimal amount = tx.getAmount();

            // -----------------------------
            // DEPOSIT
            // -----------------------------
            if ("DEPOSIT".equalsIgnoreCase(type)) {
                summary.put("Deposits", summary.get("Deposits").add(amount));
            }

            // -----------------------------
            // WITHDRAWAL
            // -----------------------------
            else if ("WITHDRAW".equalsIgnoreCase(type)) {
                summary.put("Withdrawals", summary.get("Withdrawals").add(amount));
            }

            // -----------------------------
            // TRANSFER
            // -----------------------------
            else if ("TRANSFER".equalsIgnoreCase(type)) {

                // Safe null check for older transactions
                if (sender != null && sender.equals(accountNumber)) {
                    summary.put("Transfers Sent",
                            summary.get("Transfers Sent").add(amount));
                }

                else if (receiver != null && receiver.equals(accountNumber)) {
                    summary.put("Transfers Received",
                            summary.get("Transfers Received").add(amount));
                }

                // If BOTH sender and receiver are NULL (old DB rows)
                else {
                    // If accountNumber = main account_number column → treat as received
                    if (tx.getAccountNumber().equals(accountNumber)) {
                        summary.put("Transfers Received",
                                summary.get("Transfers Received").add(amount));
                    }
                }
            }
        }

        return summary;
    }
}

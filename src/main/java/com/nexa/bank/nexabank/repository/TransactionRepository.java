package com.nexa.bank.nexabank.repository;

import com.nexa.bank.nexabank.model.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface TransactionRepository extends JpaRepository<Transaction, Integer> {

    // Fetch ALL types of transactions involving this account
    @Query("""
            SELECT t FROM Transaction t
            WHERE t.accountNumber = :acc
            OR t.senderAccountNumber = :acc
            OR t.receiverAccountNumber = :acc
            ORDER BY t.createdAt DESC
           """)
    List<Transaction> getAllTransactionsForAccount(@Param("acc") String accountNumber);
    @Query("SELECT t FROM Transaction t WHERE t.senderAccountNumber = :acc OR t.receiverAccountNumber = :acc ORDER BY t.createdAt DESC")
    List<Transaction> getPassbook(@Param("acc") String acc);
    @Query("SELECT COUNT(t) FROM Transaction t")
    Long countAllTransactions();

}

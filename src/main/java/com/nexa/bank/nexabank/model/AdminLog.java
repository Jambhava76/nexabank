package com.nexa.bank.nexabank.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "admin_logs")
public class AdminLog {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String adminId;
    private String accountNumber;
    private String action;

    private LocalDateTime timestamp;

    @PrePersist
    public void setTimestamp() {
        this.timestamp = LocalDateTime.now();
    }

    public AdminLog() {}

    public AdminLog(String adminId, String accountNumber, String action) {
        this.adminId = adminId;
        this.accountNumber = accountNumber;
        this.action = action;
    }

    // ⭐⭐⭐ ADD THESE GETTERS & SETTERS ⭐⭐⭐

    public Integer getId() {
        return id;
    }

    public String getAdminId() {
        return adminId;
    }

    public void setAdminId(String adminId) {
        this.adminId = adminId;
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
    }

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }
}

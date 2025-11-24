package com.nexa.bank.nexabank.model;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Table(name = "loan")
public class Loan {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // ----------- RELATIONSHIP WITH ACCOUNT -----------
    @ManyToOne
    @JoinColumn(name = "account_id", nullable = false)
    private Account account;

    // ----------- LOAN DETAILS -----------
    @Column(nullable = false)
    private String accountNumber;

    @Column(nullable = false)
    private String loanType;

    @Column(nullable = false)
    private BigDecimal amount;

    @Column(nullable = false)
    private Integer tenure;   // in months

    @Column(nullable = false)
    private Double interestRate;

    private BigDecimal income;

    private String employment;

    @Column(nullable = false)
    private String pan;

    @Column(nullable = false)
    private String address;

    // ----------- EMI & REPAYMENT DETAILS -----------
    private BigDecimal emiAmount;

    private BigDecimal remainingBalance;

    private LocalDate nextEmiDate;

    // ----------- STATUS -----------
    @Enumerated(EnumType.STRING)
    private LoanStatus status;

    private LocalDate createdAt;
    private LocalDate approvedAt;
    private LocalDate disbursedAt;

    // ----------- ADMIN META -----------
    // reason for approval / rejection
    private String adminRemark;

    // loan closed when fully paid
    // use wrapper Boolean so it can safely read NULL from old rows
    @Column(name = "closed")
    private Boolean closed = false;

    // ---------------------------------------------------------
    // GETTERS & SETTERS
    // ---------------------------------------------------------

    public Long getId() {
        return id;
    }

    public Account getAccount() {
        return account;
    }

    public void setAccount(Account account) {
        this.account = account;
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
    }

    public String getLoanType() {
        return loanType;
    }

    public void setLoanType(String loanType) {
        this.loanType = loanType;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public Integer getTenure() {
        return tenure;
    }

    public void setTenure(Integer tenure) {
        this.tenure = tenure;
    }

    public Double getInterestRate() {
        return interestRate;
    }

    public void setInterestRate(Double interestRate) {
        this.interestRate = interestRate;
    }

    public BigDecimal getIncome() {
        return income;
    }

    public void setIncome(BigDecimal income) {
        this.income = income;
    }

    public String getEmployment() {
        return employment;
    }

    public void setEmployment(String employment) {
        this.employment = employment;
    }

    public String getPan() {
        return pan;
    }

    public void setPan(String pan) {
        this.pan = pan;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public BigDecimal getEmiAmount() {
        return emiAmount;
    }

    public void setEmiAmount(BigDecimal emiAmount) {
        this.emiAmount = emiAmount;
    }

    public BigDecimal getRemainingBalance() {
        return remainingBalance;
    }

    public void setRemainingBalance(BigDecimal remainingBalance) {
        this.remainingBalance = remainingBalance;
    }

    public LocalDate getNextEmiDate() {
        return nextEmiDate;
    }

    public void setNextEmiDate(LocalDate nextEmiDate) {
        this.nextEmiDate = nextEmiDate;
    }

    public LoanStatus getStatus() {
        return status;
    }

    public void setStatus(LoanStatus status) {
        this.status = status;
    }

    public LocalDate getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDate createdAt) {
        this.createdAt = createdAt;
    }

    public LocalDate getApprovedAt() {
        return approvedAt;
    }

    public void setApprovedAt(LocalDate approvedAt) {
        this.approvedAt = approvedAt;
    }

    public LocalDate getDisbursedAt() {
        return disbursedAt;
    }

    public void setDisbursedAt(LocalDate disbursedAt) {
        this.disbursedAt = disbursedAt;
    }

    public String getAdminRemark() {
        return adminRemark;
    }

    public void setAdminRemark(String adminRemark) {
        this.adminRemark = adminRemark;
    }

    // keep boolean-style getter for existing code
    public boolean isClosed() {
        return Boolean.TRUE.equals(closed);
    }

    public void setClosed(boolean closed) {
        this.closed = closed;
    }

    // optional: a setter that accepts Boolean, if you ever need it
    public void setClosed(Boolean closed) {
        this.closed = closed;
    }




}

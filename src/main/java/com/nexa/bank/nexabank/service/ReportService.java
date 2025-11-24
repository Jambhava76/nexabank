package com.nexa.bank.nexabank.service;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import com.nexa.bank.nexabank.model.Account;
import com.nexa.bank.nexabank.model.AdminLog;
import com.nexa.bank.nexabank.model.Transaction;
import com.nexa.bank.nexabank.repository.AccountRepository;
import com.nexa.bank.nexabank.repository.AdminLogRepository;
import com.nexa.bank.nexabank.repository.TransactionRepository;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.PrintWriter;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Service
public class ReportService {

    private final AccountRepository accountRepo;
    private final TransactionRepository transactionRepo;
    private final AdminLogRepository logRepo;

    private static final DateTimeFormatter DATE_TIME_FMT =
            DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");

    public ReportService(AccountRepository accountRepo,
                         TransactionRepository transactionRepo,
                         AdminLogRepository logRepo) {
        this.accountRepo = accountRepo;
        this.transactionRepo = transactionRepo;
        this.logRepo = logRepo;
    }

    // =========================================================
    //                      ACCOUNT CSV
    // =========================================================
    public void generateAccountCSV(HttpServletResponse response) {
        try {
            response.setContentType("text/csv");
            response.setHeader("Content-Disposition",
                    "attachment; filename=accounts-report.csv");

            List<Account> accounts = accountRepo.findAll();
            PrintWriter writer = response.getWriter();

            // Header
            writer.println("Account Number,Holder Name,Email,Phone,Balance,Frozen");

            for (Account acc : accounts) {
                String phone = acc.getPhoneNumber() != null ? acc.getPhoneNumber() : "";
                String frozen = acc.isCardFrozen() ? "YES" : "NO";

                writer.println(
                        acc.getAccountNumber() + "," +
                                safe(acc.getHolderName()) + "," +
                                safe(acc.getEmail()) + "," +
                                safe(phone) + "," +
                                acc.getBalance() + "," +
                                frozen
                );
            }

            writer.flush();
        } catch (Exception e) {
            throw new RuntimeException("CSV creation error (accounts)", e);
        }
    }

    // =========================================================
    //                      ACCOUNT PDF
    // =========================================================
    public void generateAccountPDF(HttpServletResponse response) {
        response.setContentType("application/pdf");
        response.setHeader("Content-Disposition",
                "attachment; filename=accounts-report.pdf");

        List<Account> accounts = accountRepo.findAll();

        try {
            Document document = new Document();
            PdfWriter.getInstance(document, response.getOutputStream());
            document.open();

            document.add(new Paragraph("Nexa Bank - Account Report"));
            document.add(new Paragraph(" ")); // empty line

            // Table with 6 columns
            PdfPTable table = new PdfPTable(6);
            table.setWidthPercentage(100);

            table.addCell("Account Number");
            table.addCell("Holder Name");
            table.addCell("Email");
            table.addCell("Phone");
            table.addCell("Balance");
            table.addCell("Frozen");

            for (Account acc : accounts) {
                String phone = acc.getPhoneNumber() != null ? acc.getPhoneNumber() : "";
                String frozen = acc.isCardFrozen() ? "YES" : "NO";

                table.addCell(nullSafe(acc.getAccountNumber()));
                table.addCell(nullSafe(acc.getHolderName()));
                table.addCell(nullSafe(acc.getEmail()));
                table.addCell(nullSafe(phone));
                table.addCell(acc.getBalance() != null ? acc.getBalance().toString() : "");
                table.addCell(frozen);
            }

            document.add(table);
            document.close();
        } catch (IOException | DocumentException e) {
            throw new RuntimeException("PDF creation error (accounts)", e);
        }
    }

    // =========================================================
    //                  TRANSACTION CSV
    // =========================================================
    public void generateTransactionsCSV(HttpServletResponse response) {
        try {
            response.setContentType("text/csv");
            response.setHeader("Content-Disposition",
                    "attachment; filename=transactions-report.csv");

            List<Transaction> transactions = transactionRepo.findAll();
            PrintWriter writer = response.getWriter();

            // Header
            writer.println("Transaction ID,Account Number,Type,Amount,Time");

            for (Transaction tx : transactions) {
                String time = "";
                if (tx.getTransactionTime() != null) {
                    time = tx.getTransactionTime().format(DATE_TIME_FMT);
                }

                writer.println(
                        safe(tx.getTransactionId()) + "," +
                                safe(tx.getAccountNumber()) + "," +
                                safe(tx.getType()) + "," +
                                tx.getAmount() + "," +
                                safe(time)
                );
            }

            writer.flush();
        } catch (Exception e) {
            throw new RuntimeException("CSV creation error (transactions)", e);
        }
    }

    // =========================================================
    //                  TRANSACTION PDF
    // =========================================================
    public void generateTransactionsPDF(HttpServletResponse response) {
        response.setContentType("application/pdf");
        response.setHeader("Content-Disposition",
                "attachment; filename=transactions-report.pdf");

        List<Transaction> transactions = transactionRepo.findAll();

        try {
            Document document = new Document();
            PdfWriter.getInstance(document, response.getOutputStream());
            document.open();

            document.add(new Paragraph("Nexa Bank - Transaction Report"));
            document.add(new Paragraph(" "));

            PdfPTable table = new PdfPTable(5);
            table.setWidthPercentage(100);

            table.addCell("Transaction ID");
            table.addCell("Account Number");
            table.addCell("Type");
            table.addCell("Amount");
            table.addCell("Time");

            for (Transaction tx : transactions) {
                String time = "";
                if (tx.getTransactionTime() != null) {
                    time = tx.getTransactionTime().format(DATE_TIME_FMT);
                }

                table.addCell(nullSafe(tx.getTransactionId()));
                table.addCell(nullSafe(tx.getAccountNumber()));
                table.addCell(nullSafe(tx.getType()));
                table.addCell(tx.getAmount() != null ? tx.getAmount().toString() : "");
                table.addCell(time);
            }

            document.add(table);
            document.close();
        } catch (IOException | DocumentException e) {
            throw new RuntimeException("PDF creation error (transactions)", e);
        }
    }

    // =========================================================
    //                       LOGS CSV
    // =========================================================
    public void generateLogsCSV(HttpServletResponse response) {
        try {
            response.setContentType("text/csv");
            response.setHeader("Content-Disposition",
                    "attachment; filename=admin-logs-report.csv");

            List<AdminLog> logs = logRepo.findAll();
            PrintWriter writer = response.getWriter();

            writer.println("Admin ID,Account Number,Action,Time");

            for (AdminLog log : logs) {
                String time = "";
                if (log.getTimestamp() != null) {
                    time = log.getTimestamp().format(DATE_TIME_FMT);
                }

                writer.println(
                        safe(log.getAdminId()) + "," +
                                safe(log.getAccountNumber()) + "," +
                                safe(log.getAction()) + "," +
                                safe(time)
                );
            }

            writer.flush();
        } catch (Exception e) {
            throw new RuntimeException("CSV creation error (logs)", e);
        }
    }

    // =========================================================
    //                       LOGS PDF
    // =========================================================
    public void generateLogsPDF(HttpServletResponse response) {
        response.setContentType("application/pdf");
        response.setHeader("Content-Disposition",
                "attachment; filename=admin-logs-report.pdf");

        List<AdminLog> logs = logRepo.findAll();

        try {
            Document document = new Document();
            PdfWriter.getInstance(document, response.getOutputStream());
            document.open();

            document.add(new Paragraph("Nexa Bank - Admin Logs Report"));
            document.add(new Paragraph(" "));

            PdfPTable table = new PdfPTable(4);
            table.setWidthPercentage(100);

            table.addCell("Admin ID");
            table.addCell("Account Number");
            table.addCell("Action");
            table.addCell("Time");

            for (AdminLog log : logs) {
                String time = "";
                if (log.getTimestamp() != null) {
                    time = log.getTimestamp().format(DATE_TIME_FMT);
                }

                table.addCell(nullSafe(log.getAdminId()));
                table.addCell(nullSafe(log.getAccountNumber()));
                table.addCell(nullSafe(log.getAction()));
                table.addCell(time);
            }

            document.add(table);
            document.close();
        } catch (IOException | DocumentException e) {
            throw new RuntimeException("PDF creation error (logs)", e);
        }
    }

    // =========================================================
    //                    HELPER METHODS
    // =========================================================
    /** Escape commas and nulls for CSV */
    private String safe(String s) {
        if (s == null) return "";
        // Simple escaping: wrap with quotes if contains comma
        if (s.contains(",")) {
            return "\"" + s.replace("\"", "\"\"") + "\"";
        }
        return s;
    }

    /** Null-safe for PDF cells */
    private String nullSafe(String s) {
        return s == null ? "" : s;
    }
}

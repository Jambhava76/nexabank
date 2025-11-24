package com.nexa.bank.nexabank.service;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.font.PDType0Font;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;

@Service
public class PdfService {
    public byte[] generateDepositReceipt(
            String txId,
            String holderName,
            String accountNumber,
            Object amount,
            Object updatedBalance,
            String date,
            String time
    ) throws Exception {

        InputStream fontStream = new ClassPathResource("fonts/NotoSans-Regular.ttf").getInputStream();

        PDDocument document = new PDDocument();
        PDPage page = new PDPage();
        document.addPage(page);

        PDType0Font font = PDType0Font.load(document, fontStream);

        PDPageContentStream content = new PDPageContentStream(document, page);

        float y = 700;

        content.beginText();
        content.setFont(font, 18);
        content.newLineAtOffset(50, y);
        content.showText("Nexa Bank - Deposit Receipt");
        content.endText();

        y -= 40;

        content.beginText();
        content.setFont(font, 14);
        content.newLineAtOffset(50, y);
        content.showText("Transaction ID: " + txId);
        content.endText();

        y -= 25;

        content.beginText();
        content.newLineAtOffset(50, y);
        content.showText("Date: " + date);
        content.endText();

        y -= 25;

        content.beginText();
        content.newLineAtOffset(50, y);
        content.showText("Time: " + time);
        content.endText();

        y -= 25;

        content.beginText();
        content.newLineAtOffset(50, y);
        content.showText("Account Number: " + accountNumber);
        content.endText();

        y -= 25;

        content.beginText();
        content.newLineAtOffset(50, y);
        content.showText("Holder Name: " + holderName);
        content.endText();

        y -= 25;

        content.beginText();
        content.newLineAtOffset(50, y);
        content.showText("Amount Deposited: ₹" + amount);
        content.endText();

        y -= 25;

        content.beginText();
        content.newLineAtOffset(50, y);
        content.showText("New Balance: ₹" + updatedBalance);
        content.endText();

        content.close();

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        document.save(baos);
        document.close();

        return baos.toByteArray();
    }

    public byte[] generateWithdrawReceipt(
            String txId,
            String holderName,
            String accountNumber,
            Object amount,
            Object updatedBalance,
            String reason,
            String date,
            String time
    ) throws Exception {

        // Load Unicode font (supports ₹ symbol)
        InputStream fontStream =
                new ClassPathResource("fonts/NotoSans-Regular.ttf").getInputStream();

        PDDocument document = new PDDocument();
        PDPage page = new PDPage();
        document.addPage(page);

        PDType0Font font = PDType0Font.load(document, fontStream);

        PDPageContentStream content = new PDPageContentStream(document, page);

        float y = 700;

        content.beginText();
        content.setFont(font, 18);
        content.newLineAtOffset(50, y);
        content.showText("Nexa Bank - Withdrawal Receipt");
        content.endText();

        y -= 40;

        content.beginText();
        content.setFont(font, 14);
        content.newLineAtOffset(50, y);
        content.showText("Transaction ID: " + txId);
        content.endText();

        y -= 25;

        content.beginText();
        content.newLineAtOffset(50, y);
        content.showText("Date: " + date);
        content.endText();

        y -= 25;

        content.beginText();
        content.newLineAtOffset(50, y);
        content.showText("Time: " + time);
        content.endText();

        y -= 25;

        content.beginText();
        content.newLineAtOffset(50, y);
        content.showText("Account Number: " + accountNumber);
        content.endText();

        y -= 25;

        content.beginText();
        content.newLineAtOffset(50, y);
        content.showText("Holder Name: " + holderName);
        content.endText();

        y -= 25;

        content.beginText();
        content.newLineAtOffset(50, y);
        content.showText("Amount Withdrawn: ₹" + amount);
        content.endText();

        y -= 25;

        content.beginText();
        content.newLineAtOffset(50, y);
        content.showText("Reason: " + reason);
        content.endText();

        y -= 25;

        content.beginText();
        content.newLineAtOffset(50, y);
        content.showText("New Balance: ₹" + updatedBalance);
        content.endText();

        content.close();

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        document.save(baos);
        document.close();

        return baos.toByteArray();
    }
    public byte[] generateTransferReceipt(
            String txId,
            String senderName,
            String senderAccount,
            String receiverName,
            String receiverAccount,
            Object amount,
            Object senderUpdatedBalance,
            String date,
            String time,
            String remarks
    ) throws Exception {

        // Load Unicode font (supports ₹ symbol)
        InputStream fontStream =
                new ClassPathResource("fonts/NotoSans-Regular.ttf").getInputStream();

        PDDocument document = new PDDocument();
        PDPage page = new PDPage();
        document.addPage(page);

        PDType0Font font = PDType0Font.load(document, fontStream);

        PDPageContentStream content = new PDPageContentStream(document, page);

        float y = 700;

        content.beginText();
        content.setFont(font, 18);
        content.newLineAtOffset(50, y);
        content.showText("Nexa Bank - Transfer Receipt");
        content.endText();

        y -= 40;

        // Transaction ID
        content.beginText();
        content.setFont(font, 14);
        content.newLineAtOffset(50, y);
        content.showText("Transaction ID: " + txId);
        content.endText();
        y -= 25;

        // Date
        content.beginText();
        content.newLineAtOffset(50, y);
        content.showText("Date: " + date);
        content.endText();
        y -= 25;

        // Time
        content.beginText();
        content.newLineAtOffset(50, y);
        content.showText("Time: " + time);
        content.endText();
        y -= 25;

        // Sender Account
        content.beginText();
        content.newLineAtOffset(50, y);
        content.showText("Sender Account: " + senderAccount);
        content.endText();
        y -= 25;

        // Sender Name
        content.beginText();
        content.newLineAtOffset(50, y);
        content.showText("Sender Name: " + senderName);
        content.endText();
        y -= 25;

        // Receiver Account
        content.beginText();
        content.newLineAtOffset(50, y);
        content.showText("Receiver Account: " + receiverAccount);
        content.endText();
        y -= 25;

        // Receiver Name
        content.beginText();
        content.newLineAtOffset(50, y);
        content.showText("Receiver Name: " + receiverName);
        content.endText();
        y -= 25;

        // Amount
        content.beginText();
        content.newLineAtOffset(50, y);
        content.showText("Amount Transferred: ₹" + amount);
        content.endText();
        y -= 25;

        // Remarks
        if (remarks != null && !remarks.isBlank()) {
            content.beginText();
            content.newLineAtOffset(50, y);
            content.showText("Remarks: " + remarks);
            content.endText();
            y -= 25;
        }

        // Updated Balance
        content.beginText();
        content.newLineAtOffset(50, y);
        content.showText("Sender Balance After Transfer: ₹" + senderUpdatedBalance);
        content.endText();

        content.close();

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        document.save(baos);
        document.close();

        return baos.toByteArray();
    }

}

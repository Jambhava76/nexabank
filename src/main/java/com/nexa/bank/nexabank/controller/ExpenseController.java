package com.nexa.bank.nexabank.controller;

import com.nexa.bank.nexabank.service.ExpenseAnalysisService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ExpenseController {

    private final ExpenseAnalysisService service;

    public ExpenseController(ExpenseAnalysisService service) {
        this.service = service;
    }

    @GetMapping("/expense-analysis")
    public String analyze(String accountNumber, Model model) {

        if (accountNumber == null) {
            throw new RuntimeException("Account number missing");
        }

        model.addAttribute("summary", service.getExpenseSummary(accountNumber));
        model.addAttribute("accountNumber", accountNumber);

        return "expense-analysis";  // HTML page name
    }
}

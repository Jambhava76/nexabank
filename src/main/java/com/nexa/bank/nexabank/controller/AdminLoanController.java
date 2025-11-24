package com.nexa.bank.nexabank.controller;

import com.nexa.bank.nexabank.service.LoanService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/admin/loans")
public class AdminLoanController {

    @Autowired
    private LoanService loanService;

}

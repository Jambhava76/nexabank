package com.nexa.bank.nexabank.controller;

import com.nexa.bank.nexabank.model.Account;
import com.nexa.bank.nexabank.repository.AccountRepository;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api")
public class AccountApiController {

    private final AccountRepository accountRepo;

    public AccountApiController(AccountRepository accountRepo) {
        this.accountRepo = accountRepo;
    }

    @GetMapping("/check-account")
    public Map<String, Object> checkAccount(@RequestParam("acc") String accNo) {
        Map<String, Object> result = new HashMap<>();

        Account acc = accountRepo.findByAccountNumber(accNo).orElse(null);

        if (acc != null) {
            result.put("found", true);
            result.put("name", acc.getHolderName());
        } else {
            result.put("found", false);
        }

        return result;
    }
}

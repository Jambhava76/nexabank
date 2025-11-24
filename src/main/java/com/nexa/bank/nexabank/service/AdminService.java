package com.nexa.bank.nexabank.service;

import com.nexa.bank.nexabank.model.Admin;
import com.nexa.bank.nexabank.repository.AdminRepository;
import org.springframework.stereotype.Service;

@Service
public class AdminService {

    private final AdminRepository repo;

    public AdminService(AdminRepository repo) {
        this.repo = repo;
    }

    public Admin validateLogin(String adminId, String password) {
        return repo.findByAdminId(adminId)
                .filter(a -> a.getPassword().equals(password))
                .orElse(null);
    }
}

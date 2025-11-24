package com.nexa.bank.nexabank.repository;

import com.nexa.bank.nexabank.model.AdminLog;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AdminLogRepository extends JpaRepository<AdminLog, Integer> {
}

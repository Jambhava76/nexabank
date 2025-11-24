package com.nexa.bank.nexabank.repository;

import com.nexa.bank.nexabank.model.Complaint;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ComplaintRepository extends JpaRepository<Complaint, Long> {

}

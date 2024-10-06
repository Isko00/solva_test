package com.example.transactionservice.repository;

import com.example.transactionservice.model.Account;
import org.springframework.data.jpa.repository.JpaRepository;

import java.math.BigDecimal;

public interface AccountRepository  extends JpaRepository<Account, BigDecimal> {
}

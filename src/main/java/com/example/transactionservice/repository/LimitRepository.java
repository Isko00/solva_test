package com.example.transactionservice.repository;

import com.example.transactionservice.model.Limit;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LimitRepository extends JpaRepository<Limit, Long> {
}

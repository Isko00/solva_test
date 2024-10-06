package com.example.transactionservice.repository;
import com.example.transactionservice.model.Currency;
import com.example.transactionservice.model.ExchangeRate;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.time.LocalDate;

import java.util.List;


@Repository
public interface ExchangeRateRepository extends JpaRepository<ExchangeRate, Long> {
    List<ExchangeRate> findByCurrencyAndDate(Currency currency, LocalDate date);
}
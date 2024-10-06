package com.example.transactionservice.model;

import jakarta.persistence.*;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@Entity
@Table(name = "exchange_rates")
public class ExchangeRate {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Enumerated(EnumType.STRING)
    private Currency currency;
    private BigDecimal close_rate;
    private LocalDate date;

    // No-args constructor (required by JPA)
    public ExchangeRate() {
    }

    public ExchangeRate(Currency currency, BigDecimal close_rate, LocalDate date) {
        this.currency = currency;
        this.close_rate = close_rate;
        this.date = date;
    }
}


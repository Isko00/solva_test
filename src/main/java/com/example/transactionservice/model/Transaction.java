package com.example.transactionservice.model;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
public class Transaction {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long accountFrom;
    private Long accountTo;
    private String currencyShortName;
    private BigDecimal sum;
    private String expenseCategory;
    private LocalDateTime datetime;
    private boolean limitExceeded;

    // Getters and setters
}

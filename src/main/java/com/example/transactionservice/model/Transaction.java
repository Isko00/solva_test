package com.example.transactionservice.model;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "transaction")
public class Transaction {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long accountFrom;
    private Long accountTo;
    private String currencyShortName;
    private BigDecimal sum; // Original amount

    private BigDecimal sumInKZT; // Converted amount in KZT
    private String expenseCategory;
    private LocalDateTime datetime;
    private boolean limitExceeded;
}

package com.example.transactionservice.model;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import lombok.Data;

@Data
@Entity
@Table(name = "limits")
public class Limit {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private BigDecimal sum;
    @Enumerated(EnumType.STRING)
    private Currency currency;
    private Long accountId;
    private LocalDateTime datetime;
    @Enumerated(EnumType.STRING)
    private ExpenseCategory expenseCategory;

    public Limit(Long accountId, BigDecimal sum, Currency currency, LocalDateTime datetime, ExpenseCategory expenseCategory) {
        this.accountId = accountId;
        this.sum = sum;
        this.currency = currency;
        this.datetime = datetime;
        this.expenseCategory = expenseCategory;
    }

    public Limit() {}
}

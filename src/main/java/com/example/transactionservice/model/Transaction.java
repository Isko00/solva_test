package com.example.transactionservice.model;

import jakarta.persistence.*;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "transactions")
public class Transaction {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long accountFrom;
    private Long accountTo;
    @Enumerated(EnumType.STRING)
    private Currency currency;
    private BigDecimal sum;

    private ExpenseCategory expenseCategory;
    private LocalDateTime datetime;
    private boolean limitExceeded;
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "limit_id", referencedColumnName = "id")
    private Limit limit;

    public Transaction(Long accountFrom, Long accountTo, Currency currency, BigDecimal sum,
                       ExpenseCategory expenseCategory, LocalDateTime datetime, boolean limitExceeded, Limit limit) {
        this.accountFrom = accountFrom;
        this.accountTo = accountTo;
        this.currency = currency;
        this.sum = sum;
        this.expenseCategory = expenseCategory;
        this.datetime = datetime;
        this.limitExceeded = limitExceeded;
        this.limit = limit;
    }

    public Transaction() {
    }
}

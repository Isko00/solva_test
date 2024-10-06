package com.example.transactionservice.model.request;

import com.example.transactionservice.model.Currency;

import java.math.BigDecimal;

public record TransactionRequest(Long accountFrom, Long accountTo, Currency currency, BigDecimal sum,
                                 String expenseCategory) {
}
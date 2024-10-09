package com.example.transactionservice.model.request;

import com.example.transactionservice.model.Currency;
import com.example.transactionservice.model.ExpenseCategory;

import java.math.BigDecimal;

public record TransactionRequest(Long accountFrom, Long accountTo, Currency currency, BigDecimal sum,
                                 ExpenseCategory expenseCategory) {
}
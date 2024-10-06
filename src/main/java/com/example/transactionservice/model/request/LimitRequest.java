package com.example.transactionservice.model.request;

import com.example.transactionservice.model.Currency;
import com.example.transactionservice.model.ExpenseCategory;

import java.math.BigDecimal;

public record LimitRequest(Long accountId, BigDecimal sum, Currency currency, ExpenseCategory expenseCategory) {
}

package com.example.transactionservice.model.response;

import com.example.transactionservice.model.Currency;
import com.example.transactionservice.model.ExpenseCategory;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record LimitResponse(Long accountId, BigDecimal sum, Currency currency, ExpenseCategory expenseCategory, LocalDateTime dateTime) {
}

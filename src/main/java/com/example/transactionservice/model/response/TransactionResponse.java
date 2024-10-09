package com.example.transactionservice.model.response;

import com.example.transactionservice.model.Currency;
import com.example.transactionservice.model.ExpenseCategory;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record TransactionResponse(Long accountFrom, Long accountTo, Currency currency, BigDecimal sum,
                                  ExpenseCategory expenseCategory, LocalDateTime transactionTime, Boolean isLimitExceeded, LimitResponse limitResponse) {
}
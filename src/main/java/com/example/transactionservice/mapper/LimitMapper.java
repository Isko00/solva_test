package com.example.transactionservice.mapper;

import com.example.transactionservice.model.Currency;
import com.example.transactionservice.model.ExpenseCategory;
import com.example.transactionservice.model.Limit;
import com.example.transactionservice.model.request.LimitRequest;
import com.example.transactionservice.model.response.LimitResponse;

import java.math.BigDecimal;

public interface LimitMapper {
    Limit toEntity(LimitRequest limitRequest);

    Limit toEntity(Long accountId, BigDecimal sum, Currency currency, ExpenseCategory expenseCategory);

    LimitResponse toResponse(Limit limit);
}

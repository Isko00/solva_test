package com.example.transactionservice.service;

import com.example.transactionservice.model.Currency;
import com.example.transactionservice.model.ExpenseCategory;
import com.example.transactionservice.model.Limit;
import com.example.transactionservice.model.request.LimitRequest;

import java.math.BigDecimal;
import java.util.List;

public interface LimitService {
    boolean isLimitExceeded(ExpenseCategory expenseCategory, Long account, BigDecimal sum);
    Limit getLastLimit(ExpenseCategory expenseCategory, Long accountId);
    Limit save(LimitRequest limitRequest);
    Limit save(Long accountId, BigDecimal sum, Currency currency, ExpenseCategory expenseCategory);

    void reductLimit(Long accountId, BigDecimal sum, Currency currency, ExpenseCategory expenseCategory);

    List<Limit> getAllLimits();
}

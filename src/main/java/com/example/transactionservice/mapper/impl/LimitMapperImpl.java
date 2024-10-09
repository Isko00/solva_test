package com.example.transactionservice.mapper.impl;

import com.example.transactionservice.mapper.LimitMapper;
import com.example.transactionservice.model.*;
import com.example.transactionservice.model.request.LimitRequest;
import com.example.transactionservice.model.response.LimitResponse;
import com.example.transactionservice.service.impl.ExchangeRateServiceImpl;
import lombok.Data;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@Service
public class LimitMapperImpl implements LimitMapper {
    private final ExchangeRateServiceImpl exchangeRateService;

    @Override
    public Limit toEntity(LimitRequest limitRequest) {
        return toEntity(limitRequest.accountId(), limitRequest.sum(), limitRequest.currency(),
                limitRequest.expenseCategory());
    }

    @Override
    public Limit toEntity(Long accountId, BigDecimal sum, Currency currency, ExpenseCategory expenseCategory) {
        BigDecimal amountInUSD = exchangeRateService.convertToUSD(currency, sum);

        return new Limit(
                accountId,
                amountInUSD,
                amountInUSD,
                currency,
                LocalDateTime.now(), expenseCategory);
    }

    @Override
    public LimitResponse toResponse(Limit limit) {
        return new LimitResponse(limit.getAccountId(), limit.getSum(), limit.getCurrency(), limit.getExpenseCategory(),
                limit.getDatetime());
    }
}

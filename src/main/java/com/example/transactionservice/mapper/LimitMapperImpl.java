package com.example.transactionservice.mapper;

import com.example.transactionservice.model.*;
import com.example.transactionservice.model.request.LimitRequest;
import com.example.transactionservice.service.ExchangeRateServiceImpl;
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
        return toEntity(limitRequest.accountId(), limitRequest.sum(), limitRequest.currency(), limitRequest.expenseCategory());
    }

    @Override
    public Limit toEntity(Long accountId, BigDecimal sum, Currency currency, ExpenseCategory expenseCategory) {
        // Convert the transaction amount to USD
        BigDecimal amountInUSD = exchangeRateService.convertToUSD(currency, sum);

        return new Limit(
                accountId,
                amountInUSD,
                currency,
                LocalDateTime.now(),
                expenseCategory);
    }
}

package com.example.transactionservice.mapper;

import com.example.transactionservice.model.Currency;
import com.example.transactionservice.model.ExpenseCategory;
import com.example.transactionservice.model.Transaction;
import com.example.transactionservice.model.request.TransactionRequest;
import com.example.transactionservice.service.ExchangeRateServiceImpl;
import com.example.transactionservice.service.LimitServiceImpl;
import lombok.Data;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@Service
public class TransactionMapperImpl implements TransactionMapper {
    private final ExchangeRateServiceImpl exchangeRateService;
    private final LimitServiceImpl limitService;

    @Override
    public Transaction toEntity(TransactionRequest transactionRequest) {
        Currency currency = transactionRequest.currency();

        ExpenseCategory expenseCategory = ExpenseCategory.valueOf(transactionRequest.expenseCategory());
        // Convert the transaction amount to USD
        BigDecimal amountInUSD = exchangeRateService.convertToUSD(currency, transactionRequest.sum());

        return new Transaction(
                transactionRequest.accountFrom(),
                transactionRequest.accountTo(),
                transactionRequest.currency(),
                amountInUSD,
                expenseCategory,
                LocalDateTime.now(),
                limitService.isLimitExceeded(expenseCategory, transactionRequest.accountFrom(), amountInUSD));
    }
}

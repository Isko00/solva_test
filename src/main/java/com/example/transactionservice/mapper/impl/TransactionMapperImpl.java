package com.example.transactionservice.mapper.impl;

import com.example.transactionservice.mapper.LimitMapper;
import com.example.transactionservice.mapper.TransactionMapper;
import com.example.transactionservice.model.Limit;
import com.example.transactionservice.model.Transaction;
import com.example.transactionservice.model.request.TransactionRequest;
import com.example.transactionservice.model.response.TransactionResponse;
import lombok.Data;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@Service
public class TransactionMapperImpl implements TransactionMapper {
    private final LimitMapper limitMapper;

    @Override
    public Transaction toEntity(TransactionRequest transactionRequest, BigDecimal amountInUSD, boolean isLimitExceeded, Limit limit) {
        return new Transaction(
                transactionRequest.accountFrom(),
                transactionRequest.accountTo(),
                transactionRequest.currency(),
                amountInUSD,
                transactionRequest.expenseCategory(),
                LocalDateTime.now(),
                isLimitExceeded,
                limit);
    }

    @Override
    public TransactionResponse toResponse(Transaction transaction) {
        return new TransactionResponse(transaction.getAccountFrom(), transaction.getAccountTo(),
                transaction.getCurrency(), transaction.getSum(),transaction.getExpenseCategory(), transaction.getDatetime(),
                transaction.isLimitExceeded(), limitMapper.toResponse(transaction.getLimit()));
    }
}

package com.example.transactionservice.mapper;

import com.example.transactionservice.model.Limit;
import com.example.transactionservice.model.Transaction;
import com.example.transactionservice.model.request.TransactionRequest;
import com.example.transactionservice.model.response.TransactionResponse;

import java.math.BigDecimal;

public interface TransactionMapper {
    Transaction toEntity(TransactionRequest transactionRequest, BigDecimal amountInUSD, boolean isLimitExceeded, Limit limit);

    TransactionResponse toResponse(Transaction transaction);
}
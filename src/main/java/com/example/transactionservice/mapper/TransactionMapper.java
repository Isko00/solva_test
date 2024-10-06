package com.example.transactionservice.mapper;

import com.example.transactionservice.model.Transaction;
import com.example.transactionservice.model.request.TransactionRequest;

public interface TransactionMapper {
    Transaction toEntity(TransactionRequest transactionRequest);
}
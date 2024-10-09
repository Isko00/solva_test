package com.example.transactionservice.service;

import com.example.transactionservice.model.request.TransactionRequest;
import com.example.transactionservice.model.response.TransactionResponse;

import java.util.List;

public interface TransactionService {
    void save(TransactionRequest transactionRequest);
    List<TransactionResponse> getExceededTransactions();
}

package com.example.transactionservice.service;

import com.example.transactionservice.model.Transaction;
import com.example.transactionservice.model.request.TransactionRequest;

import java.util.List;

public interface TransactionService {
    Transaction save(TransactionRequest transactionRequest);
    List<Transaction> getAllTransactions();
    List<Transaction> getExceededTransactions();
}

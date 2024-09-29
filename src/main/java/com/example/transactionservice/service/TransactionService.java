package com.example.transactionservice.service;

import com.example.transactionservice.model.Transaction;
import com.example.transactionservice.repository.TransactionRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TransactionService {

    private final TransactionRepository transactionRepository;

    public TransactionService(TransactionRepository transactionRepository) {
        this.transactionRepository = transactionRepository;
    }

    public Transaction saveTransaction(Transaction transaction) {
        // Business logic for handling transactions goes here
        return transactionRepository.save(transaction);
    }

    public List<Transaction> getExceededTransactions() {
        return transactionRepository.findByLimitExceeded(true);
    }
}

package com.example.transactionservice.service;

import com.example.transactionservice.mapper.TransactionMapper;
import com.example.transactionservice.model.Transaction;
import com.example.transactionservice.model.request.TransactionRequest;
import com.example.transactionservice.repository.TransactionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
@RequiredArgsConstructor
@Service
public class TransactionServiceImpl implements TransactionService {

    private final TransactionRepository transactionRepository;
    private final TransactionMapper transactionMapper;
    private final LimitService limitService;

    @Transactional
    public Transaction save(TransactionRequest transactionRequest) {
        // Save the transaction
        Transaction saveTransaction = transactionRepository.save(transactionMapper.toEntity(transactionRequest));
        limitService.reductLimit(saveTransaction.getAccountFrom(), saveTransaction.getSum(), saveTransaction.getCurrency(), saveTransaction.getExpenseCategory());
        return saveTransaction;
    }

    public List<Transaction> getAllTransactions() {
        return transactionRepository.findAll();
    }

    // Method to get transactions where limitExceeded is true
    public List<Transaction> getExceededTransactions() {
        return transactionRepository.findByLimitExceeded(true);
    }
}

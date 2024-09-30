package com.example.transactionservice.service;

import com.example.transactionservice.model.Transaction;
import com.example.transactionservice.repository.TransactionRepository;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
public class TransactionService {

    private final TransactionRepository transactionRepository;
    private final CurrencyExchangeService currencyExchangeService;

    public TransactionService(TransactionRepository transactionRepository, CurrencyExchangeService currencyExchangeService) {
        this.transactionRepository = transactionRepository;
        this.currencyExchangeService = currencyExchangeService;
    }

    public Transaction saveTransaction(Transaction transaction) {
        // Convert the transaction amount to KZT
        BigDecimal amountInKZT = currencyExchangeService.convertToKZT(transaction.getCurrencyShortName(), transaction.getSum());

        // Set the converted amount
        transaction.setSumInKZT(amountInKZT);

        // Save the transaction
        return transactionRepository.save(transaction);
    }

    public List<Transaction> getAllTransactions() {
        return transactionRepository.findAll();
    }

    // Method to get transactions where limitExceeded is true
    public List<Transaction> getExceededTransactions() {
        return transactionRepository.findByLimitExceeded(true);
    }
}

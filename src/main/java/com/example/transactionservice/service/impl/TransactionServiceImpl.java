package com.example.transactionservice.service.impl;

import com.example.transactionservice.mapper.TransactionMapper;
import com.example.transactionservice.model.ExpenseCategory;
import com.example.transactionservice.model.Limit;
import com.example.transactionservice.model.Transaction;
import com.example.transactionservice.model.request.TransactionRequest;
import com.example.transactionservice.model.response.TransactionResponse;
import com.example.transactionservice.repository.TransactionRepository;
import com.example.transactionservice.service.ExchangeRateService;
import com.example.transactionservice.service.LimitService;
import com.example.transactionservice.service.TransactionService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
@RequiredArgsConstructor
@Service
public class TransactionServiceImpl implements TransactionService {

    private final TransactionRepository transactionRepository;
    private final TransactionMapper transactionMapper;
    private final LimitService limitService;
    private final ExchangeRateService exchangeRateService;

    @Transactional
    public void save(TransactionRequest transactionRequest) {
        BigDecimal sum = transactionRequest.sum();
        ExpenseCategory expenseCategory = transactionRequest.expenseCategory();
        Long accountFrom = transactionRequest.accountFrom();

        BigDecimal amountInUSD = exchangeRateService.convertToUSD(transactionRequest.currency(), sum);
        boolean isLimitExceeded = limitService.isLimitExceeded(expenseCategory, accountFrom,sum);
        Limit limit = limitService.getLastLimit(expenseCategory, accountFrom);
        Transaction entity = transactionMapper.toEntity(transactionRequest, amountInUSD, isLimitExceeded, limit);

        Transaction saveTransaction = transactionRepository.save(entity);

        limitService.reductLimit(saveTransaction.getAccountFrom(), saveTransaction.getSum(),
                saveTransaction.getCurrency(), saveTransaction.getExpenseCategory());
    }

    public List<TransactionResponse> getExceededTransactions() {
        return transactionRepository.findByLimitExceeded(true).stream()
                .map(transactionMapper::toResponse)
                .toList();
    }
}

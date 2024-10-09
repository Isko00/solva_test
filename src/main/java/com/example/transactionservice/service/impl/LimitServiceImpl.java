package com.example.transactionservice.service.impl;

import com.example.transactionservice.mapper.impl.LimitMapperImpl;
import com.example.transactionservice.model.*;
import com.example.transactionservice.model.request.LimitRequest;
import com.example.transactionservice.repository.LimitRepository;
import com.example.transactionservice.service.LimitService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class LimitServiceImpl implements LimitService {

    private final LimitRepository limitRepository;
    private final LimitMapperImpl limitMapper;

    public boolean isLimitExceeded(ExpenseCategory expenseCategory, Long account, BigDecimal sum) {
         return getLastLimit(expenseCategory, account).getBalance().compareTo(sum) < 0;
    }

    public Limit getLastLimit(ExpenseCategory expenseCategory, Long accountId) {
        Optional<Limit> limitOptional = limitRepository.findLastLimitByExpenseCategoryAndAccountId(expenseCategory, accountId);

        return limitOptional.orElseThrow(() -> new IllegalArgumentException("No limits found for the specified parameters."));
    }

    public void save(LimitRequest limitRequest) {
        limitRepository.save(limitMapper.toEntity(limitRequest));
    }

    @Override
    public Limit save(Long accountId, BigDecimal sum, Currency currency, ExpenseCategory expenseCategory) {
        var saveLimit = limitMapper.toEntity(accountId, sum, currency, expenseCategory);
        return limitRepository.save(saveLimit);
    }

    @Override
    public void reductLimit(Long accountId, BigDecimal sum, Currency currency, ExpenseCategory expenseCategory) {
        Limit lastLimit = getLastLimit(expenseCategory, accountId);
        lastLimit.setBalance(lastLimit.getBalance().subtract(sum));
        limitRepository.save(lastLimit);
    }

    public List<Limit> getAllLimits() {
        return limitRepository.findAll();
    }
}

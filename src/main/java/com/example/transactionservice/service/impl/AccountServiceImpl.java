package com.example.transactionservice.service.impl;

import com.example.transactionservice.mapper.AccountMapper;
import com.example.transactionservice.model.Account;
import com.example.transactionservice.model.Currency;
import com.example.transactionservice.model.ExpenseCategory;
import com.example.transactionservice.model.request.AccountRequest;
import com.example.transactionservice.repository.AccountRepository;
import com.example.transactionservice.service.AccountService;
import com.example.transactionservice.service.LimitService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;

@RequiredArgsConstructor
@Service
public class AccountServiceImpl implements AccountService {
    private final AccountRepository accountRepository;
    private final AccountMapper accountMapper;
    private final LimitService limitService;

    @Transactional
    public Account save(AccountRequest accountRequest) {
        Account savedAccount = accountRepository.save(accountMapper.toEntity(accountRequest));
        addDefaultLimits(savedAccount.getId());
        return savedAccount;
    }

    private void addDefaultLimits(Long accountId) {
        BigDecimal defaultLimit = new BigDecimal("1000.00");

        limitService.save(accountId, defaultLimit, Currency.USD, ExpenseCategory.GOODS);

        limitService.save(accountId, defaultLimit, Currency.USD, ExpenseCategory.SERVICES);
    }
}

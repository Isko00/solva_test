package com.example.transactionservice.mapper;

import com.example.transactionservice.model.Account;
import com.example.transactionservice.model.request.AccountRequest;

public interface AccountMapper {
    Account toEntity(AccountRequest accountRequest);
}

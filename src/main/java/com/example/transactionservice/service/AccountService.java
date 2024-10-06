package com.example.transactionservice.service;

import com.example.transactionservice.model.Account;
import com.example.transactionservice.model.request.AccountRequest;

public interface AccountService {
    Account save(AccountRequest accountRequest);
}

package com.example.transactionservice.mapper;

import com.example.transactionservice.model.Account;
import com.example.transactionservice.model.request.AccountRequest;
import lombok.Data;
import org.springframework.stereotype.Service;

@Data
@Service
public class AccountMapperImpl implements AccountMapper {
    public Account toEntity(AccountRequest accountRequest) {
        return new Account(accountRequest.name());
    }
}

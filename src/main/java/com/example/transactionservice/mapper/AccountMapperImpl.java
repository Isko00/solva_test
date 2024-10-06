package com.example.transactionservice.mapper;

import com.example.transactionservice.model.Account;
import com.example.transactionservice.model.ExpenseCategory;
import com.example.transactionservice.model.Limit;
import com.example.transactionservice.model.request.AccountRequest;
import com.example.transactionservice.model.request.LimitRequest;
import lombok.Data;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@Service
public class AccountMapperImpl implements AccountMapper {
    public Account toEntity(AccountRequest accountRequest) {
        return new Account(accountRequest.name());
    }
}

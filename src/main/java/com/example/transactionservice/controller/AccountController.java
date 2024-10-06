package com.example.transactionservice.controller;

import com.example.transactionservice.model.Account;
import com.example.transactionservice.model.Transaction;
import com.example.transactionservice.model.request.AccountRequest;
import com.example.transactionservice.service.AccountService;
import com.example.transactionservice.service.AccountServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/account")
public class AccountController {
    private final AccountServiceImpl accountService;

    @PostMapping
    public ResponseEntity add(@RequestBody AccountRequest accountRequest) {
        Account savedAccount = accountService.save(accountRequest);
        return ResponseEntity.status(201).body(savedAccount);
    }
}

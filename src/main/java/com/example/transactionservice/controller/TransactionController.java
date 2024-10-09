package com.example.transactionservice.controller;

import com.example.transactionservice.model.Transaction;
import com.example.transactionservice.model.request.TransactionRequest;
import com.example.transactionservice.model.response.TransactionResponse;
import com.example.transactionservice.service.TransactionService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/transaction")
public class TransactionController {

    private final TransactionService transactionService;

    @PostMapping
    public ResponseEntity<Transaction> add(@RequestBody TransactionRequest transactionRequest) {
        transactionService.save(transactionRequest);
        return ResponseEntity.status(201).build();
    }

    @GetMapping("/exceeded")
    public ResponseEntity<List<TransactionResponse>> getExceededTransactions() {
        var transactions = transactionService.getExceededTransactions();
        return ResponseEntity.ok(transactions);
    }
}

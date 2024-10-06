package com.example.transactionservice.controller;

import com.example.transactionservice.model.Transaction;
import com.example.transactionservice.model.request.TransactionRequest;
import com.example.transactionservice.service.TransactionServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/transaction")
public class TransactionController {

    private final TransactionServiceImpl transactionService;

    @PostMapping
    public ResponseEntity<Transaction> add(@RequestBody TransactionRequest transactionRequest) {
        Transaction savedTransaction = transactionService.save(transactionRequest);
        return ResponseEntity.status(201).body(savedTransaction);
    }

    @GetMapping("/exceeded")
    public ResponseEntity<List<Transaction>> getExceededTransactions() {
        List<Transaction> transactions = transactionService.getExceededTransactions();
        return ResponseEntity.ok(transactions);
    }
}

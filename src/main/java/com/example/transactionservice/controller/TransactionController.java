package com.example.transactionservice.controller;

import com.example.transactionservice.model.Transaction;
import com.example.transactionservice.service.TransactionService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/transactions")
public class TransactionController {

    private final TransactionService transactionService;

    public TransactionController(TransactionService transactionService) {
        this.transactionService = transactionService;
    }

    @PostMapping
    public ResponseEntity<Transaction> saveTransaction(@RequestBody Transaction transaction) {
        Transaction savedTransaction = transactionService.saveTransaction(transaction);
        return ResponseEntity.status(201).body(savedTransaction);
    }

    @GetMapping("/exceeded")
    public ResponseEntity<List<Transaction>> getExceededTransactions() {
        List<Transaction> transactions = transactionService.getExceededTransactions();
        return ResponseEntity.ok(transactions);
    }
}

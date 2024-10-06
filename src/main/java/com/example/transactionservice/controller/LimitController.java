package com.example.transactionservice.controller;

import com.example.transactionservice.model.Limit;
import com.example.transactionservice.model.request.LimitRequest;
import com.example.transactionservice.service.LimitServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/limit")
public class LimitController {

    private final LimitServiceImpl limitService;

    @PostMapping
    public ResponseEntity<Limit> add(@RequestBody LimitRequest limitRequest) {
        Limit newLimit = limitService.save(limitRequest);
        return ResponseEntity.status(201).body(newLimit);
    }

    @GetMapping
    public ResponseEntity<List<Limit>> getAllLimits() {
        List<Limit> limits = limitService.getAllLimits();
        return ResponseEntity.ok(limits);
    }
}

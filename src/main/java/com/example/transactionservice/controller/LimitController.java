package com.example.transactionservice.controller;

import com.example.transactionservice.model.Limit;
import com.example.transactionservice.model.request.LimitRequest;
import com.example.transactionservice.service.LimitService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/limit")
public class LimitController {

    private final LimitService limitService;

    @PostMapping
    public ResponseEntity<Limit> add(@RequestBody LimitRequest limitRequest) {
        limitService.save(limitRequest);
        return ResponseEntity.status(201).build();
    }
}

package com.example.transactionservice.controller;

import com.example.transactionservice.model.Limit;
import com.example.transactionservice.service.LimitService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/limits")
public class LimitController {

    private final LimitService limitService;

    public LimitController(LimitService limitService) {
        this.limitService = limitService;
    }

    @PostMapping
    public ResponseEntity<Limit> setNewLimit(@RequestBody Limit limitRequest) {
        Limit newLimit = limitService.setNewLimit(limitRequest);
        return ResponseEntity.ok(newLimit);
    }

    @GetMapping
    public ResponseEntity<List<Limit>> getAllLimits() {
        List<Limit> limits = limitService.getAllLimits();
        return ResponseEntity.ok(limits);
    }
}

package com.example.transactionservice.service;

import com.example.transactionservice.model.Limit;
import com.example.transactionservice.repository.LimitRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.time.LocalDateTime;

@Service
public class LimitService {

    private final LimitRepository limitRepository;

    public LimitService(LimitRepository limitRepository) {
        this.limitRepository = limitRepository;
    }

    public Limit setNewLimit(Limit limitRequest) {
        // Logic to set a new limit
            limitRequest.setLimitDatetime(LocalDateTime.now());
        return limitRepository.save(limitRequest);
    }

    public List<Limit> getAllLimits() {
        return limitRepository.findAll();
    }
}

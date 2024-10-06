package com.example.transactionservice.service.scheduler;

import com.example.transactionservice.service.LimitServiceImpl;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@RequiredArgsConstructor
@Component
@Log4j2
public class LimitResetScheduler {
    @Scheduled(cron = "0 0 0 1 * *")
    public void reportCurrentTime() {
        log.info("The time is now {}", LocalDateTime.now());
    }
}

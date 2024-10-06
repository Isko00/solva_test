package com.example.transactionservice.service;

import com.example.transactionservice.model.Currency;
import com.example.transactionservice.model.ExchangeRate;

import java.math.BigDecimal;

public interface ExchangeRateService {
    ExchangeRate save(Currency fromCurrency);
    BigDecimal get(Currency fromCurrency);
    BigDecimal convertToUSD(Currency fromCurrency, BigDecimal sum);
}

package com.example.transactionservice.service.impl;

import com.example.transactionservice.model.Currency;
import com.example.transactionservice.model.ExchangeRate;
import com.example.transactionservice.repository.ExchangeRateRepository;
import com.example.transactionservice.service.ExchangeRateService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.math.RoundingMode;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Map;
import java.math.BigDecimal;
import java.util.List;

@RequiredArgsConstructor
@Service
public class ExchangeRateServiceImpl implements ExchangeRateService {

    private final ExchangeRateRepository exchangeRateRepository;

    @Value("${api.currency.exchange.url}")  // Add this in application.properties
    private String apiUrl;

    @Value("${api.currency.exchange.key}")  // Add this in application.properties
    private String apiKey;

    public static LocalDate getLastWeekdayIfWeekend(LocalDate date) {
        DayOfWeek dayOfWeek = date.getDayOfWeek();

        if (dayOfWeek == DayOfWeek.SATURDAY) {
            return date.minusDays(1);
        } else if (dayOfWeek == DayOfWeek.SUNDAY) {
            return date.minusDays(2);
        } else {
            return date;
        }
    }

    public ExchangeRate save(Currency fromCurrency) {
        String url = String.format("%s?symbol=USD/%s&apikey=%s", apiUrl, fromCurrency, apiKey);

        @SuppressWarnings("unchecked")
        Map<String, Object> response = restTemplate.getForObject(url, Map.class);

        if (response != null && response.containsKey("close")) {
            String rateString = response.get("close").toString();
            BigDecimal rateBigDecimal = new BigDecimal(rateString);
            String dateString = response.get("datetime").toString();
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            LocalDate date = LocalDate.parse(dateString, formatter);
            ExchangeRate exchangeRate = new ExchangeRate(fromCurrency, rateBigDecimal, date);
            return exchangeRateRepository.save(exchangeRate);
        }

        throw new IllegalStateException("Unable to retrieve exchange rate");
    }

    private final RestTemplate restTemplate = new RestTemplate();

    public BigDecimal get(Currency fromCurrency) {
        if (fromCurrency.equals(Currency.USD)) {
            return BigDecimal.valueOf(1);
        }

        LocalDate lastWeekday = getLastWeekdayIfWeekend(LocalDate.now());

        List<ExchangeRate> myList = exchangeRateRepository.findByCurrencyAndDate(fromCurrency, lastWeekday);

        if (myList == null || myList.isEmpty()) {
            return save(fromCurrency).getClose_rate();
        }

        return myList.get(0).getClose_rate();
    }

    public BigDecimal convertToUSD(Currency fromCurrency, BigDecimal sum) {
        return sum.divide(get(fromCurrency), 3, RoundingMode.HALF_UP);
    }
}

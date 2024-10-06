package com.example.transactionservice.service;

import com.example.transactionservice.model.Currency;
import com.example.transactionservice.model.ExchangeRate;
import com.example.transactionservice.repository.ExchangeRateRepository;
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
            // If today is Saturday, return the previous Friday
            return date.minusDays(1);
        } else if (dayOfWeek == DayOfWeek.SUNDAY) {
            // If today is Sunday, return the previous Friday
            return date.minusDays(2);
        } else {
            // If today is a weekday, return the same date
            return date;
        }
    }

    public ExchangeRate save(Currency fromCurrency) {
        // Example URL format: https://api.twelvedata.com/eod?symbol=USD/KZT&apikey=eeb2f14085a44291b236cb0f13d890a2
        String url = String.format("%s?symbol=USD/%s&apikey=%s", apiUrl, fromCurrency, apiKey);

        // Call the TwelveData API and get the response as a Map
        Map<String, Object> response = restTemplate.getForObject(url, Map.class);

        // Extract the exchange rate from the response
        if (response != null && response.containsKey("close")) {
            String rateString = response.get("close").toString();
            BigDecimal rateBigDecimal = new BigDecimal(rateString);
            String dateString = response.get("datetime").toString();
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            // Parse the date
            LocalDate date = LocalDate.parse(dateString, formatter);
            ExchangeRate exchangeRate = new ExchangeRate(fromCurrency, rateBigDecimal, date);
            // Save the exchangeRate
            return exchangeRateRepository.save(exchangeRate);
        }

        // If no valid response is found, throw an exception or return a default value
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

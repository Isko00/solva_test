package com.example.transactionservice.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.math.BigDecimal;

@Service
public class CurrencyExchangeService {

    private final WebClient webClient;

    @Value("${api.currency.exchange.url}")  // Add this in application.properties
    private String apiUrl;

    @Value("${api.currency.exchange.key}")  // Add this in application.properties
    private String apiKey;

    public CurrencyExchangeService(WebClient webClient) {
        this.webClient = webClient;
    }

    public BigDecimal getExchangeRate(String fromCurrency, String toCurrency) {
        // Example URL format: https://api.example.com/convert?from=KZT&to=USD&apikey=your_api_key
        String url = String.format("%s?from=%s&to=%s&apikey=%s", apiUrl, fromCurrency, toCurrency, apiKey);

        Mono<BigDecimal> response = webClient.get()
                .uri(url)
                .retrieve()
                .bodyToMono(ExchangeRateResponse.class)
                .map(ExchangeRateResponse::getRate);  // Assume API response contains a 'rate' field

        return response.block();  // Block to get the result synchronously
    }

    public BigDecimal convertToKZT(String fromCurrency, BigDecimal amount) {
        // Fetch the exchange rate from the specified currency to KZT
        BigDecimal exchangeRate = getExchangeRate(fromCurrency, "KZT");

        // Return the amount converted to KZT
        return amount.multiply(exchangeRate);
    }


    // Create a model class for the response
    public static class ExchangeRateResponse {
        private BigDecimal rate;

        public BigDecimal getRate() {
            return rate;
        }

        public void setRate(BigDecimal rate) {
            this.rate = rate;
        }
    }
}

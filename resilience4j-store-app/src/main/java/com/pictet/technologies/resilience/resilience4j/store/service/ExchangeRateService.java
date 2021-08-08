package com.pictet.technologies.resilience.resilience4j.store.service;

import com.pictet.technologies.resilience.resilience4j.store.provider.exchangerate.ExchangeRateClient;
import com.pictet.technologies.resilience.resilience4j.store.provider.exchangerate.api.CurrencyExchangeRates;
import feign.FeignException;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;

@Service
@RequiredArgsConstructor
@Slf4j
public class ExchangeRateService {

    private final ExchangeRateClient exchangeRateClient;

    // Cached exchange rates, for recovery purpose
    private CurrencyExchangeRates latestExchangesRates;

    @CircuitBreaker(name = "getExchangeRates", fallbackMethod = "getExchangeRatesFallback")
    public CurrencyExchangeRates getExchangeRates(String currency) {
        final CurrencyExchangeRates rates = exchangeRateClient.getLatest(currency, BigDecimal.ONE);
        this.latestExchangesRates = rates;
        return rates;
    }

    public CurrencyExchangeRates getExchangeRatesFallback(String currency, FeignException feignException) {

        log.error("Ooops something went wrong when calling the exchange rate service", feignException);

        if (latestExchangesRates == null) {
            log.error("No exchange rates found for {}", currency);
            return null;
        }

        log.warn("use the latest received exchange rates received on {}", latestExchangesRates.getDate(), feignException);
        if (latestExchangesRates.getBase().equals(currency)) {
            // No conversion to perform
            return latestExchangesRates;
        }

        // Build a new currency exchange rate
        return new CurrencyExchangeRates()
                .setBase(currency)
                .setRates(new CurrencyExchangeRates.ExchangeRates()
                        .setCad(computeExchangeRate(latestExchangesRates, currency, "CAD"))
                        .setEur(computeExchangeRate(latestExchangesRates, currency, "EUR"))
                        .setUsd(computeExchangeRate(latestExchangesRates, currency, "USD"))
                        .setJpy(computeExchangeRate(latestExchangesRates, currency, "JPY"))
                        .setGbp(computeExchangeRate(latestExchangesRates, currency, "GBP")));
    }

    private BigDecimal computeExchangeRate(CurrencyExchangeRates exchangeRates, String baseCurrency, String targetCurrency) {

        final BigDecimal conversionRate = exchangeRates.getConversionRate(baseCurrency);
        return latestExchangesRates.getConversionRate(targetCurrency).divide(conversionRate, 10, RoundingMode.HALF_UP);
    }

}

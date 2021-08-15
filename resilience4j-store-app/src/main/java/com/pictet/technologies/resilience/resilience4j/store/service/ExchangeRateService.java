package com.pictet.technologies.resilience.resilience4j.store.service;

import com.pictet.technologies.resilience.resilience4j.store.provider.exchangerate.ExchangeRateClient;
import com.pictet.technologies.resilience.resilience4j.store.provider.exchangerate.api.CurrencyExchangeRates;
import io.github.resilience4j.bulkhead.BulkheadFullException;
import io.github.resilience4j.bulkhead.annotation.Bulkhead;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.retry.annotation.Retry;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;

@Service
@RequiredArgsConstructor
@Slf4j
public class ExchangeRateService {

    private static final String GET_RATE_RESILIENCE_NAME = "exchangeRateService-get";

    private final ExchangeRateClient exchangeRateClient;

    // Cached exchange rates, for recovery purpose
    private CurrencyExchangeRates latestExchangesRates;

    // When using retry and circuit breaker together
    // - Don't specify a fallback on the retry
    // - Change the aspect order (see the application.yaml)
    //@Retry(name = ExchangeRateService.GET_RATE_RESILIENCE_NAME, fallbackMethod = GET_RATE_FALLBACK)
    @Retry(name = ExchangeRateService.GET_RATE_RESILIENCE_NAME)
    @CircuitBreaker(name = ExchangeRateService.GET_RATE_RESILIENCE_NAME, fallbackMethod = "getExchangeRatesFallback")
    @Bulkhead(name = ExchangeRateService.GET_RATE_RESILIENCE_NAME, fallbackMethod = "getExchangeRatesFallbackBulkhead")
    public CurrencyExchangeRates getExchangeRates(String currency) {

        try {
            final CurrencyExchangeRates rates = exchangeRateClient.getLatest(currency, BigDecimal.ONE);
            this.latestExchangesRates = rates;
            log.info("Exchange rate service called successfully");

            return rates;

        } catch (Exception ex) {
            log.error("Exchange rate service failed with message: {}", ex.getMessage());
            throw ex;
        }

    }

    public CurrencyExchangeRates getExchangeRatesFallbackBulkhead(String currency, BulkheadFullException exception) {
        return getExchangeRatesFallback(currency, exception);
    }

    public CurrencyExchangeRates getExchangeRatesFallback(String currency, Exception exception) {

        log.error("Exchange rate fallback applied because of {}: {}", exception.getClass().getCanonicalName(), exception.getMessage());

        if (latestExchangesRates == null) {
            log.error("No exchange rates found for {}", currency);
            return null;
        }

        log.warn("use the latest received exchange rates received on {}", latestExchangesRates.getDate());
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

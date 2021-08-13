package com.pictet.technologies.resilience.resilience4j.store.service;

import com.pictet.technologies.resilience.resilience4j.store.provider.exchangerate.ExchangeRateClient;
import com.pictet.technologies.resilience.resilience4j.store.provider.exchangerate.api.CurrencyExchangeRates;
import feign.FeignException;
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

    private static final String RESILIENCE_NAME = "exchangeRateService-get";

    private final ExchangeRateClient exchangeRateClient;

    // Cached exchange rates, for recovery purpose
    private CurrencyExchangeRates latestExchangesRates;

    // When using retry and circuit breaker together
    // - Don't specify a fallback if you are also using a circuit breaker
    // - Change the aspect order (see the application.yaml)
    @Retry(name = ExchangeRateService.RESILIENCE_NAME /*, fallbackMethod = "getExchangeRatesRetryFallback"*/ )
    @CircuitBreaker(name = ExchangeRateService.RESILIENCE_NAME, fallbackMethod = "getExchangeRatesCircuitBreakerFallback")
    public CurrencyExchangeRates getExchangeRates(String currency) {

        try {
            log.info("Call the exchange rate service");

            final CurrencyExchangeRates rates = exchangeRateClient.getLatest(currency, BigDecimal.ONE);
            this.latestExchangesRates = rates;
            return rates;
        } catch(Exception ex) {
            log.error("getExchangeRates failed with message: {}", ex.getMessage());
            throw ex;
        }
    }


    public CurrencyExchangeRates getExchangeRatesCircuitBreakerFallback(String currency, Exception exception) {

        log.info("getExchangeRatesCircuitBreakerFallback");
        return getExchangeRatesFallback(currency, exception);
    }

    public CurrencyExchangeRates getExchangeRatesRetryFallback(String currency, Exception exception) {

        log.info("getExchangeRatesRetryFallback");
        return getExchangeRatesFallback(currency, exception);
    }

    public CurrencyExchangeRates getExchangeRatesFallback(String currency, Exception exception) {

        log.error("getExchangeRatesFallback applied because of ", exception);

        if (latestExchangesRates == null) {
            log.error("No exchange rates found for {}", currency);
            return null;
        }

        log.warn("use the latest received exchange rates received on {}", latestExchangesRates.getDate(), exception);
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

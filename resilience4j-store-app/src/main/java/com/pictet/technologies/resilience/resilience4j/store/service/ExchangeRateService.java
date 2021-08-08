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
    private CurrencyExchangeRates latestExchangesRates;

    @CircuitBreaker(name = "getExchangeRates", fallbackMethod = "getExchangeRatesFallback")
    public CurrencyExchangeRates getExchangeRates(String currency) {
        final CurrencyExchangeRates rates = exchangeRateClient.getLatest(currency, BigDecimal.ONE);
        this.latestExchangesRates = rates;
        return rates;
    }

    public CurrencyExchangeRates getExchangeRatesFallback(String currency, FeignException feignException) {

        log.error("Ooops something went wrong when calling the exchange rate service", feignException);

        if(latestExchangesRates != null) {

            log.warn("use the latest received exchange rates", feignException);

            if(latestExchangesRates.getBase().equals(currency)) {
                return latestExchangesRates;
            }

            // Build a new currency exchange rate
            final CurrencyExchangeRates currencyExchangeRates = new CurrencyExchangeRates();
            currencyExchangeRates.setBase(currency);

            final CurrencyExchangeRates.ExchangeRates exchangeRates = new CurrencyExchangeRates.ExchangeRates();
            currencyExchangeRates.setRates(exchangeRates);

            final BigDecimal conversionRate = latestExchangesRates.getConversionRate(currency);
            exchangeRates.setCad(latestExchangesRates.getConversionRate("CAD").divide(conversionRate, 10, RoundingMode.HALF_UP));
            exchangeRates.setEur(latestExchangesRates.getConversionRate("EUR").divide(conversionRate, 10, RoundingMode.HALF_UP));
            exchangeRates.setUsd(latestExchangesRates.getConversionRate("USD").divide(conversionRate, 10, RoundingMode.HALF_UP));
            exchangeRates.setJpy(latestExchangesRates.getConversionRate("JPY").divide(conversionRate, 10, RoundingMode.HALF_UP));
            exchangeRates.setGbp(latestExchangesRates.getConversionRate("GBP").divide(conversionRate, 10, RoundingMode.HALF_UP));

            return currencyExchangeRates;
        }

        return null;
    }

}

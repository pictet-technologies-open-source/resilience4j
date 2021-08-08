package com.pictet.technologies.resilience.resilience4j.store.provider.exchangerate.api;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
public class CurrencyExchangeRates {

    private String base;
    private LocalDate date;
    private ExchangeRates rates;

    @Data
    public static class ExchangeRates {

        @JsonProperty("CAD")
        private BigDecimal cad;

        @JsonProperty("EUR")
        private BigDecimal eur;

        @JsonProperty("GBP")
        private BigDecimal gbp;

        @JsonProperty("JPY")
        private BigDecimal jpy;

        @JsonProperty("USD")
        private BigDecimal usd;
    }

    public BigDecimal getConversionRate(String toCurrency) {

        final BigDecimal conversionRate;
        switch (toCurrency) {
            case "EUR":
                conversionRate = this.getRates().getEur();
                break;
            case "CAD":
                conversionRate = this.getRates().getCad();
                break;
            case "USD":
                conversionRate = this.getRates().getUsd();
                break;
            case "GBP":
                conversionRate = this.getRates().getGbp();
                break;
            case "JPY":
                conversionRate = this.getRates().getJpy();
                break;
            default:
                throw new IllegalArgumentException("Unsupported currency: " + toCurrency);
        }

        return conversionRate;
    }

}

package com.pictet.technologies.resilience.resilience4j.exchangerate.api;

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
        private double eur;

        @JsonProperty("GBP")
        private BigDecimal gbp;

        @JsonProperty("JPY")
        private BigDecimal jpy;

        @JsonProperty("USD")
        private BigDecimal usd;
    }

}

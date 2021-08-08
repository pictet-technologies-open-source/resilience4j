package com.pictet.technologies.resilience.resilience4j.exchangerate.controller;

import com.pictet.technologies.resilience.resilience4j.exchangerate.api.CurrencyExchangeRates;
import com.pictet.technologies.resilience.resilience4j.exchangerate.feign.ExchangeRateClient;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;

@RestController
@RequestMapping(value = "/currencies")
@RequiredArgsConstructor
public class CurrencyController {

    private final ExchangeRateClient exchangeRateClient;

    @ApiOperation("Get the exchange rates for a given currency")
    @GetMapping("/{currency}/exchange-rates")
    public ResponseEntity<CurrencyExchangeRates> getExchangeRates(@PathVariable final String currency, @RequestParam BigDecimal amount) {
        return ResponseEntity.ok(exchangeRateClient.getLatest(currency, amount));
    }

}

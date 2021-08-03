package com.pictet.technologies.resilience.resilience4j.exchangerate.feign;

import com.pictet.technologies.resilience.resilience4j.exchangerate.api.CurrencyExchangeRates;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.math.BigDecimal;

@FeignClient(name = "exchange-rates", url = "https://api.exchangerate.host")
public interface ExchangeRateClient {

    @GetMapping(value = "/latest")
    CurrencyExchangeRates getLatest(@RequestParam("base") String currency, @RequestParam BigDecimal amount);

}

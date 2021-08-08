package com.pictet.technologies.resilience.resilience4j.store.provider.exchangerate;

import com.pictet.technologies.resilience.resilience4j.store.provider.exchangerate.api.CurrencyExchangeRates;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.math.BigDecimal;

@FeignClient(name = "exchange-rates-proxy", url = "http://localhost:8100")
public interface ExchangeRateClient {

    @GetMapping(value = "/currencies/{id}/exchange-rates")
    CurrencyExchangeRates getLatest(@PathVariable("id") String currency, @RequestParam BigDecimal amount);

}

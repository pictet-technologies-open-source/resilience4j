package com.pictet.technologies.resilience.resilience4j.store.service;

import com.pictet.technologies.resilience.resilience4j.store.domain.Item;
import com.pictet.technologies.resilience.resilience4j.store.provider.exchangerate.ExchangeRateClient;
import com.pictet.technologies.resilience.resilience4j.store.provider.exchangerate.api.CurrencyExchangeRates;
import com.pictet.technologies.resilience.resilience4j.store.repository.ItemRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ItemService {

    private final ExchangeRateClient exchangeRateClient;
    private final ItemRepository itemRepository;

    public List<Item> findAll(String currency) {

        final List<Item> items = itemRepository.findAll();

        if(currency != null && !items.isEmpty()) {

            final CurrencyExchangeRates latestRates = exchangeRateClient.getLatest(currency, BigDecimal.ONE);

            items.forEach(item -> {
                final BigDecimal conversionRate = latestRates.getConversionRate(item.getCurrency());
                item.setCurrency(currency);
                item.setPrice(item.getPrice().divide(conversionRate,2, RoundingMode.HALF_UP));
            });
        }

        return items;
    }

}

package com.pictet.technologies.resilience.resilience4j.store.service;

import com.pictet.technologies.resilience.resilience4j.store.domain.Item;
import com.pictet.technologies.resilience.resilience4j.store.provider.exchangerate.api.CurrencyExchangeRates;
import com.pictet.technologies.resilience.resilience4j.store.repository.ItemRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;
import java.util.concurrent.ExecutionException;

@Service
@RequiredArgsConstructor
@Slf4j
public class ItemService {

    private final ExchangeRateService exchangeRateService;
    private final ItemRepository itemRepository;

    public List<Item> findAll(String currency) {

        final List<Item> items = itemRepository.findAll();

        if (currency != null && !items.isEmpty()) {

            try {
                final CurrencyExchangeRates latestRates = exchangeRateService.getExchangeRates(currency).get();
                if (latestRates != null) {
                    items.forEach(item -> {
                        // Price conversion
                        final BigDecimal conversionRate = latestRates.getConversionRate(item.getCurrency());
                        item.setCurrency(currency);
                        item.setPrice(item.getPrice().divide(conversionRate, 2, RoundingMode.HALF_UP));
                    });
                }
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                log.error(e.getMessage(), e);
            } catch(ExecutionException e) {
                log.error(e.getMessage(), e);
            }
        }

        return items;
    }

}

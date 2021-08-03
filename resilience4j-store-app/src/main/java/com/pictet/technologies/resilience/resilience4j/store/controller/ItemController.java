package com.pictet.technologies.resilience.resilience4j.store.controller;


import com.pictet.technologies.resilience.resilience4j.store.api.ItemResource;
import com.pictet.technologies.resilience.resilience4j.store.provider.exchangerate.ExchangeRateClient;
import com.pictet.technologies.resilience.resilience4j.store.repository.ItemRepository;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping(value = "/currency")
@RequiredArgsConstructor
public class ItemController {

    private final ExchangeRateClient exchangeRateClient;
    private final ItemRepository itemRepository;


    @ApiOperation("Get the exchange rates for a given currency")
    @GetMapping("/items")
    public ResponseEntity<List<ItemResource>> getItems(@RequestParam String currency) {

        // TODO

        final List<ItemResource> items = itemRepository.findAll().stream().map(item ->
                new ItemResource().setShortName(item.getShortName())
                        .setDescription(item.getDescription())
                        .setPrice(item.getPriceEuro())
                        .setCurrency("EUR")).collect(Collectors.toList());

        return ResponseEntity.ok(items);
    }

}

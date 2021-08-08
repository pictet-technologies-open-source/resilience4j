package com.pictet.technologies.resilience.resilience4j.store.controller;


import com.pictet.technologies.resilience.resilience4j.store.api.ItemResource;
import com.pictet.technologies.resilience.resilience4j.store.mapper.ItemMapper;
import com.pictet.technologies.resilience.resilience4j.store.service.ItemService;
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
@RequestMapping(value = "/items")
@RequiredArgsConstructor
public class ItemController {

    private final ItemService itemService;
    private final ItemMapper itemMapper;

    @ApiOperation("Get the items")
    @GetMapping
    public ResponseEntity<List<ItemResource>> getItems(@RequestParam(required = false) String currency) {

        return ResponseEntity.ok(itemService.findAll(currency).stream()
                .map(itemMapper::toResource)
                .collect(Collectors.toList()));
    }

}

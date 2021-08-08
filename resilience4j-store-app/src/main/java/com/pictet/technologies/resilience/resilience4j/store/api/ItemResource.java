package com.pictet.technologies.resilience.resilience4j.store.api;

import lombok.Data;
import lombok.experimental.Accessors;

import java.math.BigDecimal;

@Data
@Accessors(chain = true)
public class ItemResource {

    private Long id;
    private String shortName;
    private String description;
    private BigDecimal price;
    private String currency;

}

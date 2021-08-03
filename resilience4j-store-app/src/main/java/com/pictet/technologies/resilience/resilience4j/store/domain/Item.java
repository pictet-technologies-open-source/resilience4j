package com.pictet.technologies.resilience.resilience4j.store.domain;

import lombok.Data;
import lombok.experimental.Accessors;

import java.math.BigDecimal;

@Data
@Accessors(chain = true)
public class Item {

    private String shortName;
    private String description;
    private BigDecimal priceEuro;

}

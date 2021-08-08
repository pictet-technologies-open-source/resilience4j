package com.pictet.technologies.resilience.resilience4j.store.mapper;

import com.pictet.technologies.resilience.resilience4j.store.api.ItemResource;
import com.pictet.technologies.resilience.resilience4j.store.domain.Item;
import org.springframework.stereotype.Component;

@Component
public class ItemMapper {

    public ItemResource toResource(Item item) {

        if(item == null) {
            return null;
        }

        return new ItemResource()
                .setId(item.getId())
                .setShortName(item.getShortName())
                .setDescription(item.getDescription())
                .setPrice(item.getPrice())
                .setCurrency(item.getCurrency());
    }

}

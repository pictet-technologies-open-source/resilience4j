package com.pictet.technologies.resilience.resilience4j.store.repository;

import com.pictet.technologies.resilience.resilience4j.store.domain.Item;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Repository
public class ItemRepository {

    private static final List<Item> items = new ArrayList<>();

    static {
        items.add(new Item()
                .setShortName("IPhone 12 Pro Max 128Gb")
                .setDescription("The iPhone 12 Pro and iPhone 12 Pro Max are smartphones designed and marketed by Apple Inc. " +
                        "They are the flagship smartphones in the fourteenth generation of the iPhone, succeeding the iPhone 11 " +
                        "Pro and iPhone 11 Pro Max, and were announced on October 13, 2020, with the iPhone 12 Pro being released " +
                        "on October 23, 2020, and the iPhone 12 Pro Max on November 13, 2020. The devices were unveiled alongside " +
                        "the iPhone 12 and iPhone 12" +
                        " Mini at an Apple Special Event at Apple Park in Cupertino, California on October 13, 2020.")
                .setPriceEuro(new BigDecimal("1400")));

        items.add(new Item()
                .setShortName("IPhone XR")
                .setDescription("The iPhone XR (stylized and marketed as iPhone Xʀ; Roman numeral X" +
                        " pronounced ten) is a smartphone designed and manufactured by Apple Inc." +
                        " It is the twelfth generation of the iPhone. " +
                        "Pre-orders began on October 19, 2018, with an official release on October 26, 2018.")
                .setPriceEuro(new BigDecimal("799.99")));

        items.add(new Item()
                .setShortName("Nokia 3310")
                .setDescription("The Nokia 3310 is a GSM mobile phone announced on 1 September 2000," +
                        " and released in the fourth quarter of the year, replacing the popular Nokia 3210. " +
                        "It sold very well, being one of the most successful phones with 126 million units sold worldwide," +
                        " and being one of Nokia's most iconic devices. The phone is still widely acclaimed" +
                        " and has gained a cult status due to its extreme durability.")
                .setPriceEuro(new BigDecimal("80.50")));

        items.add(new Item()
                .setShortName("Nokia 6")
                .setDescription("The Nokia 6 is a Nokia-branded mid-range smartphone running on Android. " +
                        "It is the first smartphone from the Finnish company HMD Global,[a] created through the partial " +
                        "divestment of Nokia's devices division; the first Nokia-branded smartphone since the Lumia 638;" +
                        " and the first Nokia-branded Android smartphone since the short-lived Nokia X2 in 2014." +
                        " The phone was first announced for sale in China on 8 January 2017, with a global version announced" +
                        " the following month.")
                .setPriceEuro(new BigDecimal("120")));

        items.add(new Item()
                .setShortName("Samsung Galaxy S21")
                .setDescription("T")
                .setPriceEuro(new BigDecimal("1999.99")));

        items.add(new Item()
                .setShortName("Samsung Galaxy Z Fold 2")
                .setDescription("The Samsung Galaxy S21 is a series of Android-based smartphones designed, " +
                        "developed, marketed, and manufactured by Samsung Electronics as part of its Galaxy S series." +
                        " They collectively serve as the successor to the Galaxy S20 series.")
                .setPriceEuro(new BigDecimal("1199.90")));

    }

    public List<Item> findAll() {
        return new ArrayList<>(items);
    }

}

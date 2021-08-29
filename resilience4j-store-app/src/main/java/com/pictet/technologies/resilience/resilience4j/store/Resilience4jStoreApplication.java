package com.pictet.technologies.resilience.resilience4j.store;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class Resilience4jStoreApplication {

    public static void main(String[] args) {

        SpringApplication.run(Resilience4jStoreApplication.class, args);
    }

}

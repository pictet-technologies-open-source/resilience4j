package com.pictet.technologies.resilience.resilience4j.exchangerate;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class Resilience4jExchangeRateApplication {

    public static void main(String[] args) {
        SpringApplication.run(Resilience4jExchangeRateApplication.class, args);
    }

}

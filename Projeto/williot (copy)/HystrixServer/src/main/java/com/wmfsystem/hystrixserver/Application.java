package com.wmfsystem.hystrixserver;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;

/**
 * Created by wmfsystem on 4/15/17.
 */
@SpringBootApplication
@EnableCircuitBreaker
public class Application {

    public static void main(String... args) {
        SpringApplication.run(Application.class, args);
    }
}


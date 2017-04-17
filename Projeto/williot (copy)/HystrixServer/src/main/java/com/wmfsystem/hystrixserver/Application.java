package com.wmfsystem.hystrixserver;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.netflix.hystrix.EnableHystrix;
import org.springframework.cloud.netflix.hystrix.dashboard.EnableHystrixDashboard;

/**
 * Created by wmfsystem on 4/15/17.
 */
@SpringBootApplication
@EnableCircuitBreaker
@EnableEurekaClient
public class Application {

    public static void main(String... args) {
        SpringApplication.run(Application.class, args);
    }
}


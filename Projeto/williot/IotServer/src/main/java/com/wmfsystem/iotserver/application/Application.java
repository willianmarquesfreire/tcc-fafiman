package com.wmfsystem.iotserver.application;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
 * Created by wmfsystem on 3/17/17.
 */

@SpringBootApplication
@EnableDiscoveryClient
public class Application {

    public static void main(String... args) {
        SpringApplication.run(Application.class, args);
    }

}

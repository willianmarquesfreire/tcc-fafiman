package com.wmfsystem.ribbonserver;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.cloud.netflix.ribbon.RibbonClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

/**
 * Created by wmfsystem on 4/19/17.
 */
@SpringBootApplication
@Configuration
@RestController
@RibbonClient(
        name = "IotServer",
        configuration = RibbonConfiguration.class)
public class Application {

    @LoadBalanced
    @Bean
    RestTemplate getRestTemplate() {
        return new RestTemplate();
    }

    @Autowired
    RestTemplate restTemplate;

    @RequestMapping("/server-location")
    public String serverLocation() {
        return this.restTemplate.getForObject(
                "http://localhost", String.class);
    }

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }
}

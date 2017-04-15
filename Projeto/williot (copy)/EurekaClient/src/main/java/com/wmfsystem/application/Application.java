package com.wmfsystem.application;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by wmfsystem on 3/17/17.
 */

@Configuration
@ComponentScan
@EnableAutoConfiguration
@EnableEurekaClient
@RestController
public class Application {

//    @RequestMapping("/")
//    public String home() {
//        return "Hello world";
//    }

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
//        new SpringApplicationBuilder(Application.class).web(true).run(args);
    }

}

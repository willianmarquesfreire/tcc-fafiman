package com.wmfsystem.turbineserver;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.turbine.EnableTurbine;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.StandardEnvironment;

/**
 * Created by wmfsystem on 4/15/17.
 */
@Configuration
@EnableAutoConfiguration
@EnableTurbine
@EnableDiscoveryClient
public class Application {
    public static void main(String[] args) {
        boolean cloudEnvironment = new StandardEnvironment().acceptsProfiles("cloud");
        new SpringApplicationBuilder(Application.class).web(!cloudEnvironment).run(args);
    }
}

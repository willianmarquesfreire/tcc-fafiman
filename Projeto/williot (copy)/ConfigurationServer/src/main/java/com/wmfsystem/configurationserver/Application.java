package com.wmfsystem.configurationserver;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.config.server.EnableConfigServer;

/**
 * Created by willian on 17/04/17.
 */
@SpringBootApplication
@EnableConfigServer
@EnableDiscoveryClient
public class Application {

    public static void main(String... args) {
        SpringApplication.run(Application.class, args);
    }
}

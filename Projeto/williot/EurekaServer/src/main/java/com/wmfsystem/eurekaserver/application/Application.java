package com.wmfsystem.eurekaserver.application;

import com.wmfsystem.eurekaserver.broadcast.Client;
import com.wmfsystem.eurekaserver.broadcast.Server;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by wmfsystem on 3/17/17.
 */

@SpringBootApplication
@EnableEurekaServer
@RestController
@RequestMapping("/")
public class Application {

    public static Server server = null;

    public static void main(String[] args) throws InterruptedException {
        Application.server = new Server(9054);

        SpringApplication.run(Application.class, args);
    }

    @RequestMapping(value = "send-server", method = RequestMethod.GET)
    public String sendServer() {
        Application.server.sendToAll("Ola server");
        return "Server send!";
    }


}

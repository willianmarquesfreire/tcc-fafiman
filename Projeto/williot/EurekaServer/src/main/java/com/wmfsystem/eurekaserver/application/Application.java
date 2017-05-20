package com.wmfsystem.eurekaserver.application;

import com.wmfsystem.eurekaserver.broadcast.Server;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;
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


    public static void main(String[] args) throws InterruptedException {

        Thread thread = new Thread(() -> {
            System.out.println("----->  thread");
            Server server = new Server();
            server.run();
        });

        thread.start();


        SpringApplication.run(Application.class, args);
    }

    @RequestMapping(value = "send-server", method = RequestMethod.GET)
    public String sendServer() {
        return "Server send!";
    }


}

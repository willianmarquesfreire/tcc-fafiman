package com.wmfsystem.feignclient.controllers;

import com.wmfsystem.feignclient.clients.TimeClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by wmfsystem on 3/17/17.
 */

@RestController
public class PeriodoController {

    @Autowired
    private TimeClient timeClient;

    @RequestMapping("/")
    public String getTime() {
        return "Manhã/Tarde/Noite " + timeClient.getTime();
    }

}

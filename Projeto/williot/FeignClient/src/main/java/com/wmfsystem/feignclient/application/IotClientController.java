package com.wmfsystem.feignclient.application;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by wmfsystem on 3/17/17.
 */

@RestController
@RequestMapping("/iotserver")
public class IotClientController {

    @Autowired
    private IotClient iotClient;

    @RequestMapping(method = RequestMethod.GET)
    public String getTime() {
        return "WmfSystem - " + iotClient.bemVindo();
    }

}

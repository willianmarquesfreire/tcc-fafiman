package com.wmfsystem.feignclient.application;

import com.fasterxml.jackson.databind.util.JSONPObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

/**
 * Created by wmfsystem on 3/17/17.
 */

@RestController
@RequestMapping("/muniserver")
public class MunifServerController {

    @Autowired
    private MuniServer muniServer;

    @RequestMapping(method = RequestMethod.GET)
    public String getMuniServer() {
        return muniServer.munifelse();
    }

}

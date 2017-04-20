package com.wmfsystem.feignclient.application;

import com.fasterxml.jackson.databind.util.JSONPObject;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.Map;

/**
 * Created by wmfsystem on 4/19/17.
 */
@FeignClient("muniserver")
public interface MuniServer {

    @RequestMapping(method = RequestMethod.GET, value = "/")
    String munifelse();
}

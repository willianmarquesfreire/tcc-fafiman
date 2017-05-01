package com.wmfsystem.feignclient.application;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.io.InputStream;
import java.io.Serializable;

/**
 * Created by wmfsystem on 3/17/17.
 */

@FeignClient("nodemcu")
public interface NodeMcu {

    @RequestMapping(method = RequestMethod.GET, value = "/info")
    ResponseEntity<Serializable> change(@RequestParam("pin") String pin, @RequestParam("stat") String stat);

}

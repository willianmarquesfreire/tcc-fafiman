package com.wmfsystem.feignclient.application;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by wmfsystem on 3/17/17.
 */

@FeignClient("IotServer")
public interface IotClient {

    @RequestMapping(method = RequestMethod.GET, value = "/")
    String bemVindo();

}

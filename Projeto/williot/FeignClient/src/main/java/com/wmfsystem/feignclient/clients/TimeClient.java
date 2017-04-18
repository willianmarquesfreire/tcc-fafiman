package com.wmfsystem.feignclient.clients;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * Created by wmfsystem on 3/17/17.
 */

@FeignClient("time-server")
public interface TimeClient {

    @RequestMapping(method = RequestMethod.GET, value = "/")
    String getTime();

}

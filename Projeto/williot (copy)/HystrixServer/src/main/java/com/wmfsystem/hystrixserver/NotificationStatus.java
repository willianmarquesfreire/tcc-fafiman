package com.wmfsystem.hystrixserver;

import com.netflix.appinfo.InstanceInfo;
import com.netflix.discovery.DiscoveryClient;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import org.springframework.stereotype.Component;

/**
 * Created by wmfsystem on 4/15/17.
 */
@Component
public class NotificationStatus {

    private DiscoveryClient discoveryClient;

    @HystrixCommand(fallbackMethod = "statusNotFound")
    public InstanceInfo.InstanceStatus notificationsStatus() {
        return discoveryClient
                .getNextServerFromEureka("EurekaClient", false)
                .getStatus();
    }

    public InstanceInfo.InstanceStatus statusNotFound() {
        return InstanceInfo.InstanceStatus.DOWN;
    }
}

package com.wmfsystem.hystrixserver.service;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCollapser;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;
import com.wmfsystem.hystrixserver.domain.Customer;
import com.wmfsystem.hystrixserver.domain.MessageWrapper;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Future;

/**
 * Created by wmfsystem on 4/15/17.
 */

@Service
public class CustomerCollapserService {

    // timerDelayInMilliseconds is set only for testing purposes
    @HystrixCollapser(scope = com.netflix.hystrix.HystrixCollapser.Scope.GLOBAL, batchMethod = "getCustomerByIds", collapserProperties = {
            @HystrixProperty(name = "timerDelayInMilliseconds", value = "2000")
    })
    public Future<MessageWrapper> getCustomerById(Integer id) {
        throw new RuntimeException("This method body should not be executed");
    }

    @HystrixCommand
    public List<MessageWrapper> getCustomerByIds(List<Integer> ids) {

        List<MessageWrapper> customers = new ArrayList<MessageWrapper>(ids.size());

        String message = "Batched calls with IDs " + ids.toString();

        for (Integer id : ids) {
            customers.add(new MessageWrapper<Object>(new Customer(id, "First", "Last"), message));
        }
        return customers;
    }

}
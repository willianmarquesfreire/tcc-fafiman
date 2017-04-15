package com.wmfsystem.hystrixserver.controller;

import com.wmfsystem.hystrixserver.domain.MessageWrapper;
import com.wmfsystem.hystrixserver.service.CustomerCollapserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.ExecutionException;

/**
 * Created by wmfsystem on 4/15/17.
 */
@RestController
class HystrixCollapserController {

    @Autowired
    private CustomerCollapserService customerCollapserService;

    @RequestMapping(value = "/customer-collapser/{id}", method = RequestMethod.GET, produces = "application/json")
    public MessageWrapper getCustomer(@PathVariable int id) throws ExecutionException, InterruptedException {
        return customerCollapserService.getCustomerById(id).get();
    }
}
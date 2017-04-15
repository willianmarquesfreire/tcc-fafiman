package com.wmfsystem.hystrixserver.controller;

import com.wmfsystem.hystrixserver.domain.Customer;
import com.wmfsystem.hystrixserver.service.CustomerCacheService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.concurrent.ExecutionException;

/**
 * Created by wmfsystem on 4/15/17.
 */
@RestController
class HystrixCacheController {

    @Autowired
    CustomerCacheService customerCacheService;

    @RequestMapping(value = "/customer-cache/{id}", method = RequestMethod.GET, produces = "application/json")
    public Customer getCustomer(@PathVariable int id, @RequestParam String name) throws ExecutionException, InterruptedException {
        return customerCacheService.createCustomer(id, name);
    }



}
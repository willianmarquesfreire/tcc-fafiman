package com.wmfsystem.hystrixserver.service;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.cache.annotation.CacheKey;
import com.netflix.hystrix.contrib.javanica.cache.annotation.CacheResult;
import com.wmfsystem.hystrixserver.domain.Customer;
import org.springframework.stereotype.Service;

/**
 * Created by wmfsystem on 4/15/17.
 */
@Service
public class CustomerCacheService {

    @CacheResult
    @HystrixCommand
    public Customer createCustomer(@CacheKey int id, String name) {
        return new Customer(id, name, name);
    }
}
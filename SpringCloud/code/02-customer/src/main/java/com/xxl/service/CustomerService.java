package com.xxl.service;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.cache.annotation.CacheKey;
import com.netflix.hystrix.contrib.javanica.cache.annotation.CacheRemove;
import com.netflix.hystrix.contrib.javanica.cache.annotation.CacheResult;
import com.xxl.client.SearchClient;
import com.xxl.entity.Customer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @Author: xuxueli
 * @Description:
 * @Date: 2020/12/6
 * @Version: 1.0
 */
@Service
public class CustomerService {

    @Autowired
    private SearchClient searchClient;

    @CacheResult
    @HystrixCommand(commandKey = "findById")
    public Customer findById(@CacheKey Integer id){
        return searchClient.findById(id);
    }

    @CacheRemove(commandKey = "findById")
    @HystrixCommand
    public void clearFindById(@CacheKey Integer id){
        System.out.println("findById被清空");
    }
}

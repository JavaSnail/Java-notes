package com.xxl.controller;

import com.xxl.entity.Customer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

/**
 * @Author: xuxueli
 * @Description:
 * @Date: 2020/12/3
 * @Version: 1.0
 */
@RestController
public class SearchController {

    @Value("${server.port}")
    private String port;

    @GetMapping("/search")
    public String search(){
//        int i= 1/0;
        return "search: " + port;
    }

    @GetMapping("/search/{id}")
    public Customer findById(@PathVariable Integer id){
        return new Customer(id,"张三",(int)(Math.random()*1000000));
    }

    @GetMapping("/getCustomer")
    public Customer getCustomer(@RequestParam Integer id,@RequestParam String name){
        return new Customer(id,name,23);
    }

    @PostMapping("/save")
    public Customer save(@RequestBody Customer customer){
        return customer;
    }
}

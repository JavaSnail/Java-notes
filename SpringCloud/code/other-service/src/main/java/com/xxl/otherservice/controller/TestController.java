package com.xxl.otherservice.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author: xuxueli
 * @Description:
 * @Date: 2020/12/7
 * @Version: 1.0
 */
@RestController
public class TestController {

    @GetMapping("/list")
    public String list(){
        return "other-service:list";
    }
}

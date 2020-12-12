package com.xxl.controller;

import com.xxl.stream.StreamClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author: xuxueli
 * @Description:
 * @Date: 2020/12/7
 * @Version: 1.0
 */
@RestController
public class MessageController {

    @Autowired
    private StreamClient streamClient;

    @GetMapping("/send")
    public String send(){
        streamClient.output().send(MessageBuilder.withPayload("Hello Stream!!!").build());
        return "消息发送成功！！！";
    }
}

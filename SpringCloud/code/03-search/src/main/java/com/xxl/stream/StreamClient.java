package com.xxl.stream;

import org.springframework.cloud.stream.annotation.Input;
import org.springframework.messaging.SubscribableChannel;

/**
 * @Author: xuxueli
 * @Description:
 * @Date: 2020/12/7
 * @Version: 1.0
 */
public interface StreamClient {

    @Input("myMessage")
    SubscribableChannel input();
}

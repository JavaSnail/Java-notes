package com.xxl.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @Author: xuxueli
 * @Description:
 * @Date: 2020/12/7
 * @Version: 1.0
 */
@FeignClient("OTHER-SERVICE")
public interface OtherServiceClient {

    @RequestMapping("/list")
    String list();
}

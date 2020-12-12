package com.xxl.servlet;

import com.netflix.hystrix.contrib.metrics.eventstream.HystrixMetricsStreamServlet;

import javax.servlet.annotation.WebServlet;

/**
 * @Author: xuxueli
 * @Description:
 * @Date: 2020/12/4
 * @Version: 1.0
 */
@WebServlet("/actuator/hystrix.stream")
public class HystrixServlet extends HystrixMetricsStreamServlet {

    static {
        System.out.println("HystrixServlet被初始化");
    }
}

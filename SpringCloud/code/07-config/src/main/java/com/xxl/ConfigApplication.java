package com.xxl;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.cloud.config.server.EnableConfigServer;

/**
 * @Author: xuxueli
 * @Description:
 * @Date: 2020/12/7
 * @Version: 1.0
 */
@SpringBootApplication
@EnableConfigServer
@ServletComponentScan("com.xxl.filter")
public class ConfigApplication {

    public static void main(String[] args) {
        SpringApplication.run(ConfigApplication.class,args);
    }
}

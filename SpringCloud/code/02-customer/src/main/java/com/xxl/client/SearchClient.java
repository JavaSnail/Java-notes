package com.xxl.client;

import com.xxl.entity.Customer;
import com.xxl.factory.SearchClientFallBackFactory;
import com.xxl.fallback.SearchClientFallBack;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

/**
 * @Author: xuxueli
 * @Description:
 * @Date: 2020/12/3
 * @Version: 1.0
 */

/**
 * FeignClient("SEARCH"):指定服务名称
 */
@FeignClient(value = "SEARCH",
        /*fallback = SearchClientFallBack.class*/
        fallbackFactory = SearchClientFallBackFactory.class)
public interface SearchClient {
    /**
     * value ->目标服务的请求路径，method -> 映射请求方式
     * @return
     */
    @RequestMapping(value = "/search",method = RequestMethod.GET)
    String search();

    @RequestMapping(value = "/search/{id}",method = RequestMethod.GET)
    Customer findById(@PathVariable(value = "id") Integer id);

    @RequestMapping(value = "/getCustomer",method = RequestMethod.GET)
    Customer getCustomer(@RequestParam(value = "id") Integer id, @RequestParam(value = "name") String name);

    @RequestMapping(value = "/save",method = RequestMethod.POST)
    Customer save(@RequestBody Customer customer);
}

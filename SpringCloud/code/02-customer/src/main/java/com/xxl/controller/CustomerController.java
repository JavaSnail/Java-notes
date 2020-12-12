package com.xxl.controller;

import com.netflix.discovery.EurekaClient;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;
import com.xxl.client.OtherServiceClient;
import com.xxl.client.SearchClient;
import com.xxl.entity.Customer;
import com.xxl.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

/**
 * @Author: xuxueli
 * @Description:
 * @Date: 2020/12/3
 * @Version: 1.0
 */
@RestController
@RefreshScope
public class CustomerController {

    @Value("${env}")
    private String env;

    @GetMapping("/env")
    public String env(){
        return env;
    }

    @Autowired
    private OtherServiceClient otherServiceClient;

    @GetMapping("/list")
    public String list(){
        return otherServiceClient.list();
    }

    @Value("${version}")
    private String version;

    @GetMapping("/version")
    public String currentVersion() throws InterruptedException {
        Thread.sleep(3000);
        return version;
    }

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private EurekaClient eurekaClient;

    @Autowired
    private SearchClient searchClient;

    @Autowired
    private CustomerService customerService;

    @GetMapping("/customer")
    public String customer(){
        System.out.println(Thread.currentThread().getName());
        /*//1.通过eurekaClient获取到SEARCH服务信息
        InstanceInfo info = eurekaClient.getNextServerFromEureka("SEARCH", false);
        //2.获取到访问地址
        String url = info.getHomePageUrl();
        System.out.println(url);
        //3.通过restTemplate访问
        String result = restTemplate.getForObject(url + "/search", String.class);*/

        /*String result = restTemplate.getForObject("http://SEARCH/search", String.class);*/

        String result = searchClient.search();
        //4.返回
        return result;
    }

    @GetMapping("/customer/{id}")
    @HystrixCommand(fallbackMethod = "findByIdFallBack",commandProperties = {
            @HystrixProperty(name="execution.isolation.strategy",value = "SEMAPHORE"),
            @HystrixProperty(name = "circuitBreaker.enabled",value = "true"),
            @HystrixProperty(name = "circuitBreaker.requestVolumeThreshold",value = "10"),
            @HystrixProperty(name = "circuitBreaker.errorThresholdPercentage",value = "70"),
            @HystrixProperty(name = "circuitBreaker.sleepWindowInMilliseconds",value = "5000")
    })
    public Customer findById(@PathVariable Integer id)throws InterruptedException{
//        System.out.println(Thread.currentThread().getName());
//        if (id ==1 ){
//            int  i = 1/0;
//        }
//        return searchClient.findById(id);
        System.out.println(customerService.findById(id));
        System.out.println(customerService.findById(id));
        customerService.clearFindById(id);
        System.out.println(customerService.findById(id));
        System.out.println(customerService.findById(id));
        return searchClient.findById(id);
    }

    /**
     * findById的降级方法，方法的描述要和接口一致
     * @param id
     * @return
     */
    public Customer findByIdFallBack(Integer id){
        return new Customer(-1,"",0);
    }

    @GetMapping("/getCustomer")
    public Customer getCustomer(@RequestParam Integer id, @RequestParam String name){
        return searchClient.getCustomer(id,name);
    }

    @PostMapping("/save")
    public Customer save(@RequestBody Customer customer){
        return customer;
    }
}

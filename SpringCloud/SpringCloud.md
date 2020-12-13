# Spring Cloud入门笔记

## 一、SpringCloud介绍

### 1.1 微服务架构

> 微服务的提出者：Martin Flower
>
> https://martinfowler.com/microservices/

> 简而言之，微服务架构样式是一种将单个应用程序开发为一**组小型服务的方法**，每个**小型服务**都**在自己的进程中运行**并与轻量级机制（通常是HTTP资源API）进行通信。这些服务**围绕业务功能构建，**并且可以 由全自动部署机制**独立部署**。有一个**集中管理的最低限度的**这些服务，可以用不同的编程语言和使用不同的数据存储技术。

微服务的解读：

- 微服务架构只是一个样式，一个风格
- 将一个完成的项目，拆分成多个模块去分别开发
- 每一个模块都是单独的运行在自己的容器中
- 每一个模块都是需要相互通讯的。HTTP、RPC、MQ。
- 每一个模块之间是没有依赖关系的，单独部署。
- 可以使用多种语言开发不同的模块。
- 使用MySQL数据库，Redis，ES去存储数据，也可以使用多个MySQL数据库。

总结：将复杂臃肿的单体应用进行细粒度的划分，每个拆分出来的服务各自打包部署。

### 1.2 SpringCloud介绍

> SpringCloud是微服务架构落地的一套技术栈
>
> SpringCloud中的大多数技术都是基于Netflix公司的技术进行二次研发。
>
> 1、SpringCloud的中文社区网站：[Spring Cloud中国社区](http://springcloud.cn/)
>
> 2、SpringCloud的中文网：[Spring Cloud中文网-官方文档中文版](https://www.springcloud.cc/)
>
> 八个技术点：
>
> 1. Eureka - 服务的注册中心
> 2. Ribbon - 服务之间的负载均衡
> 3. Feign - 服务之间的通讯
> 4. Hystrix - 服务的线程隔离以及断路由
> 5. Zuul - 服务网关
> 6. Stream - 实现MQ的使用
> 7. Config - 动态配置
> 8. Sleuth - 服务追踪

## 二、服务的注册与发现：Eureka

### 2.1	引言

> Eureka就是帮助我们维护所有服务的信息，以便服务之间的相互调用。

![image-20201202170415350](./assets/image-20201202170415350.png)

### 2.2	Euraka的快速入门

####	2.2.1	创建EurekaServer

> 1. 创建一个父工程，并且在父工程中指定SpringCloud的版本，并且将packing修改为pom

```xml
<packaging>pom</packaging>
<properties>
    <java.version>1.8</java.version>
    <spring.cloud-version>Hoxton.SR4</spring.cloud-version>
</properties>
<dependencyManagement>
    <dependencies>
        <dependency>
            <groupId>org.springframework.cloud</groupId>
            <artifactId>spring-cloud-dependencies</artifactId>
            <version>${spring.cloud-version}</version>
            <type>pom</type>
            <scope>import</scope>
        </dependency>
    </dependencies>
</dependencyManagement>
</dependencies>
```

> 2. 创建euraka的server,并且导入依赖，在启动类中添加注解，编写yml文件

**2.1	导入依赖**

```xml
<dependencies>
    <dependency>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-web</artifactId>
    </dependency>
    <dependency>
        <groupId>org.springframework.cloud</groupId>
        <artifactId>spring-cloud-starter-netflix-eureka-server</artifactId>
    </dependency>
</dependencies>
```

**2.2	启动类添加注解**

```java
@SpringBootApplication
@EnableEurekaServer
public class EurekaApplication {
    public static void main(String[] args) {
        SpringApplication.run(EurekaApplication.class,args);
    }
}
```

**2.3	编写yml文件**

```yml
server:
  port: 8761  # 端口号
eureka:
  instance:
    hostname: localhost #localhost
  client:
    # 当前的euraka服务是单机版
    registerWithEureka: false
    fetchRegistry: false
    serviceUrl:
      defaultZone: http://${eureka.instance.hostname}:${server.port}/eureka/
```

#### 2.2.2	 创建EurekaClient

> 1.创建Maven工程，修改为SpringBoot

> 2. 导入依赖

```xml
<dependency>
    <groupId>org.springframework.cloud</groupId>
    <artifactId>spring-cloud-starter-netflix-eureka-client</artifactId>
</dependency>
```

> 3. 在启动类上添加注解

```java
@EnableEurekaClient
@SpringBootApplication
public class CustomerApplication {
    public static void main(String[] args) {
        SpringApplication.run(CustomerApplication.class,args);
    }
}
```

> 4. 编写配置文件

```yml
# 指定Eureka服务地址
eureka:
  client:
    serviceUrl:
      defaultZone: http://localhost:8761/eureka/
#指定服务的名称
spring:
  application:
    name: CUSTOMER
```

#### 	2.2.3	测试Eureka

> 1. 创建了一个Search搜索模块，并且注册到Eureka

> 2. 使用EurekaClient的对象去获取服务信息

```java
@Autowired
private EurekaClient eurekaClient;
```

> 3. 正常RestTemplate调用即可

```java
@GetMapping("/customer")
public String customer(){
    //1.通过eurekaClient获取到SEARCH服务信息
    InstanceInfo info = eurekaClient.getNextServerFromEureka("SEARCH", false);
    //2.获取到访问地址
    String url = info.getHomePageUrl();
    System.out.println(url);
    //3.通过restTemplate访问
    String result = restTemplate.getForObject(url + "/search", String.class);
    //4.返回
    return result;
}
```

### 2.3	Eureka的安全性

**实现Eureka认证**

- 导入依赖

```xml
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-security</artifactId>
</dependency>
```

- 编写配置类

```java
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        // 忽略/eureka/**
        http.csrf().ignoringAntMatchers("/eureka/**");
        super.configure(http);
    }
}
```

- 编写配置文件

```yaml
spring:
  security:
    user:
      name: root
      password: root
```

- 其他服务想要注册到注册中心需要用户名密码

```yml
# 指定Eureka服务地址
eureka:
  client:
    serviceUrl:
      defaultZone: http://username:password@localhost:8761/eureka/
```

###  2.4	Eureka的高可用

> 如果程序正在运行，突然Eureka宕机了。
>
> 1. 如果调用方访问过一次被调用方，Eureka的宕机不会影响到功能。
> 2. 如果调用方没有访问过被调用方，Eureka的宕机就会造成当前功能不可用。

**搭建Eureka高可用**

1、准备多台Eureka

> 采用复制的方式，删除iml和target文件，并且修改pom.xml中的项目名称，再给父工程添加一个module

2、让服务注册到多台Eureka

```yml
eureka:
  client:
    serviceUrl:
      defaultZone: http://root:root@localhost:8761/eureka/,http://root:root@localhost:8762/eureka/
```

3、让多台Eureka之间相互通讯

```yml
eureka:
  instance:
    hostname: localhost #localhost
  client:
    registerWithEureka: true  #注册到Eureka上
    fetchRegistry: true #从Eureka上拉取信息
    serviceUrl:
      defaultZone: http://root:root@${eureka.instance.hostname}:8762/eureka/
```

### 2.5	Eureka的细节

1、EurekaClient启动时，将自己的信息注册到EurekaServer上，EurekaServer就会存储EurekaClient的注册信息

2、当EurekaClient调用服务时，本地没有注册信息的缓存时，取EurekaServer中获取注册信息。

3、EurekaClient会通过心跳的方式和EurekaServer进行连接。（默认30s、EurekaClient会发送一次心跳请求，如果超过了90s还没有收到心跳信息的话，EurekaServer就认为EurekaClient节点宕机了，将当前的EurekaClient从注册表中移除）。

```yml
eureka:
  instance:
    lease-renewal-interval-in-seconds: 30   #心跳间隔
    lease-expiration-duration-in-seconds: 90    #多久没有发送，就认为宕机
```

4、EurekaClient会每隔30s去EurekaServer中去更新本地注册表

```yml
eureka:
  client:
    registry-fetch-interval-seconds: 30	#每隔多久去更新一下本地的注册表缓存信息
```

5、Eureka的自我保护机制：统计15分钟内，如果一个服务的心跳发送比例低于85%，EurekaServer就会开启自我保护机制

- 不会从EurekaServer中去移除长时间没有收到心跳的服务。
- EurekaServer还是可以正常提供服务的。
- 网络比较稳定时，EurekaServer才会开始将自己的信息被其他节点同步过去。

```yml
eureka:
  server:
    enable-self-preservation: true  #开启自我保护机制
```

6、CAP定理：C：一致性，A：可用性，P：分区容错性，这三个特性在分布式环境下，只能同时满足两个，而且分区容错性在分布式环境下是必须要满足的，只能在AC之间进行权衡。

如果选择了CP，保证了一致性可能会造成系统在一定时间内是不可用的。如果同步数据的时间比较长，将会造成很大的损失。

Eureka就是一个AP的效果，高可用的集群，Eureka集群是无中心，Eureka即便宕机几个也不会影响系统的使用，不需要重新的去推举一个master，但是他会导致一定时间内数据是不一致的。

## 三、服务间的负载均衡：Ribbon

### 3.1	引言

> Ribbon是帮助我们实现服务和服务负载均衡，Ribbon属于客户端负载均衡
>
> - 客户端负载均衡：customer客户模块，将两个search模块信息全部拉取到本地的缓存，在customer中自己做一个负载均衡的策略，选中某一个服务。
> - 服务端负载均衡：在注册中心中，直接根据你指定的负载均衡策略，帮你选中一个指定的服务信息，并返回。

![image-20201203112857670](./assets/image-20201203112857670.png)

### 3.2	Ribbon的快速入门

1、启动两个search模块

2、在customer导入Ribbon依赖

```xml
<dependency>
    <groupId>org.springframework.cloud</groupId>
    <artifactId>spring-cloud-starter-netflix-ribbon</artifactId>
</dependency>
```

3、配置整合RestTemplate和Robbin

```java
@Bean
@LoadBalanced
public RestTemplate restTemplate(){
    return new RestTemplate();
}
```

4、在customer中访问search

```java
@GetMapping("/customer")
public String customer(){
    String result = restTemplate.getForObject("http://SEARCH/search", String.class);
    return result;
}
```

### 3.3	Ribbon配置负载均衡策略

1、负载均衡策略

- RandomRule：随机策略
- RoundRobinRule：轮询策略
- WeightedResponseTimeRule：默认会采用轮询的策略，后续会根据服务的响应时间，自动分配权重
- BestAvailableRule：根据被调用方并发数最小的去分配

2、采用注解的形式

```java
@Bean
public IRule robbinRule(){
    return new RandomRule();
}

```

3、配置文件指定负载均衡策略（推荐）

```yml
#指定具体服务的负载均衡策略
SEARCH: #编写服务名称
  ribbon:
    NFLoadBalancerRuleClassName: com.netflix.loadbalancer.WeightedResponseTimeRule
```

## 四、服务间的调用：Feign

### 4.1	引言

> Feign可以帮助我们实现面向接口编程，直接调用其他服务，简化开发。

### 4.2	Feign的快速入门

·1、导入依赖

```xml
<dependency>
    <groupId>org.springframework.cloud</groupId>
    <artifactId>spring-cloud-starter-openfeign</artifactId>
</dependency>
```

2、启动类添加注解

```java
@EnableFeignClients
```

3、创建一个接口，并且和search模块

```java
/**
 * FeignClient("SEARCH"):指定服务名称
 */
@FeignClient("SEARCH")      
public interface SearchClient {
    /**
     * value ->目标服务的请求路径，method -> 映射请求方式
     * @return
     */
    @RequestMapping(value = "/search",method = RequestMethod.GET)
    String search();
}
```

4、测试使用

```java
@Autowired
private SearchClient searchClient;
@GetMapping("/customer")
public String customer(){
    String result = searchClient.search();
    return result;
}
```

### 4.3	Feign的参数传递 方式

1、注意事项

- 如果传递的参数比较复杂时，默认采用POST的请求方式。
- 传递单个参数时，推荐使用@PathVariable，如果传递的单个参数比较多，这里可以采用@RequestParam，不要省略value属性。
- 传递对象信息时，统一采用json方式，添加@RequestBody。
- Client接口必须采用@RequestMapping

2、在search模块下准备三个接口

```java
@GetMapping("/search/{id}")
public Customer findById(@PathVariable Integer id){
    return new Customer(id,"张三",23);
}

@GetMapping("/getCustomer")
public Customer getCustomer(@RequestParam Integer id,@RequestParam String name){
    return new Customer(id,name,23);
}

@PostMapping("/save")
public Customer save(@RequestBody Customer customer){
    return customer;
}
```

3、封装customer模块下的controller

```java
@GetMapping("/customer/{id}")
public Customer findById(@PathVariable Integer id){
    return searchClient.findById(id);
}

@GetMapping("/getCustomer")
public Customer getCustomer(@RequestParam Integer id, @RequestParam String name){
    return searchClient.getCustomer(id,name);
}

@PostMapping("/save")
public Customer save(@RequestBody Customer customer){
    return customer;
}
```

4、再封装client接口

```java
@RequestMapping(value = "/search/{id}",method = RequestMethod.GET)
Customer findById(@PathVariable(value = "id") Integer id);

@RequestMapping(value = "/getCustomer",method = RequestMethod.GET)
Customer getCustomer(@RequestParam(value = "id") Integer id, @RequestParam(value = "name") String name);

@RequestMapping(value = "/save",method = RequestMethod.POST)
Customer save(@RequestBody Customer customer);
```

### 4.4	Feign的Fallback

> Fallback可以帮助我们在使用Feign去调用另外一个服务时，如果出现了问题，走服务降级，返回一个错误数据，避免功能因为一个服务出现问题，全部失效。

1、创建一个POJO类，实现Client接口。

```java
@Component
public class SearchClientFallBack implements SearchClient {
    @Override
    public String search() {
        return "出现错误了";
    }

    @Override
    public Customer findById(Integer id) {
        return null;
    }

    @Override
    public Customer getCustomer(Integer id, String name) {
        return null;
    }

    @Override
    public Customer save(Customer customer) {
        return null;
    }
}
```

2、修改Client接口中的注解，添加一个属性

```java
@FeignClient(value = "SEARCH",fallback = SearchClientFallBack.class)
```

3、添加配置文件

```yml
#feign和hystrix组件整合
feign:
  hystrix:
    enabled: true
```

> 以上方法存在的问题：调用方无法知道具体的错误信息是什么，通过FallBackFactory的方式去实现这个功能。

1、FallBackFactory基于Fallback

2、创建一个POJO类，实现FallBackFactory`<Client>`

```java
@Component
public class SearchClientFallBackFactory implements FallbackFactory<SearchClient> {

    @Autowired
    private SearchClientFallBack searchClientFallBack;

    @Override
    public SearchClient create(Throwable throwable) {
        throwable.printStackTrace();
        return searchClientFallBack;
    }
}
```

3、修改Client接口属性

```java
@FeignClient(value = "SEARCH",fallbackFactory = SearchClientFallBackFactory.class)
```

## 五、服务的隔离及熔断：Hystrix

### 5.1	引言

![image-20201204142948147](./assets/image-20201204142948147.png)

### 5.2	降级机制的实现

1、导入依赖

```xml
<dependency>
    <groupId>org.springframework.cloud</groupId>
    <artifactId>spring-cloud-starter-netflix-hystrix</artifactId>
</dependency>
```

2、添加一个注解

```java
@EnableCircuitBreaker
```

3、针对某一个接口去编写降级方法

```java
@GetMapping("/customer/{id}")
@HystrixCommand(fallbackMethod = "findByIdFallBack")
public Customer findById(@PathVariable Integer id){
    int i = 1/0;
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
```

4、在接口上添加注解

```java
@HystrixCommand(fallbackMethod = "findByIdFallBack")
```

### 5.3	线程隔离

> 如果使用tomcat的线程池去接收用户的请求，使用当前线程去执行其他服务的功能，如果某一个服务出现了故障，导致tomcat的线程大量的堆积，导致tomcat服务无法处理其他业务功能。
>
> - Hystrix的线程池==（默认）==，接收用户请求采用tomcat的线程池，执行业务代码，调用其他服务时，采用Hystrix的线程池。
> - 信号量，使用的还是tomcat的线程池，帮助我们去管理tomcat的线程池。

1、Hystrix的线程池的配置(具体的配置name属性需要查看HystrixCommandProperties类)

- 线程隔离策略：name=`hystrix.command.default.execution.isolation.strategy`,value=`THREAD`
- 指定超时时间（针对线程池）：name=`hystrix.command.default.execution.isolation.thread.timeoutInMilliseconds`,value=`1000`
- 是否开启超时时间配置：name=`hystrix.command.default.execution.timeout.enabled`,value=`true`
- 超时之后是否中断线程：name=`hystrix.command.default.execution.isolation.thread.interruptOnTimeout`,value=`true`
- 取消任务后是否中断线程：name=`hystrix.command.default.execution.isolation.thread.interruptOnCancel`,value=`false`

```java
@GetMapping("/customer/{id}")
@HystrixCommand(fallbackMethod = "findByIdFallBack",commandProperties = {
    @HystrixProperty(name="execution.isolation.strategy",value = "THREAD"),
    @HystrixProperty(name = "execution.isolation.thread.timeoutInMilliseconds",value = "3000")
})
public Customer findById(@PathVariable Integer id)throws InterruptedException{
    System.out.println(Thread.currentThread().getName());
    Thread.sleep(3000);
    return searchClient.findById(id);
}
```

2、信号量的配置信息

- 线程隔离策略：name=`hystrix.command.default.execution.isolation.strategy`,value=`SEMAPHORE`

- 指定信号量的最大并发请求数：name=`hystrix.command.default.execution.isolation.semaphore.maxConcurrentRequests`,value=`10`

```java
@GetMapping("/customer/{id}")
@HystrixCommand(fallbackMethod = "findByIdFallBack",commandProperties = {
    @HystrixProperty(name="execution.isolation.strategy",value = "SEMAPHORE"),
})
public Customer findById(@PathVariable Integer id)throws InterruptedException{
    System.out.println(Thread.currentThread().getName());
    Thread.sleep(3000);
    return searchClient.findById(id);
}
```

### 5.4	断路器

#### 5.4.1	断路器介绍

> 在调用指定服务时，如果说这个服务的失败率达到输入的一个阈值，将断路器从closed状态，转变为open状态，指定服务是无法被访问的，如果访问就直接走fallback方法，在一定时间内，open状态会再次转变为half      open状态，允许一个请求发送到我指定的服务，如果成功，转变为closed，如果失败，服务再次转变为open状态，会再次循环到half open，直到断路器回到一个closed状态。

![image-20201204162617695](./assets/image-20201204162617695.png)

#### 5.4.2	配置断路器的监控界面

![image-20201205233318794](./assets/image-20201205233318794.png)

1、导入依赖

```xml
<dependency>
    <groupId>org.springframework.cloud</groupId>
    <artifactId>spring-cloud-starter-netflix-hystrix-dashboard</artifactId>
</dependency>
```

2、启动类中添加注解

```java
@EnableHystrixDashboard
```

3、配置一个servlet路径，指定上Hystrix的Servlet

```java
@WebServlet("/hystrix.stream")
public class HystrixServlet extends HystrixMetricsStreamServlet {

}
--------------------------------------------------------
//在启动类上添加扫描servlet的注解
```

#### 5.4.3	配置断路器的属性

![image-20201205235243369](./assets/image-20201205235243369.png)

> [详见官方文档](https://github.com/Netflix/Hystrix/wiki/Configuration#circuitBreaker.enabled)

断路器的属性(10秒钟之内)

1. 断路器开关：name=`hystrix.command.default.circuitBreaker.enabled`,value=`true`
2. 失败阈值的总请求数：name=`hystrix.command.default.circuitBreaker.requestVolumeThreshold`,value=`20`
3. 请求总数失败率达到`%`之多少时开启断路器：name=`hystrix.command.default.circuitBreaker.errorThresholdPercentage`,value=`50`
4. 断路器open状态后，多少秒是拒绝请求的：name=`hystrix.command.default.circuitBreaker.sleepWindowInMilliseconds`,value=`5000`
5. 强制让服务拒绝请求：name=`hystrix.command.default.circuitBreaker.forceOpen`,value=`false`
6. 强制让服务接收请求：name=`hystrix.command.default.circuitBreaker.forceClosed`,value=`false`

```java
@HystrixCommand(fallbackMethod = "findByIdFallBack",commandProperties = {
    @HystrixProperty(name = "circuitBreaker.enabled",value = "true"),
    @HystrixProperty(name = "circuitBreaker.requestVolumeThreshold",value = "10"),
    @HystrixProperty(name = "circuitBreaker.errorThresholdPercentage",value = "70"),
    @HystrixProperty(name = "circuitBreaker.sleepWindowInMilliseconds",value = "5000")
})
```

### 5.5	请求缓存

#### 5.5.1	请求缓存的实现

1、请求缓存的生命周期是一次请求

2、请求缓存时缓存当前线程中的一个方法，将方法的参数作为key，方法的返回结果作为value

3、在一次请求中，目标方法被调用过一次，以后就都会被缓存。

![image-20201206000650027](./assets/image-20201206000650027.png)

#### 5.5.2	请求缓存的实现

1、创建一个service，在servie中调用search服务

```java
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
```

2、使用请求缓存的注解

```java
//帮助我们缓存当前方法的返回结果(必须配合HystrixCommand使用)
@CacheResult
//帮助我们清除某一个缓存信息(基于commandKey)
@CacheRemove
//指定方法的哪个参数作为缓存的标识，默认情况下是所有的参数
@CacheKey
```

 3、修改search模块的返回结果

```java
return new Customer(id,"张三",(int)(Math.random()*1000000));
```

4、编写Filter，去构建HystrixRequestContext

```java
@WebFilter("/*")
public class HystrixRequestContextInitFilter implements Filter {
    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HystrixRequestContext.initializeContext();
        filterChain.doFilter(servletRequest,servletResponse);
    }
}
```

5、修改control

```java
public Customer findById(@PathVariable Integer id){
    System.out.println(customerService.findById(id));
    System.out.println(customerService.findById(id));
    customerService.clearFindById(id);
    System.out.println(customerService.findById(id));
    System.out.println(customerService.findById(id));
    return searchClient.findById(id);
}
```

6、测试结果

![image-20201206005450764](./assets/image-20201206005450764.png)

## 六、服务的网关：Zuul

### 6.1	引言

> - 客户端维护大量的ip和port信息，直接访问指定服务。
> - 认证和授权操作，需要在每一个模块中都添加认证和授权的操作。
> - 项目的迭代，服务要拆分，服务要合并，需要客户端进行大量的变化。
> - 统一的把安全性校验都放在Zuul中。

![image-20201206143120641](./assets/image-20201206143120641.png)

### 6.2	Zuul的快速入门

1、创建maven项目，修改为springboot

2、导入依赖

```xml
<dependency>
    <groupId>org.springframework.cloud</groupId>
    <artifactId>spring-cloud-starter-netflix-zuul</artifactId>
</dependency>
<dependency>
    <groupId>org.springframework.cloud</groupId>
    <artifactId>spring-cloud-starter-netflix-eureka-client</artifactId>
</dependency>
```

3、添加一个注解

```java
@EnableEurekaClient
@EnableZuulProxy
```

4、编写配置文件

```yml
# 指定Eureka服务地址
eureka:
  client:
    serviceUrl:
      defaultZone: http://root:root@localhost:8761/eureka/,http://root:root@localhost:8762/eureka/
#指定服务的名称
spring:
  application:
    name: ZUUL
server:
  port: 80
```

### 6.3	Zuul的常用配置信息

#### 6.3.1	Zuul的监控界面

1、导入依赖

```xml
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-actuator</artifactId>
</dependency>
```

2、编写配置文件

```yml
#查看zuul的监控界面（开发时，配置为*，上线，不要配置）
management:
  endpoints:
    web:
      exposure:
        include: "*"
```

3、直接访问

![image-20201206145429176](./assets/image-20201206145429176.png)

#### 6.3.2	忽略服务配置

```yml
#zuul配置
zuul:
  #基于服务名忽略服务，无法查看，如果要忽略全部的服务，"*"，默认配置的全部路径都会被忽略掉（自定义
  ignored-services: eureka
  #监控界面依然可以查看，在访问的时候，404
  ignored-patterns: /**/search/**
```

#### 6.3.3	自定义服务配置

```yml
#指定自定义服务(方式一，key（服务名）：value（路径））
routes:
  search: /ss/**
  customer: /cc/**
 #指定自定义服务(方式二）
routes:
  kehu: #自定义名称
    path: /ccc/** #映射的路径
    serviceId: customer #服务名称
```

#### 6.3.4	灰度发布

1、添加一个配置类

```java
@Bean
/**
 * 规定：
 *      服务的命名规则：服务名-v+版本号
 *      访问路径：/v+版本号/路径
 */
public PatternServiceRouteMapper serviceRouteMapper() {
    return new PatternServiceRouteMapper(
            "(?<name>^.+)-(?<version>v.+$)",
            "${version}/${name}");
}
```

2、准备一个服务，提供两个版本

```yml
version : v1
#指定服务的名称
spring:
  application:
    name: CUSTOMER-${version}
```

3、修改Zuul的配置

```yml
#zuul配置
zuul:
  #基于服务名忽略服务，无法查看,如果需要用的`-v`的方式，一定要忽略掉。
  #ignored-services: "*"
```

4、测试

![image-20201206154702628](./assets/image-20201206154702628.png)

### 6.4	Zuul的过滤器执行流程

> 客户端请求发送到zuul服务上，首先通过PreFilter链，如果正常放行，会把请求再次转发给RoutingFilter，请求转发到一个指定的服务，在指定的服务响应一个结果后，再次走一个PostFilter的过滤器链，最终再将响应信息交给客户端。

![image-20201206155136998](./assets/image-20201206155136998.png)

### 6.5	Zuul过滤器入门

1、创建一个POJO类，继承ZuulFilter

```java
@Component
public class TestZuulFilter2 extends ZuulFilter {}
```

2、指定当前过滤器类型

```java
@Override
public String filterType() {
    return FilterConstants.PRE_TYPE;
}
```

3、指定过滤器的执行顺序

```java
@Override
public int filterOrder() {
    return FilterConstants.PRE_DECORATION_FILTER_ORDER -1;
}
```

4、配置是否启用过滤器

```java
@Override
public boolean shouldFilter() {
    //开启当前过滤器
    return true;
}
```

5、指定过滤器中的具体业务代码

```java
@Override
public Object run() throws ZuulException {
    System.out.println("prifix过滤器执行~");
    return null;
}
```

6、测试

![image-20201206160924248](./assets/image-20201206160924248.png)

### 6.6	PreFilter实现token检验

1、准备访问路径，请求参数传递token

http://localhost/v2/customer/version?token=123

2、创建AuthenticationFilter

```java
@Component
public class AuthenticationFilter extends ZuulFilter {
    @Override
    public String filterType() {
        return FilterConstants.PRE_TYPE;
    }

    @Override
    public int filterOrder() {
        return FilterConstants.PRE_DECORATION_FILTER_ORDER - 2;
    }

    @Override
    public boolean shouldFilter() {
        return true;
    }
}
```

3、在run方法中编写具体的业务逻辑代码

```java
@Override
    public Object run() throws ZuulException {
        //1.获取Request对象
        RequestContext requestContext = RequestContext.getCurrentContext();
        HttpServletRequest request = requestContext.getRequest();
        //2.获取token参数
        String token = request.getParameter("token");
        //3.对比token
        if (token ==null || !"123".equalsIgnoreCase(token)){
            //4. token检验失败，直接响应数据
            requestContext.setSendZuulResponse(false);
            requestContext.setResponseStatusCode(HttpStatus.SC_UNAUTHORIZED);
        }
        return null;
    }
```

4、测试

![image-20201206162522700](./assets/image-20201206162522700.png)

### 6.7	Zuul降级

1、创建POJO类，实现接口FallbakProvider

```java
@Component
public class ZuulFallBack implements FallbackProvider {}
```

2、重写两个方法

```java
@Override
public String getRoute() {
    //代表出现问题的服务，都走这个降级方法
    return "*";
}

@Override
public ClientHttpResponse fallbackResponse(String route, Throwable cause) {
    System.out.println("降级的服务：" + route);
    cause.printStackTrace();
    return new ClientHttpResponse() {
        @Override
        public HttpStatus getStatusCode() throws IOException {
            //指定具体的HttpStatus
            return HttpStatus.INTERNAL_SERVER_ERROR;
        }

        @Override
        public int getRawStatusCode() throws IOException {
            //返回状态码
            return HttpStatus.INTERNAL_SERVER_ERROR.value();
        }

        @Override
        public String getStatusText() throws IOException {
            //指定错误信息
            return HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase();
        }

        @Override
        public void close() {

        }

        @Override
        public InputStream getBody() throws IOException {
            //给用户响应的信息
            String msg = "当前服务：" + route + "出现问题！！！";
            return new ByteArrayInputStream(msg.getBytes());
        }

        @Override
        public HttpHeaders getHeaders() {
            //指定响应头信息
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            return headers;
        }
    };
}
```

### 6.8	Zuul动态路由

1、创建一个过滤器

> 执行顺序最好放在Pre过滤器的最后面

2、在run方法中编写业务逻辑

```java
@Override
public Object run() throws ZuulException {
    //1.获取Request对象
    RequestContext context = RequestContext.getCurrentContext();
    HttpServletRequest request = context.getRequest();
    //2.获取参数，redisKey
    String redisKey = request.getParameter("redisKey");
    //3.直接判断
    if (redisKey !=null && redisKey.equalsIgnoreCase("customer")){
        //http://localhost:8080/customer
        context.put(FilterConstants.SERVICE_ID_KEY,"customer-v1");
        context.put(FilterConstants.REQUEST_URI_KEY,"/customer");
    }else if (redisKey !=null && redisKey.equalsIgnoreCase("search")){
        //http://localhost:9090/search/1
        context.put(FilterConstants.SERVICE_ID_KEY,"search");
        context.put(FilterConstants.REQUEST_URI_KEY,"/search/1");
    }
    return null;
}
```

3、测试

![image-20201206174139484](./assets/image-20201206174139484.png)

## 七、多语言支持 Sidecar

### 7.1	引言

> 在SpringCloud的项目中，需要接入一些非Java的程序，第三方接口，无法接入Eureka，Hystrix，Feign等等组件，启动一个代理的微服务，代理微服务去和非Java的程序或第三方接口交互，通过代理微服务去接入SpringCloud的相关组件。

![image-20201207104917081](./assets/image-20201207104917081.png)

### 7.2	Sidecar实现

1、创建一个第三方的服务

> 创建一个SpringBoot工程，并且添加一个Controller

2、创建maven工程，修改spring Boot

3、导入依赖

```xml
<dependency>
    <groupId>org.springframework.cloud</groupId>
    <artifactId>spring-cloud-netflix-sidecar</artifactId>
</dependency>
<dependency>
    <groupId>org.springframework.cloud</groupId>
    <artifactId>spring-cloud-starter-netflix-eureka-client</artifactId>
</dependency>
```

4、添加注解

```java
@EnableSidecar
```

5、编写配置文件

```yml
server:
  port: 81
# 指定Eureka服务
eureka:
  client:
    service-url:
      defaultZone: http://root:root@localhost:8761/eureka,http://root:root@localhost:8762/eureka
#指定服务名称
spring:
  application:
    name: other-service
#指定代理的第三方服务
sidecar:
  port: 7001
```

6、通过customer调用第三方服务

![image-20201207113257702](./assets/image-20201207113257702.png)

## 八、服务间消息传递：Stream

### 8.1	引言

> 用于构建消息驱动微服务的框架(在下面方便起见也叫它Stream框架)，该框架在Spring Boot的基础上整合了Spring Integration来连接消息代理中间件（RabbitMQ，Kafka等）。它支持多个消息中间件的自定义配置，同时吸收了这些消息中间件的部分概念，例如持久化订阅、消费者分组，和分区等概念。使用Stream框架，我们不必关心如何连接各个消息代理中间件，也不必关心消息的发送与接收，只需要进行简单的配置就可以实现这些功能了，可以让我们更敏捷的进行开发主体业务逻辑了。
>
> **Spring Cloud Stream框架的组成部分：**
>
> 1. Stream框架自己的应用模型；
> 2. 绑定器抽象层，可以与消息代理中间件进行绑定，通过绑定器的API，可实现插件式的绑定器。
> 3. 持久化订阅的支持。
> 4. 消费者组的支持。
> 5. Topic分区的支持。

![image-20201207114808902](./assets/image-20201207114808902.png)

### 8.2	Stream快速入门

1、启动RabbitMQ

2、消费者-导入依赖

```xml
<dependency>
    <groupId>org.springframework.cloud</groupId>
    <artifactId>spring-cloud-starter-stream-rabbit</artifactId>
</dependency>
```

3、消费者-配置文件

```yml
spring:
  # 连接RabbitMQ
  rabbitmq:
    host: 192.168.25.150
    port: 5672
    username: test
    password: test
    virtual-host: /
```

4、消费者-监听的队列

```java
public interface StreamClient {

    @Input("myMessage")
    SubscribableChannel input();
}
```

```java
@Component
@EnableBinding(StreamClient.class)
public class StreamReceiver {

    @StreamListener("myMessage")
    public void msg(Object msg){
        System.out.println("接收到消息：" + msg);
    }

}
```

5、生产者-导入依赖

```xml
<dependency>
    <groupId>org.springframework.cloud</groupId>
    <artifactId>spring-cloud-starter-stream-rabbit</artifactId>
</dependency>
```

6、生产者-配置文件

```yml
spring:
  # 连接RabbitMQ
  rabbitmq:
    host: 192.168.25.150
    port: 5672
    username: test
    password: test
    virtual-host: /
```

7、生产者-发布消息

> 需要在启动类中添加注解`@EnableBinding(StreamClient.class)`

```java
public interface StreamClient {

    @Output("myMessage")
    MessageChannel output();
}
```

```java
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
```

### 8.3	Stream重复消费

```yml
spring:
  cloud:
    stream:
      bindings:
        myMessage:				#队列名称
          group: customer		 #消费者组
```

### 8.4	Stream的消费者手动ack

1、编写配置文件

```yml
spring:
  cloud:
  	stream:
      #手动实现ACK
      rabbit:
        bindings:
          myMessage:
            consumer:
              acknowledgeMode: MANUAL
```

2、修改消费端的方法

```java
@StreamListener("myMessage")
public void msg(Object msg,
                @Header(name = AmqpHeaders.CHANNEL) Channel channel,
                @Header(name = AmqpHeaders.DELIVERY_TAG) Long deliveryTag) throws IOException {
    System.out.println("接收到消息：" + msg);
    channel.basicAck(deliveryTag,false);
}
```

## 九、服务的动态配置：Config

### 9.1	引言

> - 配置文件分散在不通的项目中，不方便维护
> - 配置文件的安全问题
> - 修改完配置文件，无法立即生效

![image-20201207145400833](./assets/image-20201207145400833.png)

### 9.2	搭建Config-Server

1、创建maven工程，修改为spring boot

2、导入依赖

```xml
<dependency>
    <groupId>org.springframework.cloud</groupId>
    <artifactId>spring-cloud-config-server</artifactId>
</dependency>
<dependency>
    <groupId>org.springframework.cloud</groupId>
    <artifactId>spring-cloud-starter-netflix-eureka-client</artifactId>
</dependency>
```

3、添加注解

```java
@EnableConfigServer
```

4、编写配置文件(Git操作)

```yml
#指定服务的名称
spring:
  cloud:
    config:
      server:
        git:
          basedir: E:\Gitee\config                         #本地仓库
          username: xuxueli982@163.com                     #远程仓库的用户名
          password: Xxl@6553345726                         #远程仓库的密码
          uri: https://gitee.com/sabiler/config-repo.git   #远程仓库地址
```

5、测试：

> `http://localhost:port/{label}/{application}-{profile}.yml`

![image-20201207170900983](./assets/image-20201207170900983.png)

==问题：==配置好config-server服务后无法通过连接地址访问到远程git仓库中的配置文件

### 9.3	修改customer连接Config

1、导入依赖

```xml
<dependency>
    <groupId>org.springframework.cloud</groupId>
    <artifactId>spring-cloud-config-client</artifactId>
</dependency>
```

2、编写配置文件

```yml
# 指定Eureka服务地址
eureka:
  client:
    serviceUrl:
      defaultZone: http://root:root@localhost:8761/eureka/,http://root:root@localhost:8762/eureka/


#指定服务的名称
spring:
  application:
    name: CUSTOMER-${version}
  cloud:
    config:
      discovery:
        enabled: true
        service-id: CONFIG
      profile: dev
      label: master
version : v1
```

3、修改配置名称

> bootstrap.yml

4、测试发布消息到RabbitMQ



**PS：注意配置文件的文件名要和应用的名称相同并且区分大小写。**

### **9.4	实现动态配置**

#### 9.4.1	实现原理

![image-20201207182600904](./assets/image-20201207182600904.png)

#### 9.4.2	服务连接RabbitMQ

1、导入依赖

```xml
<dependency>
    <groupId>org.springframework.cloud</groupId>
    <artifactId>spring-cloud-starter-bus-amqp</artifactId>
</dependency>
```

2、编写配置文件连接RabbitMQ

```yml
#指定服务的名称
spring:
  rabbitmq:
    host: 192.168.25.150
    port: 5672
    username: test
    password: test
    virtual-host: /
```

#### 9.4.3	实现手动刷新

1、导入依赖

```xml
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-actuator</artifactId>
</dependency>
```

2、编写配置文件

```yml
management:
  endpoints:
    web:
      exposure:
        include: "*"
```

3、为customer添加一个controller

```java
@RefreshScope
public class CustomerController {

    @Value("${env}")
    private String env;

    @GetMapping("/env")
    public String env(){
        return env;
    }
}
```

4、测试

> 1. CONFIG在Gitee修改之后，自动拉取最新的配置信息
> 2. 其他模块需要更新的话，需要手动给CONFIG应用发送一个请求：`http://localhost:8090/actuator/bus-refresh`，不重启项目，即可获取最新的配置信息

#### 9.4.4	实现自动刷新

1、配置Gitee中的WebHooks

![image-20201207192516664](./assets/image-20201207192516664.png)

添加完webhooks之后gitee会尝试发送一个请求给config应用，这时由于没有配置过滤器会报`400`错误码。

2、给Config添加一个过滤器

```java
@WebFilter("/*")
public class UrlFilter implements Filter {
    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest httpServletRequest= (HttpServletRequest) servletRequest;
        String url=httpServletRequest.getRequestURI();
        System.out.println(url);
        if(!url.endsWith("/actuator/bus-refresh")){
            filterChain.doFilter(servletRequest,servletResponse);
            return;
        }
        String body=(httpServletRequest).toString();
        System.out.println("original body: "+ body);
        RequestWrapper requestWrapper=new RequestWrapper(httpServletRequest);
        filterChain.doFilter(requestWrapper,servletResponse);
    }
    private class RequestWrapper extends HttpServletRequestWrapper {
        public RequestWrapper(HttpServletRequest request) {
            super(request);
        }

        @Override
        public ServletInputStream getInputStream() throws IOException {
            byte[] bytes = new byte[0];
            ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(bytes);
            ServletInputStream servletInputStream = new ServletInputStream() {
                @Override
                public int read() throws IOException {
                    return byteArrayInputStream.read();
                }

                @Override
                public boolean isFinished() {
                    return byteArrayInputStream.read() == -1 ? true : false;
                }

                @Override
                public boolean isReady() {
                    return false;
                }

                @Override
                public void setReadListener(ReadListener listener) {

                }
            };
            return servletInputStream;
        }
    }
}
```

## 十、服务的追踪：Sleuth

### 10.1	引言

> 在整个微服务架构中，微服务很多，一个请求可能需要调用很多很多的服务，最终才能完成一个功能，如果说，这个整个功能出现了问题，在这么多的服务中，如何去定位问题的所在点，出现问题的原因是什么。
>
> 1、Sleuth可以获得到整个服务链路的信息。
>
> 2、Zipkin通过图形化界面去看到信息。
>
> 3、Sleuth将日志信息存储到数据库中。

![image-20201207212921438](./assets/image-20201207212921438.png)

### 10.2	Sleuth使用

1、导入依赖

```xml
<dependency>
    <groupId>org.springframework.cloud</groupId>
    <artifactId>spring-cloud-starter-sleuth</artifactId>
</dependency>
```

2、编写配置文件

```yml
logging:
  level:
    org.springframework.web.servlet.DispatcherServlet: DEBUG
```

3、测试

![image-20201207215113742](./assets/image-20201207215113742.png)

链路日志各个字段代表的含义：

- SEARCH：总链路id
- 516：当前服务链路id
- false：不会将当前的日志信息输出到其他系统中\

### 10.3	Zipkin的使用

1、搭建Zipkin的web工程

```yml
version: "3.1"
services:
	zipkin:
	  image: daocloud.io/daocloud/zipkin:latest
	  restart: always
	  container_name: zipkin
	  ports:
	    - 9411:9411
```

2、导入依赖

```xml
<dependency>
    <groupId>org.springframework.cloud</groupId>
    <artifactId>spring-cloud-starter-zipkin</artifactId>
</dependency>
```

3、编写配置文件

```yml
spring:
  sleuth:
    sampler:
      probability: 1     #代表百分之多少的sleuth信息需要输出到zipkin中
    zipkin:
      base-url: http://192.168.25.150:9411/   #指定zipkin的地址
```

4、测试

### 10.4	整合RabbitMQ

1、导入RabbitMQ依赖

2、修改配置文件

```yml
#指定服务的名称
spring:
  zipkin:
    base-url: http://192.168.25.150:9411/   #指定zipkin的地址
    sender:
      type: rabbit
```

3、修改zipkin的信息

```yml
version: "3.1"
services:
	zipkin:
	  image: daocloud.io/daocloud/zipkin:latest
	  restart: always
	  container_name: zipkin
	  ports:
	    - 9411:9411
	  environment:
	   - RABBIT_ADDRESSES=192.168.25.150:5672
	   - RABBIT_PASSWORD=test
	   - RABBIT_USER=test
	   - RABBIT_VIRTUAL_HOST=/
```

4、测试

![image-20201207231127086](./assets/image-20201207231127086.png)

### 10.5	Zipkin存储数据到ES

1、重新修改zipkin的yml文件

```yml
version: "3.1"
services:
	zipkin:
	  image: daocloud.io/daocloud/zipkin:latest
	  restart: always
	  container_name: zipkin
	  ports:
	    - 9411:9411
	  environment:
	   - RABBIT_ADDRESSES=192.168.25.150:5672
	   - RABBIT_PASSWORD=test
	   - RABBIT_USER=test
	   - RABBIT_VIRTUAL_HOST=/
	   - STORAGE_TYPE=elasticsearch
	   - ES_HOSTS=http://192.168.25.150:9200
```

## 十一、完整SpringCloud架构图

![image-20201207233028081](./assets/image-20201207233028081.png)

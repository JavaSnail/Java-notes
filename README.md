# Learning_Notes

## FrameWork

- Struts2

- Hibernate

## Git

- 配置
- 取得项目的Git仓库
- 将记录每次更新到仓库
- 远程仓库
- 分支的使用
- 标签的使用
- 日志
- 撤销
- 选择某些commit操作
- 解决冲突
- Submodule
- Subtree
- 其他

## [SpringCloud](https://github.com/JavaSnail/Java-notes/blob/master/SpringCloud/README.md)

### [一、SpringCloud介绍](https://github.com/JavaSnail/Java-notes/blob/master/SpringCloud/README.md#%E4%B8%80springcloud%E4%BB%8B%E7%BB%8D)

### 1.1 微服务架构

### 1.2 SpringCloud介绍

### [二、服务的注册与发现：Eureka](https://github.com/JavaSnail/Java-notes/blob/master/SpringCloud/README.md#%E4%BA%8C%E6%9C%8D%E5%8A%A1%E7%9A%84%E6%B3%A8%E5%86%8C%E4%B8%8E%E5%8F%91%E7%8E%B0eureka)

### 2.1 引言

### 2.2 Euraka的快速入门

#### 2.2.1 创建EurekaServer

#### 2.2.2 创建EurekaClient

### 2.3 Eureka的安全性

### 2.4 Eureka的高可用

### 2.5 Eureka的细节

## [三、服务间的负载均衡：Ribbon](https://github.com/JavaSnail/Java-notes/blob/master/SpringCloud/README.md#%E4%B8%89%E6%9C%8D%E5%8A%A1%E9%97%B4%E7%9A%84%E8%B4%9F%E8%BD%BD%E5%9D%87%E8%A1%A1ribbon)

### 3.1 引言

### 3.2 Ribbon的快速入门

### 3.3 Ribbon配置负载均衡策略

## [四、服务间的调用：Feign](https://github.com/JavaSnail/Java-notes/blob/master/SpringCloud/README.md#%E5%9B%9B%E6%9C%8D%E5%8A%A1%E9%97%B4%E7%9A%84%E8%B0%83%E7%94%A8feign)

### 4.1 引言

### 4.2 Feign的快速入门

### 4.3 Feign的参数传递 方式

### 4.4 Feign的Fallback

## [五、服务的隔离及熔断：Hystrix](https://github.com/JavaSnail/Java-notes/blob/master/SpringCloud/README.md#%E4%BA%94%E6%9C%8D%E5%8A%A1%E7%9A%84%E9%9A%94%E7%A6%BB%E5%8F%8A%E7%86%94%E6%96%ADhystrix)

### 5.1 引言

### 5.2 降级机制的实现

### 5.3 线程隔离

### 5.4 断路器

#### 5.4.1 断路器介绍

#### 5.4.2 配置断路器的监控界面

#### 5.4.3 配置断路器的属性

### 5.5 请求缓存

#### 5.5.1 请求缓存的实现

#### 5.5.2 请求缓存的实现

## [六、服务的网关：Zuul](https://github.com/JavaSnail/Java-notes/blob/master/SpringCloud/README.md#%E5%85%AD%E6%9C%8D%E5%8A%A1%E7%9A%84%E7%BD%91%E5%85%B3zuul)

### 6.1 引言

### 6.2 Zuul的快速入门

### 6.3 Zuul的常用配置信息

#### 6.3.1 Zuul的监控界面

#### 6.3.2 忽略服务配置

#### 6.3.4 灰度发布

### 6.4 Zuul的过滤器执行流程

### 6.5 Zuul过滤器入门

### 6.6 PreFilter实现token检验

### 6.7 Zuul降级

### 6.8 Zuul动态路由

## [七、多语言支持 Sidecar](https://github.com/JavaSnail/Java-notes/blob/master/SpringCloud/README.md#%E4%B8%83%E5%A4%9A%E8%AF%AD%E8%A8%80%E6%94%AF%E6%8C%81-sidecar)

### 7.1 引言

### 7.2 Sidecar实现

## [八、服务间消息传递：Stream](https://github.com/JavaSnail/Java-notes/blob/master/SpringCloud/README.md#%E5%85%AB%E6%9C%8D%E5%8A%A1%E9%97%B4%E6%B6%88%E6%81%AF%E4%BC%A0%E9%80%92stream)

### 8.1 引言

### 8.2 Stream快速入门

### 8.3 Stream重复消费

### 8.4 Stream的消费者手动ack

## [九、服务的动态配置：Config](https://github.com/JavaSnail/Java-notes/blob/master/SpringCloud/README.md#%E4%B9%9D%E6%9C%8D%E5%8A%A1%E7%9A%84%E5%8A%A8%E6%80%81%E9%85%8D%E7%BD%AEconfig)

### 9.1 引言

### 9.2 搭建Config-Server

### 9.3 修改customer连接Config

### 9.4 实现动态配置

#### 9.4.1 实现原理

#### 9.4.2 服务连接RabbitMQ

#### 9.4.3 实现手动刷新

#### 9.4.4 实现自动刷新

## [十、服务的追踪：Sleuth](https://github.com/JavaSnail/Java-notes/blob/master/SpringCloud/README.md#%E5%8D%81%E6%9C%8D%E5%8A%A1%E7%9A%84%E8%BF%BD%E8%B8%AAsleuth)

### 10.1 引言

### 10.2 Sleuth使用

### 10.3 Zipkin的使用

### 10.4 整合RabbitMQ

### 10.5 Zipkin存储数据到ES

## [十一、完整SpringCloud架构图](https://github.com/JavaSnail/Java-notes/blob/master/SpringCloud/README.md#%E5%8D%81%E4%B8%80%E5%AE%8C%E6%95%B4springcloud%E6%9E%B6%E6%9E%84%E5%9B%BE)
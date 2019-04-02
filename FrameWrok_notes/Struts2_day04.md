# Struts2_day04

[TOC]


## Struts2拦截器概述

1. struts2是框架，封装了很多的功能，Struts2里面封装的功能都是在拦截器里面。
2. Struts2里面封装了很多的功能，有很多拦截器，不是每次这些拦截器都执行，每次执行默认拦截器
3. Struts2里面默认拦截器的位置

![1554212489504](https://github.com/xuxueli982/Learning-notes/blob/master/FrameWrok_notes/Struts2_day04.assets/1554212489504.png)

![1554212533695](https://github.com/xuxueli982/Learning-notes/blob/master/FrameWrok_notes/Struts2_day04.assets/1554212533695.png)

![1554212696021](https://github.com/xuxueli982/Learning-notes/blob/master/FrameWrok_notes/Struts2_day04.assets/1554212696021.png)

4. 拦截器在什么时候执行？
   - 在action对象创建之后，action方法执行之前。

## 拦截器底层原理

1. 拦截器底层使用两个原理

   第一个 aop思想

   - 文字描述

     Aop是面向切面（方面）编程，有基本功能，扩展功能，不通过修改源代码的方式扩展功能。

   - 画图分析

   ![1554214661060](https://github.com/xuxueli982/Learning-notes/blob/master/FrameWrok_notes/Struts2_day04.assets/Aop%E6%80%9D%E6%83%B3.png)

   第二个 责任链模式

   - 在java中后很多设计模式，责任链模式是其中的一种
   - 责任链模式和过滤连很相似

   责任链模式：

   ​	要执行多个操作，有添加、修改、删除三个操作。

   首先执行添加操作，添加操作执行之后，做类似于放行操作，执行修改操作之后做类似于放行操作，执行删除操作。

   过滤链：

   ​	一个请求可以有多个过滤器进行过滤，每个过滤器只有做放行才能到下一个过滤器。

   ![1554215203465](https://github.com/xuxueli982/Learning-notes/blob/master/FrameWrok_notes/Struts2_day04.assets/1554215203465.png)

2. aop思想和责任链模式是如何应用到拦截器里面？

   - 文字描述：

     1. 在action对象创建之后，action方法执行之前。

     2. 在action方法执行之前执行默认的拦截器，执行过程中使用aop思想，在action没有直接调用拦截器的方法，使用配置文件的方式进行操作。

     3. 执行拦截器时，会有很多个拦截器执行，这时用到了责任链设计模式的思想

        > 例如：如果有三个拦截器，先执行拦截器1，执行拦截器1之后放行，执行拦截器2，执行拦截器2之后放行，执行拦截器3，执行拦截器3之后放行，执行action中的方法。

   - 画图分析

   ![](https://github.com/xuxueli982/Learning-notes/blob/master/FrameWrok_notes/Struts2_day04.assets/%E6%8B%A6%E6%88%AA%E5%99%A8%E7%9A%84%E6%89%A7%E8%A1%8C%E8%BF%87%E7%A8%8B.png)

3. 查看源代码

   - 执行action

     ![](https://github.com/xuxueli982/Learning-notes/blob/master/FrameWrok_notes/Struts2_day04.assets/1554219043772.png)

   - 创建action对象，使用动态代理的方式

     ![1554219401005](https://github.com/xuxueli982/Learning-notes/blob/master/FrameWrok_notes/Struts2_day04.assets/1554219401005.png)

     > 代理对象：不是真正的对象，但可以和对象所能执行的相同功能。

   - 执行action的方法

     ![1554219591228](https://github.com/xuxueli982/Learning-notes/blob/master/FrameWrok_notes/Struts2_day04.assets/1554219591228.png)

   - 执行多个拦截器，遍历执行

     ![1554220271875](https://github.com/xuxueli982/Learning-notes/blob/master/FrameWrok_notes/Struts2_day04.assets/1554220271875.png)

     类似放行的操作

     ![1554220380515](https://github.com/xuxueli982/Learning-notes/blob/master/FrameWrok_notes/Struts2_day04.assets/1554220380515.png)

## 重要概念

1. 过滤器和拦截器的区别
   - 过滤器：过滤器理论上可以过滤任意内容，比如HTML、jsp、servlet
   - 拦截器：拦截器只可以拦截action
2. servlet和action的区别
   - servlet在第一次访问的时候创建，创建一次，是一个单实例对象。
   - action在每次访问的时候创建，创建多次，是一个多实例对象。

## 自定义拦截器


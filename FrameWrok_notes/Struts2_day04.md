# Struts2_day04

[TOC]


## Struts2拦截器概述

1. struts2是框架，封装了很多的功能，Struts2里面封装的功能都是在拦截器里面。
2. Struts2里面封装了很多的功能，有很多拦截器，不是每次这些拦截器都执行，每次执行默认拦截器
3. Struts2里面默认拦截器的位置

![拦截器的位置](https://github.com/xuxueli982/Learning-notes/blob/master/FrameWrok_notes/Struts2_day04.assets/1554212489504.png)

![拦截器的位置](https://github.com/xuxueli982/Learning-notes/blob/master/FrameWrok_notes/Struts2_day04.assets/1554212533695.png)

![拦截器的位置](https://github.com/xuxueli982/Learning-notes/blob/master/FrameWrok_notes/Struts2_day04.assets/1554212696021.png)

4. 拦截器在什么时候执行？
   -  <font color=orange>在action对象创建之后，action方法执行之前。</font>

## 拦截器底层原理

1. 拦截器底层使用两个原理

   第一个 aop思想

   - 文字描述

     Aop是面向切面（方面）编程，有基本功能，扩展功能，不通过修改源代码的方式扩展功能。

   - 画图分析

   ![拦截器底层实现原理](https://github.com/xuxueli982/Learning-notes/blob/master/FrameWrok_notes/Struts2_day04.assets/1554212489504.png)

   第二个 责任链模式

   - 在java中有很多设计模式，责任链模式是其中的一种
   - 责任链模式和过滤链很相似

   责任链模式：

   ​	要执行多个操作，有添加、修改、删除三个操作。

   首先执行添加操作，添加操作执行之后，做类似于放行操作，执行修改操作之后做类似于放行操作，执行删除操作。

   过滤链：

   ​	一个请求可以有多个过滤器进行过滤，每个过滤器只有做放行才能到下一个过滤器。

   ![过滤器原理](https://github.com/xuxueli982/Learning-notes/blob/master/FrameWrok_notes/Struts2_day04.assets/1554215203465.png)

2. aop思想和责任链模式是如何应用到拦截器里面？

   - 文字描述：

     1. 在action对象创建之后，action方法执行之前。

     2. 在action方法执行之前执行默认的拦截器，执行过程中使用aop思想，在action中没有直接调用拦截器的方法，而是使用配置文件的方式进行操作。

     3. 执行拦截器时，会有很多个拦截器执行，这时用到了责任链设计模式的思想

        > 例如：如果有三个拦截器，先执行拦截器1，执行拦截器1之后放行，执行拦截器2，执行拦截器2之后放行，执行拦截器3，执行拦截器3之后放行，执行action中的方法。

   - 画图分析

   ![拦截器实现原理](https://github.com/xuxueli982/Learning-notes/blob/master/FrameWrok_notes/Struts2_day04.assets/%E6%8B%A6%E6%88%AA%E5%99%A8%E7%9A%84%E6%89%A7%E8%A1%8C%E8%BF%87%E7%A8%8B.png)

3. 查看源代码

   - 执行action类创建对象调用init方法

     ![启动加载配置文件](https://github.com/xuxueli982/Learning-notes/blob/master/FrameWrok_notes/Struts2_day04.assets/1554219043772.png)

   - 创建action对象，使用动态代理的方式

     ![创建action对象](https://github.com/xuxueli982/Learning-notes/blob/master/FrameWrok_notes/Struts2_day04.assets/1554219401005.png)

     > 代理对象：不是真正的对象，但可以和对象所能执行的相同功能。

   - 执行action的方法

     ![执行action中的方法](https://github.com/xuxueli982/Learning-notes/blob/master/FrameWrok_notes/Struts2_day04.assets/1554219591228.png)

   - 执行多个拦截器，遍历执行

     ![拦截器执行过程](https://github.com/xuxueli982/Learning-notes/blob/master/FrameWrok_notes/Struts2_day04.assets/1554220271875.png)

     类似放行的操作

     ![拦截器执行过程](https://github.com/xuxueli982/Learning-notes/blob/master/FrameWrok_notes/Struts2_day04.assets/1554220380515.png)

## 重要概念

1. 过滤器和拦截器的区别
   - 过滤器：过滤器理论上可以过滤任意内容，比如HTML、jsp、servlet
   - 拦截器：拦截器只可以拦截action
2. servlet和action的区别
   - servlet在第一次访问的时候创建，创建一次，是一个单实例对象。
   - action在每次访问的时候创建，创建多次，是一个多实例对象。

## 自定义拦截器

1. Struts2里面有许多拦截器，封装了Struts2的功能，但是在实际开发的过程中，Struts2里面的拦截器没有我们需要的功能，这时就要自己实现拦截器功能。

2. 拦截器的结构

   - 通过查看源代码看拦截器结构

     1. 继承类

        ![1554268020925](https://github.com/xuxueli982/Learning-notes/blob/master/FrameWrok_notes/Struts2_day04.assets/%E6%8B%A6%E6%88%AA%E5%99%A8%E7%BB%A7%E6%89%BF%E7%9A%84%E7%B1%BB.png)

        ![1554268124015](https://github.com/xuxueli982/Learning-notes/blob/master/FrameWrok_notes/Struts2_day04.assets/%E6%8B%A6%E6%88%AA%E5%99%A8%E5%AE%9E%E7%8E%B0%E7%9A%84%E6%8E%A5%E5%8F%A3.png)接口中有三个方法：

        ![1554268314472](https://github.com/xuxueli982/Learning-notes/blob/master/FrameWrok_notes/Struts2_day04.assets/%E6%8B%A6%E6%88%AA%E5%99%A8%E5%88%9D%E5%A7%8B%E5%8C%96.png)：初始化操作

        ![1554268291583](https://github.com/xuxueli982/Learning-notes/blob/master/FrameWrok_notes/Struts2_day04.assets/%E6%8B%A6%E6%88%AA%E5%99%A8%E7%9A%84%E9%94%80%E6%AF%81.png)：销毁操作

        ![1554268251435](https://github.com/xuxueli982/Learning-notes/blob/master/FrameWrok_notes/Struts2_day04.assets/%E6%8B%A6%E6%88%AA%E5%99%A8%E6%89%A7%E8%A1%8C%E7%9A%84%E9%80%BB%E8%BE%91.png)：拦截器逻辑的操作

   - 开发中建议使用另一种方式

     1. 创建类，继承MethodFilterInterceptor类实现
     2. 可以针对action中的某个方法不进行拦截处理

   - 关联自定义拦截器和action

     不是在action中调用拦截器的方法，而是通过配置文件方式让两者之间建立联系。

## Struts2标签库

1. Struts2标签只能用在jsp页面中

2. s:property：和ognl表达式一起获取值栈中的数据。

3. s:iterator：获取值栈list集合数据，遍历list集合
4. s:debug：查看值栈的结构和数据

## Struts2表单标签（会用）

```Java 
1. html表单标签
	1）form：action、method、enctype
	2)输入项
	- 大部分在input里面封装 type=“值”
	- text：普通输入项
	- password：密码输入项
	- radio：单选输入项
	- checkout：复选输入项
	- file：文件上传项
	- hidden：隐藏项
	- button：普通按钮
	- submit：提交按钮
	- image：图片提交
	- reset：重置
	- select：下拉输入项
	- textarea：文本域
2. Struts2的标签对应HTML标签中的大多数
<!--普通输入项-->
	<s:textfield name ="" label=""></s:textfield>
	<s:password name="" label=""></s:password>
<!--标签选择项-->
    <!--单选框-->
    <s:radio list="{'',''}" name="" label=""></s:radio>
	<s:radio list="#{'':'','':''}" name="" label=""></s:radio>
	<!--复选框-->
	<s:checkboxlist list="{'','',''}" name="" label=""></s:checkboxlist>
	<!--下拉列表框-->
	<s:select list="{'','','','',''}" name="" label=""></s:select>
<!--其他标签元素-->
    <!--文件上传标签-->
    <s:file name="" label=""></s:file>
    <!--隐藏标签-->
	<s:hidden name="" value=""></s:hidden>
	<!--文本域-->
	<s:textarea rows="" cols="" name="" label=""></s:textarea>
	<s:reset value="重置"></s:reset>
	<s:submit value="提交"></s:submit>
```






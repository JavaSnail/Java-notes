package com.xxl.entity;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Author: xuxueli
 * @Description:
 * @Date: 2020/12/3
 * @Version: 1.0
 */
@NoArgsConstructor
@Data
@AllArgsConstructor
public class Customer {

    private Integer id;

    private String name;

    private Integer age;
}

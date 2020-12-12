package com.xxl.fallback;

import com.xxl.client.SearchClient;
import com.xxl.entity.Customer;
import org.springframework.stereotype.Component;

/**
 * @Author: xuxueli
 * @Description:
 * @Date: 2020/12/3
 * @Version: 1.0
 */
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

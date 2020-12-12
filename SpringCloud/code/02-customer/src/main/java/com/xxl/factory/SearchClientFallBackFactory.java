package com.xxl.factory;

import com.xxl.client.SearchClient;
import com.xxl.fallback.SearchClientFallBack;
import feign.hystrix.FallbackFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @Author: xuxueli
 * @Description:
 * @Date: 2020/12/3
 * @Version: 1.0
 */
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

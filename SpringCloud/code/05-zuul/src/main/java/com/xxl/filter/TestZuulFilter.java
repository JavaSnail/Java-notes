package com.xxl.filter;

import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.exception.ZuulException;
import org.springframework.cloud.netflix.zuul.filters.support.FilterConstants;
import org.springframework.stereotype.Component;

/**
 * @Author: xuxueli
 * @Description:
 * @Date: 2020/12/6
 * @Version: 1.0
 */
@Component
public class TestZuulFilter extends ZuulFilter {

    @Override
    public String filterType() {
        return FilterConstants.PRE_TYPE;
    }

    @Override
    public int filterOrder() {
        return FilterConstants.PRE_DECORATION_FILTER_ORDER -1;
    }

    @Override
    public boolean shouldFilter() {
        //开启当前过滤器
        return true;
    }

    @Override
    public Object run() throws ZuulException {
        System.out.println("prifix过滤器执行~");
        return null;
    }
}

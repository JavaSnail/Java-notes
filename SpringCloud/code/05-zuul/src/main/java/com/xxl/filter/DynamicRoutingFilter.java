package com.xxl.filter;

import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.netflix.zuul.exception.ZuulException;
import org.springframework.cloud.netflix.zuul.filters.support.FilterConstants;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;

/**
 * @Author: xuxueli
 * @Description:
 * @Date: 2020/12/6
 * @Version: 1.0
 */
@Component
public class DynamicRoutingFilter extends ZuulFilter {
    @Override
    public String filterType() {
        return FilterConstants.PRE_TYPE;
    }

    @Override
    public int filterOrder() {
        return FilterConstants.PRE_DECORATION_FILTER_ORDER + 2;
    }

    @Override
    public boolean shouldFilter() {
        return true;
    }

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
}

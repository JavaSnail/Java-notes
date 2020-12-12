package com.xxl.filter;


import com.netflix.hystrix.strategy.concurrency.HystrixRequestContext;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import java.io.IOException;

/**
 * @Author: xuxueli
 * @Description:
 * @Date: 2020/12/6
 * @Version: 1.0
 */
@WebFilter("/*")
public class HystrixRequestContextInitFilter implements Filter {
    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HystrixRequestContext.initializeContext();
        filterChain.doFilter(servletRequest,servletResponse);
    }
}

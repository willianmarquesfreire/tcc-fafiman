package com.wmfsystem.hystrixserver.config;

import com.netflix.hystrix.strategy.concurrency.HystrixRequestContext;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by wmfsystem on 4/15/17.
 */
public class HystrixContextInterceptor extends HandlerInterceptorAdapter {

    static HystrixRequestContext globalSharedContext;
    static {
        HystrixRequestContext.initializeContext();
        globalSharedContext = HystrixRequestContext.getContextForCurrentThread();

    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        HystrixRequestContext.setContextOnCurrentThread(globalSharedContext);
        return super.preHandle(request, response, handler);
    }
}
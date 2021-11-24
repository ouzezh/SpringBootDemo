package com.ozz.springboot.config.interceptor;

import javax.annotation.Nullable;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.MDC;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.UUID;

/**
 * 拦截器：由Spring调用 ，能实现Filter的功能，可以IoC注入Spring里的资源，AOP的一种实现策略
 */
@Component
public class MyHandlerInterceptor implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        MDC.put("myMdc", UUID.randomUUID().toString());
        System.out.println(String.format("%s.preHandle(): contextPath=%s, requestURI=%s", getClass().getSimpleName(),
                request.getContextPath(), request.getRequestURI()));
        return true;
    }

    /**
     * 视图渲染后执行，多用于清理资源
     */
    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler,
                                @Nullable Exception ex) throws Exception {
        MDC.clear();
        System.out.println(String.format("%s.afterCompletion(): contextPath=%s, requestURI=%s",
                getClass().getSimpleName(), request.getContextPath(), request.getRequestURI()));
    }
}

package com.ozz.springboot.config.interceptor;

import javax.annotation.Nullable;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

/**
 * 拦截器：由Spring调用 ，能实现Filter的功能，可以IoC注入Spring里的资源，AOP的一种实现策略
 */
@Component
public class MyHandlerInterceptor implements HandlerInterceptor {
  @Override
  public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
    System.out.println(String.format("%s.preHandle(): contextPath=%s, requestURI=%s", getClass().getSimpleName(), request.getContextPath(), request.getRequestURI()));
    return true;
  }
  @Override
  public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, @Nullable Exception ex) throws Exception {
    System.out.println(String.format("%s.afterCompletion(): contextPath=%s, requestURI=%s", getClass().getSimpleName(), request.getContextPath(), request.getRequestURI()));
  }
}

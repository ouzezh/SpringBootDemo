package com.ozz.springboot.component.interceptor;

import javax.annotation.Nullable;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.HandlerInterceptor;

/**
 * 拦截器：由Spring调用 ，能实现Filter的功能，可以IoC注入Spring里的资源，AOP的一种实现策略
 */
public interface SampleHandlerInterceptor extends HandlerInterceptor {
  @Override
  default boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
    System.out.println("spring拦截器：preHandle");
    return true;
  }
  @Override
  default void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, @Nullable Exception ex) throws Exception {
    System.out.println("spring拦截器：afterCompletion");
  }
}

package com.ozz.springboot.component.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

@Component
public class CorsInterceptor implements HandlerInterceptor {
  @Override
  public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
    String origin = request.getHeader("Origin");
    if (StringUtils.isNotEmpty(origin)) {
      response.setHeader("Access-Control-Allow-Origin", origin);
      response.setHeader("Access-Control-Allow-Headers", "*");
      // response.setHeader("Access-Control-Allow-Credentials", "true");
      // response.setHeader("Access-Control-Allow-Methods", "GET, HEAD, POST, PUT, PATCH, DELETE, OPTIONS");
      // response.setHeader("Access-Control-Max-Age", "3600");
    }

    if (HttpMethod.OPTIONS.toString().equals(request.getMethod())) {
      return false;
    }
    return true;
  }
}

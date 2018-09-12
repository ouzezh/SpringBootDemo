package com.ozz.springboot.component;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import com.ozz.springboot.component.interceptor.SampleHandlerInterceptor;

@Configuration
public class SampleWebMvcConfigurer implements WebMvcConfigurer {
  @Override
  public void addInterceptors(InterceptorRegistry registry) {
    registry.addInterceptor(new SampleHandlerInterceptor() {}).addPathPatterns("/**").excludePathPatterns("/xx/**");
  }
}

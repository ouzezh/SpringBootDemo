package com.ozz.springboot.component;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.core.convert.support.GenericConversionService;
import org.springframework.web.bind.support.ConfigurableWebBindingInitializer;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerAdapter;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ozz.springboot.component.converter.StringToDateConverter;
import com.ozz.springboot.component.interceptor.SampleHandlerInterceptor;

@Configuration
public class SampleConfiguration implements WebMvcConfigurer {
  @Autowired
  private RequestMappingHandlerAdapter handlerAdapter;
//  @Resource
//  private CorsInterceptor corsInterceptor;

  /**
   * 设置首页
   */
  @Override
  public void addViewControllers(ViewControllerRegistry registry) {
    registry.addViewController("/").setViewName("forward:/index.html");
    registry.setOrder(Ordered.HIGHEST_PRECEDENCE);
  }

  /**
   * 拦截器
   */
  @Override
  public void addInterceptors(InterceptorRegistry registry) {
//    registry.addInterceptor(corsInterceptor);// 必须放在最前面
    registry.addInterceptor(new SampleHandlerInterceptor() {}).addPathPatterns("/**").excludePathPatterns("/xx/**");
  }

  /**
   * HTTP请求参数格式转化:[]默认转为数组
   */
  @Bean
  public ObjectMapper objectMapper() {
    ObjectMapper objectMapper = new ObjectMapper();
    objectMapper.configure(DeserializationFeature.USE_JAVA_ARRAY_FOR_JSON_ARRAY, true);
    return objectMapper;
  }

  /**
   * HTTP请求参数格式转化:String->Date
   */
  @PostConstruct
  public void initWebBinding() {
    ConfigurableWebBindingInitializer initializer = (ConfigurableWebBindingInitializer) handlerAdapter.getWebBindingInitializer();
    if (initializer.getConversionService() != null) {
      GenericConversionService genericConversionService = (GenericConversionService) initializer.getConversionService();
      genericConversionService.addConverter(new StringToDateConverter());
    }
  }

  /**
   * 允许跨域访问
   */
  @Override
  public void addCorsMappings(CorsRegistry registry) {
    registry.addMapping("/**")// 允许跨域访问的路径
            .allowedOrigins("*")
            .allowedMethods("*")
            .allowedHeaders("*")
//            .exposedHeaders("header1", "header2")
            .allowCredentials(true)
            .maxAge(3600);
  }
}

package com.ozz.springboot.component;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.core.convert.support.GenericConversionService;
import org.springframework.web.bind.support.ConfigurableWebBindingInitializer;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerAdapter;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ozz.springboot.component.converter.StringToDateConverter;
import com.ozz.springboot.component.interceptor.SampleHandlerInterceptor;

@Configuration
public class SampleConfig implements WebMvcConfigurer {

  @Autowired
  private RequestMappingHandlerAdapter handlerAdapter;
  @Autowired
  private SampleHandlerInterceptor sampleHandlerInterceptor;
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
    registry.addInterceptor(sampleHandlerInterceptor).addPathPatterns("/**")
        .excludePathPatterns("/xx/**");
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
    ConfigurableWebBindingInitializer initializer = (ConfigurableWebBindingInitializer) handlerAdapter
        .getWebBindingInitializer();
    if (initializer.getConversionService() != null) {
      GenericConversionService genericConversionService = (GenericConversionService) initializer
          .getConversionService();
      genericConversionService.addConverter(new StringToDateConverter());
    }
  }

  private CorsConfiguration buildCorsConfiguration() {
    CorsConfiguration corsConfiguration = new CorsConfiguration();

    corsConfiguration.addAllowedOrigin("http://localhost:8080");
    corsConfiguration.addAllowedOrigin("http://localhost");

    corsConfiguration.addAllowedHeader("*");
    corsConfiguration.addAllowedMethod("*");
    corsConfiguration.setAllowCredentials(true);
    return corsConfiguration;
  }

  @Bean
  public CorsFilter corsFilter() {
    UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
    source.registerCorsConfiguration("/**", buildCorsConfiguration());
    return new CorsFilter(source);
  }

  @PreDestroy
  public void destroy() {
    System.out.println("Callback triggered - @PreDestroy.");
  }
}

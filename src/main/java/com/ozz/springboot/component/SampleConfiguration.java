package com.ozz.springboot.component;

import javax.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.convert.support.GenericConversionService;
import org.springframework.web.bind.support.ConfigurableWebBindingInitializer;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
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

  @Override
  public void addInterceptors(InterceptorRegistry registry) {
    registry.addInterceptor(new SampleHandlerInterceptor() {}).addPathPatterns("/**").excludePathPatterns("/xx/**");
  }

  @Bean
  public ObjectMapper objectMapper() {
    ObjectMapper objectMapper = new ObjectMapper();
    objectMapper.configure(DeserializationFeature.USE_JAVA_ARRAY_FOR_JSON_ARRAY, true);
    return objectMapper;
  }

  @PostConstruct
  public void initWebBinding() {
    ConfigurableWebBindingInitializer initializer = (ConfigurableWebBindingInitializer) handlerAdapter.getWebBindingInitializer();
    if (initializer.getConversionService() != null) {
      GenericConversionService genericConversionService = (GenericConversionService) initializer.getConversionService();
      genericConversionService.addConverter(new StringToDateConverter());
    }
  }

}

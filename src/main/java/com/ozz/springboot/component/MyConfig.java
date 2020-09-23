package com.ozz.springboot.component;

import com.alibaba.fastjson.serializer.SerializerFeature;
import com.alibaba.fastjson.support.config.FastJsonConfig;
import com.alibaba.fastjson.support.spring.FastJsonHttpMessageConverter;
import com.ozz.springboot.component.converter.StringToDateConverter;
import com.ozz.springboot.component.interceptor.MyHandlerInterceptor;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.core.convert.support.GenericConversionService;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.bind.support.ConfigurableWebBindingInitializer;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerAdapter;

@Configuration
public class MyConfig implements WebMvcConfigurer {

  @Autowired
  private RequestMappingHandlerAdapter handlerAdapter;
  @Autowired
  private MyHandlerInterceptor myHandlerInterceptor;
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
    registry.addInterceptor(myHandlerInterceptor).addPathPatterns("/**")
        .excludePathPatterns("/xx/**");
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

  /**
   * 使用fastjson代替jackson
   */
  @Override
  public void configureMessageConverters(List<HttpMessageConverter<?>> converters) {
    // remove jackson converter
    for (int i = converters.size() - 1; i >= 0; i--) {
      if (converters.get(i) instanceof MappingJackson2HttpMessageConverter) {
        converters.remove(i);
      }
    }

    // fastjson config
    FastJsonHttpMessageConverter fastJsonHttpMessageConverter = new FastJsonHttpMessageConverter();
    FastJsonConfig config = new FastJsonConfig();
    config.setSerializerFeatures(
//        SerializerFeature.WriteMapNullValue,        // 是否输出值为null的字段,默认为false
//        SerializerFeature.WriteNullListAsEmpty,     // 将Collection类型字段的字段空值输出为[]
//        SerializerFeature.WriteNullStringAsEmpty,   // 将字符串类型字段的空值输出为空字符串
        SerializerFeature.DisableCircularReferenceDetect    // 禁用循环引用
    );
    fastJsonHttpMessageConverter.setFastJsonConfig(config);

    // MediaType support
    List<MediaType> fastMediaTypes = new ArrayList<>();
    fastMediaTypes.add(MediaType.APPLICATION_JSON);
    fastJsonHttpMessageConverter.setSupportedMediaTypes(fastMediaTypes);

    converters.add(fastJsonHttpMessageConverter);
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

package com.ozz.springboot.config;

import com.alibaba.fastjson.serializer.SerializerFeature;
import com.alibaba.fastjson.support.config.FastJsonConfig;
import com.alibaba.fastjson.support.spring.FastJsonHttpMessageConverter;
import com.ozz.springboot.config.interceptor.MyHandlerInterceptor;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.PreDestroy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class MyConfig implements WebMvcConfigurer {

  @Autowired
  private MyHandlerInterceptor myHandlerInterceptor;

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
    registry.addInterceptor(myHandlerInterceptor).addPathPatterns("/**")
        .excludePathPatterns("/xx/**");
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
//        // 是否输出值为null的字段,默认为false
//        SerializerFeature.WriteMapNullValue,
//        // 将Collection类型字段的字段空值输出为[]
//        SerializerFeature.WriteNullListAsEmpty,
//        // 将字符串类型字段的空值输出为空字符串
//        SerializerFeature.WriteNullStringAsEmpty,
        // 禁用循环引用
        SerializerFeature.DisableCircularReferenceDetect
    );
    fastJsonHttpMessageConverter.setFastJsonConfig(config);

    // MediaType support
    List<MediaType> fastMediaTypes = new ArrayList<>();
    fastMediaTypes.add(MediaType.APPLICATION_JSON);
    fastJsonHttpMessageConverter.setSupportedMediaTypes(fastMediaTypes);

    converters.add(fastJsonHttpMessageConverter);
  }

  @Override
  public void addCorsMappings(CorsRegistry registry) {
    registry.addMapping("*").allowedOrigins("*").allowedHeaders("*").allowedMethods("*")
        .allowCredentials(true);
  }
//  private CorsConfiguration buildCorsConfiguration() {
//    CorsConfiguration corsConfiguration = new CorsConfiguration();
//    corsConfiguration.addAllowedOrigin("*");
//    corsConfiguration.addAllowedHeader("*");
//    corsConfiguration.addAllowedMethod("*");
//    corsConfiguration.setAllowCredentials(true);
//    return corsConfiguration;
//  }
//  @Bean
//  public CorsFilter corsFilter() {
//    UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
//    source.registerCorsConfiguration("/**", buildCorsConfiguration());
//    return new CorsFilter(source);
//  }

}

package com.ozz.springboot;

import java.util.EnumSet;

import javax.servlet.DispatcherType;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.context.embedded.FilterRegistrationBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.context.web.SpringBootServletInitializer;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;

import com.ozz.springboot.filter.SampleFilter;

@ComponentScan
@EnableAutoConfiguration(exclude = {DataSourceAutoConfiguration.class})
@EnableConfigurationProperties({Settings.class})
public class SampleApplication extends SpringBootServletInitializer {

  private static ApplicationContext ctx;

  public static void main(String[] args) {
    ctx = SpringApplication.run(SampleApplication.class, args);
  }

  @Override
  protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
    return application.sources(SampleApplication.class);
  }

  @Bean
  public FilterRegistrationBean securityFilter() {
    FilterRegistrationBean registration = new FilterRegistrationBean();
    registration.setFilter(new SampleFilter());
    registration.setDispatcherTypes(EnumSet.allOf(DispatcherType.class));
    registration.addUrlPatterns("/*");
    registration.setEnabled(true);
    return registration;
  }

  public static ApplicationContext getApplicationContext() {
    return ctx;
  }

}

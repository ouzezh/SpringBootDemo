package com.ozz.springboot;

import java.util.EnumSet;

import javax.servlet.DispatcherType;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;

import com.ozz.springboot.filter.SampleFilter;

@ComponentScan
@EnableConfigurationProperties({Settings.class})
@SpringBootApplication
public class SampleApplication extends SpringBootServletInitializer {

  private static ApplicationContext ctx;

  public static void main(String[] args) {
    ctx = SpringApplication.run(SampleApplication.class, args);
  }

  // @Override
  // protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
  // return application.sources(SampleApplication.class);
  // }

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

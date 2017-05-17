package com.ozz.springboot;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.ComponentScan;

@ComponentScan
@EnableConfigurationProperties({Settings.class})
@SpringBootApplication
public class SampleApplication extends SpringBootServletInitializer {

  private static ApplicationContext ctx;

  public static void main(String[] args) {
    ctx = SpringApplication.run(SampleApplication.class, args);
  }

  public static ApplicationContext getApplicationContext() {
    return ctx;
  }

}

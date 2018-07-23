package com.ozz.springboot;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.ApplicationContext;

@SpringBootApplication
@EnableConfigurationProperties({Settings.class})
//@ImportResource(locations={"classpath:spring-context.xml"})
public class SampleApplication {

  private static ApplicationContext ctx;

  public static void main(String[] args) {
    ctx = SpringApplication.run(SampleApplication.class, args);
  }

  public static ApplicationContext getApplicationContext() {
    return ctx;
  }

}

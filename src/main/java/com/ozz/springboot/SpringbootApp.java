package com.ozz.springboot;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableConfigurationProperties({Settings.class})
@EnableScheduling
public class SpringbootApp {

  public static void main(String[] args) {
    SpringApplication.run(SpringbootApp.class, args);
  }

}

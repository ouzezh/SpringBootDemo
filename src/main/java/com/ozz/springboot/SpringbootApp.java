package com.ozz.springboot;

import com.ozz.springboot.exception.ErrorException;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class SpringbootApp {

  public static void main(String[] args) {
    try {
      new SpringApplicationBuilder().sources(SpringbootApp.class).profiles(args[0].replace("--spring.profiles.active=", "")).run(args);
    } catch (Exception e) {
      ErrorException ee = new ErrorException("parse args --spring.profiles.active={profile} error");
      ee.addSuppressed(e);
      throw ee;
    }
  }

}

package com.ozz.springboot;

import com.ozz.springboot.exception.ErrorException;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableConfigurationProperties({MyBindConfig.class})
@EnableScheduling
public class SpringbootApp {

  public static void main(String[] args) {
    String profile = null;
    try {
      profile = args[0].replace("--spring.profiles.active=", "");
    } catch (Exception e) {
      ErrorException ee = new ErrorException("parse args --spring.profiles.active={profile} error");
      ee.addSuppressed(e);
      throw ee;
    }
    new SpringApplicationBuilder().sources(SpringbootApp.class).profiles(profile).run(args);
  }

}

package com.ozz.springboot;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class SpringbootApp {

  public static void main(String[] args) {
    String profile = args.length>0 ? args[0].replace("--spring.profiles.active=", "") : "dev";
    new SpringApplicationBuilder().sources(SpringbootApp.class).profiles(profile).run(args);
  }

}

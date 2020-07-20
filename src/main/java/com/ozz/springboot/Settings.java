package com.ozz.springboot;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "ozz")
public class Settings {
  private String myConfig;

  public String getMyConfig() {
    return myConfig;
  }
  public void setMyConfig(String myConfig) {
    this.myConfig = myConfig;
  }

}

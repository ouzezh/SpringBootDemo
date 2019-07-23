package com.ozz.springboot;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "ozz")
public class Settings {
  private String sampleConfig;

  public String getSampleConfig() {
    return sampleConfig;
  }
  public void setSampleConfig(String sampleConfig) {
    this.sampleConfig = sampleConfig;
  }

}

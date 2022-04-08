package com.ozz.springboot.vo;

import java.util.Map;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Data
@Component
@ConfigurationProperties(prefix = "ozz")
public class MyBindConfig {
  private String myConfig;
  private Map<String, Long> myMapConfig;
}

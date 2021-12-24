package com.ozz.springboot;

import java.util.Map;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Data
@Component
@ConfigurationProperties(prefix = "ozz")
public class MyBindConfig {
  private String myConfig;
  private Map<String, Long> myMapConfig;
}

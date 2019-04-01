package com.ozz.springboot.component.runner;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@Component
@Order(value = 0)
public class SampleApplicationRunner implements ApplicationRunner {
  private Logger log = LoggerFactory.getLogger(getClass());

  @Value("${ozz.sampleConfig}")
  private String sampleConfig;

  /**
   * 后台启动进程
   *
   */
  @Override
  public void run(ApplicationArguments args) throws Exception {
    log.info("start SampleApplicationRunner: read config {}", sampleConfig);
  }
}

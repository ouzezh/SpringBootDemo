package com.ozz.springboot.config.runner;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import com.ozz.springboot.Settings;

/**
 * Springboot 启动后执行初始化
 */
@Component
@Order(value = 0)
public class MyApplicationRunner implements ApplicationRunner {
  private Logger log = LoggerFactory.getLogger(getClass());

  @Autowired
  private Settings settings;

  @Override
  public void run(ApplicationArguments args) throws Exception {
    log.info("start {}: read config: {}", this.getClass().getSimpleName(), settings.getMyConfig());
  }
}

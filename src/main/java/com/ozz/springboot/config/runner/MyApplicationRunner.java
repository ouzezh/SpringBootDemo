package com.ozz.springboot.config.runner;

import com.ozz.springboot.Settings;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

/**
 * Springboot 启动后执行初始化
 */
@Slf4j
@Component
@Order(value = 0)
public class MyApplicationRunner implements ApplicationRunner {

  @Autowired
  private Settings settings;

  @Override
  public void run(ApplicationArguments args) throws Exception {
    log.info("start {}: read config: {}", this.getClass().getSimpleName(), settings.getMyConfig());
  }
}

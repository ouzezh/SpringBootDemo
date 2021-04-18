package com.ozz.springboot.config.runner;

import com.alibaba.fastjson.JSON;
import com.ozz.springboot.MyBindConfig;
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
public class MyAppRunner implements ApplicationRunner {

  @Autowired
  private MyBindConfig myBindConfig;

  @Override
  public void run(ApplicationArguments args) throws Exception {
    log.info("start {}: read config: {}", this.getClass().getSimpleName(), JSON.toJSONString(myBindConfig));
  }
}

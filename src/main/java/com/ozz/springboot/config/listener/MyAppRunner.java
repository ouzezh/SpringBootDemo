package com.ozz.springboot.config.listener;

import com.alibaba.fastjson.JSON;
import com.ozz.springboot.vo.MyBindConfig;
import com.ozz.springboot.util.LogUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

/**
 * Springboot 启动后执行初始化
 */
@Component
@Order(value = 0)
public class MyAppRunner implements ApplicationRunner {

  @Autowired
  private MyBindConfig myBindConfig;

  @Override
  public void run(ApplicationArguments args) throws Exception {
    LogUtil.log(String.format("read config: %s", JSON.toJSONString(myBindConfig)));
  }
}

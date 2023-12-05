package com.ozz.springboot.service;

import cn.hutool.core.util.StrUtil;
import cn.hutool.log.StaticLog;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest("--spring.profiles.active=dev")
class MyServiceTest {
  @Autowired
  MyService service;
//  @Autowired
//  StringEncryptor stringEncryptor;

  @Test
  void testService() {
    StaticLog.info(StrUtil.toString(service.myService("p")));
  }

//  @Test
//  void testEncryptPwd() {
//    String result = stringEncryptor.encrypt("my config");
//    String str = stringEncryptor.decrypt(result);
//    StaticLog.info(String.format("%s -> %s", result, str));
//  }

}

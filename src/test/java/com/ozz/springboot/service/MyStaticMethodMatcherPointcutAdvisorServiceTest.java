package com.ozz.springboot.service;

import org.jasypt.encryption.StringEncryptor;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest("--spring.profiles.active=dev")
class MyStaticMethodMatcherPointcutAdvisorServiceTest {
  @Autowired
  MyService service;
  @Autowired
  StringEncryptor stringEncryptor;

  @Test
  void testService() {
    System.out.println(service.sevice("p"));
  }

  @Test
  void testEncryptPwd() {
    String result = stringEncryptor.encrypt("Hello, World!");
    String str = stringEncryptor.decrypt(result);
    System.out.println(String.format("%s -> %s", result, str));
  }

}

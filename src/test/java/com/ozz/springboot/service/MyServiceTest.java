package com.ozz.springboot.service;

import org.jasypt.encryption.StringEncryptor;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class MyServiceTest {
  @Autowired
  MyService service;
  @Autowired
  StringEncryptor stringEncryptor;

  @Test
  public void testService() {
    System.out.println(service.sevice("p"));
  }

  @Test
  public void testEncryptPwd() {
    String result = stringEncryptor.encrypt("Hello, World!");
    String str = stringEncryptor.decrypt(result);
    System.out.println(String.format("%s -> %s", result, str));
  }

}

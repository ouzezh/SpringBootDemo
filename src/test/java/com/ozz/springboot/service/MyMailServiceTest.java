package com.ozz.springboot.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest("--spring.profiles.active=dev")
class MyMailServiceTest {
  @Autowired
  MyMailService myMailService;

  @Test
  void testSendMail() {
    myMailService.sendMail("测试主题", "测试正文");
//    dao.sendMimeMail("测试附件", "测试正文", Collections.singletonList(
//        Pair.of("test.txt", new File("C:\\Users\\ouzezhou\\Desktop\\Temp\\202103\\test.txt"))));
//    myMailService.sendErrorMail("测试主题x1", null, null);
//    myMailService.sendErrorMail("测试主题x2", null, new RuntimeException("x2"));
//    myMailService.sendErrorMail("测试主题x3", "x3", null);
//    myMailService.sendErrorMail("测试主题x4", "x4", new RuntimeException("x4_error"));
  }
}

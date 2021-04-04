package com.ozz.springboot.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class MyMailServiceTest {
  @Autowired
  MyMailService myMailService;

  @Test
  public void testSendMail() {
    myMailService.sendSimpleMail("测试主题", "测试正文");
//    dao.sendMimeMail("测试附件", "测试正文", Collections.singletonList(
//        Pair.of("test.txt", new File("C:\\Users\\ouzezhou\\Desktop\\Temp\\202103\\test.txt"))));
  }
}

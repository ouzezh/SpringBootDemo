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
  MailService mailService;

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

  @Test
  public void testSendMail() {
    mailService.sendSimpleMail("ouzezh@aliyun.com", "测试主题", "测试正文");
//    dao.sendMimeMail("ouzezh@aliyun.com", "测试附件", "测试正文", Collections.singletonList(
//        Pair.of("test.txt", new File("C:\\Users\\ouzezhou\\Desktop\\Temp\\202103\\test.txt"))));
  }
}

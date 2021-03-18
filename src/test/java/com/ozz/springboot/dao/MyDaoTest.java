package com.ozz.springboot.dao;

import com.ozz.springboot.service.MyDao;
import org.jasypt.encryption.StringEncryptor;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class MyDaoTest {

  @Autowired
  MyDao dao;

  @Autowired
  StringEncryptor stringEncryptor;

  @Test
  public void testService() {
    System.out.println(dao.sevice("p"));
  }

  @Test
  public void testEncryptPwd() {
    String result = stringEncryptor.encrypt("Hello, World!");
    String str = stringEncryptor.decrypt(result);
    System.out.println(String.format("%s -> %s", result, str));
  }

  @Test
  public void testSendMail() {
    dao.sendSimpleMail("ouzezh@yeah.net", "测试主题", "测试正文");
//    dao.sendMimeMail("ouzezh@yeah.net", "测试附件", "测试正文", Collections.singletonList(
//        Pair.of("test.txt", new File("C:\\Users\\ouzezhou\\Desktop\\Temp\\202103\\test.txt"))));
  }
}

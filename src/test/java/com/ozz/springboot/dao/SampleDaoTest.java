package com.ozz.springboot.dao;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
//@RunWith(SpringJUnit4ClassRunner.class)
//@SpringApplicationConfiguration(classes = SampleApplication.class)
public class SampleDaoTest {
  @Autowired
  SampleDao dao;

  @Test
  public void testService() {
    System.out.println(dao.sevice("p"));
  }

}

package com.ozz.springboot.dao;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.ozz.springboot.service.SampleDao;

@RunWith(SpringRunner.class)
@SpringBootTest
public class SampleDaoTest {
  @Autowired
  SampleDao dao;

  @Test
  public void testService() {
    System.out.println(dao.sevice("p"));
  }

}

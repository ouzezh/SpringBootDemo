package com.ozz.springboot.service;

import java.util.HashMap;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class MyService {

  private Logger log = LoggerFactory.getLogger(getClass());

  @Value("${ozz.myConfig}")
  private String myConfig;
  @Value("${spring.mail.username}")
  private String mailFrom;

  public Map<String, String> sevice(String p) {
    log.debug("test myDao service");
    Map<String, String> map = new HashMap<>();
    map.put("p", p);
    map.put("config", myConfig);
    return map;
  }
}

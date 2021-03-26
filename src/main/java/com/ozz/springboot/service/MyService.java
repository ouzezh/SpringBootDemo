package com.ozz.springboot.service;

import java.util.HashMap;
import java.util.Map;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class MyService {

  @Value("${ozz.myConfig}")
  private String myConfig;

  public Map<String, String> sevice(String p) {
    log.debug("test myDao service");
    Map<String, String> map = new HashMap<>();
    map.put("p", p);
    map.put("config", myConfig);
    return map;
  }
}

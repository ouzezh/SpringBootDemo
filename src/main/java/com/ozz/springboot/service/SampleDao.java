package com.ozz.springboot.service;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ozz.springboot.Settings;

@Service
public class SampleDao {
  private Logger log = LoggerFactory.getLogger(getClass());

  @Autowired
  private Settings settings;

  public Map<String, String> sevice(String p) {
    log.debug("test sample dao service");
    Map<String, String> map = new HashMap<>();
    map.put("p", p);
    map.put("config", settings.getSampleConfig());
    return map;
  }
}

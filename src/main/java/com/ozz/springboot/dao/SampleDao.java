package com.ozz.springboot.dao;

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

  public String sevice() {
    log.debug("test sample dao service");
    return settings.getSampleConfig();
  }
}

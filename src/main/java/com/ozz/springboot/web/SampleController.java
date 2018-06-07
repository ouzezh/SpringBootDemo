package com.ozz.springboot.web;

import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.ozz.springboot.dao.SampleDao;

@RestController
public class SampleController {
  private Logger log = LoggerFactory.getLogger(getClass());

  @Autowired
  private SampleDao dao;

  @RequestMapping(value = "/test")
  public Map<String, String> test(String p) {
    log.debug("p=" + p);
    return dao.sevice(p);
  }

  @RequestMapping(value = "/test/{id}", method = RequestMethod.GET)
  public String testPathParam(@PathVariable Long id) {
    return "id=" + id;
  }
}

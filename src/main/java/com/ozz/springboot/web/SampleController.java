package com.ozz.springboot.web;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import com.ozz.springboot.dao.SampleDao;

@RestController
@RequestMapping("/")
public class SampleController {
  private Logger log = LoggerFactory.getLogger(getClass());

  @Autowired
  private SampleDao dao;

  @RequestMapping(value = "/test", method = RequestMethod.GET)
  public String test(String sampleAutosetParam) {
    log.debug("sampleAutosetParam=" + sampleAutosetParam);
    return dao.sevice();
  }

  @RequestMapping(value = "/test/{id}", method = RequestMethod.GET)
  public void testPathParam(@PathVariable String id) {
    log.debug(id);
  }
}

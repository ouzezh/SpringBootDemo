package com.ozz.springboot.component.schedule;

import org.apache.commons.lang3.time.DateFormatUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class SampleSchedule {

  private Logger log = LoggerFactory.getLogger(getClass());
  private static volatile int scheduleCount = 0;

  @Autowired
  private Environment env;

  /**
   * 固定时间间隔执行，单位：毫秒
   */
  @Scheduled(fixedDelay = 60000)
  public void schedule() {
    log.info(String.format("schedule run %d at %s, readConfig: %s", ++scheduleCount,
        DateFormatUtils.format(System.currentTimeMillis(), "yyyy-MM-dd HH:mm:ss"),
        env.getProperty("ozz.sampleConfig")));
  }

//  @Async("xxx")
  @Scheduled(cron = "5 * * * * ?")
  public void asyncSchedule() {
    log.info("execute " + Thread.currentThread().getName());
  }
}

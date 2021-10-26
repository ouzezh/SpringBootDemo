package com.ozz.springboot.config.schedule;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class MySchedule {
  @Autowired
  private Environment env;

  /**
   * 固定时间间隔执行，单位：毫秒
   */
  @Scheduled(fixedDelay = 5000)// 任务结束时间开始计时
//  @Scheduled(fixedRate = 5000)// 任务开始时间开始计时
  public void schedule() {
    log.info(String.format("execute s1 %s start, readConfig: %s", Thread.currentThread().getName(),
        env.getProperty("ozz.myConfig")));
    try {
      Thread.sleep(30000);
    } catch (InterruptedException e) {
      throw new RuntimeException(e);
    }
    log.info(String.format("execute s1 %s end", Thread.currentThread().getName()));
  }

  @Async("asyncTaskExecutor")
  @Scheduled(cron = "0 0 1 * * ?") // 每天一点执行
  public void asyncSchedule() {
    log.info(String.format("execute a1 %s start", Thread.currentThread().getName()));
    try {
      Thread.sleep(20000);
    } catch (InterruptedException e) {
      throw new RuntimeException(e);
    }
    log.info(String.format("execute a1 %s end", Thread.currentThread().getName()));
  }
}

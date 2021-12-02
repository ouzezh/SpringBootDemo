package com.ozz.springboot.config.schedule;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.util.ReflectionUtils;

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
        log.info(String.format("task run in thread %s start, readConfig: %s", Thread.currentThread().getName(),
                env.getProperty("ozz.myConfig")));
        sleep();
        log.info(String.format("task run in thread %s end", Thread.currentThread().getName()));
    }

    @Async("myTaskExecutor")
    @Scheduled(cron = "0 0 1 * * ?") // 每天一点执行
    public void asyncSchedule() {
        log.info(String.format("task run in async thread %s start", Thread.currentThread().getName()));
        sleep();
        log.info(String.format("task run in async thread %s end", Thread.currentThread().getName()));
    }

    private void sleep() {
        try {
            Thread.sleep(30000);
        } catch (Exception e) {
            ReflectionUtils.rethrowRuntimeException(e);
        }
    }
}

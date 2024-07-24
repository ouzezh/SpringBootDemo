package com.ozz.springboot.config.schedule;

import cn.hutool.core.date.DateUtil;
import com.ozz.springboot.util.LogUtil;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.scheduling.TriggerContext;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.scheduling.support.CronSequenceGenerator;
import org.springframework.scheduling.support.CronTrigger;
import org.springframework.scheduling.support.SimpleTriggerContext;
import org.springframework.stereotype.Component;

import java.util.Date;

@Slf4j
@Component
public class MySchedule {
    @Autowired
    private Environment env;

    /**
     * 固定时间间隔执行，单位：毫秒
     */
    @SneakyThrows
    @Scheduled(fixedDelay = 3600000)// 任务结束时间开始计时
//    @Scheduled(fixedRate = 5000)// 任务开始时间开始计时
    public void schedule() {
        LogUtil.log("readProp: " + env.getProperty("ozz.myConfig"));

        CronTrigger cronTrigger = new CronTrigger("0 0/5 * * * ?");
        LogUtil.log(cronTrigger.getExpression());
        SimpleTriggerContext context = new SimpleTriggerContext();
        cronTrigger.nextExecutionTime(context);
        for(int i=0; i<10; i++) {
            Date date = cronTrigger.nextExecutionTime(context);
            LogUtil.log(DateUtil.formatDateTime(date));
            assert date != null;
            context.update(date, date, date);
        }
    }

    @SneakyThrows
    @Async("myTaskExecutor")
    @Scheduled(cron = "0 0 1 * * ?") // 每天一点执行
    public void asyncSchedule() {
        LogUtil.log();
    }
}

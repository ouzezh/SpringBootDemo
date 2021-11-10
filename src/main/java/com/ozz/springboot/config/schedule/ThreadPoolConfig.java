package com.ozz.springboot.config.schedule;

import java.util.concurrent.Executor;
import java.util.concurrent.ThreadPoolExecutor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.task.AsyncTaskExecutor;
import org.springframework.core.task.TaskExecutor;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

@Configuration
@EnableAsync
public class ThreadPoolConfig {

  /**
   * 异步定时任务线程池配置
   */
  @Bean("myTaskExecutor")
  public TaskExecutor myTaskExecutor() {
    ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
    executor.setThreadNamePrefix("myTaskExecutor-");
    executor.setCorePoolSize(1);//线程池维护线程的最少数量（最小线程数）
    executor.setMaxPoolSize(1);//线程池维护线程的最大数量（最大线程数）
    executor.setQueueCapacity(10);//缓存队列
//    对拒绝task的处理策略
//    （ABORT（缺省）：抛出TaskRejectedException异常，然后不执行;DISCARD：不执行，也不抛出异常即放弃该线程;DISCARD_OLDEST：丢弃queue中最旧的那个任务;CALLER_RUNS：不在新线程中执行任务，而是由调用者所在的线程来执行(不再异步)
    executor.setRejectedExecutionHandler(new ThreadPoolExecutor.DiscardPolicy());
    executor.setKeepAliveSeconds(300);//允许的空闲时间，超时线程会被结束直到线程数等于corePoolSize
    executor.initialize();
    return executor;
  }
}

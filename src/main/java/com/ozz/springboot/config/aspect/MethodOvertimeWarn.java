package com.ozz.springboot.config.aspect;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import java.lang.reflect.Method;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Collectors;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.tuple.MutablePair;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;

@Component
@Aspect
@Slf4j
public class MethodOvertimeWarn {
  /**
   * <methodPath, Pair<executeCount, executeTime>>
   */
  ThreadLocal<Map<String, MutablePair<AtomicInteger, AtomicLong>>> localTimeSumMap = new ThreadLocal<>();

  public static long OVERTIME_MILLIS = 600000;

  private static Cache<Integer, Long> cache = CacheBuilder.newBuilder()
      .maximumSize(1)
      .expireAfterWrite(30, TimeUnit.MINUTES)
      .concurrencyLevel(3)
      .build();

  /**
   * 从Redis等分布式存储中读取更新后的超时时间
   */
  private Long refreshTimeLimit() {
    try {
      // TO DO
      return null;
    } catch (Exception e) {
      log.error(null, e);
      return null;
    }
  }

  /**
   * 切面配置
   */
  @Pointcut("execution(public * com.ozz.springboot..*.*(..))")
  public void pointcut() {
  }

  private long getTimeOutMillis() {
    Integer key = 1;
    Long timeLimit = cache.getIfPresent(key);
    if(timeLimit == null) {
      timeLimit = refreshTimeLimit();
      if(timeLimit != null) {
        OVERTIME_MILLIS = timeLimit;
        cache.put(key, timeLimit);
      }
      timeLimit = OVERTIME_MILLIS;
    }
    return timeLimit;
  }

  @Around("pointcut()")
  public Object aroundPointcut(ProceedingJoinPoint pjp) throws Throwable {
    if(getTimeOutMillis() < 0) {
      return pjp.proceed();
    }

    MethodSignature signature = (MethodSignature) pjp.getSignature();
    Method method = signature.getMethod();
    String methodPath = String.format("%s.%s()", pjp.getTarget().getClass().getName(), method.getName());
    Map<String, MutablePair<AtomicInteger, AtomicLong>> timeSumMap = localTimeSumMap.get();

    boolean isRoot = false;
    if(timeSumMap == null) {
        isRoot = true;
        timeSumMap = new LinkedHashMap<>();
        localTimeSumMap.set(timeSumMap);
    }

    MutablePair<AtomicInteger, AtomicLong> v = null;
    if (timeSumMap != null) {
      v = timeSumMap.get(methodPath);
      if (v == null) {
        // 初始化统计信息
        v = new MutablePair<>(new AtomicInteger(), new AtomicLong());
        timeSumMap.put(methodPath, v);
      }
    }

    // 执行方法
    long ts = System.currentTimeMillis();
    Object object = pjp.proceed();
    ts = System.currentTimeMillis() - ts;

    if(timeSumMap != null) {
      // 执行次数
      v.getLeft().incrementAndGet();
      // 执行时间
      v.getRight().addAndGet(ts);
    }

    // toString
    if (isRoot) {
      localTimeSumMap.remove();
      if(ts >= getTimeOutMillis()) {
        String res = timeSumMap.entrySet().stream()
            .map(item -> String.format("%s: %s, %s", item.getKey(), item.getValue().getLeft(), item.getValue().getRight()))
            .collect(Collectors.joining("\n"));
        log.debug(String.format("-start->\n%s\n<-end-\n", res));
      }
    }
    return object;
  }

}

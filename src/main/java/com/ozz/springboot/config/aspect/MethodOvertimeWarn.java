package com.ozz.springboot.config.aspect;

import com.ozz.springboot.service.MyMailService;
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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
@Aspect
@Slf4j
public class MethodOvertimeWarn {

  @Value("${ozz.warn.overtimeMillis}")
  private long OVERTIME_MILLIS = -1;

  /**
   * <methodPath, Pair<executeCount, executeTime>>
   */
  ThreadLocal<Map<String, MutablePair<AtomicInteger, AtomicLong>>> localTimeSumMap = new ThreadLocal<>();
  @Autowired
  MyMailService myMailService;

  /**
   * 切面配置
   */
  @Pointcut("execution(public * com.ozz.springboot..*.*(..)) || execution(public * org.springframework.data.redis..*.*(..))")
  public void pointcut() {
  }

  /**
   * 超时时间，可以针对特定请求修改限时设置
   *
   * @return 超时时间设置
   */
  private long getOvertimeMillis() {
    return OVERTIME_MILLIS;
  }

  private boolean isIgnoreMail(String methodPath, Class<?> aClass) {
    // 忽略邮件警报，由于处理超时时使用了邮件，防止发生死循环
    return myMailService == null || methodPath.startsWith(MyMailService.class.getName()) || aClass
        .isAssignableFrom(MyMailService.class);
  }

  @Around("pointcut()")
  public Object aroundPointcut(ProceedingJoinPoint pjp) throws Throwable {
    Map<String, MutablePair<AtomicInteger, AtomicLong>> timeSumMap = localTimeSumMap.get();
    if (timeSumMap == null && getOvertimeMillis() < 0) {
      return pjp.proceed();
    }

    String methodPath = String.format("%s.%s()", pjp.getTarget().getClass().getName(),
        ((MethodSignature) pjp.getSignature()).getMethod().getName());
    boolean isRoot = false;
    long ts = 0;
    Throwable te = null;
    try {
      if (timeSumMap == null) {
        isRoot = true;
        timeSumMap = new LinkedHashMap<>();
        localTimeSumMap.set(timeSumMap);
      }

      MutablePair<AtomicInteger, AtomicLong> v = timeSumMap.get(methodPath);
      if (v == null) {
        // 初始化统计信息
        v = new MutablePair<>(new AtomicInteger(), new AtomicLong());
        timeSumMap.put(methodPath, v);
      }

      // 执行方法
      ts = System.nanoTime();
      Object object = null;
      try {
        object = pjp.proceed();
      } catch (Throwable e) {
        te = e;
      }
      ts = System.nanoTime() - ts;

      // 执行次数
      v.getLeft().incrementAndGet();
      // 执行时间
      v.getRight().addAndGet(ts);

      // toString
      if (isRoot) {
        ts = TimeUnit.MILLISECONDS.convert(ts, TimeUnit.NANOSECONDS);
        if (ts >= getOvertimeMillis()) {
          printInfo(timeSumMap, te, !isIgnoreMail(methodPath, pjp.getTarget().getClass()));
        }
      }

      if (te != null) {
        throw te;
      }
      return object;
    } finally {
      if(isRoot) {
        localTimeSumMap.remove();
      }
    }
  }

  private void printInfo(Map<String, MutablePair<AtomicInteger, AtomicLong>> timeSumMap,
      Throwable te, boolean sendMail) {
    String res = timeSumMap.entrySet().stream()
        .map(item -> String
            .format("%s: count=%s, time=[%s]", item.getKey(), item.getValue().getLeft(),
                getTimeStringByMillis(TimeUnit.MILLISECONDS
                    .convert(item.getValue().getRight().longValue(), TimeUnit.NANOSECONDS))))
        .collect(Collectors.joining("\n"));
    log.warn(String.format("%n--start-->%n%s%n<--end--%n", res));
    if (sendMail) {
      try {
        myMailService.sendErrorMail(te==null ? "运行超时" : "运行超时+异常", res, te);
      } catch (Exception e) {
        log.error(null, e);
      }
    }
  }

  public static String getTimeStringByMillis(long millis) {
    String[] modUnits = {"天", "时", "分", "秒", "毫秒"};
    long[] mods = {24, 60, 60, 1000, 1};

    if (millis <= 0) {
      return millis + modUnits[modUnits.length - 1];
    }

    long mod = 1;
    for (long t : mods) {
      mod = mod * t;
    }

    long tmpTime = millis;
    StringBuilder timeString = new StringBuilder();
    int bit = 0;
    for (int i = 0; i < modUnits.length && bit <= 2; i++) {
      long curr = tmpTime / mod;
      tmpTime = tmpTime % mod;
      mod = mod / mods[i];
      if (curr > 0) {
        bit++;
        timeString.append(curr).append(modUnits[i]);
      }
      if (bit > 0) {
        bit++;
      }
    }

    return timeString.toString();
  }
}

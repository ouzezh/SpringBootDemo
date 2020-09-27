package com.ozz.springboot.config.aspect;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Collectors;
import org.apache.commons.lang3.tuple.MutablePair;
import org.apache.commons.lang3.tuple.Pair;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;

@Component
@Aspect
public class MethodTimeMonitor {
  // <methodPath, Pair<executeCount, executeTime>>
  ThreadLocal<Map<String, MutablePair<AtomicInteger, AtomicLong>>> localSumInfo = new ThreadLocal<>();//<key,<timestamp,<count,time>>>

  public static List<Pair<String, Long>> timeLimit;

  public static void main(String[] args) {
    System.out.println(MethodTimeMonitor.timeLimit);
  }
  static {
    timeLimit = new ArrayList<Pair<String, Long>>() {{
      add(Pair.of("com.ozz.springboot", 3000L));
      add(Pair.of("com.ozz.springboot.web.MyRestController.test", 3000L));
      add(Pair.of("com.ozz", 3000L));
    }};
    timeLimit.sort(Comparator.reverseOrder());
  }

  @Pointcut("execution(public * com.ozz.springboot..*.*(..))")
  public void pointcut() {
    // do nothing
  }

  @Around("pointcut()")
  public Object aroundPointcut(ProceedingJoinPoint pjp) throws Throwable {
    MethodSignature signature = (MethodSignature) pjp.getSignature();
    Method method = signature.getMethod();

    Map<String, MutablePair<AtomicInteger, AtomicLong>> sumInfo = localSumInfo.get();
    String methodPath = String.format("%s.%s", pjp.getTarget().getClass().getName(), method.getName());

    long timeLimit = -1;
    if(sumInfo == null) {
      timeLimit = getTimeLimit(methodPath);
      if (timeLimit >= 0) {
        sumInfo = new LinkedHashMap<>();
        localSumInfo.set(sumInfo);
      }
    }

    MutablePair<AtomicInteger, AtomicLong> v = null;
    if (sumInfo != null) {
      v = sumInfo.get(methodPath);
      if (v == null) {
        // 初始化统计信息
        v = new MutablePair<>(new AtomicInteger(), new AtomicLong());
        sumInfo.put(methodPath, v);
      }
    }

    // 执行方法
    long ts = System.currentTimeMillis();
    Object object = pjp.proceed();
    ts = System.currentTimeMillis() - ts;

    if(sumInfo != null) {
      // 执行次数
      v.getLeft().incrementAndGet();
      // 执行时间
      v.getRight().addAndGet(ts);
    }

    // toString
    if (timeLimit >= 0) {
      localSumInfo.remove();

      boolean timeout = sumInfo.entrySet().stream().anyMatch(t -> {
        long l = getTimeLimit(t.getKey());
        return l>=0 && l<=t.getValue().getValue().get();
      });

      if(timeout) {
        String res = sumInfo.entrySet().stream()
            .map(item -> String.format("%s: %s, %s", item.getKey(), item.getValue().getLeft(), item.getValue().getRight()))
            .collect(Collectors.joining("\n"));
        System.out.printf("==>\n%s\n<==", res);
      }
    }
    return object;
  }

  private long getTimeLimit(String methodPath) {
    Optional<Pair<String, Long>> l = timeLimit.stream().filter(t -> methodPath.startsWith(t.getKey())).findFirst();
    return l.isPresent() ? l.get().getValue() : -1;
  }

}

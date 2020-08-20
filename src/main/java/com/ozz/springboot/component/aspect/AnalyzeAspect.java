package com.ozz.springboot.component.aspect;

import java.lang.reflect.Method;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;
import org.apache.commons.lang3.tuple.MutablePair;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * 请求执行时间统计
 */
@Component
@Aspect
public class AnalyzeAspect {

  private Logger log = LoggerFactory.getLogger(getClass());

  @Pointcut("execution(public * cn.xdf.ucan.payroll..*.*(..))")
  public void pointcut() {
    // do nothing
  }

  // <methodPath, <order, <executeCount, executeTime>>>
  ThreadLocal<Map<String, MutablePair<Integer, MutablePair<Integer, Long>>>> sumTime = new ThreadLocal<>();//<key,<timestamp,<count,time>>>
  ThreadLocal<AtomicInteger> invokeOrder = new ThreadLocal<>();

  @Around("pointcut()")
  public Object aroundPointcut(ProceedingJoinPoint pjp) throws Throwable {
    MethodSignature signature = (MethodSignature) pjp.getSignature();
    Method method = signature.getMethod();
    String methodPath = Optional.ofNullable(method.getAnnotation(RequestMapping.class)).map(item -> item.value()[0]).orElse(null);
    if (methodPath == null) {
      methodPath = Optional.ofNullable(method.getAnnotation(GetMapping.class)).map(item -> item.value()[0]).orElse(null);
    }
    if (methodPath == null) {
      methodPath = Optional.ofNullable(method.getAnnotation(PostMapping.class)).map(item -> item.value()[0]).orElse(null);
    }
    if (methodPath != null) {
      sumTime.set(new HashMap<>());
      invokeOrder.set(new AtomicInteger(0));
    }

    // 执行方法
    long ts = System.currentTimeMillis();
    Object object = pjp.proceed();
    ts = System.currentTimeMillis() - ts;

    Map<String, MutablePair<Integer, MutablePair<Integer, Long>>> map = sumTime.get();
    if (map != null) {
      // 方法路径
      String key = String.format("%s.%s", signature.getDeclaringType().getName(), method.getName());
      MutablePair<Integer, MutablePair<Integer, Long>> v = map.get(key);
      if (v == null) {
        v = new MutablePair<>(0, new MutablePair<>(0, new Long(0)));
        map.put(key, v);
      }
      // 排序
      v.setLeft(invokeOrder.get().incrementAndGet());
      // 执行次数
      v.getRight().setLeft(v.getRight().getLeft() + 1);
      // 执行时间
      v.getRight().setRight(v.getRight().getRight() + ts);
    }

    // toString
    if (methodPath != null) {
      sumTime.remove();
      invokeOrder.remove();
      String res = map.entrySet().stream()
          .sorted(new Comparator<Entry<String, MutablePair<Integer, MutablePair<Integer, Long>>>>() {
            @Override
            public int compare(Entry<String, MutablePair<Integer, MutablePair<Integer, Long>>> o1,
                Entry<String, MutablePair<Integer, MutablePair<Integer, Long>>> o2) {
              return o1.getValue().getLeft().compareTo(o2.getValue().getLeft());
            }
          }).map(item -> String.format("%s: %s, %s", item.getKey(), item.getValue().getRight().getLeft(), item.getValue().getRight().getRight()))
          .collect(Collectors.joining("\n"));
      System.out.println(String.format("---->%s\n%s\n<----", methodPath, res));
    }
    return object;
  }

}

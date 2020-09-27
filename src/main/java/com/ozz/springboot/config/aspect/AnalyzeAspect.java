package com.ozz.springboot.config.aspect;

import java.lang.reflect.Method;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;
import org.apache.commons.lang3.tuple.MutablePair;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Component
@Aspect
public class AnalyzeAspect {
  // <methodPath, Pair<order, Pair<executeCount, executeTime>>>
  ThreadLocal<Map<String, MutablePair<Integer, MutablePair<Integer, Long>>>> localSumInfo = new ThreadLocal<>();//<key,<timestamp,<count,time>>>
  ThreadLocal<Integer> localInvokeOrder = new ThreadLocal<>();

  @Pointcut("execution(public * com.ozz.springboot..*.*(..))")
  public void pointcut() {
    // do nothing
  }

  private String getInvokerPath(ProceedingJoinPoint pjp, Method method) {
    String methodPath = Optional.ofNullable(method.getAnnotation(RequestMapping.class)).map(item -> item.value()[0]).orElse(null);
    if (methodPath == null) {
      methodPath = Optional.ofNullable(method.getAnnotation(GetMapping.class)).map(item -> item.value()[0]).orElse(null);
    }
    if (methodPath == null) {
      methodPath = Optional.ofNullable(method.getAnnotation(PostMapping.class)).map(item -> item.value()[0]).orElse(null);
    }
    return methodPath;
  }

  @Around("pointcut()")
  public Object aroundPointcut(ProceedingJoinPoint pjp) throws Throwable {
    MethodSignature signature = (MethodSignature) pjp.getSignature();
    Method method = signature.getMethod();

    Map<String, MutablePair<Integer, MutablePair<Integer, Long>>> sumInfo = localSumInfo.get();
    String invokerPath = null;
    if(sumInfo == null) {
      invokerPath = getInvokerPath(pjp, method);
      if (invokerPath != null) {
        localSumInfo.set(new LinkedHashMap<>());
        localInvokeOrder.set(1);
      }
    }

    String key = null;
    MutablePair<Integer, MutablePair<Integer, Long>> v = null;
    if (sumInfo != null) {
      // 调用路径
      key = String.format("%s.%s", pjp.getTarget().getClass().getName(), method.getName());
      v = sumInfo.get(key);
      if (v == null) {
        // 初始化统计信息
        v = new MutablePair<>(0, new MutablePair<>(0, new Long(0)));
        sumInfo.put(key, v);
        // 排序
        v.setLeft(localInvokeOrder.get());
        localInvokeOrder.set(localInvokeOrder.get() + 1);
      }
    }

    // 执行方法
    long ts = System.currentTimeMillis();
    Object object = pjp.proceed();
    ts = System.currentTimeMillis() - ts;

    if(sumInfo != null) {
      // 执行次数
      v.getRight().setLeft(v.getRight().getLeft() + 1);
      // 执行时间
      v.getRight().setRight(v.getRight().getRight() + ts);
    }

    // toString
    if (invokerPath != null) {
      localSumInfo.remove();
      localInvokeOrder.remove();
      String res = sumInfo.entrySet().stream()
          .map(item -> String.format("%s: %s, %s", item.getKey(), item.getValue().getRight().getLeft(), item.getValue().getRight().getRight()))
          .collect(Collectors.joining("\n"));
      System.out.println(String.format("---->%s\n%s\n<----", invokerPath, res));
    }
    return object;
  }

}

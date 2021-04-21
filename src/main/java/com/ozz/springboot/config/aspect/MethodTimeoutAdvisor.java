package com.ozz.springboot.config.aspect;

import com.ozz.springboot.service.MyMailService;
import java.lang.reflect.Method;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Collectors;
import lombok.Setter;
import lombok.experimental.Accessors;
import lombok.extern.slf4j.Slf4j;
import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import org.apache.commons.lang3.tuple.MutablePair;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.aop.support.StaticMethodMatcherPointcutAdvisor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
@Aspect
@Slf4j
public class MethodTimeoutAdvisor extends StaticMethodMatcherPointcutAdvisor {
  private static long timeoutMillis = 60000L;
  private static String timeoutPackage = "com.ozz";
  @Override
  public boolean matches(Method method, Class<?> targetClass) {
//    if (java.lang.reflect.Modifier.isFinal(targetClass.getModifiers())) {
//      return false;
//    }
    if (targetClass.getName().startsWith(timeoutPackage)) {
      return true;
    }
    return false;
  }

  public MethodTimeoutAdvisor() {
    setAdvice(new MyMethodInterceptor());
  }
  @Autowired
  public void setOvertimeMillis(MyMailService myMailService) {
    ((MyMethodInterceptor)getAdvice()).setMyMailService(myMailService);
  }

  @Setter
  @Accessors(chain = true)
  protected static class MyMethodInterceptor implements MethodInterceptor {
    private MyMailService myMailService;

    /**
     * <methodPath, Pair<executeCount, executeTime>>
     */
    ThreadLocal<Map<String, MutablePair<AtomicInteger, AtomicLong>>> localTimeSumMap = new ThreadLocal<>();

    /**
     * 超时时间，可以针对特定请求修改限时设置
     *
     * @return 超时时间设置
     */
    private long getTimeoutMillis() {
      return timeoutMillis;
    }

    private boolean ifSendMail(String methodPath, Class<?> aClass) {
      // 忽略邮件警报，由于处理超时时使用了邮件，防止发生死循环
      return !(myMailService == null || methodPath.startsWith(MyMailService.class.getName())
          || aClass.isAssignableFrom(MyMailService.class));
    }

    private void printInfo(Map<String, MutablePair<AtomicInteger, AtomicLong>> timeSumMap,
        Throwable te, boolean ifSendMail) {
      String msg = timeSumMap.entrySet().stream().map(item -> String.format("[%s][%s]%s",
          getTimeStringByMillis(
              TimeUnit.NANOSECONDS.toMillis(item.getValue().getRight().longValue())),
          item.getValue().getLeft(), item.getKey()))
          .collect(Collectors.joining("\n", "\n--start-->\n", "\n<--end--\n"));
      if (ifSendMail) {
        log.warn(msg);
        try {
          myMailService.sendErrorMail(te == null ? "运行超时" : "运行超时+异常", msg, te);
        } catch (Exception e) {
          log.error(null, e);
        }
      } else {
        log.info(msg);
      }
    }

    public static String getTimeStringByMillis(long millis) {
      String[] modUnits = {"分", "秒", "毫秒"};
      long[] mods = {60, 1000, 1};

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

    @Override
    public Object invoke(MethodInvocation invocation) throws Throwable {
      Map<String, MutablePair<AtomicInteger, AtomicLong>> timeSumMap = localTimeSumMap.get();
      long overtimeMillis = getTimeoutMillis();

      String methodPath = String
          .format("%s.%s()", invocation.getMethod().getDeclaringClass().getName(),
              invocation.getMethod().getName());

      boolean isRoot = false;
      long ts = 0;
      Throwable te = null;
      try {
        if (timeSumMap == null) {
          if (overtimeMillis < 0 || !methodPath.startsWith(timeoutPackage)) {
            return invocation.proceed();
          }
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
          object = invocation.proceed();
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
          ts = TimeUnit.NANOSECONDS.toMillis(ts);
          if (ts >= overtimeMillis) {
            printInfo(timeSumMap, te,
                ifSendMail(methodPath, invocation.getMethod().getDeclaringClass()));
          }
        }

        if (te != null) {
          throw te;
        }
        return object;
      } finally {
        if (isRoot) {
          localTimeSumMap.remove();
        }
      }
    }
  }
}

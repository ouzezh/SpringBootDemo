package com.ozz.springboot.config.aspect;

import java.lang.reflect.Method;
import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.aop.support.StaticMethodMatcherPointcutAdvisor;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class MyStaticMethodMatcherPointcutAdvisor extends StaticMethodMatcherPointcutAdvisor {
  public MyStaticMethodMatcherPointcutAdvisor() {
    setAdvice(new MyMethodInterceptor());
  }

  @Override
  public boolean matches(Method method, Class<?> targetClass) {
//    if(java.lang.reflect.Modifier.isFinal(targetClass.getModifiers())) {
//      return false;
//    }
    if(targetClass.getName().startsWith("com.ozz")) {
      System.out.println(String.format("---->%s.%s()", targetClass.getName(), method.getName()));
      return true;
    }
    return false;
  }

  public static class MyMethodInterceptor implements MethodInterceptor {
    @Override
    public Object invoke(MethodInvocation invocation) throws Throwable {
      System.out.println(String.format("---->%s()", invocation.getMethod().getName()));
      return invocation.proceed();
    }
  }
}

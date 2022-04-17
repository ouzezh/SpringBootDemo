package com.ozz.springboot.config.aspect;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestMapping;

import java.lang.reflect.Method;
import java.util.Arrays;

@Slf4j
@Component
@Aspect
public class AnnotationAspect {

  @Around("@annotation(org.springframework.web.bind.annotation.RequestMapping)")
  public Object aroundAnnotation(ProceedingJoinPoint pjp) throws Throwable {
    log.debug("@Aspect:@Around@annotation begin");

    Method method = getMethod(pjp);
    RequestMapping ann = method.getAnnotation(RequestMapping.class);
    log.debug(String.format("@Aspect:@Around@annotation read field of annotation: %s %s", method.getName(), Arrays.toString(ann.value())));

    Object object = pjp.proceed();
    log.debug("@Aspect:@Around@annotation end");
    return object;
  }

  private Method getMethod(ProceedingJoinPoint pjp) {
    MethodSignature signature = (MethodSignature) pjp.getSignature();
    return signature.getMethod();
  }

}

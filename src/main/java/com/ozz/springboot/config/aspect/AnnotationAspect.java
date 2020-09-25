package com.ozz.springboot.config.aspect;

import java.lang.reflect.Method;
import java.util.Arrays;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestMapping;

@Component
@Aspect
public class AnnotationAspect {
  private Logger log = LoggerFactory.getLogger(getClass());

  @Around("@annotation(org.springframework.web.bind.annotation.RequestMapping)")
  public Object aroundAnnotation(ProceedingJoinPoint pjp) throws Throwable {
    log.debug("@Aspect:@Around@annotation begin");

    MethodSignature signature = (MethodSignature) pjp.getSignature();
    Method method = signature.getMethod();
    RequestMapping ann = method.getAnnotation(RequestMapping.class);
    log.debug(String.format("@Aspect:@Around@annotation read field of annotation: %s %s", method.getName(), Arrays.toString(ann.value())));

    Object object = pjp.proceed();
    log.debug("@Aspect:@Around@annotation end");
    return object;
  }
}

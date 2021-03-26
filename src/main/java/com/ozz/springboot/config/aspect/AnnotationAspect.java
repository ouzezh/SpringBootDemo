package com.ozz.springboot.config.aspect;

import java.lang.reflect.Method;
import java.util.Arrays;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestMapping;

@Slf4j
@Component
@Aspect
public class AnnotationAspect {

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

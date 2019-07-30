package com.ozz.springboot.component.aspect;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 * 顺序: before->pointcut()->after->afterReturning
 */
@Component
@Aspect
public class PointcutAspect {
  private Logger log = LoggerFactory.getLogger(getClass());

  /**
   * 第一个*代表返回类型不限，第二个*表示所有类，第三个*表示所有方法，第一个..表示当前包及子包，第二个..点表示方法里的参数不限
   */
  @Pointcut("execution(public * com.ozz.springboot.web..*.*(..))")
  public void pointcut() {
    // do nothing
  }

  @Before("pointcut()")
  public void before() {
    log.debug("@Aspect:@Before");
  }

  @After("pointcut()")
  public void after() {
    log.debug("@Aspect:@After");
  }

  @AfterReturning("pointcut()")
  public void doAfter() {
    log.debug("@Aspect:@AfterReturning");
  }

  @AfterThrowing("pointcut()")
  public void doAfterThrow() {
    log.debug("@Aspect:@AfterThrowing");
  }

  @Around("pointcut()")
  public Object aroundPointcut(ProceedingJoinPoint pjp) throws Throwable {
    log.debug("@Aspect:@Around@Pointcut begin");
    Object object = pjp.proceed();
    log.debug("@Aspect:@Around@Pointcut end");
    return object;
  }
}

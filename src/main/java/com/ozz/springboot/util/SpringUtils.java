package com.ozz.springboot.util;

import java.util.Map;
import java.util.Set;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.swing.*;

import cn.hutool.extra.spring.SpringUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.core.env.Profiles;
import org.springframework.stereotype.Component;
import org.springframework.util.ReflectionUtils;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.mvc.method.RequestMappingInfo;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;
import org.springframework.web.util.pattern.PathPattern;

@Slf4j
@Component
public class SpringUtils {
    /**
     * get impl at runtime
     */
    public static <T> T getBean(Class<T> beanClass) {
        return SpringUtil.getBean(beanClass);
    }

    public static <T> T getBean(String beanName) {
        return (T) SpringUtil.getBean(beanName);
    }

    public static String[] getActiveProfiles() {
        return SpringUtil.getActiveProfiles();
    }
    public static boolean acceptsProfiles(String... profiles) {
        return SpringUtil.getApplicationContext().getEnvironment().acceptsProfiles(Profiles.of(profiles));
    }

    public static void shutdownDelay(long millis) {
        new Thread(() -> {
            try {
                Thread.sleep(millis);
            } catch (InterruptedException e) {
                ReflectionUtils.rethrowRuntimeException(e);
            }
            int exitCode = SpringApplication.exit(SpringUtil.getApplicationContext(), () -> 0);
//            System.exit(exitCode);
        }).start();
    }

    @PostConstruct
    void init() {
        RequestMappingHandlerMapping mapping = SpringUtils.getBean(RequestMappingHandlerMapping.class);
        Map<RequestMappingInfo, HandlerMethod> handlerMethods =  mapping.getHandlerMethods();
        log.info("-- start print request mapping -->");
        AtomicInteger no = new AtomicInteger(0);
        handlerMethods.forEach((requestMappingInfo, handlerMethod) -> {
            try {
//                String url = requestMappingInfo.getPathPatternsCondition().getPatterns().stream().map(PathPattern::getPatternString).collect(Collectors.joining(" | "));
                String url = requestMappingInfo.getPatternsCondition().getPatterns().stream().collect(Collectors.joining(" | "));
                Set<RequestMethod> requestMethods = requestMappingInfo.getMethodsCondition().getMethods();
                String method = requestMethods.isEmpty() ? "*" : requestMethods.stream().map(RequestMethod::toString).collect(Collectors.joining(","));
                log.info(String.format("%d:\t%s\t%s", no.incrementAndGet(), method, url));
            } catch (Exception e) {
                ReflectionUtils.rethrowRuntimeException(e);
            }
        });
        log.info("<-- end print request mapping -->");
    }

    @PreDestroy
    public void destroy() {
        LogUtil.log();
    }
}

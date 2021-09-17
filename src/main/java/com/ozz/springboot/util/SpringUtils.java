package com.ozz.springboot.util;

import java.util.Map;
import java.util.Set;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;
import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.mvc.method.RequestMappingInfo;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;

@Slf4j
@Component
public class SpringUtils implements ApplicationContextAware {
    private static ApplicationContext context;

    @Override public void setApplicationContext(ApplicationContext applicationContext) {
        SpringUtils.context = applicationContext;
    }

    /**
     * get impl at runtime
     */
    public static <T> T getBean(Class<T> beanClass) {
        return context.getBean(beanClass);
    }

    public static <T> T getBean(String beanName) {
        return (T) context.getBean(beanName);
    }

    public String[] getActiveProfiles() {
        return context.getEnvironment().getActiveProfiles();
    }

    public static void shutdownDelay(long millis) {
        new Thread(() -> {
            try {
                Thread.sleep(millis);
            } catch (InterruptedException e) {
            }
            int exitCode = SpringApplication.exit(context, () -> 0);
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
            String url = requestMappingInfo.getPatternsCondition().getPatterns().stream().findFirst().get();
            Set<RequestMethod> requestMethods = requestMappingInfo.getMethodsCondition().getMethods();
            String method = requestMethods.isEmpty() ? "*" : requestMethods.stream().map(RequestMethod::toString).collect(Collectors.joining(","));
            log.info(String.format("%d:\t%s\t%s", no.incrementAndGet(), method, url));
        });
        log.info("<-- end print request mapping -->");
    }

    @PreDestroy
    public void destroy() {
        System.out.println("Callback triggered - @PreDestroy.");
    }
}

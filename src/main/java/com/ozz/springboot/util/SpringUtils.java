package com.ozz.springboot.util;

import org.springframework.boot.SpringApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

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
            System.exit(exitCode);
        }).start();
    }
}

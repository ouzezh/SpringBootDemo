package com.ozz.springboot.config.context;

import java.io.IOException;
import javax.naming.OperationNotSupportedException;
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

    public static int shutdown() throws IOException, OperationNotSupportedException {
        int exitCode = SpringApplication.exit(context, () -> 0);
        System.exit(exitCode);
        return exitCode;
    }
}

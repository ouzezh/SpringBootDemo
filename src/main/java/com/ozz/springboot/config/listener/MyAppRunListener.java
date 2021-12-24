package com.ozz.springboot.config.listener;

import com.ozz.springboot.exception.ErrorException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.ConfigurableBootstrapContext;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.SpringApplicationRunListener;
import org.springframework.cglib.core.Local;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.core.annotation.Order;
import org.springframework.core.env.ConfigurableEnvironment;

/**
 * 监听 Springboot 启动流程
 * <p>
 * 需配置 resources/META-INF/spring.factories
 */
@Order(0)
@Slf4j
public class MyAppRunListener implements SpringApplicationRunListener {
    public MyAppRunListener(final SpringApplication application, final String[] args) {
    }

    @Override
    public void starting(ConfigurableBootstrapContext bootstrapContext) {
        log();
    }

    @Override
    public void environmentPrepared(ConfigurableBootstrapContext bootstrapContext,
                                    ConfigurableEnvironment environment) {
        log();
        if (environment.getActiveProfiles().length == 0) {
            throw new ErrorException("spring.profiles.active is null");
        }
    }

    @Override
    public void contextPrepared(ConfigurableApplicationContext context) {
        log();
    }

    @Override
    public void contextLoaded(ConfigurableApplicationContext context) {
        log();
    }

    @Override
    public void started(ConfigurableApplicationContext context) {
        log();
    }

    @Override
    public void running(ConfigurableApplicationContext context) {
        log();
    }

    @Override
    public void failed(ConfigurableApplicationContext context, Throwable exception) {
        log();
    }

    private void log() {
        log.info("{} {}", getClass().getName(), Thread.currentThread().getStackTrace()[2].getMethodName());
    }
}

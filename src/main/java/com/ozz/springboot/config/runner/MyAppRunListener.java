package com.ozz.springboot.config.runner;

import com.ozz.springboot.exception.ErrorException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.ConfigurableBootstrapContext;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.SpringApplicationRunListener;
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
        log("starting");
    }

    @Override
    public void environmentPrepared(ConfigurableBootstrapContext bootstrapContext,
                                    ConfigurableEnvironment environment) {
        if (environment.getActiveProfiles().length == 0) {
            throw new ErrorException("spring.profiles.active is null");
        }
        log("environmentPrepared");
    }

    @Override
    public void contextPrepared(ConfigurableApplicationContext context) {
        log("contextPrepared");
    }

    @Override
    public void contextLoaded(ConfigurableApplicationContext context) {
        log("contextLoaded");
    }

    @Override
    public void started(ConfigurableApplicationContext context) {
        log("started");
    }

    @Override
    public void running(ConfigurableApplicationContext context) {
        log("running");
    }

    @Override
    public void failed(ConfigurableApplicationContext context, Throwable exception) {
        log("failed");
    }

    private void log(String msg) {
        log.info("{} {}", getClass().getName(), msg);
    }
}

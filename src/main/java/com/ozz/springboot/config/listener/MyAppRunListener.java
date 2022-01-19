package com.ozz.springboot.config.listener;

import com.ozz.springboot.exception.ErrorException;
import com.ozz.springboot.util.LogUtil;
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
public class MyAppRunListener implements SpringApplicationRunListener {
    public MyAppRunListener(final SpringApplication application, final String[] args) {
    }

    @Override
    public void starting(ConfigurableBootstrapContext bootstrapContext) {
        LogUtil.log();
    }

    @Override
    public void environmentPrepared(ConfigurableBootstrapContext bootstrapContext,
                                    ConfigurableEnvironment environment) {
        LogUtil.log();
    }

    @Override
    public void contextPrepared(ConfigurableApplicationContext context) {
        LogUtil.log();
    }

    @Override
    public void contextLoaded(ConfigurableApplicationContext context) {
        LogUtil.log();
    }

    @Override
    public void failed(ConfigurableApplicationContext context, Throwable exception) {
        LogUtil.log();
    }

}

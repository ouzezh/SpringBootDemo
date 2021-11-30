package com.ozz.springboot.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanFactoryPostProcessor;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.context.EnvironmentAware;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.core.PriorityOrdered;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.core.env.Environment;
import org.springframework.core.env.MutablePropertySources;
import org.springframework.core.env.PropertySource;

/**
 * 配置文件覆盖配置中心的配置
 */
@Configuration
@Slf4j
public class MyPropertyProcessor implements BeanFactoryPostProcessor, EnvironmentAware, PriorityOrdered {

    private ConfigurableEnvironment environment;

    @Override
    public void postProcessBeanFactory(ConfigurableListableBeanFactory beanFactory) throws BeansException {
        MutablePropertySources psList = environment.getPropertySources();
        for (PropertySource<?> ps : psList) {
            if (ps.getName().matches(".*config/application.*\\.(yml|properties).*")) {
                log.info("change property source priority to first {}", ps.getName());
                psList.remove(ps.getName());
                psList.addFirst(ps);
            }
        }
    }

    @Override
    public void setEnvironment(Environment environment) {
        if(environment.getActiveProfiles().length==0) {
            throw new RuntimeException("active profile not set, please use --spring.profiles.active=dev and restart");
        }
        this.environment = (ConfigurableEnvironment) environment;
    }

    @Override
    public int getOrder() {
        return Ordered.HIGHEST_PRECEDENCE;
    }

}
package com.ozz.springboot.config.listener;

import com.github.xiaoymin.knife4j.core.util.StrUtil;
import com.ozz.springboot.exception.ErrorException;
import com.ozz.springboot.util.MdcUtil;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.MDC;
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

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * 配置文件覆盖配置中心的配置
 */
@Configuration
@Slf4j
public class MyPropertyProcessor implements BeanFactoryPostProcessor, EnvironmentAware, PriorityOrdered {

    private ConfigurableEnvironment environment;

    @Override
    public void postProcessBeanFactory(ConfigurableListableBeanFactory beanFactory) throws BeansException {
//        beanFactory.registerResolvableDependency(RocketMQTemplate.class, new RocketMQTemplate());
    }

    @Override
    public void setEnvironment(Environment environment) {
        this.environment = (ConfigurableEnvironment) environment;
        sortPropertySource();

        if (environment.getActiveProfiles().length == 0) {
            throw new ErrorException("spring.profiles.active is null");
        }
    }

    private void sortPropertySource() {
        MutablePropertySources psList = this.environment.getPropertySources();
        LinkedList<PropertySource<?>> list = new LinkedList<>();
        for (PropertySource<?> ps : psList) {
            if (ps.getName().matches(".*config/.*\\.(yml|properties).*")) {
                list.addFirst(ps);
            }
        }
        for (PropertySource<?> ps : list) {
            System.out.println(StrUtil.format("MyPropertySource: addFirst {}", ps.getName()));
            psList.remove(ps.getName());
            psList.addFirst(ps);
        }
    }

    @Override
    public int getOrder() {
        return Ordered.HIGHEST_PRECEDENCE;
    }

}
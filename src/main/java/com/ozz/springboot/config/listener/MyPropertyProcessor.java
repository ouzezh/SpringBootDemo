package com.ozz.springboot.config.listener;

import com.ozz.springboot.util.Constant;
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
        MDC.put(Constant.TRACE_ID, "SortPropertySource");
        try {
            MutablePropertySources psList = environment.getPropertySources();
            Iterator<PropertySource<?>> it = psList.iterator();
            List<PropertySource<?>> list = new ArrayList<>();
            PropertySource<?> ps;
            while(it.hasNext()) {
                ps = it.next();
                if (ps.getName().matches(".*config/application.*\\.(yml|properties).*")) {
                    psList.remove(ps.getName());
                    list.add(ps);
                }
            }
            it = list.iterator();
            if (it.hasNext()) {
                ps = it.next();
                log.info("modify \"{}\" first", ps.getName());
                psList.addFirst(ps);
                String before;
                while (it.hasNext()) {
                    before = ps.getName();
                    ps = it.next();
                    log.info("modify \"{}\" after \"{}\"", ps.getName(), before);
                    psList.addAfter(before, ps);
                }
            }
        } finally {
            MDC.remove(Constant.TRACE_ID);
        }
    }

    @Override
    public void setEnvironment(Environment environment) {
        this.environment = (ConfigurableEnvironment) environment;
    }

    @Override
    public int getOrder() {
        return Ordered.HIGHEST_PRECEDENCE;
    }

}
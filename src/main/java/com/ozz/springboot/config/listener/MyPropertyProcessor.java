package com.ozz.springboot.config.listener;

import cn.hutool.core.lang.Assert;
import cn.hutool.core.util.StrUtil;
import cn.hutool.log.StaticLog;
import com.ozz.springboot.exception.ErrorException;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanFactoryPostProcessor;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.boot.env.OriginTrackedMapPropertySource;
import org.springframework.boot.env.PropertiesPropertySourceLoader;
import org.springframework.context.EnvironmentAware;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.core.PriorityOrdered;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.core.env.Environment;
import org.springframework.core.env.MutablePropertySources;
import org.springframework.core.env.PropertySource;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;

import java.util.LinkedList;
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
        // 增加生产环境配置
        addProPropertySource(this.environment.getPropertySources());

        // 配置排序
        MutablePropertySources psList = this.environment.getPropertySources();
        LinkedList<PropertySource<?>> list = new LinkedList<>();
        for (PropertySource<?> ps : psList) {
            if (ps.getName().matches(".*config/.*\\.(yml|properties).*")) {
                list.addFirst(ps);
            }
        }
        for (PropertySource<?> ps : list) {
            StaticLog.info(StrUtil.format("MyPropertySource: addFirst {}", ps.getName()));
            psList.remove(ps.getName());
            psList.addFirst(ps);
        }
    }

    @SneakyThrows
    private void addProPropertySource(MutablePropertySources psList) {
        String name = "/config/application-PRO.properties";
        Resource resource = new ClassPathResource(name);
        PropertiesPropertySourceLoader loader = new PropertiesPropertySourceLoader();
        List<PropertySource<?>> tmpList = loader.load(name, resource);
        Assert.isTrue(tmpList.size() == 1);
        PropertySource<?> tmp = tmpList.get(0);
//        OriginTrackedMapPropertySource m = (OriginTrackedMapPropertySource) tmp;
//        m.getSource().put("db.port", dbPort);
        psList.addFirst(tmp);
    }

    @Override
    public int getOrder() {
        return Ordered.HIGHEST_PRECEDENCE;
    }

}
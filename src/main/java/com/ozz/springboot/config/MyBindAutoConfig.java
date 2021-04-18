package com.ozz.springboot.config;

import com.ozz.springboot.MyBindConfig;
import com.ozz.springboot.config.runner.MyAppRunner;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConditionalOnClass({MyAppRunner.class})// 当类路径中存在这个类时才会配置该类
@EnableConfigurationProperties({MyBindConfig.class})
public class MyBindAutoConfig {

}

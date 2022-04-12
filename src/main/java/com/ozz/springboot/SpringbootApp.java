package com.ozz.springboot;

import cn.hutool.core.util.StrUtil;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.core.env.Environment;
import org.springframework.scheduling.annotation.EnableScheduling;

import java.net.InetAddress;
import java.util.Optional;

@Slf4j
@SpringBootApplication
@EnableScheduling
public class SpringbootApp {

    @SneakyThrows
    public static void main(String[] args) {
        ConfigurableApplicationContext app = SpringApplication.run(SpringbootApp.class, args);

        Environment env = app.getEnvironment();
        String ip = InetAddress.getLocalHost().getHostAddress();
        String port = Optional.ofNullable(env.getProperty("server.port")).orElse("8080");
        String path = Optional.ofNullable(env.getProperty("server.servlet.context-path")).orElse(StrUtil.EMPTY);
        log.info("\n----------------------------------------------------------\n\t" +
                "Application is running! Access URLs:\n\t" +
                "Local: \t\thttp://localhost:" + port + path + "/\n\t" +
                "External: \thttp://" + ip + ":" + port + path + "/\n\t" +
                "Swagger文档: \thttp://" + ip + ":" + port + path + "/doc.html\n" +
                "----------------------------------------------------------");
    }

}

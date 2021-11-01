package com.ozz.springboot;

import com.google.common.base.Strings;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.scheduling.annotation.EnableScheduling;

@Slf4j
@SpringBootApplication
@EnableScheduling
public class SpringbootApp {

    public static void main(String[] args) {
        new SpringApplicationBuilder().sources(SpringbootApp.class).profiles(getProfile(args)).run(args);
    }

    private static String getProfile(String[] args) {
        String prop = "--spring.profiles.active=";
        String profile = args.length > 0 ? args[0].replace(prop, "") : null;
        if(Strings.isNullOrEmpty(profile)) {
            log.info("profile config {} not set, active default profile dev", prop);
            return "dev";
        }
        return profile;
    }

}

package com.ozz.springboot.config;

import com.ozz.springboot.util.SpringUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import springfox.bean.validators.configuration.BeanValidatorPluginsConfiguration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.oas.annotations.EnableOpenApi;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;

/**
 * swagger 配置
 */
@Configuration
@EnableOpenApi
@Import(BeanValidatorPluginsConfiguration.class)
//@ConditionalOnProperty(prefix = "ozz.swagger.config", name = "enabled", matchIfMissing = false)// 此配置不可用，
@Slf4j
public class Knife4jConfig {
    @Bean
    public Docket defaultApi() {
        boolean enable = SpringUtils.acceptsProfiles("dev");
        log.info("swagger knife4j 开始加载，启用状态：" + enable);
        return new Docket(DocumentationType.OAS_30)
                .apiInfo(apiInfo())
//                .directModelSubstitute(LocalDateTime.class, String.class)// 替换文档中接口类型
                .enable(enable)// 可区分不同环境关闭
                .select()
//                .apis(RequestHandlerSelectors.basePackage("com.ozz"))
                .apis(RequestHandlerSelectors.withClassAnnotation(Api.class))
                .apis(RequestHandlerSelectors.withMethodAnnotation(ApiOperation.class))
                .paths(PathSelectors.any())
                .build();
    }

    //基本信息的配置，信息会在api文档上显示
    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title("我的文档")
                .description("我的描述")
                .version("0.1")
                .build();
    }
}
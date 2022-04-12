package com.ozz.springboot.config;

import com.ozz.springboot.util.SpringUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.oas.annotations.EnableOpenApi;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Configuration
@EnableOpenApi
//@ConditionalOnProperty(prefix = "swagger.config", name = "enabled", havingValue = "true", matchIfMissing = true)
public class SwaggerConfig {
    @Bean
    public Docket createRestApi() {
        return new Docket(DocumentationType.OAS_30)
                .apiInfo(apiInfo())
//                .directModelSubstitute(LocalDateTime.class, String.class)// 替换文档中接口类型
                .enable(SpringUtils.acceptsProfiles("dev"))// 可区分不同环境关闭
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
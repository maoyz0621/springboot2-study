/**
 * Copyright 2018 asiainfo Inc.
 **/
package com.myz.swagger.common.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * Swagger2用来记录Restful api接口信息
 * 访问地址：http://localhost:8081/swagger-ui.html
 *
 * @author maoyz on 2018/8/26
 * @version: v1.0
 */
@Configuration
@EnableSwagger2
public class Swagger2 {
    public static final String SWAGGER_SCAN_BASE_PACKAGE = "com.myz.swagger.common.web";
    public static final String VERSION = "1.0.0";

    /**
     * apis()过滤的接口
     * @return
     */
    @Bean
    public Docket createRestApi() {
        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(apiInfo())
                .select()
                .apis(RequestHandlerSelectors.basePackage(SWAGGER_SCAN_BASE_PACKAGE))
                .paths(PathSelectors.any())
                .build();
    }

    /**
     * 创建API文档模板
     *
     * @return
     */
    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title("Spring Boot中使用Swagger2构建RESTful APIs")
                .description("初次使用Swagger2构建RESTful APIs")
                // .termsOfServiceUrl("http://blog.didispace.com/")
                .contact(new Contact("maoyuezhong", "", "mao245890416@qq.com"))
                .version(VERSION)
                .build();
    }
}

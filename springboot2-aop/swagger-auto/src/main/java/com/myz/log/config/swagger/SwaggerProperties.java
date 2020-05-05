/**
 * Copyright 2019 Inc.
 **/
package com.myz.log.config.swagger;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @author maoyz0621 on 19-6-29
 * @version: v1.0
 */
@Data
@ConfigurationProperties(prefix = SwaggerProperties.SWAGGER_PREFIX)
public class SwaggerProperties {

    public static final String SWAGGER_PREFIX = "swagger";

    /**
     * 是否开启swagger
     **/
    private Boolean enabled;

    /**
     * 解析包路径
     */
    private String basePackage = "";

    private String title = "";

    private String description = "";

    private String version = "";

    private Contact contact = new Contact();

    private String termsOfServiceUrl;

    @Data
    @NoArgsConstructor
    public static class Contact {

        /**
         * 联系人
         **/
        private String name = "";
        /**
         * 联系人url
         **/
        private String url = "";
        /**
         * 联系人email
         **/
        private String email = "";

    }


}

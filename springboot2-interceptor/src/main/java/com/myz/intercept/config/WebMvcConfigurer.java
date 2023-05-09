/**
 * Copyright 2022 Inc.
 **/
package com.myz.intercept.config;


import com.myz.intercept.interceptor.AuthenticationInterceptor;
import com.myz.intercept.interceptor.BaseInterceptor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;

import java.util.List;

/**
 * @author maoyz0621 on 2022/4/4
 * @version v1.0
 */
@Configuration
public class WebMvcConfigurer implements org.springframework.web.servlet.config.annotation.WebMvcConfigurer {

    /**
     * 无需登录url
     */
    @Value("${intercept.excludePaths}")
    private List<String> excludePaths;

    /**
     * 定义拦截器InterceptorRegistry
     */
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        // 多个拦截器组成一个拦截器链
        // addPathPatterns 用于添加拦截规则
        // excludePathPatterns 用于排除拦截规则
        registry.addInterceptor(new BaseInterceptor())
                .addPathPatterns("/base/**");
        registry.addInterceptor(new AuthenticationInterceptor())
                .addPathPatterns("/auth/**")
                .excludePathPatterns(excludePaths);
    }
}
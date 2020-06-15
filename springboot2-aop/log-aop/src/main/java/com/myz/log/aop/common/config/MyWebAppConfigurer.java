/**
 * Copyright 2018 asiainfo Inc.
 **/
package com.myz.log.aop.common.config;



import com.myz.log.aop.common.interceptor.BaseInterceptor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;

/**
 * Spring 5.0 以后WebMvcConfigurerAdapter过时,使用WebMvcConfigurationSupport
 *
 * @author maoyz on 18-11-27
 * @version: v1.0
 */
@Configuration
@Slf4j
public class MyWebAppConfigurer extends WebMvcConfigurationSupport {

    // 以下WebMvcConfigurerAdapter 比较常用的重写接口
    // /** 解决跨域问题 **/
    // public void addCorsMappings(CorsRegistry registry) ;
    // /** 添加拦截器 **/
    // void addInterceptors(InterceptorRegistry registry);
    // /** 这里配置视图解析器 **/
    // void configureViewResolvers(ViewResolverRegistry registry);
    // /** 配置内容裁决的一些选项 **/
    // void configureContentNegotiation(ContentNegotiationConfigurer
    // configurer);
    // /** 视图跳转控制器 **/
    // void addViewControllers(ViewControllerRegistry registry);
    // /** 静态资源处理 **/
    // void addResourceHandlers(ResourceHandlerRegistry registry);
    // /** 默认静态资源处理器 **/
    // void configureDefaultServletHandling(DefaultServletHandlerConfigurer
    // configurer);

    /**
     * 系统拦截器
     */
    @Bean
    public BaseInterceptor baseInterceptor() {
        return new BaseInterceptor();
    }

    /**
     * 表示这些配置的表示静态文件所处路径， 不用拦截
     */
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        log.debug("MyWebAppConfigurer 执行 addResourceHandlers() *******************");

        registry.addResourceHandler("/static/**")
                .addResourceLocations("classpath:/static/");
        registry.addResourceHandler("/templates/**")
                .addResourceLocations("classpath:/templates/");
        super.addResourceHandlers(registry);
    }

    /**
     * 定义拦截器InterceptorRegistry
     * 多个拦截器组成一个拦截器链
     * addPathPatterns 用于添加拦截规则 ， 先把所有路径都加入拦截， 再一个个排除
     * excludePathPatterns 表示该路径不用拦截
     */
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        log.debug("MyWebAppConfigurer 执行 addInterceptors() *******************");

        String excludes[] = {"/images/**"};
        registry.addInterceptor(baseInterceptor()).addPathPatterns("/**").excludePathPatterns(excludes);
        super.addInterceptors(registry);
    }
}

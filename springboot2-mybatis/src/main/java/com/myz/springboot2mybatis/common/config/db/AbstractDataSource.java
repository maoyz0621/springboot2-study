/**
 * Copyright 2018 asiainfo Inc.
 **/
package com.myz.springboot2mybatis.common.config.db;


import com.alibaba.druid.pool.DruidDataSource;
import com.alibaba.druid.spring.boot.autoconfigure.DruidDataSourceBuilder;
import com.alibaba.druid.support.http.StatViewServlet;
import com.alibaba.druid.support.http.WebStatFilter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.PropertySource;

import java.util.HashMap;
import java.util.Map;

/**
 * 数据源公共属性
 * 对于其他数据库链接池的配置是可以改动的
 * 主要配置连接池公共属性
 * 配置属性 @ConfigurationProperties(prefix = "spring.datasource")
 *
 * @author maoyz on 18-10-26
 * @version: v1.0
 */
@PropertySource("classpath:config/jdbc.properties")
public abstract class AbstractDataSource {

    static final String MAPPER_SLAVE_PACKAGE = "classpath:mapper/slave/*.xml";
    static final String MAPPER_MASTER_PACKAGE = "classpath:mapper/master/*.xml";

    @Value("${spring.datasource.druid.stat-view-servlet.enabled}")
    private String enabled;

    @Value("${spring.datasource.druid.stat-view-servlet.login-username}")
    private String loginUserName;

    @Value("${spring.datasource.druid.stat-view-servlet.login-password}")
    private String loginPassword;

    @Value("${spring.datasource.druid.stat-view-servlet.url-pattern}")
    private String urlPattern;

    @Value("${spring.datasource.druid.stat-view-servlet.reset-enable}")
    private String resetEnable;

    @Value("${spring.datasource.druid.stat-view-servlet.allow}")
    private String allow;

    @Value("${spring.datasource.druid.stat-view-servlet.deny}")
    private String deny;


    /**
     * Druid公共配置信息
     */
    @ConfigurationProperties(prefix = "spring.datasource.druid")
    protected DruidDataSource createDataSource(String username, String password, String url) {
        DruidDataSource dataSource = DruidDataSourceBuilder.create().build();
        dataSource.setUsername(username);
        dataSource.setPassword(password);
        dataSource.setUrl(url);
        return dataSource;
    }

    /**
     * 配置Druid的监控
     * 1、配置一个管理后台的Servlet
     */
    @Bean
    public ServletRegistrationBean druidServlet() {
        ServletRegistrationBean servletRegistrationBean = new ServletRegistrationBean();
        servletRegistrationBean.setServlet(new StatViewServlet());
        servletRegistrationBean.addUrlMappings("/druid/*");
        Map<String, String> initParameters = new HashMap<String, String>();
        // 用户名
        initParameters.put("loginUsername", loginUserName);
        // 密码
        initParameters.put("loginPassword", loginPassword);
        // 禁用HTML页面上的“Reset All”功能
        initParameters.put("resetEnable", resetEnable);
        // IP白名单 (没有配置或者为空，则允许所有访问)
        initParameters.put("allow", allow);
        // IP黑名单 (存在共同时，deny优先于allow)
        initParameters.put("deny", deny);
        servletRegistrationBean.setInitParameters(initParameters);
        return servletRegistrationBean;
    }

    /**
     * 2、配置一个web监控的filter
     */
    @Bean
    public FilterRegistrationBean filterRegistrationBean() {
        FilterRegistrationBean filterRegistrationBean = new FilterRegistrationBean();
        filterRegistrationBean.setFilter(new WebStatFilter());
        filterRegistrationBean.addUrlPatterns("/*");
        filterRegistrationBean.addInitParameter("exclusions", "*.js,*.gif,*.jpg,*.png,*.css,*.ico,/druid/*");
        return filterRegistrationBean;
    }
}

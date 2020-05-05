/**
 * Copyright 2018 asiainfo Inc.
 **/
package com.myz.springboot2.config.db;


import com.alibaba.druid.pool.DruidDataSource;
import com.alibaba.druid.pool.xa.DruidXADataSource;
import com.alibaba.druid.spring.boot.autoconfigure.DruidDataSourceBuilder;
import com.alibaba.druid.support.http.StatViewServlet;
import com.alibaba.druid.support.http.WebStatFilter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jta.atomikos.AtomikosDataSourceBean;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;

import javax.sql.DataSource;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

/**
 * 数据源公共属性
 *   对于其他数据库链接池的配置是可以改动的
 *   主要配置连接池公共属性
 *
 * @author maoyz on 18-10-26
 * @version: v1.0
 */
@PropertySource("classpath:config/jdbc.properties")
public abstract class AbstractDataSource {

    protected static final String MAPPER_LOCATION = "classpath:mapper/*.xml";

    @Value("${spring.datasource.type}")
    protected Class<? extends DataSource> dataSourceType;

    private static final String DB_CONFIG_FILE = "config/jdbc.properties";

    @Value("${spring.datasource.type}")
    private String type;
    @Value("${spring.datasource.driver-class-name}")
    private String driverClassName;


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
     * atomic多数据源分布式事务
     */
    protected DataSource getDataSource(DataSourceConfig config, String prefix, String dataSourceName) {
        Properties prop = null;
        try {
            prop = build(config, prefix);
        } catch (IOException e) {
            e.printStackTrace();
        }
        DruidXADataSource dataSource = new DruidXADataSource();
        //dataSource.DruidXADataSource
        AtomikosDataSourceBean ds = new AtomikosDataSourceBean();
        ds.setXaDataSource(dataSource);
        ds.setUniqueResourceName(dataSourceName);
        ds.setXaProperties(prop);
        return ds;
    }

    /**
     * 主要针对druid数据库链接池
     */
    protected Properties build(DataSourceConfig config, String prefix) throws IOException {
        Resource resource = new ClassPathResource(DB_CONFIG_FILE);
        Properties prop = new Properties();
        Properties jdbcProp = new Properties();
        jdbcProp.load(resource.getInputStream());
        prop.put("druid.url", jdbcProp.getProperty(prefix + "url"));
        prop.put("druid.username", jdbcProp.getProperty(prefix + "username"));
        prop.put("druid.password", jdbcProp.getProperty(prefix + "password"));

        prop.put("type", type);
        prop.put("druid.driverClassName", driverClassName);
        prop.put("druid.initialSize", config.getInitialSize());
        prop.put("druid.maxActive", config.getMaxActive());
        prop.put("druid.minIdle", config.getMinIdle());
        prop.put("druid.maxWait", config.getMaxWait());
        //prop.put("druid.poolPreparedStatements", config.getPoolPreparedStatements());
        prop.put("druid.maxPoolPreparedStatementPerConnectionSize", config.getMaxPoolPreparedStatementPerConnectionSize());
        prop.put("druid.validationQuery", config.getValidationQuery());
        //prop.put("validationQueryTimeout", validationQueryTimeout);
        prop.put("druid.testOnBorrow", config.getTestOnBorrow());
        prop.put("druid.testOnReturn", config.getTestOnReturn());
        prop.put("druid.testWhileIdle", config.getTestWhileIdle());
        prop.put("druid.timeBetweenEvictionRunsMillis", config.getTimeBetweenEvictionRunsMillis());
        prop.put("druid.minEvictableIdleTimeMillis", config.getMinEvictableIdleTimeMillis());
        prop.put("druid.filters", config.getFilter());
        return prop;
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
        initParameters.put("loginUsername", "admin");// 用户名
        initParameters.put("loginPassword", "admin");// 密码
        initParameters.put("resetEnable", "false");// 禁用HTML页面上的“Reset All”功能
        initParameters.put("allow", ""); // IP白名单 (没有配置或者为空，则允许所有访问)
        initParameters.put("deny", "192.168.20.38");// IP黑名单 (存在共同时，deny优先于allow)
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

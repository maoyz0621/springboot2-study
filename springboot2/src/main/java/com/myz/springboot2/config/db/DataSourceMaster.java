/**
 * Copyright 2018 asiainfo Inc.
 **/
package com.myz.springboot2.config.db;


import org.apache.ibatis.session.ExecutorType;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.*;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;

import javax.sql.DataSource;

/**
 * 主数据源
 * Spring Boot 2.X 版本不再支持配置继承，多数据源的话每个数据源的所有配置都需要单独配置，否则配置不会生效
 *
 * @author maoyz on 18-10-26
 * @version: v1.0
 */
@Configuration
@MapperScan(basePackages = DataSourceMaster.BASE_PACKAGE, sqlSessionTemplateRef = "baseSqlSessionTemplate")
public class DataSourceMaster extends AbstractDataSource {

    static final String BASE_PACKAGE = "com.myz.springboot2.mapper.business1";

    @Value("${spring.datasource.druid.first.master.username}")
    private String username;
    @Value("${spring.datasource.druid.first.master.password}")
    private String password;
    @Value("${spring.datasource.druid.first.master.url}")
    private String url;

    /**
     * dataSource
     */
    //@Bean(name = "masterDataSource")
    //@Primary
    //public DataSource setDataSource() {
    //    return createDataSource(username, password, url);
    //}

    @Bean(name = "masterDataSource")
    @ConfigurationProperties(prefix = "spring.datasource.druid.first.master")
    public DataSource dataSource(){
        return DataSourceBuilder.create().build();
    }

    /**
     * 事务管理
     */
    @Bean(name = "baseTransactionManager")
    @Primary
    public DataSourceTransactionManager setTransactionManager(@Qualifier("masterDataSource") DataSource dataSource) {
        DataSourceTransactionManager transactionManager = new DataSourceTransactionManager();
        transactionManager.setDataSource(dataSource);
        return transactionManager;
    }

    /**
     * SqlSessionFactory
     */
    @Bean(name = "baseSqlSessionFactory")
    @Primary
    public SqlSessionFactory setSqlSessionFactory(@Qualifier("masterDataSource") DataSource dataSource) throws Exception {
        SqlSessionFactoryBean bean = new SqlSessionFactoryBean();
        bean.setDataSource(dataSource);
        bean.setMapperLocations(new PathMatchingResourcePatternResolver().getResources(MAPPER_LOCATION));
        return bean.getObject();
    }

    /**
     * 执行批量插入
     */
    @Bean(name = "baseSqlSessionTemplate")
    @Primary
    public SqlSessionTemplate setSqlSessionTemplate(@Qualifier("baseSqlSessionFactory") SqlSessionFactory sqlSessionFactory) throws Exception {
        return new SqlSessionTemplate(sqlSessionFactory, ExecutorType.BATCH);
    }

}

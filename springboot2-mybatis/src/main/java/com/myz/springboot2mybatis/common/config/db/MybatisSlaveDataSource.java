/**
 * Copyright 2018 asiainfo Inc.
 **/
package com.myz.springboot2mybatis.common.config.db;


import org.apache.ibatis.session.ExecutorType;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;

import javax.sql.DataSource;

/**
 * 从数据源
 * Spring Boot 2.X 版本不再支持配置继承，多数据源的话每个数据源的所有配置都需要单独配置，否则配置不会生效
 *
 * @author maoyz on 18-10-26
 * @version: v1.0
 */
@Configuration
@MapperScan(basePackages = MybatisSlaveDataSource.SLAVE_PACKAGE, sqlSessionTemplateRef = "slaveSqlSessionTemplate")
public class MybatisSlaveDataSource extends AbstractDataSource {

    static final String SLAVE_PACKAGE = "com.myz.springboot2mybatis.module.mapper.slave";

    @Value("${spring.datasource.druid.slave.username}")
    private String username;
    @Value("${spring.datasource.druid.slave.password}")
    private String password;
    @Value("${spring.datasource.druid.slave.url}")
    private String url;

    /**
     * dataSource
     */
    @Bean(name = "slaveDataSource")
    public DataSource setDataSource() {
        return createDataSource(username, password, url);
    }

    /**
     * 事务管理
     */
    @Bean(name = "slaveTransactionManager")
    public DataSourceTransactionManager setTransactionManager(@Qualifier("slaveDataSource") DataSource dataSource) {
        DataSourceTransactionManager transactionManager = new DataSourceTransactionManager();
        transactionManager.setDataSource(dataSource);
        return transactionManager;
    }

    /**
     * SqlSessionFactory
     */
    @Bean(name = "slaveSqlSessionFactory")
    public SqlSessionFactory setSqlSessionFactory(@Qualifier("slaveDataSource") DataSource dataSource) throws Exception {
        SqlSessionFactoryBean bean = new SqlSessionFactoryBean();
        bean.setDataSource(dataSource);
        bean.setMapperLocations(new PathMatchingResourcePatternResolver().getResources(MAPPER_SLAVE_PACKAGE));
        return bean.getObject();
    }

    /**
     * 执行批量插入
     */
    @Bean(name = "slaveSqlSessionTemplate")
    public SqlSessionTemplate setSqlSessionTemplate(@Qualifier("slaveSqlSessionFactory") SqlSessionFactory sqlSessionFactory) throws Exception {
        return new SqlSessionTemplate(sqlSessionFactory, ExecutorType.BATCH);
    }
}

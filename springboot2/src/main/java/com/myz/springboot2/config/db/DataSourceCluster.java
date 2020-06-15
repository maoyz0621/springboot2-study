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
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;

import javax.sql.DataSource;

/**
 * 从数据源
 * 读取额外配置文件@PropertySource()
 *
 * @author maoyz on 18-10-26
 * @version: v1.0
 */
@Configuration
@MapperScan(basePackages = DataSourceCluster.BASE_PACKAGE, sqlSessionTemplateRef = "sqlSessionTemplateCluster")
public class DataSourceCluster extends AbstractDataSource {

    static final String BASE_PACKAGE = "com.myz.springboot2.mapper.business2";

    @Value("${spring.datasource.druid.first.cluster.username}")
    private String username;
    @Value("${spring.datasource.druid.first.cluster.password}")
    private String password;
    @Value("${spring.datasource.druid.first.cluster.url}")
    private String url;


    //@Bean(name = "clusterDataSource")
    //public DataSource setDataSource() {
    //    return createDataSource(username, password, url);
    //}

    @Bean(name = "clusterDataSource")
    @ConfigurationProperties(prefix = "spring.datasource.druid.first.cluster")
    public DataSource dataSource(){
        return DataSourceBuilder.create().build();
    }

    @Bean(name = "tansactionManagerCluster")
    public DataSourceTransactionManager setTransactionManager(@Qualifier("clusterDataSource") DataSource dataSource) {
        return new DataSourceTransactionManager(dataSource);
    }

    @Bean(name = "sqlSessionFactoryCluster")
    public SqlSessionFactory setSqlSessionFactory(@Qualifier("clusterDataSource") DataSource dataSource) throws Exception {
        SqlSessionFactoryBean bean = new SqlSessionFactoryBean();
        bean.setDataSource(dataSource);
        bean.setMapperLocations(new PathMatchingResourcePatternResolver().getResources(MAPPER_LOCATION));
        return bean.getObject();
    }

    @Bean(name = "sqlSessionTemplateCluster")
    public SqlSessionTemplate setSqlSessionTemplate(@Qualifier("sqlSessionFactoryCluster") SqlSessionFactory sqlSessionFactory) throws Exception {
        return new SqlSessionTemplate(sqlSessionFactory, ExecutorType.BATCH);
    }
}

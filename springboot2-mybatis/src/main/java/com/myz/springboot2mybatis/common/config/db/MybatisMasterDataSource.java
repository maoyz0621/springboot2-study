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
import org.springframework.context.annotation.Primary;
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
@MapperScan(basePackages = MybatisMasterDataSource.MASTER_PACKAGE, sqlSessionTemplateRef = "masterSqlSessionTemplate")
public class MybatisMasterDataSource extends AbstractDataSource {

    static final String MASTER_PACKAGE = "com.myz.springboot2mybatis.module.mapper.master";


    @Value("${spring.datasource.druid.master.username}")
    private String username;
    @Value("${spring.datasource.druid.master.password}")
    private String password;
    @Value("${spring.datasource.druid.master.url}")
    private String url;

    /**
     * dataSource
     */
    @Bean(name = "masterDataSource")
    @Primary
    public DataSource setDataSource() {
        return createDataSource(username, password, url);
    }

    /**
     * 事务管理
     */
    @Bean(name = "masterTransactionManager")
    @Primary
    public DataSourceTransactionManager setTransactionManager(@Qualifier("masterDataSource") DataSource dataSource) {
        DataSourceTransactionManager transactionManager = new DataSourceTransactionManager();
        transactionManager.setDataSource(dataSource);
        return transactionManager;
    }

    /**
     * SqlSessionFactory
     */
    @Bean(name = "masterSqlSessionFactory")
    @Primary
    public SqlSessionFactory setSqlSessionFactory(@Qualifier("masterDataSource") DataSource dataSource) throws Exception {
        SqlSessionFactoryBean bean = new SqlSessionFactoryBean();
        bean.setDataSource(dataSource);
        bean.setMapperLocations(new PathMatchingResourcePatternResolver().getResources(MAPPER_MASTER_PACKAGE));
        return bean.getObject();
    }

    /**
     * 执行批量插入
     */
    @Bean(name = "masterSqlSessionTemplate")
    @Primary
    public SqlSessionTemplate setSqlSessionTemplate(@Qualifier("masterSqlSessionFactory") SqlSessionFactory sqlSessionFactory) throws Exception {
        return new SqlSessionTemplate(sqlSessionFactory, ExecutorType.BATCH);
    }


    /**
     * 分页插件配置方法2
     *
     * pagehelper:
     *   helper-dialect: mysql
     *   reasonable: true
     *   support-methods-arguments: true
     *   params: count=countSql
     *
     *
     *   helper-dialect：指定数据库，不指定的话会默认自动检测数据库类型
     *   reasonable：是否启用分页合理化。如果启用，当pagenum<1时，会自动查询第一页的数据，当pagenum>pages时，自动查询最后一页数据；不启用的，以上两种情况都会返回空数据
     *   support-methods-arguments：默认值false，分页插件会从查询方法的参数值中，自动根据上面 params 配置的字段中取值，查找到合适的值时就会自动分页。
     *   params：用于从对象中根据属性名取值， 可以配置 pageNum,pageSize,count,pageSizeZero,reasonable，不配置映射的用默认值， 默认值为pageNum=pageNum;pageSize=pageSize;count=countSql;reasonable=reasonable;pageSizeZero=pageSizeZero
     */
    // @Bean
    // public PageHelper pageHelper() {
    //     //分页插件
    //     PageHelper pageHelper = new PageHelper();
    //
    //     Properties properties = new Properties();
    //     properties.setProperty("reasonable", "true");
    //     properties.setProperty("supportMethodsArguments", "true");
    //     properties.setProperty("returnPageInfo", "check");
    //     properties.setProperty("params", "count=countSql");
    //     pageHelper.setProperties(properties);
    //
    //     return pageHelper;
    // }


}

/**
 * Copyright 2018 asiainfo Inc.
 **/
package com.myz.springboot2.config.db;

import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.annotation.MapperScan;
import org.mybatis.spring.boot.autoconfigure.SpringBootVFS;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.env.Environment;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;

/**
 * @author maoyz on 18-10-26
 * @version: v1.0
 */
//@Configuration
//@MapperScan(basePackages = MyBatisConfigA.BASE_PACKAGE, sqlSessionTemplateRef = "sqlSessionTemplate")
public class MyBatisConfigA extends AbstractDataSource {
    /**
     * mapper模式下的接口层
     */
    static final String BASE_PACKAGE = "com.myz.springboot2.mapper.business1";

    private static final String MAPPER_LOCATION = "classpath:mapper/*.xml";

    /**
     * 数据源
     */
    @Primary
    @Bean(name = "dataSourceOne")
    public DataSource dataSourceOne(DataSourceConfig config) {
        String prefix = "spring.datasource.druid.second.master.";
        return getDataSource(config, prefix, "master");
    }

    @Bean(name = "sqlSessionFactoryOne")
    public SqlSessionFactory sqlSessionFactoryOne(@Qualifier("dataSourceOne") DataSource dataSource) throws Exception {
        return createSqlSessionFactory(dataSource);
    }

    @Bean(name = "sqlSessionTemplate")
    public SqlSessionFactory sqlSessionTemplate(@Qualifier("sqlSessionFactoryOne") SqlSessionFactory factoryOne, @Qualifier("sqlSessionFactoryTwo") SqlSessionFactory factoryTwo) throws Exception {
        Map<Object, SqlSessionFactory> sqlSessionFactoryMap = new HashMap<>();
        sqlSessionFactoryMap.put("one", factoryOne);
        sqlSessionFactoryMap.put("two", factoryTwo);

        SqlSessionFactory customSqlSessionTemplate = (SqlSessionFactory) new SqlSessionFactoryBean();
        //customSqlSessionTemplate.setTargetSqlSessionFactorys(sqlSessionFactoryMap);
        return ((SqlSessionFactoryBean) customSqlSessionTemplate).getObject();
    }

    /**
     * 创建数据源
     */
    private SqlSessionFactory createSqlSessionFactory(DataSource dataSource) throws Exception {
        SqlSessionFactoryBean bean = new SqlSessionFactoryBean();
        bean.setDataSource(dataSource);
        bean.setVfs(SpringBootVFS.class);
        bean.setMapperLocations(new PathMatchingResourcePatternResolver().getResources(MAPPER_LOCATION));
        return bean.getObject();
    }
}

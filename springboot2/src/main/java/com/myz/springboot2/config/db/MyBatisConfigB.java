/**
 * Copyright 2018 asiainfo Inc.
 **/
package com.myz.springboot2.config.db;

import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.mybatis.spring.annotation.MapperScan;
import org.mybatis.spring.boot.autoconfigure.SpringBootVFS;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.env.Environment;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;

/**
 * @author maoyz on 18-10-26
 * @version: v1.0
 */
//@Configuration
//@MapperScan(basePackages = MyBatisConfigB.BASE_PACKAGE, sqlSessionTemplateRef = "sqlSessionTemplate")
public class MyBatisConfigB extends AbstractDataSource {

    static final String BASE_PACKAGE = "com.myz.springboot2.mapper.business2";

    static final String MAPPER_LOCATION = "classpath:mapping/*.xml";


    @Bean(name = "dataSourceTwo")
    public DataSource dataSourceTwo(DataSourceConfig config) {
        String prefix = "spring.datasource.druid.second.cluster.";
        return getDataSource(config, prefix, "cluster");
    }

    @Bean(name = "sqlSessionFactoryOne")
    public SqlSessionFactory sqlSessionFactoryOne(@Qualifier("dataSourceOne") DataSource dataSource)
            throws Exception {
        return createSqlSessionFactory(dataSource);
    }

    @Bean(name = "sqlSessionFactoryTwo")
    public SqlSessionFactory sqlSessionFactoryTwo(@Qualifier("dataSourceTwo") DataSource dataSource)
            throws Exception {
        return createSqlSessionFactory(dataSource);
    }


    /**
     * sqlSessionFactory
     */
    @Bean(name = "sqlSessionTemplate")
    public SqlSessionTemplate sqlSessionTemplate(@Qualifier("sqlSessionFactoryOne") SqlSessionFactory factoryOne, @Qualifier("sqlSessionFactoryTwo") SqlSessionFactory factoryTwo) throws Exception {
        Map<Object, SqlSessionFactory> sqlSessionFactoryMap = new HashMap<>();
        sqlSessionFactoryMap.put("one", factoryOne);
        sqlSessionFactoryMap.put("two", factoryTwo);

        SqlSessionTemplate customSqlSessionTemplate = new SqlSessionTemplate(factoryOne);
        //customSqlSessionTemplate.setTargetSqlSessionFactorys(sqlSessionFactoryMap);
        return customSqlSessionTemplate;
    }

    /**
     * 创建数据源
     *
     * @param dataSource
     * @return
     */
    private SqlSessionFactory createSqlSessionFactory(DataSource dataSource) throws Exception {
        SqlSessionFactoryBean bean = new SqlSessionFactoryBean();
        bean.setDataSource(dataSource);
        bean.setVfs(SpringBootVFS.class);
        bean.setMapperLocations(new PathMatchingResourcePatternResolver().getResources(MAPPER_LOCATION));
        return bean.getObject();
    }
}

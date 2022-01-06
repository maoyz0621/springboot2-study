/**
 * Copyright 2021 Inc.
 **/
package com.myz.springboot2.mybatisplus34.configuration;

import com.baomidou.mybatisplus.annotation.DbType;
import com.baomidou.mybatisplus.autoconfigure.ConfigurationCustomizer;
import com.baomidou.mybatisplus.autoconfigure.MybatisPlusAutoConfiguration;
import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import com.baomidou.mybatisplus.core.injector.DefaultSqlInjector;
import com.baomidou.mybatisplus.core.injector.ISqlInjector;
import com.baomidou.mybatisplus.extension.plugins.MybatisPlusInterceptor;
import com.baomidou.mybatisplus.extension.plugins.handler.TenantLineHandler;
import com.baomidou.mybatisplus.extension.plugins.inner.OptimisticLockerInnerInterceptor;
import com.baomidou.mybatisplus.extension.plugins.inner.PaginationInnerInterceptor;
import com.baomidou.mybatisplus.extension.plugins.inner.TenantLineInnerInterceptor;
import com.myz.springboot2.mybatisplus34.configuration.handler.MyMetaObjectHandler;
import net.sf.jsqlparser.expression.Expression;
import net.sf.jsqlparser.expression.LongValue;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 注意:
 * <p>
 * 使用多个功能需要注意顺序关系,建议使用如下顺序
 * <p>
 * 多租户,动态表名
 * 分页,乐观锁
 * sql性能规范,防止全表更新与删除
 * 总结: 对sql进行单次改造的优先放入,不对sql进行改造的最后放入
 * <p>
 * #使用方式(以分页插件举例)
 *
 * @author maoyz0621 on 2021/12/6
 * @version v1.0
 * @see MybatisPlusAutoConfiguration
 */
@Configuration
@EnableConfigurationProperties(TenantProperties.class)
@MapperScan("com.myz.springboot2.mybatisplus34.mapper")
public class MyBatisPlus34Config {

    private final TenantProperties tenantProperties;

    public MyBatisPlus34Config(TenantProperties tenantProperties) {
        this.tenantProperties = tenantProperties;
    }

    /**
     * 注册逻辑删除标志,对应
     * logic-delete-value: 1 # 逻辑已删除值(默认为 1)
     * logic-not-delete-value: 0 # 逻辑未删除值(默认为 0)
     *
     * @return
     */
    @Bean
    public ISqlInjector sqlInjector() {
        return new DefaultSqlInjector();
    }

    /**
     * 租户信息
     * @return
     */
    @Bean
    public TenantLineInnerInterceptor tenantLineInnerInterceptor() {
        return new TenantLineInnerInterceptor(new TenantLineHandler() {
            /**
             * 获取租户的字段名 默认 tenant_id
             * @return
             */
            @Override
            public String getTenantIdColumn() {
                return TenantLineHandler.super.getTenantIdColumn();
            }

            @Override
            public boolean ignoreTable(String tableName) {
                return TenantLineHandler.super.ignoreTable(tableName);
            }

            /**
             * 获取租户
             * @return
             */
            @Override
            public Expression getTenantId() {
                return new LongValue(65231313678678678L);
            }
        });
    }

    /**
     * 新多租户插件配置,一缓和二缓遵循mybatis的规则,需要设置 MybatisConfiguration#useDeprecatedExecutor = false 避免缓存万一出现问题
     */
    @Bean
    public MybatisPlusInterceptor mybatisPlusInterceptor() {
        MybatisPlusInterceptor mybatisPlusInterceptor = new MybatisPlusInterceptor();
        // 开启乐观锁
        mybatisPlusInterceptor.addInnerInterceptor(new OptimisticLockerInnerInterceptor());
        // 多租户
        if (tenantProperties.getEnabled()) {
            mybatisPlusInterceptor.addInnerInterceptor(tenantLineInnerInterceptor());
        }

        // 如果用了分页插件注意先 add TenantLineInnerInterceptor 再 add PaginationInnerInterceptor
        // 用了分页插件必须设置 MybatisConfiguration#useDeprecatedExecutor = false
        // 分页插件
        mybatisPlusInterceptor.addInnerInterceptor(new PaginationInnerInterceptor(DbType.MYSQL));
        return mybatisPlusInterceptor;
    }

    @Bean
    public ConfigurationCustomizer configurationCustomizer() {
        return configuration -> configuration.setUseDeprecatedExecutor(false);
    }

    @Bean
    public MetaObjectHandler metaObjectHandler() {
        return new MyMetaObjectHandler();
    }

}
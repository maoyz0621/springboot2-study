/**
 * Copyright 2020 Inc.
 **/
package com.myz.springboot2.mybatis.encrypt.config.plugin;


import com.myz.springboot2.mybatis.encrypt.config.annotation.CryptClass;
import com.myz.springboot2.mybatis.encrypt.config.annotation.CryptField;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.cache.CacheKey;
import org.apache.ibatis.executor.Executor;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.mapping.SqlCommandType;
import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.plugin.Intercepts;
import org.apache.ibatis.plugin.Invocation;
import org.apache.ibatis.plugin.Plugin;
import org.apache.ibatis.plugin.Signature;
import org.apache.ibatis.session.ResultHandler;
import org.apache.ibatis.session.RowBounds;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.stream.Collectors;

/**
 * 加解密插件
 *
 * @author maoyz0621 on 2020/11/3
 * @version v1.0
 */
@Intercepts({
        @Signature(type = Executor.class, method = "query", args = {MappedStatement.class, Object.class, RowBounds.class, ResultHandler.class}),
        @Signature(type = Executor.class, method = "query", args = {MappedStatement.class, Object.class, RowBounds.class, ResultHandler.class, CacheKey.class, BoundSql.class}),
        @Signature(type = Executor.class, method = "update", args = {MappedStatement.class, Object.class})
})
@Slf4j
public class CryptInterceptor implements Interceptor {

    private static final int MAPPED_STATEMENT_INDEX = 0;
    private static final int PARAMETER_INDEX = 1;
    // 缓存：存放类中注解的成员
    private static ConcurrentMap<Class, Set> map = new ConcurrentHashMap<>();

    @Override
    public Object intercept(Invocation invocation) throws Throwable {
        log.info("============= 加解密拦截器处理 =============");
        // invocation.getArgs() 与 @Signature中args参数对应
        Object[] args = invocation.getArgs();
        MappedStatement statement = (MappedStatement) args[MAPPED_STATEMENT_INDEX];
        // 获取sql语句的类型
        String methodName = invocation.getMethod().getName();

        // sql语句中放入的参数
        Object parameter = args[PARAMETER_INDEX];
        log.info("sql语句中放入的参数 = {}", parameter);

        // 如果是查询操作，并且返回值不是敏感实体类对象，并且传入参数不为空，就直接调用执行方法，返回这个方法的返回值
        // 方法中可以判断这个返回值是否是多条数据，如果有数据，就代表着是select 操作，没有就代表是update insert delete
        SqlCommandType sqlCommandType = statement.getSqlCommandType();
        boolean selectType = SqlCommandType.SELECT.equals(sqlCommandType);
        // 查询
        if (selectType) {
            if (statement.getResultMaps().size() > 0) {
                // 获取返回值的类属性
                Class<?> type = statement.getResultMaps().get(0).getType();
                // 是否有注解
                if (!type.isAnnotationPresent(CryptClass.class)) {
                    // 直接执行语句并返回
                    return invocation.proceed();
                }
            }
        } else if (SqlCommandType.INSERT.equals(sqlCommandType) || SqlCommandType.UPDATE.equals(sqlCommandType)) {
            // 如果有就扫描是否是更新操作和是否有加密注解
            // 如果是更新或者插入时，就对数据进行加密后保存在数据库
            // 更新服务
            log.info("================= update ===================");
            // Map入参
            if (!(parameter instanceof Map)) {
                // 批量加解密操作
                List<Field> fieldList = new ArrayList<>();
                Class<?> parameterClass = parameter.getClass();
                while (parameterClass != null) {
                    fieldList.addAll(Arrays.asList(parameterClass.getDeclaredFields()));
                    parameterClass = parameterClass.getSuperclass();
                }
                // 注解的field
                Set<Field> annotationFields = fieldList.stream().filter(field -> field.isAnnotationPresent(CryptField.class)).collect(Collectors.toSet());
                for (Field annotationField : annotationFields) {
                    annotationField.setAccessible(true);
                }
                map.put(parameterClass, annotationFields);
            } else {
                // TODO Map入参
            }

        }


        // 继续执行sql语句,调用当前拦截的执行方法
        Object returnValue = invocation.proceed();
        // 非查询结果直接返回
        if (!selectType) {
            return returnValue;
        }

        // 对出参解密
        if (returnValue == null || returnValue instanceof Double) {
            return returnValue;
        }

        if (returnValue instanceof Collection) {
            List list = (List) returnValue;
            Set set = map.get(list.get(0).getClass());
            for (Object o : list) {
                Class<?> aClass = o.getClass();

            }
        }


        if (returnValue instanceof Map) {

        }

        Class<?> clazz = returnValue.getClass();

        Method method = getMethod(statement);

        if (method == null) {
            return returnValue;
        }

        Class<?> returnType = method.getReturnType();

        if (returnValue instanceof String) {

        }

        if (returnValue instanceof List) {

        }
        return returnValue;
    }


    @Override
    public Object plugin(Object target) {
        return Plugin.wrap(target, this);
    }

    @Override
    public void setProperties(Properties properties) {

    }

    private Method getMethod(MappedStatement statement) throws ClassNotFoundException {
        // com.myz.springboot2.mybatis.encrypt.domain.xxxMapper.xxx
        String statementId = statement.getId();
        log.info("statementId = {}", statementId);

        Method method = null;
        final Class<?> clazz = Class.forName(statementId.substring(0, statementId.lastIndexOf(".")));
        for (Method _method : clazz.getDeclaredMethods()) {
            if (_method.getName().equalsIgnoreCase(statementId.substring(statementId.lastIndexOf(".") + 1))) {
                method = _method;
                break;
            }
        }
        return method;
    }
}
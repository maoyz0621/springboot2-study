// /**
//  * Copyright 2021 Inc.
//  **/
// package com.myz.springboot2.mybatis.encrypt.config.plugin;
//
// import org.apache.ibatis.executor.parameter.ParameterHandler;
// import org.apache.ibatis.executor.statement.StatementHandler;
// import org.apache.ibatis.mapping.BoundSql;
// import org.apache.ibatis.mapping.MappedStatement;
// import org.apache.ibatis.mapping.SqlCommandType;
// import org.apache.ibatis.plugin.Interceptor;
// import org.apache.ibatis.plugin.Intercepts;
// import org.apache.ibatis.plugin.Invocation;
// import org.apache.ibatis.plugin.Signature;
//
// import java.sql.Connection;
//
// /**
//  * StatementHandler 用来处理SQL的执行过程，我们可以在这里重写SQL非常常用。
//  *
//  * @author maoyz0621 on 2021/10/21
//  * @version v1.0
//  */
// @Intercepts({
//         @Signature(type = StatementHandler.class, method = "prepare", args = {Connection.class, Integer.class})
// })
// public class StatementIntercepter implements Interceptor {
//     @Override
//     public Object intercept(Invocation invocation) throws Throwable {
//         StatementHandler statementHandler = (StatementHandler) invocation.getTarget();
//         ParameterHandler parameterHandler = statementHandler.getParameterHandler();
//
//         MappedStatement mappedStatement;
//         //
//         // SQL类型
//         SqlCommandType sqlCommandType = mappedStatement.getSqlCommandType();
//
//         // SQL语句
//         BoundSql boundSql = statementHandler.getBoundSql();
//         switch (sqlCommandType) {
//             case INSERT:
//
//                 break;
//             case UPDATE:
//                 break;
//             case SELECT:
//                 break;
//             default:
//                 break;
//         }
//         return invocation.proceed();
//     }
// }
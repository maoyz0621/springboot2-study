/**
 * Copyright 2023 Inc.
 **/
package com.myz.mybatis.sql.encrypt.utils;

import net.sf.jsqlparser.JSQLParserException;
import net.sf.jsqlparser.parser.CCJSqlParserUtil;
import net.sf.jsqlparser.statement.Statement;
import org.apache.ibatis.mapping.BoundSql;

import java.lang.reflect.Field;

/**
 * @author maoyz0621 on 2023/5/30
 * @version v1.0
 */
public class SqlParserUtils {

    public static Statement getStatement(String sql) throws JSQLParserException {
        return CCJSqlParserUtil.parse(sql);
    }

    public static Object getProperty(Object val, String name) throws Exception {
        Field field = val.getClass().getDeclaredField(name);
        field.setAccessible(true);
        return field.get(val);
    }

    public static void dynamicModifySql(BoundSql boundSql, String newSql) throws Exception {
        Field field = BoundSql.class.getDeclaredField("sql");
        field.setAccessible(true);
        field.set(boundSql, newSql);
    }
}
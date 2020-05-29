/*
 * Copyright (C) 2020, All rights Reserved, Designed By
 * @author: maoyz
 * @Copyright: 2020-05-28 14:34  Inc. All rights reserved.
 */
package com.myz.shardingjdbc.api;

import javax.sql.DataSource;
import java.security.SecureRandom;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * @author maoyz
 */
public class ShardingJdbcManager {

    /* 执行query */
    public static void executeQuerySql(DataSource dataSource) throws SQLException {
        String sql = "SELECT o.* FROM t_order o JOIN t_order_item i ON o.order_id=i.order_id WHERE o.user_id=? AND o.order_id=?";
        Connection connection = dataSource.getConnection();
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setInt(1, 101);
        preparedStatement.setInt(2, 10);

        ResultSet resultSet = preparedStatement.executeQuery();
        while (resultSet.next()) {
            System.out.println(resultSet.getInt(1));
            System.out.println(resultSet.getInt(2));
            System.out.println(resultSet.getString(3));
            System.out.println(resultSet.getString(4));
        }
    }

    /* 执行insert */
    public static int executeInsertSql(DataSource dataSource) throws Exception {
        String sql = "INSERT INTO `t_order` (`id`,`user_id`,`order_id`,`user_name`) VALUES (?, ?, ?, ?)";
        Connection connection = dataSource.getConnection();
        connection.setAutoCommit(false);

        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        int index = SecureRandom.getInstance("SHA1PRNG").nextInt(10000);

        preparedStatement.setInt(1, index);
        preparedStatement.setInt(2, index);
        preparedStatement.setInt(3, 10 + index);
        preparedStatement.setString(4, "user" + index);
        int i = preparedStatement.executeUpdate();
        // System.out.println(i);

        connection.commit();

        return index;
    }

    /* 执行分表insert */
    public static int executeInsertSqlNoId(DataSource dataSource) throws Exception {
        String sql = "INSERT INTO `t_order` (`user_id`,`status`) VALUES (?, ?)";
        Connection connection = dataSource.getConnection();
        connection.setAutoCommit(false);

        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        int index = SecureRandom.getInstance("SHA1PRNG").nextInt(10000);

        preparedStatement.setInt(1, index);
        preparedStatement.setString(2, "user" + index);
        int i = preparedStatement.executeUpdate();
        // System.out.println(i);

        connection.commit();

        return index;
    }

    /* 分表查询 */
    public static void executeQuerySqlShardingTableSingleTable(DataSource dataSource) throws SQLException {
        String sql = "SELECT o.* FROM t_order o where o.user_id > ? and o.user_id < ?";
        Connection connection = dataSource.getConnection();
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setInt(1, 1000);
        preparedStatement.setInt(2, 2000);

        executeQuery(preparedStatement);
    }

    /* 分表分页查询 */
    public static void executeQuerySqlShardingTablePageSingleTable(DataSource dataSource, int limitBegin, int limitEnd) throws SQLException {
        String sql = "SELECT o.* FROM t_order o where o.user_id > ? and o.user_id < ? order by user_id desc limit ?, ?";
        Connection connection = dataSource.getConnection();
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setInt(1, 0);
        preparedStatement.setInt(2, 10000);
        preparedStatement.setInt(3, limitBegin);
        preparedStatement.setInt(4, limitEnd);

        executeQuery(preparedStatement);
    }

    /* 分表分页查询 */
    public static void executeQuerySqlShardingTableGroupBySingleTable(DataSource dataSource, int limitBegin, int limitEnd) throws SQLException {
        String sql = "SELECT o.* FROM t_order o where o.user_id > ? and o.user_id < ? order by user_id desc limit ?, ?";
        Connection connection = dataSource.getConnection();
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setInt(1, 0);
        preparedStatement.setInt(2, 10000);
        preparedStatement.setInt(3, limitBegin);
        preparedStatement.setInt(4, limitEnd);

        executeQuery(preparedStatement);
    }

    /* 分表查询 */
    public static void executeQuerySqlShardingDb(DataSource dataSource) throws SQLException {
        String sql = "SELECT o.* FROM t_order o";
        Connection connection = dataSource.getConnection();
        PreparedStatement preparedStatement = connection.prepareStatement(sql);

        executeQuery(preparedStatement);
    }

    /* 分表分页查询 */
    public static void executeQuerySqlShardingDbPage(DataSource dataSource, int limitBegin, int limitEnd) throws SQLException {
        String sql = "SELECT o.* FROM t_order o  order by user_id desc limit ?, ?";
        Connection connection = dataSource.getConnection();
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setInt(1, limitBegin);
        preparedStatement.setInt(2, limitEnd);

        executeQuery(preparedStatement);
    }

    private static void executeQuery(PreparedStatement preparedStatement) throws SQLException {
        ResultSet resultSet = preparedStatement.executeQuery();
        List<Map> results = new LinkedList<>();
        while (resultSet.next()) {
            Map<String, Object> result = new LinkedHashMap<>();
            result.put("order_id", resultSet.getLong(1));
            result.put("user_id", resultSet.getLong(2));
            result.put("status", resultSet.getString(3));
            results.add(result);
        }
        System.out.println(results + "\r\n" + results.size());
    }

}
/*
 * Copyright (C) 2020, All rights Reserved, Designed By
 * @author: maoyz
 * @Copyright: 2020-05-19 10:41  Inc. All rights reserved.
 */
package com.myz.redis1.utils;

import com.myz.redis1.RedisUtils;
import redis.clients.jedis.*;
import redis.clients.jedis.params.GeoRadiusParam;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author maoyz
 */
public class GeohashRedisCommand {

    public static void main(String[] args) {
        Jedis jedis = RedisUtils.getJedis();
        BigKeyDelUtils utils = new BigKeyDelUtils();
        utils.delKeyZSet("nanjing");
        // 一个元素
        jedis.geoadd("nanjing", 118.7607768191631, 32.03488422195249, "FJ24001");
        // 多个元素
        Map<String, GeoCoordinate> member = new HashMap<>();
        member.put("LA324113", new GeoCoordinate(118.75386035764922, 32.02323384046246));
        jedis.geoadd("nanjing", member);

        List<GeoCoordinate> geopos = jedis.geopos("nanjing", "FJ24001");
        System.out.println("FJ24001经纬度:" + geopos.get(0));

        List<GeoCoordinate> geopos0 = jedis.geopos("nanjing", "FJ24002");
        System.out.println("FJ24002经纬度:" + geopos0.get(0));

        System.out.println("\r\n计算经纬度间的距离");
        List<GeoRadiusResponse> nanjing = jedis.georadius("nanjing", 118.7336021592882d, 31.984722493489585d, 30, GeoUnit.KM,
                GeoRadiusParam.geoRadiusParam().withDist().withCoord().sortDescending());
        nanjing.forEach((param) -> {
            System.out.println("距离:" + param.getDistance());
            System.out.println("经纬度:" + param.getCoordinate());
        });

        System.out.println("\r\n计算地区间的距离");
        List<GeoRadiusResponse> responses = jedis.georadiusByMember("nanjing", "LA324113", 30, GeoUnit.KM, GeoRadiusParam.geoRadiusParam().withDist().withCoord());
        responses.forEach((param) -> {
            System.out.println("距离:" + param.getDistance());
            System.out.println("经纬度:" + param.getCoordinate());
        });

    }
}
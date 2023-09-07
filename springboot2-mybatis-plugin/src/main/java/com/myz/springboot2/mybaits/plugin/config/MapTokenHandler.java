/**
 * Copyright 2023 Inc.
 **/
package com.myz.springboot2.mybaits.plugin.config;

import org.apache.ibatis.parsing.GenericTokenParser;
import org.apache.ibatis.parsing.TokenHandler;

import java.util.HashMap;
import java.util.Map;

/**
 * @author maoyz0621 on 2023/4/4
 * @version v1.0
 */
public class MapTokenHandler implements TokenHandler {

    private final Map<String, String> map;

    public MapTokenHandler(Map<String, String> map) {
        this.map = map;
    }

    @Override
    public String handleToken(String token) {
        return map.get(token);
    }


    // 占位符处理
    public static void main(String[] args) {
        String or ="insert #{param} aaa";
        Map<String,String> param = new HashMap<>();
        param.put("param", "123");
        MapTokenHandler tokenHandler = new MapTokenHandler(param);

        // 获取占位符中的内容
        //
        // openToken指定占位符的开始内容
        //
        // closeToken指定占位符的结束内容
        // 解析#{}或者${}中的内容，并进行替换
        GenericTokenParser parser = new GenericTokenParser("#{", "}", tokenHandler);
        String parse = parser.parse(or);
        // 解析后的sql语句
        System.out.println(parse);
    }
}
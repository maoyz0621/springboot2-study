package com.myz.springboot2elasticsearch.service;


import com.myz.springboot2elasticsearch.config.highclient.ElasticSearchQueryOperator;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * @author maoyz0621 on 2021/1/19
 * @version v1.0
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class ElasticSearchIndexOperatorTest {

    @Resource
    ElasticSearchQueryOperator elasticsearchOperator;

    @Test
    public void createIndex() throws IOException {
        elasticsearchOperator.createIndex("asd",2,3);
    }

    @Test
    public void deleteIndex() throws IOException {
        elasticsearchOperator.deleteIndex("asd");
    }

    @Test
    public void create() throws IOException {
        Map<String,Object> data = new HashMap<>();
        data.put("a",2);
        elasticsearchOperator.create("asd",data);
    }

    @Test
    public void createAsync() {
    }

    @Test
    public void delete() throws IOException {
        elasticsearchOperator.delete("asd","-61nIHcBM1jlw-XEEwZR");
    }

    @Test
    public void update() throws IOException {
        Map<String,Object> data = new HashMap<>();
        data.put("a",3);
        elasticsearchOperator.update("asd","-61nIHcBM1jlw-XEEwZR",data);
    }

    @Test
    public void search() throws IOException {
        this.elasticsearchOperator.search("asd");
    }

    @Test
    public void query() throws IOException {
        this.elasticsearchOperator.query("asd","Dq17IHcBM1jlw-XE2wcT1");
    }
}
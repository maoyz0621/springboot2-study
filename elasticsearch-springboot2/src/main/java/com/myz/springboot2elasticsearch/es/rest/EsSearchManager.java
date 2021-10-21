/**
 * Copyright 2021 Inc.
 **/
package com.myz.springboot2elasticsearch.es.rest;

import com.myz.springboot2elasticsearch.es.model.QueryCondition;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.unit.TimeValue;
import org.elasticsearch.index.query.MatchAllQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * @author maoyz0621 on 2021/7/29
 * @version v1.0
 */
@Component
public abstract class EsSearchManager {

    @Resource
    protected RestHighLevelClient restHighLevelClient;

    public void search(QueryCondition queryCondition) throws IOException {
        SearchRequest request = new SearchRequest("a");
        SearchSourceBuilder builder = new SearchSourceBuilder();
        MatchAllQueryBuilder matchAllQueryBuilder = QueryBuilders.matchAllQuery();
        builder.query(matchAllQueryBuilder);
        builder.from(queryCondition.getPageNo());
        builder.size(queryCondition.getPageSize());
        builder.timeout(new TimeValue(60, TimeUnit.SECONDS));
        request.source(builder);
        SearchResponse searchResponse = restHighLevelClient.search(request, RequestOptions.DEFAULT);
        //6、开始处理返回的数据
        SearchHit[] hits = searchResponse.getHits().getHits();
        List<String> list = new ArrayList<String>();
        for (SearchHit hit : hits) {
            String hitString = hit.getSourceAsString();
            // JSON转实体
            System.out.println(hitString);
            list.add(hitString);
        }
    }
}
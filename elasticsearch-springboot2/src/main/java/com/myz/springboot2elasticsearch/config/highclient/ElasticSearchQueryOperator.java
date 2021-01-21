/**
 * Copyright 2021 Inc.
 **/
package com.myz.springboot2elasticsearch.config.highclient;

import lombok.extern.slf4j.Slf4j;
import org.apache.lucene.search.TotalHits;
import org.elasticsearch.action.get.GetRequest;
import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.common.Strings;
import org.elasticsearch.common.unit.Fuzziness;
import org.elasticsearch.common.unit.TimeValue;
import org.elasticsearch.index.query.MatchQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.rest.RestStatus;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHits;
import org.elasticsearch.search.aggregations.AggregationBuilders;
import org.elasticsearch.search.aggregations.bucket.terms.TermsAggregationBuilder;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.elasticsearch.search.fetch.subphase.FetchSourceContext;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightBuilder;
import org.elasticsearch.search.sort.FieldSortBuilder;
import org.elasticsearch.search.sort.ScoreSortBuilder;
import org.elasticsearch.search.sort.SortOrder;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * 参考文章:https://zhuanlan.zhihu.com/p/143786937
 * matchAllQuery   查询所有
 * matchQuery      分词
 * termQuery       不分词
 * termsQuery
 * fuzzyQuery       模糊，相似(自动判断)
 * prefixQuery      start with
 * rangeQuery       范围（gt、lt、gte、lte）
 * wildcardQuery	模糊（*匹配任意、?匹配单个字符）
 * regexpQuery	    k.*y 匹配 ky、key
 *
 * @author maoyz0621 on 2021/1/7
 * @version v1.0
 */
@Slf4j
@Component
public class ElasticSearchQueryOperator extends ElasticSearchOperator {

    /**
     * 查询数据
     *
     * @throws IOException
     */
    public void query(String index, String id) throws IOException {
        GetRequest request = new GetRequest(index, id);

        String[] includes = new String[]{};
        String[] excludes = Strings.EMPTY_ARRAY;

        FetchSourceContext fetchSourceContext = new FetchSourceContext(true, includes, excludes);
        request.fetchSourceContext(fetchSourceContext);

        GetResponse response = this.restHighLevelClient.get(request, RequestOptions.DEFAULT);
        if (response.isExists()) {
            Map<String, Object> source = response.getSource();
            log.info("【{}】根据【{}】查询数据:{}", index, id, source);
        }
        // 返回null
    }


    /**
     * 搜索数据
     * SearchRequest
     * SearchSourceBuilder
     * SearchResponse
     *
     * @throws IOException
     */
    public void search(String index) throws IOException {
        SearchRequest searchRequest = new SearchRequest(index);

        // 构建搜索
        SearchSourceBuilder sourceBuilder = new SearchSourceBuilder();
        // matchQuery 首先会解析查询字符串，进行分词，然后查询
        // termQuery 输入的查询内容是什么，就会按照什么去查询，并不会解析查询内容，对它分词
        sourceBuilder.query(QueryBuilders.matchQuery("a", "2"));
        sourceBuilder.from(0);
        sourceBuilder.size(5);
        sourceBuilder.timeout(new TimeValue(60, TimeUnit.SECONDS));

        searchRequest.source(sourceBuilder);

        SearchResponse searchResponse = this.restHighLevelClient.search(searchRequest, RequestOptions.DEFAULT);

        if (RestStatus.OK.getStatus() == searchResponse.status().getStatus()) {
            // 包含了总点击次数、最大得分等信息：
            SearchHits hits = searchResponse.getHits();
            TotalHits totalHits = hits.getTotalHits();
            log.info("搜索到 {} 条数据", totalHits.value);

            for (SearchHit hit : hits) {
                // 它还可以将数据JSON字符串或键/值对映射的形式返回。
                log.info("{}", hit.getSourceAsString());
            }
        }
    }

    /**
     * 搜索数据
     *
     * @throws IOException
     */
    public void search(String index, String id) throws IOException {
        SearchRequest searchRequest = new SearchRequest(index);

        // 构建搜索
        SearchSourceBuilder sourceBuilder = new SearchSourceBuilder();

        MatchQueryBuilder queryBuilder = new MatchQueryBuilder("", "");
        // 设置模糊查询，设置仅仅匹配前面几个字符，设置查询的展开层数。
        queryBuilder.fuzziness(Fuzziness.AUTO);
        queryBuilder.prefixLength(3)
                .maxExpansions(3);

        sourceBuilder.query(queryBuilder);

        SearchResponse searchResponse = this.restHighLevelClient.search(searchRequest, RequestOptions.DEFAULT);

        if (RestStatus.OK.getStatus() == searchResponse.status().getStatus()) {
            SearchHits hits = searchResponse.getHits();
            TotalHits totalHits = hits.getTotalHits();
            log.info("搜索到 {} 条数据", totalHits.value);

            for (SearchHit hit : hits) {
                log.info("{}", hit.getSourceAsString());
            }
        }
    }

    /**
     * 搜索数据指定排序
     *
     * @param index
     * @param id
     * @throws IOException
     */
    public void searchSort(String index, String id) throws IOException {
        SearchRequest searchRequest = new SearchRequest(index);
        // 构建搜索
        SearchSourceBuilder sourceBuilder = new SearchSourceBuilder();

        MatchQueryBuilder queryBuilder = new MatchQueryBuilder("", "");
        // 按照得分score情况降序
        sourceBuilder.sort(new ScoreSortBuilder().order(SortOrder.DESC));
        // 按照字段 升序
        sourceBuilder.sort(new FieldSortBuilder("id").order(SortOrder.ASC));
        sourceBuilder.query(queryBuilder);

        SearchResponse searchResponse = this.restHighLevelClient.search(searchRequest, RequestOptions.DEFAULT);

        if (RestStatus.OK.getStatus() == searchResponse.status().getStatus()) {
            SearchHits hits = searchResponse.getHits();
            TotalHits totalHits = hits.getTotalHits();
            log.info("搜索到 {} 条数据", totalHits.value);

            for (SearchHit hit : hits) {
                log.info("{}", hit.getSourceAsString());
            }
        }
    }

    /**
     * 搜索数据过滤字段
     *
     * @param index
     * @param id
     * @throws IOException
     */
    public void searchFilter(String index, String id) throws IOException {
        SearchRequest searchRequest = new SearchRequest(index);
        // 构建搜索
        SearchSourceBuilder sourceBuilder = new SearchSourceBuilder();

        MatchQueryBuilder queryBuilder = new MatchQueryBuilder("", "");
        String[] includes = new String[]{};
        String[] excludes = Strings.EMPTY_ARRAY;
        sourceBuilder.fetchSource(includes, excludes)
                .query(queryBuilder);

        SearchResponse searchResponse = this.restHighLevelClient.search(searchRequest, RequestOptions.DEFAULT);

        if (RestStatus.OK.getStatus() == searchResponse.status().getStatus()) {
            SearchHits hits = searchResponse.getHits();
            TotalHits totalHits = hits.getTotalHits();
            log.info("搜索到 {} 条数据", totalHits.value);

            for (SearchHit hit : hits) {
                log.info("{}", hit.getSourceAsString());
            }
        }
    }

    /**
     * 搜索数据高亮显示
     *
     * @param index
     * @param id
     * @throws IOException
     */
    public void searchHighlight(String index, String id) throws IOException {
        SearchRequest searchRequest = new SearchRequest(index);
        // 构建搜索
        SearchSourceBuilder sourceBuilder = new SearchSourceBuilder();

        // 构建高亮生成器
        HighlightBuilder highlightBuilder = new HighlightBuilder();

        // 创建字段高亮
        HighlightBuilder.Field highlight = new HighlightBuilder.Field("a");
        // 设置高亮类型
        highlight.highlighterType("unified");

        // 将高亮类型加入builder中
        highlightBuilder.field(highlight);

        HighlightBuilder.Field highlight1 = new HighlightBuilder.Field("a");
        highlightBuilder.field(highlight1);

        MatchQueryBuilder queryBuilder = new MatchQueryBuilder("", "");

        sourceBuilder.query(queryBuilder)
                .highlighter(highlightBuilder);

        SearchResponse searchResponse = this.restHighLevelClient.search(searchRequest, RequestOptions.DEFAULT);

        if (RestStatus.OK.getStatus() == searchResponse.status().getStatus()) {
            SearchHits hits = searchResponse.getHits();
            TotalHits totalHits = hits.getTotalHits();
            log.info("搜索到 {} 条数据", totalHits.value);

            for (SearchHit hit : hits) {
                log.info("{}", hit.getSourceAsString());
            }
        }
    }

    /**
     * 请求集合
     *
     * @param index
     * @param id
     * @throws IOException
     */
    public void searchAggregation(String index, String id) throws IOException {
        SearchRequest searchRequest = new SearchRequest(index);
        // 构建搜索
        SearchSourceBuilder sourceBuilder = new SearchSourceBuilder();

        TermsAggregationBuilder aggregationBuilder = AggregationBuilders.terms("")
                .field("");

        aggregationBuilder.subAggregation(AggregationBuilders.avg(""))
                .field("");

        MatchQueryBuilder queryBuilder = new MatchQueryBuilder("", "");

        sourceBuilder.query(queryBuilder)
                .aggregation(aggregationBuilder);

        SearchResponse searchResponse = this.restHighLevelClient.search(searchRequest, RequestOptions.DEFAULT);

        if (RestStatus.OK.getStatus() == searchResponse.status().getStatus()) {
            SearchHits hits = searchResponse.getHits();
            TotalHits totalHits = hits.getTotalHits();
            log.info("搜索到 {} 条数据", totalHits.value);

            for (SearchHit hit : hits) {
                log.info("{}", hit.getSourceAsString());
            }
        }
    }

    /**
     * 分析查询和聚合
     *
     * @param index
     * @param id
     * @throws IOException
     */
    public void searchProfile(String index, String id) throws IOException {
        SearchRequest searchRequest = new SearchRequest(index);
        // 构建搜索
        SearchSourceBuilder sourceBuilder = new SearchSourceBuilder();


        MatchQueryBuilder queryBuilder = new MatchQueryBuilder("", "");

        sourceBuilder.query(queryBuilder)
                .profile(true);

        SearchResponse searchResponse = this.restHighLevelClient.search(searchRequest, RequestOptions.DEFAULT);

        if (RestStatus.OK.getStatus() == searchResponse.status().getStatus()) {
            SearchHits hits = searchResponse.getHits();
            TotalHits totalHits = hits.getTotalHits();
            log.info("搜索到 {} 条数据", totalHits.value);

            for (SearchHit hit : hits) {
                log.info("{}", hit.getSourceAsString());
            }
        }
    }
}
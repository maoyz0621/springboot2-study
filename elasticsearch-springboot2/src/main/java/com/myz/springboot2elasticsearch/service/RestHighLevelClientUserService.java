/**
 * Copyright 2022 Inc.
 **/
package com.myz.springboot2elasticsearch.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.myz.springboot2elasticsearch.entity.UserEntity;
import com.myz.springboot2elasticsearch.es.constants.BizEsConstant;
import com.myz.springboot2elasticsearch.es.model.UserQueryCondition;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.lucene.search.TotalHits;
import org.elasticsearch.action.bulk.BulkRequest;
import org.elasticsearch.action.bulk.BulkResponse;
import org.elasticsearch.action.delete.DeleteRequest;
import org.elasticsearch.action.delete.DeleteResponse;
import org.elasticsearch.action.get.GetRequest;
import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.action.search.SearchScrollRequest;
import org.elasticsearch.action.update.UpdateRequest;
import org.elasticsearch.action.update.UpdateResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.Strings;
import org.elasticsearch.common.unit.TimeValue;
import org.elasticsearch.common.xcontent.XContentType;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.index.query.RangeQueryBuilder;
import org.elasticsearch.index.query.TermsQueryBuilder;
import org.elasticsearch.rest.RestStatus;
import org.elasticsearch.search.Scroll;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHits;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.elasticsearch.search.fetch.subphase.FetchSourceContext;
import org.elasticsearch.search.sort.SortOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.io.IOException;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * @author maoyz0621 on 2022/2/28
 * @version v1.0
 */
@Slf4j
@Service
@AllArgsConstructor(onConstructor_ = {@Autowired})
public class RestHighLevelClientUserService {
    private final RestHighLevelClient restHighLevelClient;
    @Autowired
    @Qualifier("objectMapperNoNull")
    private ObjectMapper objectMapper;

    /**
     * 新增数据，有则覆盖
     */
    public void save(UserEntity userEntity) {
        try {
            IndexRequest indexRequest = new IndexRequest(BizEsConstant.INDEX_USER)
                    // 设置路由
                    .routing(userEntity.getUserId() + "")
                    // 设置id
                    .id(userEntity.getId() + "")
                    // 设置超时时间
                    .timeout("2s")
                    .source(objectMapper.writeValueAsString(userEntity), XContentType.JSON);
            IndexResponse response = this.restHighLevelClient.index(indexRequest, RequestOptions.DEFAULT);
            log.info("indexRequest={},response={}", indexRequest, response);

            int status = response.status().getStatus();
            if (RestStatus.OK.getStatus() == response.status().getStatus()) {

            }

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public boolean update(UserEntity userEntity) {
        try {
            UpdateRequest updateRequest = new UpdateRequest(BizEsConstant.INDEX_USER, userEntity.getId() + "");
            // 路由
            updateRequest.routing(userEntity.getUserId() + "");
            // 有值就更新，没有值就新增
            updateRequest.docAsUpsert(true);
            updateRequest.doc(objectMapper.writeValueAsString(userEntity), XContentType.JSON);
            UpdateResponse updateResponse = this.restHighLevelClient.update(updateRequest, RequestOptions.DEFAULT);
            log.info("updateRequest={},\r\nupdateResponse={}", updateRequest, updateResponse);
            updateResponse.getVersion();
            int status = updateResponse.status().getStatus();
            if (RestStatus.OK.getStatus() == updateResponse.status().getStatus()) {
                return true;
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * 批处理
     */
    public boolean bulkUpdate(List<UserEntity> userEntityList) {
        try {
            BulkRequest bulkRequest = new BulkRequest(BizEsConstant.INDEX_USER);
            for (UserEntity userEntity : userEntityList) {
                UpdateRequest updateRequest = new UpdateRequest(BizEsConstant.INDEX_USER, userEntity.getId() + "");
                // 路由
                updateRequest.routing(userEntity.getUserId() + "");
                updateRequest.docAsUpsert(true);
                updateRequest.doc(objectMapper.writeValueAsString(userEntity), XContentType.JSON);
                bulkRequest.add(updateRequest);
            }

            BulkResponse bulkResponse = this.restHighLevelClient.bulk(bulkRequest, RequestOptions.DEFAULT);
            log.info("bulkRequest={},\r\nbulkResponse={}", bulkRequest, bulkResponse);
            int status = bulkResponse.status().getStatus();
            if (RestStatus.OK.getStatus() == bulkResponse.status().getStatus()) {
                return true;
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * 根据id 和 routing 删除数据
     */
    public boolean deleteById(Long id, String routing) {
        try {
            DeleteRequest deleteRequest = new DeleteRequest(BizEsConstant.INDEX_USER, id + "");
            deleteRequest.routing(routing);
            DeleteResponse response = this.restHighLevelClient.delete(deleteRequest, RequestOptions.DEFAULT);
            log.info("deleteRequest={},response={}", deleteRequest, response);
            if (RestStatus.OK.getStatus() == response.status().getStatus()) {
                return true;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * 根据id 和 routing 查询数据
     */
    public void getById(Long id, String routing) {
        try {
            GetRequest request = new GetRequest(BizEsConstant.INDEX_USER, id + "");
            // 设置路由
            request.routing(routing);
            // 包含字段
            String[] includes = new String[]{};
            // 排除字段
            String[] excludes = Strings.EMPTY_ARRAY;
            FetchSourceContext fetchSourceContext = new FetchSourceContext(true, includes, excludes);
            request.fetchSourceContext(fetchSourceContext);
            GetResponse response = this.restHighLevelClient.get(request, RequestOptions.DEFAULT);
            log.info("根据【{}】查询数据:{}", id, response);
            if (response.isExists()) {
                String sourceAsString = response.getSourceAsString();
                UserEntity userEntity = objectMapper.readValue(sourceAsString, UserEntity.class);
                log.info("根据【{}】查询数据:{}", id, userEntity);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /*
    {
        "from": 0,
        "size": 10,
        "timeout": "60s",
        "query": {
            "term": {
                "age": {
                    "value": 11,
                    "boost": 1.0
                }
            }
        }
    }
     */
    public void searchByConditionQuery(UserQueryCondition queryCondition) {
        try {
            SearchRequest searchRequest = new SearchRequest(BizEsConstant.INDEX_USER);
            // 构建搜索
            SearchSourceBuilder sourceBuilder = new SearchSourceBuilder();
            if (queryCondition.getAge() != null) {
                // query term
                sourceBuilder.query(QueryBuilders.termQuery("age", queryCondition.getAge()));
            }
            sourceBuilder.from(queryCondition.getPageNo());
            sourceBuilder.size(queryCondition.getPageSize());
            sourceBuilder.timeout(new TimeValue(60, TimeUnit.SECONDS));
            // 排序
            // sourceBuilder.sort();
            searchRequest.source(sourceBuilder);

            SearchResponse searchResponse = this.restHighLevelClient.search(searchRequest, RequestOptions.DEFAULT);
            log.info("searchRequest={},\r\nsearchResponse={}", searchRequest.source().toString(), searchResponse.getHits());

            readResponse(searchResponse);
        } catch (IOException e) {
            log.error("", e);
        }
    }

    /*
    精确查询terms
    {
    "query": {
        "terms": {
            "goods": [
                "a",
                "b",
                "c"
            ],
            "boost": 1.0
        }
    }
}
     */
    public void searchByConditionQueryTerms(UserQueryCondition queryCondition) {
        try {
            SearchRequest searchRequest = new SearchRequest(BizEsConstant.INDEX_USER);
            // 构建搜索
            SearchSourceBuilder sourceBuilder = new SearchSourceBuilder();
            if (!CollectionUtils.isEmpty(queryCondition.getGoods())) {
                // query term
                sourceBuilder.query(QueryBuilders.termsQuery("goods", queryCondition.getGoods()));
            }
            sourceBuilder.from(queryCondition.getPageNo());
            sourceBuilder.size(queryCondition.getPageSize());
            sourceBuilder.timeout(new TimeValue(60, TimeUnit.SECONDS));
            // 排序
            // sourceBuilder.sort();
            searchRequest.source(sourceBuilder);

            SearchResponse searchResponse = this.restHighLevelClient.search(searchRequest, RequestOptions.DEFAULT);
            log.info("searchRequest={},\r\nsearchResponse={}", searchRequest.source().toString(), searchResponse.getHits());

            readResponse(searchResponse);
        } catch (IOException e) {
            log.error("", e);
        }
    }

    /*
      模糊查询
     */
    public void searchByConditionFuzzyQuery(UserQueryCondition queryCondition) {
        try {
            SearchRequest searchRequest = new SearchRequest(BizEsConstant.INDEX_USER);
            // 构建搜索
            SearchSourceBuilder sourceBuilder = new SearchSourceBuilder();
            if (queryCondition.getAddress() != null) {
                // query term
                sourceBuilder.query(QueryBuilders.fuzzyQuery("address", queryCondition.getAddress()));
            }
            sourceBuilder.from(queryCondition.getPageNo());
            sourceBuilder.size(queryCondition.getPageSize());
            sourceBuilder.timeout(new TimeValue(60, TimeUnit.SECONDS));
            // 排序
            // sourceBuilder.sort();
            searchRequest.source(sourceBuilder);

            SearchResponse searchResponse = this.restHighLevelClient.search(searchRequest, RequestOptions.DEFAULT);
            log.info("searchRequest={},\r\nsearchResponse={}", searchRequest.source().toString(), searchResponse.getHits());

            readResponse(searchResponse);
        } catch (IOException e) {
            log.error("", e);
        }
    }

    /*
    通配符查询查询
    {
        "query": {
            "wildcard": {
                "address": {
                    "wildcard": "*江*",
                    "boost": 1.0
                }
            }
        }
    }
     */
    public void searchByConditionWildcardQuery(UserQueryCondition queryCondition) {
        try {
            SearchRequest searchRequest = new SearchRequest(BizEsConstant.INDEX_USER);
            // 构建搜索
            SearchSourceBuilder sourceBuilder = new SearchSourceBuilder();
            if (queryCondition.getAddress() != null) {
                // query term
                sourceBuilder.query(QueryBuilders.wildcardQuery("address", "*" + queryCondition.getAddress() + "*"));
            }
            sourceBuilder.from(queryCondition.getPageNo());
            sourceBuilder.size(queryCondition.getPageSize());
            sourceBuilder.timeout(new TimeValue(60, TimeUnit.SECONDS));
            // 排序
            // sourceBuilder.sort();
            searchRequest.source(sourceBuilder);

            SearchResponse searchResponse = this.restHighLevelClient.search(searchRequest, RequestOptions.DEFAULT);
            log.info("searchRequest={},\r\nsearchResponse={}", searchRequest.source().toString(), searchResponse.getHits());

            readResponse(searchResponse);
        } catch (IOException e) {
            log.error("", e);
        }
    }

    /*
    范围查找
    gt: 大于
    gte: 大于等于
    lt: 小于
    lte: 小于等于
    {
        "query": {
            "range": {
                "birth": {
                    "from": 1648125982000,
                    "to": 1648285174481,
                    "include_lower": true,
                    "include_upper": true,
                    "boost": 1.0
                }
            }
        }
    }
     */
    public void searchByConditionRangeQuery(UserQueryCondition queryCondition) {
        try {
            SearchRequest searchRequest = new SearchRequest(BizEsConstant.INDEX_USER);
            // 构建搜索
            SearchSourceBuilder sourceBuilder = new SearchSourceBuilder();
            if (queryCondition.getBirth() != null) {
                RangeQueryBuilder rangeQueryBuilder = QueryBuilders.rangeQuery("birth")
                        // 小于
                        .lt(new Date().getTime())
                        // 大于等于
                        .gte(queryCondition.getBirth().getTime())
                        .includeLower(true)     // 包含上界
                        .includeUpper(true);
                sourceBuilder.query(rangeQueryBuilder);
            }
            sourceBuilder.from(queryCondition.getPageNo());
            sourceBuilder.size(queryCondition.getPageSize());
            sourceBuilder.timeout(new TimeValue(60, TimeUnit.SECONDS));
            // 排序
            // sourceBuilder.sort();
            searchRequest.source(sourceBuilder);

            SearchResponse searchResponse = this.restHighLevelClient.search(searchRequest, RequestOptions.DEFAULT);
            log.info("searchRequest={},\r\nsearchResponse={}", searchRequest.source().toString(), searchResponse.getHits());

            readResponse(searchResponse);
        } catch (IOException e) {
            log.error("", e);
        }
    }

    /*
    bool + filter过滤
    {
        "query": {
            "bool": {
                "filter": [
                    {
                        "term": {
                            "age": {
                                "value": 11,
                                "boost": 1.0
                            }
                        }
                    }
                ],
                "adjust_pure_negative": true,
                "boost": 1.0
            }
        }
    }
     * 组合查询 bool
     * must(QueryBuilders): AND
     * mustNot(QueryBuilders): NOT
     * should(QueryBuilders): OR
     */
    public void searchByConditionBoolQueryFilter(UserQueryCondition queryCondition) {
        try {
            SearchRequest searchRequest = new SearchRequest(BizEsConstant.INDEX_USER);
            // 构建搜索
            SearchSourceBuilder sourceBuilder = new SearchSourceBuilder();
            // bool
            BoolQueryBuilder boolQueryBuilder = QueryBuilders.boolQuery();
            if (queryCondition.getAge() != null) {
                // filter term
                // boolQueryBuilder.must(QueryBuilders.termQuery("age", queryCondition.getAge()));
                boolQueryBuilder.filter(QueryBuilders.termQuery("age", queryCondition.getAge()));
            }
            sourceBuilder.query(boolQueryBuilder);
            sourceBuilder.from(queryCondition.getPageNo());
            sourceBuilder.size(queryCondition.getPageSize());
            sourceBuilder.timeout(new TimeValue(60, TimeUnit.SECONDS));
            // 排序
            // sourceBuilder.sort();
            searchRequest.source(sourceBuilder);

            SearchResponse searchResponse = this.restHighLevelClient.search(searchRequest, RequestOptions.DEFAULT);
            log.info("searchRequest={},\r\nsearchResponse={}", searchRequest.source().toString(), searchResponse);
            readResponse(searchResponse);
        } catch (IOException e) {
            log.error("", e);
        }
    }


    /*
    {
        "query": {
            "bool": {
                "must": [
                    {
                        "term": {
                            "age": {
                                "value": 11,
                                "boost": 1.0
                            }
                        }
                    }
                ],
                "filter": [
                    {
                        "term": {
                            "telephone": {
                                "value": "151515151111",
                                "boost": 1.0
                            }
                        }
                    }
                ],
                "must_not": [
                    {
                        "terms": {
                            "goods": [
                                "a1",
                                "b1",
                                "c1"
                            ],
                            "boost": 1.0
                        }
                    }
                ],
                "should": [
                    {
                        "range": {
                            "birth": {
                                "from": 1648125982000,
                                "to": 1648286201844,
                                "include_lower": true,
                                "include_upper": true,
                                "boost": 1.0
                            }
                        }
                    }
                ],
                "adjust_pure_negative": true,
                "boost": 1.0
            }
        }
    }
     * 组合查询 bool
     * must(QueryBuilders): AND
     * mustNot(QueryBuilders): NOT
     * should(QueryBuilders): OR
     */
    public void searchByConditionBoolQuery(UserQueryCondition queryCondition) {
        try {
            SearchRequest searchRequest = new SearchRequest(BizEsConstant.INDEX_USER);
            // 构建搜索
            SearchSourceBuilder sourceBuilder = new SearchSourceBuilder();
            // bool
            BoolQueryBuilder boolQueryBuilder = QueryBuilders.boolQuery();
            if (queryCondition.getTelephone() != null) {
                // must term
                boolQueryBuilder.filter(QueryBuilders.termQuery("telephone", queryCondition.getTelephone()));
            }
            if (queryCondition.getAge() != null) {
                // must term
                boolQueryBuilder.must(QueryBuilders.termQuery("age", queryCondition.getAge()));
            }
            if (queryCondition.getBirth() != null) {
                RangeQueryBuilder rangeQueryBuilder = QueryBuilders.rangeQuery("birth")
                        // 小于
                        .lt(new Date().getTime())
                        // 大于等于
                        .gte(queryCondition.getBirth().getTime())
                        .includeLower(true)     // 包含上界
                        .includeUpper(true);
                boolQueryBuilder.should(rangeQueryBuilder);
            }

            if (!CollectionUtils.isEmpty(queryCondition.getGoods())) {
                TermsQueryBuilder termsQueryBuilder = QueryBuilders.termsQuery("goods", queryCondition.getGoods());
                // must_not terms
                boolQueryBuilder.mustNot(termsQueryBuilder);
            }

            sourceBuilder.query(boolQueryBuilder);
            sourceBuilder.from(queryCondition.getPageNo());
            sourceBuilder.size(queryCondition.getPageSize());
            sourceBuilder.timeout(new TimeValue(60, TimeUnit.SECONDS));
            // 排序
            // sourceBuilder.sort();
            searchRequest.source(sourceBuilder);

            SearchResponse searchResponse = this.restHighLevelClient.search(searchRequest, RequestOptions.DEFAULT);
            log.info("searchRequest={},\r\nsearchResponse={}", searchRequest.source().toString(), searchResponse);
            readResponse(searchResponse);
        } catch (IOException e) {
            log.error("", e);
        }
    }

    /*
      深度分页 scroll
     */
    public void searchByConditionScroll(UserQueryCondition queryCondition) {
        try {
            SearchRequest searchRequest = new SearchRequest(BizEsConstant.INDEX_USER);
            // 失效时间 60s
            Scroll scroll = new Scroll(TimeValue.timeValueSeconds(60));
            searchRequest.scroll(scroll);
            // 构建搜索
            SearchSourceBuilder sourceBuilder = new SearchSourceBuilder();
            // bool
            BoolQueryBuilder boolQueryBuilder = QueryBuilders.boolQuery();
            if (queryCondition.getAge() != null) {
                // filter term
                boolQueryBuilder.filter(QueryBuilders.termQuery("age", queryCondition.getAge()));
            }
            sourceBuilder.query(boolQueryBuilder);
            sourceBuilder.size(queryCondition.getPageSize());
            sourceBuilder.timeout(new TimeValue(60, TimeUnit.SECONDS));
            searchRequest.source(sourceBuilder);

            SearchResponse searchResponse = this.restHighLevelClient.search(searchRequest, RequestOptions.DEFAULT);
            log.info("ScrollScroll searchRequest={},\r\nScroll searchResponse={}", searchRequest.source().toString(), searchResponse);

            long totalCount = searchResponse.getHits().getTotalHits().value;
            // 总页数
            int page = (int) Math.ceil((float) totalCount / queryCondition.getPageSize());
            log.info("page={}", page);
            String scrollId = searchResponse.getScrollId();
            log.info("searchResponse scrollId={}", scrollId);

            for (int i = 1; i < page; i++) {
                SearchScrollRequest searchScrollRequest = new SearchScrollRequest(scrollId);
                searchScrollRequest.scroll(scroll);
                SearchResponse scrollSearchResponse = this.restHighLevelClient.scroll(searchScrollRequest, RequestOptions.DEFAULT);
                scrollId = scrollSearchResponse.getScrollId();
                log.info("第{}次，scrollSearchResponse scrollId={}", i, scrollId);
                readResponse(scrollSearchResponse);
            }
        } catch (IOException e) {
            log.error("", e);
        }
    }

    /*
      实时处理深度分页 searchAfter,但不能够随机跳转分页，只能一页一页向后翻，并且需要至少指定一个唯一不重复的字段排序
      {
          "size": 2,
          "timeout": "60s",
          "query": {
            "bool": {
              "filter": [
                {
                  "term": {
                    "age": {
                      "value": 11,
                      "boost": 1
                    }
                  }
                }
              ],
              "adjust_pure_negative": true,
              "boost": 1
            }
          },
          "sort": [
            {
              "id": {
                "order": "asc"
              }
            },
            {
              "birth": {
                "order": "desc"
              }
            }
          ],
          "search_after": [
            10,
            1648287222345
          ]
        }
     */
    public void searchByConditionSearchAfter(UserQueryCondition queryCondition) {
        try {
            SearchRequest searchRequest = new SearchRequest(BizEsConstant.INDEX_USER);
            // 构建搜索
            SearchSourceBuilder sourceBuilder = new SearchSourceBuilder();
            // bool
            BoolQueryBuilder boolQueryBuilder = QueryBuilders.boolQuery();
            if (queryCondition.getAge() != null) {
                boolQueryBuilder.filter(QueryBuilders.termQuery("age", queryCondition.getAge()));
            }
            sourceBuilder.query(boolQueryBuilder);
            sourceBuilder.size(queryCondition.getPageSize());
            sourceBuilder.timeout(new TimeValue(60, TimeUnit.SECONDS));
            sourceBuilder.sort("id", SortOrder.ASC);
            sourceBuilder.sort("birth", SortOrder.DESC);

            searchRequest.source(sourceBuilder);
            SearchResponse searchResponse = this.restHighLevelClient.search(searchRequest, RequestOptions.DEFAULT);
            log.info("searchRequest={},\r\nSearchAfter searchResponse={}", searchRequest.source().toString(), searchResponse);
            readResponse(searchResponse);
            long totalCount = searchResponse.getHits().getTotalHits().value;
            // 总页数
            int page = (int) Math.ceil((float) totalCount / queryCondition.getPageSize());
            log.info("totalCount = {}, page={}", totalCount, page);
            log.info("================= 下一页 ==================");

            for (int i = 1; i < page; i++) {
                SearchHits hits = searchResponse.getHits();
                // 设置searchAfter
                sourceBuilder.searchAfter(hits.getHits()[hits.getHits().length - 1].getSortValues());
                searchResponse = this.restHighLevelClient.search(searchRequest, RequestOptions.DEFAULT);
                log.info("第{}次 searchRequest2={},\r\nsearchResponse2={}", i, searchRequest.source().toString(), searchResponse);
                readResponse(searchResponse);
            }

        } catch (IOException e) {
            log.error("", e);
        }
    }

    private void readResponse(SearchResponse searchResponse) throws JsonProcessingException {
        SearchHits hits = searchResponse.getHits();
        // 总数量
        TotalHits totalHits = hits.getTotalHits();
        List<UserEntity> result = new ArrayList<>(hits.getHits().length);
        for (SearchHit hit : hits) {
            UserEntity userEntity = objectMapper.readValue(hit.getSourceAsString(), UserEntity.class);
            result.add(userEntity);
        }
        log.info("total={},\r\nsize={},\r\nresult={}", totalHits.value, result.size(), result);
    }

    private void readResponse1(UserQueryCondition queryCondition, SearchSourceBuilder sourceBuilder) {
        Class clazz = queryCondition.getClass();
        //得到所有属性
        Field[] fields = clazz.getDeclaredFields();
        for (Field field : fields) {
            try {
                // 打开私有访问
                field.setAccessible(true);
                // 获取属性
                String name = field.getName();
                //获取属性值
                Object value = field.get(queryCondition);
                if (value == null) {
                    continue;
                }
                sourceBuilder.query(QueryBuilders.matchQuery(name, value));
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }
    }


}
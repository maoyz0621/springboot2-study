/**
 * Copyright 2021 Inc.
 **/
package com.myz.springboot2elasticsearch.config.highclient;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.myz.springboot2elasticsearch.entity.UserEntity;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.elasticsearch.action.ActionListener;
import org.elasticsearch.action.bulk.BulkRequest;
import org.elasticsearch.action.delete.DeleteRequest;
import org.elasticsearch.action.delete.DeleteResponse;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.update.UpdateRequest;
import org.elasticsearch.action.update.UpdateResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.common.xcontent.XContentBuilder;
import org.elasticsearch.common.xcontent.XContentFactory;
import org.elasticsearch.common.xcontent.XContentType;
import org.elasticsearch.index.VersionType;
import org.elasticsearch.rest.RestStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * @author maoyz0621 on 2021/1/7
 * @version v1.0
 */
@Slf4j
@Component
public class ElasticSearchOperator extends ElasticSearchIndexOperator {

    @Autowired
    ObjectMapper objectMapper;
    /**
     * 新增文档 Map
     *
     * @throws IOException
     */
    public void create11() throws IOException {
        Map<String, Object> data = new HashMap<>();
        IndexRequest indexRequest = new IndexRequest("a")
                .id("").source(data).versionType(VersionType.EXTERNAL);
        // 设置路由值、超时时间等
        indexRequest.routing("routing");
        indexRequest.timeout("1s");

        IndexResponse response = this.restHighLevelClient.index(indexRequest, RequestOptions.DEFAULT);

        response.getId();
        response.getIndex();
        response.getVersion();
        response.getResult();
        response.getShardId();
        response.getShardInfo();
    }

    /**
     * 新增文档 Map
     *
     * @throws IOException
     */
    public boolean create(String index, final Map<String, Object> data) throws IOException {
        if (StringUtils.isBlank(index)) {
            throw new IllegalArgumentException("The index name cannot be null.");
        }

        if (data == null || data.size() == 0) {
            throw new IllegalArgumentException("The data cannot be null.");
        }

        IndexRequest indexRequest = new IndexRequest(index).source(data);
        IndexResponse response = this.restHighLevelClient.index(indexRequest, RequestOptions.DEFAULT);

        response.getId();
        response.getIndex();
        response.getVersion();
        response.getResult();
        response.getShardId();
        response.getShardInfo();

        int status = response.status().getStatus();
        if (RestStatus.OK.getStatus() == response.status().getStatus()) {
            return true;
        }
        return false;
    }

    public boolean create(String index, final UserEntity data) throws IOException {
        if (StringUtils.isBlank(index)) {
            throw new IllegalArgumentException("The index name cannot be null.");
        }



        IndexRequest indexRequest = new IndexRequest(index).source(objectMapper.writeValueAsString(data),XContentType.JSON);
        IndexResponse response = this.restHighLevelClient.index(indexRequest, RequestOptions.DEFAULT);

        response.getId();
        response.getIndex();
        response.getVersion();
        response.getResult();
        response.getShardId();
        response.getShardInfo();

        int status = response.status().getStatus();
        if (RestStatus.OK.getStatus() == response.status().getStatus()) {
            return true;
        }
        return false;
    }

    /**
     * 以Json字符串的形式
     *
     * @throws IOException
     */
    public void create0() throws IOException {
        String json = "";

        IndexRequest indexRequest = new IndexRequest("a").source(json, XContentType.JSON);
        IndexResponse response = this.restHighLevelClient.index(indexRequest, RequestOptions.DEFAULT);

        response.getId();
        response.getIndex();
        response.getVersion();
        response.getResult();
        response.getShardId();
        response.getShardInfo();
    }

    public void createmap() throws IOException {
        XContentBuilder jsonBuilder = XContentFactory.jsonBuilder();
        jsonBuilder.startObject();
        jsonBuilder.field("", "");
        jsonBuilder.field("", "");
        jsonBuilder.endObject();

        IndexRequest indexRequest = new IndexRequest("a").source(jsonBuilder);
        IndexResponse response = this.restHighLevelClient.index(indexRequest, RequestOptions.DEFAULT);
        this.restHighLevelClient.index(indexRequest, RequestOptions.DEFAULT);

        response.getId();
        response.getIndex();
        response.getVersion();
        response.getResult();
        response.getShardId();
        response.getShardInfo();
    }

    /**
     * 以XContentBuilder对象形式
     *
     * @throws IOException
     */
    public void create1() throws IOException {
        XContentBuilder jsonBuilder = XContentFactory.jsonBuilder();
        jsonBuilder.startObject();
        jsonBuilder.field("", "");
        jsonBuilder.field("", "");
        jsonBuilder.endObject();

        IndexRequest indexRequest = new IndexRequest("a").source(jsonBuilder);
        IndexResponse response = this.restHighLevelClient.index(indexRequest, RequestOptions.DEFAULT);

        response.getId();
        response.getIndex();
        response.getVersion();
        response.getResult();
        response.getShardId();
        response.getShardInfo();
    }

    /**
     * 以Object键值对的形式
     *
     * @throws IOException
     */
    public void create2() throws IOException {

        IndexRequest indexRequest = new IndexRequest("a").source("jsonBuilder", "", "", "");
        IndexResponse response = this.restHighLevelClient.index(indexRequest, RequestOptions.DEFAULT);
        response.getId();
        response.getIndex();
        response.getVersion();
        response.getResult();
        response.getShardId();
        response.getShardInfo();
    }

    public void createAsync() {
        Map data = new HashMap();
        IndexRequest indexRequest = new IndexRequest("a").source(data);
        this.restHighLevelClient.indexAsync(indexRequest, RequestOptions.DEFAULT, new ActionListener<IndexResponse>() {

            @Override
            public void onResponse(IndexResponse indexResponse) {

            }

            @Override
            public void onFailure(Exception e) {

            }
        });
    }

    //
    // /**
    //  * 判断是否存在
    //  */
    // public boolean exists() throws IOException {
    //     GetRequest request = new GetRequest("a", "b");
    //
    //     FetchSourceContext fetchSourceContext = new FetchSourceContext();
    //     request.fetchSourceContext(fetchSourceContext);
    //
    //     boolean exists = this.restHighLevelClient.exists(request, RequestOptions.DEFAULT);
    //
    //     return exists;
    // }

    /**
     * 删除数据
     *
     * @param index 索引名称
     * @param id
     */
    public boolean delete(String index, String id) throws IOException {
        DeleteRequest request = new DeleteRequest(index, id);
        DeleteResponse response = this.restHighLevelClient.delete(request, RequestOptions.DEFAULT);
        if (RestStatus.OK.getStatus() == response.status().getStatus()) {
            return true;
        }
        return false;
    }

    /**
     * 更新数据
     *
     * @throws IOException
     */
    public boolean update(String index, String id, final Map<String, Object> updateData) throws IOException {
        // map -> json
        return update(index, id, new Gson().toJson(updateData));
    }

    public boolean update(String index, String id, String updateData) throws IOException {
        UpdateRequest updateRequest = new UpdateRequest(index, id);

        UpdateRequest updateRequest1 = new UpdateRequest();
        updateRequest1.index(index).id(id);

        updateRequest.doc(updateData, XContentType.JSON);

        UpdateResponse updateResponse = this.restHighLevelClient.update(updateRequest, RequestOptions.DEFAULT);
        updateResponse.getVersion();
        int status = updateResponse.status().getStatus();
        if (RestStatus.OK.getStatus() == updateResponse.status().getStatus()) {
            return true;
        }
        return false;
    }

    /**
     * 批处理
     * @param index
     * @param id
     * @param updateData
     * @return
     * @throws IOException
     */
    public boolean bulk(String index, String id, String updateData) throws IOException {
        BulkRequest bulkRequest = new BulkRequest();


        UpdateRequest updateRequest = new UpdateRequest(index, id);

        updateRequest.doc(updateData, XContentType.JSON);

        UpdateResponse updateResponse = this.restHighLevelClient.update(updateRequest, RequestOptions.DEFAULT);
        updateResponse.getVersion();
        int status = updateResponse.status().getStatus();
        if (RestStatus.OK.getStatus() == updateResponse.status().getStatus()) {
            return true;
        }
        return false;
    }
}
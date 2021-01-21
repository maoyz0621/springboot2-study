/**
 * Copyright 2021 Inc.
 **/
package com.myz.springboot2elasticsearch.config.highclient;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.elasticsearch.action.admin.indices.delete.DeleteIndexRequest;
import org.elasticsearch.action.support.master.AcknowledgedResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.client.indices.CreateIndexRequest;
import org.elasticsearch.client.indices.CreateIndexResponse;
import org.elasticsearch.client.indices.GetIndexRequest;
import org.elasticsearch.common.settings.Settings;

import javax.annotation.Resource;
import java.io.IOException;

/**
 * 使用elasticsearch-rest-high-level-client客户端工具
 *
 * @author maoyz0621 on 2021/1/7
 * @version v1.0
 */
@Slf4j
public abstract class ElasticSearchIndexOperator {

    @Resource
    protected RestHighLevelClient restHighLevelClient;

    @Resource
    protected RestClient restClient;

    public boolean createIndex(String index) throws IOException {
        return createIndex(index, 5, 2);
    }

    /**
     * 创建索引
     *
     * @param index    索引名称
     * @param shards   分片数量
     * @param replicas 副本数量
     * @return
     */
    public boolean createIndex(String index, int shards, int replicas) throws IOException {
        if (StringUtils.isBlank(index)) {
            throw new IllegalArgumentException("The index name cannot be null.");
        }

        if (shards <= 0 || replicas <= 0) {
            throw new IllegalArgumentException("The shards or replicas cannot be 0.");
        }

        // 首先查询索引是否存在
        if (existsIndex(index)) {
            return true;
        }

        // 创建索引
        CreateIndexRequest createIndexRequest = new CreateIndexRequest(index);

        createIndexRequest.settings(Settings.builder()
                .put("index.number_of_shards", 5)
                .put("index.number_of_replicas", 2));
        CreateIndexResponse createIndexResponse = this.restHighLevelClient.indices().create(createIndexRequest, RequestOptions.DEFAULT);
        // 可以判断是否所有节点都已确认请求
        boolean acknowledged = createIndexResponse.isAcknowledged();
        log.info("{}", acknowledged);
        return acknowledged;
    }

    /**
     * 判断索引是否存在
     *
     * @param index
     * @return
     * @throws IOException
     */
    public boolean existsIndex(String index) throws IOException {
        // 首先查询索引是否存在
        GetIndexRequest indexRequest = new GetIndexRequest(index);
        boolean exists = this.restHighLevelClient.indices().exists(indexRequest, RequestOptions.DEFAULT);
        log.info("索引【{}】{}", index, exists ? "已存在" : "不存在");

        return exists;

    }

    /**
     * 删除索引
     *
     * @param index
     * @return
     */
    public boolean deleteIndex(String index) throws IOException {
        // 首先查询索引是否存在
        if (!existsIndex(index)) {
            return true;
        }

        DeleteIndexRequest deleteIndexRequest = new DeleteIndexRequest(index);

        AcknowledgedResponse acknowledgedResponse = this.restHighLevelClient.indices().delete(deleteIndexRequest, RequestOptions.DEFAULT);
        // 可以判断是否所有节点都已确认请求
        boolean acknowledged = acknowledgedResponse.isAcknowledged();
        log.info("删除索引【{}】,结果：{}", index, acknowledged);
        return acknowledged;
    }

}
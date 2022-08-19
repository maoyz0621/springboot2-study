/**
 * Copyright 2022 Inc.
 **/
package com.myz.springboot2elasticsearch.service.join;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.myz.springboot2elasticsearch.entity.join.JoinFamily;
import com.myz.springboot2elasticsearch.es.constants.MemberTypeEnum;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.support.WriteRequest;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.xcontent.XContentType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.elasticsearch.core.join.JoinField;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Random;

/**
 * @author maoyz0621 on 2022/4/21
 * @version v1.0
 */
@Component
@Slf4j
@AllArgsConstructor(onConstructor_ = {@Autowired})
public class JoinFamilyService {
    private final RestHighLevelClient restHighLevelClient;
    @Autowired
    @Qualifier("objectMapperNoNull")
    private ObjectMapper objectMapper;

    public void addChild(String parentId, String name) throws Exception {
        this.addFamilyMember(parentId, name, MemberTypeEnum.Child);
    }

    public void addParent(String grandPaId, String name) throws Exception {
        this.addFamilyMember(grandPaId, name, MemberTypeEnum.Parent);
    }

    public void addGrandPa(String name) throws Exception {
        this.addFamilyMember(null, name, MemberTypeEnum.GrandParent);
    }

    public void addFamilyMember(String parentId, String name, MemberTypeEnum type) throws Exception {
        long id = new Random().nextInt(10000);
        log.info("id = {}", id);
        JoinFamily member = new JoinFamily();
        member.setMyId(id);
        member.setName(name);
        member.setLevel(type.getLevel());

        JoinField<String> joinField = new JoinField<>(type.getType());

        member.setJoinFiled(joinField);

        if (!MemberTypeEnum.GrandParent.equals(type)) {
            if (StringUtils.isEmpty(parentId)) {
                throw new IllegalStateException("parentId cannot be null");
            }
            joinField.setParent(parentId);
        }
        String source = objectMapper.writeValueAsString(member);
        log.info("source: " + source);
        IndexRequest indexRequest = new IndexRequest("join_family").id(id + "")
                .source(source, XContentType.JSON)
                .setRefreshPolicy(WriteRequest.RefreshPolicy.IMMEDIATE);

        if (!MemberTypeEnum.GrandParent.equals(type)) {
            indexRequest.routing(parentId);
        }
        try {
            IndexResponse out = restHighLevelClient.index(indexRequest, RequestOptions.DEFAULT);
            log.info(out.getId());
        } catch (IOException e) {
            log.error("", e);
        }
    }
}
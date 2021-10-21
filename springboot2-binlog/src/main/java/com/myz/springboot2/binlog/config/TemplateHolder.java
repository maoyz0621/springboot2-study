/**
 * Copyright 2021 Inc.
 **/
package com.myz.springboot2.binlog.config;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.myz.springboot2.binlog.domain.ParseTemplate;
import com.myz.springboot2.binlog.domain.SchemaColumnsDo;
import com.myz.springboot2.binlog.domain.TableTemplate;
import com.myz.springboot2.binlog.utils.ConvertUtils;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

import javax.annotation.PostConstruct;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author maoyz0621 on 2021/8/25
 * @version v1.0
 */
@Component
public class TemplateHolder {

    private ParseTemplate template = new ParseTemplate();

    @Autowired
    private OtherDbProperties otherDbProperties;

    // private final SchemaColumnsRepository schemaColumnsRepository;

    private final JdbcTemplate jdbcTemplate;

    String sql = "select TABLE_SCHEMA, TABLE_NAME, COLUMN_NAME, ORDINAL_POSITION, COLUMN_DEFAULT, IS_NULLABLE," +
            "DATA_TYPE, CHARACTER_MAXIMUM_LENGTH, CHARACTER_OCTET_LENGTH, NUMERIC_PRECISION, NUMERIC_SCALE," +
            "CHARACTER_SET_NAME, COLLATION_NAME from INFORMATION_SCHEMA.COLUMNS WHERE table_schema = ? AND table_name = ?";

    @Autowired
    public TemplateHolder(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @PostConstruct
    public void init() {
        loadMeta();
    }

    public TableTemplate getTable(String tableName) {
        return template.getTableTemplateMap().get(tableName);
    }

    private void loadMeta() {


        // todo database 和 table 配置文件
        List<String> databases = otherDbProperties.getDatabases();
        if (CollectionUtils.isEmpty(databases)) {
            return;
        }

        List<Map<String, Object>> schemaInfos = Lists.newArrayListWithExpectedSize(databases.size());
        for (String database : databases) {
            String[] split = StringUtils.split(database, ".");

            Assert.notNull(split[0], "database can not null");
            Assert.notNull(split[1], "table can not null");

            schemaInfos.addAll(jdbcTemplate.queryForList(sql, new Object[]{split[0], split[1]}));
        }

        if (CollectionUtils.isEmpty(schemaInfos)) {
            return;
        }

        List<SchemaColumnsDo> schemaColumnsList = Lists.newArrayListWithExpectedSize(schemaInfos.size());
        for (Map<String, Object> schemaInfo : schemaInfos) {
            System.out.println(schemaInfo);
            try {
                SchemaColumnsDo schemaColumnsDo = ConvertUtils.mapConvertToBean(schemaInfo, SchemaColumnsDo.class);
                schemaColumnsList.add(schemaColumnsDo);
            } catch (Exception e) {
                // todo
            }

        }
        Map<String, TableTemplate> tableTemplateMap = Maps.newHashMapWithExpectedSize(schemaColumnsList.size());

        // table_name分组
        Map<String, List<SchemaColumnsDo>> collect = schemaColumnsList.stream().collect(Collectors.groupingBy(e -> e.getTableSchema() + "_" + e.getTableName(), Collectors.toList()));
        for (Map.Entry<String, List<SchemaColumnsDo>> entry : collect.entrySet()) {
            Map<Integer, String> posMap = new HashMap<>();

            List<SchemaColumnsDo> value = entry.getValue();
            for (SchemaColumnsDo param : value) {
                posMap.put(param.getOrdinalPosition(), param.getColumnName());
            }
            TableTemplate tableTemplate = new TableTemplate();
            tableTemplate.setPosMap(posMap);
            tableTemplate.setTableName(value.get(0).getTableName());
            template.setDatabase(value.get(0).getTableSchema());
            tableTemplateMap.put(value.get(0).getTableName(), tableTemplate);
        }

        template.setTableTemplateMap(tableTemplateMap);
    }
}
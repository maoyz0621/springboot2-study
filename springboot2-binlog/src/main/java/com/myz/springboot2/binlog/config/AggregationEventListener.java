/**
 * Copyright 2021 Inc.
 **/
package com.myz.springboot2.binlog.config;

import com.github.shyiko.mysql.binlog.BinaryLogClient;
import com.github.shyiko.mysql.binlog.event.DeleteRowsEventData;
import com.github.shyiko.mysql.binlog.event.Event;
import com.github.shyiko.mysql.binlog.event.EventData;
import com.github.shyiko.mysql.binlog.event.EventType;
import com.github.shyiko.mysql.binlog.event.TableMapEventData;
import com.github.shyiko.mysql.binlog.event.UpdateRowsEventData;
import com.github.shyiko.mysql.binlog.event.WriteRowsEventData;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.myz.springboot2.binlog.domain.BinaryRowData;
import com.myz.springboot2.binlog.domain.TableTemplate;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.io.Serializable;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Event包含header和data两部分，header提供了event的创建时间，哪个服务器等信息，data部分提供的是针对该event的具体信息，如具体数据的修改。
 *
 * @author maoyz0621 on 2021/8/29
 * @version v1.0
 */
@Slf4j
@Component
public class AggregationEventListener implements BinaryLogClient.EventListener {

    private String database;
    private String table;
    private Map<String, IBinlogEventListener> eventListenerMap = new HashMap<>();
    @Resource
    IBinlogEventListener binlogEventListener;

    @Autowired
    private TemplateHolder templateHolder;

    public void register(String database, String table, IBinlogEventListener binlogEventListener) {
        eventListenerMap.put(genKey(database, table), binlogEventListener);
    }

    @Override
    public void onEvent(Event event) {
        EventType eventType = event.getHeader().getEventType();

        // table_map 记录操作所对应的表信息，存储了数据库名称和表名称
        if (EventType.TABLE_MAP == eventType) {
            TableMapEventData data = event.getData();
            this.database = data.getDatabase();
            this.table = data.getTable();
            return;
        }

        if (EventType.EXT_UPDATE_ROWS != eventType &&
                EventType.EXT_WRITE_ROWS != eventType && EventType.EXT_DELETE_ROWS != eventType) {
            return;
        }

        if (StringUtils.isBlank(database) || StringUtils.isBlank(table)) {
            log.error("");
            return;
        }


        String key = genKey(database, table);
        // IBinlogEventListener binlogEventListener = this.eventListenerMap.get(key);


        BinaryRowData binaryRowData = convert(event.getData());
        binaryRowData.setEventType(eventType);
        binlogEventListener.onEvent(binaryRowData);
    }

    private BinaryRowData convert(EventData eventData) {
        TableTemplate table = templateHolder.getTable(this.table);
        BinaryRowData binaryRowData = new BinaryRowData();
        binaryRowData.setBefore(getFieldList(getBeforeValues(eventData), table));
        binaryRowData.setAfter(getFieldList(getAfterValues(eventData), table));
        binaryRowData.setTableTemplate(table);
        return binaryRowData;
    }

    private List<Map<String, String>> getFieldList(List<Serializable[]> values, TableTemplate table) {
        if (CollectionUtils.isEmpty(values)) {
            return null;
        }

        List<Map<String, String>> fieldList = Lists.newArrayListWithExpectedSize(values.size());
        for (Serializable[] fieldValue : values) {
            Map<String, String> fieldMap = Maps.newHashMap();
            int len = fieldValue.length;
            for (int i = 0; i < len; i++) {
                // 列名
                String columnName = table.getPosMap().get(i + 1);
                fieldMap.put(columnName, null != fieldValue[i] ? fieldValue[i].toString() : null);
            }
            fieldList.add(fieldMap);
        }
        return fieldList;
    }


    public String genKey(String database, String table) {
        return database + ":" + table;
    }


    /**
     * 获取after column
     *
     * @param eventData
     * @return
     */
    private List<Serializable[]> getAfterValues(EventData eventData) {
        if (eventData instanceof WriteRowsEventData) {
            return ((WriteRowsEventData) eventData).getRows();
        } else if (eventData instanceof UpdateRowsEventData) {
            System.out.println(eventData.toString());
            return ((UpdateRowsEventData) eventData).getRows().stream().map(Map.Entry::getValue).collect(Collectors.toList());
        } else if (eventData instanceof DeleteRowsEventData) {
            return ((DeleteRowsEventData) eventData).getRows();
        }
        return Collections.emptyList();
    }

    /**
     * 获取before column
     *
     * @param eventData
     * @return
     */
    private List<Serializable[]> getBeforeValues(EventData eventData) {
        if ((eventData instanceof UpdateRowsEventData)) {
            return ((UpdateRowsEventData) eventData).getRows().stream().map(Map.Entry::getKey).collect(Collectors.toList());
        }
        return Collections.emptyList();
    }
}
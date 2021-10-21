/**
 * Copyright 2021 Inc.
 **/
package com.myz.springboot2.binlog.message;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.myz.springboot2.binlog.domain.OperationTypeEnum;
import org.apache.commons.collections4.MapUtils;

import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * @author maoyz0621 on 2021/8/29
 * @version v1.0
 */
public class WaresBuilder {

    private WaresBuilder() {
    }

    public static List<Wares> build(FlatMessage flatMessage) {
        List<Wares> waresList = Lists.newArrayList();
        if (Objects.isNull(flatMessage)) {
            return waresList;
        }

        int len = flatMessage.getData().size();
        if (len <= 0) {
            return waresList;
        }

        Wares wares = new Wares();
        wares.setSchema(flatMessage.getDatabase());
        wares.setTable(flatMessage.getTable());
        wares.setIsDdl(flatMessage.getIsDdl());
        wares.setEventType(flatMessage.getEventType());
        wares.setBinlogFileName(flatMessage.getLogFileName());
        wares.setBinlogPosition(flatMessage.getLogFileOffset());
        wares.setPkNames(flatMessage.getPkNames());
        wares.setMysqlType(flatMessage.getMysqlType());
        for (int i = 0; i < len; i++) {
            Map<String, String> afterColumn;
            if (OperationTypeEnum.INSERT.getType().equalsIgnoreCase(flatMessage.getEventType())) {
                afterColumn = flatMessage.getData().get(i);
                wares.setAfterColumn(afterColumn);
                wares.setBeforeColumn(Maps.newHashMap());
                wares.setChangeColumn(Maps.newHashMap());
            } else if (OperationTypeEnum.UPDATE.getType().equalsIgnoreCase(flatMessage.getEventType())) {
                afterColumn = flatMessage.getData().get(i);
                wares.setAfterColumn(afterColumn);
                wares.setBeforeColumn(difference(afterColumn, flatMessage.getOld().get(i)));
                wares.setChangeColumn(flatMessage.getOld().get(i));
            } else if (OperationTypeEnum.DELETE.getType().equalsIgnoreCase(flatMessage.getEventType())) {
                afterColumn = flatMessage.getData().get(i);
                wares.setAfterColumn(Maps.newHashMap());
                wares.setBeforeColumn(afterColumn);
                wares.setChangeColumn(Maps.newHashMap());
            }
            waresList.add(wares);
        }
        return waresList;
    }

    public static Map<String, String> difference(Map<String, String> first, Map<String, String> second) {
        if (MapUtils.isEmpty(first)) {
            return Maps.newHashMap();
        } else if (MapUtils.isEmpty(second)) {
            return first;
        } else {
            Map<String, String> diff = Maps.newHashMapWithExpectedSize(first.size());
            for (Map.Entry<String, String> entry : first.entrySet()) {
                diff.put(entry.getKey(), second.containsKey(entry.getKey()) ? second.get(entry.getKey()) : entry.getValue());
            }
            return diff;
        }
    }

    public static Map<String, String> diff(Map<String, String> first, Map<String, String> second) {
        if (MapUtils.isEmpty(first)) {
            return Maps.newHashMap();
        } else if (MapUtils.isEmpty(second)) {
            return first;
        } else {
            Map<String, String> diff = Maps.newHashMapWithExpectedSize(first.size());
            for (Map.Entry<String, String> entry : first.entrySet()) {
                second.get(entry.getKey());
                if ( !Objects.equals(entry.getValue(), second.get(entry.getKey()))) {
                    diff.put(entry.getKey(), second.containsKey(entry.getKey()) ? second.get(entry.getKey()) : entry.getValue());
                }
            }
            return diff;
        }
    }
}
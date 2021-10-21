/**
 * Copyright 2021 Inc.
 **/
package com.myz.springboot2.canal.config;

import com.alibaba.otter.canal.protocol.CanalEntry.Column;
import com.alibaba.otter.canal.protocol.CanalEntry.Entry;
import com.alibaba.otter.canal.protocol.CanalEntry.Header;
import com.alibaba.otter.canal.protocol.CanalEntry.Pair;
import com.alibaba.otter.canal.protocol.Message;

import java.util.List;

/**
 * @author maoyz0621 on 2021/9/11
 * @version v1.0
 */
public class BaseCanalClient {

    protected void printSummary(Message message, long batchId, int size) {

    }

    void buildPositionForDump(Entry entry) {
    }

    void printEntry(List<Entry> entrys) {
    }

    void printColumn(List<Column> columns) {
    }

    void printXaInfo(List<Pair> pairs) {
    }

    String getCurrentGtid(Header header) {
        return null;
    }
}
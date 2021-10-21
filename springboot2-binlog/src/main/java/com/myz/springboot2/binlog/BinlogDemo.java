/**
 * Copyright 2021 Inc.
 **/
package com.myz.springboot2.binlog;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.shyiko.mysql.binlog.BinaryLogClient;
import com.github.shyiko.mysql.binlog.BinaryLogFileReader;
import com.github.shyiko.mysql.binlog.event.DeleteRowsEventData;
import com.github.shyiko.mysql.binlog.event.Event;
import com.github.shyiko.mysql.binlog.event.EventData;
import com.github.shyiko.mysql.binlog.event.EventType;
import com.github.shyiko.mysql.binlog.event.TableMapEventData;
import com.github.shyiko.mysql.binlog.event.TableMapEventMetadata;
import com.github.shyiko.mysql.binlog.event.UpdateRowsEventData;
import com.github.shyiko.mysql.binlog.event.WriteRowsEventData;
import com.github.shyiko.mysql.binlog.event.deserialization.EventDeserializer;

import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

/**
 * @author maoyz0621 on 2021/8/22
 * @version v1.0
 */
public class BinlogDemo {

    static ObjectMapper objectMapper = new ObjectMapper();

    public static void main(String[] args) throws IOException {
        file();
        // client();
    }

    public static void file() {
        // mysql binlog日志文件
        File binlogFile = new File("E:\\WorkWare\\mysql-8.0.13-winx64\\data\\mysql-binlog.000002");
        EventDeserializer eventDeserializer = new EventDeserializer();
        eventDeserializer.setCompatibilityMode(
                // EventDeserializer.CompatibilityMode.DATE_AND_TIME_AS_LONG,
                EventDeserializer.CompatibilityMode.CHAR_AND_BINARY_AS_BYTE_ARRAY);

        BinaryLogFileReader binaryLogFileReader = null;
        try {
            binaryLogFileReader = new BinaryLogFileReader(binlogFile, eventDeserializer);
            for (Event event; (event = binaryLogFileReader.readEvent()) != null; ) {
                EventType eventType = event.getHeader().getEventType();
                EventType.isRowMutation(eventType);
                EventData data = event.getData();
                if (data instanceof TableMapEventData) {
                    System.out.println("Table");
                    TableMapEventData tableMapEventData = (TableMapEventData) data;
                    System.out.println(tableMapEventData.getTableId() + ": [" + tableMapEventData.getDatabase() + "-" + tableMapEventData.getTable() + "]");
                    System.out.println(tableMapEventData.getEventMetadata().getColumnNames());
                }


                if (data instanceof TableMapEventMetadata) {
                    TableMapEventMetadata tableMapEventMetadata = (TableMapEventMetadata) data;
                    System.out.println(tableMapEventMetadata);
                }

                if (EventType.isUpdate(eventType)) {
                    UpdateRowsEventData eventData = (UpdateRowsEventData) event.getData();
                    System.out.println("Update:");
                    System.out.println(eventData.toString());
                }

                if (EventType.isWrite(eventType)) {
                    WriteRowsEventData eventData = (WriteRowsEventData) event.getData();
                    System.out.println("Insert:");
                    System.out.println(eventData.toString());
                }

                if (EventType.isDelete(eventType)) {
                    DeleteRowsEventData eventData = (DeleteRowsEventData) event.getData();
                    System.out.println("Insert:");
                    System.out.println(eventData.toString());
                }

                System.out.println("------------------------------------------------------\r\n");

                // if (data instanceof UpdateRowsEventData) {
                //     System.out.println("Update:");
                //     System.out.println(data.toString());
                // } else if (data instanceof WriteRowsEventData) {
                //     System.out.println("Insert:");
                //     System.out.println(data.toString());
                // } else if (data instanceof DeleteRowsEventData) {
                //     System.out.println("Insert:");
                //     System.out.println(data.toString());
                // }
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                binaryLogFileReader.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static void client() {
        BinaryLogClient client = new BinaryLogClient("localhost", 3306, "root", "root");
        client.setServerId(1);
        EventDeserializer eventDeserializer = new EventDeserializer();
        eventDeserializer.setCompatibilityMode(
                EventDeserializer.CompatibilityMode.DATE_AND_TIME_AS_LONG
        );
        client.setEventDeserializer(eventDeserializer);

        // 监听事件
        client.registerEventListener(new BinaryLogClient.EventListener() {
            @Override
            public void onEvent(Event event) {
                EventData data = event.getData();
                if (data instanceof TableMapEventData) {
                    System.out.println("Table");
                    TableMapEventData tableMapEventData = (TableMapEventData) data;
                    System.out.println(tableMapEventData.getTableId() + ": [" + tableMapEventData.getDatabase() + "-" + tableMapEventData.getTable() + "]");
                }

                if (data instanceof UpdateRowsEventData) {
                    // UpdateRowsEventData{tableId=77, includedColumnsBeforeUpdate={0, 1, 2, 3, 4, 5, 6, 7, 8, 9}, includedColumns={0, 1, 2, 3, 4, 5, 6, 7, 8, 9}, rows=[
                    //     {before=[8, 1, null, null, 1629936096000, null, 1629936096000, null, 0, 0], after=[8, 2, null, null, 1629936096000, null, 1629936149000, null, 0, 0]}
                    // ]}
                    System.out.println("Update:");
                    System.out.println(data.toString());
                    UpdateRowsEventData updateRowsEventData = (UpdateRowsEventData) data;
                    for (Map.Entry<Serializable[], Serializable[]> row : updateRowsEventData.getRows()) {
                        List<Serializable> entries = Arrays.asList(row.getValue());
                        System.out.println(entries);
                        try {
                            String s = objectMapper.writeValueAsString(entries);
                            System.out.println(s);
                        } catch (JsonProcessingException e) {
                            e.printStackTrace();
                        }

                    }
                } else if (data instanceof WriteRowsEventData) {
                    System.out.println("Insert:");
                    System.out.println(data.toString());
                } else if (data instanceof DeleteRowsEventData) {
                    System.out.println("Delete:");
                    System.out.println(data.toString());
                }
            }
        });
        try {
            client.connect();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
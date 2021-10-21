/**
 * Copyright 2021 Inc.
 **/
package com.myz.springboot2.binlog.config;

import com.github.shyiko.mysql.binlog.BinaryLogClient;
import com.github.shyiko.mysql.binlog.event.deserialization.EventDeserializer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.io.IOException;

/**
 * @author maoyz0621 on 2021/8/29
 * @version v1.0
 */
@Component
public class CustomerBinlogClient {

    private BinaryLogClient client;

    private final BinaryLogClient.EventListener eventListener;
    private final BinlogConfig binlogConfig;

    @Autowired
    public CustomerBinlogClient(BinaryLogClient.EventListener eventListener, BinlogConfig binlogConfig) {
        this.eventListener = eventListener;
        this.binlogConfig = binlogConfig;
    }

    @PostConstruct
    public void start() {
        new Thread(() -> {
            client = new BinaryLogClient(binlogConfig.getHostname(),
                    binlogConfig.getPort(),
                    binlogConfig.getUsername(),
                    binlogConfig.getPassword());
            client.setServerId(1);
            EventDeserializer eventDeserializer = new EventDeserializer();
            eventDeserializer.setCompatibilityMode(
                    EventDeserializer.CompatibilityMode.DATE_AND_TIME_AS_LONG
            );
            client.setEventDeserializer(eventDeserializer);

            // 监听事件
            client.registerEventListener(eventListener);
            try {
                client.connect();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }).start();
    }
}
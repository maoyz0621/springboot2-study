/**
 * Copyright 2022 Inc.
 **/
package com.myz.handler;

import com.myz.event.ErrorEvent;
import com.myz.event.MyEvent;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.CompletableFuture;

/**
 * @author maoyz0621 on 2022/1/23
 * @version v1.0
 */
@Slf4j
@Component
public class EventHandler {

    public void doHandlerMyEvent() {
        for (int i = 0; i < 5; i++) {
            Map<String, String> param = new HashMap<>();
            param.put("a", "a1");
            param.put("b", "b1");
            param.put("index", i + "");
            CompletableFuture.runAsync(() -> {
                SpringApplicationEventPublishUtil.publishEvent(new MyEvent<>(param));
            });

        }
    }

    public void doHandlerErrorEvent() {
        for (int i = 0; i < 2; i++) {
            Map<String, String> param = new HashMap<>();
            param.put("a", "a1");
            param.put("b", "b1");
            param.put("index", i + "");
            SpringApplicationEventPublishUtil.publishEvent(new ErrorEvent<>(param));
        }
    }

    public void doHandlerErrorEventException() {
        for (int i = 0; i < 2; i++) {
            Map<String, String> param = new HashMap<>();
            param.put("a", "a1");
            param.put("b", "b1");
            param.put("index", i + "");
            SpringApplicationEventPublishUtil.publishEvent(new ErrorEvent<>(param));
            if (i == 1) {
                int j = 1 / 0;
            }
        }
    }
}
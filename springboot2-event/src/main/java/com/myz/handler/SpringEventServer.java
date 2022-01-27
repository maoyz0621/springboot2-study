/**
 * Copyright 2022 Inc.
 **/
package com.myz.handler;

import com.myz.config.SpringEventBinder;
import org.springframework.stereotype.Component;

import java.util.UUID;

/**
 * @author maoyz0621 on 2022/1/26
 * @version v1.0
 */
@Component
public class SpringEventServer {

    public void bind(){
        for (int i = 0; i < 5; i++) {
            SpringEventBinder.registerOnSpringLoader(() -> {
                System.out.println(UUID.randomUUID());
            });
        }
    }
}
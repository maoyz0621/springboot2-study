/**
 * Copyright 2021 Inc.
 **/
package com.myz.springboot2.binlog.config;

import lombok.Data;
import org.springframework.stereotype.Component;

/**
 * @author maoyz0621 on 2021/8/29
 * @version v1.0
 */
@Data
@Component
public class BinlogConfig {
    private String hostname = "127.0.0.1";
    private int port = 3306;
    private String schema;
    private String username = "root";
    private String password = "root";
}
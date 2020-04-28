/**
 * Copyright 2019 Inc.
 **/
package com.myz.springboot2error.controller;

import com.myz.springboot2error.service.ErrorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * producers 生产者　即response返回数据类型
 * consumers 消费者　即处理request请求
 *
 * @author maoyz0621 on 19-12-2
 * @version v1.0
 */
@RestController
public class IndexController {

    @GetMapping("/index")
    public String index() {
        return "index";
    }

    @Autowired
    ErrorService errorService;

    @GetMapping("/index1")
    public String index1(Integer i) {
        errorService.test(i);
        return "index1";
    }

    /**
     * 处理Content-Type=application/json
     *
     * @return
     */
    @PostMapping(value = "/post", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public String post() {
        int i = 1 / 0;
        return "post";
    }

    /**
     * 处理Content-Type=application/xml
     *
     * @return
     */
    @PostMapping(value = "/xml", consumes = MediaType.APPLICATION_XML_VALUE)
    public String xml() {
        int i = 1 / 0;
        return "xml";
    }
}

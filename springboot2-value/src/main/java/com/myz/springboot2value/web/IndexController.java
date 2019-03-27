/**
 * Copyright 2019 asiainfo Inc.
 **/
package com.myz.springboot2value.web;

import com.myz.springboot2value.service.IndexService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author maoyz0621 on 19-1-8
 * @version: v1.0
 */
@RestController
public class IndexController {
    private static final Logger logger = LoggerFactory.getLogger(IndexController.class);

    @Value("${web.user.name}")
    private String name;

    @Autowired
    private IndexService indexService;

    @GetMapping("/index")
    public String index(){
        logger.debug("name = {}", name);
        return name;
    }

    @GetMapping("/get")
    public String get(){
        return indexService.getPath();
    }
}

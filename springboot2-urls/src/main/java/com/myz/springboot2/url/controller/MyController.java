/**
 * Copyright 2023 Inc.
 **/
package com.myz.springboot2.url.controller;

import com.myz.springboot2.url.config.UrlMappingManager;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author maoyz0621 on 2023/9/11
 * @version v1.0
 */
@RestController
@RequestMapping("/urls")
@Slf4j
public class MyController {

    @Autowired
    UrlMappingManager urlMappingManager;

    @GetMapping("/get")
    public Map get(String userBean) {
        return new HashMap<>();
    }

    @PostMapping("/post")
    public Map post(String userBean) {
        log.info("post {}", userBean);
        return new HashMap<>();
    }

    @GetMapping("/all")
    public Map all(String userBean) {
        log.info("post {}", userBean);
        List<Map<String, String>> list = UrlMappingManager.getList();
        Map<String, String> map = UrlMappingManager.getMap();

        return new HashMap<>();
    }
}
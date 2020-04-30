/**
 * Copyright 2019 asiainfo Inc.
 **/
package com.myz.springboot2value.web;

import com.myz.springboot2value.config.MyProp;
import com.myz.springboot2value.service.IndexService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author maoyz0621 on 19-1-8
 * @version: v1.0
 */
@RestController
public class IndexController {
    private static final Logger logger = LoggerFactory.getLogger(IndexController.class);

    @Value("${web.user.name}")
    private String name;

    // list1: 1,2,3
    @Value("#{'${list1}'.split(',')}")
    private List<Integer> list1;

    // list2:
    //   - 1
    //   - 2
    //   - 3
    // @Value("#{'${list2}'}")
    private List<String> list2 = new ArrayList<>();

    // map0: "{key1: 'value1', key2: 'value2'}"
    @Value("#{${map0}}")
    private Map<String, String> map0;

    // map1:
    //   k1: v1
    //   k2: v2
    // @Value("#{${map1}}")
    private Map<String, String> map1;

    @Autowired
    private IndexService indexService;

    @Autowired
    private MyProp prop;

    @GetMapping("/index")
    public String index() {
        logger.debug("name = {}", name);
        System.out.println(list1);
        System.out.println(list2);
        System.out.println(map0);
        System.out.println(map1);
        return name;
    }

    @GetMapping("/prop")
    public String prop() {
        return prop.toString();
    }

    @GetMapping("/get")
    public String get() {
        return indexService.getPath();
    }
}

/**
 * Copyright 2018 asiainfo Inc.
 **/
package com.myz.log.aop.module.web;


import com.myz.log.aop.module.service.IIndexService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author maoyz0621 on 2018/12/28
 * @version: v1.0
 */
@RestController
@RequestMapping("/aop")
public class IndexController {

    @Autowired
    private IIndexService indexService;

    @GetMapping("/get")
    public String index(){
        String index = indexService.index("aaa");
        return index;
    }
}

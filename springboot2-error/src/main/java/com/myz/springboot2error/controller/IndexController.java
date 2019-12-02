/**
 * Copyright 2019 Inc.
 **/
package com.myz.springboot2error.controller;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author maoyz0621 on 19-12-2
 * @version: v1.0
 */
@RestController
public class IndexController {

    @GetMapping("/index")
    public String index() {
        return "index";
    }

    @GetMapping("/index1")
    public String index1() {
        int i = 1 / 0;
        return "index1";
    }

    @PostMapping(value = "/post", produces = MediaType.APPLICATION_JSON_VALUE)
    public String post() {
        int i = 1 / 0;
        return "post";
    }

    @PostMapping(value = "/xml", produces = MediaType.APPLICATION_XML_VALUE)
    public String xml() {
        int i = 1 / 0;
        return "xml";
    }
}

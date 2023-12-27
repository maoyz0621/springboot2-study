/**
 * Copyright 2023 Inc.
 **/
package com.myz.springboot2.jackson.controller;

import com.myz.springboot2.jackson.domain.SingleBean;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author maoyz0621 on 2023/12/27
 * @version v1.0
 */
@RestController
@RequestMapping("/json")
@Slf4j
public class LombokOfJsonController {

    @PostMapping("/a")
    public SingleBean post(@RequestBody SingleBean singleBean) {
        return singleBean;
    }
}
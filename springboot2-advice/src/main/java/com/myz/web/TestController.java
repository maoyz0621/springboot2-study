/**
 * Copyright 2022 Inc.
 **/
package com.myz.web;

import com.myz.annotations.Mask;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author maoyz0621 on 2022/8/19
 * @version v1.0
 */
@RestController
public class TestController {

    @GetMapping("/index")
    @Mask(field = "phone")
    public String index1(Integer i) {
        return i + "";
    }
}
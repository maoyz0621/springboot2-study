/**
 * Copyright 2022 Inc.
 **/
package com.myz.web;

import com.myz.annotations.NeedLogin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author maoyz0621 on 2022/4/4
 * @version v1.0
 */
@RestController
public class MyController {

    @RequestMapping(value = "/a")
    @NeedLogin
    public String a() {
        return null;
    }

    @RequestMapping(value = "/b")
    public String b() {
        return null;
    }
}
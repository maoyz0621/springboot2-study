/**
 * Copyright 2023 Inc.
 **/
package com.myz.web;

import com.myz.annotations.NeedLogin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author maoyz0621 on 2023/5/10
 * @version v1.0
 */
@RestController
public class NoInterceptorController {

    ////////////////////////// 不走任何拦截器 /////////////////////////////////////////

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
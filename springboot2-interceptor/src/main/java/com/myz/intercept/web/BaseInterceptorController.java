/**
 * Copyright 2022 Inc.
 **/
package com.myz.intercept.web;

import com.myz.intercept.annotations.NeedLogin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author maoyz0621 on 2022/4/4
 * @version v1.0
 */
@RestController
@RequestMapping("/base")
public class BaseInterceptorController {

    /////////////////////// /base/** -> BaseInterceptor /////////////////////////////

    @RequestMapping(value = "/a")
    @NeedLogin
    public String baseA() {
        return null;
    }

}
/**
 * Copyright 2023 Inc.
 **/
package com.myz.intercept.web;

import com.myz.intercept.annotations.NeedLogin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author maoyz0621 on 2023/5/10
 * @version v1.0
 */
@RestController
@RequestMapping("/auth")
public class AuthInterceptorController {

    /////////////////////// /auth/** -> AuthenticationInterceptor /////////////////////////////

    @RequestMapping(value = "/a")
    @NeedLogin
    public String authA() {
        return null;
    }

    /////////////////////// /auth/login,/auth/logout 不走AuthenticationInterceptor /////////////////////////////

    @RequestMapping(value = "/login")
    @NeedLogin
    public String authLogin() {
        return null;
    }


}
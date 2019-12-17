package com.myz.springboot2.controller;

import com.myz.springboot2.pojo.User;
import com.myz.springboot2.service.IAuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @author maoyz
 */
@RestController
@RequestMapping(value = "/auth")
public class AuthController {

    @Autowired
    private IAuthService authService;

    @RequestMapping(value = "/register", method = RequestMethod.POST)
    public User register(@RequestParam User user) {
        User register = authService.register(user);
        return register;
    }

    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public String login(@RequestBody User user) {
        String login = authService.login(user.getUsername(), user.getPassword());
        return login;
    }

}

package com.myz.actuator.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author maoyz
 */
@RestController
public class IndexController {

    @GetMapping("/index")
    public String index() {
        return "index";
    }
}

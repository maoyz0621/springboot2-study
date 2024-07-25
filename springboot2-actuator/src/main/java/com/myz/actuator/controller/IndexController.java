package com.myz.actuator.controller;

import io.micrometer.core.annotation.Timed;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author maoyz
 */
@RestController
public class IndexController {

    @Timed(value = "index.time", description = "index time")
    @GetMapping("/index")
    public String index() {
        return "index";
    }
}

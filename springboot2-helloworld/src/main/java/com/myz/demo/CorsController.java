package com.myz.demo;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 跨域处理
 * @author maoyz on 18-4-8.
 */
@RestController
public class CorsController {

    // 跨域处理
    @GetMapping("/cors")
    public String test(){
        return "http:8881 处理跨域 cors";
    }
}

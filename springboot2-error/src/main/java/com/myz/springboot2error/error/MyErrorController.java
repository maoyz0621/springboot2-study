/**
 * Copyright 2019 Inc.
 **/
package com.myz.springboot2error.error;

import org.springframework.boot.autoconfigure.web.ErrorProperties;
import org.springframework.boot.autoconfigure.web.servlet.error.BasicErrorController;
import org.springframework.boot.web.servlet.error.ErrorAttributes;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/**
 * BasicErrorController，默认是处理 text/html类型请求的错误，可以继承该基类自定义处理更多的请求类型，
 * 添加公共方法并使用 @RequestMapping 注解的 produce属性指定处理类型。
 *
 * @author maoyz0621 on 19-12-2
 * @version: v1.0
 */
@Component
public class MyErrorController extends BasicErrorController {
    public MyErrorController(ErrorAttributes errorAttributes) {
        super(errorAttributes, new ErrorProperties());
    }

    // public MyErrorController(ErrorAttributes errorAttributes, ErrorProperties errorProperties) {
    //     super(errorAttributes, errorProperties);
    // }


    /**
     * 处理json 错误
     *
     * @param request
     * @return
     */
    @RequestMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public ResponseEntity<Map<String, Object>> jsonError(HttpServletRequest request) {
        return super.error(request);
    }

    /**
     * 处理xml 错误
     *
     * @param request
     * @return
     */
    @RequestMapping(produces = MediaType.APPLICATION_XML_VALUE)
    @ResponseBody
    public ResponseEntity<Map<String, Object>> xmlError(HttpServletRequest request) {
        return super.error(request);
    }
}

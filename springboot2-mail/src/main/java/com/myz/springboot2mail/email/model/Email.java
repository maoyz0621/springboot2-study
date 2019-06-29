/**
 * Copyright 2018 asiainfo Inc.
 **/
package com.myz.springboot2mail.email.model;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.HashMap;

/**
 * Mail封装类
 *
 * @author maoyz0621 on 18-12-24
 * @version: v1.0
 */
@Setter
@Getter
public class Email implements Serializable {

    private static final long serialVersionUID = -1698637068582559689L;

    //必填参数
    /**
     * 接收方邮件
     */
    private String[] email;
    /**
     * 主题
     */
    private String subject;
    /**
     * 邮件内容
     */
    private String content;

    //选填
    /**
     * 模板
     */
    private String template;
    /**
     * 自定义参数
     */
    private HashMap<String, String> kvMap;

    public Email() {
        super();
    }

    public Email(String[] email, String subject, String content, String template, HashMap<String, String> kvMap) {
        this.email = email;
        this.subject = subject;
        this.content = content;
        this.template = template;
        this.kvMap = kvMap;
    }
}

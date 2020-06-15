/**
 * Copyright 2018 asiainfo Inc.
 **/
package com.myz.springboot2mail.email.service;

import com.myz.springboot2mail.common.model.Result;
import com.myz.springboot2mail.email.model.Email;


/**
 * @author maoyz0621 on 18-12-24
 * @version: v1.0
 */
public interface IMailService {
    /**
     * 纯文本
     */
    Boolean send(Email mail) throws Exception;

    /**
     * 富文本
     */
    Boolean sendHtml(Email mail) throws Exception;

    /**
     * 模版发送 freemarker
     */
    Boolean sendFreemarker(Email mail) throws Exception;

    /**
     * 队列
     */
    void sendQueue(Email mail) throws Exception;

    /**
     * Redis 队列
     */
    void sendRedisQueue(Email mail) throws Exception;


    Result listMail(Email mail);
}


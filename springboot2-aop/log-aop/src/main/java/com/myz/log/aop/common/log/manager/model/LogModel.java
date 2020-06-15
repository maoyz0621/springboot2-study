/**
 * Copyright 2018 asiainfo Inc.
 **/
package com.myz.log.aop.common.log.manager.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

/**
 * 保存日志信息
 *
 * @author maoyz0621 on 2018/12/28
 * @version: v1.0
 */
@Setter
@Getter
@NoArgsConstructor
@ToString
public class LogModel {

    private Long logId;
    /**
     * 操作用户
     */
    private String userId;
    private String userName;
    /**
     * 模块
     */
    private String operateModel;
    /**
     * 操作类型
     */
    private String operateEvent;

    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createDate;

    /**
     * 操作内容
     */
    private String operateContent;
    /**
     * 备注
     */
    private String desc;

}

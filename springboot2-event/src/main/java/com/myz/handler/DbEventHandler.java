/**
 * Copyright 2022 Inc.
 **/
package com.myz.handler;

import com.myz.event.ErrorEvent;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;

/**
 * @author maoyz0621 on 2022/1/23
 * @version v1.0
 */
@Slf4j
@Component
public class DbEventHandler {

    @Resource
    JdbcTemplate jdbcTemplate;

    @Transactional
    public void doHandlerErrorEventTransactional() {
        Map<String, String> param = new HashMap<>();
        param.put("a", "a1");
        SpringApplicationEventPublishUtil.publishEvent(new ErrorEvent<>(param));

        jdbcTemplate.execute("INSERT INTO `user`(`name`, `age`, `age_enum`, `gender`, `grade`, `email`, `is_delete`, `operator`, `created_by`, `created_time`, `last_modified_by`, `last_modified_time`, `remarks`, `enabled`, `version`, `tenant_id`, `company_id`) VALUES " +
                "(NULL, 11, NULL, NULL, 2, 'updateByIdbb361b77-6ade-489a-9da2-afbd680576bc', 0, NULL, 'maoyz-insert', '2021-12-29 00:16:48', 'maoyz-update', '2022-01-05 23:24:20', NULL, 0, 1, 65231313678678678, NULL)");
        log.info("========================== doHandlerErrorEventTransactional =============================");
    }

    /**
     * 执行数据库事务
     */
    @Transactional
    public void doHandlerErrorEventTransactionalException() {
        Map<String, String> param = new HashMap<>();
        param.put("a", "a1");
        // 此时已发布事件
        SpringApplicationEventPublishUtil.publishEvent(new ErrorEvent<>(param));
        log.info("========================== doHandlerErrorEventTransactionalException =============================");

        // 执行数据库操作
        jdbcTemplate.execute("INSERT INTO `user`(`name`, `age`, `age_enum`, `gender`, `grade`, `email`, `is_delete`, `operator`, `created_by`, `created_time`, `last_modified_by`, `last_modified_time`, `remarks`, `enabled`, `version`, `tenant_id`, `company_id`) VALUES " +
                "(NULL, 11, NULL, NULL, 2, 'updateByIdbb361b77-6ade-489a-9da2-afbd680576bc', 0, NULL, 'maoyz-insert', '2021-12-29 00:16:48', 'maoyz-update', '2022-01-05 23:24:20', NULL, 0, 1, 65231313678678678, NULL)");

        // 异常，事务回滚
        // @TransactionalEventListener事件不会执行
        throw new RuntimeException();
    }
}
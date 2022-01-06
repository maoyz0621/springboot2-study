DROP TABLE IF EXISTS user;

CREATE TABLE user
(
    id                   BIGINT(20)                                                     NOT NULL AUTO_INCREMENT COMMENT '主键ID',
    name                 VARCHAR(30)                                                    NULL     DEFAULT NULL COMMENT '姓名',
    age                  INT(11)                                                        NULL     DEFAULT NULL COMMENT '年龄',
    ageEnum              INT(3)                                                         NULL     DEFAULT NULL COMMENT '年龄1',
    gender               INT(2)                                                         NULL     DEFAULT NULL COMMENT '性别,0:MALE, 1:FEMALE',
    grade                INT(3)                                                         NULL     DEFAULT NULL COMMENT '年级',
    email                VARCHAR(50)                                                    NULL     DEFAULT NULL COMMENT '邮箱',
    is_delete            INT(11)                                                        NOT NULL DEFAULT 0 COMMENT '是否删除',
    operator             VARCHAR(30)                                                    NULL     DEFAULT NULL COMMENT '操作员',
    `created_by`         varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci  NULL     DEFAULT NULL,
    `created_time`       datetime(0)                                                    NULL     DEFAULT NULL,
    `last_modified_by`   varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci  NULL     DEFAULT NULL,
    `last_modified_time` datetime(0)                                                    NULL     DEFAULT NULL,
    `remarks`            varchar(1000) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL     DEFAULT NULL,
    `enabled`            int(255)                                                       NULL     DEFAULT NULL,
    `version`            int(8)                                                         NULL     DEFAULT NULL COMMENT '版本',
    `tenant_id`          int(8)                                                         NULL     DEFAULT NULL COMMENT '租户',
    `company_id`         int(8)                                                         NULL     DEFAULT NULL COMMENT '公司id',
    PRIMARY KEY (id)
);

DROP TABLE IF EXISTS emp;
CREATE TABLE emp
(
    `id`                 bigint(20)                                                     NOT NULL AUTO_INCREMENT COMMENT '主键ID',
    `name`               varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci   NULL DEFAULT NULL COMMENT '姓名',
    `email`              varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci   NULL DEFAULT NULL COMMENT '邮箱',
    `created_by`         varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci  NULL DEFAULT NULL,
    `created_time`       datetime(0)                                                    NULL DEFAULT NULL,
    `last_modified_by`   varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci  NULL DEFAULT NULL,
    `last_modified_time` datetime(0)                                                    NULL DEFAULT NULL,
    `remarks`            varchar(1000) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
    `enabled`            int(255)                                                       NULL DEFAULT NULL,
    `version`            int(8)                                                         NULL DEFAULT NULL COMMENT '版本',
    `tenant_id`          int(8)                                                         NULL     DEFAULT NULL COMMENT '租户',
    `company_id`         int(8)                                                         NULL     DEFAULT NULL COMMENT '公司id',
    PRIMARY KEY (`id`)
);
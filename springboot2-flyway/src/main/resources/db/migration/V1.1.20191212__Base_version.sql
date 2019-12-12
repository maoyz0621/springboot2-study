DROP TABLE IF EXISTS `t_role`;
CREATE TABLE `t_role`
(
    `role_id`     int(11) unsigned NOT NULL AUTO_INCREMENT,
    `role_name`   varchar(255) DEFAULT NULL,
    `description` varchar(255) DEFAULT NULL,
    PRIMARY KEY (`role_id`)
) ENGINE = InnoDB
  AUTO_INCREMENT = 2
  DEFAULT CHARSET = utf8mb4;
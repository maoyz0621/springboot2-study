DROP TABLE IF EXISTS `test_user`;
CREATE TABLE `test_user`
(
    `USER_ID`  int(11) unsigned    NOT NULL AUTO_INCREMENT,
    `USERNAME` varchar(255)        NOT NULL DEFAULT '',
    `AGE`      tinyint(4) unsigned NOT NULL,
    `BIRTHDAY` timestamp           NOT NULL DEFAULT CURRENT_TIMESTAMP,
    `ROLE_ID`  int(11)             NOT NULL,
    PRIMARY KEY (`USER_ID`)
) ENGINE = InnoDB
  AUTO_INCREMENT = 2
  DEFAULT CHARSET = utf8mb4;
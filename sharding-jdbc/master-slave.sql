# 读写分离

# 读库1
CREATE database if NOT EXISTS `ds_master` default character set utf8mb4 collate utf8mb4_unicode_ci;
use `ds_master`;
SET NAMES utf8mb4;

SET FOREIGN_KEY_CHECKS = 0;

DROP TABLE IF EXISTS `t_order`;
CREATE TABLE `t_order`
(
  id       bigint(11) not null comment '主键ID',
  user_id  bigint(11) null comment '用户ID',
  order_id bigint(11) null comment '订单ID',
  username varchar (100) null comment '用户姓名',
  PRIMARY KEY (`id`)
) ENGINE = InnoDB
  AUTO_INCREMENT = 2
  DEFAULT CHARSET = utf8mb4;

INSERT INTO `t_order`
VALUES ('1', '101', '10','user0');

DROP TABLE IF EXISTS `t_order_item`;
CREATE TABLE `t_order_item`
(
  `id`       bigint(20) unsigned NOT NULL AUTO_INCREMENT,
  `order_id` bigint(20)          NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE = InnoDB
  AUTO_INCREMENT = 2
  DEFAULT CHARSET = utf8mb4;

INSERT INTO `t_order_item`
VALUES ('1', '10');


# 写库1
CREATE database if NOT EXISTS `ds_slave0` default character set utf8mb4 collate utf8mb4_unicode_ci;
use `ds_slave0`;
SET NAMES utf8mb4;

SET FOREIGN_KEY_CHECKS = 0;

DROP TABLE IF EXISTS `t_order`;
CREATE TABLE `t_order`
(
  id       bigint(11) not null comment '主键ID',
  user_id  bigint(11) null comment '用户ID',
  order_id bigint(11) null comment '订单ID',
  username varchar (100) null comment '用户姓名',
  PRIMARY KEY (`id`)
) ENGINE = InnoDB
  AUTO_INCREMENT = 2
  DEFAULT CHARSET = utf8mb4;

INSERT INTO `t_order`
VALUES ('1', '101', '10','user1');

DROP TABLE IF EXISTS `t_order_item`;
CREATE TABLE `t_order_item`
(
  `id`       bigint(20) unsigned NOT NULL AUTO_INCREMENT,
  `order_id` bigint(20)          NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE = InnoDB
  AUTO_INCREMENT = 2
  DEFAULT CHARSET = utf8mb4;

INSERT INTO `t_order_item`
VALUES ('1', '10');

# 写库2
CREATE database if NOT EXISTS `ds_slave1` default character set utf8mb4 collate utf8mb4_unicode_ci;
use `ds_slave1`;
SET NAMES utf8mb4;

SET FOREIGN_KEY_CHECKS = 0;

DROP TABLE IF EXISTS `t_order`;
CREATE TABLE `t_order`
(
  id       bigint(11) not null comment '主键ID',
  user_id  bigint(11) null comment '用户ID',
  order_id bigint(11) null comment '订单ID',
  username varchar (100) null comment '用户姓名',
  PRIMARY KEY (`id`)
) ENGINE = InnoDB
  AUTO_INCREMENT = 2
  DEFAULT CHARSET = utf8mb4;

INSERT INTO `t_order`
VALUES ('1', '101', '10','user2');

DROP TABLE IF EXISTS `t_order_item`;
CREATE TABLE `t_order_item`
(
  `id`       bigint(20) unsigned NOT NULL AUTO_INCREMENT,
  `order_id` bigint(20)          NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE = InnoDB
  AUTO_INCREMENT = 2
  DEFAULT CHARSET = utf8mb4;

INSERT INTO `t_order_item`
VALUES ('1', '10');

# 写库3
CREATE database if NOT EXISTS `ds_slave2` default character set utf8mb4 collate utf8mb4_unicode_ci;
use `ds_slave2`;
SET NAMES utf8mb4;

SET FOREIGN_KEY_CHECKS = 0;

DROP TABLE IF EXISTS `t_order`;
CREATE TABLE `t_order`
(
  id       bigint(11) not null comment '主键ID',
  user_id  bigint(11) null comment '用户ID',
  order_id bigint(11) null comment '订单ID',
  username varchar (100) null comment '用户姓名',
  PRIMARY KEY (`id`)
) ENGINE = InnoDB
  AUTO_INCREMENT = 2
  DEFAULT CHARSET = utf8mb4;

INSERT INTO `t_order`
VALUES ('1', '101', '10','user3');

DROP TABLE IF EXISTS `t_order_item`;
CREATE TABLE `t_order_item`
(
  `id`       bigint(20) unsigned NOT NULL AUTO_INCREMENT,
  `order_id` bigint(20)          NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE = InnoDB
  AUTO_INCREMENT = 2
  DEFAULT CHARSET = utf8mb4;

INSERT INTO `t_order_item`
VALUES ('1', '10');
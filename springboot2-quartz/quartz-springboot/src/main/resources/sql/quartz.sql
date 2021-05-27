
SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for `task_schedule_job`
-- ----------------------------
DROP TABLE IF EXISTS `task_schedule_job`;
CREATE TABLE `task_schedule_job` (
  `schedule_job_id` bigint(18) NOT NULL AUTO_INCREMENT,
  `create_time` datetime NULL DEFAULT NULL,
  `update_time` datetime NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `job_name` varchar(255) NOT NULL,
  `job_group` varchar(255) NOT NULL,
  `job_status` varchar(255) DEFAULT "1",
  `cron_expression` varchar(255) NOT NULL,
  `description` varchar(255) DEFAULT NULL,
  `bean_class` varchar(255) DEFAULT NULL,
  `is_concurrent` varchar(255) DEFAULT NULL COMMENT '1',
  `spring_id` varchar(255) DEFAULT NULL,
  `method_name` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`schedule_job_id`),
  UNIQUE KEY `name_group` (`job_name`,`job_group`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of task_schedule_job
-- ----------------------------

INSERT INTO `task_schedule_job` ( `create_time`, `update_time`, `job_name`, `job_group`, `job_status`, `cron_expression`, `description`, `bean_class`, `is_concurrent`, `spring_id`, `method_name`)
VALUES
	( NULL, '2018-05-12 15:09:34', 'test', 'test', '1', '0/1 * * * * ?', 'test', 'com.asiainfo.vehicle.executeTask.TestTask', '0', NULL, 'run1'),
	(NULL, '2014-04-25 14:17:19', 'test1', 'test', '0', '0/5 * * * * ?', 'test2', NULL, '1', 'taskTest', 'run'),
	('2014-04-25 16:52:17', '2018-05-12 15:09:39', '中间', '1111', '0', '0/1 * * * * ?', 'xxx', 'com.snailxr.base.executeTask.TestTask', '1', '', 'run');

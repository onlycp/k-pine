-- ----------------------------
-- Table structure for dev_sql_run
-- ----------------------------
CREATE TABLE IF NOT EXISTS `dev_sql_run` (  `id` varchar(36) COLLATE utf8mb4_bin NOT NULL COMMENT 'id',  `version` int(11) NOT NULL COMMENT '关联版本号',  `md5` varchar(100) COLLATE utf8mb4_bin DEFAULT NULL COMMENT 'md5',  `when_created` timestamp NULL DEFAULT NULL COMMENT '执行时间',  `execution_time` int(11) DEFAULT NULL COMMENT '执行时长（毫秒）',  `success` tinyint(4) NOT NULL COMMENT '是否成功',  PRIMARY KEY (`id`)) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_bin;


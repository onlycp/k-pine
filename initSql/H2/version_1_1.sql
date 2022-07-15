-- ----------------------------
-- Table structure for dev_sql_run
-- ----------------------------
CREATE TABLE IF NOT EXISTS `dev_sql_run` (  `id` varchar(36) NOT NULL ,  `version` int(11) NOT NULL ,  `md5` varchar(100) DEFAULT NULL ,  `when_created` timestamp NULL DEFAULT NULL ,  `execution_time` int(11) DEFAULT NULL ,  `success` tinyint(4) NOT NULL ,  PRIMARY KEY (`id`)) ;


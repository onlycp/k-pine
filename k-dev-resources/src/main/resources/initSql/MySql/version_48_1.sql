ALTER TABLE `sys_login_log`
ADD COLUMN `address` varchar(255) NULL COMMENT '位置' AFTER `when_created`;

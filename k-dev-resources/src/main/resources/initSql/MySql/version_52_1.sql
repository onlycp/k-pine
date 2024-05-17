ALTER TABLE `sys_api`
    ADD COLUMN `cache_enable` int(8) NULL COMMENT '是否启用接口缓存' AFTER `module_id`,
    ADD COLUMN `cache_cron` varchar(255) NULL COMMENT '缓存刷新表达式' AFTER `cache_enable`,
    ADD COLUMN `cache_expire_time` int(10) NULL COMMENT '缓存失效时间(秒)' AFTER `cache_cron`;

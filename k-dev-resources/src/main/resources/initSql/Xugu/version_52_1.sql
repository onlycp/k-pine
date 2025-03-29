ALTER TABLE sys_menu
    ADD COLUMN affix INT DEFAULT 0 COMMENT '是否固定页签';

ALTER TABLE sys_api
    ADD COLUMN cache_enable INT DEFAULT NULL COMMENT '是否启用接口缓存';

ALTER TABLE sys_api
    ADD COLUMN cache_cron VARCHAR(255) DEFAULT NULL COMMENT '缓存刷新表达式';

ALTER TABLE sys_api
    ADD COLUMN cache_expire_time INT DEFAULT NULL COMMENT '缓存失效时间(秒)';


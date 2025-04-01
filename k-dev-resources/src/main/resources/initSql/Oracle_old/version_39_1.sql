ALTER TABLE sys_menu ADD affix NUMBER(10) DEFAULT 0 NULL;
ALTER TABLE sys_api
    ADD cache_enable NUMBER(8) NULL;
COMMENT ON COLUMN sys_api.cache_enable IS '是否启用接口缓存';
ALTER TABLE sys_api
    ADD cache_cron VARCHAR2(255 CHAR) NULL;
COMMENT ON COLUMN sys_api.cache_cron IS '缓存刷新表达式';
ALTER TABLE sys_api
    ADD cache_expire_time NUMBER(10) NULL;
COMMENT ON COLUMN sys_api.cache_expire_time IS '缓存失效时间(秒)';

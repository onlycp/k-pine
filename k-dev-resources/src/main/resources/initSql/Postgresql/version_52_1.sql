
ALTER TABLE sys_menu ADD COLUMN affix int NULL default 0;
COMMENT ON COLUMN sys_menu.affix IS '是否固定页签';
ALTER TABLE sys_api
    ADD COLUMN cache_enable integer,
    ADD COLUMN cache_cron varchar(128),
    ADD COLUMN cache_expire_time integer;

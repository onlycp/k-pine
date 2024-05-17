alter table sys_menu add affix integer null default 0;
ALTER TABLE sys_api add cache_enable integer null;
ALTER TABLE sys_api add cache_expire_time integer null;
ALTER TABLE sys_api add cache_cron VARCHAR(64) null;

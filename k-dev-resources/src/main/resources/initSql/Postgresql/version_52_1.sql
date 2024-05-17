ALTER TABLE sys_api
    ADD COLUMN cache_enable integer,
    ADD COLUMN cache_cron varchar(128),
    ADD COLUMN cache_expire_time integer;

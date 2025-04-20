ALTER TABLE sys_config modify is_public integer null default null;
update sys_config set is_public = null WHERE is_public = 0;
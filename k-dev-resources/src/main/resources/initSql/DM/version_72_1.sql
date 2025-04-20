alter table sys_config modify is_public integer NULL DEFAULT null;
update sys_config set is_public = null WHERE is_public = 0;
ALTER TABLE sys_config ALTER COLUMN is_public  set DEFAULT null;
update sys_config set is_public = null WHERE is_public = 0;
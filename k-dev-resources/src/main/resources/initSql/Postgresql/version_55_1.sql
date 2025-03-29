ALTER TABLE sys_menu ADD COLUMN active_icon varchar(255) NULL;

ALTER TABLE sys_config ADD COLUMN is_public int NULL default 0;

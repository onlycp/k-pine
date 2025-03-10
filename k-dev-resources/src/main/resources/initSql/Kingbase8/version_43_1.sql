ALTER TABLE sys_menu
    ADD COLUMN active_icon varchar(255) NULL;
alter table sys_config add is_public integer null default 0;

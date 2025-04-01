ALTER TABLE sys_menu
    ADD active_icon VARCHAR(255) NULL;
alter table public.sys_config add is_public integer null default 0;

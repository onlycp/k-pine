
ALTER TABLE sys_menu ADD COLUMN affix int NULL default 0;
COMMENT ON COLUMN sys_menu.affix IS '是否固定页签';
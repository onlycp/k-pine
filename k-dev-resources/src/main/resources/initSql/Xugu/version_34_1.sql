
ALTER TABLE sys_unit
    ADD COLUMN unit_level INT DEFAULT NULL COMMENT '机构级别';


ALTER TABLE sys_search_config
    ADD COLUMN search_columns TEXT DEFAULT NULL COMMENT '搜索字段中文';
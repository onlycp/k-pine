ALTER TABLE sys_unit
    ADD unit_level INT NULL COMMENT '机构级别';

ALTER TABLE sys_search_config
    ADD search_columns TEXT NULL COMMENT '搜索字段中文';

ALTER TABLE sys_search_config
    MODIFY COLUMN columns VARCHAR(1000) NULL COMMENT '搜索字段';

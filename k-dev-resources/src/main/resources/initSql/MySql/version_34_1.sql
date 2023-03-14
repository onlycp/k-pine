ALTER TABLE sys_unit ADD unit_level int NULL COMMENT '机构级别';
ALTER TABLE sys_search_config ADD search_columns text NULL COMMENT '搜索字段中文';
ALTER TABLE sys_search_config MODIFY COLUMN `columns` varchar(1000) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL COMMENT '搜索字段';

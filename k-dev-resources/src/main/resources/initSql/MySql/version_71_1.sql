ALTER TABLE sys_logic_flow
    ADD COLUMN i18n_keys text NULL COMMENT '国际化键名' ;
ALTER TABLE dev_application MODIFY COLUMN `short_name` varchar(200) NULL DEFAULT NULL COMMENT '应用短英文名（用于数据库等前缀命名），- 必须唯一，- 必须为纯英文、小写字母命名- 用于创建业务目录、数据库，';

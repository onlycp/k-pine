ALTER TABLE open_account ADD COLUMN app_id varchar(36) NULL;
ALTER TABLE sys_notice
    ADD COLUMN is_force tinyint(4) DEFAULT NULL COMMENT '是否重要 0: 否 1：是',
    ADD COLUMN config LONGTEXT DEFAULT NULL COMMENT '团队头像';


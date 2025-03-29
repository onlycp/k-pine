ALTER TABLE dev_team
    ADD COLUMN is_audit tinyint(4) DEFAULT NULL COMMENT '是否审核 0: 否 1：是',
    ADD COLUMN image varchar(255) DEFAULT NULL COMMENT '团队头像';

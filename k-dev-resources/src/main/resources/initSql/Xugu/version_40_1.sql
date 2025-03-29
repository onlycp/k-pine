ALTER TABLE dev_application
    MODIFY COLUMN enable_status INT DEFAULT NULL COMMENT '可用状态' ;

ALTER TABLE dev_application
    MODIFY COLUMN dev_status INT DEFAULT NULL COMMENT '开发状态，0: 新建1: 确认版本2: 有更新';

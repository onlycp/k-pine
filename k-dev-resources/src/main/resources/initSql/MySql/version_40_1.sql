ALTER TABLE dev_application
    MODIFY COLUMN enable_status int(4) NULL DEFAULT NULL COMMENT '可用状态' AFTER description,
    MODIFY COLUMN dev_status int(4) NULL DEFAULT NULL COMMENT '开发状态，0: 新建1: 确认版本2: 有更新' AFTER enable_status;

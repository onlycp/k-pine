ALTER TABLE open_api_log
    MODIFY COLUMN use_time INT NULL COMMENT '响应时间(秒)';

ALTER TABLE open_api_log
    MODIFY COLUMN error_message TEXT NULL COMMENT '错误信息';

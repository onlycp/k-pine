-- 修改字段 `use_time`
ALTER TABLE open_api_log
    MODIFY COLUMN use_time INT DEFAULT NULL COMMENT '响应时间(秒)';

-- 修改字段 `error_message`
ALTER TABLE open_api_log
    MODIFY COLUMN error_message TEXT DEFAULT NULL COMMENT '错误信息';

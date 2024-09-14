ALTER TABLE `open_api_log`
MODIFY COLUMN `use_time` int NULL COMMENT '响应时间(秒)' AFTER `request_ip`;
ALTER TABLE `open_api_log`
MODIFY COLUMN `error_message` text CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL COMMENT '错误信息' AFTER `success`;

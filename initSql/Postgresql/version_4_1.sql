ALTER TABLE `dev_application_version_history` ADD COLUMN `file_name` varchar(255) DEFAULT NULL COMMENT '文件名' AFTER `version`;

ALTER TABLE `dev_application_version_history` ADD COLUMN `note` varchar(255) DEFAULT NULL COMMENT '备注' AFTER `file_name`;

ALTER TABLE `dev_application_version_history` ADD COLUMN `export_data` text COMMENT '导出数据参数' AFTER `note`;

ALTER TABLE `sys_logic_flow` ADD COLUMN `default_source_name` varchar(100) DEFAULT NULL COMMENT '默认数据源' AFTER `app_id`;
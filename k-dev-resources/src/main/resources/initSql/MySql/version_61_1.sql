ALTER TABLE `dev_application`
    ADD COLUMN `app_namespace` varchar(255) NULL COMMENT '应用命名空间';

ALTER TABLE `dev_model_latest` ADD COLUMN `custom_type_mapping` text COMMENT '用户自定义类型映射，当用户导入表出现系统未适配的字段类型时，提醒用户选择要转成什么类型' ;
ALTER TABLE `dev_model_sql` MODIFY COLUMN content longtext NULL COMMENT '脚本';
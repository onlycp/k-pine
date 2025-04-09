ALTER TABLE `dev_application`
    ADD COLUMN `app_namespace` varchar(255) NULL COMMENT '应用命名空间';

ALTER TABLE `dev_model_sql` MODIFY COLUMN content longtext NULL COMMENT '脚本';
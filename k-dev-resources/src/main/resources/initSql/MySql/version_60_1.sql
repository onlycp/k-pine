ALTER TABLE `open_api_log` ADD COLUMN `app_id` varchar(36) COMMENT '应用id' ;

CREATE TABLE `dev_model_latest` (
    `id` varchar(36) NOT NULL COMMENT '主键ID',
    `model_name` varchar(50) DEFAULT NULL COMMENT '模型名称',
    `source_name` varchar(50) DEFAULT NULL COMMENT '数据源名称',
    `version_name` varchar(100) DEFAULT NULL COMMENT '修订版本',
    `version_who` varchar(100) DEFAULT NULL COMMENT '修订人',
    `version_time` varchar(20) DEFAULT NULL COMMENT '修订时间',
    `description` varchar(255) DEFAULT NULL COMMENT '备注',
    `diagram` longtext COMMENT '模型数据',
    `app_id` varchar(36) DEFAULT NULL COMMENT '所属应用ID',
    `inner_version` bigint(20) DEFAULT '0' COMMENT '内部版本号，用于服务端保存时校验',
    `who_created` varchar(36) DEFAULT NULL COMMENT '创建人',
    `when_created` varchar(20) DEFAULT NULL COMMENT '创建时间',
    `who_modified` varchar(36) DEFAULT NULL COMMENT '更新人',
    `when_modified` varchar(20) DEFAULT NULL COMMENT '更新时间',
    PRIMARY KEY (`id`)
) COMMENT='开发-模型数据表';

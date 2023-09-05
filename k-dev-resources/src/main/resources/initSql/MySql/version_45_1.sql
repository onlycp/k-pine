CREATE TABLE if not exists `dev_chat_history`  (
    `id` varchar(36) NOT NULL COMMENT 'id',
    `question` longtext NOT NULL COMMENT '问题',
    `answer` longtext NULL COMMENT '回答',
    `args` longtext NULL COMMENT '附加参数（JSON）',
    `when_created` varchar(50) NULL COMMENT '创建时间',
    `who_created` varchar(36) NULL COMMENT '创建人',
    PRIMARY KEY (`id`)
    ) ;

CREATE TABLE if not exists `dev_curd`  (
    `id` varchar(36) NOT NULL COMMENT '主键',
    `name` varchar(255) NULL COMMENT '名称',
    `group_id` varchar(36) NULL COMMENT '组ID',
    `source_name` varchar(255) NULL COMMENT '数据源名称',
    `table_name` varchar(255) NULL COMMENT '数据表名称',
    `primary_name` varchar(50) NULL COMMENT '主键字段名',
    `request_prefix` varchar(255) NULL COMMENT '请求前缀',
    `enable_funs` varchar(255) NULL COMMENT '启动功能',
    `create_funs` varchar(255) NULL COMMENT '创建内容',
    `column_json` text NULL COMMENT '列定义',
    `app_id` varchar(36) NULL COMMENT '应用ID',
    `who_created` varchar(36) NULL COMMENT '创建人',
    `when_created` varchar(30) NULL COMMENT '创建时间',
    `who_modified` varchar(36) NULL COMMENT '修改人',
    `when_modified` varchar(255) NULL COMMENT '修改时间',
    PRIMARY KEY (`id`)
    ) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_bin COMMENT = '开发-增删改查' ROW_FORMAT = Dynamic;

CREATE TABLE if not exists `dev_data_source`  (
    `id` varchar(36) NOT NULL COMMENT 'ID',
    `name` varchar(100) NULL COMMENT '数据源名称',
    `who_created` varchar(36) NULL COMMENT '创建人员',
    `when_created` varchar(30) NULL COMMENT '创建时间',
    `who_modified` varchar(36) NULL COMMENT '修改人员',
    `when_modified` varchar(30) NULL COMMENT '修改时间',
    `app_id` varchar(36) NULL COMMENT '关联应用',
    `kdb_id` varchar(40) NULL COMMENT '在kingDB中对应的ID',
    `team_id` varchar(36) NULL COMMENT '所属团队ID',
    `deleted` int(1) NULL COMMENT '是否已删除',
    PRIMARY KEY (`id`)
    )  COMMENT = '数据源业务映射表';

CREATE TABLE if not exists `dev_file_version`  (
    `id` varchar(36) NOT NULL COMMENT 'ID',
    `file_name` varchar(255) NULL COMMENT '文件名',
    `path` varchar(1000) NULL COMMENT '导致安装包的位置',
    `os_type` varchar(20) NULL COMMENT '操作系统类型',
    `version` varchar(100) NULL COMMENT '版本号（vX.X.X结构）',
    `path_by_package` varchar(1000) NULL COMMENT '所在package中的真实位置',
    `file_size` int(11) NULL COMMENT '文件大小',
    `description` varchar(255) NULL COMMENT '描述',
    `parent_path` varchar(1000) NULL COMMENT '父目录',
    `when_modified` varchar(30) NULL COMMENT '更新时间',
    `who_modified` varchar(36) NULL COMMENT '更新人',
    PRIMARY KEY (`id`)
    ) COMMENT = '开发表-文件版本表' ;

ALTER TABLE `rep_dataset` ADD COLUMN `dataset_search_id` varchar(32) NULL COMMENT '是否为搜索数据' AFTER `column_def`;
ALTER TABLE `rep_dataset` ADD COLUMN `shape` int(11) NULL COMMENT '是否为自定义SQL' AFTER `dataset_search_id`;
ALTER TABLE `rep_dataset` ADD COLUMN `template` int(11) NULL COMMENT '是否为Excel模板（0：否，1：是）' AFTER `shape`;
ALTER TABLE `rep_dataset` ADD COLUMN `rep_cron` varchar(50) NULL COMMENT '模板报表定时任务Cron表达式' AFTER `template`;

ALTER TABLE `sys_unit` ADD COLUMN `short_name` varchar(36) NULL COMMENT '简称' AFTER `app_id`;
ALTER TABLE `sys_unit` ADD COLUMN `unit_code` varchar(100) NULL COMMENT '机构编码' AFTER `short_name`;

ALTER TABLE `sys_user` ADD COLUMN `jira_name` varchar(100) NULL COMMENT 'jira账号名（项目管理系统）' AFTER `app_id`;

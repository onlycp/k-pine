ALTER TABLE `sys_config` ADD COLUMN `is_public` int NULL COMMENT '是否是公开的配置，公开的配置未登录也可以查看';
CREATE TABLE if not exists `dev_model_sql`
(
    `id`            varchar(36) NOT NULL COMMENT '主键',
    `app_id`        varchar(36)  DEFAULT NULL COMMENT '应用id',
    `title`         varchar(255) DEFAULT NULL COMMENT '标题',
    `source_name`   varchar(50)  DEFAULT NULL COMMENT '数据源',
    `content`       text COMMENT '脚本',
    `status`        int(8)       DEFAULT '0' COMMENT '执行状态 0: 未执行 1：已执行 2：执行异常',
    `sql_version`   int(8)       DEFAULT NULL COMMENT '版本号',
    `messages`      text COMMENT '执行结果',
    `ignore_except` int(255)     DEFAULT '1' COMMENT '是否忽略错误',
    `exec_err_line` int(11)      DEFAULT '0' COMMENT '错误行号',
    `exec_time`     varchar(20)  DEFAULT NULL COMMENT '执行时间',
    `exec_user_id`  varchar(36)  DEFAULT NULL COMMENT '执行人',
    `when_created`  varchar(20)  DEFAULT NULL COMMENT '创建时间',
    `who_created`   varchar(36)  DEFAULT NULL COMMENT '创建人',
    `when_modified` varchar(20)  DEFAULT NULL COMMENT '修改时间',
    `who_modified`  varchar(36)  DEFAULT NULL COMMENT '修改人',
    PRIMARY KEY (`id`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4;

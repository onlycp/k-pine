-- [青松4.1] 兼容 oracle 表名最多30个字符问题 --

CREATE TABLE IF NOT EXISTS dev_app_version_history  (
    id varchar(36) NOT NULL,
    app_id varchar(36) NULL DEFAULT NULL COMMENT '应用ID',
    export_data text NULL COMMENT '导出数据参数',
    file_name varchar(255) NULL DEFAULT NULL COMMENT '文件名',
    note varchar(255) NULL DEFAULT NULL COMMENT '备注',
    version varchar(50) NULL DEFAULT NULL COMMENT '版本',
    when_created varchar(30) NULL DEFAULT NULL COMMENT '创建时间',
    who_created varchar(36) NULL DEFAULT NULL COMMENT '创建人',
    PRIMARY KEY (id)
);
CREATE TABLE IF NOT EXISTS dev_model_sql
(
    id            VARCHAR(36) NOT NULL COMMENT '主键',
    app_id        VARCHAR(36)  DEFAULT NULL COMMENT '应用id',
    title         VARCHAR(255) DEFAULT NULL COMMENT '标题',
    source_name   VARCHAR(50)  DEFAULT NULL COMMENT '数据源',
    content       TEXT         DEFAULT NULL COMMENT '脚本',
    status        INT          DEFAULT 0 COMMENT '执行状态 0: 未执行 1：已执行 2：执行异常',
    sql_version   INT          DEFAULT NULL COMMENT '版本号',
    messages      TEXT         DEFAULT NULL COMMENT '执行结果',
    ignore_except INT          DEFAULT 1 COMMENT '是否忽略错误',
    exec_err_line INT          DEFAULT 0 COMMENT '错误行号',
    exec_time     VARCHAR(20)  DEFAULT NULL COMMENT '执行时间',
    exec_user_id  VARCHAR(36)  DEFAULT NULL COMMENT '执行人',
    when_created  VARCHAR(20)  DEFAULT NULL COMMENT '创建时间',
    who_created   VARCHAR(36)  DEFAULT NULL COMMENT '创建人',
    when_modified VARCHAR(20)  DEFAULT NULL COMMENT '修改时间',
    who_modified  VARCHAR(36)  DEFAULT NULL COMMENT '修改人',
    PRIMARY KEY (id)
);

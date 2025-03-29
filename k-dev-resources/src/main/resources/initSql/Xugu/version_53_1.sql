CREATE TABLE IF NOT EXISTS sys_password_log
(
    id           VARCHAR(36) NOT NULL COMMENT '主键',
    user_id      VARCHAR(36) DEFAULT NULL COMMENT '用户id',
    when_created VARCHAR(20) DEFAULT NULL COMMENT '创建时间',
    PRIMARY KEY (id)
);



CREATE TABLE IF NOT EXISTS sys_secret_rule
(
    id            VARCHAR(36) NOT NULL COMMENT '主键',
    name          VARCHAR(255) DEFAULT NULL COMMENT '规则名称',
    logic_id      VARCHAR(36)  DEFAULT NULL COMMENT '逻辑编排ID',
    secret_type   INT          DEFAULT 0 COMMENT '安全类型',
    status        INT          DEFAULT NULL COMMENT '是否启用',
    order_num     INT          DEFAULT NULL COMMENT '排序',
    notes         TEXT         DEFAULT NULL COMMENT '说明',
    when_created  VARCHAR(20)  DEFAULT NULL COMMENT '创建时间',
    who_created   VARCHAR(36)  DEFAULT NULL COMMENT '创建人',
    when_modified VARCHAR(20)  DEFAULT NULL COMMENT '更新时间',
    who_modified  VARCHAR(36)  DEFAULT NULL COMMENT '更新人',
    PRIMARY KEY (id)
);

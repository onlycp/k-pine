CREATE TABLE IF NOT EXISTS sys_auth_source
(
    id            VARCHAR(36) NOT NULL COMMENT '主键',
    code          VARCHAR(36)  DEFAULT NULL COMMENT '编码',
    icon          VARCHAR(255) DEFAULT NULL COMMENT '图标',
    config        TEXT         DEFAULT NULL COMMENT '配置信息',
    logic_flow_id VARCHAR(36)  DEFAULT NULL COMMENT '逻辑流程ID',
    name          VARCHAR(255) DEFAULT NULL COMMENT '名称',
    note          VARCHAR(255) DEFAULT NULL COMMENT '备注',
    order_num     INT          DEFAULT NULL COMMENT '排序',
    status        INT          DEFAULT NULL COMMENT '状态',
    type          INT          DEFAULT NULL COMMENT '类型',
    when_created  VARCHAR(20)  DEFAULT NULL COMMENT '创建时间',
    when_modified VARCHAR(20)  DEFAULT NULL COMMENT '修改时间',
    whoCreated    VARCHAR(36)  DEFAULT NULL COMMENT '创建人',
    who_modified  VARCHAR(36)  DEFAULT NULL COMMENT '修改人',
    PRIMARY KEY (id)
);

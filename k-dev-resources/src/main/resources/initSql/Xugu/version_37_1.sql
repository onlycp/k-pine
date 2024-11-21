CREATE TABLE sys_logic_flow_mock
(
    id            VARCHAR(36) NOT NULL COMMENT '主键',
    name          VARCHAR(90) COMMENT '名称',
    flow_id       VARCHAR(36) COMMENT '逻辑编排id',
    depend_id     VARCHAR(36) COMMENT '依赖mockId',
    request_argv  TEXT COMMENT '请求参数',
    assert_expr   VARCHAR(900) COMMENT '表达式',
    enable_mock   INT COMMENT '是否启用',
    who_created   VARCHAR(36) COMMENT '创建人',
    when_created  VARCHAR(30) COMMENT '创建时间',
    who_modified  VARCHAR(36) COMMENT '修改人员',
    when_modified VARCHAR(30) COMMENT '修改时间',
    PRIMARY KEY (id)
);

CREATE INDEX idx_sys_operate_log_app_id ON sys_operate_log (app_id);
CREATE INDEX idx_sys_operate_log_module ON sys_operate_log (module);
CREATE INDEX idx_sys_operate_log_operate_time ON sys_operate_log (operate_time);

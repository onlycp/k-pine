DROP TABLE IF EXISTS sys_logic_flow_mock;
CREATE TABLE sys_logic_flow_mock(
                                    id VARCHAR(36) NOT NULL   COMMENT '主键' ,
                                    name VARCHAR(90)    COMMENT '名称' ,
                                    flow_id VARCHAR(36)    COMMENT '逻辑编排id' ,
                                    depend_id VARCHAR(36)    COMMENT '依赖mockId' ,
                                    request_argv text    COMMENT '请求参数' ,
                                    assert_expr VARCHAR(900)    COMMENT '表达式' ,
                                    enable_mock INT    COMMENT '是否启用' ,
                                    who_created VARCHAR(36)    COMMENT '创建人' ,
                                    when_created VARCHAR(20)    COMMENT '创建时间' ,
                                    who_modified VARCHAR(36)    COMMENT '修改人员' ,
                                    when_modified VARCHAR(20)    COMMENT '修改时间' ,
                                    PRIMARY KEY (id)
)  COMMENT = '逻辑编排mock';
CREATE INDEX idx_sys_operate_log_app_id USING BTREE ON sys_operate_log (app_id);
CREATE INDEX idx_sys_operate_log_module USING BTREE ON sys_operate_log (`module`);
CREATE INDEX idx_sys_operate_log_operate_time USING BTREE ON sys_operate_log (operate_time);
CREATE INDEX idx_dev_page_deleted USING BTREE ON dev_page (deleted,`path`);


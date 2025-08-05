CREATE TABLE sys_task_history(
                                 id VARCHAR2(36),
                                 task_id VARCHAR2(36),
                                 task_name VARCHAR2(100),
                                 execute_status INT,
                                 execute_take INT,
                                 execute_begin_time VARCHAR2(20),
                                 execute_end_time VARCHAR2(20),
                                 execute_msg VARCHAR2(255),
                                 who_created VARCHAR2(36),
                                 when_created VARCHAR2(20),
                                 who_modified VARCHAR2(36),
                                 when_modified VARCHAR2(20)
);

COMMENT ON TABLE sys_task_history IS '任务执行历史';
COMMENT ON COLUMN sys_task_history.id IS '主键 id';
COMMENT ON COLUMN sys_task_history.task_id IS '任务 id';
COMMENT ON COLUMN sys_task_history.task_name IS '任务名称';
COMMENT ON COLUMN sys_task_history.execute_status IS '执行状态：1 成功，0 失败';
COMMENT ON COLUMN sys_task_history.execute_take IS '执行消耗';
COMMENT ON COLUMN sys_task_history.execute_begin_time IS '执行开始时间';
COMMENT ON COLUMN sys_task_history.execute_end_time IS '执行结束时间';
COMMENT ON COLUMN sys_task_history.execute_msg IS '执行错误信息';
COMMENT ON COLUMN sys_task_history.who_created IS '创建人';
COMMENT ON COLUMN sys_task_history.when_created IS '创建时间';
COMMENT ON COLUMN sys_task_history.who_modified IS '更新人';
COMMENT ON COLUMN sys_task_history.when_modified IS '更新时间';

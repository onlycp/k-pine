CREATE TABLE sys_task_history(
     `id` VARCHAR(36) COMMENT '主键 id' ,
     `task_id` VARCHAR(36)   COMMENT '任务 id' ,
     `task_name` VARCHAR(100)   COMMENT '任务名称' ,
     `execute_status` INT   COMMENT '执行状态：1 成功，0 失败' ,
     `execute_take` INT   COMMENT '执行消耗' ,
     `execute_begin_time` VARCHAR(20)   COMMENT '执行开始时间' ,
     `execute_end_time` VARCHAR(20)   COMMENT '执行结束时间' ,
     `execute_msg` text   COMMENT '执行错误信息' ,
     `request_body` text   COMMENT '请求体内容' ,
     `response_body` text   COMMENT '响应体内容' ,
     `who_created` VARCHAR(36)   COMMENT '创建人' ,
     `when_created` VARCHAR(20)   COMMENT '创建时间' ,
     `who_modified` VARCHAR(36)   COMMENT '更新人' ,
     `when_modified` VARCHAR(20)   COMMENT '更新时间',
     PRIMARY KEY (id)
)  COMMENT = '任务执行历史';

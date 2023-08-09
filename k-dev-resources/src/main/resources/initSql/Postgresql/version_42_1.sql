drop table if exists dev_view_model;
drop table if exists dev_view_model_field;
drop table if exists dev_view_model_flow;
drop table if exists sys_view_model;
drop table if exists sys_view_model_field;
drop table if exists sys_view_model_flow;


CREATE TABLE if not EXISTS wf_cc_inst(
                                         inst_id VARCHAR(32),
                                         actor_id VARCHAR(50),
                                         creator VARCHAR(50),
                                         create_time VARCHAR(50),
                                         finish_time VARCHAR(50),
                                         status int2
);

COMMENT ON TABLE wf_cc_inst IS '抄送实例表';
COMMENT ON COLUMN wf_cc_inst.inst_id IS '流程实例ID';
COMMENT ON COLUMN wf_cc_inst.actor_id IS '参与者ID';
COMMENT ON COLUMN wf_cc_inst.creator IS '发起人';
COMMENT ON COLUMN wf_cc_inst.create_time IS '抄送时间';
COMMENT ON COLUMN wf_cc_inst.finish_time IS '完成时间';
COMMENT ON COLUMN wf_cc_inst.status IS '状态';


CREATE TABLE if not EXISTS wf_hist_inst(
                                           id VARCHAR(32) NOT NULL,
                                           process_id VARCHAR(32) NOT NULL,
                                           inst_state int2 NOT NULL,
                                           creator VARCHAR(500),
                                           create_time VARCHAR(50) NOT NULL,
                                           end_time VARCHAR(50),
                                           expire_time VARCHAR(50),
                                           priority int2,
                                           parent_id VARCHAR(32),
                                           inst_no VARCHAR(50),
                                           variable TEXT,
                                           PRIMARY KEY (id)
);

COMMENT ON TABLE wf_hist_inst IS '历史流程实例表';
COMMENT ON COLUMN wf_hist_inst.id IS '主键ID';
COMMENT ON COLUMN wf_hist_inst.process_id IS '流程定义ID';
COMMENT ON COLUMN wf_hist_inst.inst_state IS '状态';
COMMENT ON COLUMN wf_hist_inst.creator IS '发起人';
COMMENT ON COLUMN wf_hist_inst.create_time IS '发起时间';
COMMENT ON COLUMN wf_hist_inst.end_time IS '完成时间';
COMMENT ON COLUMN wf_hist_inst.expire_time IS '期望完成时间';
COMMENT ON COLUMN wf_hist_inst.priority IS '优先级';
COMMENT ON COLUMN wf_hist_inst.parent_id IS '父流程ID';
COMMENT ON COLUMN wf_hist_inst.inst_no IS '流程实例编号';
COMMENT ON COLUMN wf_hist_inst.variable IS '附属变量json存储';

CREATE TABLE if not EXISTS wf_hist_task(
                                           id VARCHAR(32) NOT NULL,
                                           inst_id VARCHAR(32) NOT NULL,
                                           task_name VARCHAR(100) NOT NULL,
                                           display_name VARCHAR(200) NOT NULL,
                                           task_type int2 NOT NULL,
                                           perform_type int2,
                                           task_state int2 NOT NULL,
                                           operator VARCHAR(500),
                                           create_time VARCHAR(50) NOT NULL,
                                           finish_time VARCHAR(50),
                                           expire_time VARCHAR(50),
                                           action_url VARCHAR(200),
                                           parent_task_id VARCHAR(32),
                                           variable TEXT,
                                           PRIMARY KEY (id)
);

COMMENT ON TABLE wf_hist_task IS '历史任务表';
COMMENT ON COLUMN wf_hist_task.id IS '主键ID';
COMMENT ON COLUMN wf_hist_task.inst_id IS '流程实例ID';
COMMENT ON COLUMN wf_hist_task.task_name IS '任务名称';
COMMENT ON COLUMN wf_hist_task.display_name IS '任务显示名称';
COMMENT ON COLUMN wf_hist_task.task_type IS '任务类型';
COMMENT ON COLUMN wf_hist_task.perform_type IS '参与类型';
COMMENT ON COLUMN wf_hist_task.task_state IS '任务状态';
COMMENT ON COLUMN wf_hist_task.operator IS '任务处理人';
COMMENT ON COLUMN wf_hist_task.create_time IS '任务创建时间';
COMMENT ON COLUMN wf_hist_task.finish_time IS '任务完成时间';
COMMENT ON COLUMN wf_hist_task.expire_time IS '任务期望完成时间';
COMMENT ON COLUMN wf_hist_task.action_url IS '任务处理url';
COMMENT ON COLUMN wf_hist_task.parent_task_id IS '父任务ID';
COMMENT ON COLUMN wf_hist_task.variable IS '附属变量json存储';

CREATE TABLE if not EXISTS wf_hist_task_actor(
                                                 task_id VARCHAR(32) NOT NULL,
                                                 actor_id VARCHAR(500) NOT NULL
);

COMMENT ON TABLE wf_hist_task_actor IS '历史任务参与者表';
COMMENT ON COLUMN wf_hist_task_actor.task_id IS '任务ID';
COMMENT ON COLUMN wf_hist_task_actor.actor_id IS '参与者ID';

CREATE TABLE if not EXISTS wf_inst(
                                      id VARCHAR(32) NOT NULL,
                                      parent_id VARCHAR(32),
                                      process_id VARCHAR(32) NOT NULL,
                                      creator VARCHAR(500),
                                      create_time VARCHAR(50) NOT NULL,
                                      expire_time VARCHAR(50),
                                      last_update_time VARCHAR(50),
                                      last_updator VARCHAR(500),
                                      priority int2,
                                      parent_node_name VARCHAR(100),
                                      inst_no VARCHAR(50),
                                      variable TEXT,
                                      version int4,
                                      PRIMARY KEY (id)
);

COMMENT ON TABLE wf_inst IS '流程实例表';
COMMENT ON COLUMN wf_inst.id IS '主键ID';
COMMENT ON COLUMN wf_inst.parent_id IS '父流程ID';
COMMENT ON COLUMN wf_inst.process_id IS '流程定义ID';
COMMENT ON COLUMN wf_inst.creator IS '发起人';
COMMENT ON COLUMN wf_inst.create_time IS '发起时间';
COMMENT ON COLUMN wf_inst.expire_time IS '期望完成时间';
COMMENT ON COLUMN wf_inst.last_update_time IS '上次更新时间';
COMMENT ON COLUMN wf_inst.last_updator IS '上次更新人';
COMMENT ON COLUMN wf_inst.priority IS '优先级';
COMMENT ON COLUMN wf_inst.parent_node_name IS '父流程依赖的节点名称';
COMMENT ON COLUMN wf_inst.inst_no IS '流程实例编号';
COMMENT ON COLUMN wf_inst.variable IS '附属变量json存储';
COMMENT ON COLUMN wf_inst.version IS '版本';

CREATE TABLE if not EXISTS wf_process(
                                         id VARCHAR(32) NOT NULL,
                                         name VARCHAR(100),
                                         display_name VARCHAR(200),
                                         type VARCHAR(100),
                                         instance_url VARCHAR(200),
                                         state int2,
                                         content TEXT,
                                         version int4,
                                         create_time VARCHAR(50),
                                         creator VARCHAR(50),
                                         PRIMARY KEY (id)
);

COMMENT ON TABLE wf_process IS '流程定义表';
COMMENT ON COLUMN wf_process.id IS '主键ID';
COMMENT ON COLUMN wf_process.name IS '流程名称';
COMMENT ON COLUMN wf_process.display_name IS '流程显示名称';
COMMENT ON COLUMN wf_process.type IS '流程类型';
COMMENT ON COLUMN wf_process.instance_url IS '实例url';
COMMENT ON COLUMN wf_process.state IS '流程是否可用';
COMMENT ON COLUMN wf_process.content IS '流程模型定义';
COMMENT ON COLUMN wf_process.version IS '版本';
COMMENT ON COLUMN wf_process.create_time IS '创建时间';
COMMENT ON COLUMN wf_process.creator IS '创建人';



CREATE TABLE if not EXISTS wf_surrogate(
                                           id VARCHAR(32) NOT NULL,
                                           process_name VARCHAR(100),
                                           operator VARCHAR(50),
                                           surrogate VARCHAR(50),
                                           odate VARCHAR(64),
                                           sdate VARCHAR(64),
                                           edate VARCHAR(64),
                                           state int2,
                                           PRIMARY KEY (id)
);

COMMENT ON TABLE wf_surrogate IS '委托代理表';
COMMENT ON COLUMN wf_surrogate.id IS '主键ID';
COMMENT ON COLUMN wf_surrogate.process_name IS '流程名称';
COMMENT ON COLUMN wf_surrogate.operator IS '授权人';
COMMENT ON COLUMN wf_surrogate.surrogate IS '代理人';
COMMENT ON COLUMN wf_surrogate.odate IS '操作时间';
COMMENT ON COLUMN wf_surrogate.sdate IS '开始时间';
COMMENT ON COLUMN wf_surrogate.edate IS '结束时间';
COMMENT ON COLUMN wf_surrogate.state IS '状态';

CREATE TABLE if not EXISTS wf_task(
                                      id VARCHAR(32) NOT NULL,
                                      inst_id VARCHAR(32) NOT NULL,
                                      task_name VARCHAR(100) NOT NULL,
                                      display_name VARCHAR(200) NOT NULL,
                                      task_type int2 NOT NULL,
                                      perform_type int2,
                                      operator VARCHAR(500),
                                      create_time VARCHAR(50),
                                      finish_time VARCHAR(50),
                                      expire_time VARCHAR(50),
                                      action_url VARCHAR(200),
                                      parent_task_id VARCHAR(32),
                                      variable TEXT,
                                      version int2,
                                      PRIMARY KEY (id)
);

COMMENT ON TABLE wf_task IS '任务表';
COMMENT ON COLUMN wf_task.id IS '主键ID';
COMMENT ON COLUMN wf_task.inst_id IS '流程实例ID';
COMMENT ON COLUMN wf_task.task_name IS '任务名称';
COMMENT ON COLUMN wf_task.display_name IS '任务显示名称';
COMMENT ON COLUMN wf_task.task_type IS '任务类型';
COMMENT ON COLUMN wf_task.perform_type IS '参与类型';
COMMENT ON COLUMN wf_task.operator IS '任务处理人';
COMMENT ON COLUMN wf_task.create_time IS '任务创建时间';
COMMENT ON COLUMN wf_task.finish_time IS '任务完成时间';
COMMENT ON COLUMN wf_task.expire_time IS '任务期望完成时间';
COMMENT ON COLUMN wf_task.action_url IS '任务处理的url';
COMMENT ON COLUMN wf_task.parent_task_id IS '父任务ID';
COMMENT ON COLUMN wf_task.variable IS '附属变量json存储';
COMMENT ON COLUMN wf_task.version IS '版本';

CREATE TABLE if not EXISTS wf_task_actor(
                                            task_id VARCHAR(32) NOT NULL,
                                            actor_id VARCHAR(500) NOT NULL
);

COMMENT ON TABLE wf_task_actor IS '任务参与者表';
COMMENT ON COLUMN wf_task_actor.task_id IS '任务ID';
COMMENT ON COLUMN wf_task_actor.actor_id IS '参与者ID';



CREATE TABLE "sys_offline_download"
(
    "id"            varchar(36) COLLATE "pg_catalog"."default" NOT NULL,
    "file_name"     varchar(90) COLLATE "pg_catalog"."default",
    "task_name"     varchar(255) COLLATE "pg_catalog"."default",
    "file_path"     varchar(255) COLLATE "pg_catalog"."default",
    "end_time"      varchar(255) COLLATE "pg_catalog"."default",
    "script"        varchar(1024) COLLATE "pg_catalog"."default",
    "process" int4,
    "status"        varchar(255) COLLATE "pg_catalog"."default",
    "params"        varchar(255) COLLATE "pg_catalog"."default",
    "error_message" varchar(900) COLLATE "pg_catalog"."default",
    "who_created"   varchar(36) COLLATE "pg_catalog"."default",
    "when_created"  varchar(20) COLLATE "pg_catalog"."default",
    CONSTRAINT "sys_offline_download_pkey" PRIMARY KEY ("id")
)
;

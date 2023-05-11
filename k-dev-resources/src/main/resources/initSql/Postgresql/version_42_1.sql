drop table if exists dev_view_model;
drop table if exists dev_view_model_field;
drop table if exists dev_view_model_flow;
drop table if exists sys_view_model;
drop table if exists sys_view_model_field;
drop table if exists sys_view_model_flow;


CREATE TABLE if not EXISTS "wf_cc_inst"
(
    "inst_Id"     varchar(32) COLLATE "pg_catalog"."default",
    "actor_Id"    varchar(50) COLLATE "pg_catalog"."default",
    "creator"     varchar(50) COLLATE "pg_catalog"."default",
    "create_Time" varchar(50) COLLATE "pg_catalog"."default",
    "finish_Time" varchar(50) COLLATE "pg_catalog"."default",
    "status" int2
)
;
COMMENT ON COLUMN "wf_cc_inst"."inst_Id" IS '流程实例ID';
COMMENT ON COLUMN "wf_cc_inst"."actor_Id" IS '参与者ID';
COMMENT ON COLUMN "wf_cc_inst"."creator" IS '发起人';
COMMENT ON COLUMN "wf_cc_inst"."create_Time" IS '抄送时间';
COMMENT ON COLUMN "wf_cc_inst"."finish_Time" IS '完成时间';
COMMENT ON COLUMN "wf_cc_inst"."status" IS '状态';
COMMENT ON TABLE "wf_cc_inst" IS '抄送实例表';


CREATE TABLE if not EXISTS "wf_hist_inst"
(
    "id"          varchar(32) COLLATE "pg_catalog"."default" NOT NULL,
    "process_Id"  varchar(32) COLLATE "pg_catalog"."default" NOT NULL,
    "inst_State" int2 NOT NULL,
    "creator"     varchar(500) COLLATE "pg_catalog"."default",
    "create_Time" varchar(50) COLLATE "pg_catalog"."default" NOT NULL,
    "end_Time"    varchar(50) COLLATE "pg_catalog"."default",
    "expire_Time" varchar(50) COLLATE "pg_catalog"."default",
    "priority" int2,
    "parent_Id"   varchar(32) COLLATE "pg_catalog"."default",
    "inst_No"     varchar(50) COLLATE "pg_catalog"."default",
    "variable"    text COLLATE "pg_catalog"."default"
)
;
COMMENT ON COLUMN "wf_hist_inst"."id" IS '主键ID';
COMMENT ON COLUMN "wf_hist_inst"."process_Id" IS '流程定义ID';
COMMENT ON COLUMN "wf_hist_inst"."inst_State" IS '状态';
COMMENT ON COLUMN "wf_hist_inst"."creator" IS '发起人';
COMMENT ON COLUMN "wf_hist_inst"."create_Time" IS '发起时间';
COMMENT ON COLUMN "wf_hist_inst"."end_Time" IS '完成时间';
COMMENT ON COLUMN "wf_hist_inst"."expire_Time" IS '期望完成时间';
COMMENT ON COLUMN "wf_hist_inst"."priority" IS '优先级';
COMMENT ON COLUMN "wf_hist_inst"."parent_Id" IS '父流程ID';
COMMENT ON COLUMN "wf_hist_inst"."inst_No" IS '流程实例编号';
COMMENT ON COLUMN "wf_hist_inst"."variable" IS '附属变量json存储';
COMMENT ON TABLE "wf_hist_inst" IS '历史流程实例表';


CREATE TABLE if not EXISTS "wf_hist_task"
(
    "id"             varchar(32) COLLATE "pg_catalog"."default"  NOT NULL,
    "inst_Id"        varchar(32) COLLATE "pg_catalog"."default"  NOT NULL,
    "task_Name"      varchar(100) COLLATE "pg_catalog"."default" NOT NULL,
    "display_Name"   varchar(200) COLLATE "pg_catalog"."default" NOT NULL,
    "task_Type" int2 NOT NULL,
    "perform_Type" int2,
    "task_State" int2 NOT NULL,
    "operator"       varchar(500) COLLATE "pg_catalog"."default",
    "create_Time"    varchar(50) COLLATE "pg_catalog"."default"  NOT NULL,
    "finish_Time"    varchar(50) COLLATE "pg_catalog"."default",
    "expire_Time"    varchar(50) COLLATE "pg_catalog"."default",
    "action_Url"     varchar(200) COLLATE "pg_catalog"."default",
    "parent_Task_Id" varchar(32) COLLATE "pg_catalog"."default",
    "variable"       text COLLATE "pg_catalog"."default"
)
;
COMMENT ON COLUMN "wf_hist_task"."id" IS '主键ID';
COMMENT ON COLUMN "wf_hist_task"."inst_Id" IS '流程实例ID';
COMMENT ON COLUMN "wf_hist_task"."task_Name" IS '任务名称';
COMMENT ON COLUMN "wf_hist_task"."display_Name" IS '任务显示名称';
COMMENT ON COLUMN "wf_hist_task"."task_Type" IS '任务类型';
COMMENT ON COLUMN "wf_hist_task"."perform_Type" IS '参与类型';
COMMENT ON COLUMN "wf_hist_task"."task_State" IS '任务状态';
COMMENT ON COLUMN "wf_hist_task"."operator" IS '任务处理人';
COMMENT ON COLUMN "wf_hist_task"."create_Time" IS '任务创建时间';
COMMENT ON COLUMN "wf_hist_task"."finish_Time" IS '任务完成时间';
COMMENT ON COLUMN "wf_hist_task"."expire_Time" IS '任务期望完成时间';
COMMENT ON COLUMN "wf_hist_task"."action_Url" IS '任务处理url';
COMMENT ON COLUMN "wf_hist_task"."parent_Task_Id" IS '父任务ID';
COMMENT ON COLUMN "wf_hist_task"."variable" IS '附属变量json存储';
COMMENT ON TABLE "wf_hist_task" IS '历史任务表';


CREATE TABLE if not EXISTS "wf_hist_task_actor"
(
    "task_Id"  varchar(32) COLLATE "pg_catalog"."default"  NOT NULL,
    "actor_Id" varchar(500) COLLATE "pg_catalog"."default" NOT NULL
)
;
COMMENT ON COLUMN "wf_hist_task_actor"."task_Id" IS '任务ID';
COMMENT ON COLUMN "wf_hist_task_actor"."actor_Id" IS '参与者ID';
COMMENT ON TABLE "wf_hist_task_actor" IS '历史任务参与者表';


CREATE TABLE if not EXISTS "wf_inst"
(
    "id"               varchar(32) COLLATE "pg_catalog"."default" NOT NULL,
    "parent_Id"        varchar(32) COLLATE "pg_catalog"."default",
    "process_Id"       varchar(32) COLLATE "pg_catalog"."default" NOT NULL,
    "creator"          varchar(500) COLLATE "pg_catalog"."default",
    "create_Time"      varchar(50) COLLATE "pg_catalog"."default" NOT NULL,
    "expire_Time"      varchar(50) COLLATE "pg_catalog"."default",
    "last_Update_Time" varchar(50) COLLATE "pg_catalog"."default",
    "last_Updator"     varchar(500) COLLATE "pg_catalog"."default",
    "priority" int2,
    "parent_Node_Name" varchar(100) COLLATE "pg_catalog"."default",
    "inst_No"          varchar(50) COLLATE "pg_catalog"."default",
    "variable"         text COLLATE "pg_catalog"."default",
    "version" int4
)
;
COMMENT ON COLUMN "wf_inst"."id" IS '主键ID';
COMMENT ON COLUMN "wf_inst"."parent_Id" IS '父流程ID';
COMMENT ON COLUMN "wf_inst"."process_Id" IS '流程定义ID';
COMMENT ON COLUMN "wf_inst"."creator" IS '发起人';
COMMENT ON COLUMN "wf_inst"."create_Time" IS '发起时间';
COMMENT ON COLUMN "wf_inst"."expire_Time" IS '期望完成时间';
COMMENT ON COLUMN "wf_inst"."last_Update_Time" IS '上次更新时间';
COMMENT ON COLUMN "wf_inst"."last_Updator" IS '上次更新人';
COMMENT ON COLUMN "wf_inst"."priority" IS '优先级';
COMMENT ON COLUMN "wf_inst"."parent_Node_Name" IS '父流程依赖的节点名称';
COMMENT ON COLUMN "wf_inst"."inst_No" IS '流程实例编号';
COMMENT ON COLUMN "wf_inst"."variable" IS '附属变量json存储';
COMMENT ON COLUMN "wf_inst"."version" IS '版本';
COMMENT ON TABLE "wf_inst" IS '流程实例表';


CREATE TABLE if not EXISTS "wf_process"
(
    "id"           varchar(32) COLLATE "pg_catalog"."default" NOT NULL,
    "name"         varchar(100) COLLATE "pg_catalog"."default",
    "display_Name" varchar(200) COLLATE "pg_catalog"."default",
    "type"         varchar(100) COLLATE "pg_catalog"."default",
    "instance_Url" varchar(200) COLLATE "pg_catalog"."default",
    "state" int2,
    "content" bytea,
    "version" int4,
    "create_Time"  varchar(50) COLLATE "pg_catalog"."default",
    "creator"      varchar(50) COLLATE "pg_catalog"."default"
)
;
COMMENT ON COLUMN "wf_process"."id" IS '主键ID';
COMMENT ON COLUMN "wf_process"."name" IS '流程名称';
COMMENT ON COLUMN "wf_process"."display_Name" IS '流程显示名称';
COMMENT ON COLUMN "wf_process"."type" IS '流程类型';
COMMENT ON COLUMN "wf_process"."instance_Url" IS '实例url';
COMMENT ON COLUMN "wf_process"."state" IS '流程是否可用';
COMMENT ON COLUMN "wf_process"."content" IS '流程模型定义';
COMMENT ON COLUMN "wf_process"."version" IS '版本';
COMMENT ON COLUMN "wf_process"."create_Time" IS '创建时间';
COMMENT ON COLUMN "wf_process"."creator" IS '创建人';
COMMENT ON TABLE "wf_process" IS '流程定义表';


CREATE TABLE if not EXISTS "wf_surrogate"
(
    "id"           varchar(32) COLLATE "pg_catalog"."default" NOT NULL,
    "process_Name" varchar(100) COLLATE "pg_catalog"."default",
    "operator"     varchar(50) COLLATE "pg_catalog"."default",
    "surrogate"    varchar(50) COLLATE "pg_catalog"."default",
    "odate"        varchar(64) COLLATE "pg_catalog"."default",
    "sdate"        varchar(64) COLLATE "pg_catalog"."default",
    "edate"        varchar(64) COLLATE "pg_catalog"."default",
    "state" int2
)
;
COMMENT ON COLUMN "wf_surrogate"."id" IS '主键ID';
COMMENT ON COLUMN "wf_surrogate"."process_Name" IS '流程名称';
COMMENT ON COLUMN "wf_surrogate"."operator" IS '授权人';
COMMENT ON COLUMN "wf_surrogate"."surrogate" IS '代理人';
COMMENT ON COLUMN "wf_surrogate"."odate" IS '操作时间';
COMMENT ON COLUMN "wf_surrogate"."sdate" IS '开始时间';
COMMENT ON COLUMN "wf_surrogate"."edate" IS '结束时间';
COMMENT ON COLUMN "wf_surrogate"."state" IS '状态';
COMMENT ON TABLE "wf_surrogate" IS '委托代理表';


CREATE TABLE if not EXISTS "wf_task"
(
    "id"             varchar(32) COLLATE "pg_catalog"."default"  NOT NULL,
    "inst_Id"        varchar(32) COLLATE "pg_catalog"."default"  NOT NULL,
    "task_Name"      varchar(100) COLLATE "pg_catalog"."default" NOT NULL,
    "display_Name"   varchar(200) COLLATE "pg_catalog"."default" NOT NULL,
    "task_Type" int2 NOT NULL,
    "perform_Type" int2,
    "operator"       varchar(500) COLLATE "pg_catalog"."default",
    "create_Time"    varchar(50) COLLATE "pg_catalog"."default",
    "finish_Time"    varchar(50) COLLATE "pg_catalog"."default",
    "expire_Time"    varchar(50) COLLATE "pg_catalog"."default",
    "action_Url"     varchar(200) COLLATE "pg_catalog"."default",
    "parent_Task_Id" varchar(32) COLLATE "pg_catalog"."default",
    "variable"       text COLLATE "pg_catalog"."default",
    "version" int2
)
;
COMMENT ON COLUMN "wf_task"."id" IS '主键ID';
COMMENT ON COLUMN "wf_task"."inst_Id" IS '流程实例ID';
COMMENT ON COLUMN "wf_task"."task_Name" IS '任务名称';
COMMENT ON COLUMN "wf_task"."display_Name" IS '任务显示名称';
COMMENT ON COLUMN "wf_task"."task_Type" IS '任务类型';
COMMENT ON COLUMN "wf_task"."perform_Type" IS '参与类型';
COMMENT ON COLUMN "wf_task"."operator" IS '任务处理人';
COMMENT ON COLUMN "wf_task"."create_Time" IS '任务创建时间';
COMMENT ON COLUMN "wf_task"."finish_Time" IS '任务完成时间';
COMMENT ON COLUMN "wf_task"."expire_Time" IS '任务期望完成时间';
COMMENT ON COLUMN "wf_task"."action_Url" IS '任务处理的url';
COMMENT ON COLUMN "wf_task"."parent_Task_Id" IS '父任务ID';
COMMENT ON COLUMN "wf_task"."variable" IS '附属变量json存储';
COMMENT ON COLUMN "wf_task"."version" IS '版本';
COMMENT ON TABLE "wf_task" IS '任务表';


CREATE TABLE if not EXISTS "wf_task_actor"
(
    "task_Id"  varchar(32) COLLATE "pg_catalog"."default"  NOT NULL,
    "actor_Id" varchar(500) COLLATE "pg_catalog"."default" NOT NULL
)
;
COMMENT ON COLUMN "wf_task_actor"."task_Id" IS '任务ID';
COMMENT ON COLUMN "wf_task_actor"."actor_Id" IS '参与者ID';
COMMENT ON TABLE "wf_task_actor" IS '任务参与者表';


ALTER TABLE "wf_hist_inst"
    ADD CONSTRAINT "wf_hist_inst_pkey" PRIMARY KEY ("id");


ALTER TABLE "wf_hist_task"
    ADD CONSTRAINT "wf_hist_task_pkey" PRIMARY KEY ("id");


ALTER TABLE "wf_inst"
    ADD CONSTRAINT "wf_inst_pkey" PRIMARY KEY ("id");


ALTER TABLE "wf_process"
    ADD CONSTRAINT "wf_process_pkey" PRIMARY KEY ("id");


ALTER TABLE "wf_surrogate"
    ADD CONSTRAINT "wf_surrogate_pkey" PRIMARY KEY ("id");


ALTER TABLE "wf_task"
    ADD CONSTRAINT "wf_task_pkey" PRIMARY KEY ("id");


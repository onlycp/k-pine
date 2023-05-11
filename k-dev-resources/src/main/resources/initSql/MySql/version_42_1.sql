drop table if exists dev_view_model;
drop table if exists dev_view_model_field;
drop table if exists dev_view_model_flow;
drop table if exists sys_view_model;
drop table if exists sys_view_model_field;
drop table if exists sys_view_model_flow;

CREATE TABLE if not exists wf_cc_inst
(
    inst_Id     varchar(32)     DEFAULT NULL COMMENT '流程实例ID',
    actor_Id    varchar(50)     DEFAULT NULL COMMENT '参与者ID',
    creator     varchar(50)     DEFAULT NULL COMMENT '发起人',
    create_Time varchar(50)     DEFAULT NULL COMMENT '抄送时间',
    finish_Time varchar(50)     DEFAULT NULL COMMENT '完成时间',
    status      tinyint(1) NULL DEFAULT NULL COMMENT '状态'
);


CREATE TABLE if not exists  wf_hist_inst
(
    id          varchar(32) NOT NULL COMMENT '主键ID',
    process_Id  varchar(32) NOT NULL COMMENT '流程定义ID',
    inst_State  tinyint(1)  NOT NULL COMMENT '状态',
    creator     varchar(500)     DEFAULT NULL COMMENT '发起人',
    create_Time varchar(50) NOT NULL COMMENT '发起时间',
    end_Time    varchar(50)      DEFAULT NULL COMMENT '完成时间',
    expire_Time varchar(50)      DEFAULT NULL COMMENT '期望完成时间',
    priority    tinyint(1)  NULL DEFAULT NULL COMMENT '优先级',
    parent_Id   varchar(32)      DEFAULT NULL COMMENT '父流程ID',
    inst_No     varchar(50)      DEFAULT NULL COMMENT '流程实例编号',
    variable    text COMMENT '附属变量json存储',
    PRIMARY KEY (id) USING BTREE
);


CREATE TABLE if not exists wf_hist_task
(
    id             varchar(32)  NOT NULL COMMENT '主键ID',
    inst_Id        varchar(32)  NOT NULL COMMENT '流程实例ID',
    task_Name      varchar(100) NOT NULL COMMENT '任务名称',
    display_Name   varchar(200) NOT NULL COMMENT '任务显示名称',
    task_Type      tinyint(1)   NOT NULL COMMENT '任务类型',
    perform_Type   tinyint(1)   NULL DEFAULT NULL COMMENT '参与类型',
    task_State     tinyint(1)   NOT NULL COMMENT '任务状态',
    operator       varchar(500)      DEFAULT NULL COMMENT '任务处理人',
    create_Time    varchar(50)  NOT NULL COMMENT '任务创建时间',
    finish_Time    varchar(50)       DEFAULT NULL COMMENT '任务完成时间',
    expire_Time    varchar(50)       DEFAULT NULL COMMENT '任务期望完成时间',
    action_Url     varchar(200)      DEFAULT NULL COMMENT '任务处理url',
    parent_Task_Id varchar(32)       DEFAULT NULL COMMENT '父任务ID',
    variable       text COMMENT '附属变量json存储',
    PRIMARY KEY (id) USING BTREE
);


CREATE TABLE if not exists wf_hist_task_actor
(
    task_Id  varchar(32)  NOT NULL COMMENT '任务ID',
    actor_Id varchar(500) NOT NULL COMMENT '参与者ID'
);


CREATE TABLE if not exists wf_inst
(
    id               varchar(32) NOT NULL COMMENT '主键ID',
    parent_Id        varchar(32)      DEFAULT NULL COMMENT '父流程ID',
    process_Id       varchar(32) NOT NULL COMMENT '流程定义ID',
    creator          varchar(500)     DEFAULT NULL COMMENT '发起人',
    create_Time      varchar(50) NOT NULL COMMENT '发起时间',
    expire_Time      varchar(50)      DEFAULT NULL COMMENT '期望完成时间',
    last_Update_Time varchar(50)      DEFAULT NULL COMMENT '上次更新时间',
    last_Updator     varchar(500)     DEFAULT NULL COMMENT '上次更新人',
    priority         tinyint(1)  NULL DEFAULT NULL COMMENT '优先级',
    parent_Node_Name varchar(100)     DEFAULT NULL COMMENT '父流程依赖的节点名称',
    inst_No          varchar(50)      DEFAULT NULL COMMENT '流程实例编号',
    variable         text COMMENT '附属变量json存储',
    version          int(3)      NULL DEFAULT NULL COMMENT '版本',
    PRIMARY KEY (id) USING BTREE
);


CREATE TABLE if not exists wf_process
(
    id           varchar(32) NOT NULL COMMENT '主键ID',
    name         varchar(100)     DEFAULT NULL COMMENT '流程名称',
    display_Name varchar(200)     DEFAULT NULL COMMENT '流程显示名称',
    type         varchar(100)     DEFAULT NULL COMMENT '流程类型',
    instance_Url varchar(200)     DEFAULT NULL COMMENT '实例url',
    state        tinyint(1)  NULL DEFAULT NULL COMMENT '流程是否可用',
    content      longblob    NULL COMMENT '流程模型定义',
    version      int(2)      NULL DEFAULT NULL COMMENT '版本',
    create_Time  varchar(50)      DEFAULT NULL COMMENT '创建时间',
    creator      varchar(50)      DEFAULT NULL COMMENT '创建人',
    PRIMARY KEY (id) USING BTREE
);


CREATE TABLE if not exists wf_surrogate
(
    id           varchar(32) NOT NULL COMMENT '主键ID',
    process_Name varchar(100)     DEFAULT NULL COMMENT '流程名称',
    operator     varchar(50)      DEFAULT NULL COMMENT '授权人',
    surrogate    varchar(50)      DEFAULT NULL COMMENT '代理人',
    odate        varchar(64)      DEFAULT NULL COMMENT '操作时间',
    sdate        varchar(64)      DEFAULT NULL COMMENT '开始时间',
    edate        varchar(64)      DEFAULT NULL COMMENT '结束时间',
    state        tinyint(1)  NULL DEFAULT NULL COMMENT '状态',
    PRIMARY KEY (id) USING BTREE
);


CREATE TABLE if not exists wf_task
(
    id             varchar(32)  NOT NULL COMMENT '主键ID',
    inst_Id        varchar(32)  NOT NULL COMMENT '流程实例ID',
    task_Name      varchar(100) NOT NULL COMMENT '任务名称',
    display_Name   varchar(200) NOT NULL COMMENT '任务显示名称',
    task_Type      tinyint(1)   NOT NULL COMMENT '任务类型',
    perform_Type   tinyint(1)   NULL DEFAULT NULL COMMENT '参与类型',
    operator       varchar(500)      DEFAULT NULL COMMENT '任务处理人',
    create_Time    varchar(50)       DEFAULT NULL COMMENT '任务创建时间',
    finish_Time    varchar(50)       DEFAULT NULL COMMENT '任务完成时间',
    expire_Time    varchar(50)       DEFAULT NULL COMMENT '任务期望完成时间',
    action_Url     varchar(200)      DEFAULT NULL COMMENT '任务处理的url',
    parent_Task_Id varchar(32)       DEFAULT NULL COMMENT '父任务ID',
    variable       text COMMENT '附属变量json存储',
    version        tinyint(1)   NULL DEFAULT NULL COMMENT '版本',
    PRIMARY KEY (id) USING BTREE
);


CREATE TABLE if not exists wf_task_actor
(
    task_Id  varchar(32)  NOT NULL COMMENT '任务ID',
    actor_Id varchar(500) NOT NULL COMMENT '参与者ID'
);


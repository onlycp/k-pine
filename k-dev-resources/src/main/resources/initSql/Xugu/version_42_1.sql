CREATE TABLE IF NOT EXISTS wf_cc_inst
(
    inst_Id     VARCHAR(32) NOT NULL COMMENT '流程实例ID',
    actor_Id    VARCHAR(50) NOT NULL COMMENT '参与者ID',
    creator     VARCHAR(50) DEFAULT NULL COMMENT '发起人',
    create_Time VARCHAR(50) DEFAULT NULL COMMENT '抄送时间',
    finish_Time VARCHAR(50) DEFAULT NULL COMMENT '完成时间',
    status      TINYINT     DEFAULT NULL COMMENT '状态',
    PRIMARY KEY (inst_Id, actor_Id)
);
CREATE TABLE IF NOT EXISTS wf_hist_inst
(
    id          VARCHAR(32) NOT NULL COMMENT '主键ID',
    process_Id  VARCHAR(32) NOT NULL COMMENT '流程定义ID',
    inst_State  TINYINT     NOT NULL COMMENT '状态',
    creator     VARCHAR(500) DEFAULT NULL COMMENT '发起人',
    create_Time VARCHAR(50) NOT NULL COMMENT '发起时间',
    end_Time    VARCHAR(50)  DEFAULT NULL COMMENT '完成时间',
    expire_Time VARCHAR(50)  DEFAULT NULL COMMENT '期望完成时间',
    priority    TINYINT      DEFAULT NULL COMMENT '优先级',
    parent_Id   VARCHAR(32)  DEFAULT NULL COMMENT '父流程ID',
    inst_No     VARCHAR(50)  DEFAULT NULL COMMENT '流程实例编号',
    variable    TEXT COMMENT '附属变量json存储',
    PRIMARY KEY (id)
);

CREATE TABLE IF NOT EXISTS wf_hist_task
(
    id             VARCHAR(32)  NOT NULL COMMENT '主键ID',
    inst_Id        VARCHAR(32)  NOT NULL COMMENT '流程实例ID',
    task_Name      VARCHAR(100) NOT NULL COMMENT '任务名称',
    display_Name   VARCHAR(200) NOT NULL COMMENT '任务显示名称',
    task_Type      TINYINT      NOT NULL COMMENT '任务类型',
    perform_Type   TINYINT      DEFAULT NULL COMMENT '参与类型',
    task_State     TINYINT      NOT NULL COMMENT '任务状态',
    operator       VARCHAR(500) DEFAULT NULL COMMENT '任务处理人',
    create_Time    VARCHAR(50)  NOT NULL COMMENT '任务创建时间',
    finish_Time    VARCHAR(50)  DEFAULT NULL COMMENT '任务完成时间',
    expire_Time    VARCHAR(50)  DEFAULT NULL COMMENT '任务期望完成时间',
    action_Url     VARCHAR(200) DEFAULT NULL COMMENT '任务处理url',
    parent_Task_Id VARCHAR(32)  DEFAULT NULL COMMENT '父任务ID',
    variable       TEXT COMMENT '附属变量json存储',
    PRIMARY KEY (id)
);

CREATE TABLE IF NOT EXISTS wf_hist_task_actor
(
    task_Id  VARCHAR(32)  NOT NULL COMMENT '任务ID',
    actor_Id VARCHAR(500) NOT NULL COMMENT '参与者ID',
    PRIMARY KEY (task_Id, actor_Id)
);
CREATE TABLE IF NOT EXISTS wf_inst
(
    id               VARCHAR(32) NOT NULL COMMENT '主键ID',
    parent_Id        VARCHAR(32)  DEFAULT NULL COMMENT '父流程ID',
    process_Id       VARCHAR(32) NOT NULL COMMENT '流程定义ID',
    creator          VARCHAR(500) DEFAULT NULL COMMENT '发起人',
    create_Time      VARCHAR(50) NOT NULL COMMENT '发起时间',
    expire_Time      VARCHAR(50)  DEFAULT NULL COMMENT '期望完成时间',
    last_Update_Time VARCHAR(50)  DEFAULT NULL COMMENT '上次更新时间',
    last_Updator     VARCHAR(500) DEFAULT NULL COMMENT '上次更新人',
    priority         TINYINT      DEFAULT NULL COMMENT '优先级',
    parent_Node_Name VARCHAR(100) DEFAULT NULL COMMENT '父流程依赖的节点名称',
    inst_No          VARCHAR(50)  DEFAULT NULL COMMENT '流程实例编号',
    variable         TEXT COMMENT '附属变量json存储',
    version          INT          DEFAULT NULL COMMENT '版本',
    PRIMARY KEY (id)
);

CREATE TABLE IF NOT EXISTS wf_process
(
    id           VARCHAR(32) NOT NULL COMMENT '主键ID',
    name         VARCHAR(100) DEFAULT NULL COMMENT '流程名称',
    display_Name VARCHAR(200) DEFAULT NULL COMMENT '流程显示名称',
    type         VARCHAR(100) DEFAULT NULL COMMENT '流程类型',
    instance_Url VARCHAR(200) DEFAULT NULL COMMENT '实例url',
    state        TINYINT      DEFAULT NULL COMMENT '流程是否可用',
    content CLOB DEFAULT NULL COMMENT '流程模型定义',
    version      INT          DEFAULT NULL COMMENT '版本',
    create_Time  VARCHAR(50)  DEFAULT NULL COMMENT '创建时间',
    creator      VARCHAR(50)  DEFAULT NULL COMMENT '创建人',
    PRIMARY KEY (id)
);

CREATE TABLE IF NOT EXISTS wf_surrogate
(
    id           VARCHAR(32) NOT NULL COMMENT '主键ID',
    process_Name VARCHAR(100) DEFAULT NULL COMMENT '流程名称',
    operator     VARCHAR(50)  DEFAULT NULL COMMENT '授权人',
    surrogate    VARCHAR(50)  DEFAULT NULL COMMENT '代理人',
    odate        VARCHAR(64)  DEFAULT NULL COMMENT '操作时间',
    sdate        VARCHAR(64)  DEFAULT NULL COMMENT '开始时间',
    edate        VARCHAR(64)  DEFAULT NULL COMMENT '结束时间',
    state        TINYINT      DEFAULT NULL COMMENT '状态',
    PRIMARY KEY (id)
);

CREATE TABLE IF NOT EXISTS wf_task
(
    id             VARCHAR(32)  NOT NULL COMMENT '主键ID',
    inst_Id        VARCHAR(32)  NOT NULL COMMENT '流程实例ID',
    task_Name      VARCHAR(100) NOT NULL COMMENT '任务名称',
    display_Name   VARCHAR(200) NOT NULL COMMENT '任务显示名称',
    task_Type      TINYINT      NOT NULL COMMENT '任务类型',
    perform_Type   TINYINT      DEFAULT NULL COMMENT '参与类型',
    operator       VARCHAR(500) DEFAULT NULL COMMENT '任务处理人',
    create_Time    VARCHAR(50)  NOT NULL COMMENT '任务创建时间',
    finish_Time    VARCHAR(50)  DEFAULT NULL COMMENT '任务完成时间',
    expire_Time    VARCHAR(50)  DEFAULT NULL COMMENT '任务期望完成时间',
    action_Url     VARCHAR(200) DEFAULT NULL COMMENT '任务处理的url',
    parent_Task_Id VARCHAR(32)  DEFAULT NULL COMMENT '父任务ID',
    variable       TEXT COMMENT '附属变量json存储',
    version        TINYINT      DEFAULT NULL COMMENT '版本',
    PRIMARY KEY (id)
);


CREATE TABLE IF NOT EXISTS wf_task_actor
(
    task_Id  VARCHAR(32)  NOT NULL COMMENT '任务ID',
    actor_Id VARCHAR(500) NOT NULL COMMENT '参与者ID',
    PRIMARY KEY (task_Id, actor_Id)
);



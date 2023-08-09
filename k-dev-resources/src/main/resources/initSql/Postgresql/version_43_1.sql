
CREATE TABLE if not EXISTS wf_ru_timer_job(
                                              id VARCHAR(98) NOT NULL,
                                              inst_id VARCHAR(32) NOT NULL,
                                              process_id VARCHAR(32) NOT NULL,
                                              task_id VARCHAR(32) NOT NULL,
                                              retries int4,
                                              expiredate VARCHAR(50),
                                              repeats int4
);

COMMENT ON TABLE wf_ru_timer_job IS '运行中任务计时器表';
COMMENT ON COLUMN wf_ru_timer_job.id IS '计时任务ID';
COMMENT ON COLUMN wf_ru_timer_job.inst_id IS '流程实例ID';
COMMENT ON COLUMN wf_ru_timer_job.process_id IS '流程定义ID';
COMMENT ON COLUMN wf_ru_timer_job.task_id IS '工作流任务ID';
COMMENT ON COLUMN wf_ru_timer_job.retries IS '重试次数';
COMMENT ON COLUMN wf_ru_timer_job.expiredate IS '到期时间';
COMMENT ON COLUMN wf_ru_timer_job.repeats IS '重复';

CREATE TABLE if not EXISTS wf_ext_node_define(
                                                 id VARCHAR(50),
                                                 who_created VARCHAR(50),
                                                 when_created VARCHAR(50),
                                                 who_modified VARCHAR(50),
                                                 when_modified VARCHAR(50),
                                                 name VARCHAR(200),
                                                 group_name VARCHAR(200),
                                                 type VARCHAR(200),
                                                 start_icon TEXT,
                                                 show_icon TEXT,
                                                 node_json VARCHAR(200),
                                                 is_extend VARCHAR(200),
                                                 status VARCHAR(200)
);

COMMENT ON TABLE wf_ext_node_define IS '青松-节点定义表';
COMMENT ON COLUMN wf_ext_node_define.id IS '主键';
COMMENT ON COLUMN wf_ext_node_define.who_created IS '创建人';
COMMENT ON COLUMN wf_ext_node_define.when_created IS '创建时间';
COMMENT ON COLUMN wf_ext_node_define.who_modified IS '更新人';
COMMENT ON COLUMN wf_ext_node_define.when_modified IS '更新时间';
COMMENT ON COLUMN wf_ext_node_define.name IS '节点名称';
COMMENT ON COLUMN wf_ext_node_define.group_name IS '分类';
COMMENT ON COLUMN wf_ext_node_define.type IS '节点类型';
COMMENT ON COLUMN wf_ext_node_define.start_icon IS '初始图标';
COMMENT ON COLUMN wf_ext_node_define.show_icon IS '流程设计图标';
COMMENT ON COLUMN wf_ext_node_define.node_json IS '节点配置';
COMMENT ON COLUMN wf_ext_node_define.is_extend IS '是否可自定义扩展';
COMMENT ON COLUMN wf_ext_node_define.status IS '是否生效';


alter table wf_ext_node_attribute add column if not EXISTS name  VARCHAR(200) ;
alter table wf_ext_procdef add column if not EXISTS page_form  TEXT ;

alter table wf_ext_procinst add column if not EXISTS task_name  VARCHAR(100) ;
alter table wf_ext_procinst add column if not EXISTS actors  VARCHAR(500) ;
alter table wf_ext_procinst add column if not EXISTS work_inst_ids  TEXT;
alter table wf_ext_procinst add column if not EXISTS audit_user_ids  VARCHAR(500) ;
alter table wf_ext_procinst add column if not EXISTS process_key  VARCHAR(50) ;

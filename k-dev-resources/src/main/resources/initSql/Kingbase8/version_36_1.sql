CREATE TABLE IF NOT EXISTS dev_chat_history (
  id varchar(36)  NOT NULL,
  question CLOB  NOT NULL,
  answer CLOB ,
  args CLOB,
  when_created varchar(50)  DEFAULT NULL,
  who_created varchar(36)  DEFAULT NULL,
  PRIMARY KEY (id)
)

CREATE TABLE IF NOT EXISTS dev_curd (
  id varchar(36)  NOT NULL ,
  name varchar(255)  DEFAULT ,
  group_id varchar(36)  DEFAULT NULL,
  source_name varchar(255)  DEFAULT NULL,
  table_name varchar(255)  DEFAULT NULL,
  primary_name varchar(50)  DEFAULT NULL,
  request_prefix varchar(255)  DEFAULT NULL,
  enable_funs varchar(255)  DEFAULT NULL,
  create_funs varchar(255)  DEFAULT NULL,
  column_json CLOB ,
  app_id varchar(36)  DEFAULT NULL,
  who_created varchar(36)  DEFAULT NULL,
  when_created varchar(20)  DEFAULT NULL,
  who_modified varchar(36)  DEFAULT NULL,
  when_modified varchar(255)  DEFAULT NULL ,
  PRIMARY KEY (id)
)

CREATE TABLE IF NOT EXISTS  dev_data_source (
  id varchar(36)  NOT NULL,
  name varchar(100)  DEFAULT NULL,
  who_created varchar(36)  DEFAULT NULL ,
  when_created varchar(30)  DEFAULT NULL ,
  who_modified varchar(36)  DEFAULT NULL ,
  when_modified varchar(30)  DEFAULT NULL ,
  app_id varchar(36)  DEFAULT NULL ,
  kdb_id varchar(40)  DEFAULT NULL ,
  team_id varchar(36)  DEFAULT NULL ,
  deleted NUMERIC DEFAULT NULL ,
  PRIMARY KEY (id)
)

CREATE TABLE IF NOT EXISTS dev_file_version (
  id varchar(36)  NOT NULL,
  file_name varchar(255)  DEFAULT NULL ,
  path varchar(1000)  DEFAULT NULL,
  os_type varchar(20)  DEFAULT NULL ,
  version varchar(100)  DEFAULT NULL ,
  path_by_package varchar(1000)  DEFAULT NULL ,
  file_size NUMERIC DEFAULT NULL ,
  description varchar(255)  DEFAULT NULL ,
  parent_path varchar(1000)  DEFAULT NULL ,
  when_modified varchar(30)  DEFAULT NULL ,
  who_modified varchar(36)  DEFAULT NULL ,
  PRIMARY KEY (id)
)

CREATE TABLE IF NOT EXISTS dev_pine_plugin (
  id varchar(36)  NOT NULL,
  plugin_name varchar(50)  DEFAULT NULL ,
  plugin_version varchar(10)  DEFAULT NULL,
  author varchar(50)  DEFAULT NULL,
  file_id varchar(32)  DEFAULT NULL ,
  enable_status NUMERIC DEFAULT '0' ,
  app_id varchar(32)  DEFAULT NULL ,
  note CLOB  ,
  when_created varchar(32)  DEFAULT NULL ,
  who_modified varchar(32)  DEFAULT NULL ,
  when_modified varchar(20)  DEFAULT NULL ,
  who_created varchar(32)  DEFAULT NULL,
  PRIMARY KEY (id)
)

CREATE TABLE IF NOT EXISTS dev_plugin_api (
  id varchar(36)  NOT NULL ,
  title varchar(255)  DEFAULT NULL,
  group_code varchar(255)  DEFAULT NULL,
  code varchar(255)  DEFAULT NULL ,
  tags varchar(255)  DEFAULT NULL ,
  notes CLOB  ,
  order_num varchar(10)  DEFAULT NULL ,
  who_created varchar(36)  DEFAULT NULL ,
  when_created varchar(20)  DEFAULT NULL ,
  who_modified varchar(36)  DEFAULT NULL ,
  when_modified varchar(20)  DEFAULT NULL ,
  PRIMARY KEY (id)
)

CREATE TABLE IF NOT EXISTS dev_plugin_group (
  id varchar(36)  NOT NULL,
  name varchar(255)  DEFAULT NULL ,
  code varchar(255)  DEFAULT NULL,
  notes CLOB  ,
  order_num NUMERIC DEFAULT NULL ,
  who_created varchar(36)  DEFAULT NULL,
  when_created varchar(20)  DEFAULT NULL ,
  who_modified varchar(36)  DEFAULT NULL,
  when_modified varchar(20)  DEFAULT NULL ,
  PRIMARY KEY (id)
)

CREATE TABLE IF NOT EXISTS dev_plugin_operation (
  id varchar(36)  NOT NULL,
  code varchar(255)  DEFAULT NULL ,
  tags varchar(255)  DEFAULT NULL,
  api_id varchar(36)  DEFAULT NULL,
  title varchar(255)  DEFAULT NULL,
  notes CLOB ,
  cases CLOB ,
  success_resp CLOB ,
  error_resp CLOB  ,
  in_params CLOB ,
  who_created varchar(36)  DEFAULT NULL ,
  when_created varchar(20)  DEFAULT NULL ,
  who_modified varchar(36)  DEFAULT NULL ,
  when_modified varchar(20)  DEFAULT NULL ,
  order_num NUMERIC DEFAULT '0',
  PRIMARY KEY (id)
)

-- dev_power_link
ALTER TABLE dev_power_link alter column tree_id type varchar(32) ;

CREATE TABLE IF NOT EXISTS  dev_solution (
  id varchar(36)  NOT NULL ,
  name varchar(100)  NOT NULL,
  logo_id varchar(36)  DEFAULT NULL ,
  app_ids CLOB  NOT NULL,
  industry varchar(100)  DEFAULT NULL ,
  scene varchar(100)  DEFAULT NULL ,
  project_manager varchar(100)  DEFAULT NULL ,
  description varchar(255)  NOT NULL,
  content CLOB  NOT NULL ,
  when_created timestamp NULL DEFAULT NULL,
  who_created varchar(36)  DEFAULT NULL,
  when_modified timestamp NULL DEFAULT NULL,
  who_modified varchar(36)  DEFAULT NULL,
  PRIMARY KEY (id) 
)

CREATE TABLE IF NOT EXISTS sys_auto_serial (
  id varchar(100)  NOT NULL DEFAULT '',
  category varchar(100)  DEFAULT NULL,
  type NUMERIC DEFAULT NULL,
  key varchar(50)  DEFAULT NULL ,
  auto_num NUMERIC DEFAULT NULL ,
  num_length NUMERIC DEFAULT NULL ,
  step NUMERIC DEFAULT NULL ,
  start_num NUMERIC DEFAULT NULL ,
  tpl varchar(100)  DEFAULT NULL ,
  locked NUMERIC DEFAULT '0' ,
  create_time varchar(50)  DEFAULT NULL,
  create_user varchar(50)  DEFAULT NULL,
  update_time varchar(50)  DEFAULT NULL,
  update_user varchar(50)  DEFAULT NULL,
  PRIMARY KEY (id) 
)

CREATE TABLE IF NOT EXISTS sys_data_change (
  id varchar(32)  NOT NULL ,
  name varchar(255)  DEFAULT NULL ,
  table_name varchar(255)  DEFAULT NULL,
  operator varchar(255)  DEFAULT NULL,
  oper_time varchar(255)  DEFAULT NULL ,
  content varchar(255)  DEFAULT NULL ,
  object_name varchar(255)  DEFAULT NULL ,
  oper_type varchar(255)  DEFAULT NULL,
  PRIMARY KEY (id)
)

CREATE TABLE IF NOT EXISTS sys_hint_select (
  id varchar(50)  NOT NULL ,
  code varchar(100)  DEFAULT NULL ,
  type varchar(50)  DEFAULT NULL ,
  db_id varchar(100)  DEFAULT NULL ,
  select_sql CLOB ,
  select_fields CLOB  ,
  flow_id varchar(50)  DEFAULT NULL,
  remark CLOB ,
  PRIMARY KEY (id)
)

-- sys_logic_history
ALTER TABLE  sys_logic_history add new_flow_json CLOB null;

-- sys_logic_template
ALTER TABLE sys_logic_template add new_flow_json CLOB null;
ALTER TABLE sys_logic_template add flow_config CLOB null;
ALTER TABLE sys_logic_template add type NUMERIC null;


CREATE TABLE IF NOT EXISTS sys_mq_channel (
  id varchar(64)  NOT NULL ,
  channel_name varchar(50)  DEFAULT NULL ,
  zk_address varchar(50)  DEFAULT NULL ,
  topic varchar(50)  DEFAULT NULL ,
  consumer_group varchar(50)  DEFAULT NULL ,
  consumer_thread NUMERIC DEFAULT NULL ,
  batch_consumer NUMERIC DEFAULT NULL ,
  message_name varchar(50)  DEFAULT NULL ,
  enable NUMERIC DEFAULT NULL,
  PRIMARY KEY (id)
)

CREATE TABLE IF NOT EXISTS sys_task_manage (
  id varchar(40)  NOT NULL,
  template_name varchar(100)  DEFAULT NULL,
  template_content varchar(300)  DEFAULT NULL,
  flow_id varchar(36)  DEFAULT NULL,
  who_created varchar(32)  DEFAULT NULL,
  when_created varchar(20)  DEFAULT NULL,
  PRIMARY KEY (id)
)

-- sys_unit
ALTER TABLE  sys_unit add short_name varchar(36) null;
ALTER TABLE  sys_unit add short_code varchar(100)  null;

-- sys_user
ALTER TABLE sys_user add jira_name varchar(100)  null;

-- wf_ext_comment
ALTER TABLE  wf_ext_comment add task_user CLOB  null; --  用户对象
ALTER TABLE wf_ext_comment add perform_type varchar(50)  null; --'参与方式（0：普通任务；1：参与者会签任务）',
ALTER TABLE wf_ext_comment add task_actors CLOB  null; -- 任务原处理人


CREATE TABLE IF NOT EXISTS wf_ext_power (
  id varchar(50)  NOT NULL,
  who_created varchar(50)  DEFAULT NULL ,
  when_created varchar(50)  DEFAULT NULL,
  who_modified varchar(50)  DEFAULT NULL ,
  when_modified varchar(50)  DEFAULT NULL ,
  process_keys CLOB  ,
  role_ids varchar(1000)  DEFAULT NULL,
  unit_ids varchar(1000)  DEFAULT NULL,
  supervise_unit_ids CLOB  COMMENT,
  handle varchar(50)  DEFAULT NULL ,
  discard varchar(50)  DEFAULT NULL ,
  forward varchar(50)  DEFAULT NULL ,
  remove varchar(50)  DEFAULT NULL,
  revise varchar(50)  DEFAULT NULL ,
  modify_node varchar(50)  DEFAULT NULL ,
  activation varchar(50)  DEFAULT NULL ,
  shut varchar(50)  DEFAULT NULL ,
  PRIMARY KEY (id)
)

-- wf_ext_procinst
ALTER TABLE wf_ext_procinst add task_name varchar(100)  null; --  当前任务节点
ALTER TABLE wf_ext_procinst add actors varchar(50)  null; --节点处理人,
ALTER TABLE wf_ext_procinst add work_inst_ids CLOB  null; -- 关联工单ID
ALTER TABLE wf_ext_procinst add audit_user_ids varchar(100)  null; --  已办处理人
ALTER TABLE wf_ext_procinst add process_key varchar(50)  null; --流程key,
ALTER TABLE wf_ext_procinst add task_time varchar(50)  null; -- 节点送达时间
ALTER TABLE wf_ext_procinst add end_time varchar(50)  null; --  流程审结时间
ALTER TABLE wf_ext_procinst add last_user_id varchar(100)  null; --最后处理人,
ALTER TABLE wf_ext_procinst add last_task_id varchar(50)  null; -- 最后处理任务ID
ALTER TABLE wf_ext_procinst add next_run_sql CLOB  null; -- 处理人检索SQL
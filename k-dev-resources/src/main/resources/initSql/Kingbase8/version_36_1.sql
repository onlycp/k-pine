CREATE TABLE IF NOT EXISTS `dev_chat_history` (
  `id` varchar(36) COLLATE utf8mb4_bin NOT NULL COMMENT 'id',
  `question` longtext COLLATE utf8mb4_bin NOT NULL COMMENT '问题',
  `answer` longtext COLLATE utf8mb4_bin COMMENT '回答',
  `args` longtext COLLATE utf8mb4_bin COMMENT '附加参数（JSON）',
  `when_created` varchar(50) COLLATE utf8mb4_bin DEFAULT NULL COMMENT '创建时间',
  `who_created` varchar(36) COLLATE utf8mb4_bin DEFAULT NULL COMMENT '创建人',
  PRIMARY KEY (`id`)
)

CREATE TABLE IF NOT EXISTS `dev_curd` (
  `id` varchar(36) COLLATE utf8mb4_bin NOT NULL COMMENT '主键',
  `name` varchar(255) COLLATE utf8mb4_bin DEFAULT NULL COMMENT '名称',
  `group_id` varchar(36) COLLATE utf8mb4_bin DEFAULT NULL COMMENT '组ID',
  `source_name` varchar(255) COLLATE utf8mb4_bin DEFAULT NULL COMMENT '数据源名称',
  `table_name` varchar(255) COLLATE utf8mb4_bin DEFAULT NULL COMMENT '数据表名称',
  `primary_name` varchar(50) COLLATE utf8mb4_bin DEFAULT NULL COMMENT '主键字段名',
  `request_prefix` varchar(255) COLLATE utf8mb4_bin DEFAULT NULL COMMENT '请求前缀',
  `enable_funs` varchar(255) COLLATE utf8mb4_bin DEFAULT NULL COMMENT '启动功能',
  `create_funs` varchar(255) COLLATE utf8mb4_bin DEFAULT NULL COMMENT '创建内容',
  `column_json` text COLLATE utf8mb4_bin COMMENT '列定义',
  `app_id` varchar(36) COLLATE utf8mb4_bin DEFAULT NULL COMMENT '应用ID',
  `who_created` varchar(36) COLLATE utf8mb4_bin DEFAULT NULL COMMENT '创建人',
  `when_created` varchar(20) COLLATE utf8mb4_bin DEFAULT NULL COMMENT '创建时间',
  `who_modified` varchar(36) COLLATE utf8mb4_bin DEFAULT NULL COMMENT '修改人',
  `when_modified` varchar(255) COLLATE utf8mb4_bin DEFAULT NULL COMMENT '修改时间',
  PRIMARY KEY (`id`)
)

CREATE TABLE IF NOT EXISTS  `dev_data_source` (
  `id` varchar(36) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL COMMENT 'ID',
  `name` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin DEFAULT NULL COMMENT '数据源名称',
  `who_created` varchar(36) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin DEFAULT NULL COMMENT '创建人员',
  `when_created` varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin DEFAULT NULL COMMENT '创建时间',
  `who_modified` varchar(36) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin DEFAULT NULL COMMENT '修改人员',
  `when_modified` varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin DEFAULT NULL COMMENT '修改时间',
  `app_id` varchar(36) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin DEFAULT NULL COMMENT '关联应用',
  `kdb_id` varchar(40) COLLATE utf8mb4_bin DEFAULT NULL COMMENT '在kingDB中对应的ID',
  `team_id` varchar(36) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin DEFAULT NULL COMMENT '所属团队ID',
  `deleted` int(1) DEFAULT NULL COMMENT '是否已删除',
  PRIMARY KEY (`id`)
)

CREATE TABLE IF NOT EXISTS `dev_file_version` (
  `id` varchar(36) COLLATE utf8mb4_bin NOT NULL COMMENT 'ID',
  `file_name` varchar(255) COLLATE utf8mb4_bin DEFAULT NULL COMMENT '文件名',
  `path` varchar(1000) COLLATE utf8mb4_bin DEFAULT NULL COMMENT '导致安装包的位置',
  `os_type` varchar(20) COLLATE utf8mb4_bin DEFAULT NULL COMMENT '操作系统类型',
  `version` varchar(100) COLLATE utf8mb4_bin DEFAULT NULL COMMENT '版本号（vX.X.X结构）',
  `path_by_package` varchar(1000) COLLATE utf8mb4_bin DEFAULT NULL COMMENT '所在package中的真实位置',
  `file_size` int(11) DEFAULT NULL COMMENT '文件大小',
  `description` varchar(255) COLLATE utf8mb4_bin DEFAULT NULL COMMENT '描述',
  `parent_path` varchar(1000) COLLATE utf8mb4_bin DEFAULT NULL COMMENT '父目录',
  `when_modified` varchar(30) COLLATE utf8mb4_bin DEFAULT NULL COMMENT '更新时间',
  `who_modified` varchar(36) COLLATE utf8mb4_bin DEFAULT NULL COMMENT '更新人',
  PRIMARY KEY (`id`)
)

CREATE TABLE IF NOT EXISTS `dev_pine_plugin` (
  `id` varchar(36) COLLATE utf8mb4_bin NOT NULL COMMENT '主键',
  `plugin_name` varchar(50) COLLATE utf8mb4_bin DEFAULT NULL COMMENT '插件名称',
  `plugin_version` varchar(10) COLLATE utf8mb4_bin DEFAULT NULL COMMENT '插件版本号',
  `author` varchar(50) COLLATE utf8mb4_bin DEFAULT NULL COMMENT '插件作者',
  `file_id` varchar(32) COLLATE utf8mb4_bin DEFAULT NULL COMMENT '文件id',
  `enable_status` int(11) DEFAULT '0' COMMENT '是否启动',
  `app_id` varchar(32) COLLATE utf8mb4_bin DEFAULT NULL COMMENT '归属应用id',
  `note` text COLLATE utf8mb4_bin COMMENT '说明',
  `when_created` varchar(32) COLLATE utf8mb4_bin DEFAULT NULL COMMENT '创建时间',
  `who_modified` varchar(32) COLLATE utf8mb4_bin DEFAULT NULL COMMENT '修改人员',
  `when_modified` varchar(20) COLLATE utf8mb4_bin DEFAULT NULL COMMENT '修改时间',
  `who_created` varchar(32) COLLATE utf8mb4_bin DEFAULT NULL COMMENT '创建人',
  PRIMARY KEY (`id`)
)

CREATE TABLE IF NOT EXISTS `dev_plugin_api` (
  `id` varchar(36) COLLATE utf8mb4_bin NOT NULL COMMENT '主键',
  `title` varchar(255) COLLATE utf8mb4_bin DEFAULT NULL COMMENT '标题',
  `group_code` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin DEFAULT NULL COMMENT '组编码',
  `code` varchar(255) COLLATE utf8mb4_bin DEFAULT NULL COMMENT '编码',
  `tags` varchar(255) COLLATE utf8mb4_bin DEFAULT NULL COMMENT '标签',
  `notes` text COLLATE utf8mb4_bin COMMENT '描述',
  `order_num` varchar(10) COLLATE utf8mb4_bin DEFAULT NULL COMMENT '位置',
  `who_created` varchar(36) COLLATE utf8mb4_bin DEFAULT NULL COMMENT '创建人',
  `when_created` varchar(20) COLLATE utf8mb4_bin DEFAULT NULL COMMENT '创建时间',
  `who_modified` varchar(36) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin DEFAULT NULL COMMENT '修改人',
  `when_modified` varchar(20) COLLATE utf8mb4_bin DEFAULT NULL COMMENT '修改时间',
  PRIMARY KEY (`id`)
)

CREATE TABLE IF NOT EXISTS `dev_plugin_group` (
  `id` varchar(36) COLLATE utf8mb4_bin NOT NULL COMMENT '主键',
  `name` varchar(255) COLLATE utf8mb4_bin DEFAULT NULL COMMENT '名称',
  `code` varchar(255) COLLATE utf8mb4_bin DEFAULT NULL COMMENT '编码',
  `notes` text COLLATE utf8mb4_bin COMMENT '描述',
  `order_num` int(11) DEFAULT NULL COMMENT '排序',
  `who_created` varchar(36) COLLATE utf8mb4_bin DEFAULT NULL COMMENT '创建人',
  `when_created` varchar(20) COLLATE utf8mb4_bin DEFAULT NULL COMMENT '创建时间',
  `who_modified` varchar(36) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin DEFAULT NULL COMMENT '修改人',
  `when_modified` varchar(20) COLLATE utf8mb4_bin DEFAULT NULL COMMENT '修改时间',
  PRIMARY KEY (`id`)
)

CREATE TABLE IF NOT EXISTS `dev_plugin_operation` (
  `id` varchar(36) COLLATE utf8mb4_bin NOT NULL COMMENT '主键',
  `code` varchar(255) COLLATE utf8mb4_bin DEFAULT NULL COMMENT '编码',
  `tags` varchar(255) COLLATE utf8mb4_bin DEFAULT NULL COMMENT '标签',
  `api_id` varchar(36) COLLATE utf8mb4_bin DEFAULT NULL COMMENT 'API',
  `title` varchar(255) COLLATE utf8mb4_bin DEFAULT NULL COMMENT '标题',
  `notes` text COLLATE utf8mb4_bin COMMENT '描述',
  `cases` text CHARACTER SET utf8mb4 COLLATE utf8mb4_bin COMMENT '示例',
  `success_resp` text COLLATE utf8mb4_bin COMMENT '成功示例',
  `error_resp` text COLLATE utf8mb4_bin COMMENT '失败示例',
  `in_params` text COLLATE utf8mb4_bin COMMENT '请求参数 ',
  `who_created` varchar(36) COLLATE utf8mb4_bin DEFAULT NULL COMMENT '创建人',
  `when_created` varchar(20) COLLATE utf8mb4_bin DEFAULT NULL COMMENT '创建时间',
  `who_modified` varchar(36) COLLATE utf8mb4_bin DEFAULT NULL COMMENT '修改人',
  `when_modified` varchar(20) COLLATE utf8mb4_bin DEFAULT NULL COMMENT '修改时间',
  `order_num` int(8) DEFAULT '0' COMMENT '序号',
  PRIMARY KEY (`id`)
)

-- dev_power_link
alter table dev_power_link alter column   tree_id type varchar(32) ;

CREATE TABLE IF NOT EXISTS  `dev_solution` (
  `id` varchar(36) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL COMMENT '方案ID',
  `name` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL COMMENT '方案名称',
  `logo_id` varchar(36) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin DEFAULT NULL COMMENT '客户logo',
  `app_ids` text CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL COMMENT '关联应用ID',
  `industry` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin DEFAULT NULL COMMENT '行业',
  `scene` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin DEFAULT NULL COMMENT '标签',
  `project_manager` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin DEFAULT NULL COMMENT '项目经理',
  `description` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL COMMENT '方案介绍',
  `content` text CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL COMMENT '方案内容',
  `when_created` timestamp NULL DEFAULT NULL COMMENT '创建时间',
  `who_created` varchar(36) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin DEFAULT NULL COMMENT '创建人',
  `when_modified` timestamp NULL DEFAULT NULL COMMENT '修改时间',
  `who_modified` varchar(36) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin DEFAULT NULL COMMENT '修改人',
  PRIMARY KEY (`id`) USING BTREE
)

CREATE TABLE IF NOT EXISTS `sys_auto_serial` (
  `id` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL DEFAULT '',
  `category` varchar(100) COLLATE utf8mb4_bin DEFAULT NULL COMMENT '分类',
  `type` int(11) DEFAULT NULL COMMENT '计算方式1: 按日 2：按月 3:按年',
  `key` varchar(50) COLLATE utf8mb4_bin DEFAULT NULL COMMENT '计算方式key',
  `auto_num` int(11) DEFAULT NULL COMMENT '当前编号',
  `num_length` int(11) DEFAULT NULL COMMENT '编号长度，不够前面补0',
  `step` int(11) DEFAULT NULL COMMENT '步长',
  `start_num` int(11) DEFAULT NULL COMMENT '初始值',
  `tpl` varchar(100) COLLATE utf8mb4_bin DEFAULT NULL COMMENT '模板',
  `locked` int(11) DEFAULT '0' COMMENT '是否被锁 1：已锁 0：未锁',
  `create_time` varchar(50) COLLATE utf8mb4_bin DEFAULT NULL,
  `create_user` varchar(50) COLLATE utf8mb4_bin DEFAULT NULL,
  `update_time` varchar(50) COLLATE utf8mb4_bin DEFAULT NULL,
  `update_user` varchar(50) COLLATE utf8mb4_bin DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
)

CREATE TABLE IF NOT EXISTS `sys_data_change` (
  `id` varchar(32) COLLATE utf8mb4_bin NOT NULL COMMENT '主键',
  `name` varchar(255) COLLATE utf8mb4_bin DEFAULT NULL COMMENT '模块名',
  `table_name` varchar(255) COLLATE utf8mb4_bin DEFAULT NULL COMMENT '表名',
  `operator` varchar(255) COLLATE utf8mb4_bin DEFAULT NULL COMMENT '操作人员',
  `oper_time` varchar(255) COLLATE utf8mb4_bin DEFAULT NULL COMMENT '操作时间',
  `content` varchar(255) COLLATE utf8mb4_bin DEFAULT NULL COMMENT '变更内容',
  `object_name` varchar(255) COLLATE utf8mb4_bin DEFAULT NULL COMMENT '对象名称',
  `oper_type` varchar(255) COLLATE utf8mb4_bin DEFAULT NULL COMMENT '操作类型',
  PRIMARY KEY (`id`)
)

CREATE TABLE IF NOT EXISTS `sys_hint_select` (
  `id` varchar(50) COLLATE utf8mb4_bin NOT NULL COMMENT '主键',
  `code` varchar(100) COLLATE utf8mb4_bin DEFAULT NULL COMMENT '简称',
  `type` varchar(50) COLLATE utf8mb4_bin DEFAULT NULL COMMENT '类型:0=普通下拉,1=树下拉',
  `db_id` varchar(100) COLLATE utf8mb4_bin DEFAULT NULL COMMENT '数据源',
  `select_sql` text COLLATE utf8mb4_bin COMMENT '下拉SQL',
  `select_fields` text COLLATE utf8mb4_bin COMMENT '字段说明',
  `flow_id` varchar(50) COLLATE utf8mb4_bin DEFAULT NULL COMMENT '逻辑编排,优化级最高',
  `remark` text COLLATE utf8mb4_bin COMMENT '备注说明',
  PRIMARY KEY (`id`),
  KEY `code` (`code`)
)

-- sys_logic_history
alter table sys_logic_history add new_flow_json text null;

-- sys_logic_template
alter table sys_logic_template add new_flow_json text null;
alter table sys_logic_template add flow_config text null;
alter table sys_logic_template add type int(11) null;


CREATE TABLE IF NOT EXISTS `sys_mq_channel` (
  `id` varchar(64) COLLATE utf8mb4_bin NOT NULL COMMENT 'ID',
  `channel_name` varchar(50) COLLATE utf8mb4_bin DEFAULT NULL COMMENT '通道名称',
  `zk_address` varchar(50) COLLATE utf8mb4_bin DEFAULT NULL COMMENT 'zk地址',
  `topic` varchar(50) COLLATE utf8mb4_bin DEFAULT NULL COMMENT '主题',
  `consumer_group` varchar(50) COLLATE utf8mb4_bin DEFAULT NULL COMMENT '消费组名',
  `consumer_thread` int(11) DEFAULT NULL COMMENT '消费线程数',
  `batch_consumer` int(11) DEFAULT NULL COMMENT '批量消费数',
  `message_name` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin DEFAULT NULL COMMENT '消息处理逻辑编排',
  `enable` int(11) DEFAULT NULL COMMENT '是否启用 0-否 1-是',
  PRIMARY KEY (`id`)
)

CREATE TABLE IF NOT EXISTS `sys_task_manage` (
  `id` varchar(40) COLLATE utf8mb4_bin NOT NULL,
  `template_name` varchar(100) COLLATE utf8mb4_bin DEFAULT NULL,
  `template_content` varchar(300) COLLATE utf8mb4_bin DEFAULT NULL,
  `flow_id` varchar(36) COLLATE utf8mb4_bin DEFAULT NULL,
  `who_created` varchar(32) COLLATE utf8mb4_bin DEFAULT NULL,
  `when_created` varchar(20) COLLATE utf8mb4_bin DEFAULT NULL,
  PRIMARY KEY (`id`)
)

-- sys_unit
alter table sys_unit add short_name varchar(36) null;
alter table sys_unit add short_code varchar(100)  null;

-- sys_user
alter table sys_user add jira_name varchar(100)  null;

-- wf_ext_comment
alter table wf_ext_comment add task_user text  null; --  用户对象
alter table wf_ext_comment add perform_type varchar(50)  null; --'参与方式（0：普通任务；1：参与者会签任务）',
alter table wf_ext_comment add task_actors text  null; -- 任务原处理人


CREATE TABLE IF NOT EXISTS `wf_ext_power` (
  `id` varchar(50) COLLATE utf8mb4_bin NOT NULL COMMENT '主键',
  `who_created` varchar(50) COLLATE utf8mb4_bin DEFAULT NULL COMMENT '创建人',
  `when_created` varchar(50) COLLATE utf8mb4_bin DEFAULT NULL COMMENT '创建时间',
  `who_modified` varchar(50) COLLATE utf8mb4_bin DEFAULT NULL COMMENT '更新人',
  `when_modified` varchar(50) COLLATE utf8mb4_bin DEFAULT NULL COMMENT '更新时间',
  `process_keys` text COLLATE utf8mb4_bin COMMENT '流程key',
  `role_ids` varchar(1000) COLLATE utf8mb4_bin DEFAULT NULL COMMENT '角色',
  `unit_ids` varchar(1000) COLLATE utf8mb4_bin DEFAULT NULL COMMENT '机构/部门',
  `supervise_unit_ids` text COLLATE utf8mb4_bin COMMENT '监管机构/部门',
  `handle` varchar(50) COLLATE utf8mb4_bin DEFAULT NULL COMMENT '处理',
  `discard` varchar(50) COLLATE utf8mb4_bin DEFAULT NULL COMMENT '废弃',
  `forward` varchar(50) COLLATE utf8mb4_bin DEFAULT NULL COMMENT '转发',
  `remove` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin DEFAULT NULL COMMENT '删除',
  `revise` varchar(50) COLLATE utf8mb4_bin DEFAULT NULL COMMENT '修改',
  `modify_node` varchar(50) COLLATE utf8mb4_bin DEFAULT NULL COMMENT '修改节点',
  `activation` varchar(50) COLLATE utf8mb4_bin DEFAULT NULL COMMENT '激活',
  `shut` varchar(50) COLLATE utf8mb4_bin DEFAULT NULL COMMENT '关闭',
  PRIMARY KEY (`id`)
)

-- wf_ext_procinst
alter table wf_ext_procinst add task_name varchar(100)  null; --  当前任务节点
alter table wf_ext_procinst add actors varchar(50)  null; --节点处理人,
alter table wf_ext_procinst add work_inst_ids text  null; -- 关联工单ID
alter table wf_ext_procinst add audit_user_ids varchar(100)  null; --  已办处理人
alter table wf_ext_procinst add process_key varchar(50)  null; --流程key,
alter table wf_ext_procinst add task_time varchar(50)  null; -- 节点送达时间
alter table wf_ext_procinst add end_time varchar(50)  null; --  流程审结时间
alter table wf_ext_procinst add last_user_id varchar(100)  null; --最后处理人,
alter table wf_ext_procinst add last_task_id varchar(50)  null; -- 最后处理任务ID
alter table wf_ext_procinst add next_run_sql text  null; -- 处理人检索SQL
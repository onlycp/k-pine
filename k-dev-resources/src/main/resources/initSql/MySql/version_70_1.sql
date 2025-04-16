
-- 以下是来自 version_20_1.sql 的内容 --

CREATE TABLE IF NOT EXISTS `dev_sql_run`  (
                                              `id` varchar(36) NOT NULL COMMENT 'id',
                                              `version` int NOT NULL COMMENT '关联版本号',
                                              `md5` varchar(100) NULL DEFAULT NULL COMMENT 'md5',
                                              `when_created` varchar(30) NULL DEFAULT NULL COMMENT '执行时间',
                                              `execution_time` int NULL DEFAULT NULL COMMENT '执行时长（毫秒）',
                                              `success` tinyint NOT NULL COMMENT '是否成功',
                                              PRIMARY KEY (`id`) 
);


CREATE TABLE IF NOT EXISTS `dev_api`  (
                                          `id` varchar(36) NOT NULL,
                                          `api_argv_type` int NULL DEFAULT NULL COMMENT '参数组装方式',
                                          `api_code` varchar(50) NULL DEFAULT NULL COMMENT '接口编码',
                                          `api_flow_id` varchar(50) NULL DEFAULT NULL COMMENT '流程ID',
                                          `api_method` varchar(36) NULL DEFAULT 'get' COMMENT '请求方式',
                                          `api_name` varchar(50) NULL DEFAULT NULL COMMENT '接口名称',
                                          `api_note` text NULL COMMENT '接口描述',
                                          `api_req_argv` text NULL COMMENT '请求参数',
                                          `api_result_handler` varchar(128) NULL DEFAULT NULL COMMENT '结果处理类',
                                          `api_rsp_argv` text NULL COMMENT '响应结果',
                                          `api_tags` varchar(128) NULL DEFAULT NULL COMMENT '标签',
                                          `api_url` varchar(128) NULL DEFAULT NULL COMMENT '接口路径',
                                          `app_id` varchar(36) NULL DEFAULT NULL COMMENT '关联应用',
                                          `application_id` varchar(36) NULL DEFAULT NULL COMMENT '归属应用ID',
                                          `call_type` int NULL DEFAULT NULL COMMENT '调试方式',
                                          `when_created` varchar(30) NULL DEFAULT NULL COMMENT '创建时间',
                                          `when_modified` varchar(30) NULL DEFAULT NULL COMMENT '修改时间',
                                          `who_created` varchar(36) NULL DEFAULT NULL COMMENT '创建人',
                                          `who_modified` varchar(36) NULL DEFAULT NULL COMMENT '修改人',
                                          PRIMARY KEY (`id`) 
);


CREATE TABLE IF NOT EXISTS `dev_application`  (
                                                  `id` varchar(36) NOT NULL,
                                                  `app_public_type` int NULL DEFAULT 0 COMMENT '应用开启类型： 0：普通应用，1：公共库应用，2：系统库应用',
                                                  `app_type` varchar(100) NULL DEFAULT NULL COMMENT '应用类型：可多个，逗号分隔，0: PC Web应用，1：移动端Web应用，2： 小程序，3： APP',
                                                  `data_source` varchar(1000) NULL DEFAULT NULL COMMENT '数据源配置',
                                                  `default_path` varchar(255) NULL DEFAULT NULL COMMENT '默认路径',
                                                  `deleted` tinyint(1) NULL DEFAULT 0 COMMENT '是否已删除',
                                                  `description` varchar(255) NULL DEFAULT NULL COMMENT '应用介绍',
                                                  `dev_status` tinyint(1) NULL DEFAULT NULL COMMENT '开发状态，0: 新建1: 确认版本2: 有更新',
                                                  `enable_status` tinyint(1) NULL DEFAULT NULL COMMENT '可用状态',
                                                  `faas_port` int NULL DEFAULT NULL COMMENT 'faas端口号',
                                                  `name` varchar(100) NULL DEFAULT NULL COMMENT '应用名',
                                                  `pine_port` int NULL DEFAULT NULL COMMENT '青松端口号',
                                                  `short_name` varchar(30) NULL DEFAULT NULL COMMENT '应用短英文名（用于数据库等前缀命名），- 必须唯一，- 必须为纯英文、小写字母命名- 用于创建业务目录、数据库，',
                                                  `system_logo` varchar(255) NULL DEFAULT NULL COMMENT '应用图标',
                                                  `version` varchar(50) NULL DEFAULT NULL COMMENT '当前发布版本',
                                                  `when_created` varchar(30) NULL DEFAULT NULL COMMENT '创建时间',
                                                  `when_modified` varchar(30) NULL DEFAULT NULL COMMENT '修改时间',
                                                  `who_created` varchar(36) NULL DEFAULT NULL COMMENT '创建人',
                                                  `who_in_charge` varchar(255) NULL DEFAULT NULL COMMENT '负责人',
                                                  `who_modified` varchar(36) NULL DEFAULT NULL COMMENT '修改人',
                                                  PRIMARY KEY (`id`) 
);


CREATE TABLE IF NOT EXISTS `dev_application_version_history`  (
                                                                  `id` varchar(36) NOT NULL,
                                                                  `app_id` varchar(36) NULL DEFAULT NULL COMMENT '应用ID',
                                                                  `export_data` text NULL COMMENT '导出数据参数',
                                                                  `file_name` varchar(255) NULL DEFAULT NULL COMMENT '文件名',
                                                                  `note` varchar(255) NULL DEFAULT NULL COMMENT '备注',
                                                                  `version` varchar(50) NULL DEFAULT NULL COMMENT '版本',
                                                                  `when_created` varchar(30) NULL DEFAULT NULL COMMENT '创建时间',
                                                                  `who_created` varchar(36) NULL DEFAULT NULL COMMENT '创建人',
                                                                  PRIMARY KEY (`id`) 
);


CREATE TABLE IF NOT EXISTS `dev_document`  (
                                               `id` varchar(36) NOT NULL,
                                               `content` mediumtext NULL COMMENT '正文',
                                               `deleted` tinyint NULL DEFAULT 0 COMMENT '逻辑删除 0:正常 1：删除',
                                               `name` varchar(255) NULL DEFAULT NULL COMMENT '标题',
                                               `order` int NULL DEFAULT NULL COMMENT '目录排序',
                                               `parent_id` varchar(255) NULL DEFAULT NULL COMMENT '父节点id',
                                               `path` varchar(255) NULL DEFAULT NULL COMMENT '文件路径',
                                               `when_created` date NULL DEFAULT NULL COMMENT '创建时间',
                                               `who_created` varchar(255) NULL DEFAULT NULL COMMENT '创建人',
                                               PRIMARY KEY (`id`) 
);


CREATE TABLE IF NOT EXISTS `dev_module`  (
                                             `id` varchar(36) NOT NULL,
                                             `app_id` varchar(36) NULL DEFAULT NULL COMMENT '关联应用',
                                             `has_path` int NULL DEFAULT NULL COMMENT '是否有路径',
                                             `is_sys` int NULL DEFAULT NULL COMMENT '是否系统',
                                             `name` varchar(100) NULL DEFAULT NULL COMMENT '名称',
                                             `parent_id` varchar(36) NULL DEFAULT NULL COMMENT '父节点',
                                             `path` varchar(255) NULL DEFAULT NULL COMMENT '路径',
                                             `sort` int NULL DEFAULT NULL COMMENT '排序',
                                             `when_created` varchar(50) NULL DEFAULT NULL COMMENT '创建时间',
                                             `when_modified` varchar(50) NULL DEFAULT NULL COMMENT '修改时间',
                                             `who_created` varchar(36) NULL DEFAULT NULL COMMENT '创建人',
                                             `who_modified` varchar(36) NULL DEFAULT NULL COMMENT '修改人',
                                             PRIMARY KEY (`id`) 
);


CREATE TABLE IF NOT EXISTS `dev_ota_channel`  (
                                                  `id` varchar(36) NOT NULL,
                                                  `auth_token` varchar(50) NULL DEFAULT NULL COMMENT '安全令牌',
                                                  `channel_name` varchar(50) NULL DEFAULT NULL COMMENT '通道名称',
                                                  `channel_url` varchar(100) NULL DEFAULT NULL COMMENT '服务器地址',
                                                  `master` int NULL DEFAULT 0 COMMENT '是否主通道',
                                                  `note` varchar(255) NULL DEFAULT NULL COMMENT '备注信息',
                                                  `sign_secret` varchar(50) NULL DEFAULT NULL COMMENT '签名密钥',
                                                  `when_created` varchar(30) NULL DEFAULT NULL COMMENT '创建时间',
                                                  `when_modified` varchar(30) NULL DEFAULT NULL COMMENT '修改时间',
                                                  `who_created` varchar(36) NULL DEFAULT NULL COMMENT '创建人',
                                                  `who_modified` varchar(36) NULL DEFAULT NULL COMMENT '修改人员',
                                                  PRIMARY KEY (`id`) 
);


CREATE TABLE IF NOT EXISTS `dev_page`  (
                                           `id` varchar(36) NOT NULL,
                                           `app_id` varchar(36) NULL DEFAULT NULL COMMENT '应用ID',
                                           `app_type` varchar(100) NULL DEFAULT NULL COMMENT '应用类型：可多个，逗号分隔，0: PC Web应用，1：移动端Web应用，2： 小程序，3： APP',
                                           `deleted` tinyint NULL DEFAULT 0 COMMENT '删除标识',
                                           `description` varchar(255) NULL DEFAULT NULL COMMENT '页面介绍',
                                           `dev_status` tinyint(1) NULL DEFAULT NULL COMMENT '开发状态',
                                           `enable_status` tinyint(1) NULL DEFAULT NULL COMMENT '可用状态',
                                           `login_required` tinyint(1) NULL DEFAULT NULL COMMENT '是否需要登录才可以访问',
                                           `name` varchar(255) NULL DEFAULT NULL COMMENT '页面名称',
                                           `page_json` longtext NULL COMMENT '页面数据化JSON',
                                           `path` varchar(255) NULL DEFAULT NULL COMMENT '访问路径 ',
                                           `when_created` varchar(30) NULL DEFAULT NULL COMMENT '创建时间',
                                           `when_modified` varchar(30) NULL DEFAULT NULL COMMENT '修改时间',
                                           `who_created` varchar(36) NULL DEFAULT NULL COMMENT '创建人',
                                           `who_modified` varchar(36) NULL DEFAULT NULL COMMENT '修改人',
                                           PRIMARY KEY (`id`) 
);


CREATE TABLE IF NOT EXISTS `dev_page_history`  (
                                                   `id` varchar(36) NOT NULL,
                                                   `page_id` varchar(36) NULL DEFAULT NULL COMMENT '页面ID',
                                                   `page_json` longtext NULL COMMENT '页面JSON',
                                                   `version_tag` varchar(50) NULL DEFAULT NULL COMMENT '版本标签',
                                                   `version_tag_time` varchar(30) NULL DEFAULT NULL COMMENT '版本时间',
                                                   `when_created` varchar(30) NULL DEFAULT NULL COMMENT '创建时间',
                                                   `who_created` varchar(36) NULL DEFAULT NULL COMMENT '创建人',
                                                   PRIMARY KEY (`id`) 
);


CREATE TABLE IF NOT EXISTS `dev_pine_plugin`  (
                                                  `id` varchar(36) NOT NULL,
                                                  `app_id` varchar(36) NULL DEFAULT NULL COMMENT '归属应用id',
                                                  `author` varchar(50) NULL DEFAULT NULL COMMENT '插件作者',
                                                  `enable_status` int NULL DEFAULT 0 COMMENT '是否启动',
                                                  `file_id` varchar(36) NULL DEFAULT NULL COMMENT '文件id',
                                                  `note` text NULL COMMENT '说明',
                                                  `plugin_name` varchar(50) NULL DEFAULT NULL COMMENT '插件名称',
                                                  `plugin_version` varchar(10) NULL DEFAULT NULL COMMENT '插件版本号',
                                                  `when_created` varchar(36) NULL DEFAULT NULL COMMENT '创建时间',
                                                  `when_modified` varchar(30) NULL DEFAULT NULL COMMENT '修改时间',
                                                  `who_created` varchar(36) NULL DEFAULT NULL COMMENT '创建人',
                                                  `who_modified` varchar(36) NULL DEFAULT NULL COMMENT '修改人员',
                                                  PRIMARY KEY (`id`) 
);


CREATE TABLE IF NOT EXISTS `dev_power_link`  (
                                                 `id` varchar(36) NOT NULL,
                                                 `power_id` varchar(36) NULL DEFAULT NULL COMMENT '能力id',
                                                 `power_type` int NULL DEFAULT NULL COMMENT '能力类型 1: 逻辑编排 2:函数 3:kutils 4:逻辑编排模板',
                                                 `tree_id` varchar(36) NULL DEFAULT NULL COMMENT '能力树id',
                                                 `when_created` varchar(30) NULL DEFAULT NULL COMMENT '创建时间',
                                                 `who_created` varchar(36) NULL DEFAULT NULL COMMENT '创建人',
                                                 PRIMARY KEY (`id`) 
);


CREATE TABLE IF NOT EXISTS `dev_power_tree`  (
                                                 `id` varchar(36) NOT NULL,
                                                 `name` varchar(128) NULL DEFAULT NULL COMMENT '名称',
                                                 `note` varchar(255) NULL DEFAULT NULL COMMENT '说明',
                                                 `parent_id` varchar(36) NULL DEFAULT NULL COMMENT '父级id',
                                                 `path` text NULL COMMENT '树路径',
                                                 `when_created` varchar(30) NULL DEFAULT NULL COMMENT '创建时间',
                                                 `when_modified` varchar(30) NULL DEFAULT NULL COMMENT '修改时间',
                                                 `who_created` varchar(36) NULL DEFAULT NULL COMMENT '创建人',
                                                 `who_modified` varchar(36) NULL DEFAULT NULL COMMENT '修改人',
                                                 PRIMARY KEY (`id`) 
);




CREATE TABLE IF NOT EXISTS `dev_sql_script`  (
                                                 `id` varchar(36) NOT NULL,
                                                 `description` varchar(255) NULL DEFAULT NULL COMMENT '描述',
                                                 `is_once` tinyint NULL DEFAULT 1 COMMENT '是否只执行1次',
                                                 `sql` longtext NULL COMMENT '执行脚本',
                                                 `version` int NULL DEFAULT NULL COMMENT '版本号',
                                                 PRIMARY KEY (`id`) 
);


CREATE TABLE IF NOT EXISTS `dev_team`  (
                                           `id` varchar(36) NOT NULL,
                                           `deleted` tinyint(1) NULL DEFAULT 0 COMMENT '是否已删除',
                                           `description` varchar(255) NULL DEFAULT NULL COMMENT '团队简介',
                                           `name` varchar(100) NULL DEFAULT NULL COMMENT '团队名称',
                                           `owner` varchar(36) NULL DEFAULT NULL COMMENT '团队负责人',
                                           `when_created` varchar(30) NULL DEFAULT NULL COMMENT '创建时间',
                                           `when_modified` varchar(30) NULL DEFAULT NULL COMMENT '修改时间',
                                           `who_created` varchar(36) NULL DEFAULT NULL COMMENT '创建人',
                                           `who_modified` varchar(36) NULL DEFAULT NULL COMMENT '修改人',
                                           PRIMARY KEY (`id`) 
);


CREATE TABLE IF NOT EXISTS `dev_team_app`  (
                                               `id` varchar(36) NOT NULL,
                                               `app_id` varchar(36) NULL DEFAULT NULL COMMENT '当类型为应用团队时，必须有应用APP_ID',
                                               `team_id` varchar(36) NULL DEFAULT NULL COMMENT '团队ID',
                                               `team_type` tinyint(1) NULL DEFAULT NULL COMMENT '团队类型（1：平台团队，2：应用团队）',
                                               `when_created` varchar(30) NULL DEFAULT NULL COMMENT '创建时间',
                                               `who_created` varchar(36) NULL DEFAULT NULL COMMENT '创建人',
                                               PRIMARY KEY (`id`) 
);


CREATE TABLE IF NOT EXISTS `dev_team_member`  (
                                                  `id` varchar(36) NOT NULL,
                                                  `app_id` varchar(255) NULL DEFAULT NULL COMMENT '关联应用',
                                                  `is_owner` tinyint(1) NULL DEFAULT NULL COMMENT '是否为团队负责人',
                                                  `team_id` varchar(36) NULL DEFAULT NULL COMMENT '团队ID',
                                                  `team_role_id` varchar(36) NULL DEFAULT NULL COMMENT '团队角色',
                                                  `user_id` varchar(36) NULL DEFAULT NULL COMMENT '用户ID',
                                                  `when_join` varchar(20) NULL DEFAULT NULL COMMENT '加入时间',
                                                  `who_invite` varchar(36) NULL DEFAULT NULL COMMENT '邀请人',
                                                  PRIMARY KEY (`id`) 
);


CREATE TABLE IF NOT EXISTS `dev_topological`  (
                                                  `id` varchar(36) NOT NULL,
                                                  `app_id` varchar(36) NULL DEFAULT NULL COMMENT '应用ID',
                                                  `deleted` tinyint NULL DEFAULT 0 COMMENT '删除标识',
                                                  `description` varchar(255) NULL DEFAULT NULL COMMENT '页面介绍',
                                                  `enable_status` tinyint(1) NULL DEFAULT NULL COMMENT '可用状态',
                                                  `name` varchar(255) NULL DEFAULT NULL COMMENT '页面名称',
                                                  `page_json` longtext NULL COMMENT '页面数据化JSON',
                                                  `when_created` varchar(30) NULL DEFAULT NULL COMMENT '创建时间',
                                                  `when_modified` varchar(30) NULL DEFAULT NULL COMMENT '修改时间',
                                                  `who_created` varchar(36) NULL DEFAULT NULL COMMENT '创建人',
                                                  `who_modified` varchar(36) NULL DEFAULT NULL COMMENT '修改人',
                                                  PRIMARY KEY (`id`) 
);


CREATE TABLE IF NOT EXISTS `dev_view_model`  (
                                                 `id` varchar(36) NOT NULL,
                                                 `app_id` varchar(36) NULL DEFAULT NULL COMMENT '关联应用',
                                                 `deleted` int NULL DEFAULT 0 COMMENT '逻辑删除',
                                                 `name` varchar(50) NULL DEFAULT NULL COMMENT '名称',
                                                 `note` text NULL COMMENT '描述',
                                                 `tag` varchar(100) NULL DEFAULT NULL COMMENT '标签，多个用逗号分隔',
                                                 `when_created` varchar(30) NULL DEFAULT NULL COMMENT '创建时间',
                                                 `when_modified` varchar(30) NULL DEFAULT NULL COMMENT '修改时间',
                                                 `who_created` varchar(36) NULL DEFAULT NULL COMMENT '创建人员',
                                                 `who_modified` varchar(36) NULL DEFAULT NULL COMMENT '修改人员',
                                                 PRIMARY KEY (`id`) 
);


CREATE TABLE IF NOT EXISTS `dev_view_model_field`  (
                                                       `id` varchar(36) NOT NULL,
                                                       `app_id` varchar(36) NULL DEFAULT NULL COMMENT '关联应用',
                                                       `default_text` varchar(50) NULL DEFAULT NULL COMMENT '默认显示',
                                                       `field` varchar(50) NULL DEFAULT NULL COMMENT '键名',
                                                       `format_pattern` varchar(50) NULL DEFAULT NULL COMMENT '格式规则',
                                                       `format_type` varchar(20) NULL DEFAULT NULL COMMENT '格式类型',
                                                       `hidden` int NULL DEFAULT 0 COMMENT '是否隐藏',
                                                       `label` varchar(50) NULL DEFAULT NULL COMMENT '标签',
                                                       `order_num` int NULL DEFAULT 0 COMMENT '排序',
                                                       `type` varchar(20) NULL DEFAULT NULL COMMENT '数据类型',
                                                       `view_model_id` varchar(36) NULL DEFAULT NULL COMMENT '模型ID',
                                                       `when_created` varchar(30) NULL DEFAULT NULL COMMENT '创建时间',
                                                       `when_modified` varchar(30) NULL DEFAULT NULL COMMENT '修改时间',
                                                       `who_created` varchar(36) NULL DEFAULT NULL COMMENT '创建人员',
                                                       `who_modified` varchar(36) NULL DEFAULT NULL COMMENT '修改人员',
                                                       PRIMARY KEY (`id`) 
);


CREATE TABLE IF NOT EXISTS `dev_view_model_flow`  (
                                                      `id` varchar(36) NOT NULL,
                                                      `app_id` varchar(36) NULL DEFAULT NULL COMMENT '关联应用',
                                                      `flow_id` varchar(36) NULL DEFAULT NULL COMMENT '流水ID',
                                                      `view_model_id` varchar(36) NULL DEFAULT NULL COMMENT '模型ID',
                                                      `when_created` varchar(30) NULL DEFAULT NULL COMMENT '创建时间',
                                                      `when_modified` varchar(30) NULL DEFAULT NULL COMMENT '修改时间',
                                                      `who_created` varchar(36) NULL DEFAULT NULL COMMENT '创建人员',
                                                      `who_modified` varchar(36) NULL DEFAULT NULL COMMENT '修改人员',
                                                      PRIMARY KEY (`id`) 
);


CREATE TABLE IF NOT EXISTS `ext_plugin_interface`  (
                                                       `id` varchar(36) NOT NULL,
                                                       `content` text NULL COMMENT '接口使用说明Demo',
                                                       `create_time` varchar(20) NULL DEFAULT NULL,
                                                       `create_user` varchar(255) NULL DEFAULT NULL,
                                                       `deleted` int NULL DEFAULT 0 COMMENT '1:逻辑删',
                                                       `description` varchar(1024) NULL DEFAULT NULL COMMENT '接口描述',
                                                       `name` varchar(255) NULL DEFAULT NULL COMMENT '接口名称',
                                                       `plugin_id` varchar(255) NULL DEFAULT NULL COMMENT '插件id',
                                                       `resp_type` varchar(255) NULL DEFAULT '' COMMENT '返回值类型',
                                                       `update_time` varchar(20) NULL DEFAULT NULL,
                                                       `update_user` varchar(255) NULL DEFAULT NULL,
                                                       PRIMARY KEY (`id`) 
);


CREATE TABLE IF NOT EXISTS `ext_plugin_tree`  (
                                                  `id` varchar(36) NOT NULL,
                                                  `check_time` varchar(20) NULL DEFAULT NULL COMMENT '插件检测时间',
                                                  `clazz_name` varchar(255) NULL DEFAULT NULL COMMENT '调用插件方法名',
                                                  `create_time` varchar(20) NULL DEFAULT NULL,
                                                  `create_user` varchar(255) NULL DEFAULT NULL,
                                                  `description` text NULL COMMENT '描述',
                                                  `ext_name` varchar(255) NULL DEFAULT NULL COMMENT '插件名称',
                                                  `jar_name` varchar(255) NULL DEFAULT NULL COMMENT 'jar包名称',
                                                  `name` varchar(255) NULL DEFAULT NULL COMMENT '中文名',
                                                  `status` int NULL DEFAULT 0 COMMENT '服务器是否存在该包，0:不存在;1:存在',
                                                  `type` int NULL DEFAULT NULL COMMENT '1:一级节点；2:二级节点',
                                                  `update_time` varchar(20) NULL DEFAULT NULL,
                                                  `update_user` varchar(255) NULL DEFAULT NULL,
                                                  PRIMARY KEY (`id`) 
);


CREATE TABLE IF NOT EXISTS `kfaas_lib`  (
                                            `id` varchar(36) NOT NULL,
                                            `create_time` varchar(20) NULL DEFAULT NULL,
                                            `create_user` varchar(255) NULL DEFAULT NULL,
                                            `jar_name` varchar(255) NULL DEFAULT NULL COMMENT 'jar包名称',
                                            `status` int NULL DEFAULT 0 COMMENT '服务器是否存在该包，0:不存在;1:存在',
                                            `update_time` varchar(20) NULL DEFAULT NULL,
                                            `update_user` varchar(255) NULL DEFAULT NULL,
                                            PRIMARY KEY (`id`) 
);


CREATE TABLE IF NOT EXISTS `number_regulation`  (
                                                    `id` varchar(36) NOT NULL,
                                                    `sort` varchar(255) NULL DEFAULT NULL COMMENT '排序',
                                                    `type` varchar(255) NULL DEFAULT NULL COMMENT '规则类型',
                                                    `value` varchar(255) NULL DEFAULT NULL COMMENT '规则值',
                                                    PRIMARY KEY (`id`) 
);


CREATE TABLE IF NOT EXISTS `number_serial`  (
                                                `id` varchar(36) NOT NULL,
                                                `create_time` varchar(255) NULL DEFAULT NULL COMMENT '创建时间',
                                                `name` varchar(255) NULL DEFAULT NULL COMMENT '流水号名称',
                                                `number` varchar(255) NULL DEFAULT NULL COMMENT '流水号编号',
                                                `regulationId` varchar(64) NULL DEFAULT NULL COMMENT '规则ID',
                                                `remark` varchar(255) NULL DEFAULT NULL COMMENT '备注',
                                                `start_value` varchar(255) NULL DEFAULT NULL COMMENT '开始值',
                                                `step_value` varchar(255) NULL DEFAULT NULL COMMENT '步长值',
                                                `update_time` varchar(255) NULL DEFAULT NULL COMMENT '更新时间',
                                                PRIMARY KEY (`id`) 
);


CREATE TABLE IF NOT EXISTS `open_account`  (
                                               `id` varchar(36) NOT NULL,
                                               `access_id` varchar(36) NULL DEFAULT NULL COMMENT '接入者ID',
                                               `access_name` varchar(128) NULL DEFAULT NULL COMMENT '接入者名称',
                                               `auth_params` text NULL COMMENT '参数配置',
                                               `auth_type` int NULL DEFAULT NULL COMMENT '授权类型1：简单模式，即access_id为access_token, 此时token是固定的',
                                               `invalid_date` varchar(20) NULL DEFAULT NULL COMMENT '失效日期',
                                               `sign_key` varchar(50) NULL DEFAULT NULL COMMENT '签名密钥',
                                               `status` int NULL DEFAULT 1 COMMENT '是否启用',
                                               `valid_date` varchar(20) NULL DEFAULT NULL COMMENT '生效日期',
                                               `validate_sign` int NULL DEFAULT 0 COMMENT '是否验签',
                                               `when_created` varchar(30) NULL DEFAULT NULL COMMENT '创建时间',
                                               `when_modified` varchar(30) NULL DEFAULT NULL COMMENT '修改时间',
                                               `who_created` varchar(36) NULL DEFAULT NULL COMMENT '创建人',
                                               `who_modified` varchar(36) NULL DEFAULT NULL COMMENT '修改人',
                                               PRIMARY KEY (`id`) 
);


CREATE TABLE IF NOT EXISTS `open_account_api`  (
                                                   `id` varchar(36) NOT NULL,
                                                   `account_id` varchar(36) NULL DEFAULT NULL COMMENT '账号id',
                                                   `api_id` varchar(36) NULL DEFAULT NULL COMMENT '接口id',
                                                   `when_created` varchar(30) NULL DEFAULT NULL COMMENT '创建时间',
                                                   `who_created` varchar(36) NULL DEFAULT NULL COMMENT '创建人',
                                                   PRIMARY KEY (`id`) 
);


CREATE TABLE IF NOT EXISTS `open_api_log`  (
                                               `id` varchar(36) NOT NULL,
                                               `access_id` varchar(100) NULL DEFAULT NULL COMMENT '接口入商名称',
                                               `api_name` varchar(100) NULL DEFAULT NULL COMMENT '接口名称',
                                               `error_message` varchar(255) NULL DEFAULT NULL COMMENT '错误信息',
                                               `request_ip` varchar(20) NULL DEFAULT NULL COMMENT '请求IP',
                                               `request_params` longtext NULL COMMENT '请求参数',
                                               `request_time` varchar(20) NULL DEFAULT NULL COMMENT '请求时间',
                                               `success` tinyint NULL DEFAULT NULL COMMENT '是否成功',
                                               `use_time` tinyint NULL DEFAULT NULL COMMENT '响应时间(秒)',
                                               PRIMARY KEY (`id`) 
);


CREATE TABLE IF NOT EXISTS `rep_app`  (
                                          `id` varchar(36) NOT NULL,
                                          `app_name` varchar(50) NULL DEFAULT NULL COMMENT '报表名称',
                                          `app_note` varchar(256) NULL DEFAULT NULL COMMENT '报表描述',
                                          `when_created` varchar(30) NULL DEFAULT NULL COMMENT '创建时间',
                                          `when_modified` varchar(30) NULL DEFAULT NULL COMMENT '修改时间',
                                          `who_created` varchar(36) NULL DEFAULT NULL COMMENT '创建人',
                                          `who_modified` varchar(36) NULL DEFAULT NULL COMMENT '更新人',
                                          PRIMARY KEY (`id`) 
);


CREATE TABLE IF NOT EXISTS `rep_dataset`  (
                                              `id` varchar(36) NOT NULL,
                                              `column_def` text NULL COMMENT '列定义',
                                              `ds_meta` text NULL COMMENT '配置信息',
                                              `ds_name` varchar(50) NULL DEFAULT NULL COMMENT '数据集名称',
                                              `ds_note` varchar(255) NULL DEFAULT NULL COMMENT '描述信息',
                                              `ds_type` int NULL DEFAULT NULL COMMENT '数据集类型 1：sql 2:json 3:excel',
                                              `rep_app_id` varchar(36) NULL DEFAULT NULL COMMENT '报表应用id',
                                              `when_created` varchar(30) NULL DEFAULT NULL COMMENT '创建时间',
                                              `when_modified` varchar(30) NULL DEFAULT NULL COMMENT '修改时间',
                                              `who_created` varchar(36) NULL DEFAULT NULL COMMENT '创建人员',
                                              `who_modified` varchar(36) NULL DEFAULT NULL COMMENT '修改人员',
                                              PRIMARY KEY (`id`) 
);


CREATE TABLE IF NOT EXISTS `sys_api`  (
                                          `id` varchar(36) NOT NULL,
                                          `api_argv_type` int NULL DEFAULT NULL COMMENT '参数组装方式',
                                          `api_code` varchar(50) NULL DEFAULT NULL COMMENT '接口编码',
                                          `api_flow_id` varchar(50) NULL DEFAULT NULL COMMENT '流程ID',
                                          `api_method` varchar(36) NULL DEFAULT 'get' COMMENT '请求方式',
                                          `api_name` varchar(50) NULL DEFAULT NULL COMMENT '接口名称',
                                          `api_note` text NULL COMMENT '接口描述',
                                          `api_req_argv` text NULL COMMENT '请求参数',
                                          `api_result_handler` varchar(128) NULL DEFAULT NULL COMMENT '结果处理类',
                                          `api_rsp_argv` text NULL COMMENT '响应结果',
                                          `api_tags` varchar(128) NULL DEFAULT NULL COMMENT '标签',
                                          `api_url` varchar(128) NULL DEFAULT NULL COMMENT '接口路径',
                                          `app_id` varchar(36) NULL DEFAULT NULL COMMENT '关联应用',
                                          `call_type` int NULL DEFAULT NULL COMMENT '调试方式',
                                          `module_id` varchar(36) NULL DEFAULT NULL COMMENT '关联模块',
                                          `when_created` varchar(30) NULL DEFAULT NULL COMMENT '创建时间',
                                          `when_modified` varchar(30) NULL DEFAULT NULL COMMENT '修改时间',
                                          `who_created` varchar(36) NULL DEFAULT NULL COMMENT '创建人',
                                          `who_modified` varchar(36) NULL DEFAULT NULL COMMENT '修改人',
                                          PRIMARY KEY (`id`) 
);


CREATE TABLE IF NOT EXISTS `sys_auto_serial`  (
                                                  `id` varchar(36) NOT NULL,
                                                  `auto_num` int NULL DEFAULT NULL COMMENT '当前编号',
                                                  `category` varchar(100) NULL DEFAULT NULL COMMENT '分类',
                                                  `create_time` varchar(20) NULL DEFAULT NULL,
                                                  `create_user` varchar(50) NULL DEFAULT NULL,
                                                  `key` varchar(50) NULL DEFAULT NULL COMMENT '计算方式key',
                                                  `locked` int NULL DEFAULT 0 COMMENT '是否被锁 1：已锁 0：未锁',
                                                  `num_length` int NULL DEFAULT NULL COMMENT '编号长度，不够前面补0',
                                                  `start_num` int NULL DEFAULT NULL COMMENT '初始值',
                                                  `step` int NULL DEFAULT NULL COMMENT '步长',
                                                  `tpl` varchar(100) NULL DEFAULT NULL COMMENT '模板',
                                                  `type` int NULL DEFAULT NULL COMMENT '计算方式1: 按日 2：按月 3:按年',
                                                  `update_time` varchar(20) NULL DEFAULT NULL,
                                                  `update_user` varchar(50) NULL DEFAULT NULL,
                                                  PRIMARY KEY (`id`) 
);


CREATE TABLE IF NOT EXISTS `sys_base`  (
                                           `id` varchar(36) NOT NULL,
                                           `app_id` varchar(36) NULL DEFAULT NULL COMMENT '关联应用',
                                           `code` varchar(50) NULL DEFAULT NULL COMMENT '编码',
                                           `is_test` tinyint NULL DEFAULT NULL,
                                           `name` varchar(50) NULL DEFAULT NULL COMMENT '名称',
                                           `note` text NULL COMMENT '备注',
                                           `when_created` varchar(30) NULL DEFAULT NULL COMMENT '创建时间',
                                           `when_modified` varchar(30) NULL DEFAULT NULL COMMENT '修改时间',
                                           `who_created` varchar(36) NULL DEFAULT NULL COMMENT '创建人员',
                                           `who_modified` varchar(36) NULL DEFAULT NULL COMMENT '修改人员',
                                           PRIMARY KEY (`id`) 
);


CREATE TABLE IF NOT EXISTS `sys_config`  (
                                             `id` varchar(36) NOT NULL,
                                             `app_id` varchar(36) NULL DEFAULT NULL COMMENT '关联应用',
                                             `code` varchar(255) NULL DEFAULT NULL COMMENT '参数键名',
                                             `is_sys` tinyint NULL DEFAULT NULL COMMENT '是否系统内置',
                                             `name` varchar(255) NULL DEFAULT NULL COMMENT '参数名称',
                                             `note` varchar(255) NULL DEFAULT NULL COMMENT '备注',
                                             `value` varchar(255) NULL DEFAULT NULL COMMENT '参数键值',
                                             `value_type` tinyint NULL DEFAULT 0,
                                             `when_created` varchar(30) NULL DEFAULT NULL COMMENT '创建时间',
                                             `when_modified` varchar(30) NULL DEFAULT NULL COMMENT '修改时间',
                                             `who_created` varchar(36) NULL DEFAULT NULL COMMENT '创建人员',
                                             `who_modified` varchar(36) NULL DEFAULT NULL COMMENT '修改人员',
                                             PRIMARY KEY (`id`) 
);


CREATE TABLE IF NOT EXISTS `sys_data_access`  (
                                                  `id` varchar(36) NOT NULL,
                                                  `app_id` varchar(36) NULL DEFAULT NULL COMMENT '关联应用',
                                                  `name` varchar(50) NULL DEFAULT NULL COMMENT '名称',
                                                  `note` text NULL COMMENT '备注',
                                                  `status` tinyint NULL DEFAULT NULL COMMENT '启用状态',
                                                  `when_created` varchar(30) NULL DEFAULT NULL COMMENT '创建时间',
                                                  `when_modified` varchar(30) NULL DEFAULT NULL COMMENT '修改时间',
                                                  `who_created` varchar(36) NULL DEFAULT NULL COMMENT '创建人员',
                                                  `who_modified` varchar(36) NULL DEFAULT NULL COMMENT '修改人员',
                                                  PRIMARY KEY (`id`) 
);


CREATE TABLE IF NOT EXISTS `sys_data_access_resource`  (
                                                           `id` varchar(36) NOT NULL,
                                                           `access_id` varchar(36) NULL DEFAULT NULL COMMENT '编码',
                                                           `app_id` varchar(36) NULL DEFAULT NULL COMMENT '关联应用',
                                                           `data_id` varchar(36) NULL DEFAULT NULL COMMENT '名称',
                                                           `table_name` varchar(50) NULL DEFAULT NULL COMMENT '备注',
                                                           `when_created` varchar(30) NULL DEFAULT NULL COMMENT '创建时间',
                                                           `who_created` varchar(36) NULL DEFAULT NULL COMMENT '创建人员',
                                                           PRIMARY KEY (`id`) 
);


CREATE TABLE IF NOT EXISTS `sys_data_access_user`  (
                                                       `id` varchar(36) NOT NULL,
                                                       `app_id` varchar(36) NULL DEFAULT NULL COMMENT '关联应用',
                                                       `sys_data_access_id` varchar(50) NULL DEFAULT NULL COMMENT '数据访问组id',
                                                       `sys_user_id` varchar(36) NULL DEFAULT NULL COMMENT '用户id',
                                                       `when_created` varchar(30) NULL DEFAULT NULL COMMENT '创建时间',
                                                       `who_created` varchar(36) NULL DEFAULT NULL COMMENT '创建人员',
                                                       PRIMARY KEY (`id`) 
);


CREATE TABLE IF NOT EXISTS `sys_data_resource`  (
                                                    `id` varchar(36) NOT NULL,
                                                    `app_id` varchar(36) NULL DEFAULT NULL COMMENT '关联应用',
                                                    `extra_sql` text NULL COMMENT '权限附加SQL',
                                                    `is_only_leaf` tinyint NULL DEFAULT NULL COMMENT '是否只选树叶',
                                                    `is_tree` tinyint NULL DEFAULT NULL COMMENT '是否树形结构',
                                                    `label_field` varchar(50) NULL DEFAULT NULL COMMENT '标签列',
                                                    `name` varchar(50) NULL DEFAULT NULL COMMENT '名称',
                                                    `query_sql` text NULL COMMENT '查询SQL',
                                                    `status` tinyint NULL DEFAULT NULL COMMENT '启用状态',
                                                    `table_name` varchar(50) NULL DEFAULT NULL COMMENT '表名',
                                                    `value_field` varchar(50) NULL DEFAULT NULL COMMENT '值列',
                                                    `when_created` varchar(30) NULL DEFAULT NULL COMMENT '创建时间',
                                                    `when_modified` varchar(30) NULL DEFAULT NULL COMMENT '修改时间',
                                                    `who_created` varchar(36) NULL DEFAULT NULL COMMENT '创建人员',
                                                    `who_modified` varchar(36) NULL DEFAULT NULL COMMENT '修改人员',
                                                    PRIMARY KEY (`id`) 
);


CREATE TABLE IF NOT EXISTS `sys_dict`  (
                                           `id` varchar(36) NOT NULL,
                                           `app_id` varchar(36) NULL DEFAULT NULL COMMENT '关联应用',
                                           `code` varchar(50) NULL DEFAULT NULL COMMENT '编码',
                                           `name` varchar(50) NULL DEFAULT NULL COMMENT '名称',
                                           `note` text NULL COMMENT '备注',
                                           `when_created` varchar(30) NULL DEFAULT NULL COMMENT '创建时间',
                                           `when_modified` varchar(30) NULL DEFAULT NULL COMMENT '修改时间',
                                           `who_created` varchar(36) NULL DEFAULT NULL COMMENT '创建人员',
                                           `who_modified` varchar(36) NULL DEFAULT NULL COMMENT '修改人员',
                                           PRIMARY KEY (`id`) 
);


CREATE TABLE IF NOT EXISTS `sys_dict_item`  (
                                                `id` varchar(36) NOT NULL,
                                                `app_id` varchar(36) NULL DEFAULT NULL COMMENT '关联应用',
                                                `code` varchar(50) NULL DEFAULT NULL COMMENT '编码',
                                                `group_name` varchar(50) NULL DEFAULT NULL COMMENT '组名',
                                                `name` varchar(50) NULL DEFAULT NULL COMMENT '名称',
                                                `note` text NULL COMMENT '备注',
                                                `order_num` int NULL DEFAULT NULL COMMENT '排序',
                                                `sys_dict_id` varchar(36) NULL DEFAULT NULL COMMENT '字典id',
                                                `value` varchar(20) NULL DEFAULT NULL COMMENT '值',
                                                `when_created` varchar(30) NULL DEFAULT NULL COMMENT '创建时间',
                                                `when_modified` varchar(30) NULL DEFAULT NULL COMMENT '修改时间',
                                                `who_created` varchar(36) NULL DEFAULT NULL COMMENT '创建人员',
                                                `who_modified` varchar(36) NULL DEFAULT NULL COMMENT '修改人员',
                                                PRIMARY KEY (`id`) 
);



CREATE TABLE IF NOT EXISTS `sys_excel`  (
                                            `id` varchar(36) NOT NULL,
                                            `app_id` varchar(36) NULL DEFAULT NULL COMMENT '关联应用',
                                            `data_from` int NULL DEFAULT NULL COMMENT '1. 普通 2. Excel文件 3. 数据源',
                                            `data_from_id` varchar(36) NULL DEFAULT NULL COMMENT '数据来源ID：如果data_from为2，则为fileId；如果为3，则为datasetId',
                                            `data_json` longtext NULL COMMENT '数据配置',
                                            `name` varchar(255) NULL DEFAULT NULL COMMENT '名称',
                                            `when_created` varchar(30) NULL DEFAULT NULL COMMENT '创建时间',
                                            `when_modified` varchar(30) NULL DEFAULT NULL COMMENT '修改时间',
                                            `who_created` varchar(36) NULL DEFAULT NULL COMMENT '创建人',
                                            `who_modified` varchar(36) NULL DEFAULT NULL COMMENT '修改人',
                                            PRIMARY KEY (`id`) 
);


CREATE TABLE IF NOT EXISTS `sys_file`  (
                                           `id` varchar(36) NOT NULL,
                                           `app_id` varchar(36) NULL DEFAULT NULL COMMENT '关联应用',
                                           `file_content` longtext NULL COMMENT '文件内容，只有存数据库时才有值',
                                           `file_ext` text NULL COMMENT '文件扩展名',
                                           `file_from` varchar(50) NULL DEFAULT NULL COMMENT '文件来源',
                                           `file_md5` varchar(40) NULL DEFAULT NULL COMMENT '文件MD5值',
                                           `file_name` varchar(100) NULL DEFAULT NULL COMMENT '文件名称',
                                           `file_original_name` varchar(100) NULL DEFAULT NULL COMMENT '原始文件名',
                                           `file_path` varchar(100) NULL DEFAULT NULL COMMENT '存储路径',
                                           `file_size` int NULL DEFAULT NULL COMMENT '文件大小(Byte)',
                                           `save_type` int NULL DEFAULT NULL COMMENT '存储方式 0：数据库 1：本地磁盘，2：FAAS储存',
                                           `when_created` varchar(30) NULL DEFAULT NULL COMMENT '创建时间',
                                           `when_modified` varchar(30) NULL DEFAULT NULL COMMENT '修改时间',
                                           `who_created` varchar(36) NULL DEFAULT NULL COMMENT '创建人员',
                                           `who_modified` varchar(36) NULL DEFAULT NULL COMMENT '修改人员',
                                           PRIMARY KEY (`id`) 
);


CREATE TABLE IF NOT EXISTS `sys_hint_select`  (
                                                  `id` varchar(36) NOT NULL,
                                                  `code` varchar(100) NULL DEFAULT NULL COMMENT '简称',
                                                  `db_id` varchar(100) NULL DEFAULT NULL COMMENT '数据源',
                                                  `flow_id` varchar(50) NULL DEFAULT NULL COMMENT '逻辑编排,优化级最高',
                                                  `remark` text NULL COMMENT '备注说明',
                                                  `select_fields` text NULL COMMENT '字段说明',
                                                  `select_sql` text NULL COMMENT '下拉SQL',
                                                  `type` varchar(50) NULL DEFAULT NULL COMMENT '类型:0=普通下拉,1=树下拉',
                                                  PRIMARY KEY (`id`) 
);


CREATE TABLE IF NOT EXISTS `sys_i18n`  (
                                           `id` varchar(36) NOT NULL,
                                           `app_id` varchar(36) NULL DEFAULT NULL COMMENT '归属应用ID',
                                           `i18n_key` varchar(255) NULL DEFAULT NULL COMMENT '键名',
                                           `message` text NULL COMMENT '国际化配置信息，JSON保存',
                                           `when_created` varchar(30) NULL DEFAULT NULL COMMENT '创建时间',
                                           `when_modified` varchar(30) NULL DEFAULT NULL COMMENT '修改时间',
                                           `who_created` varchar(36) NULL DEFAULT NULL COMMENT '创建人员',
                                           `who_modified` varchar(36) NULL DEFAULT NULL COMMENT '修改人员',
                                           PRIMARY KEY (`id`) 
);


CREATE TABLE IF NOT EXISTS `sys_logic_flow`  (
                                                 `id` varchar(36) NOT NULL,
                                                 `app_id` varchar(36) NULL DEFAULT NULL COMMENT '事务开关（开：1，关：0）；早期用于保存’关联应用‘，是application_id的冗余。',
                                                 `application_id` varchar(36) NULL DEFAULT NULL COMMENT '应用ID',
                                                 `default_source_name` varchar(100) NULL DEFAULT NULL COMMENT '默认数据源',
                                                 `flow_id` varchar(36) NULL DEFAULT NULL COMMENT '流程ID',
                                                 `in_argv` text NULL COMMENT '输入参数',
                                                 `name` varchar(255) NULL DEFAULT NULL COMMENT '名称',
                                                 `note` varchar(255) NULL DEFAULT NULL COMMENT '备注',
                                                 `out_argv` text NULL COMMENT '输出参数',
                                                 `sub_flow_ids` text NULL COMMENT '子流程ID列表',
                                                 `tags` varchar(255) NULL DEFAULT NULL COMMENT '标签',
                                                 `when_created` varchar(30) NULL DEFAULT NULL COMMENT '创建时间',
                                                 `when_modified` varchar(30) NULL DEFAULT NULL COMMENT '修改时间',
                                                 `who_created` varchar(36) NULL DEFAULT NULL COMMENT '创建人员',
                                                 `who_modified` varchar(36) NULL DEFAULT NULL COMMENT '修改人员',
                                                 PRIMARY KEY (`id`) 
);


CREATE TABLE IF NOT EXISTS `sys_logic_history`  (
                                                    `id` varchar(36) NOT NULL,
                                                    `flow_id` varchar(36) NULL DEFAULT NULL COMMENT '流程ID',
                                                    `flow_json` longtext NULL COMMENT '流程JSON',
                                                    `version_tag` varchar(50) NULL DEFAULT NULL COMMENT '版本标签',
                                                    `version_tag_time` varchar(30) NULL DEFAULT NULL COMMENT '版本时间',
                                                    `when_created` varchar(30) NULL DEFAULT NULL COMMENT '创建时间',
                                                    `who_created` varchar(36) NULL DEFAULT NULL COMMENT '创建人',
                                                    PRIMARY KEY (`id`) 
);


CREATE TABLE IF NOT EXISTS `sys_logic_template`  (
                                                     `id` varchar(36) NOT NULL,
                                                     `app_id` varchar(36) NULL DEFAULT NULL COMMENT '关联应用',
                                                     `description` text NULL COMMENT '简介',
                                                     `links` longtext NULL COMMENT '连接列表',
                                                     `module_id` varchar(36) NULL DEFAULT NULL COMMENT '关联模块',
                                                     `name` varchar(255) NULL DEFAULT NULL COMMENT '名称',
                                                     `nodes` longtext NULL COMMENT '节点列表',
                                                     `when_created` varchar(50) NULL DEFAULT NULL COMMENT '创建时间',
                                                     `when_modified` varchar(50) NULL DEFAULT NULL COMMENT '修改时间',
                                                     `who_created` varchar(36) NULL DEFAULT NULL COMMENT '创建人',
                                                     `who_modified` varchar(36) NULL DEFAULT NULL COMMENT '修改人',
                                                     PRIMARY KEY (`id`) 
);


CREATE TABLE IF NOT EXISTS `sys_login_log`  (
                                                `id` varchar(36) NOT NULL,
                                                `ip` varchar(20) NULL DEFAULT NULL COMMENT '来访IP',
                                                `operate_time` varchar(20) NULL DEFAULT NULL COMMENT '访问时间',
                                                `operator` varchar(36) NULL DEFAULT NULL COMMENT '访问人员',
                                                `response_code` int NULL DEFAULT NULL COMMENT '响应码',
                                                `response_message` varchar(100) NULL DEFAULT NULL COMMENT '响应消息',
                                                `times` int NULL DEFAULT NULL COMMENT '耗时(ms)',
                                                `when_created` varchar(30) NULL DEFAULT NULL COMMENT '创建时间',
                                                PRIMARY KEY (`id`) 
);


CREATE TABLE IF NOT EXISTS `sys_menu`  (
                                           `id` varchar(36) NOT NULL,
                                           `api_codes` varchar(255) NULL DEFAULT NULL COMMENT '接口编码，多个用逗号分隔',
                                           `app_id` varchar(36) NULL DEFAULT NULL COMMENT '所属应用ID',
                                           `code` varchar(50) NULL DEFAULT NULL COMMENT '编码',
                                           `component_path` varchar(255) NULL DEFAULT NULL COMMENT '组件路径',
                                           `data_type` tinyint NULL DEFAULT NULL COMMENT '数据类型：0系统，1业务应用, 2开发平台',
                                           `full_path` varchar(255) NULL DEFAULT NULL COMMENT '完整路径（根据层级结构、router_path拼接的完整路径）',
                                           `icon` varchar(50) NULL DEFAULT NULL COMMENT '图标',
                                           `is_dev` tinyint(1) NULL DEFAULT 0 COMMENT '菜单是否为开发者模式菜单',
                                           `is_hidden` tinyint NULL DEFAULT 0 COMMENT '是否隐藏',
                                           `keep_alive` tinyint NULL DEFAULT NULL COMMENT '是否刷新 0: 否 1：是',
                                           `main_mode` tinyint NULL DEFAULT NULL COMMENT '内容区显示样式：0 自动撑开，1 居中',
                                           `menu_type` char(1) NULL DEFAULT '0' COMMENT '菜单类型（M目录 C菜单 F按钮）',
                                           `name` varchar(50) NULL DEFAULT NULL COMMENT '名称',
                                           `open_mode` tinyint NULL DEFAULT NULL COMMENT '打开方式 0:页签 1:新窗口',
                                           `order_num` int NULL DEFAULT 0 COMMENT '排序',
                                           `page_id` varchar(36) NULL DEFAULT NULL COMMENT '关联页面',
                                           `page_type` tinyint NULL DEFAULT NULL COMMENT '页面渲染方式：0 Vue， 1 JSON',
                                           `parent_id` varchar(36) NULL DEFAULT NULL COMMENT '上级菜单ID',
                                           `path` text NULL COMMENT '菜单层级关系，自动生成',
                                           `router_path` varchar(255) NULL DEFAULT NULL COMMENT '路由路径',
                                           `sidebar_nav_mode` tinyint NULL DEFAULT NULL COMMENT '侧边菜单显示模式：0 不显示，1 从一级菜单开始显示，2 从二级菜单开始显示, 3 显示二级以下菜单',
                                           `status` tinyint NULL DEFAULT 1 COMMENT '可用状态',
                                           `theme` varchar(50) NULL DEFAULT NULL COMMENT 'layout主题',
                                           `top_nav_mode` tinyint NULL DEFAULT NULL COMMENT '顶部菜单显示模式：0 完全不显示, top_nav_mode1 不显示nav（有header，没有菜单），2 从一级菜单开始显示，3 从二级菜单开始显示',
                                           `when_created` varchar(30) NULL DEFAULT NULL COMMENT '创建时间',
                                           `when_modified` varchar(30) NULL DEFAULT NULL COMMENT '修改时间',
                                           `who_created` varchar(36) NULL DEFAULT NULL COMMENT '创建人员',
                                           `who_modified` varchar(36) NULL DEFAULT NULL COMMENT '修改人员',
                                           PRIMARY KEY (`id`) 
);


CREATE TABLE IF NOT EXISTS `sys_mq_channel`  (
                                                 `id` varchar(36) NOT NULL,
                                                 `batch_consumer` int NULL DEFAULT NULL COMMENT '批量消费数',
                                                 `channel_name` varchar(50) NULL DEFAULT NULL COMMENT '通道名称',
                                                 `consumer_group` varchar(50) NULL DEFAULT NULL COMMENT '消费组名',
                                                 `consumer_thread` int NULL DEFAULT NULL COMMENT '消费线程数',
                                                 `enable` int NULL DEFAULT NULL COMMENT '是否启用 0-否 1-是',
                                                 `message_name` varchar(50) NULL DEFAULT NULL COMMENT '消息处理逻辑编排',
                                                 `topic` varchar(50) NULL DEFAULT NULL COMMENT '主题',
                                                 `zk_address` varchar(50) NULL DEFAULT NULL COMMENT 'zk地址',
                                                 PRIMARY KEY (`id`) 
);


CREATE TABLE IF NOT EXISTS `sys_notice`  (
                                             `id` varchar(36) NOT NULL,
                                             `app_id` varchar(36) NULL DEFAULT NULL COMMENT '关联应用',
                                             `content` text NULL COMMENT '通知内容',
                                             `deleted` tinyint(1) NULL DEFAULT NULL COMMENT '逻辑删除，0：未删除，1：已删除',
                                             `status` tinyint NULL DEFAULT 1 COMMENT '启用状态：0：待启用，1：已启用',
                                             `title` varchar(255) NULL DEFAULT NULL COMMENT '通知标题',
                                             `type` tinyint NULL DEFAULT NULL COMMENT '通知类型，1：系统维护通知，2：公告',
                                             `when_created` varchar(30) NULL DEFAULT NULL COMMENT '创建时间',
                                             `when_modified` varchar(30) NULL DEFAULT NULL COMMENT '更新时间',
                                             `who_created` varchar(36) NULL DEFAULT NULL COMMENT '创建人',
                                             `who_modified` varchar(36) NULL DEFAULT NULL COMMENT '更新人',
                                             PRIMARY KEY (`id`) 
);


CREATE TABLE IF NOT EXISTS `sys_notice_record`  (
                                                    `id` varchar(36) NOT NULL,
                                                    `app_id` varchar(36) NULL DEFAULT NULL COMMENT '关联应用',
                                                    `content` text NULL COMMENT '通知内容',
                                                    `from_who` varchar(36) NULL DEFAULT NULL COMMENT '发送人',
                                                    `from_who_name` varchar(255) NULL DEFAULT NULL COMMENT '发送人名称',
                                                    `is_read` tinyint NULL DEFAULT 0 COMMENT '是否已读，0：未读，1：已读',
                                                    `notice_id` varchar(36) NULL DEFAULT NULL COMMENT '关联通知',
                                                    `notice_time` varchar(20) NULL DEFAULT NULL COMMENT '接收通知时间',
                                                    `read_time` varchar(20) NULL DEFAULT NULL COMMENT '阅读通知时间',
                                                    `title` varchar(255) NULL DEFAULT NULL COMMENT '通知标题',
                                                    `to_who` varchar(36) NULL DEFAULT NULL COMMENT '接收人',
                                                    `to_who_name` varchar(255) NULL DEFAULT NULL COMMENT '接收人名称',
                                                    PRIMARY KEY (`id`) 
);


CREATE TABLE IF NOT EXISTS `sys_online_user`  (
                                                  `id` varchar(36) NOT NULL,
                                                  `app_id` varchar(36) NULL DEFAULT NULL COMMENT '关联应用',
                                                  `expire_time` varchar(20) NULL DEFAULT NULL COMMENT '失效时间',
                                                  `login_ip` varchar(20) NULL DEFAULT NULL COMMENT '登录ip',
                                                  `login_time` varchar(20) NULL DEFAULT NULL COMMENT '登录时间',
                                                  `login_token` varchar(1024) NULL DEFAULT NULL COMMENT '令牌',
                                                  `user_id` varchar(36) NULL DEFAULT NULL COMMENT '用户id',
                                                  `when_created` varchar(30) NULL DEFAULT NULL COMMENT '创建时间',
                                                  PRIMARY KEY (`id`) 
);


CREATE TABLE IF NOT EXISTS `sys_operate_log`  (
                                                  `id` varchar(36) NOT NULL,
                                                  `action` varchar(255) NULL DEFAULT NULL COMMENT '动作',
                                                  `app_id` varchar(36) NULL DEFAULT NULL COMMENT '关联应用',
                                                  `ip` varchar(20) NULL DEFAULT NULL COMMENT '来访IP',
                                                  `module` varchar(100) NULL DEFAULT NULL COMMENT '模块',
                                                  `operate_time` varchar(20) NULL DEFAULT NULL COMMENT '访问时间',
                                                  `operator` varchar(36) NULL DEFAULT NULL COMMENT '访问人员',
                                                  `request_body` longtext NULL COMMENT '请求内容体',
                                                  `response_code` int NULL DEFAULT NULL COMMENT '响应码',
                                                  `response_message` varchar(100) NULL DEFAULT NULL COMMENT '响应消息',
                                                  `times` int NULL DEFAULT NULL COMMENT '耗时(ms)',
                                                  `url` varchar(255) NULL DEFAULT NULL COMMENT '路径',
                                                  `when_created` varchar(30) NULL DEFAULT NULL COMMENT '创建时间',
                                                  `method` varchar(255) NULL DEFAULT NULL COMMENT '方法名称',
                                                  `request_method` varchar(20) NULL DEFAULT NULL COMMENT '请求方式',
                                                  `response_body` text NULL COMMENT '响应内容体',
                                                  PRIMARY KEY (`id`) 
);


CREATE TABLE IF NOT EXISTS `sys_role`  (
                                           `id` varchar(36) NOT NULL,
                                           `app_id` varchar(36) NULL DEFAULT NULL COMMENT '关联应用',
                                           `code` varchar(50) NULL DEFAULT NULL COMMENT '编码',
                                           `name` varchar(50) NULL DEFAULT NULL COMMENT '名称',
                                           `note` text NULL COMMENT '备注',
                                           `status` tinyint NULL DEFAULT NULL COMMENT '是否有效 0:否 1：是',
                                           `when_created` varchar(30) NULL DEFAULT NULL COMMENT '创建时间',
                                           `when_modified` varchar(30) NULL DEFAULT NULL COMMENT '修改时间',
                                           `who_created` varchar(36) NULL DEFAULT NULL COMMENT '创建人员',
                                           `who_modified` varchar(36) NULL DEFAULT NULL COMMENT '修改人员',
                                           PRIMARY KEY (`id`) 
);


CREATE TABLE IF NOT EXISTS `sys_role_menu`  (
                                                `id` varchar(36) NOT NULL,
                                                `app_id` varchar(36) NULL DEFAULT NULL COMMENT '关联应用',
                                                `sys_menu_id` varchar(36) NULL DEFAULT NULL COMMENT '名称',
                                                `sys_role_id` varchar(50) NULL DEFAULT NULL COMMENT '编码',
                                                `when_created` varchar(30) NULL DEFAULT NULL COMMENT '创建时间',
                                                `who_created` varchar(36) NULL DEFAULT NULL COMMENT '创建人员',
                                                PRIMARY KEY (`id`) 
);


CREATE TABLE IF NOT EXISTS `sys_task`  (
                                           `id` varchar(36) NOT NULL,
                                           `app_id` varchar(36) NULL DEFAULT NULL COMMENT '关联应用',
                                           `application_id` varchar(36) NULL DEFAULT NULL COMMENT '应用ID',
                                           `class_name` varchar(255) NULL DEFAULT NULL COMMENT '类名',
                                           `cron` varchar(50) NULL DEFAULT NULL COMMENT '定时表达式',
                                           `distributed` tinyint NULL DEFAULT NULL COMMENT '是否分布式',
                                           `enable` tinyint NULL DEFAULT 1 COMMENT '启用状态',
                                           `last_execute_msg` text NULL COMMENT '最后执行错误信息',
                                           `last_execute_status` tinyint NULL DEFAULT NULL COMMENT '最后执行状态 1：成功 0：失败',
                                           `last_execute_take` int NULL DEFAULT NULL COMMENT '上次执行消耗',
                                           `last_execute_time` varchar(20) NULL DEFAULT NULL COMMENT '上次执行时间',
                                           `lock_for_least` int NULL DEFAULT 1 COMMENT '最少锁定时长(秒)',
                                           `lock_for_most` int NULL DEFAULT 30 COMMENT '最多锁定时长(秒)',
                                           `lock_for_time` varchar(20) NULL DEFAULT NULL COMMENT '锁定时间',
                                           `lock_status` tinyint NULL DEFAULT 0 COMMENT '锁定状态',
                                           `name` varchar(100) NULL DEFAULT NULL COMMENT '任务名称',
                                           `note` text NULL COMMENT '备注',
                                           `task_resource_id` varchar(36) NULL DEFAULT NULL COMMENT '任务资源ID',
                                           `task_type` tinyint NULL DEFAULT 1 COMMENT '任务类型',
                                           `when_created` varchar(30) NULL DEFAULT NULL COMMENT '创建时间',
                                           `when_modified` varchar(30) NULL DEFAULT NULL COMMENT '修改时间',
                                           `who_created` varchar(36) NULL DEFAULT NULL COMMENT '创建人',
                                           `who_modified` varchar(36) NULL DEFAULT NULL COMMENT '修改人',
                                           PRIMARY KEY (`id`) 
);


CREATE TABLE IF NOT EXISTS `sys_unit`  (
                                           `id` varchar(36) NOT NULL,
                                           `app_id` varchar(36) NULL DEFAULT NULL COMMENT '关联应用',
                                           `email` varchar(50) NULL DEFAULT NULL COMMENT '电子邮箱',
                                           `leader` varchar(255) NULL DEFAULT NULL COMMENT '负责人',
                                           `mobile` varchar(20) NULL DEFAULT NULL COMMENT '联系电话',
                                           `name` varchar(50) NULL DEFAULT NULL COMMENT '名称',
                                           `note` text NULL COMMENT '备注',
                                           `order_num` int NULL DEFAULT 0 COMMENT '排序 ',
                                           `parent_id` varchar(36) NULL DEFAULT NULL COMMENT '上级单位ID',
                                           `path` text NULL COMMENT '层次关系，自动生成',
                                           `status` tinyint NULL DEFAULT 1 COMMENT '部门状态， 0：停用 1：正常',
                                           `when_created` varchar(30) NULL DEFAULT NULL COMMENT '创建时间',
                                           `when_modified` varchar(30) NULL DEFAULT NULL COMMENT '修改时间',
                                           `who_created` varchar(36) NULL DEFAULT NULL COMMENT '创建人员',
                                           `who_modified` varchar(36) NULL DEFAULT NULL COMMENT '修改人员',
                                           PRIMARY KEY (`id`) 
);


CREATE TABLE IF NOT EXISTS `sys_user`  (
                                           `id` varchar(36) NOT NULL,
                                           `app_id` varchar(36) NULL DEFAULT NULL COMMENT '关联应用',
                                           `avatar` varchar(255) NULL DEFAULT NULL,
                                           `deleted` tinyint NULL DEFAULT 0 COMMENT '逻辑删除 0:正常 1：删除',
                                           `email` varchar(50) NULL DEFAULT NULL COMMENT '电子邮箱',
                                           `mobile` varchar(20) NULL DEFAULT NULL COMMENT '手机号码',
                                           `note` text NULL COMMENT '备注',
                                           `password` varchar(256) NULL DEFAULT NULL COMMENT '密码',
                                           `post` varchar(50) NULL DEFAULT NULL COMMENT '岗位',
                                           `real_name` varchar(50) NULL DEFAULT NULL COMMENT '用户姓名',
                                           `sex` tinyint NULL DEFAULT NULL COMMENT '性别 0:男 1:女',
                                           `status` tinyint NULL DEFAULT 1 COMMENT '状态 1：正常 0：禁用',
                                           `sys_unit_id` varchar(36) NULL DEFAULT NULL COMMENT '归属部门',
                                           `username` varchar(50) NULL DEFAULT NULL COMMENT '用户名',
                                           `when_created` varchar(30) NULL DEFAULT NULL COMMENT '创建时间',
                                           `when_modified` varchar(30) NULL DEFAULT NULL COMMENT '修改时间',
                                           `who_created` varchar(36) NULL DEFAULT NULL COMMENT '创建人员',
                                           `who_modified` varchar(36) NULL DEFAULT NULL COMMENT '修改人员',
                                           PRIMARY KEY (`id`) 
);


CREATE TABLE IF NOT EXISTS `sys_user_role`  (
                                                `id` varchar(36) NOT NULL,
                                                `app_id` varchar(36) NULL DEFAULT NULL COMMENT '关联应用',
                                                `sys_role_id` varchar(36) NULL DEFAULT NULL COMMENT '编码',
                                                `sys_user_id` varchar(36) NULL DEFAULT NULL COMMENT '名称',
                                                `when_created` varchar(30) NULL DEFAULT NULL COMMENT '创建时间',
                                                `who_created` varchar(36) NULL DEFAULT NULL COMMENT '创建人员',
                                                PRIMARY KEY (`id`) 
);


CREATE TABLE IF NOT EXISTS `sys_user_unit`  (
                                                `id` varchar(36) NOT NULL COMMENT '主键',
                                                `sys_user_id` varchar(36) NOT NULL COMMENT '用户ID',
                                                `sys_unit_id` varchar(36) NOT NULL COMMENT '部门ID',
                                                `who_created` varchar(36) NOT NULL COMMENT '创建人员',
                                                `when_created` varchar(20) NOT NULL COMMENT '创建时间',
                                                `app_id` varchar(36) NULL DEFAULT NULL COMMENT '关联应用',
                                                PRIMARY KEY (`id`) 
);


CREATE TABLE IF NOT EXISTS `sys_view_model`  (
                                                 `id` varchar(36) NOT NULL,
                                                 `deleted` int NULL DEFAULT 0 COMMENT '逻辑删除',
                                                 `name` varchar(50) NULL DEFAULT NULL COMMENT '名称',
                                                 `note` text NULL COMMENT '描述',
                                                 `tag` varchar(100) NULL DEFAULT NULL COMMENT '标签，多个用逗号分隔',
                                                 `when_created` varchar(30) NULL DEFAULT NULL COMMENT '创建时间',
                                                 `when_modified` varchar(30) NULL DEFAULT NULL COMMENT '修改时间',
                                                 `who_created` varchar(36) NULL DEFAULT NULL COMMENT '创建人员',
                                                 `who_modified` varchar(36) NULL DEFAULT NULL COMMENT '修改人员',
                                                 PRIMARY KEY (`id`) 
);


CREATE TABLE IF NOT EXISTS `sys_view_model_field`  (
                                                       `id` varchar(36) NOT NULL,
                                                       `default_text` varchar(50) NULL DEFAULT NULL COMMENT '默认显示',
                                                       `field` varchar(50) NULL DEFAULT NULL COMMENT '键名',
                                                       `format_pattern` varchar(50) NULL DEFAULT NULL COMMENT '格式规则',
                                                       `format_type` varchar(20) NULL DEFAULT NULL COMMENT '格式类型',
                                                       `hidden` int NULL DEFAULT 0 COMMENT '是否隐藏',
                                                       `label` varchar(50) NULL DEFAULT NULL COMMENT '标签',
                                                       `order_num` int NULL DEFAULT 0 COMMENT '排序',
                                                       `type` varchar(20) NULL DEFAULT NULL COMMENT '数据类型',
                                                       `view_model_id` varchar(36) NULL DEFAULT NULL COMMENT '模型ID',
                                                       `when_created` varchar(30) NULL DEFAULT NULL COMMENT '创建时间',
                                                       `when_modified` varchar(30) NULL DEFAULT NULL COMMENT '修改时间',
                                                       `who_created` varchar(36) NULL DEFAULT NULL COMMENT '创建人员',
                                                       `who_modified` varchar(36) NULL DEFAULT NULL COMMENT '修改人员',
                                                       PRIMARY KEY (`id`) 
);


CREATE TABLE IF NOT EXISTS `sys_view_model_flow`  (
                                                      `id` varchar(36) NOT NULL,
                                                      `flow_id` varchar(36) NULL DEFAULT NULL COMMENT '流水ID',
                                                      `view_model_id` varchar(36) NULL DEFAULT NULL COMMENT '模型ID',
                                                      `when_created` varchar(30) NULL DEFAULT NULL COMMENT '创建时间',
                                                      `when_modified` varchar(30) NULL DEFAULT NULL COMMENT '修改时间',
                                                      `who_created` varchar(36) NULL DEFAULT NULL COMMENT '创建人员',
                                                      `who_modified` varchar(36) NULL DEFAULT NULL COMMENT '修改人员',
                                                      PRIMARY KEY (`id`) 
);


CREATE TABLE IF NOT EXISTS `wf_demo_leave`  (
                                                `id` varchar(36) NOT NULL,
                                                `day` int NULL DEFAULT NULL COMMENT '请假天数',
                                                `proc_inst_id` varchar(36) NULL DEFAULT NULL COMMENT '实例id',
                                                `reason` varchar(255) NULL DEFAULT NULL COMMENT '请假原因',
                                                PRIMARY KEY (`id`) 
);


CREATE TABLE IF NOT EXISTS `wf_ext_category`  (
                                                  `id` varchar(36) NOT NULL,
                                                  `category_name` varchar(50) NULL DEFAULT NULL COMMENT '模块名称',
                                                  `order_num` int NULL DEFAULT NULL COMMENT '排序',
                                                  `when_created` varchar(30) NULL DEFAULT NULL COMMENT '创建时间',
                                                  `when_modified` varchar(30) NULL DEFAULT NULL COMMENT '修改时间',
                                                  `who_created` varchar(36) NULL DEFAULT NULL COMMENT '创建人',
                                                  `who_modified` varchar(36) NULL DEFAULT NULL COMMENT '修改人',
                                                  PRIMARY KEY (`id`) 
);


CREATE TABLE IF NOT EXISTS `wf_ext_comment`  (
                                                 `id` varchar(36) NOT NULL,
                                                 `message` varchar(255) NULL DEFAULT NULL COMMENT '意见',
                                                 `proc_inst_id` varchar(36) NULL DEFAULT NULL COMMENT '流程实例ID',
                                                 `task_id` varchar(36) NULL DEFAULT NULL COMMENT '任务ID',
                                                 `task_name` varchar(50) NULL DEFAULT NULL COMMENT '任务名称',
                                                 `type` varchar(20) NULL DEFAULT 'comment' COMMENT '类型',
                                                 `user_id` varchar(36) NULL DEFAULT NULL COMMENT '用户ID',
                                                 `when_created` varchar(30) NULL DEFAULT NULL COMMENT '创建时间',
                                                 PRIMARY KEY (`id`) 
);


CREATE TABLE IF NOT EXISTS `wf_ext_node_attribute`  (
                                                        `id` varchar(36) NOT NULL,
                                                        `actions` varchar(100) NULL DEFAULT NULL COMMENT '处理动作权限',
                                                        `exec_mode` varchar(50) NULL DEFAULT NULL COMMENT '执行方式',
                                                        `flow_id` varchar(100) NULL DEFAULT NULL COMMENT '流程ID',
                                                        `form_attribute` text NULL COMMENT '表单属性',
                                                        `msg_send_rule` varchar(100) NULL DEFAULT NULL COMMENT '消息发送策略',
                                                        `next_user` varchar(500) NULL DEFAULT NULL COMMENT '下一步处理人',
                                                        `node_id` varchar(100) NULL DEFAULT NULL COMMENT '节点ID',
                                                        `node_type` varchar(50) NULL DEFAULT NULL COMMENT '节点类型',
                                                        `pass_ok` varchar(50) NULL DEFAULT NULL COMMENT '审核通过要求',
                                                        `person` varchar(50) NULL DEFAULT NULL COMMENT '审核通过要求-任几人',
                                                        `time_out` varchar(50) NULL DEFAULT NULL COMMENT '超时时间(分)',
                                                        `when_created` varchar(50) NULL DEFAULT NULL COMMENT '创建时间',
                                                        `when_modified` varchar(50) NULL DEFAULT NULL COMMENT '更新时间',
                                                        `who_created` varchar(100) NULL DEFAULT NULL COMMENT '创建人',
                                                        `who_modified` varchar(100) NULL DEFAULT NULL COMMENT '更新人',
                                                        PRIMARY KEY (`id`) 
);


CREATE TABLE IF NOT EXISTS `wf_ext_procdef`  (
                                                 `id` varchar(36) NOT NULL,
                                                 `category_id` varchar(36) NULL DEFAULT NULL COMMENT '分类ID',
                                                 `content` longtext NULL COMMENT '内容',
                                                 `deploy_md5` varchar(255) NULL DEFAULT NULL COMMENT '已发布版本MD5',
                                                 `deploy_status` tinyint NULL DEFAULT NULL COMMENT '部署状态 0:待发布 1:已发布',
                                                 `deploy_time` varchar(20) NULL DEFAULT NULL COMMENT '最新发布时间',
                                                 `form_key` varchar(50) NULL DEFAULT NULL COMMENT '表单key，一般是表名',
                                                 `form_page_id` varchar(36) NULL DEFAULT NULL COMMENT '表单页面ID',
                                                 `icon` varchar(50) NULL DEFAULT NULL COMMENT '图标',
                                                 `inst_desc` varchar(255) NULL DEFAULT NULL COMMENT '实例描述',
                                                 `order_num` int NULL DEFAULT NULL COMMENT '排序',
                                                 `proc_definition_id` varchar(36) NULL DEFAULT NULL COMMENT '流程定义ID',
                                                 `proc_definition_key` varchar(100) NULL DEFAULT NULL COMMENT '流程定义KEY',
                                                 `proc_name` varchar(100) NULL DEFAULT NULL COMMENT '流程定义名称',
                                                 `proc_version` int NULL DEFAULT NULL COMMENT '版本号',
                                                 `when_created` varchar(30) NULL DEFAULT NULL COMMENT '创建时间',
                                                 `when_modified` varchar(30) NULL DEFAULT NULL COMMENT '修改时间',
                                                 `who_created` varchar(36) NULL DEFAULT NULL COMMENT '创建人',
                                                 `who_modified` varchar(36) NULL DEFAULT NULL COMMENT '修改人',
                                                 `work_num` varchar(100) NULL DEFAULT NULL COMMENT '工单编号',
                                                 PRIMARY KEY (`id`) 
);


CREATE TABLE IF NOT EXISTS `wf_ext_procinst`  (
                                                  `id` varchar(36) NOT NULL,
                                                  `bill_code` varchar(50) NULL DEFAULT NULL COMMENT '工单号',
                                                  `bill_title` varchar(100) NULL DEFAULT NULL COMMENT '工单标题',
                                                  `form_data` text NULL COMMENT '表单数据',
                                                  `main_inst_id` varchar(50) NULL DEFAULT NULL COMMENT '主流程实例ID',
                                                  `main_task_id` varchar(50) NULL DEFAULT NULL COMMENT '主流程任务ID',
                                                  `proc_inst_id` varchar(36) NULL DEFAULT NULL COMMENT '流程实例ID',
                                                  `starter` varchar(36) NULL DEFAULT NULL COMMENT '发起人ID',
                                                  `when_created` varchar(30) NULL DEFAULT NULL COMMENT '创建时间',
                                                  PRIMARY KEY (`id`) 
);




-- 以下是来自 version_21_1.sql 的内容 --

INSERT INTO `sys_user` (`id`, `username`, `password`, `real_name`, `mobile`, `email`, `sex`, `sys_unit_id`, `post`, `status`, `note`, `who_created`, `when_created`, `who_modified`, `when_modified`, `deleted`, `avatar`, `app_id`) VALUES ('056fb0eeb9a44cb0953534b4c0ca01fa', 'admin', 'MTIzNDU2', '超级管理员', NULL, NULL, 1, NULL, NULL, 1, NULL, '', '2021-12-29 16:36:46', '056fb0eeb9a44cb0953534b4c0ca01fa', '2022-04-01 08:58:32', 0, 'https://wpimg.wallstcn.com/f778738c-e4f8-4870-b634-56703b4acafe.gif', NULL);
INSERT INTO `sys_role` (`id`, `name`, `code`, `note`, `status`, `who_created`, `when_created`, `who_modified`, `when_modified`, `app_id`) VALUES ('10d26189026a4dba86a8e63a4c717ed6', '超级管理员', 'admin', '应用超级管理员', 1, '', '2021-12-28 15:22:56', '056fb0eeb9a44cb0953534b4c0ca01fa', '2022-03-29 11:43:41', NULL);
INSERT INTO `sys_user_role` (`id`, `sys_user_id`, `sys_role_id`, `who_created`, `when_created`, `app_id`) VALUES ('b5f79a7cc794423e843a2b1fd9a27007', '056fb0eeb9a44cb0953534b4c0ca01fa', '10d26189026a4dba86a8e63a4c717ed6', '', '2022-03-10 06:31:39', NULL);
INSERT INTO `sys_menu` (`id`, `name`, `parent_id`, `icon`, `code`, `router_path`, `component_path`, `is_hidden`, `menu_type`, `api_codes`, `open_mode`, `keep_alive`, `path`, `order_num`, `status`, `who_created`, `when_created`, `who_modified`, `when_modified`, `app_id`, `data_type`, `theme`, `page_type`, `sidebar_nav_mode`, `top_nav_mode`, `main_mode`, `page_id`, `full_path`, `is_dev`) VALUES ('c2348bbf343a47b5852f12ee32869b13', '基础信息', NULL, 'documentation', 'system-info', 'sys/info', NULL, 0, 'M', '', 0, 1, '/c2348bbf343a47b5852f12ee32869b13/', 4, 1, '056fb0eeb9a44cb0953534b4c0ca01fa', '2021-12-30 11:11:11', '', '2022-01-10 19:02:36', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, '/sys/info', 0);
INSERT INTO `sys_menu` (`id`, `name`, `parent_id`, `icon`, `code`, `router_path`, `component_path`, `is_hidden`, `menu_type`, `api_codes`, `open_mode`, `keep_alive`, `path`, `order_num`, `status`, `who_created`, `when_created`, `who_modified`, `when_modified`, `app_id`, `data_type`, `theme`, `page_type`, `sidebar_nav_mode`, `top_nav_mode`, `main_mode`, `page_id`, `full_path`, `is_dev`) VALUES ('1a246429b14e4db2be0e1847a3939e98', '菜单管理', '843af15ab7694d54af793e4a5e6fb76e', 'nested', 'menu', 'menu', '/sys/menu/index', 0, 'C', NULL, 0, 1, '/843af15ab7694d54af793e4a5e6fb76e/1a246429b14e4db2be0e1847a3939e98/', 5, 1, '056fb0eeb9a44cb0953534b4c0ca01fa', '2021-12-30 11:11:11', '', '2022-02-23 00:29:21', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, '/sys/conf/menu', 0);
INSERT INTO `sys_menu` (`id`, `name`, `parent_id`, `icon`, `code`, `router_path`, `component_path`, `is_hidden`, `menu_type`, `api_codes`, `open_mode`, `keep_alive`, `path`, `order_num`, `status`, `who_created`, `when_created`, `who_modified`, `when_modified`, `app_id`, `data_type`, `theme`, `page_type`, `sidebar_nav_mode`, `top_nav_mode`, `main_mode`, `page_id`, `full_path`, `is_dev`) VALUES ('843af15ab7694d54af793e4a5e6fb76e', '系统配置', NULL, 'system', 'system-config', 'sys/conf', NULL, 0, 'M', NULL, 0, 1, '/843af15ab7694d54af793e4a5e6fb76e/', 5, 1, '056fb0eeb9a44cb0953534b4c0ca01fa', '2021-12-30 11:11:11', '', '2022-01-10 19:02:56', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, '/sys/conf', 0);
INSERT INTO `sys_menu` (`id`, `name`, `parent_id`, `icon`, `code`, `router_path`, `component_path`, `is_hidden`, `menu_type`, `api_codes`, `open_mode`, `keep_alive`, `path`, `order_num`, `status`, `who_created`, `when_created`, `who_modified`, `when_modified`, `app_id`, `data_type`, `theme`, `page_type`, `sidebar_nav_mode`, `top_nav_mode`, `main_mode`, `page_id`, `full_path`, `is_dev`) VALUES ('58f5f98c57c74a91b6c2ca24c5df0ba9', '用户管理', 'c2348bbf343a47b5852f12ee32869b13', 'user', 'user', 'user/index', '/sys/user/index', 0, 'C', 'sys:user:list', 0, 1, '/c2348bbf343a47b5852f12ee32869b13/58f5f98c57c74a91b6c2ca24c5df0ba9/', 0, 1, '056fb0eeb9a44cb0953534b4c0ca01fa', '2021-12-30 11:11:11', '', '2022-02-23 00:28:46', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, '/sys/info/user/index', 0);
INSERT INTO `sys_menu` (`id`, `name`, `parent_id`, `icon`, `code`, `router_path`, `component_path`, `is_hidden`, `menu_type`, `api_codes`, `open_mode`, `keep_alive`, `path`, `order_num`, `status`, `who_created`, `when_created`, `who_modified`, `when_modified`, `app_id`, `data_type`, `theme`, `page_type`, `sidebar_nav_mode`, `top_nav_mode`, `main_mode`, `page_id`, `full_path`, `is_dev`) VALUES ('611b9195b7ce4b3fb37f41023a907bda', '角色管理', 'c2348bbf343a47b5852f12ee32869b13', 'peoples', 'role', 'role/index', '/sys/role/index', 0, 'C', NULL, 0, 1, '/c2348bbf343a47b5852f12ee32869b13/611b9195b7ce4b3fb37f41023a907bda/', 2, 1, '056fb0eeb9a44cb0953534b4c0ca01fa', '2021-12-30 11:11:11', '', '2022-02-23 00:29:33', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, '/sys/info/role/index', 0);
INSERT INTO `sys_menu` (`id`, `name`, `parent_id`, `icon`, `code`, `router_path`, `component_path`, `is_hidden`, `menu_type`, `api_codes`, `open_mode`, `keep_alive`, `path`, `order_num`, `status`, `who_created`, `when_created`, `who_modified`, `when_modified`, `app_id`, `data_type`, `theme`, `page_type`, `sidebar_nav_mode`, `top_nav_mode`, `main_mode`, `page_id`, `full_path`, `is_dev`) VALUES ('49d3319c02e542db9db32a6491193348', '字典管理', '843af15ab7694d54af793e4a5e6fb76e', 'dict', 'dictionary', 'dict/index', '/sys/dict/index', 0, 'C', NULL, 0, 1, '/843af15ab7694d54af793e4a5e6fb76e/49d3319c02e542db9db32a6491193348/', 0, 1, '056fb0eeb9a44cb0953534b4c0ca01fa', '2021-12-30 11:11:11', '', '2022-02-23 00:38:52', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, '/sys/conf/dict/index', 0);
INSERT INTO `sys_menu` (`id`, `name`, `parent_id`, `icon`, `code`, `router_path`, `component_path`, `is_hidden`, `menu_type`, `api_codes`, `open_mode`, `keep_alive`, `path`, `order_num`, `status`, `who_created`, `when_created`, `who_modified`, `when_modified`, `app_id`, `data_type`, `theme`, `page_type`, `sidebar_nav_mode`, `top_nav_mode`, `main_mode`, `page_id`, `full_path`, `is_dev`) VALUES ('49d3319c02e542db9db32a6491193349', '字典数据', '843af15ab7694d54af793e4a5e6fb76e', '', 'dictionary-item', 'dict-item/index', '/sys/dictItem/index', 1, 'C', NULL, 0, 1, '/843af15ab7694d54af793e4a5e6fb76e/49d3319c02e542db9db32a6491193349/', 1, 1, '056fb0eeb9a44cb0953534b4c0ca01fa', '2021-12-30 11:11:11', '', '2021-12-30 15:32:53', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, '/sys/conf/dict-item/index', 0);
INSERT INTO `sys_menu` (`id`, `name`, `parent_id`, `icon`, `code`, `router_path`, `component_path`, `is_hidden`, `menu_type`, `api_codes`, `open_mode`, `keep_alive`, `path`, `order_num`, `status`, `who_created`, `when_created`, `who_modified`, `when_modified`, `app_id`, `data_type`, `theme`, `page_type`, `sidebar_nav_mode`, `top_nav_mode`, `main_mode`, `page_id`, `full_path`, `is_dev`) VALUES ('a17e9c809f1049668633d8fe6103e740', '系统配置管理', '843af15ab7694d54af793e4a5e6fb76e', 'swagger', 'config', 'config', '/sys/config/index', 0, 'C', NULL, 0, 1, '/843af15ab7694d54af793e4a5e6fb76e/a17e9c809f1049668633d8fe6103e740/', 7, 1, '056fb0eeb9a44cb0953534b4c0ca01fa', '2021-12-30 11:11:11', '', '2022-02-23 00:41:11', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, '/sys/conf/config', 0);
INSERT INTO sys_dict (id, name, code, note, who_created, when_created, who_modified, when_modified, app_id) VALUES('27906b13c5c0484396e284a4368b24b3', '参数类型', 'sys_config_value_type', NULL, '', '2022-01-17 17:36:40', '', '2022-01-17 17:36:40', NULL);
INSERT INTO sys_dict_item (id, name, group_name, sys_dict_id, code, value, order_num, note, who_created, when_created, who_modified, when_modified, app_id) VALUES('53fa72c48f4c4324bd36733fe871b242', '文本', NULL, '27906b13c5c0484396e284a4368b24b3', 'sys_config_value_type', '0', 1, NULL, '', '2022-01-17 17:36:59', '', '2022-01-17 17:36:59', NULL);
INSERT INTO sys_dict_item (id, name, group_name, sys_dict_id, code, value, order_num, note, who_created, when_created, who_modified, when_modified, app_id) VALUES('98dba411055b460c913ab5454a6718c9', '图片', NULL, '27906b13c5c0484396e284a4368b24b3', 'sys_config_value_type', '1', 2, NULL, '', '2022-01-17 17:37:07', '', '2022-01-17 17:37:07', NULL);
INSERT INTO sys_dict_item (id, name, group_name, sys_dict_id, code, value, order_num, note, who_created, when_created, who_modified, when_modified, app_id) VALUES('a988ca46b6b445fc8f36d8b9d1243cb8', '颜色', NULL, '27906b13c5c0484396e284a4368b24b3', 'sys_config_value_type', '2', 3, NULL, '', '2022-01-17 17:37:15', '', '2022-01-17 17:37:15', NULL);

INSERT INTO sys_user (id, app_id, avatar, deleted, email, mobile, note, password, post, real_name, sex, status, when_created, when_modified, who_created, who_modified, sys_unit_id, username) VALUES('8116f0bc8222413fb72de98a32960b1a', NULL, NULL, 0, NULL, NULL, NULL, 'MTIzNDU2', NULL, '开发人员', 1, 1, '2022-09-28 14:45:53', '2022-09-28 14:45:53', '056fb0eeb9a44cb0953534b4c0ca01fa', '056fb0eeb9a44cb0953534b4c0ca01fa', NULL, 'dev');

INSERT INTO sys_role
(id, name, code, note, status, who_created, when_created, who_modified, when_modified, app_id)
VALUES('3fc43c9c69f44144bd032d9451ba328b', '团队成员', 'team_member', '青松开发者平台-团队成员', 1, '', '2022-03-10 06:13:01', '056fb0eeb9a44cb0953534b4c0ca01fa', '2022-03-29 11:43:59', NULL);
INSERT INTO sys_role
(id, name, code, note, status, who_created, when_created, who_modified, when_modified, app_id)
VALUES('4a30f4d346074b4ba8363944f004c1d9', '团队负责人', 'team_owner', '青松开发者平台-团队负责人', 1, '', '2022-03-10 06:12:31', '056fb0eeb9a44cb0953534b4c0ca01fa', '2022-03-29 11:44:06', NULL);
INSERT INTO sys_user_role (id, app_id, when_created, who_created, sys_role_id, sys_user_id) VALUES('8d641b3aded845feae88aef3d7e32e33', NULL, '2022-09-28 14:45:53', '056fb0eeb9a44cb0953534b4c0ca01fa', '4a30f4d346074b4ba8363944f004c1d9', '8116f0bc8222413fb72de98a32960b1a');


-- 以下是来自 version_22_1.sql 的内容 --



CREATE TABLE sys_search_config (
    id varchar(36)  NOT NULL,
    data_source varchar(100) ,
    table_name varchar(100),
    columns varchar(255),
    primary_columns varchar(255),
    link varchar(255) ,
    labels varchar(255) ,
    when_created varchar(50) ,
    when_modified varchar(50) ,
    who_created varchar(36) ,
    who_modified varchar(36) ,
    title_column varchar(100) ,
    PRIMARY KEY (id)
) ;

-- 以下是来自 version_23_1.sql 的内容 --


CREATE TABLE rep_template(
                             id VARCHAR(36) NOT NULL   COMMENT '主键' ,
                             name VARCHAR(255) NOT NULL   COMMENT '模板名称' ,
                             tpl_file_id VARCHAR(36)    COMMENT '报告模板id' ,
                             excel_file VARCHAR(36)    COMMENT '配置文件' ,
                             type VARCHAR(10)    COMMENT '报告类型' ,
                             ds_sets VARCHAR(1024)    COMMENT '数据集列表' ,
                             note VARCHAR(255)    COMMENT '说明' ,
                             who_created VARCHAR(36)    COMMENT '创建人' ,
                             when_created VARCHAR(30)    COMMENT '创建时间' ,
                             who_modified VARCHAR(36)    COMMENT '更新人' ,
                             when_modified VARCHAR(30)    COMMENT '更新时间' ,
                             PRIMARY KEY (id)
)  COMMENT = '报告模板表';


-- 以下是来自 version_24_1.sql 的内容 --

CREATE TABLE sys_cache (
       id varchar(36) NOT NULL COMMENT 'ID',
       code varchar(255) DEFAULT NULL COMMENT '缓存KEY',
       value varchar(255) DEFAULT NULL COMMENT '缓存值',
       when_expired varchar(30) DEFAULT NULL COMMENT '过期时间',
       when_created varchar(30) DEFAULT NULL COMMENT '创建时间',
       app_id varchar(36) DEFAULT NULL COMMENT '关联应用ID',
       PRIMARY KEY (id)
);

-- 以下是来自 version_25_1.sql 的内容 --

alter table sys_task
    add task_argv text null comment '任务参数';


-- 以下是来自 version_26_1.sql 的内容 --


alter table sys_logic_flow add module_id VARCHAR(36) null COMMENT '关联模块';
alter table dev_page add module_id VARCHAR(36) null COMMENT '关联模块';
alter table dev_page add tags VARCHAR(36) null COMMENT '标签';



-- 以下是来自 version_27_1.sql 的内容 --

DROP TABLE IF EXISTS dev_faas_node_type;
CREATE TABLE dev_faas_node_type(
                                   `id` VARCHAR(32)    COMMENT 'ID' ,
                                   `name` VARCHAR(90)    COMMENT '名称' ,
                                   `pub_status` INT    COMMENT '发布状态 0：未发布 1：已发布' ,
                                   `icon` VARCHAR(32)    COMMENT '图标' ,
                                   `when_created` VARCHAR(255)    COMMENT '创建时间' ,
                                   `who_created` VARCHAR(255)    COMMENT '创建人员' ,
                                   `when_modified` VARCHAR(255)    COMMENT '更新时间' ,
                                   `who_modified` VARCHAR(255)    COMMENT '更新人员',
                                   PRIMARY KEY (id)
)  COMMENT = '开发-FAAS节点类型';

DROP TABLE IF EXISTS dev_faas_node;
CREATE TABLE dev_faas_node(
                              `id` VARCHAR(32) NOT NULL   COMMENT 'ID' ,
                              `name` VARCHAR(90)    COMMENT '名称' ,
                              `code` VARCHAR(90)    COMMENT '编码' ,
                              `type_id` VARCHAR(32)    COMMENT '类型id' ,
                              `config` VARCHAR(255)    COMMENT '配置文件' ,
                              `template` VARCHAR(1024)    COMMENT '脚本模板' ,
                              `icon` VARCHAR(32)    COMMENT '图标' ,
                              `pub_status` INT    COMMENT '发布状态 0：未发布 1：已发布' ,
                              `order_num` VARCHAR(255)    COMMENT '排序' ,
                              `when_created` VARCHAR(255)    COMMENT '创建时间' ,
                              `who_created` VARCHAR(255)    COMMENT '创建人员' ,
                              `when_modified` VARCHAR(255)    COMMENT '更新时间' ,
                              `who_modified` VARCHAR(255)    COMMENT '更新人员' ,
                              PRIMARY KEY (id)
)  COMMENT = '开发-FAAS节点';


-- 以下是来自 version_28_1.sql 的内容 --

DROP TABLE IF EXISTS sys_data_change;
CREATE TABLE sys_data_change(
                                `id` VARCHAR(32) NOT NULL   COMMENT '主键' ,
                                `name` VARCHAR(255)    COMMENT '模块名' ,
                                `table_name` VARCHAR(255)    COMMENT '表名' ,
                                `object_name` VARCHAR(255)    COMMENT '对象名称' ,
                                `operator` VARCHAR(255)    COMMENT '操作人员' ,
                                `oper_type` VARCHAR(255)    COMMENT '操作类型' ,
                                `oper_time` VARCHAR(255)    COMMENT '操作时间' ,
                                `content` VARCHAR(255)    COMMENT '变更内容' ,
                                PRIMARY KEY (id)
)  COMMENT = '数据变更记录';


-- 以下是来自 version_29_1.sql 的内容 --

alter table `sys_dict_item` modify column `value` varchar(255) DEFAULT NULL COMMENT '值';

-- 以下是来自 version_30_1.sql 的内容 --

DROP TABLE IF EXISTS sys_instance;
CREATE TABLE sys_instance(
                             `id` VARCHAR(32) NOT NULL   COMMENT '主键' ,
                             `host_name` VARCHAR(32)    COMMENT '主机名' ,
                             `port` INT    COMMENT '端口' ,
                             `heart_beat_time` VARCHAR(30)    COMMENT '心跳时间' ,
                             `reg_time` VARCHAR(30)    COMMENT '注册时间' ,
                             PRIMARY KEY (id)
)  COMMENT = '系统-实例表';

alter table `sys_role_menu` modify column `id` varchar(36) NOT NULL;
alter table `sys_role_menu` modify column `sys_menu_id` varchar(36) NOT NULL;
alter table `sys_role_menu` modify column `who_created` varchar(36) NOT NULL;


-- 以下是来自 version_31_1.sql 的内容 --

alter table sys_task
    add next_inst varchar(36) null;


-- 以下是来自 version_32_1.sql 的内容 --

alter table sys_instance
    add online int null;


-- 以下是来自 version_33_1.sql 的内容 --

DROP TABLE IF EXISTS sys_offline_download;
CREATE TABLE sys_offline_download(
                                     id VARCHAR(36) NOT NULL   COMMENT '主键' ,
                                     file_name VARCHAR(90)    COMMENT '文件名称' ,
                                     task_name VARCHAR(255)    COMMENT '任务名称' ,
                                     file_path VARCHAR(255)    COMMENT '文件路径' ,
                                     end_time VARCHAR(255)    COMMENT '结束时间' ,
                                     script VARCHAR(1024)    COMMENT '脚本' ,
                                     process INT    COMMENT '进度' ,
                                     status VARCHAR(255)    COMMENT '状态 0:待开始 1:进行中 2：已完成 3:异常' ,
                                     params VARCHAR(255)    COMMENT '参数' ,
                                     error_message VARCHAR(900)    COMMENT '异常信息' ,
                                     who_created VARCHAR(36)    COMMENT '创建人' ,
                                     when_created VARCHAR(20)    COMMENT '创建时间' ,
                                     PRIMARY KEY (id)
)  COMMENT = '离线下载表';


-- 以下是来自 version_34_1.sql 的内容 --

ALTER TABLE sys_unit ADD unit_level int NULL COMMENT '机构级别';
ALTER TABLE sys_search_config ADD search_columns text NULL COMMENT '搜索字段中文';
ALTER TABLE sys_search_config MODIFY COLUMN `columns` varchar(1000) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL COMMENT '搜索字段';


-- 以下是来自 version_35_1.sql 的内容 --

ALTER TABLE sys_unit ADD required_unit int DEFAULT 0 NULL COMMENT '是否必选部门';

-- 以下是来自 version_36_1.sql 的内容 --


alter table sys_api modify column api_tags VARCHAR(255) null COMMENT '标签';
alter table sys_logic_flow modify column tags VARCHAR(255) null COMMENT '标签';
alter table dev_page modify column tags VARCHAR(255) null COMMENT '标签';


-- 以下是来自 version_37_1.sql 的内容 --

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
                                    when_created VARCHAR(30)    COMMENT '创建时间' ,
                                    who_modified VARCHAR(36)    COMMENT '修改人员' ,
                                    when_modified VARCHAR(30)    COMMENT '修改时间' ,
                                    PRIMARY KEY (id)
)  COMMENT = '逻辑编排mock';
CREATE INDEX idx_sys_operate_log_app_id USING BTREE ON sys_operate_log (app_id);
CREATE INDEX idx_sys_operate_log_module USING BTREE ON sys_operate_log (`module`);
CREATE INDEX idx_sys_operate_log_operate_time USING BTREE ON sys_operate_log (operate_time);
-- CREATE INDEX idx_dev_page_deleted USING BTREE ON dev_page (deleted,`path`);



-- 以下是来自 version_38_1.sql 的内容 --

CREATE TABLE dev_page_template (
    id varchar(36)  NOT NULL COMMENT '主键',
    when_created varchar(30) COMMENT '创建时间',
    when_modified varchar(30) COMMENT '修改时间',
    who_created varchar(36)  COMMENT '创建人',
    who_modified varchar(36)  COMMENT '修改人',
    deleted int(4) DEFAULT '0' COMMENT '删除标识',
    app_id varchar(36)  COMMENT '应用ID',
    name varchar(255)  COMMENT '页面名称',
    description varchar(255)  COMMENT '页面介绍',
    app_type varchar(100)  COMMENT '应用类型：可多个，逗号分隔，0: PC Web应用，1：移动端Web应用，2： 小程序，3： APP',
    page_json longtext  COMMENT '页面数据化JSON',
    tags varchar(255)  COMMENT '标签',
    module_id varchar(36)  COMMENT '关联模块',
    PRIMARY KEY (id)
);

CREATE TABLE dev_table  (
    id varchar(36) ,
    data_source varchar(100),
    name varchar(100),
    comment varchar(255),
    when_created varchar(100),
    when_modified varchar(100),
    who_created varchar(100),
    who_modified varchar(100),
    PRIMARY KEY (id)
);

CREATE TABLE dev_table_column  (
    id varchar(36) ,
    table_id varchar(36),
    name varchar(100),
    comment varchar(255),
    sort int(11),
    type varchar(50),
    length varchar(36),
    decimal_point varchar(100),
    is_primary int(11),
    is_null int(11),
    when_created varchar(100),
    when_modified varchar(100),
    who_created varchar(100),
    who_modified varchar(100),
    is_label_column int(11) NOT NULL DEFAULT 0 COMMENT '是否为显示字段（用于迁移选择数据时）',
    PRIMARY KEY (id)
);


CREATE TABLE dev_table_update_log  (
    id varchar(36) ,
    operate_type int(11),
    table_name varchar(100),
    column_name varchar(100),
    version_tag varchar(100),
    when_created varchar(100),
    when_modified varchar(100),
    who_created varchar(100),
    who_modified varchar(100),
    old_table_name varchar(100),
    old_column_name varchar(100),
    type varchar(36),
    length varchar(36),
    decimal_point int(11),
    is_primary int(11),
    is_null int(11),
    sort varchar(36),
    comment varchar(255),
    table_id varchar(100),
    is_column_label int(11),
    PRIMARY KEY (id)
);


-- 以下是来自 version_39_1.sql 的内容 --

alter table sys_operate_log modify column operate_time timestamp;

-- 以下是来自 version_40_1.sql 的内容 --

ALTER TABLE dev_application
    MODIFY COLUMN enable_status int(4) NULL DEFAULT NULL COMMENT '可用状态' AFTER description,
    MODIFY COLUMN dev_status int(4) NULL DEFAULT NULL COMMENT '开发状态，0: 新建1: 确认版本2: 有更新' AFTER enable_status;


-- 以下是来自 version_41_1.sql 的内容 --


INSERT INTO dev_module
(id, app_id, has_path, is_sys, name, parent_id, path, sort, when_created, when_modified, who_created, who_modified)
VALUES('0913bc0b384c44d99e384b992cb7fe40', '064b3b44b85a45fe87fcce88d72b2519', 1, 1, '开发平台', NULL, '/dev', 1, '2023-03-29 10:43:48', '2023-03-30 09:55:00', '7aed8c297a6940f681c26eb6ab68893d', '7aed8c297a6940f681c26eb6ab68893d');
INSERT INTO dev_module
(id, app_id, has_path, is_sys, name, parent_id, path, sort, when_created, when_modified, who_created, who_modified)
VALUES('09e4ec197da14de3844b6b04c4fa5ee9', '064b3b44b85a45fe87fcce88d72b2519', 0, 1, '开发平台管理', NULL, NULL, 3, '2023-03-29 10:48:15', '2023-03-29 10:48:15', '7aed8c297a6940f681c26eb6ab68893d', '7aed8c297a6940f681c26eb6ab68893d');
INSERT INTO dev_module
(id, app_id, has_path, is_sys, name, parent_id, path, sort, when_created, when_modified, who_created, who_modified)
VALUES('5a10d15671704b6cbc01cc3a8bed365a', '064b3b44b85a45fe87fcce88d72b2519', 0, 1, '公共库', NULL, NULL, 4, '2022-05-31 15:18:08', '2022-05-31 15:24:40', '7aed8c297a6940f681c26eb6ab68893d', '7aed8c297a6940f681c26eb6ab68893d');
INSERT INTO dev_module
(id, app_id, has_path, is_sys, name, parent_id, path, sort, when_created, when_modified, who_created, who_modified)
VALUES('6e2c61bac14a42ca9b24bbab853022a0', '0b00821bab714d8299d07a68c25873ed', 0, 0, '接口', 'bea8f78657644fa5808a863cce3c1182', NULL, 1, '2023-04-11 14:18:15', '2023-04-11 14:21:21', '056fb0eeb9a44cb0953534b4c0ca01fa', '056fb0eeb9a44cb0953534b4c0ca01fa');
INSERT INTO dev_module
(id, app_id, has_path, is_sys, name, parent_id, path, sort, when_created, when_modified, who_created, who_modified)
VALUES('90f2c2ea0f9942d181388e24fd6ee936', '064b3b44b85a45fe87fcce88d72b2519', 1, 1, 'v3开发', '0913bc0b384c44d99e384b992cb7fe40', '/dev/v3', 1, '2023-03-29 17:37:35', '2023-03-30 09:55:12', '7aed8c297a6940f681c26eb6ab68893d', '7aed8c297a6940f681c26eb6ab68893d');
INSERT INTO dev_module
(id, app_id, has_path, is_sys, name, parent_id, path, sort, when_created, when_modified, who_created, who_modified)
VALUES('9bbe33574d0547e78f72f5982bea26cd', '064b3b44b85a45fe87fcce88d72b2519', 0, 1, '子页面', '0913bc0b384c44d99e384b992cb7fe40', NULL, 0, '2023-03-29 10:46:23', '2023-03-30 09:55:08', '7aed8c297a6940f681c26eb6ab68893d', '7aed8c297a6940f681c26eb6ab68893d');
INSERT INTO dev_module
(id, app_id, has_path, is_sys, name, parent_id, path, sort, when_created, when_modified, who_created, who_modified)
VALUES('d4820db91eab4cdc9c82703cf1d4df83', '064b3b44b85a45fe87fcce88d72b2519', 0, 0, '流程关联表单页', '1e329b86988b4dd79b49887b774b0879', NULL, 0, '2023-03-29 16:28:46', '2023-03-29 16:28:46', '7aed8c297a6940f681c26eb6ab68893d', '7aed8c297a6940f681c26eb6ab68893d');
INSERT INTO dev_module
(id, app_id, has_path, is_sys, name, parent_id, path, sort, when_created, when_modified, who_created, who_modified)
VALUES('e0047b3716fa48789d8d2377b1d23195', '064b3b44b85a45fe87fcce88d72b2519', 0, 1, '系统配置', NULL, NULL, 2, '2023-03-29 09:27:04', '2023-03-29 09:27:04', '7aed8c297a6940f681c26eb6ab68893d', '7aed8c297a6940f681c26eb6ab68893d');
INSERT INTO dev_module
(id, app_id, has_path, is_sys, name, parent_id, path, sort, when_created, when_modified, who_created, who_modified)
VALUES('fbe9d1e36a37423aa9ab4711c569093f', '064b3b44b85a45fe87fcce88d72b2519', 0, 1, '基础功能', NULL, NULL, 2, '2023-03-29 09:26:54', '2023-03-29 09:26:54', '7aed8c297a6940f681c26eb6ab68893d', '7aed8c297a6940f681c26eb6ab68893d');


-- 以下是来自 version_42_1.sql 的内容 --

drop table if exists dev_view_model;
drop table if exists dev_view_model_field;
drop table if exists dev_view_model_flow;
drop table if exists sys_view_model;
drop table if exists sys_view_model_field;
drop table if exists sys_view_model_flow;

CREATE TABLE if not exists wf_cc_inst
(
    inst_Id     varchar(32)     NOT NULL COMMENT '流程实例ID',
    actor_Id    varchar(50)     NOT NULL COMMENT '参与者ID',
    creator     varchar(50)     DEFAULT NULL COMMENT '发起人',
    create_Time varchar(50)     DEFAULT NULL COMMENT '抄送时间',
    finish_Time varchar(50)     DEFAULT NULL COMMENT '完成时间',
    status      tinyint(1) NULL DEFAULT NULL COMMENT '状态',
    PRIMARY KEY (inst_Id, actor_Id)
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
    actor_Id varchar(500) NOT NULL COMMENT '参与者ID',
    PRIMARY KEY (task_Id, actor_Id)
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
    actor_Id varchar(500) NOT NULL COMMENT '参与者ID',
    PRIMARY KEY (task_Id, actor_Id)
    );



-- 以下是来自 version_43_1.sql 的内容 --

CREATE TABLE if not exists dev_plugin_api (
                                  `id` varchar(36) COLLATE utf8mb4_bin NOT NULL COMMENT '主键',
                                  `title` varchar(255) COLLATE utf8mb4_bin DEFAULT NULL COMMENT '标题',
                                  `group_code` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin DEFAULT NULL COMMENT '组编码',
                                  `code` varchar(255) COLLATE utf8mb4_bin DEFAULT NULL COMMENT '编码',
                                  `tags` varchar(255) COLLATE utf8mb4_bin DEFAULT NULL COMMENT '标签',
                                  `notes` text COLLATE utf8mb4_bin COMMENT '描述',
                                  `order_num` varchar(10) COLLATE utf8mb4_bin DEFAULT NULL COMMENT '位置',
                                  `who_created` varchar(36) COLLATE utf8mb4_bin DEFAULT NULL COMMENT '创建人',
                                  `when_created` varchar(30) COLLATE utf8mb4_bin DEFAULT NULL COMMENT '创建时间',
                                  `who_modified` varchar(36) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin DEFAULT NULL COMMENT '修改人',
                                  `when_modified` varchar(30) COLLATE utf8mb4_bin DEFAULT NULL COMMENT '修改时间',
                                  PRIMARY KEY (`id`)
) COMMENT='FAAS接口组';

CREATE TABLE if not exists  dev_plugin_group (
                                    `id` varchar(36) COLLATE utf8mb4_bin NOT NULL COMMENT '主键',
                                    `name` varchar(255) COLLATE utf8mb4_bin DEFAULT NULL COMMENT '名称',
                                    `code` varchar(255) COLLATE utf8mb4_bin DEFAULT NULL COMMENT '编码',
                                    `notes` text COLLATE utf8mb4_bin COMMENT '描述',
                                    `order_num` int(11) DEFAULT NULL COMMENT '排序',
                                    `who_created` varchar(36) COLLATE utf8mb4_bin DEFAULT NULL COMMENT '创建人',
                                    `when_created` varchar(30) COLLATE utf8mb4_bin DEFAULT NULL COMMENT '创建时间',
                                    `who_modified` varchar(36) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin DEFAULT NULL COMMENT '修改人',
                                    `when_modified` varchar(30) COLLATE utf8mb4_bin DEFAULT NULL COMMENT '修改时间',
                                    PRIMARY KEY (`id`)
);

CREATE TABLE if not exists `dev_plugin_operation` (
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
                                        `when_created` varchar(30) COLLATE utf8mb4_bin DEFAULT NULL COMMENT '创建时间',
                                        `who_modified` varchar(36) COLLATE utf8mb4_bin DEFAULT NULL COMMENT '修改人',
                                        `when_modified` varchar(30) COLLATE utf8mb4_bin DEFAULT NULL COMMENT '修改时间',
                                        `order_num` int(8) DEFAULT '0' COMMENT '序号',
                                        PRIMARY KEY (`id`)
) ;

INSERT INTO `dev_plugin_group`(`id`, `name`, `code`, `notes`, `order_num`, `who_created`, `when_created`, `who_modified`, `when_modified`) VALUES ('a7ce7fa1e3ef4d0080cf06789d43a27b', 'Elasticsearch', 'elasticsearch', 'Elasticsearch操作相关', 9, 'bab66508eefe49ada701257548bbe54a', '2023-06-05 16:07:33', 'bab66508eefe49ada701257548bbe54a', '2023-06-05 16:07:33');
INSERT INTO `dev_plugin_group`(`id`, `name`, `code`, `notes`, `order_num`, `who_created`, `when_created`, `who_modified`, `when_modified`) VALUES ('b2d366679e774dda9b41aaabca3cf2d7', '流程引擎', 'workflow', '自研的流程引擎', 8, '94123ca363dc4dfaa62a6bb5dcd3bf50', '2023-05-26 09:30:32', '94123ca363dc4dfaa62a6bb5dcd3bf50', '2023-05-26 09:30:32');
INSERT INTO `dev_plugin_group`(`id`, `name`, `code`, `notes`, `order_num`, `who_created`, `when_created`, `who_modified`, `when_modified`) VALUES ('g1', 'Office文档操作', 'office', 'Office文件操作', 1, NULL, NULL, NULL, NULL);
INSERT INTO `dev_plugin_group`(`id`, `name`, `code`, `notes`, `order_num`, `who_created`, `when_created`, `who_modified`, `when_modified`) VALUES ('g2', '接口示例', 'demo', '接口示例', 22, NULL, NULL, '8c0fd6ed3c9f4d48b5a5a7a814b66bac', '2023-07-26 14:20:54');
INSERT INTO `dev_plugin_group`(`id`, `name`, `code`, `notes`, `order_num`, `who_created`, `when_created`, `who_modified`, `when_modified`) VALUES ('g3', '常用操作', 'utils', '常用操作', 3, NULL, NULL, NULL, NULL);
INSERT INTO `dev_plugin_group`(`id`, `name`, `code`, `notes`, `order_num`, `who_created`, `when_created`, `who_modified`, `when_modified`) VALUES ('g4', '内容存储', 'data', '内容存储', 4, NULL, NULL, NULL, NULL);
INSERT INTO `dev_plugin_group`(`id`, `name`, `code`, `notes`, `order_num`, `who_created`, `when_created`, `who_modified`, `when_modified`) VALUES ('g5', '数据库', 'db', '数据库', 5, NULL, NULL, NULL, NULL);
INSERT INTO `dev_plugin_group`(`id`, `name`, `code`, `notes`, `order_num`, `who_created`, `when_created`, `who_modified`, `when_modified`) VALUES ('g6', '网络通信', 'network', '网络通信', 6, NULL, NULL, NULL, NULL);
INSERT INTO `dev_plugin_group`(`id`, `name`, `code`, `notes`, `order_num`, `who_created`, `when_created`, `who_modified`, `when_modified`) VALUES ('g7', '图像处理', 'image', '图像处理', 7, NULL, NULL, NULL, NULL);

alter table sys_logic_flow add new_flow_json longtext null COMMENT 'v3.1+的流程显示JSON';
alter table sys_logic_history add new_flow_json longtext null COMMENT '流程图JSON';
alter table sys_logic_template add flow_config longtext null COMMENT '配置：图标、颜色、继承、参数等';
alter table sys_logic_template add type int(11) null COMMENT '默认为0，0为旧版，只保存源码数据，需要自行修改，1为新版（自定义节点），加入到流程后不可2次修改';
alter table sys_logic_template add new_flow_json longtext null COMMENT 'v3.1+的流程显示JSON';


-- 以下是来自 version_44_1.sql 的内容 --

delete from sys_dict_item where id='1537c3ca5e934e6c846b0415229dbe85';


-- 以下是来自 version_45_1.sql 的内容 --

CREATE TABLE if not exists `dev_chat_history`  (
    `id` varchar(36) NOT NULL COMMENT 'id',
    `question` longtext NOT NULL COMMENT '问题',
    `answer` longtext NULL COMMENT '回答',
    `args` longtext NULL COMMENT '附加参数（JSON）',
    `when_created` varchar(50) NULL COMMENT '创建时间',
    `who_created` varchar(36) NULL COMMENT '创建人',
    PRIMARY KEY (`id`)
    ) ;

CREATE TABLE if not exists `dev_curd`  (
    `id` varchar(36) NOT NULL COMMENT '主键',
    `name` varchar(255) NULL COMMENT '名称',
    `group_id` varchar(36) NULL COMMENT '组ID',
    `source_name` varchar(255) NULL COMMENT '数据源名称',
    `table_name` varchar(255) NULL COMMENT '数据表名称',
    `primary_name` varchar(50) NULL COMMENT '主键字段名',
    `request_prefix` varchar(255) NULL COMMENT '请求前缀',
    `enable_funs` varchar(255) NULL COMMENT '启动功能',
    `create_funs` varchar(255) NULL COMMENT '创建内容',
    `column_json` text NULL COMMENT '列定义',
    `app_id` varchar(36) NULL COMMENT '应用ID',
    `who_created` varchar(36) NULL COMMENT '创建人',
    `when_created` varchar(30) NULL COMMENT '创建时间',
    `who_modified` varchar(36) NULL COMMENT '修改人',
    `when_modified` varchar(255) NULL COMMENT '修改时间',
    PRIMARY KEY (`id`)
    ) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_bin COMMENT = '开发-增删改查' ROW_FORMAT = Dynamic;

CREATE TABLE if not exists `dev_data_source`  (
    `id` varchar(36) NOT NULL COMMENT 'ID',
    `name` varchar(100) NULL COMMENT '数据源名称',
    `who_created` varchar(36) NULL COMMENT '创建人员',
    `when_created` varchar(30) NULL COMMENT '创建时间',
    `who_modified` varchar(36) NULL COMMENT '修改人员',
    `when_modified` varchar(30) NULL COMMENT '修改时间',
    `app_id` varchar(36) NULL COMMENT '关联应用',
    `kdb_id` varchar(40) NULL COMMENT '在kingDB中对应的ID',
    `team_id` varchar(36) NULL COMMENT '所属团队ID',
    `deleted` int(1) NULL COMMENT '是否已删除',
    PRIMARY KEY (`id`)
    )  COMMENT = '数据源业务映射表';

CREATE TABLE if not exists `dev_file_version`  (
    `id` varchar(36) NOT NULL COMMENT 'ID',
    `file_name` varchar(255) NULL COMMENT '文件名',
    `path` varchar(1000) NULL COMMENT '导致安装包的位置',
    `os_type` varchar(20) NULL COMMENT '操作系统类型',
    `version` varchar(100) NULL COMMENT '版本号（vX.X.X结构）',
    `path_by_package` varchar(1000) NULL COMMENT '所在package中的真实位置',
    `file_size` int(11) NULL COMMENT '文件大小',
    `description` varchar(255) NULL COMMENT '描述',
    `parent_path` varchar(1000) NULL COMMENT '父目录',
    `when_modified` varchar(30) NULL COMMENT '更新时间',
    `who_modified` varchar(36) NULL COMMENT '更新人',
    PRIMARY KEY (`id`)
    ) COMMENT = '开发表-文件版本表' ;

ALTER TABLE `rep_dataset` ADD COLUMN `dataset_search_id` varchar(32) NULL COMMENT '是否为搜索数据' AFTER `column_def`;
ALTER TABLE `rep_dataset` ADD COLUMN `shape` int(11) NULL COMMENT '是否为自定义SQL' AFTER `dataset_search_id`;
ALTER TABLE `rep_dataset` ADD COLUMN `template` int(11) NULL COMMENT '是否为Excel模板（0：否，1：是）' AFTER `shape`;
ALTER TABLE `rep_dataset` ADD COLUMN `rep_cron` varchar(50) NULL COMMENT '模板报表定时任务Cron表达式' AFTER `template`;

ALTER TABLE `sys_unit` ADD COLUMN `short_name` varchar(36) NULL COMMENT '简称' AFTER `app_id`;
ALTER TABLE `sys_unit` ADD COLUMN `unit_code` varchar(100) NULL COMMENT '机构编码' AFTER `short_name`;

ALTER TABLE `sys_user` ADD COLUMN `jira_name` varchar(100) NULL COMMENT 'jira账号名（项目管理系统）' AFTER `app_id`;


-- 以下是来自 version_46_1.sql 的内容 --

ALTER TABLE sys_online_user MODIFY COLUMN login_token TEXT;

-- 以下是来自 version_47_1.sql 的内容 --

create table if not exists sys_auth_source
(
    id           varchar(36) not null,
    code         varchar(36),
    icon         varchar(255),
    config       longtext,
    logic_flow_id  varchar(36),
    name         varchar(255),
    note         varchar(255),
    order_num     integer,
    status       integer,
    type       integer,
    when_created  varchar(20),
    when_modified varchar(20),
    whoCreated   varchar(36),
    who_modified  varchar(36),
    primary key (id)
) engine = InnoDB;


-- 以下是来自 version_48_1.sql 的内容 --

ALTER TABLE `sys_login_log`
ADD COLUMN `address` varchar(255) NULL COMMENT '位置' AFTER `when_created`;


-- 以下是来自 version_49_1.sql 的内容 --

ALTER TABLE `open_api_log`
MODIFY COLUMN `use_time` int NULL COMMENT '响应时间(秒)' AFTER `request_ip`;
ALTER TABLE `open_api_log`
MODIFY COLUMN `error_message` text CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL COMMENT '错误信息' AFTER `success`;


-- 以下是来自 version_50_1.sql 的内容 --

CREATE TABLE sys_logic_template_user (
    id varchar(36)  NOT NULL COMMENT 'ID',
    app_id varchar(36)  DEFAULT NULL COMMENT '关联应用',
    when_created varchar(50)  DEFAULT NULL COMMENT '创建时间',
    who_created varchar(36)  DEFAULT NULL COMMENT '创建人',
    template_id varchar(36)  DEFAULT NULL COMMENT ' 组件（模板）ID',
    PRIMARY KEY (id)
) COMMENT='流程模板关联用户表';

alter table sys_logic_template add sys_suggested int(11) null COMMENT '默认常用';
alter table sys_logic_template add publish_status int(11) null COMMENT '默认null/0为公共，1：个人私有，2：应用私有，3：团队私有';


ALTER TABLE dev_page_template ADD snapshot_img_id VARCHAR(32) NULL COMMENT '模板快照图片id，模板快照图片在保存模板时前端保存';
ALTER TABLE dev_page_template ADD order_num INT NULL COMMENT '排序';
ALTER TABLE dev_page_template ADD bg_colors VARCHAR(64) NULL COMMENT '模板展示的背景色';
ALTER TABLE dev_page_template ADD page_type VARCHAR(32) NULL COMMENT '模板页面类型：page;form;flow;report;';
ALTER TABLE dev_page_template ADD use_num INT DEFAULT 0 NULL COMMENT '模板使用人数';
ALTER TABLE dev_page_template ADD extra LONGTEXT NULL COMMENT '用于配置页面参数示例数据JSON';

CREATE TABLE IF NOT EXISTS dev_page_template_history (
    id varchar(36) NOT NULL COMMENT 'ID',
    tpl_id varchar(36) DEFAULT NULL COMMENT '页面模板ID',
    page_json longtext COMMENT '页面模板JSON',
    when_created timestamp NULL DEFAULT NULL COMMENT '创建时间',
    who_created varchar(36) DEFAULT NULL COMMENT '创建人',
    version_tag varchar(50) DEFAULT NULL COMMENT '版本标签',
    version_tag_time varchar(30) DEFAULT NULL COMMENT '版本时间',
    app_id varchar(36) DEFAULT NULL COMMENT '应用ID',
    PRIMARY KEY (id),
    KEY dev_page_tpl_history_tpl_id_IDX (tpl_id)
) COMMENT='开发表-页面模板修改历史记录表';

ALTER TABLE dev_faas_node MODIFY COLUMN config text NULL COMMENT '配置文件';
ALTER TABLE dev_faas_node MODIFY COLUMN template varchar(10240) NULL COMMENT '脚本模板';

CREATE TABLE IF NOT EXISTS dev_chat_history (
    id varchar(36)  NOT NULL,
    question text  NOT NULL,
    answer text ,
    args text,
    when_created varchar(50)  DEFAULT NULL,
    who_created varchar(36)  DEFAULT NULL,
    PRIMARY KEY (id)
    );


-- 以下是来自 version_51_1.sql 的内容 --

INSERT INTO dev_team (id, deleted, description, name, owner, when_created, when_modified, who_created, who_modified) VALUES('991718335d57416a9be67d4090538402', 0, NULL, '默认团队', '8116f0bc8222413fb72de98a32960b1a', '2022-09-28 14:52:00', '2022-09-28 14:52:00', '056fb0eeb9a44cb0953534b4c0ca01fa', '056fb0eeb9a44cb0953534b4c0ca01fa');
INSERT INTO dev_team_member (id, app_id, is_owner, team_role_id, user_id, when_join, who_invite, team_id) VALUES('dfae64f660ff435086ae2482d7fa1a48', NULL, 0, '3fc43c9c69f44144bd032d9451ba328b', '8116f0bc8222413fb72de98a32960b1a', '2022-09-28 14:52:00', '056fb0eeb9a44cb0953534b4c0ca01fa', '991718335d57416a9be67d4090538402');
INSERT INTO dev_team_member (id, app_id, is_owner, team_role_id, user_id, when_join, who_invite, team_id) VALUES('dfae64f660ff435086ae2482d7fa1a49', NULL, 1, '4a30f4d346074b4ba8363944f004c1d9', '056fb0eeb9a44cb0953534b4c0ca01fa', '2022-09-28 14:52:00', '056fb0eeb9a44cb0953534b4c0ca01fa', '991718335d57416a9be67d4090538402');
CREATE TABLE IF NOT EXISTS dev_curd (
    id varchar(36)  NOT NULL ,
    name varchar(255)  DEFAULT null,
    group_id varchar(36)  DEFAULT NULL,
    source_name varchar(255)  DEFAULT NULL,
    table_name varchar(255)  DEFAULT NULL,
    primary_name varchar(50)  DEFAULT NULL,
    request_prefix varchar(255)  DEFAULT NULL,
    enable_funs varchar(255)  DEFAULT NULL,
    create_funs varchar(255)  DEFAULT NULL,
    column_json text ,
    app_id varchar(36)  DEFAULT NULL,
    who_created varchar(36)  DEFAULT NULL,
    when_created varchar(20)  DEFAULT NULL,
    who_modified varchar(36)  DEFAULT NULL,
    when_modified varchar(255)  DEFAULT NULL ,
    PRIMARY KEY (id)
    );

CREATE TABLE IF NOT EXISTS sys_logic_template_user (
                                         id varchar(36) NOT NULL,
                                         app_id varchar(36) DEFAULT NULL,
                                         when_created varchar(50) DEFAULT NULL,
                                         who_created varchar(36) DEFAULT NULL,
                                         template_id varchar(36) DEFAULT NULL,
                                         PRIMARY KEY (id)
);



ALTER TABLE dev_power_tree ADD order_num int NULL COMMENT '排序';
ALTER TABLE sys_logic_template ADD order_num int NULL COMMENT '排序';
ALTER TABLE sys_logic_template ADD enable_status int NULL default 1 COMMENT '可用状态';



-- 以下是来自 version_52_1.sql 的内容 --

ALTER TABLE sys_menu ADD affix int NULL DEFAULT '0' COMMENT '是否固定页签';
ALTER TABLE `sys_api`
    ADD COLUMN `cache_enable` int(8) NULL COMMENT '是否启用接口缓存' AFTER `module_id`,
    ADD COLUMN `cache_cron` varchar(255) NULL COMMENT '缓存刷新表达式' AFTER `cache_enable`,
    ADD COLUMN `cache_expire_time` int(10) NULL COMMENT '缓存失效时间(秒)' AFTER `cache_cron`;


-- 以下是来自 version_53_1.sql 的内容 --

CREATE TABLE if not exists `sys_password_log` (
                                    `id` varchar(36) COLLATE utf8mb4_bin NOT NULL COMMENT '主键',
                                    `user_id` varchar(36) COLLATE utf8mb4_bin DEFAULT NULL COMMENT '用户id',
                                    `when_created` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin DEFAULT NULL COMMENT '创建时间',
                                    PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_bin;


CREATE TABLE if not exists `sys_secret_rule` (
                                   `id` varchar(36) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL COMMENT '主键',
                                   `name` varchar(255) COLLATE utf8mb4_bin DEFAULT NULL COMMENT '规则名称',
                                   `logic_id` varchar(36) COLLATE utf8mb4_bin DEFAULT NULL COMMENT '逻辑编排ID',
                                   `secret_type` int(8) DEFAULT '0' COMMENT '安全类型',
                                   `status` int(8) DEFAULT NULL COMMENT '是否启用',
                                   `order_num` int(11) DEFAULT NULL COMMENT '排序',
                                   `notes` text COLLATE utf8mb4_bin COMMENT '说明',
                                   `when_created` varchar(20) COLLATE utf8mb4_bin DEFAULT NULL COMMENT '创建时间',
                                   `who_created` varchar(36) COLLATE utf8mb4_bin DEFAULT NULL COMMENT '创建人',
                                   `when_modified` varchar(20) COLLATE utf8mb4_bin DEFAULT NULL COMMENT '更新时间',
                                   `who_modified` varchar(36) COLLATE utf8mb4_bin DEFAULT NULL COMMENT '更新人',
                                   PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_bin COMMENT='密码校验规则';


-- 以下是来自 version_54_1.sql 的内容 --

ALTER TABLE `sys_config` ADD COLUMN `is_public` int NULL COMMENT '是否是公开的配置，公开的配置未登录也可以查看';
CREATE TABLE if not exists `dev_model_sql`
(
    `id`            varchar(36) NOT NULL COMMENT '主键',
    `app_id`        varchar(36)  DEFAULT NULL COMMENT '应用id',
    `title`         varchar(255) DEFAULT NULL COMMENT '标题',
    `source_name`   varchar(50)  DEFAULT NULL COMMENT '数据源',
    `content`       text COMMENT '脚本',
    `status`        int(8)       DEFAULT '0' COMMENT '执行状态 0: 未执行 1：已执行 2：执行异常',
    `sql_version`   int(8)       DEFAULT NULL COMMENT '版本号',
    `messages`      text COMMENT '执行结果',
    `ignore_except` int(255)     DEFAULT '1' COMMENT '是否忽略错误',
    `exec_err_line` int(11)      DEFAULT '0' COMMENT '错误行号',
    `exec_time`     varchar(20)  DEFAULT NULL COMMENT '执行时间',
    `exec_user_id`  varchar(36)  DEFAULT NULL COMMENT '执行人',
    `when_created`  varchar(20)  DEFAULT NULL COMMENT '创建时间',
    `who_created`   varchar(36)  DEFAULT NULL COMMENT '创建人',
    `when_modified` varchar(20)  DEFAULT NULL COMMENT '修改时间',
    `who_modified`  varchar(36)  DEFAULT NULL COMMENT '修改人',
    PRIMARY KEY (`id`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4;


-- 以下是来自 version_55_1.sql 的内容 --

ALTER TABLE sys_logic_flow
    ADD COLUMN i18n_keys text NULL COMMENT '国际化键名' ;


-- 以下是来自 version_56_1.sql 的内容 --

ALTER TABLE sys_menu
    ADD COLUMN active_icon VARCHAR(255) NULL COMMENT '选中得图标';


-- 以下是来自 version_57_1.sql 的内容 --

ALTER TABLE dev_team
    ADD COLUMN is_audit tinyint(4) DEFAULT NULL COMMENT '是否审核 0: 否 1：是',
    ADD COLUMN image varchar(255) DEFAULT NULL COMMENT '团队头像';


-- 以下是来自 version_58_1.sql 的内容 --

ALTER TABLE open_account ADD COLUMN app_id varchar(36) NULL;
ALTER TABLE sys_notice
    ADD COLUMN is_force tinyint(4) DEFAULT NULL COMMENT '是否重要 0: 否 1：是',
    ADD COLUMN config LONGTEXT DEFAULT NULL COMMENT '团队头像';
ALTER TABLE sys_instance
    ADD COLUMN cluster_no int(255) NULL COMMENT '集群号';



-- 以下是来自 version_59_1.sql 的内容 --

ALTER TABLE sys_config
    ADD COLUMN group_id varchar(36) DEFAULT NULL COMMENT '关联组';

CREATE TABLE if not exists  sys_config_group
(
    id            varchar(36)  NOT NULL COMMENT '主键ID',
    app_id        varchar(36) DEFAULT NULL COMMENT '关联应用',
    group_name    varchar(255) NOT NULL COMMENT '组名称',
    group_path    varchar(255)     DEFAULT NULL COMMENT '路径',
    when_created  varchar(30) DEFAULT NULL COMMENT '创建时间',
    when_modified varchar(30) DEFAULT NULL COMMENT '修改时间',
    who_created   varchar(36) DEFAULT NULL COMMENT '创建人员',
    who_modified  varchar(36) DEFAULT NULL COMMENT '修改人员',
    note          varchar(255) DEFAULT NULL COMMENT '备注',
    parent_id     varchar(32)      DEFAULT NULL COMMENT '父分组ID',
    group_type    int(11) DEFAULT '1' COMMENT '分组层级',
    leaf_config   longtext COMMENT '配置格式json',
    sort          int(11) DEFAULT NULL COMMENT '排序',
    icon          varchar(50) DEFAULT NULL COMMENT '图标',
    PRIMARY KEY (id) USING BTREE
);

ALTER TABLE dev_application
    ADD COLUMN depend_datasources text NULL COMMENT '依赖数据源',
    ADD COLUMN depend_apps text NULL COMMENT '依赖应用';

-- 以下是来自 version_60_1.sql 的内容 --

ALTER TABLE `open_api_log` ADD COLUMN `api_id` varchar(36) COMMENT '接口id' ;


-- 以下是来自 version_61_1.sql 的内容 --

ALTER TABLE `dev_application`
    ADD COLUMN `app_namespace` varchar(255) NULL COMMENT '应用命名空间';

ALTER TABLE `dev_model_sql` MODIFY COLUMN content longtext NULL COMMENT '脚本';

-- 以下是来自 version_62_1.sql 的内容 --


CREATE TABLE `dev_git_tag` (
  `id` varchar(36) CHARACTER SET utf8mb4  NOT NULL COMMENT '主键',
  `tag` varchar(255) CHARACTER SET utf8mb4  DEFAULT NULL COMMENT '标签名称',
  `repo` varchar(255) CHARACTER SET utf8mb4  DEFAULT NULL COMMENT '仓库地址',
  `resource` varchar(255) CHARACTER SET utf8mb4  DEFAULT NULL COMMENT '资源文件',
  `commit_id` varchar(255) CHARACTER SET utf8mb4  DEFAULT NULL COMMENT '提交ID',
  `public_commit_ids` text CHARACTER SET utf8mb4  COMMENT '公共依赖库',
  `note` text CHARACTER SET utf8mb4  COMMENT '版本说明',
  `when_created` varchar(20) CHARACTER SET utf8mb4  DEFAULT NULL COMMENT '创建时间',
  `who_created` varchar(255) CHARACTER SET utf8mb4  DEFAULT NULL COMMENT '创建人',
  PRIMARY KEY (`id`) USING BTREE
);
-- `k-pine`.dev_model_latest definition

CREATE TABLE `dev_model_latest` (
  `id` varchar(36) CHARACTER SET utf8mb4  NOT NULL COMMENT '主键ID',
  `model_name` varchar(50) CHARACTER SET utf8mb4  DEFAULT NULL COMMENT '模型名称',
  `source_name` varchar(50) CHARACTER SET utf8mb4  DEFAULT NULL COMMENT '数据源名称',
  `version_name` varchar(100) CHARACTER SET utf8mb4  DEFAULT NULL COMMENT '修订版本',
  `version_who` varchar(100) CHARACTER SET utf8mb4  DEFAULT NULL COMMENT '修订人',
  `version_time` varchar(20) CHARACTER SET utf8mb4  DEFAULT NULL COMMENT '修订时间',
  `description` varchar(255) CHARACTER SET utf8mb4  DEFAULT NULL COMMENT '备注',
  `diagram` longtext CHARACTER SET utf8mb4  COMMENT '模型数据',
  `inner_version` bigint DEFAULT '0' COMMENT '内部版本号，用于服务端保存时校验',
  `custom_type_mapping` text CHARACTER SET utf8mb4  COMMENT '用户自定义类型映射，当用户导入表出现系统未适配的字段类型时，提醒用户选择要转成什么类型',
  `app_id` varchar(36) CHARACTER SET utf8mb4  DEFAULT NULL COMMENT '所属应用ID',
  `who_created` varchar(36) CHARACTER SET utf8mb4  DEFAULT NULL COMMENT '创建人',
  `when_created` varchar(20) CHARACTER SET utf8mb4  DEFAULT NULL COMMENT '创建时间',
  `who_modified` varchar(36) CHARACTER SET utf8mb4  DEFAULT NULL COMMENT '更新人',
  `when_modified` varchar(20) CHARACTER SET utf8mb4  DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE
) COMMENT='开发-模型数据表';

CREATE TABLE `dev_page_template_action_log` (
  `id` varchar(36) CHARACTER SET utf8mb4  NOT NULL COMMENT '主键',
  `action_type` int NOT NULL COMMENT '动作类型；1：模板复制；2：模板预览',
  `is_copy_all` tinyint(1) DEFAULT NULL COMMENT '是否全文复制',
  `template_id` varchar(36) CHARACTER SET utf8mb4  NOT NULL COMMENT '数据id，根据action_type指向不同的表',
  `app_id` varchar(36) CHARACTER SET utf8mb4  DEFAULT NULL COMMENT '关联应用',
  `team_id` varchar(36) CHARACTER SET utf8mb4  DEFAULT NULL COMMENT '关联团队',
  `action_content` longtext CHARACTER SET utf8mb4  COMMENT '动作关联的内容，例如action_type为模板复制时，content就是复制的内容',
  `who_created` varchar(36) CHARACTER SET utf8mb4  NOT NULL COMMENT '创建人员',
  `when_created` varchar(20) CHARACTER SET utf8mb4  NOT NULL COMMENT '创建时间',
  PRIMARY KEY (`id`) USING BTREE
) COMMENT='开发表-页面模板操作记录表';




-- 以下是来自 version_63_1.sql 的内容 --


ALTER TABLE `dev_page_template` ADD COLUMN `view_num` int DEFAULT '0' COMMENT '预览量' ;
ALTER TABLE `dev_page_template` ADD COLUMN `copy_num` int DEFAULT '0' COMMENT '被复制的次数；copy_num = copy_all_num+copy_part_num' ;
ALTER TABLE `dev_page_template` ADD COLUMN `copy_all_num` int DEFAULT '0' COMMENT '被全量复制的次数' ;
ALTER TABLE `dev_page_template` ADD COLUMN `copy_part_num` int DEFAULT '0' COMMENT '被部分复制的次数' ;

-- `k-pine`.dev_search_history definition

CREATE TABLE `dev_search_history` (
  `id` varchar(36) CHARACTER SET utf8mb4 NOT NULL COMMENT '主键',
  `keyword` varchar(36) CHARACTER SET utf8mb4 NOT NULL COMMENT '关键字',
  `use_num` int DEFAULT NULL COMMENT '搜索次数',
  `deleted` tinyint(1) DEFAULT '0' COMMENT '是否删除',
  PRIMARY KEY (`id`) USING BTREE
)  COMMENT='开发表-页面新建的搜索记录';

-- 以下是来自 version_64_1.sql 的内容 --

CREATE TABLE `dev_seats` (
     `id` varchar(100) NOT NULL,
     `node_id` varchar(100) DEFAULT NULL,
     `num` varchar(50) DEFAULT NULL,
     `type` varchar(50) DEFAULT NULL,
     `status` varchar(50) DEFAULT NULL,
     `user_id` varchar(50) DEFAULT NULL,
     `floor_id` varchar(50) DEFAULT NULL,
     `description` varchar(200) DEFAULT NULL,
     `when_created` varchar(100) DEFAULT NULL,
     `when_modified` varchar(100) DEFAULT NULL,
     `who_created` varchar(255) DEFAULT NULL,
     `who_modified` varchar(255) DEFAULT NULL,
     PRIMARY KEY (`id`)
);

-- `k-pine-closed`.dev_floors definition

CREATE TABLE `dev_floors` (
      `id` varchar(100) NOT NULL,
      `name` varchar(255) DEFAULT NULL,
      `type` varchar(50) DEFAULT NULL,
      `status` varchar(50) DEFAULT NULL,
      `description` varchar(200) DEFAULT NULL,
      `flow_id` varchar(100) DEFAULT NULL,
      `when_created` varchar(100) DEFAULT NULL,
      `when_modified` varchar(100) DEFAULT NULL,
      `who_created` varchar(255) DEFAULT NULL,
      `who_modified` varchar(255) DEFAULT NULL,
      PRIMARY KEY (`id`)
);
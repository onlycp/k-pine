CREATE TABLE if not exists  dev_sql_run
(
    id             VARCHAR(36)  NOT NULL COMMENT 'id',
    version        INTEGER          NOT NULL COMMENT '关联版本号',
    md5            VARCHAR(100) NULL DEFAULT NULL COMMENT 'md5',
    when_created   VARCHAR(30)  NULL DEFAULT NULL COMMENT '执行时间',
    execution_time INTEGER          NULL DEFAULT NULL COMMENT '执行时长（毫秒）',
    success        INTEGER      NOT NULL COMMENT '是否成功',
    PRIMARY KEY (id)
);


CREATE TABLE  dev_api
(
    id                 VARCHAR(36)  NOT NULL,
    api_argv_type      INTEGER          NULL DEFAULT NULL COMMENT '参数组装方式',
    api_code           VARCHAR(50)  NULL DEFAULT NULL COMMENT '接口编码',
    api_flow_id        VARCHAR(50)  NULL DEFAULT NULL COMMENT '流程ID',
    api_method         VARCHAR(36)  NULL DEFAULT 'get' COMMENT '请求方式',
    api_name           VARCHAR(50)  NULL DEFAULT NULL COMMENT '接口名称',
    api_note           CLOB         NULL COMMENT '接口描述',
    api_req_argv       CLOB         NULL COMMENT '请求参数',
    api_result_handler VARCHAR(128) NULL DEFAULT NULL COMMENT '结果处理类',
    api_rsp_argv       CLOB         NULL COMMENT '响应结果',
    api_tags           VARCHAR(128) NULL DEFAULT NULL COMMENT '标签',
    api_url            VARCHAR(128) NULL DEFAULT NULL COMMENT '接口路径',
    app_id             VARCHAR(36)  NULL DEFAULT NULL COMMENT '关联应用',
    application_id     VARCHAR(36)  NULL DEFAULT NULL COMMENT '归属应用ID',
    call_type          INTEGER          NULL DEFAULT NULL COMMENT '调试方式',
    when_created       VARCHAR(30)  NULL DEFAULT NULL COMMENT '创建时间',
    when_modified      VARCHAR(30)  NULL DEFAULT NULL COMMENT '修改时间',
    who_created        VARCHAR(36)  NULL DEFAULT NULL COMMENT '创建人',
    who_modified       VARCHAR(36)  NULL DEFAULT NULL COMMENT '修改人',
    PRIMARY KEY (id)
);


CREATE TABLE dev_application
(
    id              VARCHAR(36)   NOT NULL,
    app_public_type INTEGER        DEFAULT 0 COMMENT '应用开启类型： 0：普通应用，1：公共库应用，2：系统库应用',
    app_type        VARCHAR(100)  DEFAULT NULL COMMENT '应用类型：可多个，逗号分隔，0: PC Web应用，1：移动端Web应用，2： 小程序，3： APP',
    data_source     VARCHAR(1000) DEFAULT NULL COMMENT '数据源配置',
    default_path    VARCHAR(255)  DEFAULT NULL COMMENT '默认路径',
    deleted         SMALLINT       DEFAULT 0 COMMENT '是否已删除',
    description     VARCHAR(255)  DEFAULT NULL COMMENT '应用介绍',
    dev_status      SMALLINT       DEFAULT NULL COMMENT '开发状态，0: 新建1: 确认版本2: 有更新',
    enable_status   SMALLINT       DEFAULT NULL COMMENT '可用状态',
    faas_port       INTEGER        DEFAULT NULL COMMENT 'faas端口号',
    name            VARCHAR(100)  DEFAULT NULL COMMENT '应用名',
    pine_port       INTEGER        DEFAULT NULL COMMENT '青松端口号',
    short_name      VARCHAR(30)   DEFAULT NULL COMMENT '应用短英文名（用于数据库等前缀命名），- 必须唯一，- 必须为纯英文、小写字母命名- 用于创建业务目录、数据库，',
    system_logo     VARCHAR(255)  DEFAULT NULL COMMENT '应用图标',
    version         VARCHAR(50)   DEFAULT NULL COMMENT '当前发布版本',
    when_created    VARCHAR(30)   DEFAULT NULL COMMENT '创建时间',
    when_modified   VARCHAR(30)   DEFAULT NULL COMMENT '修改时间',
    who_created     VARCHAR(36)   DEFAULT NULL COMMENT '创建人',
    who_in_charge   VARCHAR(255)  DEFAULT NULL COMMENT '负责人',
    who_modified    VARCHAR(36)   DEFAULT NULL COMMENT '修改人',
    PRIMARY KEY (id)
);


CREATE TABLE  dev_application_version_history
(
    id           VARCHAR(36)  NOT NULL,
    app_id       VARCHAR(36)  NULL DEFAULT NULL COMMENT '应用ID',
    export_data  CLOB         NULL COMMENT '导出数据参数',
    file_name    VARCHAR(255) NULL DEFAULT NULL COMMENT '文件名',
    note         VARCHAR(255) NULL DEFAULT NULL COMMENT '备注',
    version      VARCHAR(50)  NULL DEFAULT NULL COMMENT '版本',
    when_created VARCHAR(30)  NULL DEFAULT NULL COMMENT '创建时间',
    who_created  VARCHAR(36)  NULL DEFAULT NULL COMMENT '创建人',
    PRIMARY KEY (id)
);


CREATE TABLE dev_document
(
    id           VARCHAR(36) NOT NULL,
    content      CLOB NULL,
    deleted      INT DEFAULT 0,
    name         VARCHAR(255) DEFAULT NULL,
    "ORDER"      INT DEFAULT NULL,
    parent_id    VARCHAR(255) DEFAULT NULL,
    path         VARCHAR(255) DEFAULT NULL,
    when_created DATE DEFAULT NULL,
    who_created  VARCHAR(255) DEFAULT NULL,
    PRIMARY KEY (id)
);



CREATE TABLE  dev_module
(
    id            VARCHAR(36)  NOT NULL,
    app_id        VARCHAR(36)  NULL DEFAULT NULL COMMENT '关联应用',
    has_path      INTEGER          NULL DEFAULT NULL COMMENT '是否有路径',
    is_sys        INTEGER          NULL DEFAULT NULL COMMENT '是否系统',
    name          VARCHAR(100) NULL DEFAULT NULL COMMENT '名称',
    parent_id     VARCHAR(36)  NULL DEFAULT NULL COMMENT '父节点',
    path          VARCHAR(255) NULL DEFAULT NULL COMMENT '路径',
    sort          INTEGER          NULL DEFAULT NULL COMMENT '排序',
    when_created  VARCHAR(50)  NULL DEFAULT NULL COMMENT '创建时间',
    when_modified VARCHAR(50)  NULL DEFAULT NULL COMMENT '修改时间',
    who_created   VARCHAR(36)  NULL DEFAULT NULL COMMENT '创建人',
    who_modified  VARCHAR(36)  NULL DEFAULT NULL COMMENT '修改人',
    PRIMARY KEY (id)
);


CREATE TABLE  dev_ota_channel
(
    id            VARCHAR(36)  NOT NULL,
    auth_token    VARCHAR(50)  NULL DEFAULT NULL COMMENT '安全令牌',
    channel_name  VARCHAR(50)  NULL DEFAULT NULL COMMENT '通道名称',
    channel_url   VARCHAR(100) NULL DEFAULT NULL COMMENT '服务器地址',
    master        INTEGER          NULL DEFAULT 0 COMMENT '是否主通道',
    note          VARCHAR(255) NULL DEFAULT NULL COMMENT '备注信息',
    sign_secret   VARCHAR(50)  NULL DEFAULT NULL COMMENT '签名密钥',
    when_created  VARCHAR(30)  NULL DEFAULT NULL COMMENT '创建时间',
    when_modified VARCHAR(30)  NULL DEFAULT NULL COMMENT '修改时间',
    who_created   VARCHAR(36)  NULL DEFAULT NULL COMMENT '创建人',
    who_modified  VARCHAR(36)  NULL DEFAULT NULL COMMENT '修改人员',
    PRIMARY KEY (id)
);


CREATE TABLE dev_page
(
    id             VARCHAR(36) NOT NULL,
    app_id         VARCHAR(36) DEFAULT NULL,
    app_type       VARCHAR(100) DEFAULT NULL,
    deleted        INTEGER DEFAULT 0,
    description    VARCHAR(255) DEFAULT NULL,
    dev_status     INTEGER DEFAULT NULL,
    enable_status  INTEGER DEFAULT NULL,
    login_required INTEGER DEFAULT NULL,
    name           VARCHAR(255) DEFAULT NULL,
    page_json      CLOB NULL,
    path           VARCHAR(255) DEFAULT NULL,
    when_created   VARCHAR(30) DEFAULT NULL,
    when_modified  VARCHAR(30) DEFAULT NULL,
    who_created    VARCHAR(36) DEFAULT NULL,
    who_modified   VARCHAR(36) DEFAULT NULL,
    PRIMARY KEY (id)
);



CREATE TABLE dev_page_history
(
    id               VARCHAR(36) NOT NULL,
    page_id          VARCHAR(36) DEFAULT NULL,
    page_json        CLOB NULL,
    version_tag      VARCHAR(50) DEFAULT NULL,
    version_tag_time VARCHAR(30) DEFAULT NULL,
    when_created     VARCHAR(30) DEFAULT NULL,
    who_created      VARCHAR(36) DEFAULT NULL,
    PRIMARY KEY (id)
);


CREATE TABLE  dev_pine_plugin
(
    id             VARCHAR(36) NOT NULL,
    app_id         VARCHAR(36) NULL DEFAULT NULL COMMENT '归属应用id',
    author         VARCHAR(50) NULL DEFAULT NULL COMMENT '插件作者',
    enable_status  INTEGER         NULL DEFAULT 0 COMMENT '是否启动',
    file_id        VARCHAR(36) NULL DEFAULT NULL COMMENT '文件id',
    note           CLOB        NULL COMMENT '说明',
    plugin_name    VARCHAR(50) NULL DEFAULT NULL COMMENT '插件名称',
    plugin_version VARCHAR(10) NULL DEFAULT NULL COMMENT '插件版本号',
    when_created   VARCHAR(36) NULL DEFAULT NULL COMMENT '创建时间',
    when_modified  VARCHAR(30) NULL DEFAULT NULL COMMENT '修改时间',
    who_created    VARCHAR(36) NULL DEFAULT NULL COMMENT '创建人',
    who_modified   VARCHAR(36) NULL DEFAULT NULL COMMENT '修改人员',
    PRIMARY KEY (id)
);


CREATE TABLE  dev_power_link
(
    id           VARCHAR(36) NOT NULL,
    power_id     VARCHAR(36) NULL DEFAULT NULL COMMENT '能力id',
    power_type   INTEGER         NULL DEFAULT NULL COMMENT '能力类型 1: 逻辑编排 2:函数 3:kutils 4:逻辑编排模板',
    tree_id      VARCHAR(36) NULL DEFAULT NULL COMMENT '能力树id',
    when_created VARCHAR(30) NULL DEFAULT NULL COMMENT '创建时间',
    who_created  VARCHAR(36) NULL DEFAULT NULL COMMENT '创建人',
    PRIMARY KEY (id)
);


CREATE TABLE  dev_power_tree
(
    id            VARCHAR(36)  NOT NULL,
    name          VARCHAR(128) NULL DEFAULT NULL COMMENT '名称',
    note          VARCHAR(255) NULL DEFAULT NULL COMMENT '说明',
    parent_id     VARCHAR(36)  NULL DEFAULT NULL COMMENT '父级id',
    path          CLOB         NULL COMMENT '树路径',
    when_created  VARCHAR(30)  NULL DEFAULT NULL COMMENT '创建时间',
    when_modified VARCHAR(30)  NULL DEFAULT NULL COMMENT '修改时间',
    who_created   VARCHAR(36)  NULL DEFAULT NULL COMMENT '创建人',
    who_modified  VARCHAR(36)  NULL DEFAULT NULL COMMENT '修改人',
    PRIMARY KEY (id)
);


CREATE TABLE dev_sql_script
(
    id          VARCHAR(36) NOT NULL,
    description VARCHAR(255) DEFAULT NULL,
    is_once     INTEGER DEFAULT 1,
    sql         CLOB NULL,
    version     INT DEFAULT NULL,
    PRIMARY KEY (id)
);



CREATE TABLE dev_team
(
    id            VARCHAR(36) NOT NULL,
    deleted       INTEGER DEFAULT 0,
    description   VARCHAR(255) DEFAULT NULL,
    name          VARCHAR(100) DEFAULT NULL,
    owner         VARCHAR(36) DEFAULT NULL,
    when_created  VARCHAR(30) DEFAULT NULL,
    when_modified VARCHAR(30) DEFAULT NULL,
    who_created   VARCHAR(36) DEFAULT NULL,
    who_modified  VARCHAR(36) DEFAULT NULL,
    PRIMARY KEY (id)
);



CREATE TABLE  dev_team_app
(
    id           VARCHAR(36) NOT NULL,
    app_id       VARCHAR(36) NULL DEFAULT NULL COMMENT '当类型为应用团队时，必须有应用APP_ID',
    team_id      VARCHAR(36) NULL DEFAULT NULL COMMENT '团队ID',
    team_type    INTEGER  NULL DEFAULT NULL COMMENT '团队类型（1：平台团队，2：应用团队）',
    when_created VARCHAR(30) NULL DEFAULT NULL COMMENT '创建时间',
    who_created  VARCHAR(36) NULL DEFAULT NULL COMMENT '创建人',
    PRIMARY KEY (id)
);

CREATE TABLE dev_team_member
(
    id           VARCHAR(36) NOT NULL,
    app_id       VARCHAR(255) DEFAULT NULL,
    is_owner     INT DEFAULT NULL,
    team_id      VARCHAR(36) DEFAULT NULL,
    team_role_id VARCHAR(36) DEFAULT NULL,
    user_id      VARCHAR(36) DEFAULT NULL,
    when_join    VARCHAR(20) DEFAULT NULL,
    who_invite   VARCHAR(36) DEFAULT NULL,
    PRIMARY KEY (id)
);


CREATE TABLE dev_topological
(
    id            VARCHAR(36) NOT NULL,
    app_id        VARCHAR(36) DEFAULT NULL,
    deleted       INT DEFAULT 0,
    description   VARCHAR(255) DEFAULT NULL,
    enable_status INT DEFAULT NULL,
    name          VARCHAR(255) DEFAULT NULL,
    page_json     CLOB,
    when_created  VARCHAR(30) DEFAULT NULL,
    when_modified VARCHAR(30) DEFAULT NULL,
    who_created   VARCHAR(36) DEFAULT NULL,
    who_modified  VARCHAR(36) DEFAULT NULL,
    PRIMARY KEY (id)
);



CREATE TABLE dev_view_model
(
    id            VARCHAR(36) NOT NULL,
    app_id        VARCHAR(36) DEFAULT NULL COMMENT '关联应用',
    deleted       INT DEFAULT 0 COMMENT '逻辑删除',
    name          VARCHAR(50) DEFAULT NULL COMMENT '名称',
    note          CLOB COMMENT '描述',
    tag           VARCHAR(100) DEFAULT NULL COMMENT '标签，多个用逗号分隔',
    when_created  VARCHAR(30) DEFAULT NULL COMMENT '创建时间',
    when_modified VARCHAR(30) DEFAULT NULL COMMENT '修改时间',
    who_created   VARCHAR(36) DEFAULT NULL COMMENT '创建人员',
    who_modified  VARCHAR(36) DEFAULT NULL COMMENT '修改人员',
    PRIMARY KEY (id)
);



CREATE TABLE dev_view_model_field
(
    id             VARCHAR(36) NOT NULL,
    app_id         VARCHAR(36) DEFAULT NULL COMMENT '关联应用',
    default_CLOB   VARCHAR(50) DEFAULT NULL COMMENT '默认显示',
    field          VARCHAR(50) DEFAULT NULL COMMENT '键名',
    format_pattern VARCHAR(50) DEFAULT NULL COMMENT '格式规则',
    format_type    VARCHAR(20) DEFAULT NULL COMMENT '格式类型',
    hidden         INT DEFAULT 0 COMMENT '是否隐藏',
    label          VARCHAR(50) DEFAULT NULL COMMENT '标签',
    order_num      INT DEFAULT 0 COMMENT '排序',
    type           VARCHAR(20) DEFAULT NULL COMMENT '数据类型',
    view_model_id  VARCHAR(36) DEFAULT NULL COMMENT '模型ID',
    when_created   VARCHAR(30) DEFAULT NULL COMMENT '创建时间',
    when_modified  VARCHAR(30) DEFAULT NULL COMMENT '修改时间',
    who_created    VARCHAR(36) DEFAULT NULL COMMENT '创建人员',
    who_modified   VARCHAR(36) DEFAULT NULL COMMENT '修改人员',
    PRIMARY KEY (id)
);



CREATE TABLE dev_view_model_flow
(
    id            VARCHAR(36) NOT NULL,
    app_id        VARCHAR(36) DEFAULT NULL COMMENT '关联应用',
    flow_id       VARCHAR(36) DEFAULT NULL COMMENT '流水ID',
    view_model_id VARCHAR(36) DEFAULT NULL COMMENT '模型ID',
    when_created  VARCHAR(30) DEFAULT NULL COMMENT '创建时间',
    when_modified VARCHAR(30) DEFAULT NULL COMMENT '修改时间',
    who_created   VARCHAR(36) DEFAULT NULL COMMENT '创建人员',
    who_modified  VARCHAR(36) DEFAULT NULL COMMENT '修改人员',
    PRIMARY KEY (id)
);

CREATE TABLE ext_plugin_interface
(
    id          VARCHAR(36) NOT NULL,
    content     CLOB DEFAULT NULL COMMENT '接口使用说明Demo',
    create_time VARCHAR(20) DEFAULT NULL,
    create_user VARCHAR(255) DEFAULT NULL,
    deleted     INTEGER DEFAULT 0 COMMENT '1:逻辑删',
    description VARCHAR(1024) DEFAULT NULL COMMENT '接口描述',
    name        VARCHAR(255) DEFAULT NULL COMMENT '接口名称',
    plugin_id   VARCHAR(255) DEFAULT NULL COMMENT '插件id',
    resp_type   VARCHAR(255) DEFAULT '' COMMENT '返回值类型',
    update_time VARCHAR(20) DEFAULT NULL,
    update_user VARCHAR(255) DEFAULT NULL,
    PRIMARY KEY (id)
);



CREATE TABLE ext_plugin_tree
(
    id          VARCHAR(36) NOT NULL,
    check_time  VARCHAR(20) DEFAULT NULL COMMENT '插件检测时间',
    clazz_name  VARCHAR(255) DEFAULT NULL COMMENT '调用插件方法名',
    create_time VARCHAR(20) DEFAULT NULL,
    create_user VARCHAR(255) DEFAULT NULL,
    description CLOB DEFAULT NULL COMMENT '描述',
    ext_name    VARCHAR(255) DEFAULT NULL COMMENT '插件名称',
    jar_name    VARCHAR(255) DEFAULT NULL COMMENT 'jar包名称',
    name        VARCHAR(255) DEFAULT NULL COMMENT '中文名',
    status      INTEGER DEFAULT 0 COMMENT '服务器是否存在该包，0:不存在;1:存在',
    type        INTEGER DEFAULT NULL COMMENT '1:一级节点；2:二级节点',
    update_time VARCHAR(20) DEFAULT NULL,
    update_user VARCHAR(255) DEFAULT NULL,
    PRIMARY KEY (id)
);



CREATE TABLE kfaas_lib
(
    id          VARCHAR(36) NOT NULL,
    create_time VARCHAR(20) DEFAULT NULL,
    create_user VARCHAR(255) DEFAULT NULL,
    jar_name    VARCHAR(255) DEFAULT NULL COMMENT 'jar包名称',
    status      INTEGER DEFAULT 0 COMMENT '服务器是否存在该包，0:不存在;1:存在',
    update_time VARCHAR(20) DEFAULT NULL,
    update_user VARCHAR(255) DEFAULT NULL,
    PRIMARY KEY (id)
);



CREATE TABLE number_regulation
(
    id    VARCHAR(36) NOT NULL,
    sort  VARCHAR(255) DEFAULT NULL COMMENT '排序',
    type  VARCHAR(255) DEFAULT NULL COMMENT '规则类型',
    value VARCHAR(255) DEFAULT NULL COMMENT '规则值',
    PRIMARY KEY (id)
);


CREATE TABLE number_serial
(
    id           VARCHAR(36) NOT NULL,
    create_time  VARCHAR(255) DEFAULT NULL COMMENT '创建时间',
    name         VARCHAR(255) DEFAULT NULL COMMENT '流水号名称',
    "NUMBER"       VARCHAR(255) DEFAULT NULL COMMENT '流水号编号',
    regulationId VARCHAR(64) DEFAULT NULL COMMENT '规则ID',
    remark       VARCHAR(255) DEFAULT NULL COMMENT '备注',
    start_value  VARCHAR(255) DEFAULT NULL COMMENT '开始值',
    step_value   VARCHAR(255) DEFAULT NULL COMMENT '步长值',
    update_time  VARCHAR(255) DEFAULT NULL COMMENT '更新时间',
    PRIMARY KEY (id)
);


CREATE TABLE open_account
(
    id            VARCHAR(36) NOT NULL,
    access_id     VARCHAR(36) DEFAULT NULL COMMENT '接入者ID',
    access_name   VARCHAR(128) DEFAULT NULL COMMENT '接入者名称',
    auth_params   CLOB DEFAULT NULL COMMENT '参数配置',
    auth_type     INTEGER DEFAULT NULL COMMENT '授权类型1：简单模式，即access_id为access_token, 此时token是固定的',
    invalid_date  VARCHAR(20) DEFAULT NULL COMMENT '失效日期',
    sign_key      VARCHAR(50) DEFAULT NULL COMMENT '签名密钥',
    status        INTEGER DEFAULT 1 COMMENT '是否启用',
    valid_date    VARCHAR(20) DEFAULT NULL COMMENT '生效日期',
    validate_sign INTEGER DEFAULT 0 COMMENT '是否验签',
    when_created  VARCHAR(30) DEFAULT NULL COMMENT '创建时间',
    when_modified VARCHAR(30) DEFAULT NULL COMMENT '修改时间',
    who_created   VARCHAR(36) DEFAULT NULL COMMENT '创建人',
    who_modified  VARCHAR(36) DEFAULT NULL COMMENT '修改人',
    PRIMARY KEY (id)
);

CREATE TABLE open_account_api
(
    id           VARCHAR(36) NOT NULL,
    account_id   VARCHAR(36) DEFAULT NULL COMMENT '账号id',
    api_id       VARCHAR(36) DEFAULT NULL COMMENT '接口id',
    when_created VARCHAR(30) DEFAULT NULL COMMENT '创建时间',
    who_created  VARCHAR(36) DEFAULT NULL COMMENT '创建人',
    PRIMARY KEY (id)
);

CREATE TABLE open_api_log
(
    id             VARCHAR(36) NOT NULL,
    access_id      VARCHAR(100) DEFAULT NULL COMMENT '接口入商名称',
    api_name       VARCHAR(100) DEFAULT NULL COMMENT '接口名称',
    error_message  VARCHAR(255) DEFAULT NULL COMMENT '错误信息',
    request_ip     VARCHAR(20) DEFAULT NULL COMMENT '请求IP',
    request_params CLOB DEFAULT NULL COMMENT '请求参数',
    request_time   VARCHAR(20) DEFAULT NULL COMMENT '请求时间',
    success        INTEGER DEFAULT NULL COMMENT '是否成功',
    use_time       INTEGER DEFAULT NULL COMMENT '响应时间(秒)',
    PRIMARY KEY (id)
);


CREATE TABLE rep_app
(
    id            VARCHAR(36) NOT NULL,
    app_name      VARCHAR(50) DEFAULT NULL COMMENT '报表名称',
    app_note      VARCHAR(256) DEFAULT NULL COMMENT '报表描述',
    when_created  VARCHAR(30) DEFAULT NULL COMMENT '创建时间',
    when_modified VARCHAR(30) DEFAULT NULL COMMENT '修改时间',
    who_created   VARCHAR(36) DEFAULT NULL COMMENT '创建人',
    who_modified  VARCHAR(36) DEFAULT NULL COMMENT '更新人',
    PRIMARY KEY (id)
);



CREATE TABLE rep_dataset
(
    id            VARCHAR(36) NOT NULL,
    column_def    CLOB COMMENT '列定义',
    ds_meta       CLOB COMMENT '配置信息',
    ds_name       VARCHAR(50) DEFAULT NULL COMMENT '数据集名称',
    ds_note       VARCHAR(255) DEFAULT NULL COMMENT '描述信息',
    ds_type       INTEGER DEFAULT NULL COMMENT '数据集类型 1：sql 2:json 3:excel',
    rep_app_id    VARCHAR(36) DEFAULT NULL COMMENT '报表应用id',
    when_created  VARCHAR(30) DEFAULT NULL COMMENT '创建时间',
    when_modified VARCHAR(30) DEFAULT NULL COMMENT '修改时间',
    who_created   VARCHAR(36) DEFAULT NULL COMMENT '创建人员',
    who_modified  VARCHAR(36) DEFAULT NULL COMMENT '修改人员',
    PRIMARY KEY (id)
);

CREATE TABLE sys_api
(
    id                 VARCHAR(36) NOT NULL,
    api_argv_type      INTEGER DEFAULT NULL COMMENT '参数组装方式',
    api_code           VARCHAR(50) DEFAULT NULL COMMENT '接口编码',
    api_flow_id        VARCHAR(50) DEFAULT NULL COMMENT '流程ID',
    api_method         VARCHAR(36) DEFAULT 'get' COMMENT '请求方式',
    api_name           VARCHAR(50) DEFAULT NULL COMMENT '接口名称',
    api_note           CLOB COMMENT '接口描述',
    api_req_argv       CLOB COMMENT '请求参数',
    api_result_handler VARCHAR(128) DEFAULT NULL COMMENT '结果处理类',
    api_rsp_argv       CLOB COMMENT '响应结果',
    api_tags           VARCHAR(128) DEFAULT NULL COMMENT '标签',
    api_url            VARCHAR(128) DEFAULT NULL COMMENT '接口路径',
    app_id             VARCHAR(36) DEFAULT NULL COMMENT '关联应用',
    call_type          INTEGER DEFAULT NULL COMMENT '调试方式',
    module_id          VARCHAR(36) DEFAULT NULL COMMENT '关联模块',
    when_created       VARCHAR(30) DEFAULT NULL COMMENT '创建时间',
    when_modified      VARCHAR(30) DEFAULT NULL COMMENT '修改时间',
    who_created        VARCHAR(36) DEFAULT NULL COMMENT '创建人',
    who_modified       VARCHAR(36) DEFAULT NULL COMMENT '修改人',
    PRIMARY KEY (id)
);



CREATE TABLE sys_auto_serial
(
    id          VARCHAR(36) NOT NULL,
    auto_num    INTEGER DEFAULT NULL COMMENT '当前编号',
    category    VARCHAR(100) DEFAULT NULL COMMENT '分类',
    create_time VARCHAR(20) DEFAULT NULL,
    create_user VARCHAR(50) DEFAULT NULL,
    key         VARCHAR(50) DEFAULT NULL COMMENT '计算方式key',
    locked      INTEGER DEFAULT 0 COMMENT '是否被锁 1：已锁 0：未锁',
    num_length  INTEGER DEFAULT NULL COMMENT '编号长度，不够前面补0',
    start_num   INTEGER DEFAULT NULL COMMENT '初始值',
    step        INTEGER DEFAULT NULL COMMENT '步长',
    tpl         VARCHAR(100) DEFAULT NULL COMMENT '模板',
    type        INTEGER DEFAULT NULL COMMENT '计算方式1: 按日 2：按月 3:按年',
    update_time VARCHAR(20) DEFAULT NULL,
    update_user VARCHAR(50) DEFAULT NULL,
    PRIMARY KEY (id)
);


CREATE TABLE sys_base
(
    id            VARCHAR(36) NOT NULL,
    app_id        VARCHAR(36) DEFAULT NULL COMMENT '关联应用',
    code          VARCHAR(50) DEFAULT NULL COMMENT '编码',
    is_test       INTEGER DEFAULT NULL,
    name          VARCHAR(50) DEFAULT NULL COMMENT '名称',
    note          CLOB COMMENT '备注',
    when_created  VARCHAR(30) DEFAULT NULL COMMENT '创建时间',
    when_modified VARCHAR(30) DEFAULT NULL COMMENT '修改时间',
    who_created   VARCHAR(36) DEFAULT NULL COMMENT '创建人员',
    who_modified  VARCHAR(36) DEFAULT NULL COMMENT '修改人员',
    PRIMARY KEY (id)
);



CREATE TABLE sys_config
(
    id            VARCHAR(36) NOT NULL,
    app_id        VARCHAR(36) DEFAULT NULL COMMENT '关联应用',
    code          VARCHAR(255) DEFAULT NULL COMMENT '参数键名',
    is_sys        INTEGER DEFAULT NULL COMMENT '是否系统内置',
    name          VARCHAR(255) DEFAULT NULL COMMENT '参数名称',
    note          VARCHAR(255) DEFAULT NULL COMMENT '备注',
    value         VARCHAR(255) DEFAULT NULL COMMENT '参数键值',
    value_type    INTEGER DEFAULT 0,
    when_created  VARCHAR(30) DEFAULT NULL COMMENT '创建时间',
    when_modified VARCHAR(30) DEFAULT NULL COMMENT '修改时间',
    who_created   VARCHAR(36) DEFAULT NULL COMMENT '创建人员',
    who_modified  VARCHAR(36) DEFAULT NULL COMMENT '修改人员',
    PRIMARY KEY (id)
);



CREATE TABLE sys_data_access
(
    id            VARCHAR(36) NOT NULL,
    app_id        VARCHAR(36) DEFAULT NULL COMMENT '关联应用',
    name          VARCHAR(50) DEFAULT NULL COMMENT '名称',
    note          CLOB DEFAULT NULL COMMENT '备注',
    status        INTEGER DEFAULT NULL COMMENT '启用状态',
    when_created  VARCHAR(30) DEFAULT NULL COMMENT '创建时间',
    when_modified VARCHAR(30) DEFAULT NULL COMMENT '修改时间',
    who_created   VARCHAR(36) DEFAULT NULL COMMENT '创建人员',
    who_modified  VARCHAR(36) DEFAULT NULL COMMENT '修改人员',
    PRIMARY KEY (id)
);


CREATE TABLE sys_data_access_resource
(
    id           VARCHAR(36) NOT NULL,
    access_id    VARCHAR(36) DEFAULT NULL COMMENT '编码',
    app_id       VARCHAR(36) DEFAULT NULL COMMENT '关联应用',
    data_id      VARCHAR(36) DEFAULT NULL COMMENT '名称',
    table_name   VARCHAR(50) DEFAULT NULL COMMENT '备注',
    when_created VARCHAR(30) DEFAULT NULL COMMENT '创建时间',
    who_created  VARCHAR(36) DEFAULT NULL COMMENT '创建人员',
    PRIMARY KEY (id)
);


CREATE TABLE sys_data_access_user
(
    id                 VARCHAR(36) NOT NULL,
    app_id             VARCHAR(36) DEFAULT NULL COMMENT '关联应用',
    sys_data_access_id VARCHAR(50) DEFAULT NULL COMMENT '数据访问组id',
    sys_user_id        VARCHAR(36) DEFAULT NULL COMMENT '用户id',
    when_created       VARCHAR(30) DEFAULT NULL COMMENT '创建时间',
    who_created        VARCHAR(36) DEFAULT NULL COMMENT '创建人员',
    PRIMARY KEY (id)
);


CREATE TABLE sys_data_resource
(
    id            VARCHAR(36) NOT NULL,
    app_id        VARCHAR(36) DEFAULT NULL COMMENT '关联应用',
    extra_sql     CLOB DEFAULT NULL COMMENT '权限附加SQL',
    is_only_leaf  INTEGER DEFAULT NULL COMMENT '是否只选树叶',
    is_tree       INTEGER DEFAULT NULL COMMENT '是否树形结构',
    label_field   VARCHAR(50) DEFAULT NULL COMMENT '标签列',
    name          VARCHAR(50) DEFAULT NULL COMMENT '名称',
    query_sql     CLOB DEFAULT NULL COMMENT '查询SQL',
    status        INTEGER DEFAULT NULL COMMENT '启用状态',
    table_name    VARCHAR(50) DEFAULT NULL COMMENT '表名',
    value_field   VARCHAR(50) DEFAULT NULL COMMENT '值列',
    when_created  VARCHAR(30) DEFAULT NULL COMMENT '创建时间',
    when_modified VARCHAR(30) DEFAULT NULL COMMENT '修改时间',
    who_created   VARCHAR(36) DEFAULT NULL COMMENT '创建人员',
    who_modified  VARCHAR(36) DEFAULT NULL COMMENT '修改人员',
    PRIMARY KEY (id)
);



CREATE TABLE sys_dict
(
    id            VARCHAR(36) NOT NULL,
    app_id        VARCHAR(36) DEFAULT NULL COMMENT '关联应用',
    code          VARCHAR(50) DEFAULT NULL COMMENT '编码',
    name          VARCHAR(50) DEFAULT NULL COMMENT '名称',
    note          CLOB DEFAULT NULL COMMENT '备注',
    when_created  VARCHAR(30) DEFAULT NULL COMMENT '创建时间',
    when_modified VARCHAR(30) DEFAULT NULL COMMENT '修改时间',
    who_created   VARCHAR(36) DEFAULT NULL COMMENT '创建人员',
    who_modified  VARCHAR(36) DEFAULT NULL COMMENT '修改人员',
    PRIMARY KEY (id)
);


CREATE TABLE sys_dict_item
(
    id            VARCHAR(36) NOT NULL,
    app_id        VARCHAR(36) DEFAULT NULL COMMENT '关联应用',
    code          VARCHAR(50) DEFAULT NULL COMMENT '编码',
    group_name    VARCHAR(50) DEFAULT NULL COMMENT '组名',
    name          VARCHAR(50) DEFAULT NULL COMMENT '名称',
    note          CLOB DEFAULT NULL COMMENT '备注',
    order_num     INTEGER DEFAULT NULL COMMENT '排序',
    sys_dict_id   VARCHAR(36) DEFAULT NULL COMMENT '字典id',
    value         VARCHAR(20) DEFAULT NULL COMMENT '值',
    when_created  VARCHAR(30) DEFAULT NULL COMMENT '创建时间',
    when_modified VARCHAR(30) DEFAULT NULL COMMENT '修改时间',
    who_created   VARCHAR(36) DEFAULT NULL COMMENT '创建人员',
    who_modified  VARCHAR(36) DEFAULT NULL COMMENT '修改人员',
    PRIMARY KEY (id)
);




CREATE TABLE sys_excel
(
    id            VARCHAR(36) NOT NULL,
    app_id        VARCHAR(36) DEFAULT NULL COMMENT '关联应用',
    data_from     INTEGER DEFAULT NULL COMMENT '1. 普通 2. Excel文件 3. 数据源',
    data_from_id  VARCHAR(36) DEFAULT NULL COMMENT '数据来源ID：如果data_from为2，则为fileId；如果为3，则为datasetId',
    data_json     CLOB DEFAULT NULL COMMENT '数据配置',
    name          VARCHAR(255) DEFAULT NULL COMMENT '名称',
    when_created  VARCHAR(30) DEFAULT NULL COMMENT '创建时间',
    when_modified VARCHAR(30) DEFAULT NULL COMMENT '修改时间',
    who_created   VARCHAR(36) DEFAULT NULL COMMENT '创建人',
    who_modified  VARCHAR(36) DEFAULT NULL COMMENT '修改人',
    PRIMARY KEY (id)
);

CREATE TABLE sys_file
(
    id                 VARCHAR(36) NOT NULL,
    app_id             VARCHAR(36) DEFAULT NULL COMMENT '关联应用',
    file_content       CLOB DEFAULT NULL COMMENT '文件内容，只有存数据库时才有值',
    file_ext           CLOB DEFAULT NULL COMMENT '文件扩展名',
    file_from          VARCHAR(50) DEFAULT NULL COMMENT '文件来源',
    file_md5           VARCHAR(40) DEFAULT NULL COMMENT '文件MD5值',
    file_name          VARCHAR(100) DEFAULT NULL COMMENT '文件名称',
    file_original_name VARCHAR(100) DEFAULT NULL COMMENT '原始文件名',
    file_path          VARCHAR(100) DEFAULT NULL COMMENT '存储路径',
    file_size          INTEGER DEFAULT NULL COMMENT '文件大小(Byte)',
    save_type          INTEGER DEFAULT NULL COMMENT '存储方式 0：数据库 1：本地磁盘，2：FAAS储存',
    when_created       VARCHAR(30) DEFAULT NULL COMMENT '创建时间',
    when_modified      VARCHAR(30) DEFAULT NULL COMMENT '修改时间',
    who_created        VARCHAR(36) DEFAULT NULL COMMENT '创建人员',
    who_modified       VARCHAR(36) DEFAULT NULL COMMENT '修改人员',
    PRIMARY KEY (id)
);



CREATE TABLE sys_hint_select
(
    id            VARCHAR(36) NOT NULL,
    code          VARCHAR(100) DEFAULT NULL COMMENT '简称',
    db_id         VARCHAR(100) DEFAULT NULL COMMENT '数据源',
    flow_id       VARCHAR(50) DEFAULT NULL COMMENT '逻辑编排,优化级最高',
    remark        CLOB DEFAULT NULL COMMENT '备注说明',
    select_fields CLOB DEFAULT NULL COMMENT '字段说明',
    select_sql    CLOB DEFAULT NULL COMMENT '下拉SQL',
    type          VARCHAR(50) DEFAULT NULL COMMENT '类型:0=普通下拉,1=树下拉',
    PRIMARY KEY (id)
);


CREATE TABLE sys_i18n
(
    id            VARCHAR(36) NOT NULL,
    app_id        VARCHAR(36) DEFAULT NULL COMMENT '归属应用ID',
    i18n_key      VARCHAR(255) DEFAULT NULL COMMENT '键名',
    message       CLOB DEFAULT NULL COMMENT '国际化配置信息，JSON保存',
    when_created  VARCHAR(30) DEFAULT NULL COMMENT '创建时间',
    when_modified VARCHAR(30) DEFAULT NULL COMMENT '修改时间',
    who_created   VARCHAR(36) DEFAULT NULL COMMENT '创建人员',
    who_modified  VARCHAR(36) DEFAULT NULL COMMENT '修改人员',
    PRIMARY KEY (id)
);



CREATE TABLE sys_logic_flow
(
    id                  VARCHAR(36) NOT NULL,
    app_id              VARCHAR(36) DEFAULT NULL COMMENT '事务开关（开：1，关：0）；早期用于保存’关联应用‘，是application_id的冗余。',
    application_id      VARCHAR(36) DEFAULT NULL COMMENT '应用ID',
    default_source_name VARCHAR(100) DEFAULT NULL COMMENT '默认数据源',
    flow_id             VARCHAR(36) DEFAULT NULL COMMENT '流程ID',
    in_argv             CLOB DEFAULT NULL COMMENT '输入参数',
    name                VARCHAR(255) DEFAULT NULL COMMENT '名称',
    note                VARCHAR(255) DEFAULT NULL COMMENT '备注',
    out_argv            CLOB DEFAULT NULL COMMENT '输出参数',
    sub_flow_ids        CLOB DEFAULT NULL COMMENT '子流程ID列表',
    tags                VARCHAR(255) DEFAULT NULL COMMENT '标签',
    when_created        VARCHAR(30) DEFAULT NULL COMMENT '创建时间',
    when_modified       VARCHAR(30) DEFAULT NULL COMMENT '修改时间',
    who_created         VARCHAR(36) DEFAULT NULL COMMENT '创建人员',
    who_modified        VARCHAR(36) DEFAULT NULL COMMENT '修改人员',
    PRIMARY KEY (id)
);



CREATE TABLE sys_logic_history
(
    id               VARCHAR(36) NOT NULL,
    flow_id          VARCHAR(36) DEFAULT NULL COMMENT '流程ID',
    flow_json        CLOB DEFAULT NULL COMMENT '流程JSON',
    version_tag      VARCHAR(50) DEFAULT NULL COMMENT '版本标签',
    version_tag_time VARCHAR(30) DEFAULT NULL COMMENT '版本时间',
    when_created     VARCHAR(30) DEFAULT NULL COMMENT '创建时间',
    who_created      VARCHAR(36) DEFAULT NULL COMMENT '创建人',
    PRIMARY KEY (id)
);



CREATE TABLE sys_logic_template
(
    id            VARCHAR(36) NOT NULL,
    app_id        VARCHAR(36) DEFAULT NULL COMMENT '关联应用',
    description   CLOB DEFAULT NULL COMMENT '简介',
    links         CLOB DEFAULT NULL COMMENT '连接列表',
    module_id     VARCHAR(36) DEFAULT NULL COMMENT '关联模块',
    name          VARCHAR(255) DEFAULT NULL COMMENT '名称',
    nodes         CLOB DEFAULT NULL COMMENT '节点列表',
    when_created  VARCHAR(50) DEFAULT NULL COMMENT '创建时间',
    when_modified VARCHAR(50) DEFAULT NULL COMMENT '修改时间',
    who_created   VARCHAR(36) DEFAULT NULL COMMENT '创建人',
    who_modified  VARCHAR(36) DEFAULT NULL COMMENT '修改人',
    PRIMARY KEY (id)
);



CREATE TABLE sys_login_log
(
    id               VARCHAR(36) NOT NULL,
    ip               VARCHAR(20) DEFAULT NULL COMMENT '来访IP',
    operate_time     VARCHAR(20) DEFAULT NULL COMMENT '访问时间',
    operator         VARCHAR(36) DEFAULT NULL COMMENT '访问人员',
    response_code    INTEGER DEFAULT NULL COMMENT '响应码',
    response_message VARCHAR(100) DEFAULT NULL COMMENT '响应消息',
    times            INTEGER DEFAULT NULL COMMENT '耗时(ms)',
    when_created     VARCHAR(30) DEFAULT NULL COMMENT '创建时间',
    PRIMARY KEY (id)
);


CREATE TABLE sys_menu
(
    id               VARCHAR(36) NOT NULL,
    api_codes        VARCHAR(255) DEFAULT NULL COMMENT '接口编码，多个用逗号分隔',
    app_id           VARCHAR(36) DEFAULT NULL COMMENT '所属应用ID',
    code             VARCHAR(50) DEFAULT NULL COMMENT '编码',
    component_path   VARCHAR(255) DEFAULT NULL COMMENT '组件路径',
    data_type        INTEGER DEFAULT NULL COMMENT '数据类型：0系统，1业务应用, 2开发平台',
    full_path        VARCHAR(255) DEFAULT NULL COMMENT '完整路径（根据层级结构、router_path拼接的完整路径）',
    icon             VARCHAR(50) DEFAULT NULL COMMENT '图标',
    is_dev           INTEGER DEFAULT 0 COMMENT '菜单是否为开发者模式菜单',
    is_hidden        INTEGER DEFAULT 0 COMMENT '是否隐藏',
    keep_alive       INTEGER DEFAULT NULL COMMENT '是否刷新 0: 否 1：是',
    main_mode        INTEGER DEFAULT NULL COMMENT '内容区显示样式：0 自动撑开，1 居中',
    menu_type        CHAR(1) DEFAULT '0' COMMENT '菜单类型（M目录 C菜单 F按钮）',
    name             VARCHAR(50) DEFAULT NULL COMMENT '名称',
    open_mode        INTEGER DEFAULT NULL COMMENT '打开方式 0:页签 1:新窗口',
    order_num        INTEGER DEFAULT 0 COMMENT '排序',
    page_id          VARCHAR(36) DEFAULT NULL COMMENT '关联页面',
    page_type        INTEGER DEFAULT NULL COMMENT '页面渲染方式：0 Vue， 1 JSON',
    parent_id        VARCHAR(36) DEFAULT NULL COMMENT '上级菜单ID',
    path             CLOB DEFAULT NULL COMMENT '菜单层级关系，自动生成',
    router_path      VARCHAR(255) DEFAULT NULL COMMENT '路由路径',
    sidebar_nav_mode INTEGER DEFAULT NULL COMMENT '侧边菜单显示模式：0 不显示，1 从一级菜单开始显示，2 从二级菜单开始显示, 3 显示二级以下菜单',
    status           INTEGER DEFAULT 1 COMMENT '可用状态',
    theme            VARCHAR(50) DEFAULT NULL COMMENT 'layout主题',
    top_nav_mode     INTEGER DEFAULT NULL COMMENT '顶部菜单显示模式：0 完全不显示, top_nav_mode1 不显示nav（有header，没有菜单），2 从一级菜单开始显示，3 从二级菜单开始显示',
    when_created     VARCHAR(30) DEFAULT NULL COMMENT '创建时间',
    when_modified    VARCHAR(30) DEFAULT NULL COMMENT '修改时间',
    who_created      VARCHAR(36) DEFAULT NULL COMMENT '创建人员',
    who_modified     VARCHAR(36) DEFAULT NULL COMMENT '修改人员',
    PRIMARY KEY (id)
);



CREATE TABLE sys_mq_channel
(
    id              VARCHAR(36) NOT NULL,
    batch_consumer  INTEGER DEFAULT NULL COMMENT '批量消费数',
    channel_name    VARCHAR(50) DEFAULT NULL COMMENT '通道名称',
    consumer_group  VARCHAR(50) DEFAULT NULL COMMENT '消费组名',
    consumer_thread INTEGER DEFAULT NULL COMMENT '消费线程数',
    enable          INTEGER DEFAULT NULL COMMENT '是否启用 0-否 1-是',
    message_name    VARCHAR(50) DEFAULT NULL COMMENT '消息处理逻辑编排',
    topic           VARCHAR(50) DEFAULT NULL COMMENT '主题',
    zk_address      VARCHAR(50) DEFAULT NULL COMMENT 'zk地址',
    PRIMARY KEY (id)
);


CREATE TABLE sys_notice
(
    id            VARCHAR(36)  NOT NULL,
    app_id        VARCHAR(36)  DEFAULT NULL COMMENT '关联应用',
    content       CLOB         DEFAULT NULL COMMENT '通知内容',
    deleted       INTEGER   DEFAULT NULL COMMENT '逻辑删除，0：未删除，1：已删除',
    status        INTEGER      DEFAULT 1 COMMENT '启用状态：0：待启用，1：已启用',
    title         VARCHAR(255) DEFAULT NULL COMMENT '通知标题',
    type          INTEGER      DEFAULT NULL COMMENT '通知类型，1：系统维护通知，2：公告',
    when_created  VARCHAR(30)  DEFAULT NULL COMMENT '创建时间',
    when_modified VARCHAR(30)  DEFAULT NULL COMMENT '更新时间',
    who_created   VARCHAR(36)  DEFAULT NULL COMMENT '创建人',
    who_modified  VARCHAR(36)  DEFAULT NULL COMMENT '更新人',
    PRIMARY KEY (id)
);


CREATE TABLE sys_notice_record
(
    id            VARCHAR(36)  NOT NULL,
    app_id        VARCHAR(36)  DEFAULT NULL COMMENT '关联应用',
    content       CLOB         DEFAULT NULL COMMENT '通知内容',
    from_who      VARCHAR(36)  DEFAULT NULL COMMENT '发送人',
    from_who_name VARCHAR(255) DEFAULT NULL COMMENT '发送人名称',
    is_read       INTEGER      DEFAULT 0 COMMENT '是否已读，0：未读，1：已读',
    notice_id     VARCHAR(36)  DEFAULT NULL COMMENT '关联通知',
    notice_time   VARCHAR(20)  DEFAULT NULL COMMENT '接收通知时间',
    read_time     VARCHAR(20)  DEFAULT NULL COMMENT '阅读通知时间',
    title         VARCHAR(255) DEFAULT NULL COMMENT '通知标题',
    to_who        VARCHAR(36)  DEFAULT NULL COMMENT '接收人',
    to_who_name   VARCHAR(255) DEFAULT NULL COMMENT '接收人名称',
    PRIMARY KEY (id)
);



CREATE TABLE sys_online_user
(
    id           VARCHAR(36)   NOT NULL,
    app_id       VARCHAR(36)   DEFAULT NULL COMMENT '关联应用',
    expire_time  VARCHAR(20)   DEFAULT NULL COMMENT '失效时间',
    login_ip     VARCHAR(20)   DEFAULT NULL COMMENT '登录ip',
    login_time   VARCHAR(20)   DEFAULT NULL COMMENT '登录时间',
    login_token  VARCHAR(1024) DEFAULT NULL COMMENT '令牌',
    user_id      VARCHAR(36)   DEFAULT NULL COMMENT '用户id',
    when_created VARCHAR(30)   DEFAULT NULL COMMENT '创建时间',
    PRIMARY KEY (id)
);


CREATE TABLE sys_operate_log
(
    id               VARCHAR(36)  NOT NULL,
    action           VARCHAR(255) DEFAULT NULL COMMENT '动作',
    app_id           VARCHAR(36)  DEFAULT NULL COMMENT '关联应用',
    ip               VARCHAR(20)  DEFAULT NULL COMMENT '来访IP',
    module           VARCHAR(100) DEFAULT NULL COMMENT '模块',
    operate_time     VARCHAR(20)  DEFAULT NULL COMMENT '访问时间',
    operator         VARCHAR(36)  DEFAULT NULL COMMENT '访问人员',
    request_body     CLOB         DEFAULT NULL COMMENT '请求内容体',
    response_code    INTEGER      DEFAULT NULL COMMENT '响应码',
    response_message VARCHAR(100) DEFAULT NULL COMMENT '响应消息',
    times            INTEGER      DEFAULT NULL COMMENT '耗时(ms)',
    url              VARCHAR(255) DEFAULT NULL COMMENT '路径',
    when_created     VARCHAR(30)  DEFAULT NULL COMMENT '创建时间',
    method           VARCHAR(255) DEFAULT NULL COMMENT '方法名称',
    request_method   VARCHAR(20)  DEFAULT NULL COMMENT '请求方式',
    response_body    CLOB         DEFAULT NULL COMMENT '响应内容体',
    PRIMARY KEY (id)
);



CREATE TABLE sys_role
(
    id            VARCHAR(36)  NOT NULL,
    app_id        VARCHAR(36)  DEFAULT NULL COMMENT '关联应用',
    code          VARCHAR(50)  DEFAULT NULL COMMENT '编码',
    name          VARCHAR(50)  DEFAULT NULL COMMENT '名称',
    note          CLOB         DEFAULT NULL COMMENT '备注',
    status        INTEGER      DEFAULT NULL COMMENT '是否有效 0:否 1：是',
    when_created  VARCHAR(30)  DEFAULT NULL COMMENT '创建时间',
    when_modified VARCHAR(30)  DEFAULT NULL COMMENT '修改时间',
    who_created   VARCHAR(36)  DEFAULT NULL COMMENT '创建人员',
    who_modified  VARCHAR(36)  DEFAULT NULL COMMENT '修改人员',
    PRIMARY KEY (id)
);


CREATE TABLE sys_role_menu
(
    id           VARCHAR(36)  NOT NULL,
    app_id       VARCHAR(36)  DEFAULT NULL COMMENT '关联应用',
    sys_menu_id  VARCHAR(36)  DEFAULT NULL COMMENT '名称',
    sys_role_id  VARCHAR(50)  DEFAULT NULL COMMENT '编码',
    when_created VARCHAR(30)  DEFAULT NULL COMMENT '创建时间',
    who_created  VARCHAR(36)  DEFAULT NULL COMMENT '创建人员',
    PRIMARY KEY (id)
);

CREATE TABLE sys_task
(
    id                  VARCHAR(36)  NOT NULL,
    app_id              VARCHAR(36)  DEFAULT NULL COMMENT '关联应用',
    application_id      VARCHAR(36)  DEFAULT NULL COMMENT '应用ID',
    class_name          VARCHAR(255) DEFAULT NULL COMMENT '类名',
    cron                VARCHAR(50)  DEFAULT NULL COMMENT '定时表达式',
    distributed         INTEGER      DEFAULT NULL COMMENT '是否分布式',
    enable              INTEGER      DEFAULT 1 COMMENT '启用状态',
    last_execute_msg    CLOB         DEFAULT NULL COMMENT '最后执行错误信息',
    last_execute_status INTEGER      DEFAULT NULL COMMENT '最后执行状态 1：成功 0：失败',
    last_execute_take   INTEGER      DEFAULT NULL COMMENT '上次执行消耗',
    last_execute_time   VARCHAR(20)  DEFAULT NULL COMMENT '上次执行时间',
    lock_for_least      INTEGER      DEFAULT 1 COMMENT '最少锁定时长(秒)',
    lock_for_most       INTEGER      DEFAULT 30 COMMENT '最多锁定时长(秒)',
    lock_for_time       VARCHAR(20)  DEFAULT NULL COMMENT '锁定时间',
    lock_status         INTEGER      DEFAULT 0 COMMENT '锁定状态',
    name                VARCHAR(100) DEFAULT NULL COMMENT '任务名称',
    note                CLOB         DEFAULT NULL COMMENT '备注',
    task_resource_id    VARCHAR(36)  DEFAULT NULL COMMENT '任务资源ID',
    task_type           INTEGER      DEFAULT 1 COMMENT '任务类型',
    when_created        VARCHAR(30)  DEFAULT NULL COMMENT '创建时间',
    when_modified       VARCHAR(30)  DEFAULT NULL COMMENT '修改时间',
    who_created         VARCHAR(36)  DEFAULT NULL COMMENT '创建人',
    who_modified        VARCHAR(36)  DEFAULT NULL COMMENT '修改人',
    PRIMARY KEY (id)
);

CREATE TABLE sys_unit
(
    id            VARCHAR(36)  NOT NULL,
    app_id        VARCHAR(36)  DEFAULT NULL COMMENT '关联应用',
    email         VARCHAR(50)  DEFAULT NULL COMMENT '电子邮箱',
    leader        VARCHAR(255) DEFAULT NULL COMMENT '负责人',
    mobile        VARCHAR(20)  DEFAULT NULL COMMENT '联系电话',
    name          VARCHAR(50)  DEFAULT NULL COMMENT '名称',
    note          CLOB         DEFAULT NULL COMMENT '备注',
    order_num     INTEGER      DEFAULT 0 COMMENT '排序 ',
    parent_id     VARCHAR(36)  DEFAULT NULL COMMENT '上级单位ID',
    path          CLOB         DEFAULT NULL COMMENT '层次关系，自动生成',
    status        INTEGER      DEFAULT 1 COMMENT '部门状态， 0：停用 1：正常',
    when_created  VARCHAR(30)  DEFAULT NULL COMMENT '创建时间',
    when_modified VARCHAR(30)  DEFAULT NULL COMMENT '修改时间',
    who_created   VARCHAR(36)  DEFAULT NULL COMMENT '创建人员',
    who_modified  VARCHAR(36)  DEFAULT NULL COMMENT '修改人员',
    PRIMARY KEY (id)
);



CREATE TABLE sys_user
(
    id            VARCHAR(36)  NOT NULL,
    app_id        VARCHAR(36)  DEFAULT NULL COMMENT '关联应用',
    avatar        VARCHAR(255) DEFAULT NULL,
    deleted       INTEGER      DEFAULT 0 COMMENT '逻辑删除 0:正常 1：删除',
    email         VARCHAR(50)  DEFAULT NULL COMMENT '电子邮箱',
    mobile        VARCHAR(20)  DEFAULT NULL COMMENT '手机号码',
    note          CLOB         DEFAULT NULL COMMENT '备注',
    password      VARCHAR(256) DEFAULT NULL COMMENT '密码',
    post          VARCHAR(50)  DEFAULT NULL COMMENT '岗位',
    real_name     VARCHAR(50)  DEFAULT NULL COMMENT '用户姓名',
    sex           INTEGER      DEFAULT NULL COMMENT '性别 0:男 1:女',
    status        INTEGER      DEFAULT 1 COMMENT '状态 1：正常 0：禁用',
    sys_unit_id   VARCHAR(36)  DEFAULT NULL COMMENT '归属部门',
    username      VARCHAR(50)  DEFAULT NULL COMMENT '用户名',
    when_created  VARCHAR(30)  DEFAULT NULL COMMENT '创建时间',
    when_modified VARCHAR(30)  DEFAULT NULL COMMENT '修改时间',
    who_created   VARCHAR(36)  DEFAULT NULL COMMENT '创建人员',
    who_modified  VARCHAR(36)  DEFAULT NULL COMMENT '修改人员',
    PRIMARY KEY (id)
);


CREATE TABLE sys_user_role
(
    id           VARCHAR(36) NOT NULL,
    app_id       VARCHAR(36) DEFAULT NULL COMMENT '关联应用',
    sys_role_id  VARCHAR(36) DEFAULT NULL COMMENT '编码',
    sys_user_id  VARCHAR(36) DEFAULT NULL COMMENT '名称',
    when_created VARCHAR(30) DEFAULT NULL COMMENT '创建时间',
    who_created  VARCHAR(36) DEFAULT NULL COMMENT '创建人员',
    PRIMARY KEY (id)
);

CREATE TABLE sys_user_unit
(
    id           VARCHAR(36) NOT NULL COMMENT '主键',
    sys_user_id  VARCHAR(36) NOT NULL COMMENT '用户ID',
    sys_unit_id  VARCHAR(36) NOT NULL COMMENT '部门ID',
    who_created  VARCHAR(36) NOT NULL COMMENT '创建人员',
    when_created VARCHAR(20) NOT NULL COMMENT '创建时间',
    app_id       VARCHAR(36) DEFAULT NULL COMMENT '关联应用',
    PRIMARY KEY (id)
);

CREATE TABLE sys_view_model
(
    id            VARCHAR(36) NOT NULL,
    deleted       INTEGER DEFAULT 0 COMMENT '逻辑删除',
    name          VARCHAR(50) DEFAULT NULL COMMENT '名称',
    note          CLOB COMMENT '描述',
    tag           VARCHAR(100) DEFAULT NULL COMMENT '标签，多个用逗号分隔',
    when_created  VARCHAR(30) DEFAULT NULL COMMENT '创建时间',
    when_modified VARCHAR(30) DEFAULT NULL COMMENT '修改时间',
    who_created   VARCHAR(36) DEFAULT NULL COMMENT '创建人员',
    who_modified  VARCHAR(36) DEFAULT NULL COMMENT '修改人员',
    PRIMARY KEY (id)
);

CREATE TABLE sys_view_model_field
(
    id             VARCHAR(36) NOT NULL,
    default_CLOB   VARCHAR(50) DEFAULT NULL COMMENT '默认显示',
    field          VARCHAR(50) DEFAULT NULL COMMENT '键名',
    format_pattern VARCHAR(50) DEFAULT NULL COMMENT '格式规则',
    format_type    VARCHAR(20) DEFAULT NULL COMMENT '格式类型',
    hidden         INTEGER DEFAULT 0 COMMENT '是否隐藏',
    label          VARCHAR(50) DEFAULT NULL COMMENT '标签',
    order_num      INTEGER DEFAULT 0 COMMENT '排序',
    type           VARCHAR(20) DEFAULT NULL COMMENT '数据类型',
    view_model_id  VARCHAR(36) DEFAULT NULL COMMENT '模型ID',
    when_created   VARCHAR(30) DEFAULT NULL COMMENT '创建时间',
    when_modified  VARCHAR(30) DEFAULT NULL COMMENT '修改时间',
    who_created    VARCHAR(36) DEFAULT NULL COMMENT '创建人员',
    who_modified   VARCHAR(36) DEFAULT NULL COMMENT '修改人员',
    PRIMARY KEY (id)
);

CREATE TABLE sys_view_model_flow
(
    id            VARCHAR(36) NOT NULL,
    flow_id       VARCHAR(36) DEFAULT NULL COMMENT '流水ID',
    view_model_id VARCHAR(36) DEFAULT NULL COMMENT '模型ID',
    when_created  VARCHAR(30) DEFAULT NULL COMMENT '创建时间',
    when_modified VARCHAR(30) DEFAULT NULL COMMENT '修改时间',
    who_created   VARCHAR(36) DEFAULT NULL COMMENT '创建人员',
    who_modified  VARCHAR(36) DEFAULT NULL COMMENT '修改人员',
    PRIMARY KEY (id)
);

CREATE TABLE wf_demo_leave
(
    id           VARCHAR(36) NOT NULL,
    day          INTEGER DEFAULT NULL COMMENT '请假天数',
    proc_inst_id VARCHAR(36) DEFAULT NULL COMMENT '实例id',
    reason       VARCHAR(255) DEFAULT NULL COMMENT '请假原因',
    PRIMARY KEY (id)
);

CREATE TABLE wf_ext_category
(
    id            VARCHAR(36) NOT NULL,
    category_name VARCHAR(50) DEFAULT NULL COMMENT '模块名称',
    order_num     INTEGER DEFAULT NULL COMMENT '排序',
    when_created  VARCHAR(30) DEFAULT NULL COMMENT '创建时间',
    when_modified VARCHAR(30) DEFAULT NULL COMMENT '修改时间',
    who_created   VARCHAR(36) DEFAULT NULL COMMENT '创建人',
    who_modified  VARCHAR(36) DEFAULT NULL COMMENT '修改人',
    PRIMARY KEY (id)
);

CREATE TABLE wf_ext_comment
(
    id           VARCHAR(36) NOT NULL,
    message      VARCHAR(255) DEFAULT NULL COMMENT '意见',
    proc_inst_id VARCHAR(36) DEFAULT NULL COMMENT '流程实例ID',
    task_id      VARCHAR(36) DEFAULT NULL COMMENT '任务ID',
    task_name    VARCHAR(50) DEFAULT NULL COMMENT '任务名称',
    type         VARCHAR(20) DEFAULT 'comment' COMMENT '类型',
    user_id      VARCHAR(36) DEFAULT NULL COMMENT '用户ID',
    when_created VARCHAR(30) DEFAULT NULL COMMENT '创建时间',
    PRIMARY KEY (id)
);

CREATE TABLE wf_ext_node_attribute
(
    id             VARCHAR(36) NOT NULL,
    actions        VARCHAR(100) DEFAULT NULL COMMENT '处理动作权限',
    exec_mode      VARCHAR(50) DEFAULT NULL COMMENT '执行方式',
    flow_id        VARCHAR(100) DEFAULT NULL COMMENT '流程ID',
    form_attribute CLOB COMMENT '表单属性',
    msg_send_rule  VARCHAR(100) DEFAULT NULL COMMENT '消息发送策略',
    next_user      VARCHAR(500) DEFAULT NULL COMMENT '下一步处理人',
    node_id        VARCHAR(100) DEFAULT NULL COMMENT '节点ID',
    node_type      VARCHAR(50) DEFAULT NULL COMMENT '节点类型',
    pass_ok        VARCHAR(50) DEFAULT NULL COMMENT '审核通过要求',
    person         VARCHAR(50) DEFAULT NULL COMMENT '审核通过要求-任几人',
    time_out       VARCHAR(50) DEFAULT NULL COMMENT '超时时间(分)',
    when_created   VARCHAR(50) DEFAULT NULL COMMENT '创建时间',
    when_modified  VARCHAR(50) DEFAULT NULL COMMENT '更新时间',
    who_created    VARCHAR(100) DEFAULT NULL COMMENT '创建人',
    who_modified   VARCHAR(100) DEFAULT NULL COMMENT '更新人',
    PRIMARY KEY (id)
);

CREATE TABLE wf_ext_procdef
(
    id                  VARCHAR(36) NOT NULL,
    category_id         VARCHAR(36) DEFAULT NULL COMMENT '分类ID',
    content             CLOB COMMENT '内容',
    deploy_md5          VARCHAR(255) DEFAULT NULL COMMENT '已发布版本MD5',
    deploy_status       INTEGER DEFAULT NULL COMMENT '部署状态 0:待发布 1:已发布',
    deploy_time         VARCHAR(20) DEFAULT NULL COMMENT '最新发布时间',
    form_key            VARCHAR(50) DEFAULT NULL COMMENT '表单key，一般是表名',
    form_page_id        VARCHAR(36) DEFAULT NULL COMMENT '表单页面ID',
    icon                VARCHAR(50) DEFAULT NULL COMMENT '图标',
    inst_desc           VARCHAR(255) DEFAULT NULL COMMENT '实例描述',
    order_num           INTEGER DEFAULT NULL COMMENT '排序',
    proc_definition_id  VARCHAR(36) DEFAULT NULL COMMENT '流程定义ID',
    proc_definition_key VARCHAR(100) DEFAULT NULL COMMENT '流程定义KEY',
    proc_name           VARCHAR(100) DEFAULT NULL COMMENT '流程定义名称',
    proc_version        INTEGER DEFAULT NULL COMMENT '版本号',
    when_created        VARCHAR(30) DEFAULT NULL COMMENT '创建时间',
    when_modified       VARCHAR(30) DEFAULT NULL COMMENT '修改时间',
    who_created         VARCHAR(36) DEFAULT NULL COMMENT '创建人',
    who_modified        VARCHAR(36) DEFAULT NULL COMMENT '修改人',
    work_num            VARCHAR(100) DEFAULT NULL COMMENT '工单编号',
    PRIMARY KEY (id)
);

CREATE TABLE wf_ext_procinst
(
    id           VARCHAR(36) NOT NULL,
    bill_code    VARCHAR(50) DEFAULT NULL COMMENT '工单号',
    bill_title   VARCHAR(100) DEFAULT NULL COMMENT '工单标题',
    form_data    CLOB COMMENT '表单数据',
    main_inst_id VARCHAR(50) DEFAULT NULL COMMENT '主流程实例ID',
    main_task_id VARCHAR(50) DEFAULT NULL COMMENT '主流程任务ID',
    proc_inst_id VARCHAR(36) DEFAULT NULL COMMENT '流程实例ID',
    starter      VARCHAR(36) DEFAULT NULL COMMENT '发起人ID',
    when_created VARCHAR(30) DEFAULT NULL COMMENT '创建时间',
    PRIMARY KEY (id)
);

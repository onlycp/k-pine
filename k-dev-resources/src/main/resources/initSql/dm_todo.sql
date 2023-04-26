ALTER TABLE sys_unit
    ADD required_unit int DEFAULT 0 NULL;

alter table sys_api
    modify api_tags VARCHAR(255) null;
alter table sys_logic_flow
    modify tags VARCHAR(255) null;
alter table dev_page
    modify tags VARCHAR(255) null;


CREATE TABLE IF NOT EXISTS sys_logic_flow_mock
(
    id            VARCHAR(36) NOT NULL,
    name          VARCHAR(90),
    flow_id       VARCHAR(36),
    depend_id     VARCHAR(36),
    request_argv  text,
    assert_expr   VARCHAR(900),
    enable_mock   INT,
    who_created   VARCHAR(36),
    when_created  VARCHAR(20),
    who_modified  VARCHAR(36),
    when_modified VARCHAR(20),
    PRIMARY KEY (id)
);

CREATE TABLE IF NOT EXISTS dev_page_template
(
    id            varchar(36) NOT NULL,
    when_created  varchar(20),
    when_modified varchar(20),
    who_created   varchar(36),
    who_modified  varchar(36),
    deleted       int DEFAULT 0,
    app_id        varchar(36),
    name          varchar(255),
    description   varchar(255),
    app_type      varchar(100),
    page_json CLOB,
    tags          varchar(255),
    module_id     varchar(36),
    PRIMARY KEY (id)
);

CREATE TABLE IF NOT EXISTS dev_table
(
    id            varchar(36),
    data_source   varchar(100),
    name          varchar(100),
    "comment"     varchar(255),
    when_created  varchar(100),
    when_modified varchar(100),
    who_created   varchar(100),
    who_modified  varchar(100),
    PRIMARY KEY (id)
);

CREATE TABLE IF NOT EXISTS dev_table_column
(
    id              varchar(36),
    table_id        varchar(36),
    name            varchar(100),
    "comment"       varchar(255),
    sort            int,
    type            varchar(50),
    length          varchar(36),
    decimal_point   varchar(100),
    is_primary      int,
    is_null         int,
    when_created    varchar(100),
    when_modified   varchar(100),
    who_created     varchar(100),
    who_modified    varchar(100),
    is_label_column int NOT NULL DEFAULT 0,
    PRIMARY KEY (id)
);


CREATE TABLE IF NOT EXISTS dev_table_update_log
(
    id              varchar(36),
    operate_type    int,
    table_name      varchar(100),
    column_name     varchar(100),
    version_tag     varchar(100),
    when_created    varchar(100),
    when_modified   varchar(100),
    who_created     varchar(100),
    who_modified    varchar(100),
    old_table_name  varchar(100),
    old_column_name varchar(100),
    type            varchar(36),
    length          varchar(36),
    decimal_point   int,
    is_primary      int,
    is_null         int,
    sort            varchar(36),
    "comment"       varchar(255),
    table_id        varchar(100),
    is_column_label int,
    PRIMARY KEY (id)
);

alter table sys_operate_log modify  operate_time varchar(20);
alter table dev_application modify  enable_status int NULL DEFAULT NULL;
alter table dev_application modify  dev_status int NULL DEFAULT NULL;
INSERT INTO DEV_FAAS_NODE (ID, NAME, TYPE_ID, TEMPLATE, ICON, PUB_STATUS, ORDER_NUM, WHEN_CREATED, WHO_CREATED, WHEN_MODIFIED, WHO_MODIFIED, CONFIG, CODE) VALUES ('2dc3aa0ca085438790ef0020d2df2cc7', '编辑', '107d4c31f6ce4cf69a467d1c2df3bc94', '
// 表名称
var tableName = `{{table}}`;
// 数据源名称
var sourceName = `{{sourceName}}`;
// ID列
var idCol = `{{idCol}}`;
// 调用通用编辑
const result = curdUpdate(sourceName, tableName, idCol, {})
// 写入上下文
setResult(''result'', result);', 'k-curd-update', 1, '2', '2023-01-30 15:13:40', '94123ca363dc4dfaa62a6bb5dcd3bf50', '2023-02-09 09:49:11', '94123ca363dc4dfaa62a6bb5dcd3bf50', '{
  "form": [
    {
      "label": "数据源",
      "field": "sourceName",
      "editor": "select",
      "source": "flow:d4ae54a989f04dba9e0af1b9815161f2"
    },
    {
      "label": "表名",
      "field": "table",
      "editor": "input"
    },
    {
      "label": "ID列",
      "field": "idCol",
      "editor": "input",
      "default": "id"
    }
  ]
}', 'curd_update');
INSERT INTO DEV_FAAS_NODE (ID, NAME, TYPE_ID, TEMPLATE, ICON, PUB_STATUS, ORDER_NUM, WHEN_CREATED, WHO_CREATED, WHEN_MODIFIED, WHO_MODIFIED, CONFIG, CODE) VALUES ('5d2e5853dab241ac931c7d26eaa9fb48', '新增', '107d4c31f6ce4cf69a467d1c2df3bc94', '// 表名称
var tableName = `{{table}}`;
// 数据源名称
var sourceName = `{{sourceName}}`;
// 调用通用新增
const result = curdAdd(sourceName, tableName, {});
// 写入上下文
setResult(''result'', result);', 'k-curd-add', 1, '1', '2023-01-29 10:40:36', '94123ca363dc4dfaa62a6bb5dcd3bf50', '2023-02-09 09:49:40', '94123ca363dc4dfaa62a6bb5dcd3bf50', '{
  "form": [
    {
      "label": "数据源",
      "field": "sourceName",
      "editor": "select",
      "source": "flow:d4ae54a989f04dba9e0af1b9815161f2"
    },
    {
      "label": "表名",
      "field": "table",
      "editor": "input"
    }
  ]
}', 'curd_add');
INSERT INTO DEV_FAAS_NODE (ID, NAME, TYPE_ID, TEMPLATE, ICON, PUB_STATUS, ORDER_NUM, WHEN_CREATED, WHO_CREATED, WHEN_MODIFIED, WHO_MODIFIED, CONFIG, CODE) VALUES ('640aee70854247d8a20216af547baa33', '下载任务', '1418ab1b9dc24275bbf951d90a512185', '
// 数据源名称
var sourceName = `{{sourceName}}`;
// 值属性
var contennt = `{{content}}`;
// 文件名称
var contennt = `{{content}}`;
// 调用通用编辑
const result = sysOfflineDownload(sourceName, contennt, fileName, {})
// 写入上下文
setResult(''result'', JSON.stringify(result))', 'download', 1, '1', '2023-02-27 15:51:04', '94123ca363dc4dfaa62a6bb5dcd3bf50', '2023-02-27 16:25:28', '94123ca363dc4dfaa62a6bb5dcd3bf50', '{
  "form": [
    {
      "label": "数据源",
      "field": "sourceName",
      "editor": "select",
      "source": "flow:d4ae54a989f04dba9e0af1b9815161f2"
    },
    {
      "label": "文件名称",
      "field": "fileName",
      "editor": "input"
    },
        {
          "label": "任务名称",
          "field": "taskName",
          "editor": "input"
        },
    {
      "label": "查询语句",
      "field": "content",
      "editor": "code"
    }
  ]
}', 'offline-downlad');
INSERT INTO DEV_FAAS_NODE (ID, NAME, TYPE_ID, TEMPLATE, ICON, PUB_STATUS, ORDER_NUM, WHEN_CREATED, WHO_CREATED, WHEN_MODIFIED, WHO_MODIFIED, CONFIG, CODE) VALUES ('6cf81a5268444bd6b28ace29995df12a', '查询单条数据', '107d4c31f6ce4cf69a467d1c2df3bc94', '
// 数据源名称
var sourceName = `{{sourceName}}`;
// 值属性
var contennt = `{{content}}`;
// 调用通用编辑
const result = curdGetOne(sourceName, contennt, {})
// 写入上下文
setResult(''result'', JSON.stringify(result))', 'k-curd-one', 1, '5', '2023-02-08 17:56:09', '94123ca363dc4dfaa62a6bb5dcd3bf50', '2023-02-18 11:56:07', '94123ca363dc4dfaa62a6bb5dcd3bf50', '{
    "form": [
        {
            "label": "数据源",
            "field": "sourceName",
            "editor": "select",
            "source": "flow:d4ae54a989f04dba9e0af1b9815161f2"
        },
        {
            "label": "查询语句",
            "field": "content",
            "editor": "code"
        }
    ]
}', 'curd-one');
INSERT INTO DEV_FAAS_NODE (ID, NAME, TYPE_ID, TEMPLATE, ICON, PUB_STATUS, ORDER_NUM, WHEN_CREATED, WHO_CREATED, WHEN_MODIFIED, WHO_MODIFIED, CONFIG, CODE) VALUES ('713b6a1a35d74371aea23d54140566d4', '删除', '107d4c31f6ce4cf69a467d1c2df3bc94', '
// 表名称
var tableName = `{{table}}`;
// 数据源名称
var sourceName = `{{sourceName}}`;
// ID列
var idCol = `{{idCol}}`;
// 值属性
var valueField = `{{valueField}}`;
// 调用通用编辑
const result = curdDelete(sourceName, tableName, idCol, valueField, {})
// 写入上下文
setResult(''result'', result);', 'k-curd-delete', 1, '3', '2023-02-07 10:39:50', '94123ca363dc4dfaa62a6bb5dcd3bf50', '2023-02-09 09:47:01', '94123ca363dc4dfaa62a6bb5dcd3bf50', '{
  "form": [
    {
      "label": "数据源",
      "field": "sourceName",
      "editor": "select",
      "source": "flow:d4ae54a989f04dba9e0af1b9815161f2"
    },
    {
      "label": "表名",
      "field": "table",
      "editor": "input"
    },
    {
      "label": "ID列",
      "field": "idCol",
      "editor": "input",
      "default": "id"
    },
      {
        "label": "值属性名",
        "field": "valueField",
        "editor": "input",
        "default": "id"
      }
  ]
}', 'curd_delete');
INSERT INTO DEV_FAAS_NODE (ID, NAME, TYPE_ID, TEMPLATE, ICON, PUB_STATUS, ORDER_NUM, WHEN_CREATED, WHO_CREATED, WHEN_MODIFIED, WHO_MODIFIED, CONFIG, CODE) VALUES ('c0611398a04e40b7bb51651186214c97', '分页查询', '107d4c31f6ce4cf69a467d1c2df3bc94', '
// 数据源名称
var sourceName = `{{sourceName}}`;
// 值属性
var contennt = `{{content}}`;
// 调用通用编辑
const result = curdPage(sourceName, contennt, {})
// 写入上下文
setResult(''result'', JSON.stringify(result))', 'k-curd-page', 1, '4', '2023-02-07 17:51:48', '94123ca363dc4dfaa62a6bb5dcd3bf50', '2023-02-09 09:46:39', '94123ca363dc4dfaa62a6bb5dcd3bf50', '{
  "form": [
    {
      "label": "数据源",
      "field": "sourceName",
      "editor": "select",
      "source": "flow:d4ae54a989f04dba9e0af1b9815161f2"
    },
    {
      "label": "查询语句",
      "field": "content",
      "editor": "code"
    }
  ]
}', 'curd_page');

INSERT INTO DEV_FAAS_NODE_TYPE (ID, NAME, PUB_STATUS, ICON, WHEN_CREATED, WHO_CREATED, WHEN_MODIFIED, WHO_MODIFIED) VALUES ('107d4c31f6ce4cf69a467d1c2df3bc94', '关系数据库', 1, 'database', '2023-01-28 16:54:28', '94123ca363dc4dfaa62a6bb5dcd3bf50', '2023-01-28 16:54:28', '94123ca363dc4dfaa62a6bb5dcd3bf50');
INSERT INTO DEV_FAAS_NODE_TYPE (ID, NAME, PUB_STATUS, ICON, WHEN_CREATED, WHO_CREATED, WHEN_MODIFIED, WHO_MODIFIED) VALUES ('1418ab1b9dc24275bbf951d90a512185', '系统工具', 1, 'tools', '2023-02-27 15:48:57', '94123ca363dc4dfaa62a6bb5dcd3bf50', '2023-02-27 15:48:57', '94123ca363dc4dfaa62a6bb5dcd3bf50');
INSERT INTO DEV_FAAS_NODE_TYPE (ID, NAME, PUB_STATUS, ICON, WHEN_CREATED, WHO_CREATED, WHEN_MODIFIED, WHO_MODIFIED) VALUES ('2e512cbbfede45218dd6ab3f1c2a72e2', 'NoSql', 1, 'NoSql', '2023-01-28 16:57:47', '94123ca363dc4dfaa62a6bb5dcd3bf50', '2023-01-28 16:58:30', '94123ca363dc4dfaa62a6bb5dcd3bf50');
INSERT INTO DEV_FAAS_NODE_TYPE (ID, NAME, PUB_STATUS, ICON, WHEN_CREATED, WHO_CREATED, WHEN_MODIFIED, WHO_MODIFIED) VALUES ('8fc3ea0e4c884bf4a5e5e5bfa3a79c88', '消息队列', 1, 'MQ', '2023-01-28 16:58:48', '94123ca363dc4dfaa62a6bb5dcd3bf50', '2023-01-28 16:58:48', '94123ca363dc4dfaa62a6bb5dcd3bf50');
INSERT INTO DEV_FAAS_NODE_TYPE (ID, NAME, PUB_STATUS, ICON, WHEN_CREATED, WHO_CREATED, WHEN_MODIFIED, WHO_MODIFIED) VALUES ('c94977f8e74240d8927f9fde864be80b', '脚本语言', 1, 'SCRIPT', '2023-01-28 16:59:13', '94123ca363dc4dfaa62a6bb5dcd3bf50', '2023-01-28 16:59:13', '94123ca363dc4dfaa62a6bb5dcd3bf50');


create table if not exists sf_ext_form
(
    id               varchar(50)  not null
        primary key,
    who_created      varchar(50)  null,
    when_created     varchar(50)  null,
    who_modified     varchar(50)  null,
    when_modified    varchar(50)  null,
    name             varchar(100) null,
    type             varchar(50)  null,
    group_name       varchar(500) null,
    field_properties CLOB     null,
    list_button      CLOB     null,
    page_path        varchar(500) null,
    db_name          varchar(100) null,
    tab_name         varchar(500) null,
    is_app           varchar(50)  null,
    app_id           varchar(50)  null,
    page_id          varchar(50)  null,
    description      text         null
);

create table if not exists rep_app
(
    id            varchar(32)  not null
        primary key,
    app_name      varchar(50)  null,
    app_note      varchar(256) null,
    who_created   varchar(32)  null,
    when_created  varchar(20)  null,
    who_modified  varchar(32)  null,
    when_modified varchar(20)  null
);

-- auto-generated definition
create table rep_dataset
(
    id            varchar(32)  not null
        primary key,
    ds_name       varchar(50)  null,
    rep_app_id    varchar(32)  null,
    ds_meta       text         null,
    ds_type       int          null,
    ds_note       varchar(255) null,
    when_created  varchar(20)  null,
    who_modified  varchar(32)  null,
    when_modified varchar(20)  null,
    who_created   varchar(32)  null,
    column_def    text         null
);

create table if not exists rep_dataset_config
(
    id                  varchar(32)  not null,
    select_table        varchar(100) null ,
    conditions_assembly text         null ,
    field_alias         text         null,
    condition_group     text         null ,
    when_create         varchar(100) null,
    who_create          varchar(100) null ,
    when_modified       varchar(100) null ,
    who_modified        varchar(100) null ,
    rep_dataset_id      varchar(32)  null,
    source_id           varchar(32)  null
);

-- auto-generated definition
create table if not exists wf_ext_category
(
    id            varchar(32) not null
        primary key,
    category_name varchar(50) null ,
    order_num     int      null ,
    who_created   varchar(32) null ,
    when_created  varchar(20) null ,
    who_modified  varchar(32) null ,
    when_modified varchar(20) null
);

-- auto-generated definition
create table if not exists wf_ext_comment
(
    id             varchar(32)                   not null
        primary key,
    task_name      varchar(50)                   null,
    proc_inst_id   varchar(32)                   null ,
    task_id        varchar(32)                   null ,
    type           varchar(20) default 'comment' null,
    user_id        varchar(32)                   null ,
    message        varchar(255)                  null,
    when_created   varchar(20)                   null ,
    parent_inst_id varchar(50)                   null
);


-- auto-generated definition
create table if not exists wf_ext_node_attribute
(
    id             varchar(100) not null
        primary key,
    node_type      varchar(50)  null,
    next_user      varchar(500) null,
    msg_send_rule  varchar(100) null,
    time_out       varchar(50)  null,
    actions        varchar(100) null,
    when_created   varchar(50)  null,
    who_created    varchar(100) null,
    when_modified  varchar(50)  null,
    who_modified   varchar(100) null,
    flow_id        varchar(100) null,
    node_id        varchar(100) null,
    form_attribute text         null,
    exec_mode      varchar(50)  null,
    pass_ok        varchar(50)  null,
    person         varchar(50)  null ,
    back_node      varchar(500) null,
    name           varchar(200) null
);

-- auto-generated definition
create table if not exists wf_ext_node_define
(
    id            varchar(50)  null,
    who_created   varchar(50)  null,
    when_created  varchar(50)  null,
    who_modified  varchar(50)  null,
    when_modified varchar(50)  null,
    name          varchar(200) null,
    group_name    varchar(200) null,
    type          varchar(200) null,
    start_icon    text         null,
    show_icon     text         null,
    node_json     varchar(200) null,
    is_extend     varchar(200) null,
    status        varchar(200) null
);

-- auto-generated definition
create table if not exists wf_ext_procdef
(
    id                  varchar(50)  not null
        primary key,
    proc_definition_key varchar(100) null,
    proc_name           varchar(100) null,
    category_id         varchar(32)  null,
    proc_version        int      null,
    deploy_md5          varchar(255) null,
    icon                varchar(50)  null,
    content             clob     null,
    deploy_status       int      null,
    deploy_time         varchar(20)  null,
    order_num           int       null,
    who_created         varchar(32)  null,
    when_created        varchar(20)  null,
    who_modified        varchar(32)  null,
    when_modified       varchar(20)  null,
    proc_definition_id  varchar(32)  null,
    form_page_id        varchar(32)  null,
    form_key            varchar(50)  null,
    work_num            varchar(100) null,
    inst_desc           varchar(255) null,
    page_form           clob     null
);

-- auto-generated definition
create table if not exists wf_ext_procinst
(
    id           varchar(32)  not null
        primary key,
    proc_inst_id varchar(32)  null ,
    starter      varchar(32)  null ,
    bill_code    varchar(50)  null ,
    bill_title   varchar(100) null ,
    when_created varchar(20)  null ,
    form_data    text         null ,
    main_inst_id varchar(50)  null ,
    main_task_id varchar(50)  null
);

-- auto-generated definition
create table sys_excel
(
    id            varchar(255) not null
        primary key,
    name          varchar(255) null,
    data_json     clob     null,
    data_from     int          null ,
    when_created  varchar(30)  null ,
    when_modified varchar(30)  null,
    who_created   varchar(36)  null ,
    who_modified  varchar(36)  null ,
    app_id        varchar(36)  null ,
    data_from_id  varchar(36)  null
);

-- auto-generated definition
create table dev_application_version_history
(
    id           varchar(36)  not null
        primary key,
    when_created timestamp    null,
    who_created  varchar(36)  null,
    app_id       varchar(36)  null,
    version      varchar(50)  null,
    file_name    varchar(255) null,
    note         varchar(255) null,
    export_data  text         null
);






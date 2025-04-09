ALTER TABLE sys_unit
    ADD required_unit int DEFAULT 0 NULL;

alter table sys_api
    alter column api_tags type VARCHAR(255) ;
alter table sys_logic_flow
    alter column tags type VARCHAR(255) ;
alter table dev_page
    alter column tags  type VARCHAR(255) ;


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

alter table sys_operate_log alter column operate_time type varchar(20);
alter table dev_application alter column enable_status type int;
alter table dev_application alter column dev_status type int;

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

INSERT INTO DEV_MODULE (ID, NAME, PATH, HAS_PATH, PARENT_ID, SORT, WHEN_CREATED, WHO_CREATED, WHEN_MODIFIED, WHO_MODIFIED, IS_SYS, APP_ID) VALUES ('0913bc0b384c44d99e384b992cb7fe40', '开发平台', '/dev', 1, null, 1, '2023-03-29 10:43:48', '7aed8c297a6940f681c26eb6ab68893d', '2023-03-30 09:55:00', '7aed8c297a6940f681c26eb6ab68893d', 1, '064b3b44b85a45fe87fcce88d72b2519');
INSERT INTO DEV_MODULE (ID, NAME, PATH, HAS_PATH, PARENT_ID, SORT, WHEN_CREATED, WHO_CREATED, WHEN_MODIFIED, WHO_MODIFIED, IS_SYS, APP_ID) VALUES ('09e4ec197da14de3844b6b04c4fa5ee9', '开发平台管理', null, 0, null, 3, '2023-03-29 10:48:15', '7aed8c297a6940f681c26eb6ab68893d', '2023-03-29 10:48:15', '7aed8c297a6940f681c26eb6ab68893d', 1, '064b3b44b85a45fe87fcce88d72b2519');
INSERT INTO DEV_MODULE (ID, NAME, PATH, HAS_PATH, PARENT_ID, SORT, WHEN_CREATED, WHO_CREATED, WHEN_MODIFIED, WHO_MODIFIED, IS_SYS, APP_ID) VALUES ('1e329b86988b4dd79b49887b774b0879', '示例', null, 0, null, 18, '2023-03-29 16:28:22', '7aed8c297a6940f681c26eb6ab68893d', '2023-03-29 16:28:22', '7aed8c297a6940f681c26eb6ab68893d', 1, '064b3b44b85a45fe87fcce88d72b2519');
INSERT INTO DEV_MODULE (ID, NAME, PATH, HAS_PATH, PARENT_ID, SORT, WHEN_CREATED, WHO_CREATED, WHEN_MODIFIED, WHO_MODIFIED, IS_SYS, APP_ID) VALUES ('5a10d15671704b6cbc01cc3a8bed365a', '公共库', null, 0, null, 4, '2022-05-31 15:18:08', '7aed8c297a6940f681c26eb6ab68893d', '2022-05-31 15:24:40', '7aed8c297a6940f681c26eb6ab68893d', 1, '064b3b44b85a45fe87fcce88d72b2519');
INSERT INTO DEV_MODULE (ID, NAME, PATH, HAS_PATH, PARENT_ID, SORT, WHEN_CREATED, WHO_CREATED, WHEN_MODIFIED, WHO_MODIFIED, IS_SYS, APP_ID) VALUES ('90f2c2ea0f9942d181388e24fd6ee936', 'v3开发', '/dev/v3', 1, '0913bc0b384c44d99e384b992cb7fe40', 1, '2023-03-29 17:37:35', '7aed8c297a6940f681c26eb6ab68893d', '2023-03-30 09:55:12', '7aed8c297a6940f681c26eb6ab68893d', 1, '064b3b44b85a45fe87fcce88d72b2519');
INSERT INTO DEV_MODULE (ID, NAME, PATH, HAS_PATH, PARENT_ID, SORT, WHEN_CREATED, WHO_CREATED, WHEN_MODIFIED, WHO_MODIFIED, IS_SYS, APP_ID) VALUES ('9bbe33574d0547e78f72f5982bea26cd', '子页面', null, 0, '0913bc0b384c44d99e384b992cb7fe40', 0, '2023-03-29 10:46:23', '7aed8c297a6940f681c26eb6ab68893d', '2023-03-30 09:55:08', '7aed8c297a6940f681c26eb6ab68893d', 1, '064b3b44b85a45fe87fcce88d72b2519');
INSERT INTO DEV_MODULE (ID, NAME, PATH, HAS_PATH, PARENT_ID, SORT, WHEN_CREATED, WHO_CREATED, WHEN_MODIFIED, WHO_MODIFIED, IS_SYS, APP_ID) VALUES ('d4820db91eab4cdc9c82703cf1d4df83', '流程关联表单页', null, 0, '1e329b86988b4dd79b49887b774b0879', 0, '2023-03-29 16:28:46', '7aed8c297a6940f681c26eb6ab68893d', '2023-03-29 16:28:46', '7aed8c297a6940f681c26eb6ab68893d', 0, '064b3b44b85a45fe87fcce88d72b2519');
INSERT INTO DEV_MODULE (ID, NAME, PATH, HAS_PATH, PARENT_ID, SORT, WHEN_CREATED, WHO_CREATED, WHEN_MODIFIED, WHO_MODIFIED, IS_SYS, APP_ID) VALUES ('e0047b3716fa48789d8d2377b1d23195', '系统配置', null, 0, null, 2, '2023-03-29 09:27:04', '7aed8c297a6940f681c26eb6ab68893d', '2023-03-29 09:27:04', '7aed8c297a6940f681c26eb6ab68893d', 1, '064b3b44b85a45fe87fcce88d72b2519');
INSERT INTO DEV_MODULE (ID, NAME, PATH, HAS_PATH, PARENT_ID, SORT, WHEN_CREATED, WHO_CREATED, WHEN_MODIFIED, WHO_MODIFIED, IS_SYS, APP_ID) VALUES ('fbe9d1e36a37423aa9ab4711c569093f', '基础功能', null, 0, null, 2, '2023-03-29 09:26:54', '7aed8c297a6940f681c26eb6ab68893d', '2023-03-29 09:26:54', '7aed8c297a6940f681c26eb6ab68893d', 1, '064b3b44b85a45fe87fcce88d72b2519');







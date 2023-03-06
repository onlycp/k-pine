

ALTER TABLE "dev_application" ADD  "app_public_type" int NULL DEFAULT 0 ;

create table dev_module
(
    id            varchar(36) not null
        constraint dev_module_PK_
            primary key,
    name          varchar(100),
    path          varchar(255),
    has_path      int,
    parent_id     varchar(36),
    sort          int,
    when_created  varchar(50),
    who_created   varchar(36),
    when_modified varchar(50),
    who_modified  varchar(36),
    is_sys        int,
    app_id        varchar(36)
);


create table ext_plugin_interface
(
    id          varchar(255)           not null
        constraint ext_plugin_interface_PK_dev_module
            primary key,
    name        varchar(255),
    resp_type   varchar(255) default '',
    content     text,
    description varchar(1024),
    pluginId    varchar(255),
    createTime  datetime,
    createUser  varchar(255),
    updateTime  datetime,
    updateuser  varchar(255),
    deleted     int          default 0 not null
);


create table ext_plugin_tree
(
    id          varchar(255) not null
        constraint ext_plugin_tree_PK_ext_plugin_interface
            primary key,
    extName     varchar(255),
    jarName     varchar(255),
    type        int,
    createTime  datetime,
    updateTime  datetime,
    createUser  varchar(255),
    updateUser  varchar(255),
    status      int default 0,
    name        varchar(255),
    clazzName   varchar(255),
    description varchar(255),
    checkTime   datetime
);


create table kfaas_lib
(
    jarName    varchar(255) not null
        constraint kfaas_lib_PK_ext_plugin_tree
            primary key,
    createTime datetime,
    updateTime datetime,
    createUser varchar(255),
    updateUser varchar(255),
    status     int default 0
);


create table sys_i18n
(
    id            varchar(36)  not null
        constraint sys_i18n_PK_kfaas_lib
            primary key,
    i18n_key      varchar(255) not null,
    message       text,
    app_id        varchar(36),
    when_created  varchar(20),
    who_created   varchar(36),
    when_modified varchar(20),
    who_modified  varchar(36)
);


create table sys_logic_template
(
    id            varchar(36) not null
        constraint sys_logic_template_PK_sys_i18n
            primary key,
    name          varchar(255),
    module_id     varchar(36),
    description   text,
    nodes         text,
    links         text,
    app_id        varchar(36),
    when_created  varchar(50),
    who_created   varchar(36),
    when_modified varchar(50),
    who_modified  varchar(36)
);


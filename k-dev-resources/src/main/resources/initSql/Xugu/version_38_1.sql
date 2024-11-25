CREATE TABLE dev_page_template
(
    id            VARCHAR(36) NOT NULL COMMENT '主键',
    when_created  VARCHAR(30) COMMENT '创建时间',
    when_modified VARCHAR(30) COMMENT '修改时间',
    who_created   VARCHAR(36) COMMENT '创建人',
    who_modified  VARCHAR(36) COMMENT '修改人',
    deleted       INT DEFAULT 0 COMMENT '删除标识',
    app_id        VARCHAR(36) COMMENT '应用ID',
    name          VARCHAR(255) COMMENT '页面名称',
    description   VARCHAR(255) COMMENT '页面介绍',
    app_type      VARCHAR(100) COMMENT '应用类型：可多个，逗号分隔，0: PC Web应用，1：移动端Web应用，2： 小程序，3： APP',
    page_json     TEXT COMMENT '页面数据化JSON',
    tags          VARCHAR(255) COMMENT '标签',
    module_id     VARCHAR(36) COMMENT '关联模块',
    PRIMARY KEY (id)
);

CREATE TABLE dev_table
(
    id            VARCHAR(36) NOT NULL,
    data_source   VARCHAR(100),
    name          VARCHAR(100),
    "comment"     VARCHAR(255),
    when_created  VARCHAR(100),
    when_modified VARCHAR(100),
    who_created   VARCHAR(100),
    who_modified  VARCHAR(100),
    PRIMARY KEY (id)
);

CREATE TABLE dev_table_column
(
    id              VARCHAR(36) NOT NULL,
    table_id        VARCHAR(36),
    name            VARCHAR(100),
    "comment"       VARCHAR(255),
    sort            INT,
    type            VARCHAR(50),
    length          VARCHAR(36),
    decimal_point   VARCHAR(100),
    is_primary      INT,
    is_null         INT,
    when_created    VARCHAR(100),
    when_modified   VARCHAR(100),
    who_created     VARCHAR(100),
    who_modified    VARCHAR(100),
    is_label_column INT         NOT NULL DEFAULT 0 COMMENT '是否为显示字段（用于迁移选择数据时）',
    PRIMARY KEY (id)
);


CREATE TABLE dev_table_update_log
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

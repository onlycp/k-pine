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

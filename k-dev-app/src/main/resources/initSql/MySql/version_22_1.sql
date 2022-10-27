CREATE TABLE sys_user_unit (
     id varchar(36) NOT NULL COMMENT '主键',
     sys_user_id varchar(36) NOT NULL COMMENT '用户ID',
     sys_unit_id varchar(36) NOT NULL COMMENT '部门ID',
     who_created varchar(36) NOT NULL COMMENT '创建人员',
     when_created varchar(20) NOT NULL COMMENT '创建时间',
     app_id varchar(36) DEFAULT NULL COMMENT '关联应用',
     PRIMARY KEY (id)
);

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
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
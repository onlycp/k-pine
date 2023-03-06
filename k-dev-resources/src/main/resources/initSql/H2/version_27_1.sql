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
);

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
) ;

DROP TABLE IF EXISTS dev_faas_node_type;
CREATE TABLE dev_faas_node_type(
                                   id VARCHAR(32)  ,
                                   name VARCHAR(90)    ,
                                   pub_status INT    ,
                                   icon VARCHAR(32)    ,
                                   when_created VARCHAR(255),
                                   who_created VARCHAR(255) ,
                                   when_modified VARCHAR(255) ,
                                   who_modified VARCHAR(255) ,
                                   PRIMARY KEY (id)
);

DROP TABLE IF EXISTS dev_faas_node;
CREATE TABLE dev_faas_node(
                              `id` VARCHAR(32) NOT NULL   ,
                              `name` VARCHAR(90)    ,
                              `code` VARCHAR(90)   ,
                              `type_id` VARCHAR(32)    ,
                              `config` VARCHAR(255)   ,
                              `template` VARCHAR(1024)    ,
                              `icon` VARCHAR(32)   ,
                              `pub_status` INT   ,
                              `order_num` VARCHAR(255),
                              `when_created` VARCHAR(255)   ,
                              `who_created` VARCHAR(255)    ,
                              `when_modified` VARCHAR(255) ,
                              `who_modified` VARCHAR(255)   ,
                              PRIMARY KEY (id)
) ;


DROP TABLE IF EXISTS sys_instance;
CREATE TABLE sys_instance(
                             id VARCHAR(32) NOT NULL,
                             host_name VARCHAR(32),
                             port INTEGER,
                             heart_beat_time VARCHAR(20),
                             reg_time VARCHAR(20),
                             PRIMARY KEY (id)
);

COMMENT ON TABLE sys_instance IS '系统-实例表';
COMMENT ON COLUMN sys_instance.id IS '主键';
COMMENT ON COLUMN sys_instance.host_name IS '主机名';
COMMENT ON COLUMN sys_instance.port IS '端口';
COMMENT ON COLUMN sys_instance.heart_beat_time IS '心跳时间';
COMMENT ON COLUMN sys_instance.reg_time IS '注册时间';

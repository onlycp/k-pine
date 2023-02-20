DROP TABLE IF EXISTS sys_instance;
CREATE TABLE sys_instance(
                             `id` VARCHAR(32) NOT NULL   COMMENT '主键' ,
                             `host_name` VARCHAR(32)    COMMENT '主机名' ,
                             `port` INT    COMMENT '端口' ,
                             `heart_beat_time` VARCHAR(20)    COMMENT '心跳时间' ,
                             `reg_time` VARCHAR(20)    COMMENT '注册时间' ,
                             PRIMARY KEY (id)
)  COMMENT = '系统-实例表';

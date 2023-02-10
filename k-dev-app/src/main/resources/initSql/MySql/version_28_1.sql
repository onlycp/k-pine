DROP TABLE IF EXISTS sys_data_change;
CREATE TABLE sys_data_change(
                                `id` VARCHAR(32) NOT NULL   COMMENT '主键' ,
                                `name` VARCHAR(255)    COMMENT '模块名' ,
                                `table_name` VARCHAR(255)    COMMENT '表名' ,
                                `object_name` VARCHAR(255)    COMMENT '对象名称' ,
                                `operator` VARCHAR(255)    COMMENT '操作人员' ,
                                `oper_time` VARCHAR(255)    COMMENT '操作时间' ,
                                `oper_time` VARCHAR(255)    COMMENT '操作时间' ,
                                `content` VARCHAR(255)    COMMENT '变更内容' ,
                                PRIMARY KEY (id)
)  COMMENT = '数据变更记录';

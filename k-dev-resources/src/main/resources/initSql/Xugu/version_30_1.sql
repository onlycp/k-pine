CREATE TABLE sys_instance
(
    id              VARCHAR(32) NOT NULL COMMENT '主键',
    host_name       VARCHAR(32) COMMENT '主机名',
    port            INT COMMENT '端口',
    heart_beat_time VARCHAR(30) COMMENT '心跳时间',
    reg_time        VARCHAR(30) COMMENT '注册时间',
    PRIMARY KEY (id)
);
ALTER TABLE sys_role_menu
    MODIFY COLUMN id VARCHAR(36) NOT NULL;

ALTER TABLE sys_role_menu
    MODIFY COLUMN sys_menu_id VARCHAR(36) NOT NULL;

ALTER TABLE sys_role_menu
    MODIFY COLUMN who_created VARCHAR(36) NOT NULL;


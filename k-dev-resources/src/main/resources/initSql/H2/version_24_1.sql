CREATE TABLE sys_cache (
       id varchar(36) NOT NULL COMMENT 'ID',
       code varchar(255) DEFAULT NULL COMMENT '缓存KEY',
       `value` varchar(255) DEFAULT NULL COMMENT '缓存值',
       when_expired varchar(30) DEFAULT NULL COMMENT '过期时间',
       when_created varchar(30) DEFAULT NULL COMMENT '创建时间',
       app_id varchar(36) DEFAULT NULL COMMENT '关联应用ID',
       PRIMARY KEY (id)
);

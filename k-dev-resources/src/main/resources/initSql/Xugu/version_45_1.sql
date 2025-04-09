CREATE TABLE IF NOT EXISTS dev_chat_history
(
    id           VARCHAR(36) NOT NULL COMMENT 'id',
    question     TEXT        NOT NULL COMMENT '问题',
    answer       TEXT        DEFAULT NULL COMMENT '回答',
    args         TEXT        DEFAULT NULL COMMENT '附加参数（JSON）',
    when_created VARCHAR(50) DEFAULT NULL COMMENT '创建时间',
    who_created  VARCHAR(36) DEFAULT NULL COMMENT '创建人',
    PRIMARY KEY (id)
);

CREATE TABLE IF NOT EXISTS dev_curd
(
    id             VARCHAR(36) NOT NULL COMMENT '主键',
    name           VARCHAR(255) DEFAULT NULL COMMENT '名称',
    group_id       VARCHAR(36)  DEFAULT NULL COMMENT '组ID',
    source_name    VARCHAR(255) DEFAULT NULL COMMENT '数据源名称',
    table_name     VARCHAR(255) DEFAULT NULL COMMENT '数据表名称',
    primary_name   VARCHAR(50)  DEFAULT NULL COMMENT '主键字段名',
    request_prefix VARCHAR(255) DEFAULT NULL COMMENT '请求前缀',
    enable_funs    VARCHAR(255) DEFAULT NULL COMMENT '启动功能',
    create_funs    VARCHAR(255) DEFAULT NULL COMMENT '创建内容',
    column_json    TEXT         DEFAULT NULL COMMENT '列定义',
    app_id         VARCHAR(36)  DEFAULT NULL COMMENT '应用ID',
    who_created    VARCHAR(36)  DEFAULT NULL COMMENT '创建人',
    when_created   VARCHAR(30)  DEFAULT NULL COMMENT '创建时间',
    who_modified   VARCHAR(36)  DEFAULT NULL COMMENT '修改人',
    when_modified  VARCHAR(255) DEFAULT NULL COMMENT '修改时间',
    PRIMARY KEY (id)
);

CREATE TABLE IF NOT EXISTS dev_data_source
(
    id            VARCHAR(36) NOT NULL COMMENT 'ID',
    name          VARCHAR(100) DEFAULT NULL COMMENT '数据源名称',
    who_created   VARCHAR(36)  DEFAULT NULL COMMENT '创建人员',
    when_created  VARCHAR(30)  DEFAULT NULL COMMENT '创建时间',
    who_modified  VARCHAR(36)  DEFAULT NULL COMMENT '修改人员',
    when_modified VARCHAR(30)  DEFAULT NULL COMMENT '修改时间',
    app_id        VARCHAR(36)  DEFAULT NULL COMMENT '关联应用',
    kdb_id        VARCHAR(40)  DEFAULT NULL COMMENT '在kingDB中对应的ID',
    team_id       VARCHAR(36)  DEFAULT NULL COMMENT '所属团队ID',
    deleted       INT          DEFAULT NULL COMMENT '是否已删除',
    PRIMARY KEY (id)
);

CREATE TABLE IF NOT EXISTS dev_file_version
(
    id              VARCHAR(36) NOT NULL COMMENT 'ID',
    file_name       VARCHAR(255)  DEFAULT NULL COMMENT '文件名',
    path            VARCHAR(1000) DEFAULT NULL COMMENT '导致安装包的位置',
    os_type         VARCHAR(20)   DEFAULT NULL COMMENT '操作系统类型',
    version         VARCHAR(100)  DEFAULT NULL COMMENT '版本号（vX.X.X结构）',
    path_by_package VARCHAR(1000) DEFAULT NULL COMMENT '所在package中的真实位置',
    file_size       INT           DEFAULT NULL COMMENT '文件大小',
    description     VARCHAR(255)  DEFAULT NULL COMMENT '描述',
    parent_path     VARCHAR(1000) DEFAULT NULL COMMENT '父目录',
    when_modified   VARCHAR(30)   DEFAULT NULL COMMENT '更新时间',
    who_modified    VARCHAR(36)   DEFAULT NULL COMMENT '更新人',
    PRIMARY KEY (id)
);

ALTER TABLE rep_dataset
    ADD COLUMN dataset_search_id VARCHAR(32) DEFAULT NULL COMMENT '是否为搜索数据';

ALTER TABLE rep_dataset
    ADD COLUMN shape INT DEFAULT NULL COMMENT '是否为自定义SQL';

ALTER TABLE rep_dataset
    ADD COLUMN template INT DEFAULT NULL COMMENT '是否为Excel模板（0：否，1：是）';

ALTER TABLE rep_dataset
    ADD COLUMN rep_cron VARCHAR(50) DEFAULT NULL COMMENT '模板报表定时任务Cron表达式';


ALTER TABLE sys_unit
    ADD COLUMN short_name VARCHAR(36) DEFAULT NULL COMMENT '简称';

ALTER TABLE sys_unit
    ADD COLUMN unit_code VARCHAR(100) DEFAULT NULL COMMENT '机构编码';


ALTER TABLE sys_user
    ADD COLUMN jira_name VARCHAR(100) DEFAULT NULL COMMENT 'jira账号名（项目管理系统）';
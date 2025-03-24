CREATE TABLE IF NOT EXISTS dev_model_sql (
    id varchar(36) NOT NULL,
    app_id varchar(36),
    title varchar(255),
    source_name varchar(50),
    "content" text,
    status int8 DEFAULT 0,
    sql_version int8,
    messages text,
    ignore_except int8 DEFAULT 1,
    exec_err_line int8 DEFAULT 0,
    exec_time varchar(20),
    exec_user_id varchar(36),
    when_created varchar(20),
    who_created varchar(36),
    when_modified varchar(20),
    who_modified varchar(36),
    PRIMARY KEY (id)
);
COMMENT ON COLUMN dev_model_sql.id IS '主键';
COMMENT ON COLUMN dev_model_sql.app_id IS '应用id';
COMMENT ON COLUMN dev_model_sql.title IS '标题';
COMMENT ON COLUMN dev_model_sql.source_name IS '数据源';
COMMENT ON COLUMN dev_model_sql."content" IS '脚本';
COMMENT ON COLUMN dev_model_sql.status IS '执行状态 0: 未执行 1：已执行 2：执行异常';
COMMENT ON COLUMN dev_model_sql.sql_version IS '版本号';
COMMENT ON COLUMN dev_model_sql.messages IS '执行结果';
COMMENT ON COLUMN dev_model_sql.ignore_except IS '是否忽略错误';
COMMENT ON COLUMN dev_model_sql.exec_err_line IS '错误行号';
COMMENT ON COLUMN dev_model_sql.exec_time IS '执行时间';
COMMENT ON COLUMN dev_model_sql.exec_user_id IS '执行人';
COMMENT ON COLUMN dev_model_sql.when_created IS '创建时间';
COMMENT ON COLUMN dev_model_sql.who_created IS '创建人';
COMMENT ON COLUMN dev_model_sql.when_modified IS '修改时间';
COMMENT ON COLUMN dev_model_sql.who_modified IS '修改人';

-- 数据源业务映射表
CREATE TABLE IF NOT EXISTS dev_data_source (
    id varchar(36) NOT NULL,
    "name" varchar(100),
    who_created varchar(36),
    when_created varchar(30),
    who_modified varchar(36),
    when_modified varchar(30),
    app_id varchar(36),
    kdb_id varchar(40),
    team_id varchar(36),
    deleted int8,
    PRIMARY KEY (id)
);
COMMENT ON COLUMN dev_data_source.id IS 'ID';
COMMENT ON COLUMN dev_data_source."name" IS '数据源名称';
COMMENT ON COLUMN dev_data_source.who_created IS '创建人员';
COMMENT ON COLUMN dev_data_source.when_created IS '创建时间';
COMMENT ON COLUMN dev_data_source.who_modified IS '修改人员';
COMMENT ON COLUMN dev_data_source.when_modified IS '修改时间';
COMMENT ON COLUMN dev_data_source.app_id IS '关联应用';
COMMENT ON COLUMN dev_data_source.kdb_id IS '在kingDB中对应的ID';
COMMENT ON COLUMN dev_data_source.team_id IS '所属团队ID';
COMMENT ON COLUMN dev_data_source.deleted IS '是否已删除';

-- 开发表-页面新建的搜索记录
CREATE TABLE IF NOT EXISTS dev_search_history (
    id varchar(36) NOT NULL,
    keyword varchar(36) NOT NULL,
    use_num int8,
    deleted int DEFAULT 0,
    PRIMARY KEY (id)
    );

COMMENT ON COLUMN dev_search_history.id IS '主键';
COMMENT ON COLUMN dev_search_history.keyword IS '关键字';
COMMENT ON COLUMN dev_search_history.use_num IS '搜索次数';
COMMENT ON COLUMN dev_search_history.deleted IS '是否删除';

CREATE TABLE IF NOT EXISTS dev_git_tag (
    id varchar(36) NOT NULL,
    tag varchar(255),
    repo varchar(255),
    resource varchar(255),
    commit_id varchar(255),
    public_commit_ids text,
    note text,
    when_created varchar(20),
    who_created varchar(255),
    PRIMARY KEY (id)
    );
COMMENT ON COLUMN dev_git_tag.id IS '主键';
COMMENT ON COLUMN dev_git_tag.tag IS '标签名称';
COMMENT ON COLUMN dev_git_tag.repo IS '仓库地址';
COMMENT ON COLUMN dev_git_tag.resource IS '资源文件';
COMMENT ON COLUMN dev_git_tag.commit_id IS '提交ID';
COMMENT ON COLUMN dev_git_tag.public_commit_ids IS '公共依赖库';
COMMENT ON COLUMN dev_git_tag.note IS '版本说明';
COMMENT ON COLUMN dev_git_tag.when_created IS '创建时间';
COMMENT ON COLUMN dev_git_tag.who_created IS '创建人';

CREATE TABLE IF NOT EXISTS sys_config_group (
    id varchar(36) NOT NULL,
    app_id varchar(36),
    group_name varchar(255) NOT NULL,
    group_path varchar(255),
    when_created varchar(30),
    when_modified varchar(30),
    who_created varchar(36),
    who_modified varchar(36),
    note varchar(255),
    parent_id varchar(32),
    group_type int8 DEFAULT 1,
    leaf_config text,
    sort int8,
    icon varchar(50),
    PRIMARY KEY (id)
    );
COMMENT ON COLUMN sys_config_group.id IS '主键ID';
COMMENT ON COLUMN sys_config_group.app_id IS '关联应用';
COMMENT ON COLUMN sys_config_group.group_name IS '组名称';
COMMENT ON COLUMN sys_config_group.group_path IS '路径';
COMMENT ON COLUMN sys_config_group.when_created IS '创建时间';
COMMENT ON COLUMN sys_config_group.when_modified IS '修改时间';
COMMENT ON COLUMN sys_config_group.who_created IS '创建人员';
COMMENT ON COLUMN sys_config_group.who_modified IS '修改人员';
COMMENT ON COLUMN sys_config_group.note IS '备注';
COMMENT ON COLUMN sys_config_group.parent_id IS '父分组ID';
COMMENT ON COLUMN sys_config_group.group_type IS '分组层级';
COMMENT ON COLUMN sys_config_group.leaf_config IS '配置格式json';
COMMENT ON COLUMN sys_config_group.sort IS '排序';
COMMENT ON COLUMN sys_config_group.icon IS '图标';

CREATE TABLE IF NOT EXISTS dev_model_latest (
    id varchar(36) NOT NULL,
    model_name varchar(50),
    source_name varchar(50),
    version_name varchar(100),
    version_who varchar(100),
    version_time varchar(20),
    description varchar(255),
    diagram text,
    inner_version bigint DEFAULT 0,
    custom_type_mapping text,
    app_id varchar(36),
    who_created varchar(36),
    when_created varchar(20),
    who_modified varchar(36),
    when_modified varchar(20),
    PRIMARY KEY (id)
    );
COMMENT ON COLUMN dev_model_latest.id IS '主键ID';
COMMENT ON COLUMN dev_model_latest.model_name IS '模型名称';
COMMENT ON COLUMN dev_model_latest.source_name IS '数据源名称';
COMMENT ON COLUMN dev_model_latest.version_name IS '修订版本';
COMMENT ON COLUMN dev_model_latest.version_who IS '修订人';
COMMENT ON COLUMN dev_model_latest.version_time IS '修订时间';
COMMENT ON COLUMN dev_model_latest.description IS '备注';
COMMENT ON COLUMN dev_model_latest.diagram IS '模型数据';
COMMENT ON COLUMN dev_model_latest.inner_version IS '内部版本号，用于服务端保存时校验';
COMMENT ON COLUMN dev_model_latest.custom_type_mapping IS '用户自定义类型映射，当用户导入表出现系统未适配的字段类型时，提醒用户选择要转成什么类型';
COMMENT ON COLUMN dev_model_latest.app_id IS '所属应用ID';
COMMENT ON COLUMN dev_model_latest.who_created IS '创建人';
COMMENT ON COLUMN dev_model_latest.when_created IS '创建时间';
COMMENT ON COLUMN dev_model_latest.who_modified IS '更新人';
COMMENT ON COLUMN dev_model_latest.when_modified IS '更新时间';

CREATE TABLE IF NOT EXISTS dev_page_template_action_log (
    id varchar(36) NOT NULL,
    action_type int8 NOT NULL,
    is_copy_all int,
    template_id varchar(36) NOT NULL,
    app_id varchar(36),
    team_id varchar(36),
    action_content text,
    who_created varchar(36) NOT NULL,
    when_created varchar(20) NOT NULL,
    PRIMARY KEY (id)
    );
COMMENT ON COLUMN dev_page_template_action_log.id IS '主键';
COMMENT ON COLUMN dev_page_template_action_log.action_type IS '动作类型；1：模板复制；2：模板预览';
COMMENT ON COLUMN dev_page_template_action_log.is_copy_all IS '是否全文复制';
COMMENT ON COLUMN dev_page_template_action_log.template_id IS '数据id，根据action_type指向不同的表';
COMMENT ON COLUMN dev_page_template_action_log.app_id IS '关联应用';
COMMENT ON COLUMN dev_page_template_action_log.team_id IS '关联团队';
COMMENT ON COLUMN dev_page_template_action_log.action_content IS '动作关联的内容，例如action_type为模板复制时，content就是复制的内容';
COMMENT ON COLUMN dev_page_template_action_log.who_created IS '创建人员';
COMMENT ON COLUMN dev_page_template_action_log.when_created IS '创建时间';

CREATE TABLE IF NOT EXISTS dev_file_version (
    id varchar(36) NOT NULL,
    file_name varchar(255),
    path varchar(1000),
    os_type varchar(20),
    "version" varchar(100),
    path_by_package varchar(1000),
    file_size int8,
    description varchar(255),
    parent_path varchar(1000),
    when_modified varchar(30),
    who_modified varchar(36),
    PRIMARY KEY (id)
    );
COMMENT ON COLUMN dev_file_version.id IS 'ID';
COMMENT ON COLUMN dev_file_version.file_name IS '文件名';
COMMENT ON COLUMN dev_file_version.path IS '导致安装包的位置';
COMMENT ON COLUMN dev_file_version.os_type IS '操作系统类型';
COMMENT ON COLUMN dev_file_version."version" IS '版本号（vX.X.X结构）';
COMMENT ON COLUMN dev_file_version.path_by_package IS '所在package中的真实位置';
COMMENT ON COLUMN dev_file_version.file_size IS '文件大小';
COMMENT ON COLUMN dev_file_version.description IS '描述';
COMMENT ON COLUMN dev_file_version.parent_path IS '父目录';
COMMENT ON COLUMN dev_file_version.when_modified IS '更新时间';
COMMENT ON COLUMN dev_file_version.who_modified IS '更新人';

CREATE TABLE IF NOT EXISTS sys_base (
    id varchar(36) NOT NULL,
    app_id varchar(36),
    code varchar(50),
    is_test int2,
    "name" varchar(50),
    note text,
    when_created varchar(30),
    when_modified varchar(30),
    who_created varchar(36),
    who_modified varchar(36),
    PRIMARY KEY (id)
    );
COMMENT ON COLUMN sys_base.app_id IS '关联应用';
COMMENT ON COLUMN sys_base.code IS '编码';
COMMENT ON COLUMN sys_base."name" IS '名称';
COMMENT ON COLUMN sys_base.note IS '备注';
COMMENT ON COLUMN sys_base.when_created IS '创建时间';
COMMENT ON COLUMN sys_base.when_modified IS '修改时间';
COMMENT ON COLUMN sys_base.who_created IS '创建人员';
COMMENT ON COLUMN sys_base.who_modified IS '修改人员';





















-- 创建 dev_curd 表
CREATE TABLE dev_curd
(
    id VARCHAR2 (36 CHAR) NOT NULL,
    name VARCHAR2 (255 CHAR),
    group_id VARCHAR2 (36 CHAR),
    source_name VARCHAR2 (255 CHAR),
    table_name VARCHAR2 (255 CHAR),
    primary_name VARCHAR2 (50 CHAR),
    request_prefix VARCHAR2 (255 CHAR),
    enable_funs VARCHAR2 (255 CHAR),
    create_funs VARCHAR2 (255 CHAR),
    column_json CLOB,
    app_id VARCHAR2 (36 CHAR),
    who_created VARCHAR2 (36 CHAR),
    when_created VARCHAR2 (20 CHAR),
    who_modified VARCHAR2 (36 CHAR),
    when_modified VARCHAR2 (255 CHAR),
    PRIMARY KEY (id)
);


-- 创建 sys_logic_template_user 表
CREATE TABLE sys_logic_template_user
(
    id VARCHAR2 (36 CHAR) NOT NULL,
    app_id VARCHAR2 (36 CHAR) DEFAULT NULL,
    when_created VARCHAR2 (50 CHAR) DEFAULT NULL,
    who_created VARCHAR2 (36 CHAR) DEFAULT NULL,
    template_id VARCHAR2 (36 CHAR) DEFAULT NULL,
    PRIMARY KEY (id)
);

-- 创建 dev_page_template 表
CREATE TABLE dev_page_template
(
    id VARCHAR2 (36 CHAR) NOT NULL,
    when_created VARCHAR2 (50 CHAR) DEFAULT NULL,
    when_modified VARCHAR2 (50 CHAR) DEFAULT NULL,
    who_created VARCHAR2 (36 CHAR) DEFAULT NULL,
    who_modified VARCHAR2 (36 CHAR) DEFAULT NULL,
    deleted NUMBER (1) DEFAULT 0,
    app_id VARCHAR2 (36 CHAR) DEFAULT NULL,
    name VARCHAR2 (255 CHAR) DEFAULT NULL,
    description CLOB,
    app_type VARCHAR2 (100 CHAR) DEFAULT NULL,
    page_json CLOB,
    tags VARCHAR2 (255 CHAR) DEFAULT NULL,
    module_id VARCHAR2 (36 CHAR) DEFAULT NULL,
    snapshot_img_id VARCHAR2 (32 CHAR) DEFAULT NULL,
    order_num NUMBER (11) DEFAULT NULL,
    bg_colors VARCHAR2 (64 CHAR) DEFAULT NULL,
    page_type VARCHAR2 (32 CHAR) DEFAULT NULL,
    use_num NUMBER (11) DEFAULT 0,
    extra CLOB,
    view_num NUMBER (11) DEFAULT 0,
    copy_num NUMBER (11) DEFAULT 0,
    copy_all_num NUMBER (11) DEFAULT 0,
    copy_part_num NUMBER (11) DEFAULT 0,
    PRIMARY KEY (id)
);

-- 修改 sys_logic_template 表，添加新列
ALTER TABLE sys_logic_template
    ADD sys_suggested NUMBER (10) NULL;
ALTER TABLE sys_logic_template
    ADD publish_status NUMBER (10) NULL;

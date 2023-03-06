DROP TABLE IF EXISTS rep_template;
CREATE TABLE rep_template(
                             id VARCHAR(36) NOT NULL,
                             name VARCHAR(255) NOT NULL,
                             tpl_file_id VARCHAR(36),
                             excel_file VARCHAR(36),
                             type VARCHAR(10),
                             ds_sets VARCHAR(1024),
                             note VARCHAR(255),
                             who_created VARCHAR(36),
                             when_created VARCHAR(20),
                             who_modified VARCHAR(36),
                             when_modified VARCHAR(20),
                             PRIMARY KEY (id)
);

COMMENT ON TABLE rep_template IS '报告模板表';
COMMENT ON COLUMN rep_template.id IS '主键';
COMMENT ON COLUMN rep_template.name IS '模板名称';
COMMENT ON COLUMN rep_template.tpl_file_id IS '报告模板id';
COMMENT ON COLUMN rep_template.type IS '报告类型';
COMMENT ON COLUMN rep_template.ds_sets IS '数据集列表';
COMMENT ON COLUMN rep_template.note IS '说明';
COMMENT ON COLUMN rep_template.who_created IS '创建人';
COMMENT ON COLUMN rep_template.when_created IS '创建时间';
COMMENT ON COLUMN rep_template.who_modified IS '更新人';
COMMENT ON COLUMN rep_template.when_modified IS '更新时间';

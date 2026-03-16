-- [青松4.1] 兼容 oracle 表名最多30个字符问题 --

CREATE TABLE IF NOT EXISTS dev_app_version_history  (
    id varchar(36) NOT NULL,
    app_id varchar(36) NULL DEFAULT NULL,
    export_data text NULL,
    file_name varchar(255) NULL DEFAULT NULL,
    note varchar(255) NULL DEFAULT NULL,
    version varchar(50) NULL DEFAULT NULL,
    when_created varchar(30) NULL DEFAULT NULL,
    who_created varchar(36) NULL DEFAULT NULL,
    PRIMARY KEY (id)
);

COMMENT ON COLUMN dev_app_version_history.id IS 'ID';
COMMENT ON COLUMN dev_app_version_history.app_id IS '应用ID';
COMMENT ON COLUMN dev_app_version_history.export_data IS '导出数据参数';
COMMENT ON COLUMN dev_app_version_history.file_name IS '文件名';
COMMENT ON COLUMN dev_app_version_history.note IS '备注';
COMMENT ON COLUMN dev_app_version_history.version IS '版本';
COMMENT ON COLUMN dev_app_version_history.when_created IS '创建时间';
COMMENT ON COLUMN dev_app_version_history.who_created IS '创建人';
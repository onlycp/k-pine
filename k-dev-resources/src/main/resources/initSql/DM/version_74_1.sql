-- [青松4.1] 兼容 oracle 表名最多30个字符问题 --

CREATE TABLE dev_app_version_history  (
    id varchar2(36 char) NOT NULL,
    app_id varchar2(36 char) NULL,
    export_data CLOB NULL,
    file_name varchar2(255 char) NULL,
    note varchar2(255 char) NULL,
    version varchar2(50 char) NULL,
    when_created varchar2(30 char) NULL,
    who_created varchar2(36 char) NULL,
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

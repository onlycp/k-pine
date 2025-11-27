ALTER TABLE sys_file MODIFY file_original_name NULL;
ALTER TABLE sys_file MODIFY file_name NULL;
ALTER TABLE sys_file MODIFY file_from NULL;
COMMENT ON COLUMN sys_file.file_original_name IS '文件新名称';
COMMENT ON COLUMN sys_file.file_name IS '文件旧名称';
COMMENT ON COLUMN sys_file.file_from IS '文件来源';

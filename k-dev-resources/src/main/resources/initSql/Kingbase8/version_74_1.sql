ALTER TABLE sys_file ALTER COLUMN file_original_name TYPE VARCHAR(255);
COMMENT ON COLUMN sys_file.file_original_name IS '文件新名称';
ALTER TABLE sys_file ALTER COLUMN file_name TYPE VARCHAR(255);
COMMENT ON COLUMN sys_file.file_name IS '文件旧名称';
ALTER TABLE sys_file ALTER COLUMN file_from TYPE VARCHAR(255);
COMMENT ON COLUMN sys_file.file_from IS '文件来源';

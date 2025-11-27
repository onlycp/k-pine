ALTER TABLE sys_file MODIFY COLUMN file_original_name varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL COMMENT '文件新名称';
ALTER TABLE sys_file MODIFY COLUMN file_name varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL COMMENT '文件旧名称';
ALTER TABLE sys_file MODIFY COLUMN file_from varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL COMMENT '文件来源';

alter table dev_git_tag add db_schema int COMMENT '是否包含表结构和数据';
alter table dev_git_tag add db_sourcename varchar(100) COMMENT '包含了哪些数据源的表结构和数据';
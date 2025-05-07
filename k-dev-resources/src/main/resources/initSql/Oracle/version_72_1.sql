alter table dev_git_tag add db_schema  int ;
COMMENT ON COLUMN dev_git_tag.db_schema IS '是否包含表结构和数据';

alter table dev_git_tag add db_sourcename varchar2(100 char) ;
COMMENT ON COLUMN dev_git_tag.db_sourcename IS '包含了哪些数据源的表结构和数据';
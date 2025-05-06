alter table dev_git_tag add column if not EXISTS db_schema  int ;
COMMENT ON COLUMN dev_git_tag.db_schema IS '是否包含表结构和数据';
ALTER TABLE sys_search_config ADD search_columns text NULL;
ALTER TABLE sys_search_config ALTER COLUMN columns TYPE varchar(1000);

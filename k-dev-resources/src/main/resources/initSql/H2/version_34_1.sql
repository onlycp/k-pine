ALTER TABLE sys_search_config ADD search_columns text NULL;
ALTER TABLE sys_search_config MODIFY COLUMN `columns` varchar(1000);

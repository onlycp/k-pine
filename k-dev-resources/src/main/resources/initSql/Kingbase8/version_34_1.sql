
ALTER TABLE sys_unit ADD unit_level int NULL ;
ALTER TABLE sys_search_config ADD search_columns varchar(1000) NULL ;
ALTER TABLE sys_search_config alter column columns type varchar(1000) ;

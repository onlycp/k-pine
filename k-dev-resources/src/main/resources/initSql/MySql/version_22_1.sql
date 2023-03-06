

CREATE TABLE sys_search_config (
    id varchar(36)  NOT NULL,
    data_source varchar(100) ,
    table_name varchar(100),
    columns varchar(255),
    primary_columns varchar(255),
    link varchar(255) ,
    labels varchar(255) ,
    when_created varchar(50) ,
    when_modified varchar(50) ,
    who_created varchar(36) ,
    who_modified varchar(36) ,
    title_column varchar(100) ,
    PRIMARY KEY (id)
) ;
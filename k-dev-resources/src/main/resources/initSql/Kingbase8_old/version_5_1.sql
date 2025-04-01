

CREATE TABLE sys_search_config (
                                   id varchar2(36)  NOT NULL,
                                   data_source varchar2(100) ,
                                   table_name varchar2(100),
                                   columns varchar2(255),
                                   primary_columns varchar2(255),
                                   link varchar2(255) ,
                                   labels varchar2(255) ,
                                   when_created varchar2(50) ,
                                   when_modified varchar2(50) ,
                                   who_created varchar2(36) ,
                                   who_modified varchar2(36) ,
                                   title_column varchar2(100) ,
                                   PRIMARY KEY (id)
) ;

CREATE TABLE rep_template(
                             id varchar2(36) NOT NULL ,
                             name varchar2(255) NOT NULL  ,
                             tpl_file_id varchar2(36) ,
                             excel_file varchar2(36) ,
                             type varchar2(10) ,
                             ds_sets varchar2(1024) ,
                             note varchar2(255) ,
                             who_created varchar2(36) ,
                             when_created varchar2(30)  ,
                             who_modified varchar2(36) ,
                             when_modified varchar2(30)  ,
                             PRIMARY KEY (id)
) ;
CREATE TABLE sys_cache (
                           id varchar2(36) NOT NULL,
                           code varchar2(255) DEFAULT NULL,
                           value varchar2(255) DEFAULT NULL ,
                           when_expired varchar2(30) DEFAULT NULL,
                           when_created varchar2(30) DEFAULT NULL ,
                           app_id varchar2(36) DEFAULT NULL  ,
                           PRIMARY KEY (id)
);
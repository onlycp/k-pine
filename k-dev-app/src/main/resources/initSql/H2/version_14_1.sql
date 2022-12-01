DROP TABLE IF EXISTS rep_template;
CREATE TABLE rep_template(
                             id VARCHAR(36) NOT NULL  ,
                             name VARCHAR(255) NOT NULL   ,
                             tpl_file_id VARCHAR(36)   ,
                             excel_file VARCHAR(36)   ,
                             type VARCHAR(10)  ,
                             ds_sets VARCHAR(1024)  ,
                             note VARCHAR(255)    ,
                             who_created VARCHAR(36)    ,
                             when_created VARCHAR(20)  ,
                             who_modified VARCHAR(36)   ,
                             when_modified VARCHAR(20)  ,
                             PRIMARY KEY (id)
)  ;

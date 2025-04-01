
CREATE TABLE sys_offline_download(
                                     id VARCHAR(36) NOT NULL   primary key,
                                     file_name VARCHAR(90)   ,
                                     task_name VARCHAR(255)  ,
                                     file_path VARCHAR(255)   ,
                                     end_time VARCHAR(255)    ,
                                     script VARCHAR(1024)   ,
                                     process INT    ,
                                     status VARCHAR(255)     ,
                                     params VARCHAR(255)   ,
                                     error_message VARCHAR(900)   ,
                                     who_created VARCHAR(36)  ,
                                     when_created VARCHAR(20)
) ;

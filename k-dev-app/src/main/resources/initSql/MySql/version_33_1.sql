DROP TABLE IF EXISTS sys_offline_download;
CREATE TABLE sys_offline_download(
                                     id VARCHAR(36) NOT NULL   COMMENT '主键' ,
                                     file_name VARCHAR(90)    COMMENT '文件名称' ,
                                     task_name VARCHAR(255)    COMMENT '任务名称' ,
                                     file_path VARCHAR(255)    COMMENT '文件路径' ,
                                     end_time VARCHAR(255)    COMMENT '结束时间' ,
                                     script VARCHAR(1024)    COMMENT '脚本' ,
                                     process INT    COMMENT '进度' ,
                                     status VARCHAR(255)    COMMENT '状态 0:待开始 1:进行中 2：已完成 3:异常' ,
                                     params VARCHAR(255)    COMMENT '参数' ,
                                     error_message VARCHAR(900)    COMMENT '异常信息' ,
                                     who_created VARCHAR(36)    COMMENT '创建人' ,
                                     when_created VARCHAR(20)    COMMENT '创建时间' ,
                                     PRIMARY KEY (id)
)  COMMENT = '离线下载表';

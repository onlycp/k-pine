
CREATE TABLE rep_template(
                             id VARCHAR(36) NOT NULL   COMMENT '主键' ,
                             name VARCHAR(255) NOT NULL   COMMENT '模板名称' ,
                             tpl_file_id VARCHAR(36)    COMMENT '报告模板id' ,
                             excel_file VARCHAR(36)    COMMENT '配置文件' ,
                             type VARCHAR(10)    COMMENT '报告类型' ,
                             ds_sets VARCHAR(1024)    COMMENT '数据集列表' ,
                             note VARCHAR(255)    COMMENT '说明' ,
                             who_created VARCHAR(36)    COMMENT '创建人' ,
                             when_created VARCHAR(20)    COMMENT '创建时间' ,
                             who_modified VARCHAR(36)    COMMENT '更新人' ,
                             when_modified VARCHAR(20)    COMMENT '更新时间' ,
                             PRIMARY KEY (id)
);

CREATE TABLE SYS_INSTANCE (
                              ID VARCHAR2(32 CHAR) NOT NULL PRIMARY KEY,
                              HOST_NAME VARCHAR2(32 CHAR),
                              PORT NUMBER,
                              HEART_BEAT_TIME VARCHAR2(20 CHAR),
                              REG_TIME VARCHAR2(20 CHAR)
);


alter table sys_role_menu modify  id varchar(36);
alter table sys_role_menu modify  sys_menu_id varchar(36) ;
alter table sys_role_menu modify  who_created varchar(36) ;

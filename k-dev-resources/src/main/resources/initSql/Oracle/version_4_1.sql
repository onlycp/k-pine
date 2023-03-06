create table sys_user_unit (
       id varchar(36) not null ,
       sys_user_id varchar(36) not null ,
       sys_unit_id varchar(36) not null,
       who_created varchar(36) not null ,
       when_created varchar(20) not null ,
       app_id varchar(36),
       primary key(id)
);
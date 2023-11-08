create table sys_auth_source
(
    id varchar2 (36 char) not null,
    code varchar2 (36 char),
    config clob,
    logicFlowId varchar2 (36 char),
    name varchar2 (255 char),
    note varchar2 (255 char),
    orderNum number (10,0),
    status number (10,0),
    whenCreated varchar2 (20 char),
    whenModified varchar2 (20 char),
    whoCreated varchar2 (36 char),
    whoModified varchar2 (36 char),
    primary key (id)
);

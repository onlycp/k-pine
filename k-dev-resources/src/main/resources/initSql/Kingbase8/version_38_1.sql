create table if not exists sys_auth_source
(
    id varchar2 (36) not null,
    code varchar2 (36),
    config clob,
    logicFlowId varchar2 (36),
    name varchar2 (255),
    note varchar2 (255),
    orderNum NUMBER,
    status NUMBER,
    whenCreated varchar2 (20),
    whenModified varchar2 (20),
    whoCreated varchar2 (36),
    whoModified varchar2 (36),
    primary key (id)
);

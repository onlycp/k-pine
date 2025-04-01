create table if not exists sys_auth_source
(
    id varchar2 (36) not null,
    code varchar2 (36),
    icon varchar2 (255),
    config clob,
    logic_flow_id varchar2 (36),
    name varchar2 (255),
    note varchar2 (255),
    order_num NUMBER,
    status NUMBER,
    type NUMBER,
    when_created varchar2 (20),
    when_modified varchar2 (20),
    who_created varchar2 (36),
    who_modified varchar2 (36),
    primary key (id)
);

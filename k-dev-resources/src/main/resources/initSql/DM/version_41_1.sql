create table sys_auth_source
(
    id varchar2 (36 char) not null,
    code varchar2 (36 char),
    config clob,
    logic_flow_id varchar2 (36 char),
    name varchar2 (255 char),
    note varchar2 (255 char),
    order_num number (10,0),
    status number (10,0),
    when_created varchar2 (20 char),
    when_modified varchar2 (20 char),
    who_created varchar2 (36 char),
    who_modified varchar2 (36 char),
    primary key (id)
);

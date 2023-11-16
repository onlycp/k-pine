create table if not exists sys_auth_source
(
    id           varchar(36) not null,
    code         varchar(36),
    config       longtext,
    logic_flow_id  varchar(36),
    name         varchar(255),
    note         varchar(255),
    order_num     integer,
    status       integer,
    when_created  varchar(20),
    when_modified varchar(20),
    whoCreated   varchar(36),
    who_modified  varchar(36),
    primary key (id)
) engine = InnoDB;

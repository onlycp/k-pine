create table if not exists sys_auth_source
(
    id           varchar(36) not null,
    code         varchar(36),
    config       longtext,
    logicFlowId  varchar(36),
    name         varchar(255),
    note         varchar(255),
    orderNum     integer,
    status       integer,
    whenCreated  varchar(20),
    whenModified varchar(20),
    whoCreated   varchar(36),
    whoModified  varchar(36),
    primary key (id)
) engine = InnoDB;

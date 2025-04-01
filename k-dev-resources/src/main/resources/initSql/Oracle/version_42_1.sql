alter table SYS_LOGIN_LOG
    add ADDRESS varchar(255) null;

CREATE TABLE  dev_chat_history (
    id varchar2(36)  NOT NULL,
    question CLOB  NOT NULL,
    answer CLOB ,
    args CLOB,
    when_created varchar2(50)  DEFAULT NULL,
    who_created varchar2(36)  DEFAULT NULL,
    PRIMARY KEY (id)
    );
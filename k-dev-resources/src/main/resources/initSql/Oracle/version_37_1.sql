alter table sys_login_log
    add address varchar(255) null;

CREATE TABLE dev_chat_history (
                                  id VARCHAR2(36 CHAR) NOT NULL,
                                  question CLOB NOT NULL,
                                  answer CLOB,
                                  args CLOB,
                                  when_created VARCHAR2(50 CHAR),
                                  who_created VARCHAR2(36 CHAR),
                                  PRIMARY KEY (id)
);

ALTER TABLE sys_config ALTER COLUMN is_public  set DEFAULT null;
update sys_config set is_public = null WHERE is_public = 0;


-- 以下是来自 version_73_1.sql ,4.1AI库需要的表 的内容 --


CREATE TABLE IF NOT EXISTS dev_chat_session (
    session_id varchar(255) NOT NULL,
    user_id varchar(255) NOT NULL,
    title varchar(255) ,
    is_archived int8 DEFAULT 0,
    when_created varchar(30) ,
    when_modified varchar(30) ,
    PRIMARY KEY (session_id)
    );

CREATE TABLE IF NOT EXISTS dev_chat_message (
    message_id varchar(36) NOT NULL,
    session_id varchar(255) NOT NULL,
    sender varchar(10) ,
    message_content text ,
    message_type varchar(20) DEFAULT 'text',
    args text,
    when_created varchar(30) ,
    when_modified varchar(30) ,
    PRIMARY KEY (message_id)
    );

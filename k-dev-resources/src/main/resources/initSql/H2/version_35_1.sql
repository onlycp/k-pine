
alter table sys_api alter column api_tags VARCHAR(255) null;
alter table sys_logic_flow alter column tags VARCHAR(255) null;
alter table dev_page alter column tags VARCHAR(255) null;

CREATE TABLE IF NOT EXISTS dev_chat_history (
    id varchar(36)  NOT NULL,
    question text  NOT NULL,
    answer text ,
    args text,
    when_created varchar(50)  DEFAULT NULL,
    who_created varchar(36)  DEFAULT NULL,
    PRIMARY KEY (id)
    );
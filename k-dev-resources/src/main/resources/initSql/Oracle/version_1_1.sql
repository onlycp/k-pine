create table DEV_SQL_RUN
(
    ID             VARCHAR2(36 char) not null
        constraint DEV_SQL_RUN_PK_DEV_POWER_TREE
        primary key,
    VERSION        NUMBER            not null,
    MD5            VARCHAR(100),
    WHEN_CREATED   VARCHAR(20),
    EXECUTION_TIME NUMBER,
    SUCCESS        NUMBER(3)         not null
);

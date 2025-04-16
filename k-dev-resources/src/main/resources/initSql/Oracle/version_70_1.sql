
-- 以下是来自 version_1_1.sql 的内容 --

create table DEV_SQL_RUN
(
    ID             VARCHAR2(36 char) not null primary key,
    VERSION        NUMBER            not null,
    MD5            VARCHAR2(100 char),
    WHEN_CREATED   VARCHAR2(20 char),
    EXECUTION_TIME NUMBER,
    SUCCESS        NUMBER(3)         not null
);


-- 以下是来自 version_2_1.sql 的内容 --

create table DEV_API
(
    ID                 VARCHAR2(36 char) default '' not null
        constraint DEV_API_PK_
        primary key,
    API_NAME           VARCHAR2(50 char),
    APPLICATION_ID     VARCHAR2(36 char),
    API_URL            VARCHAR2(128 char),
    API_NOTE           CLOB,
    API_TAGS           VARCHAR2(128 char),
    API_METHOD         VARCHAR2(36 char) default 'get',
    API_ARGV_TYPE      NUMBER,
    API_REQ_ARGV       CLOB,
    API_RSP_ARGV       CLOB,
    API_RESULT_HANDLER VARCHAR2(128 char),
    WHO_CREATED        VARCHAR2(36 char),
    WHEN_CREATED       VARCHAR2(20 char),
    WHO_MODIFIED       VARCHAR2(36 char),
    WHEN_MODIFIED      VARCHAR2(20 char),
    API_FLOW_ID        VARCHAR2(50 char),
    API_CODE           VARCHAR2(50 char),
    CALL_TYPE          NUMBER,
    APP_ID             VARCHAR2(36 char)
);
create table DEV_APPLICATION
(
    ID              VARCHAR2(36 char)   not null
        constraint DEV_APPLICATION_PK_DEV_API
        primary key,
    NAME            VARCHAR2(100 char),
    SHORT_NAME      VARCHAR2(30 char)   not null,
    DESCRIPTION     VARCHAR2(255 char),
    ENABLE_STATUS   NUMBER(3),
    DEV_STATUS      NUMBER(3),
    VERSION         VARCHAR2(50 char),
    WHO_IN_CHARGE   VARCHAR2(255 char),
    SYSTEM_LOGO     VARCHAR2(255 char),
    APP_TYPE        VARCHAR2(100 char),
    DEFAULT_PATH    VARCHAR2(255 char),
    DELETED         NUMBER(3) default 0 not null,
    WHEN_CREATED    VARCHAR2(20 char),
    WHO_CREATED     VARCHAR2(36 char),
    WHEN_MODIFIED   VARCHAR2(20 char),
    WHO_MODIFIED    VARCHAR2(36 char),
    FAAS_PORT       NUMBER,
    PINE_PORT       NUMBER,
    DATA_SOURCE     VARCHAR2(1000 char),
    APP_PUBLIC_TYPE NUMBER    default 0
);

create table DEV_DOCUMENT
(
    ID           VARCHAR2(255 char) not null primary key,
    NAME         VARCHAR2(255 char),
    PATH         VARCHAR2(255 char),
    CONTENT      CLOB,
    PARENT_ID    VARCHAR2(255 char),
    "ORDER"      NUMBER             not null,
    WHEN_CREATED DATE,
    WHO_CREATED  VARCHAR2(255 char),
    DELETED      NUMBER(3) default 0
);



create table DEV_MODULE
(
    ID            VARCHAR2(36 char) not null
        constraint DEV_MODULE_PK_DEV_DOCUMENT
        primary key,
    NAME          VARCHAR2(100 char),
    PATH          VARCHAR2(255 char),
    HAS_PATH      NUMBER,
    PARENT_ID     VARCHAR2(36 char),
    SORT          NUMBER,
    WHEN_CREATED  VARCHAR2(50 char),
    WHO_CREATED   VARCHAR2(36 char),
    WHEN_MODIFIED VARCHAR2(50 char),
    WHO_MODIFIED  VARCHAR2(36 char),
    IS_SYS        NUMBER,
    APP_ID        VARCHAR2(36 char)
);

create table DEV_OTA_CHANNEL
(
    ID            VARCHAR2(36 char) not null
        constraint DEV_OTA_CHANNEL_PK_DEV_MODULE
        primary key,
    CHANNEL_NAME  VARCHAR2(50 char),
    CHANNEL_URL   VARCHAR2(100 char),
    AUTH_TOKEN    VARCHAR2(50 char),
    SIGN_SECRET   VARCHAR2(50 char),
    MASTER        NUMBER default 0,
    NOTE          VARCHAR2(255 char),
    WHEN_CREATED  VARCHAR2(20 char),
    WHO_CREATED   VARCHAR2(36 char),
    WHEN_MODIFIED VARCHAR2(20 char),
    WHO_MODIFIED  VARCHAR2(36 char)
);

create table DEV_PAGE
(
    ID             VARCHAR2(36 char) not null
        constraint DEV_PAGE_PK_DEV_OTA_CHANNEL
        primary key,
    WHEN_CREATED   VARCHAR2(20 char),
    WHEN_MODIFIED  VARCHAR2(20 char),
    WHO_CREATED    VARCHAR2(36 char),
    WHO_MODIFIED   VARCHAR2(36 char),
    DELETED        NUMBER(3) default 0,
    APP_ID         VARCHAR2(36 char),
    NAME           VARCHAR2(255 char),
    DESCRIPTION    VARCHAR2(255 char),
    PATH           VARCHAR2(255 char),
    APP_TYPE       VARCHAR2(100 char),
    LOGIN_REQUIRED NUMBER(3),
    ENABLE_STATUS  NUMBER(3),
    DEV_STATUS     NUMBER(3),
    PAGE_JSON      CLOB
);
create table dev_page_history
(
    id               varchar(36) not null
        primary key,
    page_id          varchar(36) ,
    page_json        clob   ,
    when_created     timestamp   ,
    who_created      varchar(36) ,
    version_tag      varchar(50) ,
    version_tag_time varchar(30)
);

create table DEV_POWER_LINK
(
    ID           VARCHAR2(36 char) not null
        constraint IND_93035A0CDEDE6A72
        primary key,
    TREE_ID      VARCHAR2(36 char),
    POWER_ID     VARCHAR2(36 char),
    POWER_TYPE   NUMBER,
    WHO_CREATED  VARCHAR2(36 char),
    WHEN_CREATED VARCHAR2(20 char)
);

create table DEV_POWER_TREE
(
    ID            VARCHAR2(36 char) not null
        constraint IND_61723BC7B93A02FB
        primary key,
    NAME          VARCHAR2(128 char),
    PARENT_ID     VARCHAR2(36 char),
    NOTE          VARCHAR2(255 char),
    WHO_CREATED   VARCHAR2(36 char),
    WHEN_CREATED  VARCHAR2(20 char),
    WHO_MODIFIED  VARCHAR2(36 char),
    WHEN_MODIFIED VARCHAR2(20 char),
    PATH          CLOB
);

create table DEV_SQL_SCRIPT
(
    SQL         CLOB                not null,
    DESCRIPTION VARCHAR2(255 char),
    VERSION     NUMBER              not null
        constraint DEV_SQL_SCRIPT_PK_DEV_SQL_RUN
            primary key,
    IS_ONCE     NUMBER(3) default 1 not null
);


create table DEV_TEAM
(
    ID            VARCHAR2(36 char)   not null
        constraint DEV_TEAM_PK_DEV_SQL_SCRIPT
        primary key,
    NAME          VARCHAR2(100 char)  not null,
    OWNER         VARCHAR2(36 char)   not null,
    DESCRIPTION   VARCHAR2(255 char),
    DELETED       NUMBER(3) default 0 not null,
    WHEN_CREATED  VARCHAR2(20 char),
    WHO_CREATED   VARCHAR2(36 char),
    WHEN_MODIFIED VARCHAR2(20 char),
    WHO_MODIFIED  VARCHAR2(36 char)
);

create table DEV_TEAM_APP
(
    ID           VARCHAR2(36 char) not null
        constraint DEV_TEAM_APP_PK_DEV_TEAM
        primary key,
    TEAM_ID      VARCHAR2(36 char),
    APP_ID       VARCHAR2(36 char),
    TEAM_TYPE    NUMBER(3)         not null,
    WHEN_CREATED VARCHAR2(20 char),
    WHO_CREATED  VARCHAR2(36 char)
);

create table DEV_TEAM_MEMBER
(
    ID           VARCHAR2(36 char) not null
        constraint IND_9780D19670F5F074
        primary key,
    TEAM_ID      VARCHAR2(36 char) not null,
    USER_ID      VARCHAR2(36 char) not null,
    WHEN_JOIN    VARCHAR2(20 char),
    WHO_INVITE   VARCHAR2(36 char),
    TEAM_ROLE_ID VARCHAR2(36 char),
    IS_OWNER     NUMBER(3),
    APP_ID       VARCHAR2(255 char)
);

create table DEV_TOPOLOGICAL
(
    ID            VARCHAR2(36 char) not null,
    WHEN_CREATED  VARCHAR2(20 char),
    WHEN_MODIFIED VARCHAR2(20 char),
    WHO_CREATED   VARCHAR2(36 char),
    WHO_MODIFIED  VARCHAR2(36 char),
    DELETED       NUMBER(3) default 0,
    APP_ID        VARCHAR2(36 char),
    NAME          VARCHAR2(255 char),
    DESCRIPTION   VARCHAR2(255 char),
    PAGE_JSON     CLOB,
    ENABLE_STATUS NUMBER(3)
);

create table DEV_VIEW_MODEL
(
    ID            VARCHAR2(36 char) not null
        constraint IND_D45B602F3B03325E
        primary key,
    NAME          VARCHAR2(50 char),
    NOTE          CLOB,
    WHO_CREATED   VARCHAR2(36 char),
    WHEN_CREATED  VARCHAR2(20 char),
    WHO_MODIFIED  VARCHAR2(36 char),
    WHEN_MODIFIED VARCHAR2(20 char),
    DELETED       NUMBER default 0,
    TAG           VARCHAR2(100 char),
    APP_ID        VARCHAR2(36 char)
);

create table DEV_VIEW_MODEL_FIELD
(
    ID             VARCHAR2(36 char) not null
        constraint IND_B7F8EC3B5B6D845F
        primary key,
    VIEW_MODEL_ID  VARCHAR2(36 char),
    FIELD          VARCHAR2(50 char),
    LABEL          VARCHAR2(50 char),
    TYPE           VARCHAR2(20 char),
    FORMAT_TYPE    VARCHAR2(20 char),
    FORMAT_PATTERN VARCHAR2(50 char),
    DEFAULT_TEXT   VARCHAR2(50 char),
    WHO_CREATED    VARCHAR2(36 char),
    WHEN_CREATED   VARCHAR2(20 char),
    WHO_MODIFIED   VARCHAR2(36 char),
    WHEN_MODIFIED  TIMESTAMP(6),
    HIDDEN         VARCHAR2(20 char) default '0',
    ORDER_NUM      NUMBER            default 0,
    APP_ID         VARCHAR2(36 char)
);

create table DEV_VIEW_MODEL_FLOW
(
    ID            VARCHAR2(36 char) not null
        constraint IND_69EC72C50F2576E3
        primary key,
    FLOW_ID       VARCHAR2(36 char),
    VIEW_MODEL_ID VARCHAR2(36 char),
    WHO_CREATED   VARCHAR2(36 char),
    WHEN_CREATED  VARCHAR2(20 char),
    WHO_MODIFIED  VARCHAR2(36 char),
    WHEN_MODIFIED VARCHAR2(20 char),
    APP_ID        VARCHAR2(36 char)
);

create table ext_plugin_interface
(
    id          varchar(255)                           not null
        primary key,
    name        varchar(255)                           ,
    resp_type   varchar(255) default ''                ,
    content     CLOB                                ,
    description varchar(1024)                         ,
    plugin_id   varchar(255)                           ,
    create_time timestamp    default CURRENT_TIMESTAMP ,
    create_user varchar(255)                           ,
    update_time timestamp    default CURRENT_TIMESTAMP ,
    update_user varchar(255)                           ,
    deleted     int          default 0
);




create table ext_plugin_tree
(
    id          varchar(255)
        primary key,
    ext_name    varchar(255)                     ,
    jar_name    varchar(255)                       ,
    type        int                                ,
    create_time timestamp default CURRENT_TIMESTAMP,
    update_time timestamp default CURRENT_TIMESTAMP ,
    create_user varchar(255)                        ,
    update_user varchar(255)                        ,
    status      int       default 0               ,
    name        varchar(255)                        ,
    clazz_name  varchar(255)                        ,
    description CLOB                              ,
    check_time  timestamp
);

create table KFAAS_LIB
(
    JARNAME    VARCHAR2(255 char) not null
        constraint KFAAS_LIB_PK_EXT_PLUGIN_TREE
        primary key,
    CREATETIME TIMESTAMP(6) default CURRENT_TIMESTAMP,
    UPDATETIME TIMESTAMP(6) default CURRENT_TIMESTAMP,
    CREATEUSER VARCHAR2(255 char),
    UPDATEUSER VARCHAR2(255 char),
    STATUS     NUMBER       default 0
);

create table OPEN_ACCOUNT
(
    ID            VARCHAR2(36 char) not null
        constraint OPEN_ACCOUNT_PK_KFAAS_LIB
        primary key,
    ACCESS_NAME   VARCHAR2(128 char),
    ACCESS_ID     VARCHAR2(36 char),
    AUTH_TYPE     NUMBER,
    SIGN_KEY      VARCHAR2(50 char),
    VALIDATE_SIGN NUMBER default 0,
    VALID_DATE    VARCHAR2(20 char),
    INVALID_DATE  VARCHAR2(20 char),
    STATUS        NUMBER default 1,
    AUTH_PARAMS   CLOB,
    WHO_CREATED   VARCHAR2(36 char),
    WHEN_CREATED  VARCHAR2(20 char),
    WHO_MODIFIED  VARCHAR2(36 char),
    WHEN_MODIFIED VARCHAR2(20 char)
);

create table OPEN_ACCOUNT_API
(
    ID           VARCHAR2(36 char) not null
        constraint IND_18E26ABA9325DC00
        primary key,
    ACCOUNT_ID   VARCHAR2(36),
    API_ID       VARCHAR2(36 char),
    WHEN_CREATED VARCHAR2(20 char),
    WHO_CREATED  VARCHAR2(36 char)
);

create table OPEN_API_LOG
(
    ID             VARCHAR2(36 char) not null
        constraint IND_5054CEE393E0EEE5
        primary key,
    ACCESS_ID      VARCHAR2(100 char),
    API_NAME       VARCHAR2(100 char),
    REQUEST_PARAMS CLOB,
    REQUEST_TIME   VARCHAR2(20 char),
    REQUEST_IP     VARCHAR2(20 char),
    USE_TIME       NUMBER(3),
    SUCCESS        NUMBER(3),
    ERROR_MESSAGE  VARCHAR2(255 char)
);

create table SYS_API
(
    ID                 VARCHAR2(36 char) default '' not null
        constraint SYS_API_PK_OPEN_API_LOG
        primary key,
    API_NAME           VARCHAR2(255 char),
    API_URL            VARCHAR2(128 char),
    API_NOTE           CLOB,
    API_TAGS           VARCHAR2(128 char),
    API_METHOD         VARCHAR2(36 char) default 'get',
    API_ARGV_TYPE      NUMBER,
    API_REQ_ARGV       CLOB,
    API_RSP_ARGV       CLOB,
    API_RESULT_HANDLER VARCHAR2(128 char),
    WHO_CREATED        VARCHAR2(36 char),
    WHEN_CREATED       VARCHAR2(20 char),
    WHO_MODIFIED       VARCHAR2(36 char),
    WHEN_MODIFIED      VARCHAR2(20 char),
    API_FLOW_ID        VARCHAR2(50 char),
    API_CODE           VARCHAR2(50 char),
    CALL_TYPE          NUMBER,
    APP_ID             VARCHAR2(36 char)
);

create table SYS_BASE
(
    ID            VARCHAR2(36 char) not null
        constraint SYS_BASE_PK_SYS_API
        primary key,
    NAME          VARCHAR2(50 char),
    CODE          VARCHAR2(50 char),
    NOTE          CLOB,
    WHO_CREATED   VARCHAR2(36 char),
    WHEN_CREATED  VARCHAR2(20 char),
    WHO_MODIFIED  VARCHAR2(36 char),
    WHEN_MODIFIED VARCHAR2(20 char),
    IS_TEST       NUMBER(3),
    APP_ID        VARCHAR2(36 char)
);

create table SYS_CONFIG
(
    ID            VARCHAR2(36 char) not null
        constraint SYS_CONFIG_PK_SYS_BASE
        primary key,
    NAME          VARCHAR2(255 char),
    CODE          VARCHAR2(255 char),
    VALUE         VARCHAR2(255 char),
    IS_SYS        NUMBER(3),
    NOTE          VARCHAR2(255 char),
    WHO_CREATED   VARCHAR2(36 char),
    WHEN_CREATED  VARCHAR2(20 char),
    WHO_MODIFIED  VARCHAR2(36 char),
    WHEN_MODIFIED VARCHAR2(20 char),
    VALUE_TYPE    NUMBER(3) default 0,
    APP_ID        VARCHAR2(36 char)
);

create table SYS_DATA_ACCESS
(
    ID            VARCHAR2(36 char) not null
        constraint SYS_DATA_ACCESS_PK_SYS_CONFIG
        primary key,
    NAME          VARCHAR2(50 char),
    STATUS        NUMBER(3),
    NOTE          CLOB,
    WHO_CREATED   VARCHAR2(36 char),
    WHEN_CREATED  VARCHAR2(20 char),
    WHO_MODIFIED  VARCHAR2(36 char),
    WHEN_MODIFIED VARCHAR2(20 char),
    APP_ID        VARCHAR2(36 char)
);

create table SYS_DATA_ACCESS_RESOURCE
(
    ID           VARCHAR2(36 char) not null
        constraint IND_4F3FAC411D998D88
        primary key,
    DATA_ID      VARCHAR2(36 char),
    ACCESS_ID    VARCHAR2(36 char),
    TABLE_NAME   VARCHAR2(50 char),
    WHO_CREATED  VARCHAR2(36 char),
    WHEN_CREATED VARCHAR2(20 char),
    APP_ID       VARCHAR2(36 char)
);

create table SYS_DATA_ACCESS_USER
(
    ID                 VARCHAR2(36 char) not null
        constraint IND_B9439577C148032B
        primary key,
    SYS_USER_ID        VARCHAR2(36 char),
    SYS_DATA_ACCESS_ID VARCHAR2(50 char),
    WHO_CREATED        VARCHAR2(36 char),
    WHEN_CREATED       VARCHAR2(20 char),
    APP_ID             VARCHAR2(36 char)
);

create table SYS_DATA_RESOURCE
(
    ID            VARCHAR2(36 char) not null
        constraint IND_C0094018357BBE24
        primary key,
    NAME          VARCHAR2(50 char),
    TABLE_NAME    VARCHAR2(50 char),
    LABEL_FIELD   VARCHAR2(50 char),
    VALUE_FIELD   VARCHAR2(50 char),
    QUERY_SQL     CLOB,
    IS_TREE       NUMBER(3),
    IS_ONLY_LEAF  NUMBER(3),
    STATUS        NUMBER(3),
    WHO_CREATED   VARCHAR2(36 char),
    WHEN_CREATED  VARCHAR2(20 char),
    WHO_MODIFIED  VARCHAR2(36 char),
    WHEN_MODIFIED VARCHAR2(20 char),
    EXTRA_SQL     CLOB,
    APP_ID        VARCHAR2(36 char)
);

create table SYS_DICT
(
    ID            VARCHAR2(36 char) not null
        constraint SYS_DICT_PK_SYS_DATA_RESOURCE
        primary key,
    NAME          VARCHAR2(255 char),
    CODE          VARCHAR2(255 char),
    NOTE          CLOB,
    WHO_CREATED   VARCHAR2(36 char),
    WHEN_CREATED  VARCHAR2(20 char),
    WHO_MODIFIED  VARCHAR2(36 char),
    WHEN_MODIFIED VARCHAR2(20 char),
    APP_ID        VARCHAR2(36 char)
);

create table SYS_DICT_ITEM
(
    ID            VARCHAR2(36 char) not null
        constraint SYS_DICT_ITEM_PK_SYS_DICT
        primary key,
    NAME          VARCHAR2(255 char),
    GROUP_NAME    VARCHAR2(255 char),
    SYS_DICT_ID   VARCHAR2(36 char),
    CODE          VARCHAR2(255 char),
    VALUE         VARCHAR2(20 char),
    ORDER_NUM     NUMBER,
    NOTE          CLOB,
    WHO_CREATED   VARCHAR2(36 char),
    WHEN_CREATED  VARCHAR2(20 char),
    WHO_MODIFIED  VARCHAR2(36 char),
    WHEN_MODIFIED VARCHAR2(20 char),
    APP_ID        VARCHAR2(36 char)
);


create table SYS_FILE
(
    ID                 VARCHAR2(36 char) not null
        constraint SYS_FILE_PK_SYS_DICT_ITEM
        primary key,
    FILE_NAME          VARCHAR2(100 char),
    FILE_ORIGINAL_NAME VARCHAR2(100 char),
    FILE_SIZE          NUMBER,
    FILE_EXT           CLOB,
    FILE_MD5           VARCHAR2(40 char),
    FILE_FROM          VARCHAR2(50 char),
    SAVE_TYPE          NUMBER,
    FILE_PATH          VARCHAR2(100 char),
    FILE_CONTENT       CLOB,
    WHO_CREATED        VARCHAR2(36 char),
    WHEN_CREATED       VARCHAR2(20 char),
    WHO_MODIFIED       VARCHAR2(36 char),
    WHEN_MODIFIED      VARCHAR2(20 char),
    APP_ID             VARCHAR2(36 char)
);
create table SYS_I18N
(
    ID            VARCHAR2(36 char)  not null
        constraint SYS_I18N_PK_SYS_FILE
        primary key,
    I18N_KEY      VARCHAR2(255 char) not null,
    MESSAGE       CLOB,
    APP_ID        VARCHAR2(36 char),
    WHEN_CREATED  VARCHAR2(20 char),
    WHO_CREATED   VARCHAR2(36 char),
    WHEN_MODIFIED VARCHAR2(20 char),
    WHO_MODIFIED  VARCHAR2(36 char)
);
create table SYS_LOGIC_FLOW
(
    ID                  VARCHAR2(36 char) not null
        constraint SYS_LOGIC_FLOW_PK_SYS_I18N
        primary key,
    NAME                VARCHAR2(255 char),
    FLOW_ID             VARCHAR2(36 char),
    APPLICATION_ID      VARCHAR2(36 char),
    TAGS                VARCHAR2(255 char),
    IN_ARGV             CLOB,
    OUT_ARGV            CLOB,
    SUB_FLOW_IDS        CLOB,
    NOTE                VARCHAR2(255 char),
    WHO_CREATED         VARCHAR2(36 char),
    WHEN_CREATED        VARCHAR2(20 char),
    WHO_MODIFIED        VARCHAR2(36 char),
    WHEN_MODIFIED       VARCHAR2(20 char),
    APP_ID              VARCHAR2(36 char),
    DEFAULT_SOURCE_NAME VARCHAR2(100 char)
);

create table sys_logic_history
(
    id               varchar(36) not null
        primary key,
    flow_id          varchar(36) null ,
    flow_json        clob    null ,
    when_created     timestamp   null ,
    who_created      varchar(36) null ,
    version_tag      varchar(50) null ,
    version_tag_time varchar(30) null
);
create table SYS_LOGIC_TEMPLATE
(
    ID            VARCHAR2(36 char) not null
        constraint IND_7785ED58DD2228CF
        primary key,
    NAME          VARCHAR2(255 char),
    MODULE_ID     VARCHAR2(36 char),
    DESCRIPTION   CLOB,
    NODES         CLOB,
    LINKS         CLOB,
    APP_ID        VARCHAR2(36 char),
    WHEN_CREATED  VARCHAR2(50 char),
    WHO_CREATED   VARCHAR2(36 char),
    WHEN_MODIFIED VARCHAR2(50 char),
    WHO_MODIFIED  VARCHAR2(36 char)
);

create table SYS_LOGIN_LOG
(
    ID               VARCHAR2(36 char) not null
        constraint IND_92DBFF804AA263E0
        primary key,
    OPERATE_TIME     VARCHAR2(20 char),
    OPERATOR         VARCHAR2(36 char),
    IP               VARCHAR2(20 char),
    TIMES            NUMBER,
    RESPONSE_CODE    NUMBER,
    RESPONSE_MESSAGE VARCHAR2(100 char),
    WHEN_CREATED     VARCHAR2(20 char)
);


create table SYS_MENU
(
    ID               VARCHAR2(36 char)        not null
        constraint SYS_MENU_PK_SYS_LOGIN_LOG
        primary key,
    NAME             VARCHAR2(255 char)        not null,
    PARENT_ID        VARCHAR2(36 char),
    ICON             VARCHAR2(50 char),
    CODE             VARCHAR2(50 char),
    ROUTER_PATH      VARCHAR2(255 char),
    COMPONENT_PATH   VARCHAR2(255 char),
    IS_HIDDEN        NUMBER(3)    default 0   not null,
    MENU_TYPE        CHAR(1 char) default '0' not null,
    API_CODES        VARCHAR2(255 char),
    OPEN_MODE        NUMBER(3)                not null,
    KEEP_ALIVE       NUMBER(3),
    PATH             CLOB                     not null,
    ORDER_NUM        NUMBER       default 0   not null,
    STATUS           NUMBER(3)    default 1   not null,
    WHO_CREATED      VARCHAR2(36 char)        not null,
    WHEN_CREATED     VARCHAR2(20 char)        not null,
    WHO_MODIFIED     VARCHAR2(36 char)        not null,
    WHEN_MODIFIED    VARCHAR2(20 char)        not null,
    APP_ID           VARCHAR2(36 char),
    DATA_TYPE        NUMBER(3),
    THEME            VARCHAR2(50 char),
    PAGE_TYPE        NUMBER(3),
    SIDEBAR_NAV_MODE NUMBER(3),
    TOP_NAV_MODE     NUMBER(3),
    MAIN_MODE        NUMBER(3),
    PAGE_ID          VARCHAR2(36 char),
    FULL_PATH        VARCHAR2(255 char),
    IS_DEV           NUMBER(3)    default 0
);
create table SYS_NOTICE
(
    ID            VARCHAR2(36 char)   not null
        constraint SYS_NOTICE_PK_SYS_MENU
        primary key,
    TITLE         VARCHAR2(255 char),
    CONTENT       CLOB,
    TYPE          NUMBER(3),
    STATUS        NUMBER(3) default 1 not null,
    WHO_CREATED   VARCHAR2(36 char),
    WHEN_CREATED  VARCHAR2(20 char),
    WHO_MODIFIED  VARCHAR2(36 char),
    WHEN_MODIFIED VARCHAR2(20 char),
    DELETED       NUMBER(3),
    APP_ID        VARCHAR2(36 char)
);

create table SYS_NOTICE_RECORD
(
    ID            VARCHAR2(36 char)   not null
        constraint IND_1ADC39F23AD032B1
        primary key,
    FROM_WHO      VARCHAR2(36 char),
    TO_WHO        VARCHAR2(36 char)   not null,
    NOTICE_ID     VARCHAR2(36 char),
    IS_READ       NUMBER(3) default 0 not null,
    READ_TIME     VARCHAR2(30 char),
    NOTICE_TIME   VARCHAR2(30 char)   not null,
    TITLE         VARCHAR2(255 char),
    CONTENT       CLOB,
    TO_WHO_NAME   VARCHAR2(255 char),
    FROM_WHO_NAME VARCHAR2(255 char),
    APP_ID        VARCHAR2(36 char)
);

create table SYS_ONLINE_USER
(
    ID           VARCHAR2(36 char) not null
        constraint IND_534210AC1B2C86A1
        primary key,
    USER_ID      VARCHAR2(36 char),
    LOGIN_TIME   VARCHAR2(20 char),
    LOGIN_IP     VARCHAR2(20 char),
    LOGIN_TOKEN  VARCHAR2(1024 char),
    EXPIRE_TIME  VARCHAR2(20 char),
    WHEN_CREATED VARCHAR2(20 char),
    APP_ID       VARCHAR2(36 char)
);


create table SYS_OPERATE_LOG
(
    ID               VARCHAR2(36 char) not null
        constraint IND_7E50FF753E7784FC
        primary key,
    MODULE           VARCHAR2(100 char),
    ACTION           VARCHAR2(255 char),
    URL              VARCHAR2(255 char),
    OPERATE_TIME     VARCHAR2(20 char),
    OPERATOR         VARCHAR2(36 char),
    IP               VARCHAR2(20 char),
    TIMES            NUMBER,
    REQUEST_BODY     CLOB,
    RESPONSE_CODE    NUMBER,
    RESPONSE_MESSAGE VARCHAR2(100 char),
    WHEN_CREATED     VARCHAR2(20 char),
    APP_ID           VARCHAR2(36 char)
);


create table SYS_ROLE
(
    ID            VARCHAR2(36 char) not null
        constraint SYS_ROLE_PK_SYS_OPERATE_LOG
        primary key,
    NAME          VARCHAR2(255 char) not null,
    CODE          VARCHAR2(255 char) not null,
    NOTE          CLOB,
    STATUS        NUMBER(3),
    WHO_CREATED   VARCHAR2(36 char) ,
    WHEN_CREATED  VARCHAR2(20 char),
    WHO_MODIFIED  VARCHAR2(36 char),
    WHEN_MODIFIED VARCHAR2(20 char),
    APP_ID        VARCHAR2(36 char)
);


create table SYS_ROLE_MENU
(
    ID           VARCHAR2(36 char) not null
        constraint SYS_ROLE_MENU_PK_SYS_ROLE
        primary key,
    SYS_MENU_ID  VARCHAR2(36 char) not null,
    SYS_ROLE_ID  VARCHAR2(36 char) not null,
    WHO_CREATED  VARCHAR2(36 char) not null,
    WHEN_CREATED VARCHAR2(20 char) not null,
    APP_ID       VARCHAR2(36 char)
);

create table SYS_TASK
(
    ID                  VARCHAR2(36 char) not null
        constraint SYS_TASK_PK_SYS_ROLE_MENU
        primary key,
    NAME                VARCHAR2(100 char),
    CRON                VARCHAR2(50 char),
    "DISTRIBUTED"         NUMBER(3),
    APPLICATION_ID      VARCHAR2(36 char),
    TASK_TYPE           NUMBER(3) default 1,
    TASK_RESOURCE_ID    VARCHAR2(36 char),
    CLASS_NAME          VARCHAR2(255 char),
    ENABLE              NUMBER(3) default 1,
    LAST_EXECUTE_STATUS NUMBER(3),
    LAST_EXECUTE_MSG    CLOB,
    LAST_EXECUTE_TIME   VARCHAR2(20 char),
    LAST_EXECUTE_TAKE   NUMBER,
    LOCK_STATUS         NUMBER(3) default 0,
    LOCK_FOR_MOST       NUMBER    default 30,
    LOCK_FOR_LEAST      NUMBER    default 1,
    LOCK_FOR_TIME       VARCHAR2(20 char),
    NOTE                CLOB,
    WHO_CREATED         VARCHAR2(36 char),
    WHEN_CREATED        VARCHAR2(20 char),
    WHO_MODIFIED        VARCHAR2(36 char),
    WHEN_MODIFIED       VARCHAR2(20 char),
    APP_ID              VARCHAR2(36 char)
);

create table SYS_UNIT
(
    ID            VARCHAR2(36 char)   not null
        constraint SYS_UNIT_PK_SYS_TASK
        primary key,
    NAME          VARCHAR2(255 char)   not null,
    PARENT_ID     VARCHAR2(36 char),
    PATH          CLOB                not null,
    LEADER        VARCHAR2(255 char),
    MOBILE        VARCHAR2(20 char),
    EMAIL         VARCHAR2(50 char),
    STATUS        NUMBER(3) default 1 not null,
    NOTE          CLOB,
    ORDER_NUM     NUMBER    default 0,
    WHO_CREATED   VARCHAR2(36 char)   not null,
    WHEN_CREATED  VARCHAR2(20 char)   not null,
    WHO_MODIFIED  VARCHAR2(36 char)   not null,
    WHEN_MODIFIED VARCHAR2(20 char)   not null,
    APP_ID        VARCHAR2(36 char)
);

create table SYS_USER
(
    ID            VARCHAR2(36 char) not null
        constraint SYS_USER_PK_SYS_UNIT
        primary key,
    USERNAME      VARCHAR2(255 char) not null,
    PASSWORD      VARCHAR2(256 char),
    REAL_NAME     VARCHAR2(255 char) not null,
    MOBILE        VARCHAR2(20 char),
    EMAIL         VARCHAR2(50 char),
    SEX           NUMBER(3),
    SYS_UNIT_ID   VARCHAR2(36 char),
    POST          VARCHAR2(50 char),
    STATUS        NUMBER(3) default 1,
    NOTE          CLOB,
    WHO_CREATED   VARCHAR2(36 char),
    WHEN_CREATED  VARCHAR2(20 char),
    WHO_MODIFIED  VARCHAR2(36 char),
    WHEN_MODIFIED VARCHAR2(20 char),
    DELETED       NUMBER(3) default 0,
    AVATAR        VARCHAR2(255 char),
    APP_ID        VARCHAR2(36 char)
);


create table SYS_USER_ROLE
(
    ID           VARCHAR2(36 char) not null
        constraint SYS_USER_ROLE_PK_SYS_USER
        primary key,
    SYS_USER_ID  VARCHAR2(36 char) not null,
    SYS_ROLE_ID  VARCHAR2(36 char) not null,
    WHO_CREATED  VARCHAR2(36 char) not null,
    WHEN_CREATED VARCHAR2(20 char) not null,
    APP_ID       VARCHAR2(36 char)
);


create table SYS_VIEW_MODEL
(
    ID            VARCHAR2(36 char) not null
        constraint IND_804CF57D84E815EB
        primary key,
    NAME          VARCHAR2(50 char),
    NOTE          CLOB,
    WHO_CREATED   VARCHAR2(36 char),
    WHEN_CREATED  VARCHAR2(20 char),
    WHO_MODIFIED  VARCHAR2(36 char),
    WHEN_MODIFIED VARCHAR2(20 char),
    DELETED       NUMBER default 0,
    TAG           VARCHAR2(100 char)
);

create table SYS_VIEW_MODEL_FIELD
(
    ID             VARCHAR2(36 char) not null,
    VIEW_MODEL_ID  VARCHAR2(36 char),
    FIELD          VARCHAR2(50 char),
    LABEL          VARCHAR2(50 char),
    TYPE           VARCHAR2(20 char),
    FORMAT_TYPE    VARCHAR2(20 char),
    FORMAT_PATTERN VARCHAR2(50 char),
    DEFAULT_TEXT   VARCHAR2(50 char),
    WHO_CREATED    VARCHAR2(36 char),
    WHEN_CREATED   VARCHAR2(20 char),
    WHO_MODIFIED   VARCHAR2(36 char),
    WHEN_MODIFIED  VARCHAR2(20 char),
    HIDDEN         NUMBER default 0,
    ORDER_NUM      NUMBER default 0
);

create table SYS_VIEW_MODEL_FLOW
(
    ID            VARCHAR2(36 char) not null
        constraint IND_8B74B862559750DE
        primary key,
    FLOW_ID       VARCHAR2(36 char),
    VIEW_MODEL_ID VARCHAR2(36 char),
    WHO_CREATED   VARCHAR2(36 char),
    WHEN_CREATED  VARCHAR2(20 char),
    WHO_MODIFIED  VARCHAR2(36 char),
    WHEN_MODIFIED VARCHAR2(20 char)
);

create table dev_faas_node
(
    id            varchar(32)    ,
    name          varchar(90)   ,
    type_id       varchar(32)    ,
    template      CLOB ,
    icon          varchar(32)   ,
    pub_status    int           ,
    order_num     varchar(255)   ,
    when_created  varchar(255)   ,
    who_created   varchar(255)   ,
    when_modified varchar(255)   ,
    who_modified  varchar(255)   ,
    config        CLOB        ,
    code          varchar(255)
);

create table dev_faas_node_type
(
    id            varchar(32)  ,
    name          varchar(90)  ,
    pub_status    int         ,
    icon          varchar(32)  ,
    when_created  varchar(255) ,
    who_created   varchar(255) ,
    when_modified varchar(255) ,
    who_modified  varchar(255)
);



-- 以下是来自 version_3_1.sql 的内容 --

INSERT INTO DEV_APPLICATION (ID, "NAME", SHORT_NAME, DESCRIPTION, ENABLE_STATUS, DEV_STATUS, VERSION, WHO_IN_CHARGE, SYSTEM_LOGO, APP_TYPE, DEFAULT_PATH, DELETED, WHEN_CREATED, WHO_CREATED, WHEN_MODIFIED, WHO_MODIFIED, FAAS_PORT, PINE_PORT, DATA_SOURCE, APP_PUBLIC_TYPE) VALUES ('064b3b44b85a45fe87fcce88d72b2519', '青松开发管理', 'dev', '青松K-Pine 1.0', 1, 1, 'v1.0.0', null, '3508f1b2f1f442a584c4ffe118e2a9dd', '0', '/', 0, '2022-02-13 19:53:19', null, '2022-08-24 16:56:26', 'a7d903b65e8c42479b9774db664f9468', null, null, null, 2);
INSERT INTO DEV_OTA_CHANNEL (ID, CHANNEL_NAME, CHANNEL_URL, AUTH_TOKEN, SIGN_SECRET, MASTER, NOTE, WHEN_CREATED, WHO_CREATED, WHEN_MODIFIED, WHO_MODIFIED) VALUES ('8bab3a61164740c5958e4652d344f3f9', '主通道', 'http://10.11.2.115:18882', 'yebKhNp2prcAXHkNFX4M8HDZc5ybsMep', '8T4xxmArYXjQWJjK', 1, '青松应用', '2022-08-23 15:56:38', '94123ca363dc4dfaa62a6bb5dcd3bf50', '2022-08-23 18:41:46', '94123ca363dc4dfaa62a6bb5dcd3bf50');
INSERT INTO SYS_API (ID, API_NAME, API_URL, API_NOTE, API_TAGS, API_METHOD, API_ARGV_TYPE, API_REQ_ARGV, API_RSP_ARGV, API_RESULT_HANDLER, WHO_CREATED, WHEN_CREATED, WHO_MODIFIED, WHEN_MODIFIED, API_FLOW_ID, API_CODE, CALL_TYPE, APP_ID) VALUES ('285a470cb68b451f95938b3cf9e3a7eb', '团队删除', '/v1/admin-team/del', null, 'admin', 'delete', null, null, null, null, '7aed8c297a6940f681c26eb6ab68893d', '2022-06-13 01:50:56', null, '2022-07-14 18:07:03', '8f825dac514845ea87c955767cf3c938', null, 2, '064b3b44b85a45fe87fcce88d72b2519');
INSERT INTO SYS_API (ID, API_NAME, API_URL, API_NOTE, API_TAGS, API_METHOD, API_ARGV_TYPE, API_REQ_ARGV, API_RSP_ARGV, API_RESULT_HANDLER, WHO_CREATED, WHEN_CREATED, WHO_MODIFIED, WHEN_MODIFIED, API_FLOW_ID, API_CODE, CALL_TYPE, APP_ID) VALUES ('285dc1e328a6435491d96f1eccefe17e', '通用下拉删除', '/v1/sys/hint-select/delete', null, null, 'post', null, null, null, null, 'd4040ae0800844d99406157b798bae01', '2022-07-28 14:04:13', 'd4040ae0800844d99406157b798bae01', '2022-07-28 14:04:13', 'a5694ac62ca945a3aae26c673db81f79', null, 2, '064b3b44b85a45fe87fcce88d72b2519');
INSERT INTO SYS_API (ID, API_NAME, API_URL, API_NOTE, API_TAGS, API_METHOD, API_ARGV_TYPE, API_REQ_ARGV, API_RSP_ARGV, API_RESULT_HANDLER, WHO_CREATED, WHEN_CREATED, WHO_MODIFIED, WHEN_MODIFIED, API_FLOW_ID, API_CODE, CALL_TYPE, APP_ID) VALUES ('2924a3361a9e4c9598e3578f52004760', '流水号新增', '/v1/number/save', null, null, 'post', null, null, null, null, 'a7d903b65e8c42479b9774db664f9468', '2022-04-22 17:00:01', null, '2022-07-14 18:07:03', '78be66386a90490a91c080e7d052e4ce', null, 2, '064b3b44b85a45fe87fcce88d72b2519');
INSERT INTO SYS_API (ID, API_NAME, API_URL, API_NOTE, API_TAGS, API_METHOD, API_ARGV_TYPE, API_REQ_ARGV, API_RSP_ARGV, API_RESULT_HANDLER, WHO_CREATED, WHEN_CREATED, WHO_MODIFIED, WHEN_MODIFIED, API_FLOW_ID, API_CODE, CALL_TYPE, APP_ID) VALUES ('2b9520f42cad44ec890373f48ee63f95', '函数库-修改', '/v1/dev-function/edit', null, null, 'put', null, null, null, null, '7aed8c297a6940f681c26eb6ab68893d', '2022-05-31 18:58:21', null, '2022-07-14 18:07:03', 'cedbe69f17174f3f8b4b36aeb5fec9f2', null, 2, '064b3b44b85a45fe87fcce88d72b2519');
INSERT INTO SYS_API (ID, API_NAME, API_URL, API_NOTE, API_TAGS, API_METHOD, API_ARGV_TYPE, API_REQ_ARGV, API_RSP_ARGV, API_RESULT_HANDLER, WHO_CREATED, WHEN_CREATED, WHO_MODIFIED, WHEN_MODIFIED, API_FLOW_ID, API_CODE, CALL_TYPE, APP_ID) VALUES ('2de35b1b662f4a549e91be2ea0b85df2', '获取pid', '/getPid', null, null, 'get', null, null, null, null, 'd4040ae0800844d99406157b798bae01', '2022-08-05 17:16:03', 'd4040ae0800844d99406157b798bae01', '2022-08-05 17:16:03', '3ac511f0f40b467099e42bfef906bb63', null, 2, '064b3b44b85a45fe87fcce88d72b2519');
INSERT INTO SYS_API (ID, API_NAME, API_URL, API_NOTE, API_TAGS, API_METHOD, API_ARGV_TYPE, API_REQ_ARGV, API_RSP_ARGV, API_RESULT_HANDLER, WHO_CREATED, WHEN_CREATED, WHO_MODIFIED, WHEN_MODIFIED, API_FLOW_ID, API_CODE, CALL_TYPE, APP_ID) VALUES ('2eb98bd6212145339951503cf7e4ad34', 'pine-删除能力关联', '/v1/powers/removeLinks', null, null, 'post', null, null, null, null, '056fb0eeb9a44cb0953534b4c0ca01fa', '2022-05-31 20:16:24', null, '2022-07-14 18:07:03', 'e8c724cb9d504b87b108cc7b02b62531', null, 2, '064b3b44b85a45fe87fcce88d72b2519');
INSERT INTO SYS_API (ID, API_NAME, API_URL, API_NOTE, API_TAGS, API_METHOD, API_ARGV_TYPE, API_REQ_ARGV, API_RSP_ARGV, API_RESULT_HANDLER, WHO_CREATED, WHEN_CREATED, WHO_MODIFIED, WHEN_MODIFIED, API_FLOW_ID, API_CODE, CALL_TYPE, APP_ID) VALUES ('3155da6a4918414999fc5f150ee273f1', 'pine-应用管理-详情', '/v1/dev-app/get/{id}', null, 'pine', 'get', null, null, null, null, '7aed8c297a6940f681c26eb6ab68893d', '2022-04-08 17:52:56', null, '2022-07-14 18:07:03', 'b76da1b4366749309bb0521e65153823', null, 2, '064b3b44b85a45fe87fcce88d72b2519');
INSERT INTO SYS_API (ID, API_NAME, API_URL, API_NOTE, API_TAGS, API_METHOD, API_ARGV_TYPE, API_REQ_ARGV, API_RSP_ARGV, API_RESULT_HANDLER, WHO_CREATED, WHEN_CREATED, WHO_MODIFIED, WHEN_MODIFIED, API_FLOW_ID, API_CODE, CALL_TYPE, APP_ID) VALUES ('33e05095497e4454976ad93862250226', '工单编号查询', '/v1/wokr-number', null, null, 'get', null, null, null, null, 'd4040ae0800844d99406157b798bae01', '2022-07-27 14:33:09', 'd4040ae0800844d99406157b798bae01', '2022-07-27 14:33:09', '1c9f58dbf3ee4ab09f41aa662188c6cc', null, 2, '064b3b44b85a45fe87fcce88d72b2519');
INSERT INTO SYS_API (ID, API_NAME, API_URL, API_NOTE, API_TAGS, API_METHOD, API_ARGV_TYPE, API_REQ_ARGV, API_RSP_ARGV, API_RESULT_HANDLER, WHO_CREATED, WHEN_CREATED, WHO_MODIFIED, WHEN_MODIFIED, API_FLOW_ID, API_CODE, CALL_TYPE, APP_ID) VALUES ('349114a989bf440ab4ebea38581b358a', '逻辑编排模板管理-删除', '/v1/dev-flow-template/del', null, null, 'delete', null, null, null, null, '7aed8c297a6940f681c26eb6ab68893d', '2022-05-30 16:33:44', null, '2022-07-14 18:07:03', '6b36dc6ac02549a5a8583116f595ae2a', null, 2, '064b3b44b85a45fe87fcce88d72b2519');
INSERT INTO SYS_API (ID, API_NAME, API_URL, API_NOTE, API_TAGS, API_METHOD, API_ARGV_TYPE, API_REQ_ARGV, API_RSP_ARGV, API_RESULT_HANDLER, WHO_CREATED, WHEN_CREATED, WHO_MODIFIED, WHEN_MODIFIED, API_FLOW_ID, API_CODE, CALL_TYPE, APP_ID) VALUES ('3553cdf1d7e4409888803975ab679223', '通用下拉SQL查询', '/sys/common/hint', null, '下拉,有,sys', 'get', null, null, null, null, '3db2cfb7cbb84113a902808aef44f2a0', '2022-07-08 14:31:30', '3db2cfb7cbb84113a902808aef44f2a0', '2022-07-11 16:38:14', 'dc941ed4ae4e4768ad16388f6f0e821f', null, 2, '064b3b44b85a45fe87fcce88d72b2519');
INSERT INTO SYS_API (ID, API_NAME, API_URL, API_NOTE, API_TAGS, API_METHOD, API_ARGV_TYPE, API_REQ_ARGV, API_RSP_ARGV, API_RESULT_HANDLER, WHO_CREATED, WHEN_CREATED, WHO_MODIFIED, WHEN_MODIFIED, API_FLOW_ID, API_CODE, CALL_TYPE, APP_ID) VALUES ('3736ec39b69840a08a4a2dd3a1dfb75a', 'pine-页面列表', '/v1/dev-page/query', null, null, 'get', null, null, null, null, '7aed8c297a6940f681c26eb6ab68893d', '2022-06-27 11:04:25', null, '2022-07-14 18:07:04', '15e4220b770749b590da2fe216f9a9bc', null, 2, '064b3b44b85a45fe87fcce88d72b2519');
INSERT INTO SYS_API (ID, API_NAME, API_URL, API_NOTE, API_TAGS, API_METHOD, API_ARGV_TYPE, API_REQ_ARGV, API_RSP_ARGV, API_RESULT_HANDLER, WHO_CREATED, WHEN_CREATED, WHO_MODIFIED, WHEN_MODIFIED, API_FLOW_ID, API_CODE, CALL_TYPE, APP_ID) VALUES ('3ac23dc5179448e7badce0a71492692a', 'pine-能力树-逻辑和函数', '/v1/dev-power-tree/flowsAndFuns', null, 'PINE', 'get', null, null, null, null, '056fb0eeb9a44cb0953534b4c0ca01fa', '2022-05-13 10:01:51', null, '2022-07-14 18:07:04', 'fbe98b22cc2541fc837f8d35238f5301', null, 2, '064b3b44b85a45fe87fcce88d72b2519');
INSERT INTO SYS_API (ID, API_NAME, API_URL, API_NOTE, API_TAGS, API_METHOD, API_ARGV_TYPE, API_REQ_ARGV, API_RSP_ARGV, API_RESULT_HANDLER, WHO_CREATED, WHEN_CREATED, WHO_MODIFIED, WHEN_MODIFIED, API_FLOW_ID, API_CODE, CALL_TYPE, APP_ID) VALUES ('3bf38349337f47d4b68bfc7016ec835e', '导出', '/v1/sys-kdb-flows/export', null, '导出', 'get', null, null, null, null, '056fb0eeb9a44cb0953534b4c0ca01fa', '2022-03-31 09:34:33', null, '2022-07-14 18:07:04', 'e574bed567814183b90bfe5994340a29', null, 2, '064b3b44b85a45fe87fcce88d72b2519');
INSERT INTO SYS_API (ID, API_NAME, API_URL, API_NOTE, API_TAGS, API_METHOD, API_ARGV_TYPE, API_REQ_ARGV, API_RSP_ARGV, API_RESULT_HANDLER, WHO_CREATED, WHEN_CREATED, WHO_MODIFIED, WHEN_MODIFIED, API_FLOW_ID, API_CODE, CALL_TYPE, APP_ID) VALUES ('3c245ac6e60542af9bc4be0bdf00c780', 'pine-修改菜单', '/v1/dev-menu', null, 'pine', 'put', null, null, null, null, '7aed8c297a6940f681c26eb6ab68893d', '2022-04-01 17:42:34', null, '2022-07-14 18:07:04', '26a3eae0f0d84cbdb953966a3e4f0c73', null, 2, '064b3b44b85a45fe87fcce88d72b2519');
INSERT INTO SYS_API (ID, API_NAME, API_URL, API_NOTE, API_TAGS, API_METHOD, API_ARGV_TYPE, API_REQ_ARGV, API_RSP_ARGV, API_RESULT_HANDLER, WHO_CREATED, WHEN_CREATED, WHO_MODIFIED, WHEN_MODIFIED, API_FLOW_ID, API_CODE, CALL_TYPE, APP_ID) VALUES ('3caeae42485a478b996fa286ae929395', '测试逻辑编排选择', '2222', null, null, 'get', null, null, null, null, '94123ca363dc4dfaa62a6bb5dcd3bf50', '2022-08-08 11:29:12', '94123ca363dc4dfaa62a6bb5dcd3bf50', '2022-08-08 11:29:12', '779860cb14214694b9c84a37b68b6d32', null, 2, '064b3b44b85a45fe87fcce88d72b2519');
INSERT INTO SYS_API (ID, API_NAME, API_URL, API_NOTE, API_TAGS, API_METHOD, API_ARGV_TYPE, API_REQ_ARGV, API_RSP_ARGV, API_RESULT_HANDLER, WHO_CREATED, WHEN_CREATED, WHO_MODIFIED, WHEN_MODIFIED, API_FLOW_ID, API_CODE, CALL_TYPE, APP_ID) VALUES ('3dec9cb07f454175bcc4cc7914d4c79e', 'pine-在线Excel更新', '/v1/sys-excel/edit', null, 'excel', 'put', null, null, null, null, '7aed8c297a6940f681c26eb6ab68893d', '2022-07-22 09:15:16', '7aed8c297a6940f681c26eb6ab68893d', '2022-07-22 09:15:16', 'b11ba48b2ce643538775e6ba9e5482e3', null, 2, '064b3b44b85a45fe87fcce88d72b2519');
INSERT INTO SYS_API (ID, API_NAME, API_URL, API_NOTE, API_TAGS, API_METHOD, API_ARGV_TYPE, API_REQ_ARGV, API_RSP_ARGV, API_RESULT_HANDLER, WHO_CREATED, WHEN_CREATED, WHO_MODIFIED, WHEN_MODIFIED, API_FLOW_ID, API_CODE, CALL_TYPE, APP_ID) VALUES ('3e1b3be8571349bd92bf572055eb7d0c', 'ext-插件jar包检测', '/v1/ext_plugin/check', null, null, 'post', null, null, null, null, '63454c98827e4a0384abf30e0e6eef54', '2022-04-02 16:08:55', null, '2022-07-14 18:07:04', '4c8236e167f649598b4ccb483d6a37af', null, 2, '064b3b44b85a45fe87fcce88d72b2519');
INSERT INTO SYS_API (ID, API_NAME, API_URL, API_NOTE, API_TAGS, API_METHOD, API_ARGV_TYPE, API_REQ_ARGV, API_RSP_ARGV, API_RESULT_HANDLER, WHO_CREATED, WHEN_CREATED, WHO_MODIFIED, WHEN_MODIFIED, API_FLOW_ID, API_CODE, CALL_TYPE, APP_ID) VALUES ('401e7e73222949f38f642bc1861f8d93', '获取用户', '/sys/user/search', null, null, 'post', null, null, null, null, 'd4040ae0800844d99406157b798bae01', '2022-06-06 11:43:48', null, '2022-07-14 18:07:04', '1d997eaaaa4e44449f3f0fcd96544caa', null, 2, '064b3b44b85a45fe87fcce88d72b2519');
INSERT INTO SYS_API (ID, API_NAME, API_URL, API_NOTE, API_TAGS, API_METHOD, API_ARGV_TYPE, API_REQ_ARGV, API_RSP_ARGV, API_RESULT_HANDLER, WHO_CREATED, WHEN_CREATED, WHO_MODIFIED, WHEN_MODIFIED, API_FLOW_ID, API_CODE, CALL_TYPE, APP_ID) VALUES ('427467ae303d47d0bb763d1af3643143', 'pine-创建应用', '/v1/dev-app', null, null, 'post', null, null, null, null, '7aed8c297a6940f681c26eb6ab68893d', '2022-06-12 21:40:22', null, '2022-07-14 18:07:04', 'd2d9214af2684702b68a4fd757f5f86c', null, 2, '064b3b44b85a45fe87fcce88d72b2519');
INSERT INTO SYS_API (ID, API_NAME, API_URL, API_NOTE, API_TAGS, API_METHOD, API_ARGV_TYPE, API_REQ_ARGV, API_RSP_ARGV, API_RESULT_HANDLER, WHO_CREATED, WHEN_CREATED, WHO_MODIFIED, WHEN_MODIFIED, API_FLOW_ID, API_CODE, CALL_TYPE, APP_ID) VALUES ('42bc044f9cd0403eb0924d175223dcce', 'ext-接口插件批量删除', '/v1/ext_plugin/interface/delete', null, null, 'post', null, null, null, null, '63454c98827e4a0384abf30e0e6eef54', '2022-04-01 09:21:56', null, '2022-07-14 18:07:04', '56bf904b474b43eb9b1ee6c8bf2d0bf3', null, 2, '064b3b44b85a45fe87fcce88d72b2519');
INSERT INTO SYS_API (ID, API_NAME, API_URL, API_NOTE, API_TAGS, API_METHOD, API_ARGV_TYPE, API_REQ_ARGV, API_RSP_ARGV, API_RESULT_HANDLER, WHO_CREATED, WHEN_CREATED, WHO_MODIFIED, WHEN_MODIFIED, API_FLOW_ID, API_CODE, CALL_TYPE, APP_ID) VALUES ('43ab77b1ab1b4092ac1895b03e19e264', 'pine-能力查询', '/v1/powers/query-links', null, null, 'post', null, null, null, null, '056fb0eeb9a44cb0953534b4c0ca01fa', '2022-05-30 18:52:37', null, '2022-07-14 18:07:04', '912218b630ec4a56900bb41ffa712082', null, 2, '064b3b44b85a45fe87fcce88d72b2519');
INSERT INTO SYS_API (ID, API_NAME, API_URL, API_NOTE, API_TAGS, API_METHOD, API_ARGV_TYPE, API_REQ_ARGV, API_RSP_ARGV, API_RESULT_HANDLER, WHO_CREATED, WHEN_CREATED, WHO_MODIFIED, WHEN_MODIFIED, API_FLOW_ID, API_CODE, CALL_TYPE, APP_ID) VALUES ('4405e24f6cb547a3b9b75910f49ab4d4', 'pine-逻辑编排设计历史记录', '/v1/sys-logic-history/query', null, null, 'get', null, null, null, null, '7aed8c297a6940f681c26eb6ab68893d', '2022-07-13 15:49:52', '7aed8c297a6940f681c26eb6ab68893d', '2022-07-13 15:50:27', '0652ecfeccea467697e99d126ba66bba', null, 2, '064b3b44b85a45fe87fcce88d72b2519');
INSERT INTO SYS_API (ID, API_NAME, API_URL, API_NOTE, API_TAGS, API_METHOD, API_ARGV_TYPE, API_REQ_ARGV, API_RSP_ARGV, API_RESULT_HANDLER, WHO_CREATED, WHEN_CREATED, WHO_MODIFIED, WHEN_MODIFIED, API_FLOW_ID, API_CODE, CALL_TYPE, APP_ID) VALUES ('4417519fb8cf43bc90fd6d4427c92b3b', '临时', '/dev-test', null, null, 'get', null, null, null, null, '7aed8c297a6940f681c26eb6ab68893d', '2022-06-06 09:31:14', null, '2022-07-14 18:07:04', '84883106dfbd4ae9b88733786100862e', null, 2, '064b3b44b85a45fe87fcce88d72b2519');
INSERT INTO SYS_API (ID, API_NAME, API_URL, API_NOTE, API_TAGS, API_METHOD, API_ARGV_TYPE, API_REQ_ARGV, API_RSP_ARGV, API_RESULT_HANDLER, WHO_CREATED, WHEN_CREATED, WHO_MODIFIED, WHEN_MODIFIED, API_FLOW_ID, API_CODE, CALL_TYPE, APP_ID) VALUES ('46eb2c4a837b4055818509f83cfaa7b2', '国际化删除', '/v1/sys-i18ns/delete', null, null, 'post', null, null, null, null, 'd4040ae0800844d99406157b798bae01', '2022-06-08 20:37:24', null, '2022-07-14 18:07:05', '59dc83764be84735975675df7ebe018b', null, 2, '064b3b44b85a45fe87fcce88d72b2519');
INSERT INTO SYS_API (ID, API_NAME, API_URL, API_NOTE, API_TAGS, API_METHOD, API_ARGV_TYPE, API_REQ_ARGV, API_RSP_ARGV, API_RESULT_HANDLER, WHO_CREATED, WHEN_CREATED, WHO_MODIFIED, WHEN_MODIFIED, API_FLOW_ID, API_CODE, CALL_TYPE, APP_ID) VALUES ('4715d20fbc53498ba62b272ade6f03cb', 'pine-应用管理-导入', '/v1/dev-app/import', null, 'pine', 'post', null, null, null, null, '056fb0eeb9a44cb0953534b4c0ca01fa', '2022-04-11 17:35:24', null, '2022-07-14 18:07:05', 'a3fb10c16e0942479c42c0f848eab6fc', null, 2, '064b3b44b85a45fe87fcce88d72b2519');
INSERT INTO SYS_API (ID, API_NAME, API_URL, API_NOTE, API_TAGS, API_METHOD, API_ARGV_TYPE, API_REQ_ARGV, API_RSP_ARGV, API_RESULT_HANDLER, WHO_CREATED, WHEN_CREATED, WHO_MODIFIED, WHEN_MODIFIED, API_FLOW_ID, API_CODE, CALL_TYPE, APP_ID) VALUES ('485ffb8a3fad462a88244be7af32a371', 'pine-获取当前团队列表', '/v1/dev-team/get-teams', null, null, 'get', null, null, null, null, '7aed8c297a6940f681c26eb6ab68893d', '2022-04-15 05:36:04', null, '2022-07-14 18:07:05', 'ce2b9f1e604644f286f2f85107ded1bc', null, 2, '064b3b44b85a45fe87fcce88d72b2519');
INSERT INTO SYS_API (ID, API_NAME, API_URL, API_NOTE, API_TAGS, API_METHOD, API_ARGV_TYPE, API_REQ_ARGV, API_RSP_ARGV, API_RESULT_HANDLER, WHO_CREATED, WHEN_CREATED, WHO_MODIFIED, WHEN_MODIFIED, API_FLOW_ID, API_CODE, CALL_TYPE, APP_ID) VALUES ('4abc8d16157843c2be0b8142d2016015', '根据id查询帮助文档', '/v1/dev_document/query/byId', null, null, 'get', null, null, null, null, '37b9d0ee9b194c52a5d2a70fbb0cf65e', '2022-04-12 17:48:36', null, '2022-07-14 18:07:05', 'bbc54afeda044a8580dbe2b9b59ca8ae', null, 2, '064b3b44b85a45fe87fcce88d72b2519');
INSERT INTO SYS_API (ID, API_NAME, API_URL, API_NOTE, API_TAGS, API_METHOD, API_ARGV_TYPE, API_REQ_ARGV, API_RSP_ARGV, API_RESULT_HANDLER, WHO_CREATED, WHEN_CREATED, WHO_MODIFIED, WHEN_MODIFIED, API_FLOW_ID, API_CODE, CALL_TYPE, APP_ID) VALUES ('4d52d148edab4d2193bff0495901e500', '逻辑编排模板管理-新增', '/v1/dev-flow-template/add', null, null, 'post', null, null, null, null, '7aed8c297a6940f681c26eb6ab68893d', '2022-05-30 16:31:46', null, '2022-07-14 18:07:05', 'c47160d89bc347b9a716ff7c5ed804f0', null, 2, '064b3b44b85a45fe87fcce88d72b2519');
INSERT INTO SYS_API (ID, API_NAME, API_URL, API_NOTE, API_TAGS, API_METHOD, API_ARGV_TYPE, API_REQ_ARGV, API_RSP_ARGV, API_RESULT_HANDLER, WHO_CREATED, WHEN_CREATED, WHO_MODIFIED, WHEN_MODIFIED, API_FLOW_ID, API_CODE, CALL_TYPE, APP_ID) VALUES ('56f38d9d976143b2a49b9b717aea6017', 'ext-获取目录节点类型', '/v1/ext_plugin/tree/getNodeType', null, null, 'post', null, null, null, null, '63454c98827e4a0384abf30e0e6eef54', '2022-04-06 10:50:44', null, '2022-07-14 18:07:05', '0b5192d6fa57435aa9e979c276a9fe4b', null, 2, '064b3b44b85a45fe87fcce88d72b2519');
INSERT INTO SYS_API (ID, API_NAME, API_URL, API_NOTE, API_TAGS, API_METHOD, API_ARGV_TYPE, API_REQ_ARGV, API_RSP_ARGV, API_RESULT_HANDLER, WHO_CREATED, WHEN_CREATED, WHO_MODIFIED, WHEN_MODIFIED, API_FLOW_ID, API_CODE, CALL_TYPE, APP_ID) VALUES ('581c169a9b684315b2eac63ed4030d14', '工单编号分类唯一校验', '/v1/work-number/category', null, null, 'get', null, null, null, null, 'd4040ae0800844d99406157b798bae01', '2022-07-27 15:33:16', 'd4040ae0800844d99406157b798bae01', '2022-07-27 15:33:16', '16be8f45b10f45a1afad12e296c22cf1', null, 2, '064b3b44b85a45fe87fcce88d72b2519');
INSERT INTO SYS_API (ID, API_NAME, API_URL, API_NOTE, API_TAGS, API_METHOD, API_ARGV_TYPE, API_REQ_ARGV, API_RSP_ARGV, API_RESULT_HANDLER, WHO_CREATED, WHEN_CREATED, WHO_MODIFIED, WHEN_MODIFIED, API_FLOW_ID, API_CODE, CALL_TYPE, APP_ID) VALUES ('589a97aaafef40cfa290e11113bb8bce', '查询gc信息', '/getGcMessage', null, null, 'post', null, null, null, null, 'd4040ae0800844d99406157b798bae01', '2022-08-08 10:44:56', 'd4040ae0800844d99406157b798bae01', '2022-08-08 10:44:56', '46e4fe2b0edf4737bfd616d4ed35960e', null, 2, '064b3b44b85a45fe87fcce88d72b2519');
INSERT INTO SYS_API (ID, API_NAME, API_URL, API_NOTE, API_TAGS, API_METHOD, API_ARGV_TYPE, API_REQ_ARGV, API_RSP_ARGV, API_RESULT_HANDLER, WHO_CREATED, WHEN_CREATED, WHO_MODIFIED, WHEN_MODIFIED, API_FLOW_ID, API_CODE, CALL_TYPE, APP_ID) VALUES ('58a2c40896504f4796aa528985a9eabb', '流水号查询', '/v1/number/list', null, null, 'post', null, null, null, null, 'a7d903b65e8c42479b9774db664f9468', '2022-04-22 16:59:23', null, '2022-07-14 18:07:06', '2cf836f5fe614b009dcbca0ea9068f52', null, 2, '064b3b44b85a45fe87fcce88d72b2519');
INSERT INTO SYS_API (ID, API_NAME, API_URL, API_NOTE, API_TAGS, API_METHOD, API_ARGV_TYPE, API_REQ_ARGV, API_RSP_ARGV, API_RESULT_HANDLER, WHO_CREATED, WHEN_CREATED, WHO_MODIFIED, WHEN_MODIFIED, API_FLOW_ID, API_CODE, CALL_TYPE, APP_ID) VALUES ('5deb7cfe9ddc46e0ade1d6ae21b00c6e', '删除用户', '/demotest/user/delete', null, null, 'delete', null, null, null, null, '7aed8c297a6940f681c26eb6ab68893d', '2022-05-23 09:47:04', null, '2022-07-14 18:07:06', '36776413c1544231a96eb8550af727d9', null, 2, '064b3b44b85a45fe87fcce88d72b2519');
INSERT INTO SYS_API (ID, API_NAME, API_URL, API_NOTE, API_TAGS, API_METHOD, API_ARGV_TYPE, API_REQ_ARGV, API_RSP_ARGV, API_RESULT_HANDLER, WHO_CREATED, WHEN_CREATED, WHO_MODIFIED, WHEN_MODIFIED, API_FLOW_ID, API_CODE, CALL_TYPE, APP_ID) VALUES ('5f5bee7cc1b343f389c8e77866ecccf8', 'ext-插件树新增节点', '/v1/ext_plugin/tree/insert', null, null, 'post', null, null, null, null, '63454c98827e4a0384abf30e0e6eef54', '2022-03-30 09:50:33', null, '2022-07-14 18:07:06', '1859a8fb3bc545e2be29084d7027c875', null, 2, '064b3b44b85a45fe87fcce88d72b2519');
INSERT INTO SYS_API (ID, API_NAME, API_URL, API_NOTE, API_TAGS, API_METHOD, API_ARGV_TYPE, API_REQ_ARGV, API_RSP_ARGV, API_RESULT_HANDLER, WHO_CREATED, WHEN_CREATED, WHO_MODIFIED, WHEN_MODIFIED, API_FLOW_ID, API_CODE, CALL_TYPE, APP_ID) VALUES ('60536a63b1f040f2a4b8518fe0ec8050', 'pine-应用修改', '/v2/dev-app', null, 'pine', 'put', null, null, null, null, 'a7d903b65e8c42479b9774db664f9468', '2022-08-24 14:32:26', 'a7d903b65e8c42479b9774db664f9468', '2022-08-24 14:47:22', 'f3e09378d243495e9a26fc97dcb800e9', null, 2, '064b3b44b85a45fe87fcce88d72b2519');
INSERT INTO SYS_API (ID, API_NAME, API_URL, API_NOTE, API_TAGS, API_METHOD, API_ARGV_TYPE, API_REQ_ARGV, API_RSP_ARGV, API_RESULT_HANDLER, WHO_CREATED, WHEN_CREATED, WHO_MODIFIED, WHEN_MODIFIED, API_FLOW_ID, API_CODE, CALL_TYPE, APP_ID) VALUES ('65306b1fdc664583a9543cdeafa1ca0e', '新增团队', '/v1/admin-team/add', null, 'admin', 'post', null, null, null, null, '7aed8c297a6940f681c26eb6ab68893d', '2022-06-13 01:06:37', null, '2022-07-14 18:07:06', 'd65cc28c9dd146c9abe2536cee8d8911', null, 2, '064b3b44b85a45fe87fcce88d72b2519');
INSERT INTO SYS_API (ID, API_NAME, API_URL, API_NOTE, API_TAGS, API_METHOD, API_ARGV_TYPE, API_REQ_ARGV, API_RSP_ARGV, API_RESULT_HANDLER, WHO_CREATED, WHEN_CREATED, WHO_MODIFIED, WHEN_MODIFIED, API_FLOW_ID, API_CODE, CALL_TYPE, APP_ID) VALUES ('6734227f85fd448cbfdbfafed8633f97', 'ext-插件新增按钮获取插件树', '/v1/ext_plugin/tree/getAllPlugin', null, null, 'get', null, null, null, null, '63454c98827e4a0384abf30e0e6eef54', '2022-04-01 09:50:10', null, '2022-07-14 18:07:06', '4557d6ae08624ae9a0a38e07d52cadfe', null, 2, '064b3b44b85a45fe87fcce88d72b2519');
INSERT INTO SYS_API (ID, API_NAME, API_URL, API_NOTE, API_TAGS, API_METHOD, API_ARGV_TYPE, API_REQ_ARGV, API_RSP_ARGV, API_RESULT_HANDLER, WHO_CREATED, WHEN_CREATED, WHO_MODIFIED, WHEN_MODIFIED, API_FLOW_ID, API_CODE, CALL_TYPE, APP_ID) VALUES ('6ad5f2fa554040c480ef7a978e336ccc', '青松插件', '/v1/dev-plugins/query', null, '青松插件', 'post', null, null, null, null, '94123ca363dc4dfaa62a6bb5dcd3bf50', '2022-08-19 11:48:42', '94123ca363dc4dfaa62a6bb5dcd3bf50', '2022-08-19 11:48:42', '84b4fad82e9c44f7af84d6df5351ffad', null, 2, '064b3b44b85a45fe87fcce88d72b2519');
INSERT INTO SYS_API (ID, API_NAME, API_URL, API_NOTE, API_TAGS, API_METHOD, API_ARGV_TYPE, API_REQ_ARGV, API_RSP_ARGV, API_RESULT_HANDLER, WHO_CREATED, WHEN_CREATED, WHO_MODIFIED, WHEN_MODIFIED, API_FLOW_ID, API_CODE, CALL_TYPE, APP_ID) VALUES ('6df0bea53f01416590de5cf46746d420', 'pine-能力树-删除', '/v1/dev-power-tree/delete', null, 'PINE', 'post', null, null, null, null, '056fb0eeb9a44cb0953534b4c0ca01fa', '2022-05-12 20:04:10', null, '2022-07-14 18:07:07', '55b2f7c2a07d43eca080d96cec976635', null, 2, '064b3b44b85a45fe87fcce88d72b2519');
INSERT INTO SYS_API (ID, API_NAME, API_URL, API_NOTE, API_TAGS, API_METHOD, API_ARGV_TYPE, API_REQ_ARGV, API_RSP_ARGV, API_RESULT_HANDLER, WHO_CREATED, WHEN_CREATED, WHO_MODIFIED, WHEN_MODIFIED, API_FLOW_ID, API_CODE, CALL_TYPE, APP_ID) VALUES ('739e5861b2814ac98e74c69588896035', '查gc发邮件', '/checkGc', null, null, 'get', null, null, null, null, 'd4040ae0800844d99406157b798bae01', '2022-08-03 16:52:11', 'd4040ae0800844d99406157b798bae01', '2022-08-03 16:52:11', '2ed14cbc232440169b9308ed3b3cd752', null, 2, '064b3b44b85a45fe87fcce88d72b2519');
INSERT INTO SYS_API (ID, API_NAME, API_URL, API_NOTE, API_TAGS, API_METHOD, API_ARGV_TYPE, API_REQ_ARGV, API_RSP_ARGV, API_RESULT_HANDLER, WHO_CREATED, WHEN_CREATED, WHO_MODIFIED, WHEN_MODIFIED, API_FLOW_ID, API_CODE, CALL_TYPE, APP_ID) VALUES ('74dc823be2184ae6a37a4c37e04f70ff', '列表', '/test/query', null, null, 'get', null, null, null, null, '7aed8c297a6940f681c26eb6ab68893d', '2022-06-30 20:53:01', null, '2022-07-14 18:07:07', '459ba21960694c63b6cc5f809dfca1ca', null, 2, '064b3b44b85a45fe87fcce88d72b2519');
INSERT INTO SYS_API (ID, API_NAME, API_URL, API_NOTE, API_TAGS, API_METHOD, API_ARGV_TYPE, API_REQ_ARGV, API_RSP_ARGV, API_RESULT_HANDLER, WHO_CREATED, WHEN_CREATED, WHO_MODIFIED, WHEN_MODIFIED, API_FLOW_ID, API_CODE, CALL_TYPE, APP_ID) VALUES ('772df4269fa947b39a28fb7b07b7820e', 'ext-获取lib目录下的jar包', '/ext/getLibJar', null, null, 'get', null, null, null, null, 'd4040ae0800844d99406157b798bae01', '2022-06-06 11:34:21', null, '2022-07-14 18:07:07', '128f3eed7a76473ab1479ff159172744', null, 2, '064b3b44b85a45fe87fcce88d72b2519');
INSERT INTO SYS_API (ID, API_NAME, API_URL, API_NOTE, API_TAGS, API_METHOD, API_ARGV_TYPE, API_REQ_ARGV, API_RSP_ARGV, API_RESULT_HANDLER, WHO_CREATED, WHEN_CREATED, WHO_MODIFIED, WHEN_MODIFIED, API_FLOW_ID, API_CODE, CALL_TYPE, APP_ID) VALUES ('7747a2ff6c804687891adc3433796fa7', 'pine-应用管理-导出', '/v1/dev-app/export', null, 'pine', 'post', null, null, null, null, '056fb0eeb9a44cb0953534b4c0ca01fa', '2022-04-11 10:16:53', null, '2022-07-14 18:07:07', '61b0ed7055de48fe918d5b28a91aca4e', null, 2, '064b3b44b85a45fe87fcce88d72b2519');
INSERT INTO SYS_API (ID, API_NAME, API_URL, API_NOTE, API_TAGS, API_METHOD, API_ARGV_TYPE, API_REQ_ARGV, API_RSP_ARGV, API_RESULT_HANDLER, WHO_CREATED, WHEN_CREATED, WHO_MODIFIED, WHEN_MODIFIED, API_FLOW_ID, API_CODE, CALL_TYPE, APP_ID) VALUES ('781a341b721d4252a2853ebb438c3087', '菜单列表', '/v1/dev-menu/query', null, 'pine', 'get', null, null, null, null, '7aed8c297a6940f681c26eb6ab68893d', '2022-03-31 15:14:58', null, '2022-07-14 18:07:07', 'de52a15f29624f2caab8afb492c2d3fe', null, 2, '064b3b44b85a45fe87fcce88d72b2519');
INSERT INTO SYS_API (ID, API_NAME, API_URL, API_NOTE, API_TAGS, API_METHOD, API_ARGV_TYPE, API_REQ_ARGV, API_RSP_ARGV, API_RESULT_HANDLER, WHO_CREATED, WHEN_CREATED, WHO_MODIFIED, WHEN_MODIFIED, API_FLOW_ID, API_CODE, CALL_TYPE, APP_ID) VALUES ('7827fbbeef654ce9bcda97d3cf065132', 'ext-插件树节点删除', '/v1/ext_plugin/tree/delete', null, 'ext插件', 'post', null, null, null, null, '63454c98827e4a0384abf30e0e6eef54', '2022-03-30 14:49:44', null, '2022-07-14 18:07:07', '188b86475c9d4aa193f130de1c50eb14', null, 2, '064b3b44b85a45fe87fcce88d72b2519');
INSERT INTO SYS_API (ID, API_NAME, API_URL, API_NOTE, API_TAGS, API_METHOD, API_ARGV_TYPE, API_REQ_ARGV, API_RSP_ARGV, API_RESULT_HANDLER, WHO_CREATED, WHEN_CREATED, WHO_MODIFIED, WHEN_MODIFIED, API_FLOW_ID, API_CODE, CALL_TYPE, APP_ID) VALUES ('7a011929e95f4cfb8488c906b09c6f10', 'pine-页面历史详情', '/v1/dev-page-history/get/{id}', null, null, 'get', null, null, null, null, '7aed8c297a6940f681c26eb6ab68893d', '2022-07-13 16:38:55', '7aed8c297a6940f681c26eb6ab68893d', '2022-07-14 00:05:35', '9dc7cb075f4c4ccdb5127fb82df5f94d', null, 2, '064b3b44b85a45fe87fcce88d72b2519');
INSERT INTO SYS_API (ID, API_NAME, API_URL, API_NOTE, API_TAGS, API_METHOD, API_ARGV_TYPE, API_REQ_ARGV, API_RSP_ARGV, API_RESULT_HANDLER, WHO_CREATED, WHEN_CREATED, WHO_MODIFIED, WHEN_MODIFIED, API_FLOW_ID, API_CODE, CALL_TYPE, APP_ID) VALUES ('7c10d9f5f84e49a7bdd9a4a5fa1103d6', 'pine-菜单详情', '/v1/dev-menu/{id}', null, 'pine', 'get', null, null, null, null, '7aed8c297a6940f681c26eb6ab68893d', '2022-04-01 17:59:35', null, '2022-07-14 18:07:07', 'f311ee261aec4271ae9257426b4b930b', null, 2, '064b3b44b85a45fe87fcce88d72b2519');
INSERT INTO SYS_API (ID, API_NAME, API_URL, API_NOTE, API_TAGS, API_METHOD, API_ARGV_TYPE, API_REQ_ARGV, API_RSP_ARGV, API_RESULT_HANDLER, WHO_CREATED, WHEN_CREATED, WHO_MODIFIED, WHEN_MODIFIED, API_FLOW_ID, API_CODE, CALL_TYPE, APP_ID) VALUES ('7cb9877280694897a3bed306ca382423', 'ext-获取插件一级目录树接口', '/v1/ext_plugin/tree/firstNode/get', null, null, 'get', null, null, null, null, '63454c98827e4a0384abf30e0e6eef54', '2022-04-06 09:45:53', null, '2022-07-14 18:07:07', '02f253353f5f432abab8f0ea12ab4797', null, 2, '064b3b44b85a45fe87fcce88d72b2519');
INSERT INTO SYS_API (ID, API_NAME, API_URL, API_NOTE, API_TAGS, API_METHOD, API_ARGV_TYPE, API_REQ_ARGV, API_RSP_ARGV, API_RESULT_HANDLER, WHO_CREATED, WHEN_CREATED, WHO_MODIFIED, WHEN_MODIFIED, API_FLOW_ID, API_CODE, CALL_TYPE, APP_ID) VALUES ('7d4ef28ea37c484b95b3cf4d970c0d63', '逻辑编排模板管理-列表', '/v1/dev-flow-template/query', null, null, 'get', null, null, null, null, '7aed8c297a6940f681c26eb6ab68893d', '2022-05-30 16:31:14', null, '2022-07-14 18:07:07', '8b5c6ac5d9e745958ef8bde26bdc2f3c', null, 2, '064b3b44b85a45fe87fcce88d72b2519');
INSERT INTO SYS_API (ID, API_NAME, API_URL, API_NOTE, API_TAGS, API_METHOD, API_ARGV_TYPE, API_REQ_ARGV, API_RSP_ARGV, API_RESULT_HANDLER, WHO_CREATED, WHEN_CREATED, WHO_MODIFIED, WHEN_MODIFIED, API_FLOW_ID, API_CODE, CALL_TYPE, APP_ID) VALUES ('802f3a72df12446fae922080d0620178', '新增帮助文档', '/v1/dev_document/add', null, null, 'post', null, null, null, null, '61c7c741fc5548f09258139ebcc1daa6', '2022-04-11 17:33:52', null, '2022-07-14 18:07:07', '765157d562c5465083746755bebca2ea', null, 2, '064b3b44b85a45fe87fcce88d72b2519');
INSERT INTO SYS_API (ID, API_NAME, API_URL, API_NOTE, API_TAGS, API_METHOD, API_ARGV_TYPE, API_REQ_ARGV, API_RSP_ARGV, API_RESULT_HANDLER, WHO_CREATED, WHEN_CREATED, WHO_MODIFIED, WHEN_MODIFIED, API_FLOW_ID, API_CODE, CALL_TYPE, APP_ID) VALUES ('803eab8cd9df4c00972bdd088cea74e8', 'pine-能力关联-保存', '/v1/power-tree/savePowers', null, 'PINE', 'post', null, null, null, null, '056fb0eeb9a44cb0953534b4c0ca01fa', '2022-05-17 15:37:05', null, '2022-07-14 18:07:08', null, null, 2, '064b3b44b85a45fe87fcce88d72b2519');
INSERT INTO SYS_API (ID, API_NAME, API_URL, API_NOTE, API_TAGS, API_METHOD, API_ARGV_TYPE, API_REQ_ARGV, API_RSP_ARGV, API_RESULT_HANDLER, WHO_CREATED, WHEN_CREATED, WHO_MODIFIED, WHEN_MODIFIED, API_FLOW_ID, API_CODE, CALL_TYPE, APP_ID) VALUES ('8056e829618c4e84962d382743c7a6d8', 'ext-获取插件目录名称', '/v1/ext_plugin/tree/getExtName', null, null, 'post', null, null, null, null, '63454c98827e4a0384abf30e0e6eef54', '2022-04-06 16:04:36', null, '2022-07-14 18:07:08', '7e9fb9eebdbe4f5f8051e63dff8683fc', null, 2, '064b3b44b85a45fe87fcce88d72b2519');
INSERT INTO SYS_API (ID, API_NAME, API_URL, API_NOTE, API_TAGS, API_METHOD, API_ARGV_TYPE, API_REQ_ARGV, API_RSP_ARGV, API_RESULT_HANDLER, WHO_CREATED, WHEN_CREATED, WHO_MODIFIED, WHEN_MODIFIED, API_FLOW_ID, API_CODE, CALL_TYPE, APP_ID) VALUES ('814735af4fae44a5a7db29debbb827a7', '通用下拉查询', '/v1/sys/hint-select', null, null, 'get', null, null, null, null, 'd4040ae0800844d99406157b798bae01', '2022-07-28 11:38:26', 'd4040ae0800844d99406157b798bae01', '2022-07-28 11:38:26', '0814c5028f6a48eca39f25a985d44209', null, 2, '064b3b44b85a45fe87fcce88d72b2519');
INSERT INTO SYS_API (ID, API_NAME, API_URL, API_NOTE, API_TAGS, API_METHOD, API_ARGV_TYPE, API_REQ_ARGV, API_RSP_ARGV, API_RESULT_HANDLER, WHO_CREATED, WHEN_CREATED, WHO_MODIFIED, WHEN_MODIFIED, API_FLOW_ID, API_CODE, CALL_TYPE, APP_ID) VALUES ('8291b2caf4a44a0a99cceb782906ed6b', '模块管理-修改', '/v1/dev-module/edit', null, null, 'put', null, null, null, null, '7aed8c297a6940f681c26eb6ab68893d', '2022-05-30 16:26:32', null, '2022-07-14 18:07:08', 'b031190a59554494ae611f2adf788e0c', null, 2, '064b3b44b85a45fe87fcce88d72b2519');
INSERT INTO SYS_API (ID, API_NAME, API_URL, API_NOTE, API_TAGS, API_METHOD, API_ARGV_TYPE, API_REQ_ARGV, API_RSP_ARGV, API_RESULT_HANDLER, WHO_CREATED, WHEN_CREATED, WHO_MODIFIED, WHEN_MODIFIED, API_FLOW_ID, API_CODE, CALL_TYPE, APP_ID) VALUES ('834dc91aa1ec4db4b7db5265b5f7859d', 'pine-能力树-树结构查询', '/v1/power-tree/tree', null, null, 'get', null, null, null, null, '7aed8c297a6940f681c26eb6ab68893d', '2022-06-06 00:01:09', null, '2022-07-14 18:07:08', '92fb7d7bccd04168a27c2880fc78d380', null, 2, '064b3b44b85a45fe87fcce88d72b2519');
INSERT INTO SYS_API (ID, API_NAME, API_URL, API_NOTE, API_TAGS, API_METHOD, API_ARGV_TYPE, API_REQ_ARGV, API_RSP_ARGV, API_RESULT_HANDLER, WHO_CREATED, WHEN_CREATED, WHO_MODIFIED, WHEN_MODIFIED, API_FLOW_ID, API_CODE, CALL_TYPE, APP_ID) VALUES ('84141f56d7ee4b0597072eb138778809', '国际化新增', '/v1/sys-i18ns/add', null, null, 'post', null, null, null, null, 'd4040ae0800844d99406157b798bae01', '2022-06-08 20:36:27', null, '2022-07-14 18:07:08', 'e333970092e44b64a9b00e11af76a766', null, 2, '064b3b44b85a45fe87fcce88d72b2519');
INSERT INTO SYS_API (ID, API_NAME, API_URL, API_NOTE, API_TAGS, API_METHOD, API_ARGV_TYPE, API_REQ_ARGV, API_RSP_ARGV, API_RESULT_HANDLER, WHO_CREATED, WHEN_CREATED, WHO_MODIFIED, WHEN_MODIFIED, API_FLOW_ID, API_CODE, CALL_TYPE, APP_ID) VALUES ('8613dc04c8cb45eb9e3334d08d107122', 'pine-已选择的能力', '/v1/powers/selected', null, null, 'get', null, null, null, null, '056fb0eeb9a44cb0953534b4c0ca01fa', '2022-05-30 21:03:13', null, '2022-07-14 18:07:08', 'd0dabfe6ea0144a4a8f3edce39e09eac', null, 2, '064b3b44b85a45fe87fcce88d72b2519');
INSERT INTO SYS_API (ID, API_NAME, API_URL, API_NOTE, API_TAGS, API_METHOD, API_ARGV_TYPE, API_REQ_ARGV, API_RSP_ARGV, API_RESULT_HANDLER, WHO_CREATED, WHEN_CREATED, WHO_MODIFIED, WHEN_MODIFIED, API_FLOW_ID, API_CODE, CALL_TYPE, APP_ID) VALUES ('86358b236b784fc4b548ce4ffcef227f', 'pine-基础功能查询', '/v1/dev-app/base-menus', null, null, 'get', null, null, null, null, '056fb0eeb9a44cb0953534b4c0ca01fa', '2022-06-07 14:06:13', null, '2022-07-14 18:07:08', 'f9e140b766f0430e948a68a943395e5d', null, 2, '064b3b44b85a45fe87fcce88d72b2519');
INSERT INTO SYS_API (ID, API_NAME, API_URL, API_NOTE, API_TAGS, API_METHOD, API_ARGV_TYPE, API_REQ_ARGV, API_RSP_ARGV, API_RESULT_HANDLER, WHO_CREATED, WHEN_CREATED, WHO_MODIFIED, WHEN_MODIFIED, API_FLOW_ID, API_CODE, CALL_TYPE, APP_ID) VALUES ('8789d15b3e4244629630ae7b8aa83ad8', '获取当前系统配置', '/v1/sys-config/get-sys-config', null, null, 'get', null, null, null, null, '7aed8c297a6940f681c26eb6ab68893d', '2022-08-22 11:26:10', '7aed8c297a6940f681c26eb6ab68893d', '2022-08-22 14:08:50', 'f09c30463acc44e58a8562b79312fbae', ':open', 2, '064b3b44b85a45fe87fcce88d72b2519');
INSERT INTO SYS_API (ID, API_NAME, API_URL, API_NOTE, API_TAGS, API_METHOD, API_ARGV_TYPE, API_REQ_ARGV, API_RSP_ARGV, API_RESULT_HANDLER, WHO_CREATED, WHEN_CREATED, WHO_MODIFIED, WHEN_MODIFIED, API_FLOW_ID, API_CODE, CALL_TYPE, APP_ID) VALUES ('87cbe601e3a84a0488667bf39ca82ddc', 'ext-获取插件接口所有方法', '/v1/ext_plugin/interface/select', null, null, 'post', null, null, null, null, '63454c98827e4a0384abf30e0e6eef54', '2022-03-31 09:33:22', null, '2022-07-14 18:07:08', 'c7a3bf34ef7645a6a5c317392bb95da3', null, 2, '064b3b44b85a45fe87fcce88d72b2519');
INSERT INTO SYS_API (ID, API_NAME, API_URL, API_NOTE, API_TAGS, API_METHOD, API_ARGV_TYPE, API_REQ_ARGV, API_RSP_ARGV, API_RESULT_HANDLER, WHO_CREATED, WHEN_CREATED, WHO_MODIFIED, WHEN_MODIFIED, API_FLOW_ID, API_CODE, CALL_TYPE, APP_ID) VALUES ('8be4059e7e9f4ed49b13e61030ca1694', '通用下拉更新', '/v1/sys/hint-select', null, null, 'put', null, null, null, null, 'd4040ae0800844d99406157b798bae01', '2022-07-28 11:57:07', 'd4040ae0800844d99406157b798bae01', '2022-07-28 11:57:07', '63dfddbaec55499a988cdef5923e36fa', null, 2, '064b3b44b85a45fe87fcce88d72b2519');
INSERT INTO SYS_API (ID, API_NAME, API_URL, API_NOTE, API_TAGS, API_METHOD, API_ARGV_TYPE, API_REQ_ARGV, API_RSP_ARGV, API_RESULT_HANDLER, WHO_CREATED, WHEN_CREATED, WHO_MODIFIED, WHEN_MODIFIED, API_FLOW_ID, API_CODE, CALL_TYPE, APP_ID) VALUES ('8c6a6e1d019d48ebb8ab5398f28fec8a', '团队成员删除', '/v1/dev-team-member/del', null, 'pine', 'delete', null, null, null, null, '7aed8c297a6940f681c26eb6ab68893d', '2022-06-13 02:02:34', null, '2022-07-14 18:07:09', '19232fd83e9547b18ea9d79d42a3dfca', null, 2, '064b3b44b85a45fe87fcce88d72b2519');
INSERT INTO SYS_API (ID, API_NAME, API_URL, API_NOTE, API_TAGS, API_METHOD, API_ARGV_TYPE, API_REQ_ARGV, API_RSP_ARGV, API_RESULT_HANDLER, WHO_CREATED, WHEN_CREATED, WHO_MODIFIED, WHEN_MODIFIED, API_FLOW_ID, API_CODE, CALL_TYPE, APP_ID) VALUES ('8cbb6eb4e4744d50825416ce51eb08ba', '临时，测试函数', '/dev-tmp', null, null, 'get', null, null, null, null, '8c263f81d2b14784906787d67ce58b47', '2022-06-06 10:43:07', null, '2022-07-14 18:07:09', 'ea60c74c271242a2ae338e87551b6c3f', null, 2, '064b3b44b85a45fe87fcce88d72b2519');
INSERT INTO SYS_API (ID, API_NAME, API_URL, API_NOTE, API_TAGS, API_METHOD, API_ARGV_TYPE, API_REQ_ARGV, API_RSP_ARGV, API_RESULT_HANDLER, WHO_CREATED, WHEN_CREATED, WHO_MODIFIED, WHEN_MODIFIED, API_FLOW_ID, API_CODE, CALL_TYPE, APP_ID) VALUES ('8dac6dbfc9294cb3a82f224a90a6bce3', '国际化查询', '/v1/sys-i18ns/query', null, null, 'get', null, null, null, null, 'd4040ae0800844d99406157b798bae01', '2022-06-08 20:35:58', null, '2022-07-14 18:07:09', 'cca47b52c710478693929357417f7c52', null, 2, '064b3b44b85a45fe87fcce88d72b2519');
INSERT INTO SYS_API (ID, API_NAME, API_URL, API_NOTE, API_TAGS, API_METHOD, API_ARGV_TYPE, API_REQ_ARGV, API_RSP_ARGV, API_RESULT_HANDLER, WHO_CREATED, WHEN_CREATED, WHO_MODIFIED, WHEN_MODIFIED, API_FLOW_ID, API_CODE, CALL_TYPE, APP_ID) VALUES ('8f2d51a8eb3e4c32b54a24b49598230b', 'pine-删除菜单', '/v1/dev-menu/delete', null, 'pine', 'delete', null, null, null, null, '7aed8c297a6940f681c26eb6ab68893d', '2022-04-01 04:53:14', null, '2022-07-14 18:07:09', '8939f77d278847a28f1c06f0ca4a49fb', null, 2, '064b3b44b85a45fe87fcce88d72b2519');
INSERT INTO SYS_API (ID, API_NAME, API_URL, API_NOTE, API_TAGS, API_METHOD, API_ARGV_TYPE, API_REQ_ARGV, API_RSP_ARGV, API_RESULT_HANDLER, WHO_CREATED, WHEN_CREATED, WHO_MODIFIED, WHEN_MODIFIED, API_FLOW_ID, API_CODE, CALL_TYPE, APP_ID) VALUES ('8f4d56fac3df4935958972f0ae36de46', 'pine-逻辑编排历史详情', '/v1/sys-logic-history/get/{id}', null, null, 'get', null, null, null, null, '7aed8c297a6940f681c26eb6ab68893d', '2022-07-14 07:41:46', '7aed8c297a6940f681c26eb6ab68893d', '2022-07-14 07:42:27', 'bff2a8d46ba44748900d1660d098b576', null, 2, '064b3b44b85a45fe87fcce88d72b2519');
INSERT INTO SYS_API (ID, API_NAME, API_URL, API_NOTE, API_TAGS, API_METHOD, API_ARGV_TYPE, API_REQ_ARGV, API_RSP_ARGV, API_RESULT_HANDLER, WHO_CREATED, WHEN_CREATED, WHO_MODIFIED, WHEN_MODIFIED, API_FLOW_ID, API_CODE, CALL_TYPE, APP_ID) VALUES ('8f7031bbd7834056b870d4708a6bc08a', '获取函数详情', '/v1/dev-function/info', null, null, 'get', null, null, null, null, '7aed8c297a6940f681c26eb6ab68893d', '2022-06-07 18:56:38', null, '2022-07-14 18:07:09', 'e2397b3a0a0345739f2cc54e269d341b', null, 2, '064b3b44b85a45fe87fcce88d72b2519');
INSERT INTO SYS_API (ID, API_NAME, API_URL, API_NOTE, API_TAGS, API_METHOD, API_ARGV_TYPE, API_REQ_ARGV, API_RSP_ARGV, API_RESULT_HANDLER, WHO_CREATED, WHEN_CREATED, WHO_MODIFIED, WHEN_MODIFIED, API_FLOW_ID, API_CODE, CALL_TYPE, APP_ID) VALUES ('9132889686fd4d23990e3ae944740f83', '青松插件-删除', '/v1/dev-plugins/delete', null, '插件开发', 'post', null, null, null, null, '94123ca363dc4dfaa62a6bb5dcd3bf50', '2022-08-19 11:37:45', '94123ca363dc4dfaa62a6bb5dcd3bf50', '2022-08-19 11:37:45', 'eb44f2a2ecc24a9f874d9deb5c69b648', null, 2, '064b3b44b85a45fe87fcce88d72b2519');
INSERT INTO SYS_API (ID, API_NAME, API_URL, API_NOTE, API_TAGS, API_METHOD, API_ARGV_TYPE, API_REQ_ARGV, API_RSP_ARGV, API_RESULT_HANDLER, WHO_CREATED, WHEN_CREATED, WHO_MODIFIED, WHEN_MODIFIED, API_FLOW_ID, API_CODE, CALL_TYPE, APP_ID) VALUES ('916175dc25544e96a1d118b975e7fd93', '应用列表', '/v2/dev-app/query', null, 'pine', 'get', null, null, null, null, '7aed8c297a6940f681c26eb6ab68893d', '2022-06-11 18:00:21', null, '2022-07-14 18:07:09', '5ee5a31c903c44e48bce3aa7a7a309dd', null, 2, '064b3b44b85a45fe87fcce88d72b2519');
INSERT INTO SYS_API (ID, API_NAME, API_URL, API_NOTE, API_TAGS, API_METHOD, API_ARGV_TYPE, API_REQ_ARGV, API_RSP_ARGV, API_RESULT_HANDLER, WHO_CREATED, WHEN_CREATED, WHO_MODIFIED, WHEN_MODIFIED, API_FLOW_ID, API_CODE, CALL_TYPE, APP_ID) VALUES ('91792293c4a04f568f5b1b9001ca8a6f', '获取国际化配置文件', '/v1/sys-i18ns/list', null, null, 'get', null, null, null, null, '056fb0eeb9a44cb0953534b4c0ca01fa', '2022-06-10 11:29:34', null, '2022-07-14 18:07:09', '73def37a705144548251581f6c2811fb', ':open', 2, '064b3b44b85a45fe87fcce88d72b2519');
INSERT INTO SYS_API (ID, API_NAME, API_URL, API_NOTE, API_TAGS, API_METHOD, API_ARGV_TYPE, API_REQ_ARGV, API_RSP_ARGV, API_RESULT_HANDLER, WHO_CREATED, WHEN_CREATED, WHO_MODIFIED, WHEN_MODIFIED, API_FLOW_ID, API_CODE, CALL_TYPE, APP_ID) VALUES ('9202e0e22e984eee9011ee85af0ff6b1', 'pine-菜单排序', '/v1/dev-menu/sort', null, null, 'post', null, null, null, null, '7aed8c297a6940f681c26eb6ab68893d', '2022-04-01 04:04:20', null, '2022-07-14 18:07:10', 'e4a3ae8f65fe4541ba1f2496d626c8fb', null, 2, '064b3b44b85a45fe87fcce88d72b2519');
INSERT INTO SYS_API (ID, API_NAME, API_URL, API_NOTE, API_TAGS, API_METHOD, API_ARGV_TYPE, API_REQ_ARGV, API_RSP_ARGV, API_RESULT_HANDLER, WHO_CREATED, WHEN_CREATED, WHO_MODIFIED, WHEN_MODIFIED, API_FLOW_ID, API_CODE, CALL_TYPE, APP_ID) VALUES ('93575ad2ee004a7eaa7ff870b8193c10', 'pine-接口标签', '/v1/sys-apis/tags', null, 'pine', 'get', null, null, null, null, '056fb0eeb9a44cb0953534b4c0ca01fa', '2022-03-22 00:03:13', null, '2022-07-14 18:07:10', '1a920a37fe1648b8b284d5d75c6872f1', null, 2, '064b3b44b85a45fe87fcce88d72b2519');
INSERT INTO SYS_API (ID, API_NAME, API_URL, API_NOTE, API_TAGS, API_METHOD, API_ARGV_TYPE, API_REQ_ARGV, API_RSP_ARGV, API_RESULT_HANDLER, WHO_CREATED, WHEN_CREATED, WHO_MODIFIED, WHEN_MODIFIED, API_FLOW_ID, API_CODE, CALL_TYPE, APP_ID) VALUES ('93cc643fd3ce48a1a92b716f5109bf30', '查询帮助文档的目录', '/v1/dev_document/query/directory', null, null, 'get', null, null, null, null, '61c7c741fc5548f09258139ebcc1daa6', '2022-04-11 11:41:13', null, '2022-07-14 18:07:10', '3662697a76464e908ded017f30f538d3', null, 2, '064b3b44b85a45fe87fcce88d72b2519');
INSERT INTO SYS_API (ID, API_NAME, API_URL, API_NOTE, API_TAGS, API_METHOD, API_ARGV_TYPE, API_REQ_ARGV, API_RSP_ARGV, API_RESULT_HANDLER, WHO_CREATED, WHEN_CREATED, WHO_MODIFIED, WHEN_MODIFIED, API_FLOW_ID, API_CODE, CALL_TYPE, APP_ID) VALUES ('9547dac3aa72457aba9089bceae44ca7', '逻辑编排模板管理-修改', '/v1/dev-flow-template/edit', null, null, 'put', null, null, null, null, '7aed8c297a6940f681c26eb6ab68893d', '2022-05-30 16:32:35', null, '2022-07-14 18:07:10', '0966c5989cd2455b8c16b85a11071b91', null, 2, '064b3b44b85a45fe87fcce88d72b2519');
INSERT INTO SYS_API (ID, API_NAME, API_URL, API_NOTE, API_TAGS, API_METHOD, API_ARGV_TYPE, API_REQ_ARGV, API_RSP_ARGV, API_RESULT_HANDLER, WHO_CREATED, WHEN_CREATED, WHO_MODIFIED, WHEN_MODIFIED, API_FLOW_ID, API_CODE, CALL_TYPE, APP_ID) VALUES ('966f6b9dc3f648bfba64e9ce00baa9b4', '通用下拉新增', '/v1/sys/hint-select', null, null, 'post', null, null, null, null, 'd4040ae0800844d99406157b798bae01', '2022-07-28 11:49:00', 'd4040ae0800844d99406157b798bae01', '2022-07-28 11:49:00', '81ee4e24f8f344b196d458bbbb40f4b4', null, 2, '064b3b44b85a45fe87fcce88d72b2519');
INSERT INTO SYS_API (ID, API_NAME, API_URL, API_NOTE, API_TAGS, API_METHOD, API_ARGV_TYPE, API_REQ_ARGV, API_RSP_ARGV, API_RESULT_HANDLER, WHO_CREATED, WHEN_CREATED, WHO_MODIFIED, WHEN_MODIFIED, API_FLOW_ID, API_CODE, CALL_TYPE, APP_ID) VALUES ('987c8cdaa6cf4faaa664810f173eba75', '模块管理-删除', '/v1/dev-module/del', null, null, 'delete', null, null, null, null, '7aed8c297a6940f681c26eb6ab68893d', '2022-05-30 16:26:59', null, '2022-07-14 18:07:10', 'e457993894914fc2aba2278850193202', null, 2, '064b3b44b85a45fe87fcce88d72b2519');
INSERT INTO SYS_API (ID, API_NAME, API_URL, API_NOTE, API_TAGS, API_METHOD, API_ARGV_TYPE, API_REQ_ARGV, API_RSP_ARGV, API_RESULT_HANDLER, WHO_CREATED, WHEN_CREATED, WHO_MODIFIED, WHEN_MODIFIED, API_FLOW_ID, API_CODE, CALL_TYPE, APP_ID) VALUES ('989e664c36304b79af4d579364397b07', '数据访问配置', '/v1/sys-tasks', null, 'admin', 'put', null, null, null, null, '7aed8c297a6940f681c26eb6ab68893d', '2022-08-22 14:46:23', '7aed8c297a6940f681c26eb6ab68893d', '2022-08-22 15:21:42', '22d9eda47fb545c8bc0c57a317412d5f', null, 2, '064b3b44b85a45fe87fcce88d72b2519');
INSERT INTO SYS_API (ID, API_NAME, API_URL, API_NOTE, API_TAGS, API_METHOD, API_ARGV_TYPE, API_REQ_ARGV, API_RSP_ARGV, API_RESULT_HANDLER, WHO_CREATED, WHEN_CREATED, WHO_MODIFIED, WHEN_MODIFIED, API_FLOW_ID, API_CODE, CALL_TYPE, APP_ID) VALUES ('9998224d1671466bb088315089a9dce4', '工单编号更新', '/v1/wokr-number', null, null, 'put', null, null, null, null, 'd4040ae0800844d99406157b798bae01', '2022-07-27 15:47:37', 'd4040ae0800844d99406157b798bae01', '2022-07-27 15:47:37', '93dd72f3a4114a71b597d6cff97b11cb', null, 2, '064b3b44b85a45fe87fcce88d72b2519');
INSERT INTO SYS_API (ID, API_NAME, API_URL, API_NOTE, API_TAGS, API_METHOD, API_ARGV_TYPE, API_REQ_ARGV, API_RSP_ARGV, API_RESULT_HANDLER, WHO_CREATED, WHEN_CREATED, WHO_MODIFIED, WHEN_MODIFIED, API_FLOW_ID, API_CODE, CALL_TYPE, APP_ID) VALUES ('9aa5046d462840bd8a44151385f79be3', 'pine应用历史版本查询', '/v1/dev-app/history', null, 'pine', 'get', null, null, null, null, '056fb0eeb9a44cb0953534b4c0ca01fa', '2022-05-05 16:55:20', null, '2022-07-14 18:07:10', '8fcb6cb47c2f4ec3a4a5b4a748be768c', null, 2, '064b3b44b85a45fe87fcce88d72b2519');
INSERT INTO SYS_API (ID, API_NAME, API_URL, API_NOTE, API_TAGS, API_METHOD, API_ARGV_TYPE, API_REQ_ARGV, API_RSP_ARGV, API_RESULT_HANDLER, WHO_CREATED, WHEN_CREATED, WHO_MODIFIED, WHEN_MODIFIED, API_FLOW_ID, API_CODE, CALL_TYPE, APP_ID) VALUES ('a0c55a4b8665406e8964acd961813a46', '逻辑编排树', '/v1/power-tree/flowTemplateTree', null, null, 'get', null, null, null, null, '7aed8c297a6940f681c26eb6ab68893d', '2022-06-06 02:32:12', null, '2022-07-14 18:07:10', 'f590e18c588040fcbf2fa32e7b411d87', null, 2, '064b3b44b85a45fe87fcce88d72b2519');
INSERT INTO SYS_API (ID, API_NAME, API_URL, API_NOTE, API_TAGS, API_METHOD, API_ARGV_TYPE, API_REQ_ARGV, API_RSP_ARGV, API_RESULT_HANDLER, WHO_CREATED, WHEN_CREATED, WHO_MODIFIED, WHEN_MODIFIED, API_FLOW_ID, API_CODE, CALL_TYPE, APP_ID) VALUES ('a20fef455e65435daa6fc98b7a983b70', 'ext-插件接口新增', '/v1/ext_plugin/interface/insert', null, null, 'post', null, null, null, null, '63454c98827e4a0384abf30e0e6eef54', '2022-04-01 09:57:39', null, '2022-07-14 18:07:11', 'a8bb91b330854492bc201861e327b580', null, 2, '064b3b44b85a45fe87fcce88d72b2519');
INSERT INTO SYS_API (ID, API_NAME, API_URL, API_NOTE, API_TAGS, API_METHOD, API_ARGV_TYPE, API_REQ_ARGV, API_RSP_ARGV, API_RESULT_HANDLER, WHO_CREATED, WHEN_CREATED, WHO_MODIFIED, WHEN_MODIFIED, API_FLOW_ID, API_CODE, CALL_TYPE, APP_ID) VALUES ('a5ae1d6d42a7486fb82382e6780e0822', '国际化测试', '/v1/sys-i18ns/test', null, 'PINE', 'get', null, null, null, null, 'd4040ae0800844d99406157b798bae01', '2022-06-09 17:04:19', null, '2022-07-14 18:07:11', 'a25dd10f17ec41e392efe06a7258f367', ':open', 2, '064b3b44b85a45fe87fcce88d72b2519');
INSERT INTO SYS_API (ID, API_NAME, API_URL, API_NOTE, API_TAGS, API_METHOD, API_ARGV_TYPE, API_REQ_ARGV, API_RSP_ARGV, API_RESULT_HANDLER, WHO_CREATED, WHEN_CREATED, WHO_MODIFIED, WHEN_MODIFIED, API_FLOW_ID, API_CODE, CALL_TYPE, APP_ID) VALUES ('a5ef9e7000f94ac7819d06b06e5d09ad', 'pine-应用管理-导出数据列表', '/v1/dev-app/export-data', null, null, 'get', null, null, null, null, '7aed8c297a6940f681c26eb6ab68893d', '2022-04-11 10:40:57', null, '2022-07-14 18:07:11', 'e6000cb2335543b78676cbd61fab88c1', null, 2, '064b3b44b85a45fe87fcce88d72b2519');
INSERT INTO SYS_API (ID, API_NAME, API_URL, API_NOTE, API_TAGS, API_METHOD, API_ARGV_TYPE, API_REQ_ARGV, API_RSP_ARGV, API_RESULT_HANDLER, WHO_CREATED, WHEN_CREATED, WHO_MODIFIED, WHEN_MODIFIED, API_FLOW_ID, API_CODE, CALL_TYPE, APP_ID) VALUES ('a929436f9030499380b6c44f4607d01b', '模块管理-列表', '/v1/dev-module/query', null, null, 'get', null, null, null, null, '7aed8c297a6940f681c26eb6ab68893d', '2022-05-30 16:25:37', null, '2022-07-14 18:07:11', '040833b8989948b3b5f38635d509b2b6', null, 2, '064b3b44b85a45fe87fcce88d72b2519');
INSERT INTO SYS_API (ID, API_NAME, API_URL, API_NOTE, API_TAGS, API_METHOD, API_ARGV_TYPE, API_REQ_ARGV, API_RSP_ARGV, API_RESULT_HANDLER, WHO_CREATED, WHEN_CREATED, WHO_MODIFIED, WHEN_MODIFIED, API_FLOW_ID, API_CODE, CALL_TYPE, APP_ID) VALUES ('aa318f87b38743788b375f670c6e4372', '国际化编辑', '/v1/sys-i18ns/edit', null, null, 'post', null, null, null, null, 'd4040ae0800844d99406157b798bae01', '2022-06-08 20:37:01', null, '2022-07-14 18:07:11', '721c826c4d0a45628776edc508373643', null, 2, '064b3b44b85a45fe87fcce88d72b2519');
INSERT INTO SYS_API (ID, API_NAME, API_URL, API_NOTE, API_TAGS, API_METHOD, API_ARGV_TYPE, API_REQ_ARGV, API_RSP_ARGV, API_RESULT_HANDLER, WHO_CREATED, WHEN_CREATED, WHO_MODIFIED, WHEN_MODIFIED, API_FLOW_ID, API_CODE, CALL_TYPE, APP_ID) VALUES ('aa72cafacb26486fb6abc5c2db81def0', 'pine-字典项管理-列表', '/v1/sys-dict-item/query', null, null, 'get', null, null, null, null, '7aed8c297a6940f681c26eb6ab68893d', '2022-04-06 11:50:39', '7aed8c297a6940f681c26eb6ab68893d', '2022-08-18 10:12:12', '6224d0e55f204235a19d7e67b3ca2921', null, 2, '064b3b44b85a45fe87fcce88d72b2519');
INSERT INTO SYS_API (ID, API_NAME, API_URL, API_NOTE, API_TAGS, API_METHOD, API_ARGV_TYPE, API_REQ_ARGV, API_RSP_ARGV, API_RESULT_HANDLER, WHO_CREATED, WHEN_CREATED, WHO_MODIFIED, WHEN_MODIFIED, API_FLOW_ID, API_CODE, CALL_TYPE, APP_ID) VALUES ('ae025a4a9f2d40a4b287cf1607c94bbc', 'ext-插件移动', '/ext/plugin/move', null, null, 'get', null, null, null, null, 'd4040ae0800844d99406157b798bae01', '2022-06-01 11:22:31', null, '2022-07-14 18:07:11', '901a59c73e2d4cc2b624a28231d99442', null, 2, '064b3b44b85a45fe87fcce88d72b2519');
INSERT INTO SYS_API (ID, API_NAME, API_URL, API_NOTE, API_TAGS, API_METHOD, API_ARGV_TYPE, API_REQ_ARGV, API_RSP_ARGV, API_RESULT_HANDLER, WHO_CREATED, WHEN_CREATED, WHO_MODIFIED, WHEN_MODIFIED, API_FLOW_ID, API_CODE, CALL_TYPE, APP_ID) VALUES ('b01fff2359a14513b2a151f84ea24d21', 'ext-插件管理同步插件方法', '/ext/plugin/syncMethod', null, null, 'get', null, null, null, null, 'd4040ae0800844d99406157b798bae01', '2022-05-27 16:19:29', null, '2022-07-14 18:07:11', '2b657a7ef4334cdabfa59977ac4afcf2', null, 2, '064b3b44b85a45fe87fcce88d72b2519');
INSERT INTO SYS_API (ID, API_NAME, API_URL, API_NOTE, API_TAGS, API_METHOD, API_ARGV_TYPE, API_REQ_ARGV, API_RSP_ARGV, API_RESULT_HANDLER, WHO_CREATED, WHEN_CREATED, WHO_MODIFIED, WHEN_MODIFIED, API_FLOW_ID, API_CODE, CALL_TYPE, APP_ID) VALUES ('b04a1da23378416c966c54b6264871df', 'pine-页面更新版本', '/v1/dev-page-history/version', null, null, 'post', null, null, null, null, '7aed8c297a6940f681c26eb6ab68893d', '2022-07-14 00:07:06', '7aed8c297a6940f681c26eb6ab68893d', '2022-07-14 00:07:06', '49bb8ad776b643b78460dfaacad3c19d', null, 2, '064b3b44b85a45fe87fcce88d72b2519');
INSERT INTO SYS_API (ID, API_NAME, API_URL, API_NOTE, API_TAGS, API_METHOD, API_ARGV_TYPE, API_REQ_ARGV, API_RSP_ARGV, API_RESULT_HANDLER, WHO_CREATED, WHEN_CREATED, WHO_MODIFIED, WHEN_MODIFIED, API_FLOW_ID, API_CODE, CALL_TYPE, APP_ID) VALUES ('b12e828013014d31ba4f8b1f0b5fa419', '团队成员添加', '/v1/dev-team-member/add', null, 'pine', 'post', null, null, null, null, '7aed8c297a6940f681c26eb6ab68893d', '2022-06-13 02:01:29', null, '2022-07-14 18:07:11', 'c874dd04a8bb411eb849eabeaabc4408', null, 2, '064b3b44b85a45fe87fcce88d72b2519');
INSERT INTO SYS_API (ID, API_NAME, API_URL, API_NOTE, API_TAGS, API_METHOD, API_ARGV_TYPE, API_REQ_ARGV, API_RSP_ARGV, API_RESULT_HANDLER, WHO_CREATED, WHEN_CREATED, WHO_MODIFIED, WHEN_MODIFIED, API_FLOW_ID, API_CODE, CALL_TYPE, APP_ID) VALUES ('b1a6416fd25a469bbc9ac3a15953bdec', 'pine-图片查看-加水印', '/v1/images/watermark/{id}', null, null, 'get', null, null, null, null, '056fb0eeb9a44cb0953534b4c0ca01fa', '2022-05-13 21:03:33', null, '2022-07-14 18:07:12', '97d1c0ad0f67458ab39d174bea76e553', null, 2, '064b3b44b85a45fe87fcce88d72b2519');
INSERT INTO SYS_API (ID, API_NAME, API_URL, API_NOTE, API_TAGS, API_METHOD, API_ARGV_TYPE, API_REQ_ARGV, API_RSP_ARGV, API_RESULT_HANDLER, WHO_CREATED, WHEN_CREATED, WHO_MODIFIED, WHEN_MODIFIED, API_FLOW_ID, API_CODE, CALL_TYPE, APP_ID) VALUES ('b2df22b8dc654cc092505e11f29f8bb5', '生成工单编号', '/v1/work-number/produce', null, null, 'get', null, null, null, null, 'd4040ae0800844d99406157b798bae01', '2022-07-27 15:58:25', 'd4040ae0800844d99406157b798bae01', '2022-07-27 15:58:25', '53138e56530e4433b3c3915491aa5a1e', null, 2, '064b3b44b85a45fe87fcce88d72b2519');
INSERT INTO SYS_API (ID, API_NAME, API_URL, API_NOTE, API_TAGS, API_METHOD, API_ARGV_TYPE, API_REQ_ARGV, API_RSP_ARGV, API_RESULT_HANDLER, WHO_CREATED, WHEN_CREATED, WHO_MODIFIED, WHEN_MODIFIED, API_FLOW_ID, API_CODE, CALL_TYPE, APP_ID) VALUES ('b6a76b3271934d5d872840868fb91b02', '一键导出v2.0', '/v2/dev-app/export', null, null, 'post', null, null, null, null, '7aed8c297a6940f681c26eb6ab68893d', '2022-07-08 06:52:37', null, '2022-07-14 18:07:12', '534eeda63fec422ebb5bc0f38014c277', null, 2, '064b3b44b85a45fe87fcce88d72b2519');
INSERT INTO SYS_API (ID, API_NAME, API_URL, API_NOTE, API_TAGS, API_METHOD, API_ARGV_TYPE, API_REQ_ARGV, API_RSP_ARGV, API_RESULT_HANDLER, WHO_CREATED, WHEN_CREATED, WHO_MODIFIED, WHEN_MODIFIED, API_FLOW_ID, API_CODE, CALL_TYPE, APP_ID) VALUES ('b6eccf38ccb844a7a17ec2959fc98ebc', 'pine-能力树-查询', '/v1/dev-power-tree/query', null, 'PINE', 'get', null, null, null, null, '056fb0eeb9a44cb0953534b4c0ca01fa', '2022-05-12 14:55:45', null, '2022-07-14 18:07:12', '3571b703da104da4af31ee770cc93386', null, 2, '064b3b44b85a45fe87fcce88d72b2519');
INSERT INTO SYS_API (ID, API_NAME, API_URL, API_NOTE, API_TAGS, API_METHOD, API_ARGV_TYPE, API_REQ_ARGV, API_RSP_ARGV, API_RESULT_HANDLER, WHO_CREATED, WHEN_CREATED, WHO_MODIFIED, WHEN_MODIFIED, API_FLOW_ID, API_CODE, CALL_TYPE, APP_ID) VALUES ('b779a759fc224c98963ed8916c8fdaca', 'ext-插件接口更新', '/v1/ext_plugin/interface/update', null, null, 'post', null, null, null, null, '63454c98827e4a0384abf30e0e6eef54', '2022-04-01 09:11:53', null, '2022-07-14 18:07:12', 'cb34d03af7884c2681119588f96648e6', null, 2, '064b3b44b85a45fe87fcce88d72b2519');
INSERT INTO SYS_API (ID, API_NAME, API_URL, API_NOTE, API_TAGS, API_METHOD, API_ARGV_TYPE, API_REQ_ARGV, API_RSP_ARGV, API_RESULT_HANDLER, WHO_CREATED, WHEN_CREATED, WHO_MODIFIED, WHEN_MODIFIED, API_FLOW_ID, API_CODE, CALL_TYPE, APP_ID) VALUES ('b8e4037e697d4bf09ea81723714ba8ff', '获取逻辑编排模板详情', '/v1/dev-logic-template/info', null, null, 'get', null, null, null, null, '7aed8c297a6940f681c26eb6ab68893d', '2022-06-07 18:57:35', null, '2022-07-14 18:07:12', '7387b38b5fe44130a06ba3c2d099cb44', null, 2, '064b3b44b85a45fe87fcce88d72b2519');
INSERT INTO SYS_API (ID, API_NAME, API_URL, API_NOTE, API_TAGS, API_METHOD, API_ARGV_TYPE, API_REQ_ARGV, API_RSP_ARGV, API_RESULT_HANDLER, WHO_CREATED, WHEN_CREATED, WHO_MODIFIED, WHEN_MODIFIED, API_FLOW_ID, API_CODE, CALL_TYPE, APP_ID) VALUES ('b8f4798cd64047e1b4ee391036bdbc19', '保存能力关联', '/v1/power-tree/links', null, null, 'post', null, null, null, null, '056fb0eeb9a44cb0953534b4c0ca01fa', '2022-05-28 17:42:36', null, '2022-07-14 18:07:12', 'a69e0c153bad4154905bd37ec11ae24a', null, 2, '064b3b44b85a45fe87fcce88d72b2519');
INSERT INTO SYS_API (ID, API_NAME, API_URL, API_NOTE, API_TAGS, API_METHOD, API_ARGV_TYPE, API_REQ_ARGV, API_RSP_ARGV, API_RESULT_HANDLER, WHO_CREATED, WHEN_CREATED, WHO_MODIFIED, WHEN_MODIFIED, API_FLOW_ID, API_CODE, CALL_TYPE, APP_ID) VALUES ('ba3065f44e714b3cb14b47c9ef49652f', '流程中心-流程定义查询', '/v1/wf-process/query', null, null, 'get', null, null, null, null, '056fb0eeb9a44cb0953534b4c0ca01fa', '2022-06-10 14:39:47', null, '2022-07-14 18:07:12', 'a1ffc220aace45158c6d07c6719d21b4', null, 2, '064b3b44b85a45fe87fcce88d72b2519');
INSERT INTO SYS_API (ID, API_NAME, API_URL, API_NOTE, API_TAGS, API_METHOD, API_ARGV_TYPE, API_REQ_ARGV, API_RSP_ARGV, API_RESULT_HANDLER, WHO_CREATED, WHEN_CREATED, WHO_MODIFIED, WHEN_MODIFIED, API_FLOW_ID, API_CODE, CALL_TYPE, APP_ID) VALUES ('c3e7c4a8d4f340ae83037ae1038a9dd4', 'ext-插件树更新节点', '/v1/ext_plugin/tree/update', null, null, 'post', null, null, null, null, '63454c98827e4a0384abf30e0e6eef54', '2022-03-30 14:19:15', null, '2022-07-14 18:07:12', 'f71ccfb7e046473e84870c3b211ab7e3', null, 2, '064b3b44b85a45fe87fcce88d72b2519');
INSERT INTO SYS_API (ID, API_NAME, API_URL, API_NOTE, API_TAGS, API_METHOD, API_ARGV_TYPE, API_REQ_ARGV, API_RSP_ARGV, API_RESULT_HANDLER, WHO_CREATED, WHEN_CREATED, WHO_MODIFIED, WHEN_MODIFIED, API_FLOW_ID, API_CODE, CALL_TYPE, APP_ID) VALUES ('c54e20a8f36c4f73b47f67af83b43b8a', 'pine-在线Excel详情', '/v1/sys-excel/get', null, null, 'get', null, null, null, null, '7aed8c297a6940f681c26eb6ab68893d', '2022-07-22 09:14:16', '7aed8c297a6940f681c26eb6ab68893d', '2022-07-22 09:14:16', 'bf4d48df6c0e4b79b37346c8bf065629', null, 2, '064b3b44b85a45fe87fcce88d72b2519');
INSERT INTO SYS_API (ID, API_NAME, API_URL, API_NOTE, API_TAGS, API_METHOD, API_ARGV_TYPE, API_REQ_ARGV, API_RSP_ARGV, API_RESULT_HANDLER, WHO_CREATED, WHEN_CREATED, WHO_MODIFIED, WHEN_MODIFIED, API_FLOW_ID, API_CODE, CALL_TYPE, APP_ID) VALUES ('c6ea5101afde4e989299f4b71a53a8ff', '新增', '/v1/dev-plugins/edit', null, '青松插件', 'post', null, null, null, null, '94123ca363dc4dfaa62a6bb5dcd3bf50', '2022-08-12 16:05:15', '94123ca363dc4dfaa62a6bb5dcd3bf50', '2022-08-19 17:16:31', 'cde681354d0549d99fbb8fe04297e1b6', null, 2, '064b3b44b85a45fe87fcce88d72b2519');
INSERT INTO SYS_API (ID, API_NAME, API_URL, API_NOTE, API_TAGS, API_METHOD, API_ARGV_TYPE, API_REQ_ARGV, API_RSP_ARGV, API_RESULT_HANDLER, WHO_CREATED, WHEN_CREATED, WHO_MODIFIED, WHEN_MODIFIED, API_FLOW_ID, API_CODE, CALL_TYPE, APP_ID) VALUES ('c799a1a9c7bf4df0977e4ac9c712a2b2', 'pine-逻辑编排更新版本', '/v1/sys-logic-history/version', null, null, 'post', null, null, null, null, '7aed8c297a6940f681c26eb6ab68893d', '2022-07-14 07:43:07', '7aed8c297a6940f681c26eb6ab68893d', '2022-07-14 07:43:07', '49685500dc6a47b8b6704d0ab4c47ab9', null, 2, '064b3b44b85a45fe87fcce88d72b2519');
INSERT INTO SYS_API (ID, API_NAME, API_URL, API_NOTE, API_TAGS, API_METHOD, API_ARGV_TYPE, API_REQ_ARGV, API_RSP_ARGV, API_RESULT_HANDLER, WHO_CREATED, WHEN_CREATED, WHO_MODIFIED, WHEN_MODIFIED, API_FLOW_ID, API_CODE, CALL_TYPE, APP_ID) VALUES ('c88e4a8ed68a4000a23f4c06862e414e', '流水号编辑', '/v1/number/update', null, null, 'post', null, null, null, null, 'a7d903b65e8c42479b9774db664f9468', '2022-04-22 17:00:30', null, '2022-07-14 18:07:12', 'e33999c62f40468487f192b9c63b7b94', null, 2, '064b3b44b85a45fe87fcce88d72b2519');
INSERT INTO SYS_API (ID, API_NAME, API_URL, API_NOTE, API_TAGS, API_METHOD, API_ARGV_TYPE, API_REQ_ARGV, API_RSP_ARGV, API_RESULT_HANDLER, WHO_CREATED, WHEN_CREATED, WHO_MODIFIED, WHEN_MODIFIED, API_FLOW_ID, API_CODE, CALL_TYPE, APP_ID) VALUES ('c97c5d47e2b349e1a70a2261cd16a0a9', '流水号删除', '/v1/number/delete', null, null, 'post', null, null, null, null, 'a7d903b65e8c42479b9774db664f9468', '2022-04-22 17:01:05', null, '2022-07-14 18:07:13', '4540f884ed0b43ada901404b881259f3', null, 2, '064b3b44b85a45fe87fcce88d72b2519');
INSERT INTO SYS_API (ID, API_NAME, API_URL, API_NOTE, API_TAGS, API_METHOD, API_ARGV_TYPE, API_REQ_ARGV, API_RSP_ARGV, API_RESULT_HANDLER, WHO_CREATED, WHEN_CREATED, WHO_MODIFIED, WHEN_MODIFIED, API_FLOW_ID, API_CODE, CALL_TYPE, APP_ID) VALUES ('ca697f7616ee42d99fdd4809ee513894', '应用选项', '/v2/dev-app/select', null, null, 'get', null, null, null, null, '7aed8c297a6940f681c26eb6ab68893d', '2022-06-12 05:18:06', null, '2022-07-14 18:07:13', '1b25aae027ec4574b7d3fde22a8548bd', null, 2, '064b3b44b85a45fe87fcce88d72b2519');
INSERT INTO SYS_API (ID, API_NAME, API_URL, API_NOTE, API_TAGS, API_METHOD, API_ARGV_TYPE, API_REQ_ARGV, API_RSP_ARGV, API_RESULT_HANDLER, WHO_CREATED, WHEN_CREATED, WHO_MODIFIED, WHEN_MODIFIED, API_FLOW_ID, API_CODE, CALL_TYPE, APP_ID) VALUES ('cb0d371ffc00476093a517acc44e286f', 'pine-在线Excel添加', '/v1/sys-excel/add', null, 'excel', 'post', null, null, null, null, '7aed8c297a6940f681c26eb6ab68893d', '2022-07-22 09:18:51', '7aed8c297a6940f681c26eb6ab68893d', '2022-07-22 09:18:51', 'e408cfb3807f4c7498d041b0db8bcdb8', null, 2, '064b3b44b85a45fe87fcce88d72b2519');
INSERT INTO SYS_API (ID, API_NAME, API_URL, API_NOTE, API_TAGS, API_METHOD, API_ARGV_TYPE, API_REQ_ARGV, API_RSP_ARGV, API_RESULT_HANDLER, WHO_CREATED, WHEN_CREATED, WHO_MODIFIED, WHEN_MODIFIED, API_FLOW_ID, API_CODE, CALL_TYPE, APP_ID) VALUES ('d2009bd69076438b8f59582707d92b3f', 'pine-新增菜单', '/v1/dev-menu', null, 'pine', 'post', null, null, null, null, '7aed8c297a6940f681c26eb6ab68893d', '2022-04-01 03:09:36', null, '2022-07-14 18:07:13', 'ed21f4817fe545098ce9709c216a13bd', null, 2, '064b3b44b85a45fe87fcce88d72b2519');
INSERT INTO SYS_API (ID, API_NAME, API_URL, API_NOTE, API_TAGS, API_METHOD, API_ARGV_TYPE, API_REQ_ARGV, API_RSP_ARGV, API_RESULT_HANDLER, WHO_CREATED, WHEN_CREATED, WHO_MODIFIED, WHEN_MODIFIED, API_FLOW_ID, API_CODE, CALL_TYPE, APP_ID) VALUES ('d86f75d88964432793d3f239c242e38e', 'pine-团队管理', '/v1/dev-team/query', null, 'pine', 'get', null, null, null, null, '7aed8c297a6940f681c26eb6ab68893d', '2022-03-28 06:43:43', null, '2022-07-14 18:07:13', '5fe12c4f04584ec69137e6f011207f1d', null, 2, '064b3b44b85a45fe87fcce88d72b2519');
INSERT INTO SYS_API (ID, API_NAME, API_URL, API_NOTE, API_TAGS, API_METHOD, API_ARGV_TYPE, API_REQ_ARGV, API_RSP_ARGV, API_RESULT_HANDLER, WHO_CREATED, WHEN_CREATED, WHO_MODIFIED, WHEN_MODIFIED, API_FLOW_ID, API_CODE, CALL_TYPE, APP_ID) VALUES ('d9622cc890d744149938e55c8488a851', 'pine-青松图标库', '/v1/dev/icons', null, 'pine', 'get', null, null, null, null, '7aed8c297a6940f681c26eb6ab68893d', '2022-08-19 11:51:13', '7aed8c297a6940f681c26eb6ab68893d', '2022-08-19 11:51:13', '83b40511b8564175ac1f5619a4d4497f', null, 2, '064b3b44b85a45fe87fcce88d72b2519');
INSERT INTO SYS_API (ID, API_NAME, API_URL, API_NOTE, API_TAGS, API_METHOD, API_ARGV_TYPE, API_REQ_ARGV, API_RSP_ARGV, API_RESULT_HANDLER, WHO_CREATED, WHEN_CREATED, WHO_MODIFIED, WHEN_MODIFIED, API_FLOW_ID, API_CODE, CALL_TYPE, APP_ID) VALUES ('dba3e3ea233248ae93618c4762e6f56b', '重新加载faas插件', '/faas/reload/plugin', null, null, 'get', null, null, null, null, 'd4040ae0800844d99406157b798bae01', '2022-06-22 09:51:28', null, '2022-07-14 18:07:13', 'eecb04a8927f40188940264b9afeda96', null, 2, '064b3b44b85a45fe87fcce88d72b2519');
INSERT INTO SYS_API (ID, API_NAME, API_URL, API_NOTE, API_TAGS, API_METHOD, API_ARGV_TYPE, API_REQ_ARGV, API_RSP_ARGV, API_RESULT_HANDLER, WHO_CREATED, WHEN_CREATED, WHO_MODIFIED, WHEN_MODIFIED, API_FLOW_ID, API_CODE, CALL_TYPE, APP_ID) VALUES ('dbdf73ceb5434eed81cc800a4dabc631', '测试导出Excel', '/v1/dev/test/exportExcel', null, null, 'get', null, null, null, null, '7aed8c297a6940f681c26eb6ab68893d', '2022-04-29 19:17:48', null, '2022-07-14 18:07:13', 'd81481b5a4ff4008ad15932d99bf0798', ':open', 2, '064b3b44b85a45fe87fcce88d72b2519');
INSERT INTO SYS_API (ID, API_NAME, API_URL, API_NOTE, API_TAGS, API_METHOD, API_ARGV_TYPE, API_REQ_ARGV, API_RSP_ARGV, API_RESULT_HANDLER, WHO_CREATED, WHEN_CREATED, WHO_MODIFIED, WHEN_MODIFIED, API_FLOW_ID, API_CODE, CALL_TYPE, APP_ID) VALUES ('def3d38efec640a89eca69add286a35a', 'pine-能力树-编辑', '/v1/dev-power-tree/edit', null, 'PINE', 'post', null, null, null, null, '056fb0eeb9a44cb0953534b4c0ca01fa', '2022-05-12 20:11:46', null, '2022-07-14 18:07:13', '23e2b0240ce245c685c763ea2fcf6fa1', null, 2, '064b3b44b85a45fe87fcce88d72b2519');
INSERT INTO SYS_API (ID, API_NAME, API_URL, API_NOTE, API_TAGS, API_METHOD, API_ARGV_TYPE, API_REQ_ARGV, API_RSP_ARGV, API_RESULT_HANDLER, WHO_CREATED, WHEN_CREATED, WHO_MODIFIED, WHEN_MODIFIED, API_FLOW_ID, API_CODE, CALL_TYPE, APP_ID) VALUES ('e0510400cfef4b75ba868993923002cf', '模块管理-关联功能', '/v1/dev-module/relate-function', null, null, 'post', null, null, null, null, '7aed8c297a6940f681c26eb6ab68893d', '2022-05-30 16:29:56', null, '2022-07-14 18:07:14', '39faedc180684b84ba098ed2e8837c80', null, 2, '064b3b44b85a45fe87fcce88d72b2519');
INSERT INTO SYS_API (ID, API_NAME, API_URL, API_NOTE, API_TAGS, API_METHOD, API_ARGV_TYPE, API_REQ_ARGV, API_RSP_ARGV, API_RESULT_HANDLER, WHO_CREATED, WHEN_CREATED, WHO_MODIFIED, WHEN_MODIFIED, API_FLOW_ID, API_CODE, CALL_TYPE, APP_ID) VALUES ('e21c5da07e8545638de8125c64f12206', '删除帮助文档', '/v1/dev_document/delete', null, null, 'delete', null, null, null, null, '61c7c741fc5548f09258139ebcc1daa6', '2022-04-12 09:39:43', null, '2022-07-14 18:07:14', 'b5fddfaf0e894bffb67abd16ca36dd15', null, 2, '064b3b44b85a45fe87fcce88d72b2519');
INSERT INTO SYS_API (ID, API_NAME, API_URL, API_NOTE, API_TAGS, API_METHOD, API_ARGV_TYPE, API_REQ_ARGV, API_RSP_ARGV, API_RESULT_HANDLER, WHO_CREATED, WHEN_CREATED, WHO_MODIFIED, WHEN_MODIFIED, API_FLOW_ID, API_CODE, CALL_TYPE, APP_ID) VALUES ('e2827ac9527543f9b39ae420e170bdee', 'OTA通道-编辑', '/v1/ota-channels/edit', null, 'OTA', 'post', null, null, null, null, '94123ca363dc4dfaa62a6bb5dcd3bf50', '2022-08-23 15:34:18', '94123ca363dc4dfaa62a6bb5dcd3bf50', '2022-08-23 15:34:18', '3687b9a54f704d689ba82056dd9bc9a4', null, 2, '064b3b44b85a45fe87fcce88d72b2519');
INSERT INTO SYS_API (ID, API_NAME, API_URL, API_NOTE, API_TAGS, API_METHOD, API_ARGV_TYPE, API_REQ_ARGV, API_RSP_ARGV, API_RESULT_HANDLER, WHO_CREATED, WHEN_CREATED, WHO_MODIFIED, WHEN_MODIFIED, API_FLOW_ID, API_CODE, CALL_TYPE, APP_ID) VALUES ('e320fbd21bc540d18fb34f4bdf61a75b', '工单编号删除', '/v1/wokr-number/delete', null, null, 'post', null, null, null, null, 'd4040ae0800844d99406157b798bae01', '2022-07-27 15:53:31', 'd4040ae0800844d99406157b798bae01', '2022-07-27 15:53:31', '7f24cb93965044bfabf989c75b5bc7f4', null, 2, '064b3b44b85a45fe87fcce88d72b2519');
INSERT INTO SYS_API (ID, API_NAME, API_URL, API_NOTE, API_TAGS, API_METHOD, API_ARGV_TYPE, API_REQ_ARGV, API_RSP_ARGV, API_RESULT_HANDLER, WHO_CREATED, WHEN_CREATED, WHO_MODIFIED, WHEN_MODIFIED, API_FLOW_ID, API_CODE, CALL_TYPE, APP_ID) VALUES ('01c56b5b01d043d5a75d535cd474ff9e', 'ext-获取插件树', '/v1/ext_plugin/tree/select', null, null, 'get', null, null, null, null, '63454c98827e4a0384abf30e0e6eef54', '2022-03-29 17:45:01', null, '2022-07-14 18:07:00', '2518d42fe1ca4504a60c5dd3f82efec4', null, 2, '064b3b44b85a45fe87fcce88d72b2519');
INSERT INTO SYS_API (ID, API_NAME, API_URL, API_NOTE, API_TAGS, API_METHOD, API_ARGV_TYPE, API_REQ_ARGV, API_RSP_ARGV, API_RESULT_HANDLER, WHO_CREATED, WHEN_CREATED, WHO_MODIFIED, WHEN_MODIFIED, API_FLOW_ID, API_CODE, CALL_TYPE, APP_ID) VALUES ('0215aa7664da421d82c9426c51854572', 'ext-插件树编辑查找带回', '/v1/ext_plugin/tree/getNodeInfo', null, null, 'get', null, null, null, null, '63454c98827e4a0384abf30e0e6eef54', '2022-04-22 16:01:18', null, '2022-07-14 18:07:00', '24e61631a24145ada5fb6cfdac0141f0', null, 2, '064b3b44b85a45fe87fcce88d72b2519');
INSERT INTO SYS_API (ID, API_NAME, API_URL, API_NOTE, API_TAGS, API_METHOD, API_ARGV_TYPE, API_REQ_ARGV, API_RSP_ARGV, API_RESULT_HANDLER, WHO_CREATED, WHEN_CREATED, WHO_MODIFIED, WHEN_MODIFIED, API_FLOW_ID, API_CODE, CALL_TYPE, APP_ID) VALUES ('02b881bbdeba4981a2e2241856f59a19', 'pine-一键导出', '/v1/dev-app/export2', null, 'pine', 'post', null, null, null, null, '056fb0eeb9a44cb0953534b4c0ca01fa', '2022-05-05 17:59:54', null, '2022-07-14 18:07:00', '505b8f987a1b44fdaabadcf42a737f02', null, 2, '064b3b44b85a45fe87fcce88d72b2519');
INSERT INTO SYS_API (ID, API_NAME, API_URL, API_NOTE, API_TAGS, API_METHOD, API_ARGV_TYPE, API_REQ_ARGV, API_RSP_ARGV, API_RESULT_HANDLER, WHO_CREATED, WHEN_CREATED, WHO_MODIFIED, WHEN_MODIFIED, API_FLOW_ID, API_CODE, CALL_TYPE, APP_ID) VALUES ('030aaeb123154bbc89f8e6f6bf7108d5', 'JS函数调试接口', '/v1/dev-function/debug', null, null, 'post', null, null, null, null, '7aed8c297a6940f681c26eb6ab68893d', '2022-06-01 20:34:11', null, '2022-07-14 18:07:01', '5647b1f1c4674d7081787102d8ba6bb5', null, 2, '064b3b44b85a45fe87fcce88d72b2519');
INSERT INTO SYS_API (ID, API_NAME, API_URL, API_NOTE, API_TAGS, API_METHOD, API_ARGV_TYPE, API_REQ_ARGV, API_RSP_ARGV, API_RESULT_HANDLER, WHO_CREATED, WHEN_CREATED, WHO_MODIFIED, WHEN_MODIFIED, API_FLOW_ID, API_CODE, CALL_TYPE, APP_ID) VALUES ('03cd109282aa4298ba7600f861a0e337', '数据源字典', '/v1/sys/data-source', null, null, 'get', null, null, null, null, 'd4040ae0800844d99406157b798bae01', '2022-07-28 14:13:50', 'd4040ae0800844d99406157b798bae01', '2022-07-28 14:13:50', 'e86d717510544130bf14fa76c74bb487', null, 2, '064b3b44b85a45fe87fcce88d72b2519');
INSERT INTO SYS_API (ID, API_NAME, API_URL, API_NOTE, API_TAGS, API_METHOD, API_ARGV_TYPE, API_REQ_ARGV, API_RSP_ARGV, API_RESULT_HANDLER, WHO_CREATED, WHEN_CREATED, WHO_MODIFIED, WHEN_MODIFIED, API_FLOW_ID, API_CODE, CALL_TYPE, APP_ID) VALUES ('086570c710a14b33b4a1906771267180', '查询全部帮助文档', '/v1/dev_document/query', null, null, 'get', null, null, null, null, '61c7c741fc5548f09258139ebcc1daa6', '2022-04-11 15:48:28', null, '2022-07-14 18:07:01', '8b4a05f4a8624bb785b82b52b95988d5', null, 2, '064b3b44b85a45fe87fcce88d72b2519');
INSERT INTO SYS_API (ID, API_NAME, API_URL, API_NOTE, API_TAGS, API_METHOD, API_ARGV_TYPE, API_REQ_ARGV, API_RSP_ARGV, API_RESULT_HANDLER, WHO_CREATED, WHEN_CREATED, WHO_MODIFIED, WHEN_MODIFIED, API_FLOW_ID, API_CODE, CALL_TYPE, APP_ID) VALUES ('096bb6e189c74fb2b4aeabb0fadedae4', '获取最新应用数据包', '/v1/pine/app/update-to-date', null, '开放接口', 'post', null, null, null, null, '94123ca363dc4dfaa62a6bb5dcd3bf50', '2022-08-23 17:06:19', '94123ca363dc4dfaa62a6bb5dcd3bf50', '2022-08-25 09:46:28', '2ae122371b944dc09b4821f84e868eb3', ':open:app-upate-to-date', 2, '064b3b44b85a45fe87fcce88d72b2519');
INSERT INTO SYS_API (ID, API_NAME, API_URL, API_NOTE, API_TAGS, API_METHOD, API_ARGV_TYPE, API_REQ_ARGV, API_RSP_ARGV, API_RESULT_HANDLER, WHO_CREATED, WHEN_CREATED, WHO_MODIFIED, WHEN_MODIFIED, API_FLOW_ID, API_CODE, CALL_TYPE, APP_ID) VALUES ('0dbdcd6cdf5b4af581f5c110e7589404', '根据目录查询帮助文档', '/v1/dev_document/query/content', null, null, 'post', null, null, null, null, '61c7c741fc5548f09258139ebcc1daa6', '2022-04-08 14:58:36', null, '2022-07-14 18:07:01', '3d1e5b5a6b3b4f73837edea295b9e290', null, 2, '064b3b44b85a45fe87fcce88d72b2519');
INSERT INTO SYS_API (ID, API_NAME, API_URL, API_NOTE, API_TAGS, API_METHOD, API_ARGV_TYPE, API_REQ_ARGV, API_RSP_ARGV, API_RESULT_HANDLER, WHO_CREATED, WHEN_CREATED, WHO_MODIFIED, WHEN_MODIFIED, API_FLOW_ID, API_CODE, CALL_TYPE, APP_ID) VALUES ('10381bdac67640ceb714175750896622', 'admin-团队管理', '/v1/admin-team/query', null, 'admin', 'get', null, null, null, null, '7aed8c297a6940f681c26eb6ab68893d', '2022-06-13 01:11:31', null, '2022-07-14 18:07:01', '8529193fe3fb432589c069c0e76e68e4', null, 2, '064b3b44b85a45fe87fcce88d72b2519');
INSERT INTO SYS_API (ID, API_NAME, API_URL, API_NOTE, API_TAGS, API_METHOD, API_ARGV_TYPE, API_REQ_ARGV, API_RSP_ARGV, API_RESULT_HANDLER, WHO_CREATED, WHEN_CREATED, WHO_MODIFIED, WHEN_MODIFIED, API_FLOW_ID, API_CODE, CALL_TYPE, APP_ID) VALUES ('15b3cb74b5214c1f8dc115de1b0d606d', 'pine-在线Excel删除', '/v1/sys-excel/delete', null, null, 'delete', null, null, null, null, '7aed8c297a6940f681c26eb6ab68893d', '2022-07-22 11:04:07', '7aed8c297a6940f681c26eb6ab68893d', '2022-07-22 11:04:07', 'bdc9cf9c71524be4ab7c0cc64687ac98', null, 2, '064b3b44b85a45fe87fcce88d72b2519');
INSERT INTO SYS_API (ID, API_NAME, API_URL, API_NOTE, API_TAGS, API_METHOD, API_ARGV_TYPE, API_REQ_ARGV, API_RSP_ARGV, API_RESULT_HANDLER, WHO_CREATED, WHEN_CREATED, WHO_MODIFIED, WHEN_MODIFIED, API_FLOW_ID, API_CODE, CALL_TYPE, APP_ID) VALUES ('17c94146bd2a440fb5b67dfd5bf69bd9', '通用下拉code唯一检验', '/vi/sys/hint-select/code', null, null, 'get', null, null, null, null, 'd4040ae0800844d99406157b798bae01', '2022-07-28 14:24:48', 'd4040ae0800844d99406157b798bae01', '2022-07-28 14:24:48', '8aaa51af553f416fb603267339b7cc27', null, 2, '064b3b44b85a45fe87fcce88d72b2519');
INSERT INTO SYS_API (ID, API_NAME, API_URL, API_NOTE, API_TAGS, API_METHOD, API_ARGV_TYPE, API_REQ_ARGV, API_RSP_ARGV, API_RESULT_HANDLER, WHO_CREATED, WHEN_CREATED, WHO_MODIFIED, WHEN_MODIFIED, API_FLOW_ID, API_CODE, CALL_TYPE, APP_ID) VALUES ('1842c05a5df74ce6b73d5ae119f227b9', '修改团队', '/v1/admin-team/edit', null, 'admin', 'put', null, null, null, null, '7aed8c297a6940f681c26eb6ab68893d', '2022-06-13 01:40:37', null, '2022-07-14 18:07:01', 'a731094e31d444289a99dc3e0e6bea10', null, 2, '064b3b44b85a45fe87fcce88d72b2519');
INSERT INTO SYS_API (ID, API_NAME, API_URL, API_NOTE, API_TAGS, API_METHOD, API_ARGV_TYPE, API_REQ_ARGV, API_RSP_ARGV, API_RESULT_HANDLER, WHO_CREATED, WHEN_CREATED, WHO_MODIFIED, WHEN_MODIFIED, API_FLOW_ID, API_CODE, CALL_TYPE, APP_ID) VALUES ('1880fa8ba894405e9534818a2f335891', 'pine-能力树-新增', '/v1/dev-power-tree/add', null, 'PINE', 'post', null, null, null, null, '056fb0eeb9a44cb0953534b4c0ca01fa', '2022-05-12 15:11:23', null, '2022-07-14 18:07:01', 'be3df3463ca64cf3a39a2124c71d4b52', null, 2, '064b3b44b85a45fe87fcce88d72b2519');
INSERT INTO SYS_API (ID, API_NAME, API_URL, API_NOTE, API_TAGS, API_METHOD, API_ARGV_TYPE, API_REQ_ARGV, API_RSP_ARGV, API_RESULT_HANDLER, WHO_CREATED, WHEN_CREATED, WHO_MODIFIED, WHEN_MODIFIED, API_FLOW_ID, API_CODE, CALL_TYPE, APP_ID) VALUES ('19b09e8d601640da80cfcc7be568358e', 'OTA通道-删除', '/v1/ota-channels/delete', null, 'OTA', 'post', null, null, null, null, '94123ca363dc4dfaa62a6bb5dcd3bf50', '2022-08-23 15:35:16', '94123ca363dc4dfaa62a6bb5dcd3bf50', '2022-08-23 15:35:16', '708308ba2a0145ab8deb71fed15f2659', null, 2, '064b3b44b85a45fe87fcce88d72b2519');
INSERT INTO SYS_API (ID, API_NAME, API_URL, API_NOTE, API_TAGS, API_METHOD, API_ARGV_TYPE, API_REQ_ARGV, API_RSP_ARGV, API_RESULT_HANDLER, WHO_CREATED, WHEN_CREATED, WHO_MODIFIED, WHEN_MODIFIED, API_FLOW_ID, API_CODE, CALL_TYPE, APP_ID) VALUES ('19fed171953744578b5a68d880608d8c', '应用选择列表', '/v1/dev-app/query', null, 'pine', 'get', null, null, null, null, '7aed8c297a6940f681c26eb6ab68893d', '2022-06-21 07:06:55', null, '2022-07-14 18:07:02', 'e4389348752845939baf7d3d7af108ab', null, 2, '064b3b44b85a45fe87fcce88d72b2519');
INSERT INTO SYS_API (ID, API_NAME, API_URL, API_NOTE, API_TAGS, API_METHOD, API_ARGV_TYPE, API_REQ_ARGV, API_RSP_ARGV, API_RESULT_HANDLER, WHO_CREATED, WHEN_CREATED, WHO_MODIFIED, WHEN_MODIFIED, API_FLOW_ID, API_CODE, CALL_TYPE, APP_ID) VALUES ('1a7ee02f689c40e2b652266cfe96b405', '团队成员管理', '/v1/dev-team-member/query', null, 'pine', 'get', null, null, null, null, '7aed8c297a6940f681c26eb6ab68893d', '2022-06-13 02:01:04', null, '2022-07-14 18:07:02', '31bb5384ba75469e90f7d862572070bc', null, 2, '064b3b44b85a45fe87fcce88d72b2519');
INSERT INTO SYS_API (ID, API_NAME, API_URL, API_NOTE, API_TAGS, API_METHOD, API_ARGV_TYPE, API_REQ_ARGV, API_RSP_ARGV, API_RESULT_HANDLER, WHO_CREATED, WHEN_CREATED, WHO_MODIFIED, WHEN_MODIFIED, API_FLOW_ID, API_CODE, CALL_TYPE, APP_ID) VALUES ('1b9d477520024aa3bb94b12b3eea1340', '用户列表', '/demotest/user/query', null, null, 'get', null, null, null, null, '7aed8c297a6940f681c26eb6ab68893d', '2022-05-23 06:05:31', null, '2022-07-14 18:07:02', 'c9007b575d88487383d13f1f76c7956f', null, 2, '064b3b44b85a45fe87fcce88d72b2519');
INSERT INTO SYS_API (ID, API_NAME, API_URL, API_NOTE, API_TAGS, API_METHOD, API_ARGV_TYPE, API_REQ_ARGV, API_RSP_ARGV, API_RESULT_HANDLER, WHO_CREATED, WHEN_CREATED, WHO_MODIFIED, WHEN_MODIFIED, API_FLOW_ID, API_CODE, CALL_TYPE, APP_ID) VALUES ('1dacb53dd4cf4055ae921c21829ebdf8', 'pine-流程导入', '/v1/sys-kdb-flows/excel/import', null, 'pine', 'post', null, null, null, null, '056fb0eeb9a44cb0953534b4c0ca01fa', '2022-04-01 10:13:47', null, '2022-07-14 18:07:02', null, null, 2, '064b3b44b85a45fe87fcce88d72b2519');
INSERT INTO SYS_API (ID, API_NAME, API_URL, API_NOTE, API_TAGS, API_METHOD, API_ARGV_TYPE, API_REQ_ARGV, API_RSP_ARGV, API_RESULT_HANDLER, WHO_CREATED, WHEN_CREATED, WHO_MODIFIED, WHEN_MODIFIED, API_FLOW_ID, API_CODE, CALL_TYPE, APP_ID) VALUES ('2074998be64f4085b32e34bbd9118c45', '函数库-新增', '/v1/dev-function/add', null, null, 'post', null, null, null, null, '7aed8c297a6940f681c26eb6ab68893d', '2022-05-31 18:57:56', null, '2022-07-14 18:07:02', 'cf7b4b09f52f4b5db166835dcc8ddb45', null, 2, '064b3b44b85a45fe87fcce88d72b2519');
INSERT INTO SYS_API (ID, API_NAME, API_URL, API_NOTE, API_TAGS, API_METHOD, API_ARGV_TYPE, API_REQ_ARGV, API_RSP_ARGV, API_RESULT_HANDLER, WHO_CREATED, WHEN_CREATED, WHO_MODIFIED, WHEN_MODIFIED, API_FLOW_ID, API_CODE, CALL_TYPE, APP_ID) VALUES ('208528c6f55a45289dad7789ec6f17b4', '修改用户', '/demotest/user/update', null, null, 'put', null, null, null, null, '7aed8c297a6940f681c26eb6ab68893d', '2022-05-23 07:40:02', null, '2022-07-14 18:07:02', '9ad5115e5a2f4343baa32f23f9437611', null, 2, '064b3b44b85a45fe87fcce88d72b2519');
INSERT INTO SYS_API (ID, API_NAME, API_URL, API_NOTE, API_TAGS, API_METHOD, API_ARGV_TYPE, API_REQ_ARGV, API_RSP_ARGV, API_RESULT_HANDLER, WHO_CREATED, WHEN_CREATED, WHO_MODIFIED, WHEN_MODIFIED, API_FLOW_ID, API_CODE, CALL_TYPE, APP_ID) VALUES ('20bc85bf512847a3a4e8238023e0d2c8', '韦奕涛-临时-测试函数', '/dev-wyt', null, null, 'get', null, null, null, null, '8c263f81d2b14784906787d67ce58b47', '2022-06-07 09:14:58', null, '2022-07-14 18:07:02', 'c3f02c07595c4a47a49c5ffdcc3a9926', null, 2, '064b3b44b85a45fe87fcce88d72b2519');
INSERT INTO SYS_API (ID, API_NAME, API_URL, API_NOTE, API_TAGS, API_METHOD, API_ARGV_TYPE, API_REQ_ARGV, API_RSP_ARGV, API_RESULT_HANDLER, WHO_CREATED, WHEN_CREATED, WHO_MODIFIED, WHEN_MODIFIED, API_FLOW_ID, API_CODE, CALL_TYPE, APP_ID) VALUES ('223acb160dbf4c24843e474e675e7186', 'FAAS插件-查询', '/v1/pine/plugins/query', null, '插件市场', 'get', null, null, null, null, '94123ca363dc4dfaa62a6bb5dcd3bf50', '2022-08-31 17:15:37', '94123ca363dc4dfaa62a6bb5dcd3bf50', '2022-08-31 17:25:38', '44df9bdbda1142bbb3593d209c2bac23', ':open:plugin-query', 2, '064b3b44b85a45fe87fcce88d72b2519');
INSERT INTO SYS_API (ID, API_NAME, API_URL, API_NOTE, API_TAGS, API_METHOD, API_ARGV_TYPE, API_REQ_ARGV, API_RSP_ARGV, API_RESULT_HANDLER, WHO_CREATED, WHEN_CREATED, WHO_MODIFIED, WHEN_MODIFIED, API_FLOW_ID, API_CODE, CALL_TYPE, APP_ID) VALUES ('224466eb82474add94efb50875636924', 'pine-页面设计历史记录', '/v1/dev-page-history/query', null, null, 'get', null, null, null, null, '7aed8c297a6940f681c26eb6ab68893d', '2022-07-13 15:15:07', '7aed8c297a6940f681c26eb6ab68893d', '2022-07-13 15:15:07', 'f4d4b1b93dda4f7d8b8d87e86f104258', null, 2, '064b3b44b85a45fe87fcce88d72b2519');
INSERT INTO SYS_API (ID, API_NAME, API_URL, API_NOTE, API_TAGS, API_METHOD, API_ARGV_TYPE, API_REQ_ARGV, API_RSP_ARGV, API_RESULT_HANDLER, WHO_CREATED, WHEN_CREATED, WHO_MODIFIED, WHEN_MODIFIED, API_FLOW_ID, API_CODE, CALL_TYPE, APP_ID) VALUES ('22a060a42a4a4ba089a03f6b2a70bcfe', 'OTA通道-查询', '/v1/ota-channels/query', null, 'OTA', 'post', null, null, null, null, '94123ca363dc4dfaa62a6bb5dcd3bf50', '2022-08-23 15:36:50', '94123ca363dc4dfaa62a6bb5dcd3bf50', '2022-08-23 15:36:50', '8a3a91bf416741b0bcb488b6537092dc', null, 2, '064b3b44b85a45fe87fcce88d72b2519');
INSERT INTO SYS_API (ID, API_NAME, API_URL, API_NOTE, API_TAGS, API_METHOD, API_ARGV_TYPE, API_REQ_ARGV, API_RSP_ARGV, API_RESULT_HANDLER, WHO_CREATED, WHEN_CREATED, WHO_MODIFIED, WHEN_MODIFIED, API_FLOW_ID, API_CODE, CALL_TYPE, APP_ID) VALUES ('23574458c1ec4527958b810cfbf337fb', '工单编号新增', '/v1/wokr-number', null, null, 'post', null, null, null, null, 'd4040ae0800844d99406157b798bae01', '2022-07-27 15:18:17', 'd4040ae0800844d99406157b798bae01', '2022-07-27 15:18:17', '36e539258c674ee1a631c332dc5cb2f4', null, 2, '064b3b44b85a45fe87fcce88d72b2519');
INSERT INTO SYS_API (ID, API_NAME, API_URL, API_NOTE, API_TAGS, API_METHOD, API_ARGV_TYPE, API_REQ_ARGV, API_RSP_ARGV, API_RESULT_HANDLER, WHO_CREATED, WHEN_CREATED, WHO_MODIFIED, WHEN_MODIFIED, API_FLOW_ID, API_CODE, CALL_TYPE, APP_ID) VALUES ('241eec9b62304a0da5145807ed940d53', '新增用户', '/demotest/user/add', null, null, 'post', null, null, null, null, '7aed8c297a6940f681c26eb6ab68893d', '2022-05-23 06:26:50', null, '2022-07-14 18:07:02', '787d81dd96a44d7e868cc77e793450bf', null, 2, '064b3b44b85a45fe87fcce88d72b2519');
INSERT INTO SYS_API (ID, API_NAME, API_URL, API_NOTE, API_TAGS, API_METHOD, API_ARGV_TYPE, API_REQ_ARGV, API_RSP_ARGV, API_RESULT_HANDLER, WHO_CREATED, WHEN_CREATED, WHO_MODIFIED, WHEN_MODIFIED, API_FLOW_ID, API_CODE, CALL_TYPE, APP_ID) VALUES ('245b11c3a7b147e8aa600b338737bbc3', 'OTA通道-新增', '/v1/ota-channels/add', null, 'OTA', 'post', null, null, null, null, '94123ca363dc4dfaa62a6bb5dcd3bf50', '2022-08-23 15:33:38', '94123ca363dc4dfaa62a6bb5dcd3bf50', '2022-08-23 15:34:34', '8db8355e4a8648b0a9672e73b4237ce2', null, 2, '064b3b44b85a45fe87fcce88d72b2519');
INSERT INTO SYS_API (ID, API_NAME, API_URL, API_NOTE, API_TAGS, API_METHOD, API_ARGV_TYPE, API_REQ_ARGV, API_RSP_ARGV, API_RESULT_HANDLER, WHO_CREATED, WHEN_CREATED, WHO_MODIFIED, WHEN_MODIFIED, API_FLOW_ID, API_CODE, CALL_TYPE, APP_ID) VALUES ('2577e3b013eb424cb9fe554de8bc8604', '编辑帮助文档', '/v1/dev_document/edit', null, null, 'put', null, null, null, null, '61c7c741fc5548f09258139ebcc1daa6', '2022-04-11 16:22:55', null, '2022-07-14 18:07:02', '428de4c149f248aabebaa941609e98e7', null, 2, '064b3b44b85a45fe87fcce88d72b2519');
INSERT INTO SYS_API (ID, API_NAME, API_URL, API_NOTE, API_TAGS, API_METHOD, API_ARGV_TYPE, API_REQ_ARGV, API_RSP_ARGV, API_RESULT_HANDLER, WHO_CREATED, WHEN_CREATED, WHO_MODIFIED, WHEN_MODIFIED, API_FLOW_ID, API_CODE, CALL_TYPE, APP_ID) VALUES ('2633a92337754a41af8a08a7d53b7a3d', 'pine-应用获取', '/v2/dev-app/{id}', null, null, 'get', null, null, null, null, 'a7d903b65e8c42479b9774db664f9468', '2022-08-24 15:16:51', 'a7d903b65e8c42479b9774db664f9468', '2022-08-24 15:16:51', '377199d95f5f4824b9f9bb94d47a7c87', null, 2, '064b3b44b85a45fe87fcce88d72b2519');
INSERT INTO SYS_API (ID, API_NAME, API_URL, API_NOTE, API_TAGS, API_METHOD, API_ARGV_TYPE, API_REQ_ARGV, API_RSP_ARGV, API_RESULT_HANDLER, WHO_CREATED, WHEN_CREATED, WHO_MODIFIED, WHEN_MODIFIED, API_FLOW_ID, API_CODE, CALL_TYPE, APP_ID) VALUES ('e32ebbfb315a48ac9cca1461cb0debf3', '逻辑编排标签', '/v1/dev-logic-flow/tags', null, null, 'get', null, null, null, null, '7aed8c297a6940f681c26eb6ab68893d', '2022-04-26 19:12:27', null, '2022-07-14 18:07:14', '02f3fac07e444330a3a249f803ef5453', null, 2, '064b3b44b85a45fe87fcce88d72b2519');
INSERT INTO SYS_API (ID, API_NAME, API_URL, API_NOTE, API_TAGS, API_METHOD, API_ARGV_TYPE, API_REQ_ARGV, API_RSP_ARGV, API_RESULT_HANDLER, WHO_CREATED, WHEN_CREATED, WHO_MODIFIED, WHEN_MODIFIED, API_FLOW_ID, API_CODE, CALL_TYPE, APP_ID) VALUES ('e5dfa1698bc04168b98af5520efef686', '青松插件-新增', '/v1/dev-plugins/add', null, '青松插件', 'post', null, null, null, null, '94123ca363dc4dfaa62a6bb5dcd3bf50', '2022-08-12 11:30:05', '94123ca363dc4dfaa62a6bb5dcd3bf50', '2022-08-12 11:30:05', '48d1e1aa343c4c88ba8b34ba80fc4c9c', null, 2, '064b3b44b85a45fe87fcce88d72b2519');
INSERT INTO SYS_API (ID, API_NAME, API_URL, API_NOTE, API_TAGS, API_METHOD, API_ARGV_TYPE, API_REQ_ARGV, API_RSP_ARGV, API_RESULT_HANDLER, WHO_CREATED, WHEN_CREATED, WHO_MODIFIED, WHEN_MODIFIED, API_FLOW_ID, API_CODE, CALL_TYPE, APP_ID) VALUES ('e98b9d1890514724aa2efff89a26bc35', '函数库-列表', '/v1/dev-function/query', null, null, 'get', null, null, null, null, '7aed8c297a6940f681c26eb6ab68893d', '2022-05-31 18:57:28', null, '2022-07-14 18:07:14', 'b40c094311ea479eaa81b8ac7836f9f0', null, 2, '064b3b44b85a45fe87fcce88d72b2519');
INSERT INTO SYS_API (ID, API_NAME, API_URL, API_NOTE, API_TAGS, API_METHOD, API_ARGV_TYPE, API_REQ_ARGV, API_RSP_ARGV, API_RESULT_HANDLER, WHO_CREATED, WHEN_CREATED, WHO_MODIFIED, WHEN_MODIFIED, API_FLOW_ID, API_CODE, CALL_TYPE, APP_ID) VALUES ('ea7a97e780a44ccc9b52ffe5b97e24f4', 'pine-导出页面HTML', '/v1/dev-page/exportToHtml', null, 'pine', 'post', null, null, null, null, '7aed8c297a6940f681c26eb6ab68893d', '2022-06-22 20:25:31', null, '2022-07-14 18:07:14', '01ba8e8129d44e149ebf1d2f95e3113f', null, 2, '064b3b44b85a45fe87fcce88d72b2519');
INSERT INTO SYS_API (ID, API_NAME, API_URL, API_NOTE, API_TAGS, API_METHOD, API_ARGV_TYPE, API_REQ_ARGV, API_RSP_ARGV, API_RESULT_HANDLER, WHO_CREATED, WHEN_CREATED, WHO_MODIFIED, WHEN_MODIFIED, API_FLOW_ID, API_CODE, CALL_TYPE, APP_ID) VALUES ('eaeb62776c354aab81818c3785cc9f40', 'pine-在线Excel列表', '/v1/sys-excel/query', null, null, 'get', null, null, null, null, '7aed8c297a6940f681c26eb6ab68893d', '2022-07-22 09:13:25', '7aed8c297a6940f681c26eb6ab68893d', '2022-07-22 09:13:25', '7abf1c6d97df4d57903b282dbc15a1d6', null, 2, '064b3b44b85a45fe87fcce88d72b2519');
INSERT INTO SYS_API (ID, API_NAME, API_URL, API_NOTE, API_TAGS, API_METHOD, API_ARGV_TYPE, API_REQ_ARGV, API_RSP_ARGV, API_RESULT_HANDLER, WHO_CREATED, WHEN_CREATED, WHO_MODIFIED, WHEN_MODIFIED, API_FLOW_ID, API_CODE, CALL_TYPE, APP_ID) VALUES ('eeac6f15d4284526adf447b268f30c14', 'pine-接口导入', '/v1/sys-apis/excel/import', null, 'pine', 'post', null, null, null, null, '056fb0eeb9a44cb0953534b4c0ca01fa', '2022-03-31 16:17:43', null, '2022-07-14 18:07:14', 'f6211539813c4ed492f97fd551025781', null, 2, '064b3b44b85a45fe87fcce88d72b2519');
INSERT INTO SYS_API (ID, API_NAME, API_URL, API_NOTE, API_TAGS, API_METHOD, API_ARGV_TYPE, API_REQ_ARGV, API_RSP_ARGV, API_RESULT_HANDLER, WHO_CREATED, WHEN_CREATED, WHO_MODIFIED, WHEN_MODIFIED, API_FLOW_ID, API_CODE, CALL_TYPE, APP_ID) VALUES ('efc99810852845c589dca166e3fabadd', '函数库-删除', '/v1/dev-function/del', null, null, 'delete', null, null, null, null, '7aed8c297a6940f681c26eb6ab68893d', '2022-05-31 18:58:41', null, '2022-07-14 18:07:14', '2a3753b67c8341dfaf209342b04178b8', null, 2, '064b3b44b85a45fe87fcce88d72b2519');
INSERT INTO SYS_API (ID, API_NAME, API_URL, API_NOTE, API_TAGS, API_METHOD, API_ARGV_TYPE, API_REQ_ARGV, API_RSP_ARGV, API_RESULT_HANDLER, WHO_CREATED, WHEN_CREATED, WHO_MODIFIED, WHEN_MODIFIED, API_FLOW_ID, API_CODE, CALL_TYPE, APP_ID) VALUES ('f07c30e8484b499c8433deebfed5bc95', 'pine-能力树-树查询', '/v1/power-tree/funTree', null, null, 'get', null, null, null, null, '7aed8c297a6940f681c26eb6ab68893d', '2022-06-02 15:52:27', null, '2022-07-14 18:07:15', 'e6c51ee0342e429c8127683866398b04', null, 2, '064b3b44b85a45fe87fcce88d72b2519');
INSERT INTO SYS_API (ID, API_NAME, API_URL, API_NOTE, API_TAGS, API_METHOD, API_ARGV_TYPE, API_REQ_ARGV, API_RSP_ARGV, API_RESULT_HANDLER, WHO_CREATED, WHEN_CREATED, WHO_MODIFIED, WHEN_MODIFIED, API_FLOW_ID, API_CODE, CALL_TYPE, APP_ID) VALUES ('f1cbf52a912f464fb22eb1703c67a55c', '模块管理-新增', '/v1/dev-module/add', null, null, 'post', null, null, null, null, '7aed8c297a6940f681c26eb6ab68893d', '2022-05-30 16:26:04', null, '2022-07-14 18:07:15', '9a5ff53cc6f84447909ad050697bc978', null, 2, '064b3b44b85a45fe87fcce88d72b2519');
INSERT INTO SYS_API (ID, API_NAME, API_URL, API_NOTE, API_TAGS, API_METHOD, API_ARGV_TYPE, API_REQ_ARGV, API_RSP_ARGV, API_RESULT_HANDLER, WHO_CREATED, WHEN_CREATED, WHO_MODIFIED, WHEN_MODIFIED, API_FLOW_ID, API_CODE, CALL_TYPE, APP_ID) VALUES ('f1dec52312e44fe9b1b424c9cb71388f', 'PINE在线用户-查询', '/v1/sys-online-users/query', null, 'PINE', 'get', null, null, null, null, '056fb0eeb9a44cb0953534b4c0ca01fa', '2022-04-27 20:24:12', null, '2022-07-14 18:07:15', 'dcbc300394d048238712129c5cd38245', null, 2, '064b3b44b85a45fe87fcce88d72b2519');
INSERT INTO SYS_API (ID, API_NAME, API_URL, API_NOTE, API_TAGS, API_METHOD, API_ARGV_TYPE, API_REQ_ARGV, API_RSP_ARGV, API_RESULT_HANDLER, WHO_CREATED, WHEN_CREATED, WHO_MODIFIED, WHEN_MODIFIED, API_FLOW_ID, API_CODE, CALL_TYPE, APP_ID) VALUES ('f68bc6f7e9c142a79ac43a032e8a1f91', '接口导出', '/v1/sys-apis/exports', null, '接口管理', 'post', null, null, null, null, '056fb0eeb9a44cb0953534b4c0ca01fa', '2022-03-31 11:30:28', null, '2022-07-14 18:07:15', '99ffe3178b684fea9314912e55efcc2b', null, 2, '064b3b44b85a45fe87fcce88d72b2519');
INSERT INTO SYS_API (ID, API_NAME, API_URL, API_NOTE, API_TAGS, API_METHOD, API_ARGV_TYPE, API_REQ_ARGV, API_RSP_ARGV, API_RESULT_HANDLER, WHO_CREATED, WHEN_CREATED, WHO_MODIFIED, WHEN_MODIFIED, API_FLOW_ID, API_CODE, CALL_TYPE, APP_ID) VALUES ('fa96971972ea4218bda5d6e7891143b3', 'ext-根据插件ID获取插件类型', '/ext/plugin/getTypeById', null, null, 'get', null, null, null, null, 'd4040ae0800844d99406157b798bae01', '2022-05-27 17:21:33', null, '2022-07-14 18:07:15', 'e2d983117777496b81916ba1f17a713e', null, 2, '064b3b44b85a45fe87fcce88d72b2519');
INSERT INTO SYS_API (ID, API_NAME, API_URL, API_NOTE, API_TAGS, API_METHOD, API_ARGV_TYPE, API_REQ_ARGV, API_RSP_ARGV, API_RESULT_HANDLER, WHO_CREATED, WHEN_CREATED, WHO_MODIFIED, WHEN_MODIFIED, API_FLOW_ID, API_CODE, CALL_TYPE, APP_ID) VALUES ('fc5f89f286aa43d386dc5a1c41dc5ee6', '模块管理-排序', '/v1/dev-module/sort', null, null, 'post', null, null, null, null, '7aed8c297a6940f681c26eb6ab68893d', '2022-05-30 16:27:30', null, '2022-07-14 18:07:15', '1a5c45d406284251bebc1f5b189e3a20', null, 2, '064b3b44b85a45fe87fcce88d72b2519');
INSERT INTO SYS_CONFIG (ID, "NAME", CODE, "VALUE", IS_SYS, NOTE, WHO_CREATED, WHEN_CREATED, WHO_MODIFIED, WHEN_MODIFIED, VALUE_TYPE, APP_ID) VALUES ('9f9021e73fc540968926114214a43739', '系统用户密码校验正则', 'application.user.passwordValidate', '^(?![a-zA-Z]+$)(?![A-Z0-9]+$)(?![A-Z\W_-]+$)(?![a-z0-9]+$)(?![a-z\W_-]+$)(?![0-9\W_-]+$)[a-zA-Z0-9\W_-]', 1, null, '7aed8c297a6940f681c26eb6ab68893d', '2022-08-26 11:42:19', '7aed8c297a6940f681c26eb6ab68893d', '2022-08-26 11:47:28', 0, '064b3b44b85a45fe87fcce88d72b2519');
INSERT INTO SYS_CONFIG (ID, "NAME", CODE, "VALUE", IS_SYS, NOTE, WHO_CREATED, WHEN_CREATED, WHO_MODIFIED, WHEN_MODIFIED, VALUE_TYPE, APP_ID) VALUES ('a11c6a1e8e834ed4b20b3f90da8a591d', '流程设计图动态页面ID', 'application.flow.pageId', '11', 0, '11', '94123ca363dc4dfaa62a6bb5dcd3bf50', '2022-07-18 15:43:36', '94123ca363dc4dfaa62a6bb5dcd3bf50', '2022-07-18 15:43:52', 0, '064b3b44b85a45fe87fcce88d72b2519');
INSERT INTO SYS_CONFIG (ID, "NAME", CODE, "VALUE", IS_SYS, NOTE, WHO_CREATED, WHEN_CREATED, WHO_MODIFIED, WHEN_MODIFIED, VALUE_TYPE, APP_ID) VALUES ('b2f6b6579906447caf37faf8a118cdec', 'faas服务', 'application.faasServer', 'http://10.11.2.215:8030/', 1, null, 'd4040ae0800844d99406157b798bae01', '2022-06-22 09:55:32', null, '2022-07-14 18:07:32', 0, '064b3b44b85a45fe87fcce88d72b2519');
INSERT INTO SYS_CONFIG (ID, "NAME", CODE, "VALUE", IS_SYS, NOTE, WHO_CREATED, WHEN_CREATED, WHO_MODIFIED, WHEN_MODIFIED, VALUE_TYPE, APP_ID) VALUES ('db152dd222a641cab7848c93027181a5', 'test', 'test.aaa', 'tests111', 0, null, '7aed8c297a6940f681c26eb6ab68893d', '2022-08-22 14:21:33', '7aed8c297a6940f681c26eb6ab68893d', '2022-08-22 14:21:33', 0, '064b3b44b85a45fe87fcce88d72b2519');
INSERT INTO SYS_CONFIG (ID, "NAME", CODE, "VALUE", IS_SYS, NOTE, WHO_CREATED, WHEN_CREATED, WHO_MODIFIED, WHEN_MODIFIED, VALUE_TYPE, APP_ID) VALUES ('f7a252bdf9b8467da21122a8fcef0817', '系统用户密码校验提示信息', 'application.user.passwordValidateMessage', '必须包含大写字母，小写字母，数字，特殊符号''_-''中任意3项', 1, null, '7aed8c297a6940f681c26eb6ab68893d', '2022-08-26 11:48:34', '7aed8c297a6940f681c26eb6ab68893d', '2022-08-26 11:48:34', 0, '064b3b44b85a45fe87fcce88d72b2519');
INSERT INTO SYS_DICT (ID, "NAME", CODE, NOTE, WHO_CREATED, WHEN_CREATED, WHO_MODIFIED, WHEN_MODIFIED, APP_ID) VALUES ('27906b13c5c0484396e284a4368b24b3', '参数类型', 'sys_config_value_type', null, null, '2022-01-17 17:36:40', null, '2022-01-17 17:36:40', null);
INSERT INTO SYS_DICT (ID, "NAME", CODE, NOTE, WHO_CREATED, WHEN_CREATED, WHO_MODIFIED, WHEN_MODIFIED, APP_ID) VALUES ('39788cbdd2fa4c54a92815108f04b9aa', '能力分类', 'dev_power_type', null, '056fb0eeb9a44cb0953534b4c0ca01fa', '2022-05-12 17:53:16', null, '2022-07-14 18:07:16', '064b3b44b85a45fe87fcce88d72b2519');
INSERT INTO SYS_DICT (ID, "NAME", CODE, NOTE, WHO_CREATED, WHEN_CREATED, WHO_MODIFIED, WHEN_MODIFIED, APP_ID) VALUES ('4029aaef94ef4ab884c0c0bf1d7ec79b', '开放账号授权类型', 'open_account_auth_type', null, '056fb0eeb9a44cb0953534b4c0ca01fa', '2022-05-12 17:49:32', null, '2022-07-14 18:07:17', '064b3b44b85a45fe87fcce88d72b2519');
INSERT INTO SYS_DICT (ID, "NAME", CODE, NOTE, WHO_CREATED, WHEN_CREATED, WHO_MODIFIED, WHEN_MODIFIED, APP_ID) VALUES ('477bf88140714eb4b04c89c75b1917ad', '在线Excel数据来源类型', 'pine_excel_data_from', '1. 普通 2. Excel文件 3. 数据源', '7aed8c297a6940f681c26eb6ab68893d', '2022-07-25 16:15:08', '7aed8c297a6940f681c26eb6ab68893d', '2022-07-25 16:15:08', '064b3b44b85a45fe87fcce88d72b2519');
INSERT INTO SYS_DICT_ITEM (ID, "NAME", GROUP_NAME, SYS_DICT_ID, CODE, "VALUE", ORDER_NUM, NOTE, WHO_CREATED, WHEN_CREATED, WHO_MODIFIED, WHEN_MODIFIED, APP_ID) VALUES ('13d5b69264c4428790519ef2f93f0d64', '函数', null, '39788cbdd2fa4c54a92815108f04b9aa', 'dev_power_type', '2', 2, null, '056fb0eeb9a44cb0953534b4c0ca01fa', '2022-05-12 17:53:40', '056fb0eeb9a44cb0953534b4c0ca01fa', '2022-05-12 17:53:40', null);
INSERT INTO SYS_DICT_ITEM (ID, "NAME", GROUP_NAME, SYS_DICT_ID, CODE, "VALUE", ORDER_NUM, NOTE, WHO_CREATED, WHEN_CREATED, WHO_MODIFIED, WHEN_MODIFIED, APP_ID) VALUES ('47765e2eac794f538a31bb2e5fe6cb0a', '普通', null, '477bf88140714eb4b04c89c75b1917ad', 'pine_excel_data_from', '1', 1, null, '7aed8c297a6940f681c26eb6ab68893d', '2022-07-25 16:16:49', '7aed8c297a6940f681c26eb6ab68893d', '2022-07-25 16:16:49', '064b3b44b85a45fe87fcce88d72b2519');
INSERT INTO SYS_DICT_ITEM (ID, "NAME", GROUP_NAME, SYS_DICT_ID, CODE, "VALUE", ORDER_NUM, NOTE, WHO_CREATED, WHEN_CREATED, WHO_MODIFIED, WHEN_MODIFIED, APP_ID) VALUES ('53fa72c48f4c4324bd36733fe871b242', '文本', null, '27906b13c5c0484396e284a4368b24b3', 'sys_config_value_type', '0', 1, null, null, '2022-01-17 17:36:59', null, '2022-01-17 17:36:59', null);
INSERT INTO SYS_DICT_ITEM (ID, "NAME", GROUP_NAME, SYS_DICT_ID, CODE, "VALUE", ORDER_NUM, NOTE, WHO_CREATED, WHEN_CREATED, WHO_MODIFIED, WHEN_MODIFIED, APP_ID) VALUES ('5e1ce0a5a7c24d5795cfd500418dd984', '简单类型', null, '4029aaef94ef4ab884c0c0bf1d7ec79b', 'open_account_auth_type', '1', 1, null, '056fb0eeb9a44cb0953534b4c0ca01fa', '2022-05-12 17:49:47', '056fb0eeb9a44cb0953534b4c0ca01fa', '2022-05-12 17:49:47', null);
INSERT INTO SYS_DICT_ITEM (ID, "NAME", GROUP_NAME, SYS_DICT_ID, CODE, "VALUE", ORDER_NUM, NOTE, WHO_CREATED, WHEN_CREATED, WHO_MODIFIED, WHEN_MODIFIED, APP_ID) VALUES ('70821b7e46b54258a37d491860af63bb', '数据集', null, '477bf88140714eb4b04c89c75b1917ad', 'pine_excel_data_from', '3', 3, null, '7aed8c297a6940f681c26eb6ab68893d', '2022-07-25 16:17:21', '7aed8c297a6940f681c26eb6ab68893d', '2022-07-25 16:17:21', '064b3b44b85a45fe87fcce88d72b2519');
INSERT INTO SYS_DICT_ITEM (ID, "NAME", GROUP_NAME, SYS_DICT_ID, CODE, "VALUE", ORDER_NUM, NOTE, WHO_CREATED, WHEN_CREATED, WHO_MODIFIED, WHEN_MODIFIED, APP_ID) VALUES ('97dd1f9d789144099ce6977156fb2e30', '逻辑编排', null, '39788cbdd2fa4c54a92815108f04b9aa', 'dev_power_type', '1', 1, null, '056fb0eeb9a44cb0953534b4c0ca01fa', '2022-05-12 17:53:32', '056fb0eeb9a44cb0953534b4c0ca01fa', '2022-05-12 17:53:32', null);
INSERT INTO SYS_DICT_ITEM (ID, "NAME", GROUP_NAME, SYS_DICT_ID, CODE, "VALUE", ORDER_NUM, NOTE, WHO_CREATED, WHEN_CREATED, WHO_MODIFIED, WHEN_MODIFIED, APP_ID) VALUES ('98dba411055b460c913ab5454a6718c9', '图片', null, '27906b13c5c0484396e284a4368b24b3', 'sys_config_value_type', '1', 2, null, null, '2022-01-17 17:37:07', null, '2022-01-17 17:37:07', null);
INSERT INTO SYS_DICT_ITEM (ID, "NAME", GROUP_NAME, SYS_DICT_ID, CODE, "VALUE", ORDER_NUM, NOTE, WHO_CREATED, WHEN_CREATED, WHO_MODIFIED, WHEN_MODIFIED, APP_ID) VALUES ('a988ca46b6b445fc8f36d8b9d1243cb8', '颜色', null, '27906b13c5c0484396e284a4368b24b3', 'sys_config_value_type', '2', 3, null, null, '2022-01-17 17:37:15', null, '2022-01-17 17:37:15', null);
INSERT INTO SYS_DICT_ITEM (ID, "NAME", GROUP_NAME, SYS_DICT_ID, CODE, "VALUE", ORDER_NUM, NOTE, WHO_CREATED, WHEN_CREATED, WHO_MODIFIED, WHEN_MODIFIED, APP_ID) VALUES ('e3dc63108d5b497cbaf8fba707ad7019', 'Excel文件', null, '477bf88140714eb4b04c89c75b1917ad', 'pine_excel_data_from', '2', 2, null, '7aed8c297a6940f681c26eb6ab68893d', '2022-07-25 16:17:06', '7aed8c297a6940f681c26eb6ab68893d', '2022-07-25 16:17:06', '064b3b44b85a45fe87fcce88d72b2519');
INSERT INTO SYS_MENU (ID, "NAME", PARENT_ID, ICON, CODE, ROUTER_PATH, COMPONENT_PATH, IS_HIDDEN, MENU_TYPE, API_CODES, OPEN_MODE, KEEP_ALIVE, "PATH", ORDER_NUM, STATUS, WHO_CREATED, WHEN_CREATED, WHO_MODIFIED, WHEN_MODIFIED, APP_ID, DATA_TYPE, THEME, PAGE_TYPE, SIDEBAR_NAV_MODE, TOP_NAV_MODE, MAIN_MODE, PAGE_ID, FULL_PATH, IS_DEV) VALUES ('611b9195b7ce4b3fb37f41023a907bda', '角色管理', 'c2348bbf343a47b5852f12ee32869b13', 'peoples', 'role', 'role/index', '/sys/role/index', 0, 'C', null, 0, 1, '/c2348bbf343a47b5852f12ee32869b13/611b9195b7ce4b3fb37f41023a907bda/', 2, 1, '056fb0eeb9a44cb0953534b4c0ca01fa', '2021-12-30 11:11:11', ' ', '2022-02-23 00:29:33', null, null, null, null, null, null, null, null, '/sys/info/role/index', 0);
INSERT INTO SYS_MENU (ID, "NAME", PARENT_ID, ICON, CODE, ROUTER_PATH, COMPONENT_PATH, IS_HIDDEN, MENU_TYPE, API_CODES, OPEN_MODE, KEEP_ALIVE, "PATH", ORDER_NUM, STATUS, WHO_CREATED, WHEN_CREATED, WHO_MODIFIED, WHEN_MODIFIED, APP_ID, DATA_TYPE, THEME, PAGE_TYPE, SIDEBAR_NAV_MODE, TOP_NAV_MODE, MAIN_MODE, PAGE_ID, FULL_PATH, IS_DEV) VALUES ('692314da0a4d4c8dbc601b0b2776e0cd', 'preview5', '15c109ba424342948333deba7e0e3a76', null, null, 'preview5', null, 1, 'C', null, 0, 1, '/15c109ba424342948333deba7e0e3a76/692314da0a4d4c8dbc601b0b2776e0cd/', 99, 0, '056fb0eeb9a44cb0953534b4c0ca01fa', '2022-05-25 17:52:57', '056fb0eeb9a44cb0953534b4c0ca01fa', '2022-05-25 17:52:57', '064b3b44b85a45fe87fcce88d72b2519', null, null, null, null, null, null, null, null, null);
INSERT INTO SYS_MENU (ID, "NAME", PARENT_ID, ICON, CODE, ROUTER_PATH, COMPONENT_PATH, IS_HIDDEN, MENU_TYPE, API_CODES, OPEN_MODE, KEEP_ALIVE, "PATH", ORDER_NUM, STATUS, WHO_CREATED, WHEN_CREATED, WHO_MODIFIED, WHEN_MODIFIED, APP_ID, DATA_TYPE, THEME, PAGE_TYPE, SIDEBAR_NAV_MODE, TOP_NAV_MODE, MAIN_MODE, PAGE_ID, FULL_PATH, IS_DEV) VALUES ('7462f2e0b4c545a6841cfa59e9736aa4', '函数库', '15c109ba424342948333deba7e0e3a76', 'code', 'kdb-fun', 'kdb-funs/index', '/sys/kdbFun/index', 0, 'C', 'kdb-fun', 0, 1, '/15c109ba424342948333deba7e0e3a76/7462f2e0b4c545a6841cfa59e9736aa4/', 8, 0, ' ', '2022-02-10 18:57:30', ' ', '2022-02-23 00:44:18', '064b3b44b85a45fe87fcce88d72b2519', 2, 'dev', 1, 0, 3, 1, null, '/dev/kdb-funs/index', null);
INSERT INTO SYS_MENU (ID, "NAME", PARENT_ID, ICON, CODE, ROUTER_PATH, COMPONENT_PATH, IS_HIDDEN, MENU_TYPE, API_CODES, OPEN_MODE, KEEP_ALIVE, "PATH", ORDER_NUM, STATUS, WHO_CREATED, WHEN_CREATED, WHO_MODIFIED, WHEN_MODIFIED, APP_ID, DATA_TYPE, THEME, PAGE_TYPE, SIDEBAR_NAV_MODE, TOP_NAV_MODE, MAIN_MODE, PAGE_ID, FULL_PATH, IS_DEV) VALUES ('843af15ab7694d54af793e4a5e6fb76e', '系统配置', null, 'system', 'system-config', 'sys/conf', null, 0, 'M', null, 0, 1, '/843af15ab7694d54af793e4a5e6fb76e/', 5, 1, '056fb0eeb9a44cb0953534b4c0ca01fa', '2021-12-30 11:11:11', ' ', '2022-01-10 19:02:56', null, null, null, null, null, null, null, null, '/sys/conf', 0);
INSERT INTO SYS_MENU (ID, "NAME", PARENT_ID, ICON, CODE, ROUTER_PATH, COMPONENT_PATH, IS_HIDDEN, MENU_TYPE, API_CODES, OPEN_MODE, KEEP_ALIVE, "PATH", ORDER_NUM, STATUS, WHO_CREATED, WHEN_CREATED, WHO_MODIFIED, WHEN_MODIFIED, APP_ID, DATA_TYPE, THEME, PAGE_TYPE, SIDEBAR_NAV_MODE, TOP_NAV_MODE, MAIN_MODE, PAGE_ID, FULL_PATH, IS_DEV) VALUES ('96326f8a472f49e483aacf0b2413f96b', '数据源管理', '15c109ba424342948333deba7e0e3a76', 'data-query', 'dev-kdb-data-source', 'dev-kdb-data-source/index', '/sys/kdbDataSource/index', 0, 'C', null, 0, 1, '/15c109ba424342948333deba7e0e3a76/96326f8a472f49e483aacf0b2413f96b/', 13, 0, ' ', '2022-01-14 09:55:25', ' ', '2022-02-23 00:44:04', '064b3b44b85a45fe87fcce88d72b2519', 2, 'dev', 1, 0, 3, 1, null, '/dev/dev-kdb-data-source/index', null);
INSERT INTO SYS_MENU (ID, "NAME", PARENT_ID, ICON, CODE, ROUTER_PATH, COMPONENT_PATH, IS_HIDDEN, MENU_TYPE, API_CODES, OPEN_MODE, KEEP_ALIVE, "PATH", ORDER_NUM, STATUS, WHO_CREATED, WHEN_CREATED, WHO_MODIFIED, WHEN_MODIFIED, APP_ID, DATA_TYPE, THEME, PAGE_TYPE, SIDEBAR_NAV_MODE, TOP_NAV_MODE, MAIN_MODE, PAGE_ID, FULL_PATH, IS_DEV) VALUES ('9bb44b43cf184446b04f6992403023f5', '拓扑图-编辑', '15c109ba424342948333deba7e0e3a76', null, null, 'topological-edit', '/dev/topological/design', 1, 'C', null, 0, 1, '/15c109ba424342948333deba7e0e3a76/9bb44b43cf184446b04f6992403023f5/', 13, 0, ' ', '2022-03-07 16:35:50', ' ', '2022-03-09 02:44:36', '064b3b44b85a45fe87fcce88d72b2519', 2, 'dev', 1, 0, 3, 1, null, '/dev/topological-edit', null);
INSERT INTO SYS_MENU (ID, "NAME", PARENT_ID, ICON, CODE, ROUTER_PATH, COMPONENT_PATH, IS_HIDDEN, MENU_TYPE, API_CODES, OPEN_MODE, KEEP_ALIVE, "PATH", ORDER_NUM, STATUS, WHO_CREATED, WHEN_CREATED, WHO_MODIFIED, WHEN_MODIFIED, APP_ID, DATA_TYPE, THEME, PAGE_TYPE, SIDEBAR_NAV_MODE, TOP_NAV_MODE, MAIN_MODE, PAGE_ID, FULL_PATH, IS_DEV) VALUES ('a17e9c809f1049668633d8fe6103e740', '系统配置管理', '843af15ab7694d54af793e4a5e6fb76e', 'swagger', 'config', 'config', '/sys/config/index', 0, 'C', null, 0, 1, '/843af15ab7694d54af793e4a5e6fb76e/a17e9c809f1049668633d8fe6103e740/', 7, 1, '056fb0eeb9a44cb0953534b4c0ca01fa', '2021-12-30 11:11:11', ' ', '2022-02-23 00:41:11', null, null, null, null, null, null, null, null, '/sys/conf/config', 0);
INSERT INTO SYS_MENU (ID, "NAME", PARENT_ID, ICON, CODE, ROUTER_PATH, COMPONENT_PATH, IS_HIDDEN, MENU_TYPE, API_CODES, OPEN_MODE, KEEP_ALIVE, "PATH", ORDER_NUM, STATUS, WHO_CREATED, WHEN_CREATED, WHO_MODIFIED, WHEN_MODIFIED, APP_ID, DATA_TYPE, THEME, PAGE_TYPE, SIDEBAR_NAV_MODE, TOP_NAV_MODE, MAIN_MODE, PAGE_ID, FULL_PATH, IS_DEV) VALUES ('b1bef2f8e2f442a3927696d1ce218b06', '页面设计器', '15c109ba424342948333deba7e0e3a76', null, 'pageDesigner', 'designer', '/sys/pageDesigner/index', 1, 'C', null, 0, 1, '/15c109ba424342948333deba7e0e3a76/b1bef2f8e2f442a3927696d1ce218b06/', 1, 0, ' ', '2022-02-14 06:08:23', '977255e5a1d0434c975bf34cce8083d5', '2022-07-13 23:57:19', '064b3b44b85a45fe87fcce88d72b2519', 2, 'dev', 1, 0, 3, 1, null, '/dev/page-designer', null);
INSERT INTO SYS_MENU (ID, "NAME", PARENT_ID, ICON, CODE, ROUTER_PATH, COMPONENT_PATH, IS_HIDDEN, MENU_TYPE, API_CODES, OPEN_MODE, KEEP_ALIVE, "PATH", ORDER_NUM, STATUS, WHO_CREATED, WHEN_CREATED, WHO_MODIFIED, WHEN_MODIFIED, APP_ID, DATA_TYPE, THEME, PAGE_TYPE, SIDEBAR_NAV_MODE, TOP_NAV_MODE, MAIN_MODE, PAGE_ID, FULL_PATH, IS_DEV) VALUES ('bd896854b0764aa1a506e2bfc829205a', '旧版逻辑编排', '15c109ba424342948333deba7e0e3a76', null, null, 'sys-kdb-flow-old', '/sys/kdbFlow/index', 1, 'C', null, 0, 1, '/15c109ba424342948333deba7e0e3a76/bd896854b0764aa1a506e2bfc829205a/', 99, 0, '056fb0eeb9a44cb0953534b4c0ca01fa', '2022-06-06 10:37:25', '056fb0eeb9a44cb0953534b4c0ca01fa', '2022-06-06 10:37:37', '064b3b44b85a45fe87fcce88d72b2519', null, null, null, 0, 0, 0, null, null, null);
INSERT INTO SYS_MENU (ID, "NAME", PARENT_ID, ICON, CODE, ROUTER_PATH, COMPONENT_PATH, IS_HIDDEN, MENU_TYPE, API_CODES, OPEN_MODE, KEEP_ALIVE, "PATH", ORDER_NUM, STATUS, WHO_CREATED, WHEN_CREATED, WHO_MODIFIED, WHEN_MODIFIED, APP_ID, DATA_TYPE, THEME, PAGE_TYPE, SIDEBAR_NAV_MODE, TOP_NAV_MODE, MAIN_MODE, PAGE_ID, FULL_PATH, IS_DEV) VALUES ('c137988391e440299865bd23884872a3', '应用安装', '843af15ab7694d54af793e4a5e6fb76e', 'k-icon k-icon-rocket', null, 'upgrade', null, 0, 'C', null, 0, 1, '/843af15ab7694d54af793e4a5e6fb76e/c137988391e440299865bd23884872a3/', 14, 1, '056fb0eeb9a44cb0953534b4c0ca01fa', '2022-08-30 16:51:26', '056fb0eeb9a44cb0953534b4c0ca01fa', '2022-08-30 16:53:01', null, 2, 'biz', 1, null, null, null, null, '/sys/conf/upgrade', 0);
INSERT INTO SYS_MENU (ID, "NAME", PARENT_ID, ICON, CODE, ROUTER_PATH, COMPONENT_PATH, IS_HIDDEN, MENU_TYPE, API_CODES, OPEN_MODE, KEEP_ALIVE, "PATH", ORDER_NUM, STATUS, WHO_CREATED, WHEN_CREATED, WHO_MODIFIED, WHEN_MODIFIED, APP_ID, DATA_TYPE, THEME, PAGE_TYPE, SIDEBAR_NAV_MODE, TOP_NAV_MODE, MAIN_MODE, PAGE_ID, FULL_PATH, IS_DEV) VALUES ('c2348bbf343a47b5852f12ee32869b13', '基础信息', null, 'documentation', 'system-info', 'sys/info', null, 0, 'M', null, 0, 1, '/c2348bbf343a47b5852f12ee32869b13/', 4, 1, '056fb0eeb9a44cb0953534b4c0ca01fa', '2021-12-30 11:11:11', '056fb0eeb9a44cb0953534b4c0ca01fa', '2022-09-01 09:39:26', null, null, null, null, null, null, null, null, '/sys/info', 0);
INSERT INTO SYS_MENU (ID, "NAME", PARENT_ID, ICON, CODE, ROUTER_PATH, COMPONENT_PATH, IS_HIDDEN, MENU_TYPE, API_CODES, OPEN_MODE, KEEP_ALIVE, "PATH", ORDER_NUM, STATUS, WHO_CREATED, WHEN_CREATED, WHO_MODIFIED, WHEN_MODIFIED, APP_ID, DATA_TYPE, THEME, PAGE_TYPE, SIDEBAR_NAV_MODE, TOP_NAV_MODE, MAIN_MODE, PAGE_ID, FULL_PATH, IS_DEV) VALUES ('cf48d240f030427ab3b986e11645879a', '流程中心', null, null, null, 'workflow', null, 1, 'M', null, 0, 1, '/cf48d240f030427ab3b986e11645879a/', 1, 0, '7aed8c297a6940f681c26eb6ab68893d', '2022-06-17 16:49:03', '7aed8c297a6940f681c26eb6ab68893d', '2022-06-18 09:59:26', '064b3b44b85a45fe87fcce88d72b2519', 2, 'biz', 1, null, null, null, null, '/workflow', null);
INSERT INTO SYS_MENU (ID, "NAME", PARENT_ID, ICON, CODE, ROUTER_PATH, COMPONENT_PATH, IS_HIDDEN, MENU_TYPE, API_CODES, OPEN_MODE, KEEP_ALIVE, "PATH", ORDER_NUM, STATUS, WHO_CREATED, WHEN_CREATED, WHO_MODIFIED, WHEN_MODIFIED, APP_ID, DATA_TYPE, THEME, PAGE_TYPE, SIDEBAR_NAV_MODE, TOP_NAV_MODE, MAIN_MODE, PAGE_ID, FULL_PATH, IS_DEV) VALUES ('d0c39e53a5b34747abd4c40922dfc929', '逻辑编排v2', '15c109ba424342948333deba7e0e3a76', null, null, 'logicflow/design', '/dev/logicflow/design', 1, 'C', null, 0, 1, '/15c109ba424342948333deba7e0e3a76/d0c39e53a5b34747abd4c40922dfc929/', 20, 0, '056fb0eeb9a44cb0953534b4c0ca01fa', '2022-05-09 10:39:01', '056fb0eeb9a44cb0953534b4c0ca01fa', '2022-05-09 10:39:01', '064b3b44b85a45fe87fcce88d72b2519', 1, 'dev', 2, 0, 0, 0, null, '/dev/logicflow/design', null);
INSERT INTO SYS_MENU (ID, "NAME", PARENT_ID, ICON, CODE, ROUTER_PATH, COMPONENT_PATH, IS_HIDDEN, MENU_TYPE, API_CODES, OPEN_MODE, KEEP_ALIVE, "PATH", ORDER_NUM, STATUS, WHO_CREATED, WHEN_CREATED, WHO_MODIFIED, WHEN_MODIFIED, APP_ID, DATA_TYPE, THEME, PAGE_TYPE, SIDEBAR_NAV_MODE, TOP_NAV_MODE, MAIN_MODE, PAGE_ID, FULL_PATH, IS_DEV) VALUES ('d512adf9690a4bc884c502995f0210b7', 'preview4', '15c109ba424342948333deba7e0e3a76', null, null, 'preview4', null, 1, 'C', null, 0, 1, '/15c109ba424342948333deba7e0e3a76/d512adf9690a4bc884c502995f0210b7/', 1, 0, '056fb0eeb9a44cb0953534b4c0ca01fa', '2022-05-25 17:32:13', '056fb0eeb9a44cb0953534b4c0ca01fa', '2022-05-25 17:33:10', '064b3b44b85a45fe87fcce88d72b2519', null, null, null, null, null, null, null, null, null);
INSERT INTO SYS_MENU (ID, "NAME", PARENT_ID, ICON, CODE, ROUTER_PATH, COMPONENT_PATH, IS_HIDDEN, MENU_TYPE, API_CODES, OPEN_MODE, KEEP_ALIVE, "PATH", ORDER_NUM, STATUS, WHO_CREATED, WHEN_CREATED, WHO_MODIFIED, WHEN_MODIFIED, APP_ID, DATA_TYPE, THEME, PAGE_TYPE, SIDEBAR_NAV_MODE, TOP_NAV_MODE, MAIN_MODE, PAGE_ID, FULL_PATH, IS_DEV) VALUES ('d65657e653a14758bed6b518f30bffc0', 'preview2', '15c109ba424342948333deba7e0e3a76', null, null, 'preview2', null, 1, 'C', null, 0, 1, '/15c109ba424342948333deba7e0e3a76/d65657e653a14758bed6b518f30bffc0/', 99, 0, '056fb0eeb9a44cb0953534b4c0ca01fa', '2022-05-25 17:31:49', '056fb0eeb9a44cb0953534b4c0ca01fa', '2022-05-25 17:33:20', '064b3b44b85a45fe87fcce88d72b2519', null, null, null, null, null, null, null, null, null);
INSERT INTO SYS_MENU (ID, "NAME", PARENT_ID, ICON, CODE, ROUTER_PATH, COMPONENT_PATH, IS_HIDDEN, MENU_TYPE, API_CODES, OPEN_MODE, KEEP_ALIVE, "PATH", ORDER_NUM, STATUS, WHO_CREATED, WHEN_CREATED, WHO_MODIFIED, WHEN_MODIFIED, APP_ID, DATA_TYPE, THEME, PAGE_TYPE, SIDEBAR_NAV_MODE, TOP_NAV_MODE, MAIN_MODE, PAGE_ID, FULL_PATH, IS_DEV) VALUES ('d72ba047ce4c4a088a53c0c0085e1a15', '逻辑流程编辑', '15c109ba424342948333deba7e0e3a76', null, 'logic-design', 'logic-design', '/sys/kdbFlow/design', 1, 'C', null, 0, 1, '/15c109ba424342948333deba7e0e3a76/d72ba047ce4c4a088a53c0c0085e1a15/', 13, 0, ' ', '2022-01-27 02:00:50', ' ', '2022-02-14 18:10:30', '064b3b44b85a45fe87fcce88d72b2519', 2, 'dev', 1, 0, 3, 0, null, '/dev/logic-design', null);
INSERT INTO SYS_MENU (ID, "NAME", PARENT_ID, ICON, CODE, ROUTER_PATH, COMPONENT_PATH, IS_HIDDEN, MENU_TYPE, API_CODES, OPEN_MODE, KEEP_ALIVE, "PATH", ORDER_NUM, STATUS, WHO_CREATED, WHEN_CREATED, WHO_MODIFIED, WHEN_MODIFIED, APP_ID, DATA_TYPE, THEME, PAGE_TYPE, SIDEBAR_NAV_MODE, TOP_NAV_MODE, MAIN_MODE, PAGE_ID, FULL_PATH, IS_DEV) VALUES ('ddf6db4486584655b3aa227e50aeb697', '插件管理', '15c109ba424342948333deba7e0e3a76', '<svg-icon icon-class="list" />', null, 'plugin', null, 0, 'C', null, 0, 1, '/15c109ba424342948333deba7e0e3a76%/ddf6db4486584655b3aa227e50aeb697/', 2, 0, '056fb0eeb9a44cb0953534b4c0ca01fa', '2022-03-29 11:32:58', '63454c98827e4a0384abf30e0e6eef54', '2022-04-25 15:10:16', '064b3b44b85a45fe87fcce88d72b2519', null, null, null, null, null, null, '83fee357f5db43faba47c90d963cc821', '/dev/plugin', null);
INSERT INTO SYS_MENU (ID, "NAME", PARENT_ID, ICON, CODE, ROUTER_PATH, COMPONENT_PATH, IS_HIDDEN, MENU_TYPE, API_CODES, OPEN_MODE, KEEP_ALIVE, "PATH", ORDER_NUM, STATUS, WHO_CREATED, WHEN_CREATED, WHO_MODIFIED, WHEN_MODIFIED, APP_ID, DATA_TYPE, THEME, PAGE_TYPE, SIDEBAR_NAV_MODE, TOP_NAV_MODE, MAIN_MODE, PAGE_ID, FULL_PATH, IS_DEV) VALUES ('e238560484c84c168cf91a64be27c876', '应用主页', '15c109ba424342948333deba7e0e3a76', 'el-icon-menu', null, 'app', null, 1, 'C', null, 0, 1, '/15c109ba424342948333deba7e0e3a76/e238560484c84c168cf91a64be27c876/', 2, 0, ' ', '2022-03-04 17:17:03', ' ', '2022-03-10 08:36:20', '064b3b44b85a45fe87fcce88d72b2519', 2, 'dev', 1, 0, 1, 1, null, '/dev/app', null);
INSERT INTO SYS_MENU (ID, "NAME", PARENT_ID, ICON, CODE, ROUTER_PATH, COMPONENT_PATH, IS_HIDDEN, MENU_TYPE, API_CODES, OPEN_MODE, KEEP_ALIVE, "PATH", ORDER_NUM, STATUS, WHO_CREATED, WHEN_CREATED, WHO_MODIFIED, WHEN_MODIFIED, APP_ID, DATA_TYPE, THEME, PAGE_TYPE, SIDEBAR_NAV_MODE, TOP_NAV_MODE, MAIN_MODE, PAGE_ID, FULL_PATH, IS_DEV) VALUES ('ee00aa994e6842bf933bfd964b9844b0', '页面管理', '15c109ba424342948333deba7e0e3a76', 'el-icon-menu', 'page-mgt', 'page-mgt', null, 0, 'C', null, 0, 1, '/15c109ba424342948333deba7e0e3a76/ee00aa994e6842bf933bfd964b9844b0/', 5, 0, ' ', '2022-02-15 11:23:32', ' ', '2022-03-10 11:43:52', '064b3b44b85a45fe87fcce88d72b2519', 2, 'dev', 1, 0, 3, 1, null, '/dev/page-mgt', null);
INSERT INTO SYS_MENU (ID, "NAME", PARENT_ID, ICON, CODE, ROUTER_PATH, COMPONENT_PATH, IS_HIDDEN, MENU_TYPE, API_CODES, OPEN_MODE, KEEP_ALIVE, "PATH", ORDER_NUM, STATUS, WHO_CREATED, WHEN_CREATED, WHO_MODIFIED, WHEN_MODIFIED, APP_ID, DATA_TYPE, THEME, PAGE_TYPE, SIDEBAR_NAV_MODE, TOP_NAV_MODE, MAIN_MODE, PAGE_ID, FULL_PATH, IS_DEV) VALUES ('f1c93a367d7541c6ad895b9c168ea945', 'preview6', '15c109ba424342948333deba7e0e3a76', null, null, 'preview6', null, 1, 'C', null, 0, 1, '/15c109ba424342948333deba7e0e3a76/f1c93a367d7541c6ad895b9c168ea945/', 99, 0, '056fb0eeb9a44cb0953534b4c0ca01fa', '2022-05-25 17:53:16', '056fb0eeb9a44cb0953534b4c0ca01fa', '2022-05-25 17:53:28', '064b3b44b85a45fe87fcce88d72b2519', null, null, null, null, null, null, null, null, null);
INSERT INTO SYS_MENU (ID, "NAME", PARENT_ID, ICON, CODE, ROUTER_PATH, COMPONENT_PATH, IS_HIDDEN, MENU_TYPE, API_CODES, OPEN_MODE, KEEP_ALIVE, "PATH", ORDER_NUM, STATUS, WHO_CREATED, WHEN_CREATED, WHO_MODIFIED, WHEN_MODIFIED, APP_ID, DATA_TYPE, THEME, PAGE_TYPE, SIDEBAR_NAV_MODE, TOP_NAV_MODE, MAIN_MODE, PAGE_ID, FULL_PATH, IS_DEV) VALUES ('f8bb5559a95d4b4e81b4aa7901fc4914', '应用配置', '15c109ba424342948333deba7e0e3a76', null, null, 'config', null, 0, 'M', null, 0, 1, '/15c109ba424342948333deba7e0e3a76/f8bb5559a95d4b4e81b4aa7901fc4914/', 14, 0, '056fb0eeb9a44cb0953534b4c0ca01fa', '2022-04-01 08:10:43', '056fb0eeb9a44cb0953534b4c0ca01fa', '2022-04-06 02:03:38', '064b3b44b85a45fe87fcce88d72b2519', 2, 'dev', 1, 3, 3, 0, null, '/dev/config', null);
INSERT INTO SYS_MENU (ID, "NAME", PARENT_ID, ICON, CODE, ROUTER_PATH, COMPONENT_PATH, IS_HIDDEN, MENU_TYPE, API_CODES, OPEN_MODE, KEEP_ALIVE, "PATH", ORDER_NUM, STATUS, WHO_CREATED, WHEN_CREATED, WHO_MODIFIED, WHEN_MODIFIED, APP_ID, DATA_TYPE, THEME, PAGE_TYPE, SIDEBAR_NAV_MODE, TOP_NAV_MODE, MAIN_MODE, PAGE_ID, FULL_PATH, IS_DEV) VALUES ('fe2c56bb98cd4fc796c32b46082b336b', '团队管理', '15c109ba424342948333deba7e0e3a76', null, null, 'team', null, 0, 'C', null, 0, 1, '/15c109ba424342948333deba7e0e3a76/fe2c56bb98cd4fc796c32b46082b336b/', 20, 0, '0b77a45e06984e5097a11a0896818d55', '2022-04-12 13:57:39', '0b77a45e06984e5097a11a0896818d55', '2022-04-12 13:57:39', '064b3b44b85a45fe87fcce88d72b2519', 2, 'dev', 1, 0, 3, 1, null, '/dev/team', null);
INSERT INTO SYS_MENU (ID, "NAME", PARENT_ID, ICON, CODE, ROUTER_PATH, COMPONENT_PATH, IS_HIDDEN, MENU_TYPE, API_CODES, OPEN_MODE, KEEP_ALIVE, "PATH", ORDER_NUM, STATUS, WHO_CREATED, WHEN_CREATED, WHO_MODIFIED, WHEN_MODIFIED, APP_ID, DATA_TYPE, THEME, PAGE_TYPE, SIDEBAR_NAV_MODE, TOP_NAV_MODE, MAIN_MODE, PAGE_ID, FULL_PATH, IS_DEV) VALUES ('ff50d725626644c78d4085fede6aa74f', 'preview3', '15c109ba424342948333deba7e0e3a76', null, null, 'preview3', null, 1, 'C', null, 0, 1, '/15c109ba424342948333deba7e0e3a76/ff50d725626644c78d4085fede6aa74f/', 1, 0, '056fb0eeb9a44cb0953534b4c0ca01fa', '2022-05-25 17:32:00', '056fb0eeb9a44cb0953534b4c0ca01fa', '2022-05-25 17:33:15', '064b3b44b85a45fe87fcce88d72b2519', null, null, null, null, null, null, null, null, null);
INSERT INTO SYS_MENU (ID, "NAME", PARENT_ID, ICON, CODE, ROUTER_PATH, COMPONENT_PATH, IS_HIDDEN, MENU_TYPE, API_CODES, OPEN_MODE, KEEP_ALIVE, "PATH", ORDER_NUM, STATUS, WHO_CREATED, WHEN_CREATED, WHO_MODIFIED, WHEN_MODIFIED, APP_ID, DATA_TYPE, THEME, PAGE_TYPE, SIDEBAR_NAV_MODE, TOP_NAV_MODE, MAIN_MODE, PAGE_ID, FULL_PATH, IS_DEV) VALUES ('01708827edc84880a13c0e294fc7507f', 'test', '3d5bb86ad7d64d918c73071dfedc4d63', null, null, 'tes', null, 0, 'C', null, 0, 1, '/3d5bb86ad7d64d918c73071dfedc4d63/01708827edc84880a13c0e294fc7507f/', 4, 0, '7aed8c297a6940f681c26eb6ab68893d', '2022-04-01 07:48:04', '7aed8c297a6940f681c26eb6ab68893d', '2022-04-01 07:48:04', '064b3b44b85a45fe87fcce88d72b2519', 2, 'biz', 1, null, null, null, null, '/biz/bank/tes', null);
INSERT INTO SYS_MENU (ID, "NAME", PARENT_ID, ICON, CODE, ROUTER_PATH, COMPONENT_PATH, IS_HIDDEN, MENU_TYPE, API_CODES, OPEN_MODE, KEEP_ALIVE, "PATH", ORDER_NUM, STATUS, WHO_CREATED, WHEN_CREATED, WHO_MODIFIED, WHEN_MODIFIED, APP_ID, DATA_TYPE, THEME, PAGE_TYPE, SIDEBAR_NAV_MODE, TOP_NAV_MODE, MAIN_MODE, PAGE_ID, FULL_PATH, IS_DEV) VALUES ('05de132989db4bb284ae3ff1d56e28d4', '团队管理', '0a3d0a8130b6494cba7b9f27487b8136', null, null, 'team', null, 0, 'C', null, 0, 1, '/0a3d0a8130b6494cba7b9f27487b8136/05de132989db4bb284ae3ff1d56e28d4/', 0, 0, '056fb0eeb9a44cb0953534b4c0ca01fa', '2022-06-13 01:57:08', '056fb0eeb9a44cb0953534b4c0ca01fa', '2022-06-13 01:57:08', '064b3b44b85a45fe87fcce88d72b2519', 2, 'biz', 1, null, null, null, 'e9ec16868f1c4716b4cbff3439bf69d9', '/dev-mgt/team', null);
INSERT INTO SYS_MENU (ID, "NAME", PARENT_ID, ICON, CODE, ROUTER_PATH, COMPONENT_PATH, IS_HIDDEN, MENU_TYPE, API_CODES, OPEN_MODE, KEEP_ALIVE, "PATH", ORDER_NUM, STATUS, WHO_CREATED, WHEN_CREATED, WHO_MODIFIED, WHEN_MODIFIED, APP_ID, DATA_TYPE, THEME, PAGE_TYPE, SIDEBAR_NAV_MODE, TOP_NAV_MODE, MAIN_MODE, PAGE_ID, FULL_PATH, IS_DEV) VALUES ('0a3d0a8130b6494cba7b9f27487b8136', '开发者平台管理', null, 'k-icon k-icon-code', null, 'dev-mgt', null, 0, 'M', null, 0, 1, '/0a3d0a8130b6494cba7b9f27487b8136/', 13, 0, '056fb0eeb9a44cb0953534b4c0ca01fa', '2022-06-13 01:56:27', '7aed8c297a6940f681c26eb6ab68893d', '2022-08-23 00:23:08', '064b3b44b85a45fe87fcce88d72b2519', 2, 'biz', 1, null, null, null, null, '/dev-mgt', null);
INSERT INTO SYS_MENU (ID, "NAME", PARENT_ID, ICON, CODE, ROUTER_PATH, COMPONENT_PATH, IS_HIDDEN, MENU_TYPE, API_CODES, OPEN_MODE, KEEP_ALIVE, "PATH", ORDER_NUM, STATUS, WHO_CREATED, WHEN_CREATED, WHO_MODIFIED, WHEN_MODIFIED, APP_ID, DATA_TYPE, THEME, PAGE_TYPE, SIDEBAR_NAV_MODE, TOP_NAV_MODE, MAIN_MODE, PAGE_ID, FULL_PATH, IS_DEV) VALUES ('102fd651dbe640069362865778e4f80d', '页面配置', '15c109ba424342948333deba7e0e3a76', 'el-icon-menu', 'page', 'page/index', '/dev/page/index', 0, 'C', null, 0, 1, '/15c109ba424342948333deba7e0e3a76/102fd651dbe640069362865778e4f80d/', 4, 0, ' ', '2022-03-10 11:49:22', ' ', '2022-03-10 11:52:43', '064b3b44b85a45fe87fcce88d72b2519', 2, 'dev', 1, 0, 3, 0, null, '/dev/page/index', null);
INSERT INTO SYS_MENU (ID, "NAME", PARENT_ID, ICON, CODE, ROUTER_PATH, COMPONENT_PATH, IS_HIDDEN, MENU_TYPE, API_CODES, OPEN_MODE, KEEP_ALIVE, "PATH", ORDER_NUM, STATUS, WHO_CREATED, WHEN_CREATED, WHO_MODIFIED, WHEN_MODIFIED, APP_ID, DATA_TYPE, THEME, PAGE_TYPE, SIDEBAR_NAV_MODE, TOP_NAV_MODE, MAIN_MODE, PAGE_ID, FULL_PATH, IS_DEV) VALUES ('12463e4270ea43a3bd773879cceb0f4d', '应用管理', '15c109ba424342948333deba7e0e3a76', 'el-icon-menu', 'appMgt', 'appMgt', null, 0, 'C', null, 0, 1, '/15c109ba424342948333deba7e0e3a76/12463e4270ea43a3bd773879cceb0f4d/', 0, 0, ' ', '2022-03-10 08:36:51', ' ', '2022-03-10 08:37:06', '064b3b44b85a45fe87fcce88d72b2519', null, null, null, 0, 3, 1, null, '/dev/appMgt', null);
INSERT INTO SYS_MENU (ID, "NAME", PARENT_ID, ICON, CODE, ROUTER_PATH, COMPONENT_PATH, IS_HIDDEN, MENU_TYPE, API_CODES, OPEN_MODE, KEEP_ALIVE, "PATH", ORDER_NUM, STATUS, WHO_CREATED, WHEN_CREATED, WHO_MODIFIED, WHEN_MODIFIED, APP_ID, DATA_TYPE, THEME, PAGE_TYPE, SIDEBAR_NAV_MODE, TOP_NAV_MODE, MAIN_MODE, PAGE_ID, FULL_PATH, IS_DEV) VALUES ('15c109ba424342948333deba7e0e3a76', '开发者平台', null, 'dev', 'dev', 'dev', null, 0, 'M', null, 0, 1, '/15c109ba424342948333deba7e0e3a76/', 8, 0, ' ', '2022-02-14 06:07:27', '7aed8c297a6940f681c26eb6ab68893d', '2022-08-19 17:24:07', '064b3b44b85a45fe87fcce88d72b2519', null, null, null, null, null, null, null, '/dev', null);
INSERT INTO SYS_MENU (ID, "NAME", PARENT_ID, ICON, CODE, ROUTER_PATH, COMPONENT_PATH, IS_HIDDEN, MENU_TYPE, API_CODES, OPEN_MODE, KEEP_ALIVE, "PATH", ORDER_NUM, STATUS, WHO_CREATED, WHEN_CREATED, WHO_MODIFIED, WHEN_MODIFIED, APP_ID, DATA_TYPE, THEME, PAGE_TYPE, SIDEBAR_NAV_MODE, TOP_NAV_MODE, MAIN_MODE, PAGE_ID, FULL_PATH, IS_DEV) VALUES ('1a246429b14e4db2be0e1847a3939e98', '菜单管理', '843af15ab7694d54af793e4a5e6fb76e', 'nested', 'menu', 'menu', '/sys/menu/index', 0, 'C', null, 0, 1, '/843af15ab7694d54af793e4a5e6fb76e/1a246429b14e4db2be0e1847a3939e98/', 5, 1, '056fb0eeb9a44cb0953534b4c0ca01fa', '2021-12-30 11:11:11', ' ', '2022-02-23 00:29:21', null, null, null, null, null, null, null, null, '/sys/conf/menu', 0);
INSERT INTO SYS_MENU (ID, "NAME", PARENT_ID, ICON, CODE, ROUTER_PATH, COMPONENT_PATH, IS_HIDDEN, MENU_TYPE, API_CODES, OPEN_MODE, KEEP_ALIVE, "PATH", ORDER_NUM, STATUS, WHO_CREATED, WHEN_CREATED, WHO_MODIFIED, WHEN_MODIFIED, APP_ID, DATA_TYPE, THEME, PAGE_TYPE, SIDEBAR_NAV_MODE, TOP_NAV_MODE, MAIN_MODE, PAGE_ID, FULL_PATH, IS_DEV) VALUES ('1e66802db992442680c27b8e9107156b', 'preview', '15c109ba424342948333deba7e0e3a76', null, null, 'preview', null, 1, 'C', null, 0, 1, '/15c109ba424342948333deba7e0e3a76/1e66802db992442680c27b8e9107156b/', 0, 0, '7aed8c297a6940f681c26eb6ab68893d', '2022-04-29 14:21:15', '056fb0eeb9a44cb0953534b4c0ca01fa', '2022-05-21 16:04:50', '064b3b44b85a45fe87fcce88d72b2519', 2, 'dev', 1, 0, 3, 0, null, '/dev/preview', null);
INSERT INTO SYS_MENU (ID, "NAME", PARENT_ID, ICON, CODE, ROUTER_PATH, COMPONENT_PATH, IS_HIDDEN, MENU_TYPE, API_CODES, OPEN_MODE, KEEP_ALIVE, "PATH", ORDER_NUM, STATUS, WHO_CREATED, WHEN_CREATED, WHO_MODIFIED, WHEN_MODIFIED, APP_ID, DATA_TYPE, THEME, PAGE_TYPE, SIDEBAR_NAV_MODE, TOP_NAV_MODE, MAIN_MODE, PAGE_ID, FULL_PATH, IS_DEV) VALUES ('25639b53d2cb434086677657bf37a974', '逻辑编排管理', '15c109ba424342948333deba7e0e3a76', 'dev', 'sys-kdb-flow', 'sys-kdb-flow', null, 0, 'C', null, 0, 1, '/15c109ba424342948333deba7e0e3a76/25639b53d2cb434086677657bf37a974/', 6, 0, ' ', '2022-01-14 09:52:41', '056fb0eeb9a44cb0953534b4c0ca01fa', '2022-06-06 10:38:27', '064b3b44b85a45fe87fcce88d72b2519', 2, 'dev', 1, 0, 3, 0, null, '/dev/sys-kdb-flow', null);
INSERT INTO SYS_MENU (ID, "NAME", PARENT_ID, ICON, CODE, ROUTER_PATH, COMPONENT_PATH, IS_HIDDEN, MENU_TYPE, API_CODES, OPEN_MODE, KEEP_ALIVE, "PATH", ORDER_NUM, STATUS, WHO_CREATED, WHEN_CREATED, WHO_MODIFIED, WHEN_MODIFIED, APP_ID, DATA_TYPE, THEME, PAGE_TYPE, SIDEBAR_NAV_MODE, TOP_NAV_MODE, MAIN_MODE, PAGE_ID, FULL_PATH, IS_DEV) VALUES ('372c4bd4da39495e8fabe7aa25993ca8', '能力树', '15c109ba424342948333deba7e0e3a76', 'tree-table', null, 'dev-power/index', null, 0, 'C', null, 0, 1, '/15c109ba424342948333deba7e0e3a76/372c4bd4da39495e8fabe7aa25993ca8/', 1, 0, '056fb0eeb9a44cb0953534b4c0ca01fa', '2022-06-01 15:32:41', '056fb0eeb9a44cb0953534b4c0ca01fa', '2022-06-01 16:04:43', '064b3b44b85a45fe87fcce88d72b2519', null, null, null, null, null, null, null, null, null);
INSERT INTO SYS_MENU (ID, "NAME", PARENT_ID, ICON, CODE, ROUTER_PATH, COMPONENT_PATH, IS_HIDDEN, MENU_TYPE, API_CODES, OPEN_MODE, KEEP_ALIVE, "PATH", ORDER_NUM, STATUS, WHO_CREATED, WHEN_CREATED, WHO_MODIFIED, WHEN_MODIFIED, APP_ID, DATA_TYPE, THEME, PAGE_TYPE, SIDEBAR_NAV_MODE, TOP_NAV_MODE, MAIN_MODE, PAGE_ID, FULL_PATH, IS_DEV) VALUES ('3a5dc5dd217a4dc8811a007496e56716', '接口管理', '15c109ba424342948333deba7e0e3a76', 'swagger', 'api-codes', 'dev-api/index', '/sys/api/index', 0, 'C', null, 0, 1, '/15c109ba424342948333deba7e0e3a76/3a5dc5dd217a4dc8811a007496e56716/', 7, 0, ' ', '2022-01-18 18:07:07', ' ', '2022-02-23 00:45:29', '064b3b44b85a45fe87fcce88d72b2519', 2, 'dev', 1, 0, 3, 0, null, '/dev/dev-api/index', null);
INSERT INTO SYS_MENU (ID, "NAME", PARENT_ID, ICON, CODE, ROUTER_PATH, COMPONENT_PATH, IS_HIDDEN, MENU_TYPE, API_CODES, OPEN_MODE, KEEP_ALIVE, "PATH", ORDER_NUM, STATUS, WHO_CREATED, WHEN_CREATED, WHO_MODIFIED, WHEN_MODIFIED, APP_ID, DATA_TYPE, THEME, PAGE_TYPE, SIDEBAR_NAV_MODE, TOP_NAV_MODE, MAIN_MODE, PAGE_ID, FULL_PATH, IS_DEV) VALUES ('49d3319c02e542db9db32a6491193348', '字典管理', '843af15ab7694d54af793e4a5e6fb76e', 'dict', 'dictionary', 'dict/index', '/sys/dict/index', 0, 'C', null, 0, 1, '/843af15ab7694d54af793e4a5e6fb76e/49d3319c02e542db9db32a6491193348/', 0, 1, '056fb0eeb9a44cb0953534b4c0ca01fa', '2021-12-30 11:11:11', ' ', '2022-02-23 00:38:52', null, null, null, null, null, null, null, null, '/sys/conf/dict/index', 0);
INSERT INTO SYS_MENU (ID, "NAME", PARENT_ID, ICON, CODE, ROUTER_PATH, COMPONENT_PATH, IS_HIDDEN, MENU_TYPE, API_CODES, OPEN_MODE, KEEP_ALIVE, "PATH", ORDER_NUM, STATUS, WHO_CREATED, WHEN_CREATED, WHO_MODIFIED, WHEN_MODIFIED, APP_ID, DATA_TYPE, THEME, PAGE_TYPE, SIDEBAR_NAV_MODE, TOP_NAV_MODE, MAIN_MODE, PAGE_ID, FULL_PATH, IS_DEV) VALUES ('49d3319c02e542db9db32a6491193349', '字典数据', '843af15ab7694d54af793e4a5e6fb76e', null, 'dictionary-item', 'dict-item/index', '/sys/dictItem/index', 1, 'C', null, 0, 1, '/843af15ab7694d54af793e4a5e6fb76e/49d3319c02e542db9db32a6491193349/', 1, 1, '056fb0eeb9a44cb0953534b4c0ca01fa', '2021-12-30 11:11:11', ' ', '2021-12-30 15:32:53', null, null, null, null, null, null, null, null, '/sys/conf/dict-item/index', 0);
INSERT INTO SYS_MENU (ID, "NAME", PARENT_ID, ICON, CODE, ROUTER_PATH, COMPONENT_PATH, IS_HIDDEN, MENU_TYPE, API_CODES, OPEN_MODE, KEEP_ALIVE, "PATH", ORDER_NUM, STATUS, WHO_CREATED, WHEN_CREATED, WHO_MODIFIED, WHEN_MODIFIED, APP_ID, DATA_TYPE, THEME, PAGE_TYPE, SIDEBAR_NAV_MODE, TOP_NAV_MODE, MAIN_MODE, PAGE_ID, FULL_PATH, IS_DEV) VALUES ('4b925312449c4fb2a0ee9acfa088ca27', '团队成员管理', '0a3d0a8130b6494cba7b9f27487b8136', null, null, 'team-member', null, 0, 'C', null, 0, 1, '/0a3d0a8130b6494cba7b9f27487b8136/4b925312449c4fb2a0ee9acfa088ca27/', 1, 0, '7aed8c297a6940f681c26eb6ab68893d', '2022-06-24 17:44:12', '7aed8c297a6940f681c26eb6ab68893d', '2022-06-24 17:44:12', '064b3b44b85a45fe87fcce88d72b2519', 2, 'biz', 1, null, null, null, 'd3a74c7e1a074c1bbfb857cf5a3d9821', '/dev-mgt/team-member', null);
INSERT INTO SYS_MENU (ID, "NAME", PARENT_ID, ICON, CODE, ROUTER_PATH, COMPONENT_PATH, IS_HIDDEN, MENU_TYPE, API_CODES, OPEN_MODE, KEEP_ALIVE, "PATH", ORDER_NUM, STATUS, WHO_CREATED, WHEN_CREATED, WHO_MODIFIED, WHEN_MODIFIED, APP_ID, DATA_TYPE, THEME, PAGE_TYPE, SIDEBAR_NAV_MODE, TOP_NAV_MODE, MAIN_MODE, PAGE_ID, FULL_PATH, IS_DEV) VALUES ('58f5f98c57c74a91b6c2ca24c5df0ba9', '用户管理', 'c2348bbf343a47b5852f12ee32869b13', 'user', 'user', 'user/index', '/sys/user/index', 0, 'C', 'sys:user:list', 0, 1, '/c2348bbf343a47b5852f12ee32869b13/58f5f98c57c74a91b6c2ca24c5df0ba9/', 0, 1, '056fb0eeb9a44cb0953534b4c0ca01fa', '2021-12-30 11:11:11', ' ', '2022-02-23 00:28:46', null, null, null, null, null, null, null, null, '/sys/info/user/index', 0);
INSERT INTO SYS_MENU (ID, "NAME", PARENT_ID, ICON, CODE, ROUTER_PATH, COMPONENT_PATH, IS_HIDDEN, MENU_TYPE, API_CODES, OPEN_MODE, KEEP_ALIVE, "PATH", ORDER_NUM, STATUS, WHO_CREATED, WHEN_CREATED, WHO_MODIFIED, WHEN_MODIFIED, APP_ID, DATA_TYPE, THEME, PAGE_TYPE, SIDEBAR_NAV_MODE, TOP_NAV_MODE, MAIN_MODE, PAGE_ID, FULL_PATH, IS_DEV) VALUES ('5a46b9de1e6347089a2b96228fc9c2bc', '数据模型', '15c109ba424342948333deba7e0e3a76', 'build', 'view-models', 'dev-view-model/index', '/sys/viewModel/index', 0, 'C', null, 0, 1, '/15c109ba424342948333deba7e0e3a76/5a46b9de1e6347089a2b96228fc9c2bc/', 11, 0, ' ', '2022-01-29 16:27:17', ' ', '2022-02-23 00:45:39', '064b3b44b85a45fe87fcce88d72b2519', null, null, null, 0, 3, 1, null, '/dev/dev-view-model/index', null);
INSERT INTO SYS_MENU (ID, "NAME", PARENT_ID, ICON, CODE, ROUTER_PATH, COMPONENT_PATH, IS_HIDDEN, MENU_TYPE, API_CODES, OPEN_MODE, KEEP_ALIVE, "PATH", ORDER_NUM, STATUS, WHO_CREATED, WHEN_CREATED, WHO_MODIFIED, WHEN_MODIFIED, APP_ID, DATA_TYPE, THEME, PAGE_TYPE, SIDEBAR_NAV_MODE, TOP_NAV_MODE, MAIN_MODE, PAGE_ID, FULL_PATH, IS_DEV) VALUES ('5ab9613b08f541e9a0c68e3bef0ea007', '拓扑图', '15c109ba424342948333deba7e0e3a76', null, null, 'topological', null, 0, 'C', null, 0, 1, '/15c109ba424342948333deba7e0e3a76/5ab9613b08f541e9a0c68e3bef0ea007/', 8, 0, ' ', '2022-03-07 16:32:06', '056fb0eeb9a44cb0953534b4c0ca01fa', '2022-05-27 14:05:37', '064b3b44b85a45fe87fcce88d72b2519', 2, 'dev', 1, 0, 3, 1, null, '/dev/topological', null);
INSERT INTO SYS_ROLE (ID, "NAME", CODE, NOTE, STATUS, WHO_CREATED, WHEN_CREATED, WHO_MODIFIED, WHEN_MODIFIED, APP_ID) VALUES ('10d26189026a4dba86a8e63a4c717ed6', '超级管理员', 'admin', '应用超级管理员2', 1, ' ', '2021-12-28 15:22:56', '056fb0eeb9a44cb0953534b4c0ca01fa', '2022-09-01 09:41:49', null);
INSERT INTO SYS_ROLE_MENU (ID, SYS_MENU_ID, SYS_ROLE_ID, WHO_CREATED, WHEN_CREATED, APP_ID) VALUES ('3092ea24bb9f45fc9a01303240c1d00a', '49d3319c02e542db9db32a6491193349', '10d26189026a4dba86a8e63a4c717ed6', '056fb0eeb9a44cb0953534b4c0ca01fa', '2022-09-01 09:41:45', null);
INSERT INTO SYS_ROLE_MENU (ID, SYS_MENU_ID, SYS_ROLE_ID, WHO_CREATED, WHEN_CREATED, APP_ID) VALUES ('3599c9e82fde4ebaa465853bc42fe952', '58f5f98c57c74a91b6c2ca24c5df0ba9', '10d26189026a4dba86a8e63a4c717ed6', '056fb0eeb9a44cb0953534b4c0ca01fa', '2022-09-01 09:41:45', null);
INSERT INTO SYS_ROLE_MENU (ID, SYS_MENU_ID, SYS_ROLE_ID, WHO_CREATED, WHEN_CREATED, APP_ID) VALUES ('6afd6e3d91ec4d7e8f87b9189c6d052c', '611b9195b7ce4b3fb37f41023a907bda', '10d26189026a4dba86a8e63a4c717ed6', '056fb0eeb9a44cb0953534b4c0ca01fa', '2022-09-01 09:41:45', null);
INSERT INTO SYS_ROLE_MENU (ID, SYS_MENU_ID, SYS_ROLE_ID, WHO_CREATED, WHEN_CREATED, APP_ID) VALUES ('b153b92e43384131bb1806394116fcaa', '1a246429b14e4db2be0e1847a3939e98', '10d26189026a4dba86a8e63a4c717ed6', '056fb0eeb9a44cb0953534b4c0ca01fa', '2022-09-01 09:41:45', null);
INSERT INTO SYS_ROLE_MENU (ID, SYS_MENU_ID, SYS_ROLE_ID, WHO_CREATED, WHEN_CREATED, APP_ID) VALUES ('be12e066eb1144cca7988e5794097d68', '843af15ab7694d54af793e4a5e6fb76e', '10d26189026a4dba86a8e63a4c717ed6', '056fb0eeb9a44cb0953534b4c0ca01fa', '2022-09-01 09:41:45', null);
INSERT INTO SYS_ROLE_MENU (ID, SYS_MENU_ID, SYS_ROLE_ID, WHO_CREATED, WHEN_CREATED, APP_ID) VALUES ('c62b6f3f887c4209a1fc7a84d649b375', 'a17e9c809f1049668633d8fe6103e740', '10d26189026a4dba86a8e63a4c717ed6', '056fb0eeb9a44cb0953534b4c0ca01fa', '2022-09-01 09:41:45', null);
INSERT INTO SYS_ROLE_MENU (ID, SYS_MENU_ID, SYS_ROLE_ID, WHO_CREATED, WHEN_CREATED, APP_ID) VALUES ('c9f64efbb5d348b3a568c2fe058d22e0', '49d3319c02e542db9db32a6491193348', '10d26189026a4dba86a8e63a4c717ed6', '056fb0eeb9a44cb0953534b4c0ca01fa', '2022-09-01 09:41:45', null);
INSERT INTO SYS_ROLE_MENU (ID, SYS_MENU_ID, SYS_ROLE_ID, WHO_CREATED, WHEN_CREATED, APP_ID) VALUES ('dea1c2ac721b491c9aa94b7069cce3a0', 'c137988391e440299865bd23884872a3', '10d26189026a4dba86a8e63a4c717ed6', '056fb0eeb9a44cb0953534b4c0ca01fa', '2022-09-01 09:41:45', null);
INSERT INTO SYS_ROLE_MENU (ID, SYS_MENU_ID, SYS_ROLE_ID, WHO_CREATED, WHEN_CREATED, APP_ID) VALUES ('eea8f8b75f6c47ee98f70170c185ebb5', 'c2348bbf343a47b5852f12ee32869b13', '10d26189026a4dba86a8e63a4c717ed6', '056fb0eeb9a44cb0953534b4c0ca01fa', '2022-09-01 09:41:45', null);
INSERT INTO SYS_USER (ID, USERNAME, PASSWORD, REAL_NAME, MOBILE, EMAIL, SEX, SYS_UNIT_ID, POST, STATUS, NOTE, WHO_CREATED, WHEN_CREATED, WHO_MODIFIED, WHEN_MODIFIED, DELETED, AVATAR, APP_ID) VALUES ('056fb0eeb9a44cb0953534b4c0ca01fa', 'admin', 'MTIzNDU2', '超级管理员', null, null, 1, null, null, 1, null, null, '2021-12-29 16:36:46', '056fb0eeb9a44cb0953534b4c0ca01fa', '2022-04-01 08:58:32', 0, 'https://wpimg.wallstcn.com/f778738c-e4f8-4870-b634-56703b4acafe.gif', null);
INSERT INTO SYS_USER_ROLE (ID, SYS_USER_ID, SYS_ROLE_ID, WHO_CREATED, WHEN_CREATED, APP_ID) VALUES ('b5f79a7cc794423e843a2b1fd9a27007', '056fb0eeb9a44cb0953534b4c0ca01fa', '10d26189026a4dba86a8e63a4c717ed6', ' ', '2022-03-10 06:31:39', null);

INSERT INTO SYS_USER (ID, APP_ID, AVATAR, DELETED, EMAIL, MOBILE, NOTE, PASSWORD, POST, REAL_NAME, SEX, STATUS, WHEN_CREATED, WHEN_MODIFIED, WHO_CREATED, WHO_MODIFIED, SYS_UNIT_ID, USERNAME) VALUES('8116f0bc8222413fb72de98a32960b1a', NULL, NULL, 0, NULL, NULL, NULL, 'MTIzNDU2', NULL, '开发人员', 1, 1, '2022-09-28 14:45:53', '2022-09-28 14:45:53', '056fb0eeb9a44cb0953534b4c0ca01fa', '056fb0eeb9a44cb0953534b4c0ca01fa', NULL, 'dev');

INSERT INTO SYS_ROLE
(ID, "NAME", CODE, NOTE, STATUS, WHO_CREATED, WHEN_CREATED, WHO_MODIFIED, WHEN_MODIFIED, APP_ID)
VALUES('3fc43c9c69f44144bd032d9451ba328b', '团队成员', 'team_member', '青松开发者平台-团队成员', 1, '', '2022-03-10 06:13:01', '056fb0eeb9a44cb0953534b4c0ca01fa', '2022-03-29 11:43:59', NULL);
INSERT INTO SYS_ROLE
(ID, "NAME", CODE, NOTE, STATUS, WHO_CREATED, WHEN_CREATED, WHO_MODIFIED, WHEN_MODIFIED, APP_ID)
VALUES('4a30f4d346074b4ba8363944f004c1d9', '团队负责人', 'team_owner', '青松开发者平台-团队负责人', 1, '', '2022-03-10 06:12:31', '056fb0eeb9a44cb0953534b4c0ca01fa', '2022-03-29 11:44:06', NULL);
INSERT INTO SYS_USER_ROLE (ID, APP_ID, WHEN_CREATED, WHO_CREATED, SYS_ROLE_ID, SYS_USER_ID) VALUES('8d641b3aded845feae88aef3d7e32e33', NULL, '2022-09-28 14:45:53', '056fb0eeb9a44cb0953534b4c0ca01fa', '4a30f4d346074b4ba8363944f004c1d9', '8116f0bc8222413fb72de98a32960b1a');


-- 以下是来自 version_4_1.sql 的内容 --

create table sys_user_unit (
       id varchar(36) not null ,
       sys_user_id varchar(36) not null ,
       sys_unit_id varchar(36) not null,
       who_created varchar(36) not null ,
       when_created varchar(20) not null ,
       app_id varchar(36),
       primary key(id)
);

-- 以下是来自 version_5_1.sql 的内容 --



CREATE TABLE sys_search_config (
                                   id varchar2(36)  NOT NULL,
                                   data_source varchar2(100) ,
                                   table_name varchar2(100),
                                   columns varchar2(255),
                                   primary_columns varchar2(255),
                                   link varchar2(255) ,
                                   labels varchar2(255) ,
                                   when_created varchar2(50) ,
                                   when_modified varchar2(50) ,
                                   who_created varchar2(36) ,
                                   who_modified varchar2(36) ,
                                   title_column varchar2(100) ,
                                   PRIMARY KEY (id)
) ;

CREATE TABLE rep_template(
                             id varchar2(36) NOT NULL ,
                             name varchar2(255) NOT NULL  ,
                             tpl_file_id varchar2(36) ,
                             excel_file varchar2(36) ,
                             type varchar2(10) ,
                             ds_sets varchar2(1024) ,
                             note varchar2(255) ,
                             who_created varchar2(36) ,
                             when_created varchar2(30)  ,
                             who_modified varchar2(36) ,
                             when_modified varchar2(30)  ,
                             PRIMARY KEY (id)
) ;
CREATE TABLE sys_cache (
                           id varchar2(36) NOT NULL,
                           code varchar2(255) DEFAULT NULL,
                           value varchar2(255) DEFAULT NULL ,
                           when_expired varchar2(30) DEFAULT NULL,
                           when_created varchar2(30) DEFAULT NULL ,
                           app_id varchar2(36) DEFAULT NULL  ,
                           PRIMARY KEY (id)
);

-- 以下是来自 version_6_1.sql 的内容 --

alter table SYS_TASK add TASK_ARGV CLOB;


-- 以下是来自 version_7_1.sql 的内容 --


alter table sys_api add module_id VARCHAR(36) null ;
alter table sys_logic_flow add module_id VARCHAR(36) null;
alter table dev_page add module_id VARCHAR(36) null;
alter table dev_page add tags VARCHAR(36) null ;



-- 以下是来自 version_8_1.sql 的内容 --

alter table sys_dict_item modify  value varchar2(255) ;


-- 以下是来自 version_29_1.sql 的内容 --

alter table SYS_OPERATE_LOG
    add METHOD varchar(255) null;
alter table SYS_OPERATE_LOG
    add REQUEST_METHOD varchar(255) null;
alter table SYS_OPERATE_LOG
    add RESPONSE_BODY CLOB null;



-- 以下是来自 version_30_1.sql 的内容 --


CREATE TABLE sys_instance(
                                  id VARCHAR(32) NOT NULL PRIMARY KEY,
                                  host_name VARCHAR(32)  ,
                                  port INT  ,
                                  heart_beat_time VARCHAR(20)   ,
                                  reg_time VARCHAR(20),
                                  "online" INT

);

alter table sys_role_menu modify  id varchar(36);
alter table sys_role_menu modify  sys_menu_id varchar(36) ;
alter table sys_role_menu modify  who_created varchar(36) ;


-- 以下是来自 version_31_1.sql 的内容 --

alter table sys_task
    add next_inst varchar(36) null;


-- 以下是来自 version_33_1.sql 的内容 --


CREATE TABLE sys_offline_download(
                                     id VARCHAR(36) NOT NULL   primary key,
                                     file_name VARCHAR(90)   ,
                                     task_name VARCHAR(255)  ,
                                     file_path VARCHAR(255)   ,
                                     end_time VARCHAR(255)    ,
                                     script VARCHAR(1024)   ,
                                     process INT    ,
                                     status VARCHAR(255)     ,
                                     params VARCHAR(255)   ,
                                     error_message VARCHAR(900)   ,
                                     who_created VARCHAR(36)  ,
                                     when_created VARCHAR(20)
) ;


-- 以下是来自 version_34_1.sql 的内容 --


ALTER TABLE sys_unit ADD unit_level int NULL ;
ALTER TABLE sys_search_config ADD search_columns varchar(1000) NULL ;
ALTER TABLE sys_search_config MODIFY  columns varchar(1000) ;


-- 以下是来自 version_35_1.sql 的内容 --

ALTER TABLE sys_unit
    ADD required_unit int DEFAULT 0 ;

alter table sys_api
    modify api_tags VARCHAR(255) ;
alter table sys_logic_flow
    modify tags VARCHAR(255) ;
alter table dev_page
    modify tags VARCHAR(255) ;


CREATE TABLE  sys_logic_flow_mock
(
    id            VARCHAR(36) NOT NULL,
    name          VARCHAR(90),
    flow_id       VARCHAR(36),
    depend_id     VARCHAR(36),
    request_argv  CLOB,
    assert_expr   VARCHAR(900),
    enable_mock   INT,
    who_created   VARCHAR(36),
    when_created  VARCHAR(20),
    who_modified  VARCHAR(36),
    when_modified VARCHAR(20),
    PRIMARY KEY (id)
    );

CREATE TABLE  dev_page_template
(
    id            varchar(36) NOT NULL,
    when_created  varchar(20),
    when_modified varchar(20),
    who_created   varchar(36),
    who_modified  varchar(36),
    deleted       int DEFAULT 0,
    app_id        varchar(36),
    name          varchar(255),
    description   varchar(255),
    app_type      varchar(100),
    page_json CLOB,
    tags          varchar(255),
    module_id     varchar(36),
    PRIMARY KEY (id)
    );

CREATE TABLE  dev_table
(
    id            varchar(36),
    data_source   varchar(100),
    name          varchar(100),
    "comment"     varchar(255),
    when_created  varchar(100),
    when_modified varchar(100),
    who_created   varchar(100),
    who_modified  varchar(100),
    PRIMARY KEY (id)
    );

CREATE TABLE  dev_table_column
(
    id              varchar(36),
    table_id        varchar(36),
    name            varchar(100),
    "comment"       varchar(255),
    sort            int,
    type            varchar(50),
    length          varchar(36),
    decimal_point   varchar(100),
    is_primary      int,
    is_null         int,
    when_created    varchar(100),
    when_modified   varchar(100),
    who_created     varchar(100),
    who_modified    varchar(100),
    is_label_column int NOT NULL,
    PRIMARY KEY (id)
    );


CREATE TABLE  dev_table_update_log
(
    id              varchar(36),
    operate_type    int,
    table_name      varchar(100),
    column_name     varchar(100),
    version_tag     varchar(100),
    when_created    varchar(100),
    when_modified   varchar(100),
    who_created     varchar(100),
    who_modified    varchar(100),
    old_table_name  varchar(100),
    old_column_name varchar(100),
    type            varchar(36),
    length          varchar(36),
    decimal_point   int,
    is_primary      int,
    is_null         int,
    sort            varchar(36),
    "comment"       varchar(255),
    table_id        varchar(100),
    is_column_label int,
    PRIMARY KEY (id)
    );

alter table sys_operate_log modify  operate_time varchar(20);
alter table dev_application modify  enable_status int ;
alter table dev_application modify  dev_status int ;

create table  sf_ext_form (
    id               varchar(50)  not null
    primary key,
    who_created      varchar(50)  null,
    when_created     varchar(50)  null,
    who_modified     varchar(50)  null,
    when_modified    varchar(50)  null,
    name             varchar(100) null,
    type             varchar(50)  null,
    group_name       varchar(500) null,
    field_properties CLOB     null,
    list_button      CLOB     null,
    page_path        varchar(500) null,
    db_name          varchar(100) null,
    tab_name         varchar(500) null,
    is_app           varchar(50)  null,
    app_id           varchar(50)  null,
    page_id          varchar(50)  null,
    description      CLOB         null
    );

create table  rep_app
(
    id            varchar(32)  not null
    primary key,
    app_name      varchar(50)  null,
    app_note      varchar(256) null,
    who_created   varchar(32)  null,
    when_created  varchar(20)  null,
    who_modified  varchar(32)  null,
    when_modified varchar(20)  null
    );

-- auto-generated definition
create table rep_dataset
(
    id            varchar(32)  not null
        primary key,
    ds_name       varchar(50)  null,
    rep_app_id    varchar(32)  null,
    ds_meta       CLOB         null,
    ds_type       int          null,
    ds_note       varchar(255) null,
    when_created  varchar(20)  null,
    who_modified  varchar(32)  null,
    when_modified varchar(20)  null,
    who_created   varchar(32)  null,
    column_def    CLOB         null
);

create table  rep_dataset_config
(
    id                  varchar(32)  not null,
    select_table        varchar(100) null ,
    conditions_assembly CLOB         null ,
    field_alias         CLOB         null,
    condition_group     CLOB         null ,
    when_create         varchar(100) null,
    who_create          varchar(100) null ,
    when_modified       varchar(100) null ,
    who_modified        varchar(100) null ,
    rep_dataset_id      varchar(32)  null,
    source_id           varchar(32)  null
    );

-- auto-generated definition
create table  wf_ext_category
(
    id            varchar(32) not null
    primary key,
    category_name varchar(50) null ,
    order_num     int      null ,
    who_created   varchar(32) null ,
    when_created  varchar(20) null ,
    who_modified  varchar(32) null ,
    when_modified varchar(20) null
    );

-- auto-generated definition
create table  wf_ext_comment
(
    id             varchar(32)                   not null
    primary key,
    task_name      varchar(50)                   null,
    proc_inst_id   varchar(32)                   null ,
    task_id        varchar(32)                   null ,
    type           varchar(20) default 'comment' null,
    user_id        varchar(32)                   null ,
    message        varchar(255)                  null,
    when_created   varchar(20)                   null ,
    parent_inst_id varchar(50)                   null
    );


-- auto-generated definition
create table  wf_ext_node_attribute
(
    id             varchar(100) not null
    primary key,
    node_type      varchar(50)  null,
    next_user      varchar(500) null,
    msg_send_rule  varchar(100) null,
    time_out       varchar(50)  null,
    actions        varchar(100) null,
    when_created   varchar(50)  null,
    who_created    varchar(100) null,
    when_modified  varchar(50)  null,
    who_modified   varchar(100) null,
    flow_id        varchar(100) null,
    node_id        varchar(100) null,
    form_attribute CLOB         null,
    exec_mode      varchar(50)  null,
    pass_ok        varchar(50)  null,
    person         varchar(50)  null ,
    back_node      varchar(500) null,
    name           varchar(200) null
    );

-- auto-generated definition
create table  wf_ext_node_define
(
    id            varchar(50)  null,
    who_created   varchar(50)  null,
    when_created  varchar(50)  null,
    who_modified  varchar(50)  null,
    when_modified varchar(50)  null,
    name          varchar(200) null,
    group_name    varchar(200) null,
    type          varchar(200) null,
    start_icon    CLOB         null,
    show_icon     CLOB         null,
    node_json     varchar(200) null,
    is_extend     varchar(200) null,
    status        varchar(200) null
    );

-- auto-generated definition
create table  wf_ext_procdef
(
    id                  varchar(50)  not null
    primary key,
    proc_definition_key varchar(100) null,
    proc_name           varchar(100) null,
    category_id         varchar(32)  null,
    proc_version        int      null,
    deploy_md5          varchar(255) null,
    icon                varchar(50)  null,
    content             clob     null,
    deploy_status       int      null,
    deploy_time         varchar(20)  null,
    order_num           int       null,
    who_created         varchar(32)  null,
    when_created        varchar(20)  null,
    who_modified        varchar(32)  null,
    when_modified       varchar(20)  null,
    proc_definition_id  varchar(32)  null,
    form_page_id        varchar(32)  null,
    form_key            varchar(50)  null,
    work_num            varchar(100) null,
    inst_desc           varchar(255) null,
    page_form           clob     null
    );

-- auto-generated definition
create table  wf_ext_procinst
(
    id           varchar(32)  not null
    primary key,
    proc_inst_id varchar(32)  null ,
    starter      varchar(32)  null ,
    bill_code    varchar(50)  null ,
    bill_title   varchar(100) null ,
    when_created varchar(20)  null ,
    form_data    CLOB         null ,
    main_inst_id varchar(50)  null ,
    main_task_id varchar(50)  null
    );

-- auto-generated definition
create table sys_excel
(
    id            varchar(255) not null
        primary key,
    name          varchar(255) null,
    data_json     clob     null,
    data_from     int          null ,
    when_created  varchar(30)  null ,
    when_modified varchar(30)  null,
    who_created   varchar(36)  null ,
    who_modified  varchar(36)  null ,
    app_id        varchar(36)  null ,
    data_from_id  varchar(36)  null
);

-- auto-generated definition
-- create table dev_application_version_history
-- (
--     id           varchar(36)  not null
--         primary key,
--     when_created timestamp    null,
--     who_created  varchar(36)  null,
--     app_id       varchar(36)  null,
--     version      varchar(50)  null,
--     file_name    varchar(255) null,
--     note         varchar(255) null,
--     export_data  CLOB         null
-- );

INSERT INTO DEV_MODULE (ID, NAME, PATH, HAS_PATH, PARENT_ID, SORT, WHEN_CREATED, WHO_CREATED, WHEN_MODIFIED, WHO_MODIFIED, IS_SYS, APP_ID) VALUES ('0913bc0b384c44d99e384b992cb7fe40', '开发平台', '/dev', 1, null, 1, '2023-03-29 10:43:48', '7aed8c297a6940f681c26eb6ab68893d', '2023-03-30 09:55:00', '7aed8c297a6940f681c26eb6ab68893d', 1, '064b3b44b85a45fe87fcce88d72b2519');
INSERT INTO DEV_MODULE (ID, NAME, PATH, HAS_PATH, PARENT_ID, SORT, WHEN_CREATED, WHO_CREATED, WHEN_MODIFIED, WHO_MODIFIED, IS_SYS, APP_ID) VALUES ('09e4ec197da14de3844b6b04c4fa5ee9', '开发平台管理', null, 0, null, 3, '2023-03-29 10:48:15', '7aed8c297a6940f681c26eb6ab68893d', '2023-03-29 10:48:15', '7aed8c297a6940f681c26eb6ab68893d', 1, '064b3b44b85a45fe87fcce88d72b2519');
INSERT INTO DEV_MODULE (ID, NAME, PATH, HAS_PATH, PARENT_ID, SORT, WHEN_CREATED, WHO_CREATED, WHEN_MODIFIED, WHO_MODIFIED, IS_SYS, APP_ID) VALUES ('1e329b86988b4dd79b49887b774b0879', '示例', null, 0, null, 18, '2023-03-29 16:28:22', '7aed8c297a6940f681c26eb6ab68893d', '2023-03-29 16:28:22', '7aed8c297a6940f681c26eb6ab68893d', 1, '064b3b44b85a45fe87fcce88d72b2519');
INSERT INTO DEV_MODULE (ID, NAME, PATH, HAS_PATH, PARENT_ID, SORT, WHEN_CREATED, WHO_CREATED, WHEN_MODIFIED, WHO_MODIFIED, IS_SYS, APP_ID) VALUES ('5a10d15671704b6cbc01cc3a8bed365a', '公共库', null, 0, null, 4, '2022-05-31 15:18:08', '7aed8c297a6940f681c26eb6ab68893d', '2022-05-31 15:24:40', '7aed8c297a6940f681c26eb6ab68893d', 1, '064b3b44b85a45fe87fcce88d72b2519');
INSERT INTO DEV_MODULE (ID, NAME, PATH, HAS_PATH, PARENT_ID, SORT, WHEN_CREATED, WHO_CREATED, WHEN_MODIFIED, WHO_MODIFIED, IS_SYS, APP_ID) VALUES ('90f2c2ea0f9942d181388e24fd6ee936', 'v3开发', '/dev/v3', 1, '0913bc0b384c44d99e384b992cb7fe40', 1, '2023-03-29 17:37:35', '7aed8c297a6940f681c26eb6ab68893d', '2023-03-30 09:55:12', '7aed8c297a6940f681c26eb6ab68893d', 1, '064b3b44b85a45fe87fcce88d72b2519');
INSERT INTO DEV_MODULE (ID, NAME, PATH, HAS_PATH, PARENT_ID, SORT, WHEN_CREATED, WHO_CREATED, WHEN_MODIFIED, WHO_MODIFIED, IS_SYS, APP_ID) VALUES ('9bbe33574d0547e78f72f5982bea26cd', '子页面', null, 0, '0913bc0b384c44d99e384b992cb7fe40', 0, '2023-03-29 10:46:23', '7aed8c297a6940f681c26eb6ab68893d', '2023-03-30 09:55:08', '7aed8c297a6940f681c26eb6ab68893d', 1, '064b3b44b85a45fe87fcce88d72b2519');
INSERT INTO DEV_MODULE (ID, NAME, PATH, HAS_PATH, PARENT_ID, SORT, WHEN_CREATED, WHO_CREATED, WHEN_MODIFIED, WHO_MODIFIED, IS_SYS, APP_ID) VALUES ('d4820db91eab4cdc9c82703cf1d4df83', '流程关联表单页', null, 0, '1e329b86988b4dd79b49887b774b0879', 0, '2023-03-29 16:28:46', '7aed8c297a6940f681c26eb6ab68893d', '2023-03-29 16:28:46', '7aed8c297a6940f681c26eb6ab68893d', 0, '064b3b44b85a45fe87fcce88d72b2519');
INSERT INTO DEV_MODULE (ID, NAME, PATH, HAS_PATH, PARENT_ID, SORT, WHEN_CREATED, WHO_CREATED, WHEN_MODIFIED, WHO_MODIFIED, IS_SYS, APP_ID) VALUES ('e0047b3716fa48789d8d2377b1d23195', '系统配置', null, 0, null, 2, '2023-03-29 09:27:04', '7aed8c297a6940f681c26eb6ab68893d', '2023-03-29 09:27:04', '7aed8c297a6940f681c26eb6ab68893d', 1, '064b3b44b85a45fe87fcce88d72b2519');
INSERT INTO DEV_MODULE (ID, NAME, PATH, HAS_PATH, PARENT_ID, SORT, WHEN_CREATED, WHO_CREATED, WHEN_MODIFIED, WHO_MODIFIED, IS_SYS, APP_ID) VALUES ('fbe9d1e36a37423aa9ab4711c569093f', '基础功能', null, 0, null, 2, '2023-03-29 09:26:54', '7aed8c297a6940f681c26eb6ab68893d', '2023-03-29 09:26:54', '7aed8c297a6940f681c26eb6ab68893d', 1, '064b3b44b85a45fe87fcce88d72b2519');








-- 以下是来自 version_36_1.sql 的内容 --

CREATE TABLE  dev_plugin_api (
                                id VARCHAR(36) PRIMARY KEY,
                                title VARCHAR(255),
                                group_code VARCHAR(255),
                                code VARCHAR(255),
                                tags VARCHAR(255),
                                notes CLOB,
                                order_num VARCHAR(10),
                                who_created VARCHAR(36),
                                when_created VARCHAR(20),
                                who_modified VARCHAR(36),
                                when_modified VARCHAR(20)
);

CREATE TABLE   dev_plugin_group (
                                                 id VARCHAR(36) PRIMARY KEY,
                                                 name VARCHAR(255),
                                                 code VARCHAR(255),
                                                 notes CLOB,
                                                 order_num INT,
                                                 who_created VARCHAR(36),
                                                 when_created VARCHAR(20),
                                                 who_modified VARCHAR(36),
                                                 when_modified VARCHAR(20)
);



CREATE TABLE dev_plugin_operation (
                                                    id VARCHAR(36) PRIMARY KEY,
                                                    code VARCHAR(255),
                                                    tags VARCHAR(255),
                                                    api_id VARCHAR(36),
                                                    title VARCHAR(255),
                                                    notes CLOB,
                                                    cases CLOB,
                                                    success_resp CLOB,
                                                    error_resp CLOB,
                                                    in_params CLOB,
                                                    who_created VARCHAR(36),
                                                    when_created VARCHAR(20),
                                                    who_modified VARCHAR(36),
                                                    when_modified VARCHAR(20),
                                                    order_num INT DEFAULT 0
);




-- 以下是来自 version_37_1.sql 的内容 --

delete from sys_dict_item where id='1537c3ca5e934e6c846b0415229dbe85';


-- 以下是来自 version_38_1.sql 的内容 --


alter table sys_logic_flow add new_flow_json CLOB null;
alter table sys_logic_history add new_flow_json CLOB null;
alter table sys_logic_template add flow_config CLOB null;
alter table sys_logic_template add type INT null;
alter table sys_logic_template add new_flow_json CLOB null;


-- 以下是来自 version_39_1.sql 的内容 --

CREATE TABLE  "SYS_DATA_CHANGE" ("ID" VARCHAR(32) NOT NULL, "NAME" VARCHAR(255), "TABLE_NAME" VARCHAR(255), "OBJECT_NAME" VARCHAR(255), "OPERATOR" VARCHAR(255), "OPER_TYPE" VARCHAR(255), "OPER_TIME" VARCHAR(255), "CONTENT" VARCHAR(255), PRIMARY KEY ("ID"));

CREATE TABLE  "NUMBER_REGULATION" ("ID" VARCHAR(36) NOT NULL, "SORT" VARCHAR(255), "TYPE" VARCHAR(255), "VALUE" VARCHAR(255), PRIMARY KEY ("ID"));

CREATE TABLE  "NUMBER_SERIAL" ("ID" VARCHAR(36) NOT NULL, "CREATE_TIME" VARCHAR(255), "NAME" VARCHAR(255), "NUMBER" VARCHAR(255), "REGULATIONID" VARCHAR(64), "REMARK" VARCHAR(255), "START_VALUE" VARCHAR(255), "STEP_VALUE" VARCHAR(255), "UPDATE_TIME" VARCHAR(255), PRIMARY KEY ("ID"));

CREATE TABLE  "SYS_AUTO_SERIAL" ("ID" VARCHAR(36) NOT NULL, "AUTO_NUM" INT, "CATEGORY" VARCHAR(100), "CREATE_TIME" VARCHAR(20), "CREATE_USER" VARCHAR(50), "KEY" VARCHAR(50), "LOCKED" INT DEFAULT 0, "NUM_LENGTH" INT, "START_NUM" INT, "STEP" INT, "TPL" VARCHAR(100), "TYPE" INT, "UPDATE_TIME" VARCHAR(20), "UPDATE_USER" VARCHAR(50), PRIMARY KEY ("ID"));

CREATE TABLE  "SYS_HINT_SELECT" ("ID" VARCHAR(36) NOT NULL, "CODE" VARCHAR(100), "DB_ID" VARCHAR(100), "FLOW_ID" VARCHAR(50), "REMARK" CLOB, "SELECT_FIELDS" CLOB, "SELECT_SQL" CLOB, "TYPE" VARCHAR(50), PRIMARY KEY ("ID"));

CREATE TABLE  "SYS_MQ_CHANNEL" ("ID" VARCHAR(36) NOT NULL, "BATCH_CONSUMER" INT, "CHANNEL_NAME" VARCHAR(50), "CONSUMER_GROUP" VARCHAR(50), "CONSUMER_THREAD" INT, "ENABLE" INT, "MESSAGE_NAME" VARCHAR(50), "TOPIC" VARCHAR(50), "ZK_ADDRESS" VARCHAR(50), PRIMARY KEY ("ID"));


-- 以下是来自 version_40_1.sql 的内容 --



-- 以下是来自 version_41_1.sql 的内容 --

create table sys_auth_source
(
    id varchar2 (36 char) not null,
    code varchar2 (36 char),
    icon varchar2 (255 char),
    config clob,
    logic_flow_id varchar2 (36 char),
    name varchar2 (255 char),
    note varchar2 (255 char),
    order_num number (10,0),
    status number (10,0),
    type number (10,0),
    when_created varchar2 (20 char),
    when_modified varchar2 (20 char),
    who_created varchar2 (36 char),
    who_modified varchar2 (36 char),
    primary key (id)
);


-- 以下是来自 version_42_1.sql 的内容 --

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

-- 以下是来自 version_43_1.sql 的内容 --

INSERT INTO dev_team (id, deleted, description, name, owner, when_created, when_modified, who_created, who_modified) VALUES('991718335d57416a9be67d4090538402', 0, NULL, '默认团队', '8116f0bc8222413fb72de98a32960b1a', '2022-09-28 14:52:00', '2022-09-28 14:52:00', '056fb0eeb9a44cb0953534b4c0ca01fa', '056fb0eeb9a44cb0953534b4c0ca01fa');
INSERT INTO dev_team_member (id, app_id, is_owner, team_role_id, user_id, when_join, who_invite, team_id) VALUES('dfae64f660ff435086ae2482d7fa1a48', NULL, 0, '3fc43c9c69f44144bd032d9451ba328b', '8116f0bc8222413fb72de98a32960b1a', '2022-09-28 14:52:00', '056fb0eeb9a44cb0953534b4c0ca01fa', '991718335d57416a9be67d4090538402');
INSERT INTO dev_team_member (id, app_id, is_owner, team_role_id, user_id, when_join, who_invite, team_id) VALUES('dfae64f660ff435086ae2482d7fa1a49', NULL, 1, '4a30f4d346074b4ba8363944f004c1d9', '056fb0eeb9a44cb0953534b4c0ca01fa', '2022-09-28 14:52:00', '056fb0eeb9a44cb0953534b4c0ca01fa', '991718335d57416a9be67d4090538402');

CREATE TABLE  sys_logic_template_user (
                                         id varchar(36) NOT NULL,
                                         app_id varchar(36) DEFAULT NULL,
                                         when_created varchar(50) DEFAULT NULL,
                                         who_created varchar(36) DEFAULT NULL,
                                         template_id varchar(36) DEFAULT NULL,
                                         PRIMARY KEY (id)
);
CREATE TABLE  dev_curd (
    id varchar(36)  NOT NULL ,
    name varchar(255)  DEFAULT null,
    group_id varchar(36)  DEFAULT NULL,
    source_name varchar(255)  DEFAULT NULL,
    table_name varchar(255)  DEFAULT NULL,
    primary_name varchar(50)  DEFAULT NULL,
    request_prefix varchar(255)  DEFAULT NULL,
    enable_funs varchar(255)  DEFAULT NULL,
    create_funs varchar(255)  DEFAULT NULL,
    column_json clob ,
    app_id varchar(36)  DEFAULT NULL,
    who_created varchar(36)  DEFAULT NULL,
    when_created varchar(20)  DEFAULT NULL,
    who_modified varchar(36)  DEFAULT NULL,
    when_modified varchar(255)  DEFAULT NULL ,
    PRIMARY KEY (id)
    );

ALTER TABLE dev_page_template ADD snapshot_img_id VARCHAR(32) NULL;
ALTER TABLE dev_page_template ADD order_num integer NULL  ;
ALTER TABLE dev_page_template ADD bg_colors VARCHAR(64) NULL;
ALTER TABLE dev_page_template ADD page_type VARCHAR(32) NULL;
ALTER TABLE dev_page_template ADD use_num integer DEFAULT 0 NULL;
ALTER TABLE dev_page_template ADD extra CLOB NULL;


alter table sys_logic_template add sys_suggested integer null;
alter table sys_logic_template add publish_status integer null;


CREATE TABLE  dev_page_template_history (
        id varchar(36) NOT NULL,
        tpl_id varchar(36) DEFAULT NULL,
        page_json CLOB,
        when_created varchar(50) NULL ,
        who_created varchar(36) DEFAULT NULL,
        version_tag varchar(50) DEFAULT NULL,
        version_tag_time varchar(30),
        app_id varchar(36) DEFAULT NULL,
        PRIMARY KEY (id)
);


-- 修改 dev_power_tree 表
ALTER TABLE dev_power_tree ADD order_num integer ;

-- 修改 sys_logic_template 表
ALTER TABLE sys_logic_template ADD order_num integer ;
ALTER TABLE sys_logic_template ADD enable_status integer ;

-- 以下是来自 version_44_1.sql 的内容 --

alter table sys_menu add affix integer null;
ALTER TABLE sys_api add cache_enable integer null;
ALTER TABLE sys_api add cache_expire_time integer null;
ALTER TABLE sys_api add cache_cron VARCHAR(64) null;


-- 以下是来自 version_45_1.sql 的内容 --

CREATE TABLE sys_password_log (
                                  id VARCHAR(36) PRIMARY KEY,
                                  user_id VARCHAR(36),
                                  when_created VARCHAR(20)
);

COMMENT ON COLUMN sys_password_log.id IS '主键';
COMMENT ON COLUMN sys_password_log.user_id IS '用户id';
COMMENT ON COLUMN sys_password_log.when_created IS '创建时间';

CREATE TABLE sys_secret_rule (
                                 id VARCHAR(36) PRIMARY KEY,
                                 name VARCHAR(255),
                                 logic_id VARCHAR(36),
                                 secret_type INTEGER DEFAULT 0,
                                 status INTEGER,
                                 order_num INTEGER,
                                 notes CLOB,
                                 when_created VARCHAR(20),
                                 who_created VARCHAR(36),
                                 when_modified VARCHAR(20),
                                 who_modified VARCHAR(36)
);

COMMENT ON COLUMN sys_secret_rule.id IS '主键';
COMMENT ON COLUMN sys_secret_rule.name IS '规则名称';
COMMENT ON COLUMN sys_secret_rule.logic_id IS '逻辑编排ID';
COMMENT ON COLUMN sys_secret_rule.secret_type IS '安全类型';
COMMENT ON COLUMN sys_secret_rule.status IS '是否启用';
COMMENT ON COLUMN sys_secret_rule.order_num IS '排序';
COMMENT ON COLUMN sys_secret_rule.notes IS '说明';
COMMENT ON COLUMN sys_secret_rule.when_created IS '创建时间';
COMMENT ON COLUMN sys_secret_rule.who_created IS '创建人';
COMMENT ON COLUMN sys_secret_rule.when_modified IS '更新时间';
COMMENT ON COLUMN sys_secret_rule.who_modified IS '更新人';


-- 以下是来自 version_46_1.sql 的内容 --

-- sys_unit
ALTER TABLE  sys_unit add short_name varchar(36) null;
ALTER TABLE  sys_unit add short_code varchar(100)  null;

-- sys_user
ALTER TABLE sys_user add jira_name varchar(100)  null;

-- 以下是来自 version_47_1.sql 的内容 --

ALTER TABLE sys_menu
    ADD active_icon VARCHAR(255) NULL;
alter table sys_config add is_public integer null;


-- 以下是来自 version_48_1.sql 的内容 --

ALTER TABLE dev_team ADD is_audit number DEFAULT NULL;
ALTER TABLE dev_team ADD image VARCHAR(255) DEFAULT NULL;

-- 以下是来自 version_49_1.sql 的内容 --

CREATE TABLE  DEV_PINE_PLUGIN (
    ID varchar(36) NOT NULL,
    APP_ID varchar(36),
    AUTHOR varchar(50),
    ENABLE_STATUS int DEFAULT 0,
    FILE_ID varchar(36),
    NOTE clob,
    PLUGIN_NAME varchar(50),
    PLUGIN_VERSION varchar(10),
    WHEN_CREATED varchar(36),
    WHEN_MODIFIED varchar(30),
    WHO_CREATED varchar(36),
    WHO_MODIFIED varchar(36),
    PRIMARY KEY (ID)
);
COMMENT ON COLUMN DEV_PINE_PLUGIN.APP_ID IS '归属应用id';
COMMENT ON COLUMN DEV_PINE_PLUGIN.AUTHOR IS '插件作者';
COMMENT ON COLUMN DEV_PINE_PLUGIN.ENABLE_STATUS IS '是否启动';
COMMENT ON COLUMN DEV_PINE_PLUGIN.FILE_ID IS '文件id';
COMMENT ON COLUMN DEV_PINE_PLUGIN.NOTE IS '说明';
COMMENT ON COLUMN DEV_PINE_PLUGIN.PLUGIN_NAME IS '插件名称';
COMMENT ON COLUMN DEV_PINE_PLUGIN.PLUGIN_VERSION IS '插件版本号';
COMMENT ON COLUMN DEV_PINE_PLUGIN.WHEN_CREATED IS '创建时间';
COMMENT ON COLUMN DEV_PINE_PLUGIN.WHEN_MODIFIED IS '修改时间';
COMMENT ON COLUMN DEV_PINE_PLUGIN.WHO_CREATED IS '创建人';
COMMENT ON COLUMN DEV_PINE_PLUGIN.WHO_MODIFIED IS '修改人员';


CREATE TABLE  DEV_MODEL_SQL (
    ID varchar(36) NOT NULL,
    APP_ID varchar(36),
    TITLE varchar(255),
    SOURCE_NAME varchar(50),
    CONTENT clob,
    STATUS int DEFAULT 0,
    SQL_VERSION int,
    MESSAGES clob,
    IGNORE_EXCEPT int DEFAULT 1,
    EXEC_ERR_LINE int DEFAULT 0,
    EXEC_TIME varchar(20),
    EXEC_USER_ID varchar(36),
    WHEN_CREATED varchar(20),
    WHO_CREATED varchar(36),
    WHEN_MODIFIED varchar(20),
    WHO_MODIFIED varchar(36),
    PRIMARY KEY (ID)
);
COMMENT ON COLUMN DEV_MODEL_SQL.ID IS '主键';
COMMENT ON COLUMN DEV_MODEL_SQL.APP_ID IS '应用id';
COMMENT ON COLUMN DEV_MODEL_SQL.TITLE IS '标题';
COMMENT ON COLUMN DEV_MODEL_SQL.SOURCE_NAME IS '数据源';
COMMENT ON COLUMN DEV_MODEL_SQL.CONTENT IS '脚本';
COMMENT ON COLUMN DEV_MODEL_SQL.STATUS IS '执行状态 0: 未执行 1：已执行 2：执行异常';
COMMENT ON COLUMN DEV_MODEL_SQL.SQL_VERSION IS '版本号';
COMMENT ON COLUMN DEV_MODEL_SQL.MESSAGES IS '执行结果';
COMMENT ON COLUMN DEV_MODEL_SQL.IGNORE_EXCEPT IS '是否忽略错误';
COMMENT ON COLUMN DEV_MODEL_SQL.EXEC_ERR_LINE IS '错误行号';
COMMENT ON COLUMN DEV_MODEL_SQL.EXEC_TIME IS '执行时间';
COMMENT ON COLUMN DEV_MODEL_SQL.EXEC_USER_ID IS '执行人';
COMMENT ON COLUMN DEV_MODEL_SQL.WHEN_CREATED IS '创建时间';
COMMENT ON COLUMN DEV_MODEL_SQL.WHO_CREATED IS '创建人';
COMMENT ON COLUMN DEV_MODEL_SQL.WHEN_MODIFIED IS '修改时间';
COMMENT ON COLUMN DEV_MODEL_SQL.WHO_MODIFIED IS '修改人';


CREATE TABLE  DEV_DATA_SOURCE (
    ID varchar(36) NOT NULL,
    NAME varchar(100),
    WHO_CREATED varchar(36),
    WHEN_CREATED varchar(30),
    WHO_MODIFIED varchar(36),
    WHEN_MODIFIED varchar(30),
    APP_ID varchar(36),
    KDB_ID varchar(40),
    TEAM_ID varchar(36),
    DELETED int,
    PRIMARY KEY (ID)
);
COMMENT ON COLUMN DEV_DATA_SOURCE.ID IS 'ID';
COMMENT ON COLUMN DEV_DATA_SOURCE.NAME IS '数据源名称';
COMMENT ON COLUMN DEV_DATA_SOURCE.WHO_CREATED IS '创建人员';
COMMENT ON COLUMN DEV_DATA_SOURCE.WHEN_CREATED IS '创建时间';
COMMENT ON COLUMN DEV_DATA_SOURCE.WHO_MODIFIED IS '修改人员';
COMMENT ON COLUMN DEV_DATA_SOURCE.WHEN_MODIFIED IS '修改时间';
COMMENT ON COLUMN DEV_DATA_SOURCE.APP_ID IS '关联应用';
COMMENT ON COLUMN DEV_DATA_SOURCE.KDB_ID IS '在kingDB中对应的ID';
COMMENT ON COLUMN DEV_DATA_SOURCE.TEAM_ID IS '所属团队ID';
COMMENT ON COLUMN DEV_DATA_SOURCE.DELETED IS '是否已删除';


CREATE TABLE  DEV_SEARCH_HISTORY (
    ID varchar(36) NOT NULL,
    KEYWORD varchar(36) NOT NULL,
    USE_NUM int,
    DELETED int DEFAULT 0,
    PRIMARY KEY (ID)
 );
COMMENT ON COLUMN DEV_SEARCH_HISTORY.ID IS '主键';
COMMENT ON COLUMN DEV_SEARCH_HISTORY.KEYWORD IS '关键字';
COMMENT ON COLUMN DEV_SEARCH_HISTORY.USE_NUM IS '搜索次数';
COMMENT ON COLUMN DEV_SEARCH_HISTORY.DELETED IS '是否删除';


CREATE TABLE  DEV_GIT_TAG (
    ID varchar(36) NOT NULL,
    TAG varchar(255),
    REPO varchar(255),
    "RESOURCE" varchar(255),
    COMMIT_ID varchar(255),
    PUBLIC_COMMIT_IDS clob,
    NOTE clob,
    WHEN_CREATED varchar(20),
    WHO_CREATED varchar(255),
    PRIMARY KEY (ID)
);
COMMENT ON COLUMN DEV_GIT_TAG.ID IS '主键';
COMMENT ON COLUMN DEV_GIT_TAG.TAG IS '标签名称';
COMMENT ON COLUMN DEV_GIT_TAG.REPO IS '仓库地址';
COMMENT ON COLUMN DEV_GIT_TAG."RESOURCE" IS '资源文件';
COMMENT ON COLUMN DEV_GIT_TAG.COMMIT_ID IS '提交ID';
COMMENT ON COLUMN DEV_GIT_TAG.PUBLIC_COMMIT_IDS IS '公共依赖库';
COMMENT ON COLUMN DEV_GIT_TAG.NOTE IS '版本说明';
COMMENT ON COLUMN DEV_GIT_TAG.WHEN_CREATED IS '创建时间';
COMMENT ON COLUMN DEV_GIT_TAG.WHO_CREATED IS '创建人';

CREATE TABLE  SYS_CONFIG_GROUP (
    ID varchar(36) NOT NULL,
    APP_ID varchar(36),
    GROUP_NAME varchar(255) NOT NULL,
    GROUP_PATH varchar(255),
    WHEN_CREATED varchar(30),
    WHEN_MODIFIED varchar(30),
    WHO_CREATED varchar(36),
    WHO_MODIFIED varchar(36),
    NOTE varchar(255),
    PARENT_ID varchar(32),
    GROUP_TYPE int DEFAULT 1,
    LEAF_CONFIG clob,
    SORT int,
    ICON varchar(50),
    PRIMARY KEY (ID)
);
COMMENT ON COLUMN SYS_CONFIG_GROUP.ID IS '主键ID';
COMMENT ON COLUMN SYS_CONFIG_GROUP.APP_ID IS '关联应用';
COMMENT ON COLUMN SYS_CONFIG_GROUP.GROUP_NAME IS '组名称';
COMMENT ON COLUMN SYS_CONFIG_GROUP.GROUP_PATH IS '路径';
COMMENT ON COLUMN SYS_CONFIG_GROUP.WHEN_CREATED IS '创建时间';
COMMENT ON COLUMN SYS_CONFIG_GROUP.WHEN_MODIFIED IS '修改时间';
COMMENT ON COLUMN SYS_CONFIG_GROUP.WHO_CREATED IS '创建人员';
COMMENT ON COLUMN SYS_CONFIG_GROUP.WHO_MODIFIED IS '修改人员';
COMMENT ON COLUMN SYS_CONFIG_GROUP.NOTE IS '备注';
COMMENT ON COLUMN SYS_CONFIG_GROUP.PARENT_ID IS '父分组ID';
COMMENT ON COLUMN SYS_CONFIG_GROUP.GROUP_TYPE IS '分组层级';
COMMENT ON COLUMN SYS_CONFIG_GROUP.LEAF_CONFIG IS '配置格式json';
COMMENT ON COLUMN SYS_CONFIG_GROUP.SORT IS '排序';
COMMENT ON COLUMN SYS_CONFIG_GROUP.ICON IS '图标';


CREATE TABLE  DEV_MODEL_LATEST (
    ID varchar(36) NOT NULL,
    MODEL_NAME varchar(50),
    SOURCE_NAME varchar(50),
    VERSION_NAME varchar(100),
    VERSION_WHO varchar(100),
    VERSION_TIME varchar(20),
    DESCRIPTION varchar(255),
    DIAGRAM clob,
    INNER_VERSION number DEFAULT 0,
    CUSTOM_TYPE_MAPPING clob,
    APP_ID varchar(36),
    WHO_CREATED varchar(36),
    WHEN_CREATED varchar(20),
    WHO_MODIFIED varchar(36),
    WHEN_MODIFIED varchar(20),
    PRIMARY KEY (ID)
);
COMMENT ON COLUMN DEV_MODEL_LATEST.ID IS '主键ID';
COMMENT ON COLUMN DEV_MODEL_LATEST.MODEL_NAME IS '模型名称';
COMMENT ON COLUMN DEV_MODEL_LATEST.SOURCE_NAME IS '数据源名称';
COMMENT ON COLUMN DEV_MODEL_LATEST.VERSION_NAME IS '修订版本';
COMMENT ON COLUMN DEV_MODEL_LATEST.VERSION_WHO IS '修订人';
COMMENT ON COLUMN DEV_MODEL_LATEST.VERSION_TIME IS '修订时间';
COMMENT ON COLUMN DEV_MODEL_LATEST.DESCRIPTION IS '备注';
COMMENT ON COLUMN DEV_MODEL_LATEST.DIAGRAM IS '模型数据';
COMMENT ON COLUMN DEV_MODEL_LATEST.INNER_VERSION IS '内部版本号，用于服务端保存时校验';
COMMENT ON COLUMN DEV_MODEL_LATEST.CUSTOM_TYPE_MAPPING IS '用户自定义类型映射，当用户导入表出现系统未适配的字段类型时，提醒用户选择要转成什么类型';
COMMENT ON COLUMN DEV_MODEL_LATEST.APP_ID IS '所属应用ID';
COMMENT ON COLUMN DEV_MODEL_LATEST.WHO_CREATED IS '创建人';
COMMENT ON COLUMN DEV_MODEL_LATEST.WHEN_CREATED IS '创建时间';
COMMENT ON COLUMN DEV_MODEL_LATEST.WHO_MODIFIED IS '更新人';
COMMENT ON COLUMN DEV_MODEL_LATEST.WHEN_MODIFIED IS '更新时间';


CREATE TABLE  DEV_PAGE_TEMPLATE_ACTION_LOG (
    ID varchar(36) NOT NULL,
    ACTION_TYPE int NOT NULL,
    IS_COPY_ALL int,
    TEMPLATE_ID varchar(36) NOT NULL,
    APP_ID varchar(36),
    TEAM_ID varchar(36),
    ACTION_CONTENT clob,
    WHO_CREATED varchar(36) NOT NULL,
    WHEN_CREATED varchar(20) NOT NULL,
    PRIMARY KEY (ID)
);
COMMENT ON COLUMN DEV_PAGE_TEMPLATE_ACTION_LOG.ID IS '主键';
COMMENT ON COLUMN DEV_PAGE_TEMPLATE_ACTION_LOG.ACTION_TYPE IS '动作类型；1：模板复制；2：模板预览';
COMMENT ON COLUMN DEV_PAGE_TEMPLATE_ACTION_LOG.IS_COPY_ALL IS '是否全文复制';
COMMENT ON COLUMN DEV_PAGE_TEMPLATE_ACTION_LOG.TEMPLATE_ID IS '数据id，根据action_type指向不同的表';
COMMENT ON COLUMN DEV_PAGE_TEMPLATE_ACTION_LOG.APP_ID IS '关联应用';
COMMENT ON COLUMN DEV_PAGE_TEMPLATE_ACTION_LOG.TEAM_ID IS '关联团队';
COMMENT ON COLUMN DEV_PAGE_TEMPLATE_ACTION_LOG.ACTION_CONTENT IS '动作关联的内容，例如action_type为模板复制时，content就是复制的内容';
COMMENT ON COLUMN DEV_PAGE_TEMPLATE_ACTION_LOG.WHO_CREATED IS '创建人员';
COMMENT ON COLUMN DEV_PAGE_TEMPLATE_ACTION_LOG.WHEN_CREATED IS '创建时间';


CREATE TABLE  DEV_FILE_VERSION (
    ID varchar(36) NOT NULL,
    FILE_NAME varchar(255),
    PATH varchar(1000),
    OS_TYPE varchar(20),
    VERSION varchar(100),
    PATH_BY_PACKAGE varchar(1000),
    FILE_SIZE int,
    DESCRIPTION varchar(255),
    PARENT_PATH varchar(1000),
    WHEN_MODIFIED varchar(30),
    WHO_MODIFIED varchar(36),
    PRIMARY KEY (ID)
);
COMMENT ON COLUMN DEV_FILE_VERSION.ID IS 'ID';
COMMENT ON COLUMN DEV_FILE_VERSION.FILE_NAME IS '文件名';
COMMENT ON COLUMN DEV_FILE_VERSION.PATH IS '导致安装包的位置';
COMMENT ON COLUMN DEV_FILE_VERSION.OS_TYPE IS '操作系统类型';
COMMENT ON COLUMN DEV_FILE_VERSION.VERSION IS '版本号（vX.X.X结构）';
COMMENT ON COLUMN DEV_FILE_VERSION.PATH_BY_PACKAGE IS '所在package中的真实位置';
COMMENT ON COLUMN DEV_FILE_VERSION.FILE_SIZE IS '文件大小';
COMMENT ON COLUMN DEV_FILE_VERSION.DESCRIPTION IS '描述';
COMMENT ON COLUMN DEV_FILE_VERSION.PARENT_PATH IS '父目录';
COMMENT ON COLUMN DEV_FILE_VERSION.WHEN_MODIFIED IS '更新时间';
COMMENT ON COLUMN DEV_FILE_VERSION.WHO_MODIFIED IS '更新人';


-- 以下是来自 version_50_1.sql 的内容 --

ALTER TABLE OPEN_ACCOUNT ADD APP_ID varchar(36);

ALTER TABLE SYS_NOTICE ADD IS_FORCE int;
COMMENT ON COLUMN SYS_NOTICE.IS_FORCE IS '是否重要 0: 否 1：是';

ALTER TABLE SYS_NOTICE ADD CONFIG clob;
COMMENT ON COLUMN SYS_NOTICE.CONFIG IS '团队头像';

ALTER TABLE DEV_APPLICATION ADD DEPEND_DATASOURCES varchar2(2048 char);
COMMENT ON COLUMN DEV_APPLICATION.DEPEND_DATASOURCES IS '依赖数据源';

ALTER TABLE DEV_APPLICATION ADD DEPEND_APPS varchar2(2048 char);
COMMENT ON COLUMN DEV_APPLICATION.DEPEND_APPS IS '依赖应用';

ALTER TABLE DEV_APPLICATION ADD APP_NAMESPACE varchar(255);
COMMENT ON COLUMN DEV_APPLICATION.APP_NAMESPACE IS '应用命名空间';

ALTER TABLE KFAAS_LIB ADD ID varchar(36) NOT NULL;
ALTER TABLE KFAAS_LIB ADD CREATE_TIME varchar(20);
ALTER TABLE KFAAS_LIB ADD CREATE_USER varchar(255);
ALTER TABLE KFAAS_LIB ADD JAR_NAME varchar(255);
ALTER TABLE KFAAS_LIB ADD UPDATE_TIME varchar(20);
ALTER TABLE KFAAS_LIB ADD UPDATE_USER varchar(255);
ALTER TABLE KFAAS_LIB DROP COLUMN jarname;
ALTER TABLE KFAAS_LIB DROP COLUMN createtime;
ALTER TABLE KFAAS_LIB DROP COLUMN updatetime;
ALTER TABLE KFAAS_LIB DROP COLUMN createuser;
ALTER TABLE KFAAS_LIB DROP COLUMN updateuser;
ALTER TABLE KFAAS_LIB MODIFY STATUS int;

ALTER TABLE REP_DATASET ADD DATASET_SEARCH_ID varchar(32);
COMMENT ON COLUMN REP_DATASET.DATASET_SEARCH_ID IS '是否为搜索数据';

ALTER TABLE REP_DATASET ADD SHAPE int;
COMMENT ON COLUMN REP_DATASET.SHAPE IS '是否为自定义SQL';

ALTER TABLE REP_DATASET ADD TEMPLATE int;
COMMENT ON COLUMN REP_DATASET.TEMPLATE IS '是否为Excel模板（0：否，1：是）';

ALTER TABLE REP_DATASET ADD REP_CRON varchar(50);
COMMENT ON COLUMN REP_DATASET.REP_CRON IS '模板报表定时任务Cron表达式';

-- ALTER TABLE SYS_AUTH_SOURCE ADD WHOCREATED varchar(36);

ALTER TABLE DEV_PAGE_TEMPLATE ADD VIEW_NUM int DEFAULT 0;
COMMENT ON COLUMN DEV_PAGE_TEMPLATE.VIEW_NUM IS '预览量';

ALTER TABLE DEV_PAGE_TEMPLATE ADD COPY_NUM int DEFAULT 0;
COMMENT ON COLUMN DEV_PAGE_TEMPLATE.COPY_NUM IS '被复制的次数；copy_num = copy_all_num+copy_part_num';

ALTER TABLE DEV_PAGE_TEMPLATE ADD COPY_ALL_NUM int DEFAULT 0;
COMMENT ON COLUMN DEV_PAGE_TEMPLATE.COPY_ALL_NUM IS '被全量复制的次数';

ALTER TABLE DEV_PAGE_TEMPLATE ADD COPY_PART_NUM int DEFAULT 0;
COMMENT ON COLUMN DEV_PAGE_TEMPLATE.COPY_PART_NUM IS '被部分复制的次数';

ALTER TABLE OPEN_API_LOG ADD API_ID varchar(36);
COMMENT ON COLUMN OPEN_API_LOG.API_ID IS '接口id';

ALTER TABLE SYS_LOGIC_FLOW ADD I18N_KEYS clob;
COMMENT ON COLUMN SYS_LOGIC_FLOW.I18N_KEYS IS '国际化键名';

ALTER TABLE SYS_UNIT ADD UNIT_CODE varchar(100);
COMMENT ON COLUMN SYS_UNIT.UNIT_CODE IS '机构编码';

ALTER TABLE SYS_CONFIG ADD GROUP_ID varchar(36);
COMMENT ON COLUMN SYS_CONFIG.GROUP_ID IS '关联组';

ALTER TABLE SYS_INSTANCE ADD CLUSTER_NO int;
COMMENT ON COLUMN SYS_INSTANCE.CLUSTER_NO IS '集群号';

ALTER TABLE DEV_SQL_SCRIPT ADD ID varchar(36) NOT NULL;


-- 以下是来自 version_51_1.sql 的内容 --

CREATE TABLE dev_seats (
       id varchar2(100 char) NOT NULL,
       node_id varchar2(100 char) DEFAULT NULL,
       num varchar2(50 char) DEFAULT NULL,
       type varchar2(50 char) DEFAULT NULL,
       status varchar2(50 char) DEFAULT NULL,
       user_id varchar2(50 char) DEFAULT NULL,
       floor_id varchar2(50 char) DEFAULT NULL,
       description varchar2(200 char) DEFAULT NULL,
       when_created varchar2(100 char) DEFAULT NULL,
       when_modified varchar2(100 char) DEFAULT NULL,
       who_created varchar2(255 char) DEFAULT NULL,
       who_modified varchar2(255 char) DEFAULT NULL,
       PRIMARY KEY (id)
);


CREATE TABLE dev_floors (
        id varchar2(100 char) NOT NULL,
        name varchar2(255 char) DEFAULT NULL,
        type varchar2(50 char) DEFAULT NULL,
        status varchar2(50 char) DEFAULT NULL,
        description varchar2(200 char) DEFAULT NULL,
        flow_id varchar2(100 char) DEFAULT NULL,
        when_created varchar2(100 char) DEFAULT NULL,
        when_modified varchar2(100 char) DEFAULT NULL,
        who_created varchar2(255 char) DEFAULT NULL,
        who_modified varchar2(255 char) DEFAULT NULL,
        PRIMARY KEY (id)
);
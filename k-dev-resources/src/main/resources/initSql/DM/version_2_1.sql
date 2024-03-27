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
    ID           VARCHAR2(255 char) not null,
    NAME         VARCHAR2(255 char),
    PATH         VARCHAR2(255 char),
    CONTENT      CLOB,
    PARENT_ID    VARCHAR2(255 char),
    "ORDER"      NUMBER             not null
        constraint IND_CB324EE3A9A32AA8
            primary key,
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
    content     text                                ,
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
    description text                              ,
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
    API_NAME           VARCHAR2(50 char),
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
    NAME          VARCHAR2(50 char),
    CODE          VARCHAR2(50 char),
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
    NAME          VARCHAR2(50 char),
    GROUP_NAME    VARCHAR2(50 char),
    SYS_DICT_ID   VARCHAR2(36 char),
    CODE          VARCHAR2(50 char),
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
    APP_ID             VARCHARpom2(36 char)
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
    NAME             VARCHAR2(50 char)        not null,
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
    READ_TIME     VARCHAR2(20 char),
    NOTICE_TIME   VARCHAR2(20 char)   not null,
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
    NAME          VARCHAR2(50 char) not null,
    CODE          VARCHAR2(50 char) not null,
    NOTE          CLOB,
    STATUS        NUMBER(3),
    WHO_CREATED   VARCHAR2(36 char) not null,
    WHEN_CREATED  VARCHAR2(20 char) not null,
    WHO_MODIFIED  VARCHAR2(36 char) not null,
    WHEN_MODIFIED VARCHAR2(20 char) not null,
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
    NAME          VARCHAR2(50 char)   not null,
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
    USERNAME      VARCHAR2(50 char) not null,
    PASSWORD      VARCHAR2(256 char),
    REAL_NAME     VARCHAR2(50 char) not null,
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
    template      varchar(10240) ,
    icon          varchar(32)   ,
    pub_status    int           ,
    order_num     varchar(255)   ,
    when_created  varchar(255)   ,
    who_created   varchar(255)   ,
    when_modified varchar(255)   ,
    who_modified  varchar(255)   ,
    config        text        ,
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


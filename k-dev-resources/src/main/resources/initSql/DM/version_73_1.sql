

-- 以下是来自 version_73_1.sql ,4.1AI库需要的表 的内容 --



CREATE TABLE DEV_CHAT_SESSION (
                                  SESSION_ID     VARCHAR2(255 CHAR) NOT NULL
                                      CONSTRAINT PK_CHAT_SESSION PRIMARY KEY,
                                  USER_ID        VARCHAR2(255 CHAR) NOT NULL,
                                  TITLE          VARCHAR2(255 CHAR),
                                  IS_ARCHIVED    NUMBER(1) DEFAULT 0,
                                  WHEN_CREATED   VARCHAR2(30 CHAR),
                                  WHEN_MODIFIED  VARCHAR2(30 CHAR)
);

CREATE TABLE DEV_CHAT_MESSAGE (
                                  MESSAGE_ID       VARCHAR2(36 CHAR) NOT NULL
                                      CONSTRAINT PK_CHAT_MESSAGE PRIMARY KEY,
                                  SESSION_ID       VARCHAR2(255 CHAR) NOT NULL,
                                  SENDER           VARCHAR2(10 CHAR) NOT NULL,
                                  MESSAGE_CONTENT  CLOB NOT NULL,
                                  MESSAGE_TYPE     VARCHAR2(20 CHAR) DEFAULT 'text',
                                  ARGS             CLOB,
                                  WHEN_CREATED     VARCHAR2(30 CHAR),
                                  WHEN_MODIFIED    VARCHAR2(30 CHAR)
);

CREATE TABLE DEV_ONLINE_USER (
                                 ID              VARCHAR2(36 CHAR) NOT NULL
                                     CONSTRAINT PK_DEV_ONLINE_USER PRIMARY KEY,
                                 TAB_ID          VARCHAR2(36 CHAR) NOT NULL,
                                 USER_ID         VARCHAR2(36 CHAR) NOT NULL,
                                 USER_NAME       VARCHAR2(50 CHAR),
                                 AVATAR          VARCHAR2(255 CHAR),
                                 PAGE_ID         VARCHAR2(36 CHAR) NOT NULL,
                                 LAST_HEARTBEAT  VARCHAR2(30 CHAR),
                                 WHEN_CREATED    VARCHAR2(30 CHAR),
                                 WHEN_MODIFIED   VARCHAR2(30 CHAR)
);

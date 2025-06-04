

-- 以下是来自 version_73_1.sql ,4.1AI库需要的表 的内容 --

CREATE TABLE IF NOT EXISTS "dev_chat_session" (
    "session_id" varchar(255) COLLATE "pg_catalog"."default" NOT NULL,
    "user_id" varchar(255) COLLATE "pg_catalog"."default" NOT NULL,
    "title" varchar(255) COLLATE "pg_catalog"."default",
    "is_archived" int8 DEFAULT 0,
    "when_created" varchar(30) COLLATE "pg_catalog"."default",
    "when_modified" varchar(30) COLLATE "pg_catalog"."default",
    PRIMARY KEY ("session_id")
    );

CREATE TABLE IF NOT EXISTS "dev_chat_message" (
    "message_id" varchar(36) COLLATE "pg_catalog"."default" NOT NULL,
    "session_id" varchar(255) COLLATE "pg_catalog"."default" NOT NULL,
    "sender" varchar(10) COLLATE "pg_catalog"."default" NOT NULL,
    "message_content" text COLLATE "pg_catalog"."default" NOT NULL,
    "message_type" varchar(20) COLLATE "pg_catalog"."default" DEFAULT 'text',
    "args" text COLLATE "pg_catalog"."default",
    "when_created" varchar(30) COLLATE "pg_catalog"."default",
    "when_modified" varchar(30) COLLATE "pg_catalog"."default",
    PRIMARY KEY ("message_id")
    );
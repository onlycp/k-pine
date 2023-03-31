CREATE TABLE IF NOT EXISTS "sys_logic_history" (
                                              "id" varchar(36) COLLATE "pg_catalog"."default" NOT NULL,
                                              "flow_id" varchar(36) COLLATE "pg_catalog"."default",
                                              "flow_json" text COLLATE "pg_catalog"."default",
                                              "when_created" timestamp(6),
                                              "who_created" varchar(36) COLLATE "pg_catalog"."default",
                                              CONSTRAINT "sys_logic_history_pk_" PRIMARY KEY ("id")
)
;

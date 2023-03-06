CREATE TABLE  "sys_logic_history" (
    "id" varchar(36)  NOT NULL,
    "flow_id" varchar(36)  DEFAULT NULL,
    "flow_json" text ,
    "when_created" timestamp NULL ,
    "who_created" varchar(36)  DEFAULT NULL ,
    PRIMARY KEY ("id")
    );
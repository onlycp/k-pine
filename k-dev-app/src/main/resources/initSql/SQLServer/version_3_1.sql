CREATE TABLE  "open_account" (
    "id" varchar(36) NOT NULL,
    "access_name" varchar(128),
    "access_id" varchar(36)  DEFAULT NULL,
    "auth_type" int DEFAULT NULL,
    "sign_key" varchar(50) ,
    "validate_sign" int ,
    "valid_date" varchar(20) ,
    "invalid_date" varchar(20)  ,
    "status" int DEFAULT '1',
    "auth_params" text,
    "who_created" varchar(36)  DEFAULT NULL ,
    "when_created" varchar(20)  DEFAULT NULL,
    "who_modified" varchar(36)  DEFAULT NULL ,
    "when_modified" varchar(20)  DEFAULT NULL,
    PRIMARY KEY ("id")
);

CREATE TABLE  "open_account_api" (
    "id" varchar(36)  NOT NULL ,
    "account_id" varbinary(32) DEFAULT NULL ,
    "api_id" varchar(36)  DEFAULT NULL ,
    "when_created" varchar(20)  DEFAULT NULL ,
    "who_created" varchar(36)  DEFAULT NULL ,
    PRIMARY KEY ("id")
);
CREATE TABLE "dev_power_link" (
    "id" varchar(36)  NOT NULL,
    "tree_id" varchar(36)  DEFAULT NULL ,
    "power_id" varchar(36)  DEFAULT NULL ,
    "power_type" int DEFAULT NULL ,
    "who_created" varchar(36)  DEFAULT NULL ,
    "when_created" varchar(20)  DEFAULT NULL,
    PRIMARY KEY ("id")
);
CREATE TABLE  "dev_power_tree" (
    "id" varchar(36)  NOT NULL,
    "name" varchar(128)  DEFAULT NULL ,
    "parent_id" varchar(36)  DEFAULT NULL ,
    "note" varchar(255)  DEFAULT NULL,
    "who_created" varchar(36)  DEFAULT NULL ,
    "when_created" varchar(20)  DEFAULT NULL,
    "who_modified" varchar(36)  DEFAULT NULL,
    "when_modified" varchar(20)  DEFAULT NULL,
    "path" text ,
    PRIMARY KEY ("id")
);
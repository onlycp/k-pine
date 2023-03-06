CREATE TABLE "open_api_log" (
                                "id" varchar(36)  NOT NULL,
                                "access_id" varchar(100)  DEFAULT NULL,
                                "api_name" varchar(100)  DEFAULT NULL,
                                "request_params" text,
                                "request_time" varchar(20)  DEFAULT NULL,
                                "request_ip" varchar(20)  DEFAULT NULL,
                                "use_time" tinyint DEFAULT NULL,
                                "success" tinyint DEFAULT NULL,
                                "error_message" varchar(255)  DEFAULT NULL,
                                PRIMARY KEY ("id")
);
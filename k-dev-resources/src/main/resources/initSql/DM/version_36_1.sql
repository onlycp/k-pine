CREATE TABLE if not exists dev_plugin_api (
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

CREATE TABLE  if not exists dev_plugin_group (
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



CREATE TABLE if not exists dev_plugin_operation (
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



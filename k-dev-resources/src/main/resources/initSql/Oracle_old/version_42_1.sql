
CREATE TABLE sys_password_log
(
    id VARCHAR2 (36 CHAR) NOT NULL,
    user_id VARCHAR2 (36 CHAR) DEFAULT NULL,
    when_created VARCHAR2 (20 CHAR) DEFAULT NULL,
    PRIMARY KEY (id)
);

CREATE TABLE sys_secret_rule
(
    id VARCHAR2 (36 CHAR) NOT NULL,
    name VARCHAR2 (255 CHAR) DEFAULT NULL,
    logic_id VARCHAR2 (36 CHAR) DEFAULT NULL,
    secret_type NUMBER (8) DEFAULT 0,
    status NUMBER (8) DEFAULT NULL,
    order_num NUMBER (11) DEFAULT NULL,
    notes CLOB DEFAULT NULL,
    when_created VARCHAR2 (20 CHAR) DEFAULT NULL,
    who_created VARCHAR2 (36 CHAR) DEFAULT NULL,
    when_modified VARCHAR2 (20 CHAR) DEFAULT NULL,
    who_modified VARCHAR2 (36 CHAR) DEFAULT NULL,
    PRIMARY KEY (id)
);

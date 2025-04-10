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
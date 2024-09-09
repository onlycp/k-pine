CREATE TABLE dev_curd (
                          id VARCHAR2(36 CHAR) NOT NULL,
                          name VARCHAR2(255 CHAR),
                          group_id VARCHAR2(36 CHAR),
                          source_name VARCHAR2(255 CHAR),
                          table_name VARCHAR2(255 CHAR),
                          primary_name VARCHAR2(50 CHAR),
                          request_prefix VARCHAR2(255 CHAR),
                          enable_funs VARCHAR2(255 CHAR),
                          create_funs VARCHAR2(255 CHAR),
                          column_json CLOB,
                          app_id VARCHAR2(36 CHAR),
                          who_created VARCHAR2(36 CHAR),
                          when_created VARCHAR2(20 CHAR),
                          who_modified VARCHAR2(36 CHAR),
                          when_modified VARCHAR2(255 CHAR),
                          PRIMARY KEY (id)
);

INSERT INTO dev_team (id, deleted, description, name, owner, when_created, when_modified, who_created, who_modified) VALUES('991718335d57416a9be67d4090538402', 0, NULL, '默认团队', '8116f0bc8222413fb72de98a32960b1a', '2022-09-28 14:52:00', '2022-09-28 14:52:00', '056fb0eeb9a44cb0953534b4c0ca01fa', '056fb0eeb9a44cb0953534b4c0ca01fa');
INSERT INTO dev_team_member (id, app_id, is_owner, team_role_id, user_id, when_join, who_invite, team_id) VALUES('dfae64f660ff435086ae2482d7fa1a48', NULL, 0, '3fc43c9c69f44144bd032d9451ba328b', '8116f0bc8222413fb72de98a32960b1a', '2022-09-28 14:52:00', '056fb0eeb9a44cb0953534b4c0ca01fa', '991718335d57416a9be67d4090538402');
INSERT INTO dev_team_member (id, app_id, is_owner, team_role_id, user_id, when_join, who_invite, team_id) VALUES('dfae64f660ff435086ae2482d7fa1a49', NULL, 1, '4a30f4d346074b4ba8363944f004c1d9', '056fb0eeb9a44cb0953534b4c0ca01fa', '2022-09-28 14:52:00', '056fb0eeb9a44cb0953534b4c0ca01fa', '991718335d57416a9be67d4090538402');

CREATE TABLE sys_logic_template_user (
     id varchar(36) NOT NULL,
     app_id varchar(36) DEFAULT NULL,
     when_created varchar(50) DEFAULT NULL,
     who_created varchar(36) DEFAULT NULL,
     template_id varchar(36) DEFAULT NULL,
     PRIMARY KEY (id)
);

CREATE TABLE dev_page_template (
                                   id VARCHAR2(36 CHAR) NOT NULL,
                                   when_created VARCHAR2(50 CHAR) DEFAULT NULL,
                                   when_modified VARCHAR2(50 CHAR) DEFAULT NULL,
                                   who_created VARCHAR2(36 CHAR) DEFAULT NULL,
                                   who_modified VARCHAR2(36 CHAR) DEFAULT NULL,
                                   deleted NUMBER(1) DEFAULT 0,
                                   app_id VARCHAR2(36 CHAR) DEFAULT NULL,
                                   name VARCHAR2(255 CHAR) DEFAULT NULL,
                                   description CLOB,
                                   app_type VARCHAR2(100 CHAR) DEFAULT NULL,
                                   page_json CLOB,
                                   tags VARCHAR2(255 CHAR) DEFAULT NULL,
                                   module_id VARCHAR2(36 CHAR) DEFAULT NULL,
                                   snapshot_img_id VARCHAR2(32 CHAR) DEFAULT NULL,
                                   order_num NUMBER(11) DEFAULT NULL,
                                   bg_colors VARCHAR2(64 CHAR) DEFAULT NULL,
                                   page_type VARCHAR2(32 CHAR) DEFAULT NULL,
                                   use_num NUMBER(11) DEFAULT 0,
                                   extra CLOB,
                                   view_num NUMBER(11) DEFAULT 0,
                                   copy_num NUMBER(11) DEFAULT 0,
                                   copy_all_num NUMBER(11) DEFAULT 0,
                                   copy_part_num NUMBER(11) DEFAULT 0,
                                   PRIMARY KEY (id)
);

alter table sys_logic_template add sys_suggested integer null;
alter table sys_logic_template add publish_status integer null;

INSERT INTO dev_team (id, deleted, description, name, owner, when_created, when_modified, who_created, who_modified) VALUES('991718335d57416a9be67d4090538402', 0, NULL, '默认团队', '8116f0bc8222413fb72de98a32960b1a', '2022-09-28 14:52:00', '2022-09-28 14:52:00', '056fb0eeb9a44cb0953534b4c0ca01fa', '056fb0eeb9a44cb0953534b4c0ca01fa');
INSERT INTO dev_team_member (id, app_id, is_owner, team_role_id, user_id, when_join, who_invite, team_id) VALUES('dfae64f660ff435086ae2482d7fa1a48', NULL, 0, '3fc43c9c69f44144bd032d9451ba328b', '8116f0bc8222413fb72de98a32960b1a', '2022-09-28 14:52:00', '056fb0eeb9a44cb0953534b4c0ca01fa', '991718335d57416a9be67d4090538402');
INSERT INTO dev_team_member (id, app_id, is_owner, team_role_id, user_id, when_join, who_invite, team_id) VALUES('dfae64f660ff435086ae2482d7fa1a49', NULL, 1, '4a30f4d346074b4ba8363944f004c1d9', '056fb0eeb9a44cb0953534b4c0ca01fa', '2022-09-28 14:52:00', '056fb0eeb9a44cb0953534b4c0ca01fa', '991718335d57416a9be67d4090538402');

CREATE TABLE  sys_logic_template_user (
                                         id varchar(36) NOT NULL,
                                         app_id varchar(36) DEFAULT NULL,
                                         when_created varchar(50) DEFAULT NULL,
                                         who_created varchar(36) DEFAULT NULL,
                                         template_id varchar(36) DEFAULT NULL,
                                         PRIMARY KEY (id)
);
CREATE TABLE  dev_curd (
    id varchar(36)  NOT NULL ,
    name varchar(255)  DEFAULT null,
    group_id varchar(36)  DEFAULT NULL,
    source_name varchar(255)  DEFAULT NULL,
    table_name varchar(255)  DEFAULT NULL,
    primary_name varchar(50)  DEFAULT NULL,
    request_prefix varchar(255)  DEFAULT NULL,
    enable_funs varchar(255)  DEFAULT NULL,
    create_funs varchar(255)  DEFAULT NULL,
    column_json clob ,
    app_id varchar(36)  DEFAULT NULL,
    who_created varchar(36)  DEFAULT NULL,
    when_created varchar(20)  DEFAULT NULL,
    who_modified varchar(36)  DEFAULT NULL,
    when_modified varchar(255)  DEFAULT NULL ,
    PRIMARY KEY (id)
    );

ALTER TABLE dev_page_template ADD snapshot_img_id VARCHAR(32) NULL;
ALTER TABLE dev_page_template ADD order_num integer NULL  ;
ALTER TABLE dev_page_template ADD bg_colors VARCHAR(64) NULL;
ALTER TABLE dev_page_template ADD page_type VARCHAR(32) NULL;
ALTER TABLE dev_page_template ADD use_num integer DEFAULT 0 NULL;
ALTER TABLE dev_page_template ADD extra CLOB NULL;


alter table sys_logic_template add sys_suggested integer null;
alter table sys_logic_template add publish_status integer null;


CREATE TABLE  dev_page_template_history (
    id varchar(36) NOT NULL,
    tpl_id varchar(36) DEFAULT NULL,
    page_json CLOB,
    when_created varchar(50) NULL DEFAULT NULL,
    who_created varchar(36) DEFAULT NULL,
    version_tag varchar(50) DEFAULT NULL,
    version_tag_time varchar(30) DEFAULT NULL,
    app_id varchar(36) DEFAULT NULL,
    PRIMARY KEY (id)
    );


CREATE TABLE  dev_chat_history (
    id varchar(36)  NOT NULL,
    question CLOB  NOT NULL,
    answer CLOB ,
    args CLOB,
    when_created varchar(50)  DEFAULT NULL,
    who_created varchar(36)  DEFAULT NULL,
    PRIMARY KEY (id)
    );


-- 修改 dev_power_tree 表
ALTER TABLE dev_power_tree ADD order_num integer NULL;

-- 修改 sys_logic_template 表
ALTER TABLE sys_logic_template ADD order_num integer NULL;
ALTER TABLE sys_logic_template ADD enable_status integer NULL;
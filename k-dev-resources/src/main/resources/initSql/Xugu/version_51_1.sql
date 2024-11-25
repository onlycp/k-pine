INSERT INTO dev_team (id, deleted, description, name, owner, when_created, when_modified, who_created, who_modified)
VALUES ('991718335d57416a9be67d4090538402', 0, NULL, '默认团队', '8116f0bc8222413fb72de98a32960b1a',
        '2022-09-28 14:52:00', '2022-09-28 14:52:00', '056fb0eeb9a44cb0953534b4c0ca01fa',
        '056fb0eeb9a44cb0953534b4c0ca01fa');

INSERT INTO dev_team_member (id, app_id, is_owner, team_role_id, user_id, when_join, who_invite, team_id)
VALUES ('dfae64f660ff435086ae2482d7fa1a48', NULL, 0, '3fc43c9c69f44144bd032d9451ba328b',
        '8116f0bc8222413fb72de98a32960b1a', '2022-09-28 14:52:00', '056fb0eeb9a44cb0953534b4c0ca01fa',
        '991718335d57416a9be67d4090538402');

INSERT INTO dev_team_member (id, app_id, is_owner, team_role_id, user_id, when_join, who_invite, team_id)
VALUES ('dfae64f660ff435086ae2482d7fa1a49', NULL, 1, '4a30f4d346074b4ba8363944f004c1d9',
        '056fb0eeb9a44cb0953534b4c0ca01fa', '2022-09-28 14:52:00', '056fb0eeb9a44cb0953534b4c0ca01fa',
        '991718335d57416a9be67d4090538402');



CREATE INDEX idx_sys_operate_log_operate_time ON sys_operate_log (operate_time);
CREATE INDEX sys_operate_log_action_IDX ON sys_operate_log (action);


CREATE INDEX dev_team_app_app_id ON dev_team_app (app_id);

CREATE INDEX dev_team_app_team_id ON dev_team_app (team_id);

CREATE UNIQUE INDEX idx_username_uindex ON sys_user (username);


ALTER TABLE dev_power_tree
    ADD COLUMN order_num INT DEFAULT NULL COMMENT '排序';

ALTER TABLE sys_logic_template
    ADD COLUMN order_num INT DEFAULT NULL COMMENT '排序';

ALTER TABLE sys_logic_template
    ADD COLUMN enable_status INT DEFAULT 1 COMMENT '可用状态';



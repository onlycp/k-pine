CREATE TABLE IF NOT EXISTS sys_password_log (
                                                id VARCHAR(36) PRIMARY KEY,
                                                user_id VARCHAR(36),
                                                when_created VARCHAR(20)
);

COMMENT ON COLUMN sys_password_log.id IS '主键';
COMMENT ON COLUMN sys_password_log.user_id IS '用户id';
COMMENT ON COLUMN sys_password_log.when_created IS '创建时间';

CREATE TABLE IF NOT EXISTS sys_secret_rule (
                                               id VARCHAR(36) PRIMARY KEY,
                                               name VARCHAR(255),
                                               logic_id VARCHAR(36),
                                               secret_type INTEGER DEFAULT 0,
                                               status INTEGER,
                                               order_num INTEGER,
                                               notes TEXT,
                                               when_created VARCHAR(20),
                                               who_created VARCHAR(36),
                                               when_modified VARCHAR(20),
                                               who_modified VARCHAR(36)
);

COMMENT ON COLUMN sys_secret_rule.id IS '主键';
COMMENT ON COLUMN sys_secret_rule.name IS '规则名称';
COMMENT ON COLUMN sys_secret_rule.logic_id IS '逻辑编排ID';
COMMENT ON COLUMN sys_secret_rule.secret_type IS '安全类型';
COMMENT ON COLUMN sys_secret_rule.status IS '是否启用';
COMMENT ON COLUMN sys_secret_rule.order_num IS '排序';
COMMENT ON COLUMN sys_secret_rule.notes IS '说明';
COMMENT ON COLUMN sys_secret_rule.when_created IS '创建时间';
COMMENT ON COLUMN sys_secret_rule.who_created IS '创建人';
COMMENT ON COLUMN sys_secret_rule.when_modified IS '更新时间';
COMMENT ON COLUMN sys_secret_rule.who_modified IS '更新人';

-- ----------------------------
-- Table structure for open_account 开放接口账户
-- ----------------------------
CREATE TABLE IF NOT EXISTS `open_account`
(
    `id`            varchar(36)  NOT NULL ,
    `access_name`   varchar(128)  DEFAULT NULL ,
    `access_id`     varchar(36)  DEFAULT NULL ,
    `auth_type`     int(4) DEFAULT NULL ,
    `sign_key`      varchar(50)  DEFAULT NULL ,
    `validate_sign` int(4) DEFAULT '0' ,
    `valid_date`    varchar(20)  DEFAULT NULL ,
    `invalid_date`  varchar(20)  DEFAULT NULL ,
    `status`        int(4) DEFAULT '1',
    `auth_params`   text  ,
    `who_created`   varchar(36)  DEFAULT NULL ,
    `when_created`  varchar(20)  DEFAULT NULL ,
    `who_modified`  varchar(36)  DEFAULT NULL ,
    `when_modified` varchar(20)  DEFAULT NULL ,
    PRIMARY KEY (`id`)
    );
-- ----------------------------
-- Table structure for open_account_api 开放接口
-- ----------------------------
CREATE TABLE IF NOT EXISTS `open_account_api`
(
    `id`           varchar(36)  NOT NULL ,
    `account_id`   varchar (32) DEFAULT NULL ,
    `api_id`       varchar(36)  DEFAULT NULL ,
    `when_created` varchar(20)  DEFAULT NULL ,
    `who_created`  varchar(36)  DEFAULT NULL ,
    PRIMARY KEY (`id`)
    );
-- ----------------------------
-- Table structure for dev_power_link 能力链接
-- ----------------------------
CREATE TABLE IF NOT EXISTS `dev_power_link`
(
    `id`           varchar(36)  NOT NULL ,
    `tree_id`      varchar(36)  DEFAULT NULL,
    `power_id`     varchar(36)  DEFAULT NULL ,
    `power_type`   int(4) DEFAULT NULL ,
    `who_created`  varchar(36)  DEFAULT NULL,
    `when_created` varchar(20)  DEFAULT NULL ,
    PRIMARY KEY (`id`)
    ) ;
-- ----------------------------
-- Table structure for dev_power_tree 能力树
-- ----------------------------
CREATE TABLE IF NOT EXISTS `dev_power_tree`
(
    `id`            varchar(36)  NOT NULL ,
    `name`          varchar(128)  DEFAULT NULL,
    `parent_id`     varchar(36)  DEFAULT NULL ,
    `note`          varchar(255)  DEFAULT NULL ,
    `who_created`   varchar(36)  DEFAULT NULL,
    `when_created`  varchar(20)  DEFAULT NULL,
    `who_modified`  varchar(36)  DEFAULT NULL ,
    `when_modified` varchar(20)  DEFAULT NULL,
    `path`          text  COMMENT '树路径',
    PRIMARY KEY (`id`)
    );
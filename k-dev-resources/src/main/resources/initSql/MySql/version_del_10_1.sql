create table IF NOT EXISTS  dev_ota_channel
(
    id            varchar(36)   not null comment 'ID',
    channel_name  varchar(50)   null comment '通道名称',
    channel_url   varchar(100)  null comment '服务器地址',
    auth_token    varchar(50)   null comment '安全令牌',
    sign_secret   varchar(50)   null comment '签名密钥',
    master        int default 0 null comment '是否主通道',
    note          varchar(255)  null comment '备注信息',
    when_created  varchar(20)   null comment '创建时间',
    who_created   varchar(36)   null comment '创建人',
    when_modified varchar(20)   null comment '修改时间',
    who_modified  varchar(36)   null comment '修改人员',
    constraint dev_ota_channel_pk
        primary key (id)
)
    comment 'OTA通道 ';

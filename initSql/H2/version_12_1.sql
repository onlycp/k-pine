create table IF NOT EXISTS  dev_ota_channel
(
    id            varchar(36)   not null ,
    channel_name  varchar(50)   null,
    channel_url   varchar(100)  null,
    auth_token    varchar(50)   null,
    sign_secret   varchar(50)   null,
    master        int default 0 null,
    note          varchar(255)  null,
    when_created  varchar(20)   null,
    who_created   varchar(36)   null,
    when_modified varchar(20)   null,
    who_modified  varchar(36)   null,
    PRIMARY KEY (`id`)
)
    comment 'OTA通道 ';

alter table SYS_OPERATE_LOG
    add METHOD varchar(255) null;
alter table SYS_OPERATE_LOG
    add REQUEST_METHOD varchar(255) null;
alter table SYS_OPERATE_LOG
    add RESPONSE_BODY text null;


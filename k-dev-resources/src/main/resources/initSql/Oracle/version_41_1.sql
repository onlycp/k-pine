alter table DEV_APPLICATION modify  SHORT_NAME varchar2(200) ;

alter table dev_team add image VARCHAR2(255 CHAR) null;
alter table dev_team add is_audit integer null default 0;

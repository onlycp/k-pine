ALTER TABLE "dev_application" ALTER COLUMN "short_name" TYPE varchar(200);
alter table sys_logic_flow add i18n_keys text null;
ALTER TABLE dev_team
    ADD COLUMN is_audit int NULL,
    ADD COLUMN image varchar(255) NULL;

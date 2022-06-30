
ALTER TABLE "dev_application_version_history"
    ADD COLUMN "file_name" VARCHAR(255) null
    ;
ALTER TABLE "dev_application_version_history"
    ADD COLUMN "note" VARCHAR(255) null
;
ALTER TABLE "dev_application_version_history"
    ADD COLUMN "export_data" text null
;
ALTER TABLE "sys_logic_flow"
    ADD COLUMN "default_source_name" VARCHAR(100) null
;


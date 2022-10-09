ALTER TABLE "dev_application_version_history" ADD "file_name" varchar(255)  NULL;

ALTER TABLE "dev_application_version_history" ADD "note" varchar(255)  NULL;

ALTER TABLE "dev_application_version_history" ADD "export_data" text ;

ALTER TABLE "sys_logic_flow" ADD  "default_source_name" varchar(100) null ;
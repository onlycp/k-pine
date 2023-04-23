
ALTER TABLE "rep_dataset"
    ALTER COLUMN "ds_type" TYPE varchar(32) USING "ds_type"::varchar(32);
ALTER TABLE "wf_ext_category"
    ALTER COLUMN "order_num" TYPE varchar(16) USING "order_num"::varchar(16);

ALTER TABLE "dev_ota_channel"
    ALTER COLUMN "master" TYPE varchar(32) USING "master"::varchar(32);
ALTER TABLE "sys_logic_history"
    ALTER COLUMN "when_created" TYPE varchar(20) USING "when_created"::varchar(20);
ALTER TABLE "ext_plugin_tree" RENAME COLUMN "extName" TO "ext_name";

ALTER TABLE "ext_plugin_tree" RENAME COLUMN "jarName" TO "jar_name";

ALTER TABLE "ext_plugin_tree" RENAME COLUMN "createTime" TO "create_time";

ALTER TABLE "ext_plugin_tree" RENAME COLUMN "updateTime" TO "update_time";

ALTER TABLE "ext_plugin_tree" RENAME COLUMN "createUser" TO "create_user";

ALTER TABLE "ext_plugin_tree" RENAME COLUMN "updateUser" TO "update_user";

ALTER TABLE "ext_plugin_tree" RENAME COLUMN "checkTime" TO "check_time";

ALTER TABLE "ext_plugin_interface" RENAME COLUMN "pluginId" TO "plugin_id";

ALTER TABLE "ext_plugin_interface" RENAME COLUMN "createTime" TO "create_time";

ALTER TABLE "ext_plugin_interface" RENAME COLUMN "createUser" TO "create_user";

ALTER TABLE "ext_plugin_interface" RENAME COLUMN "updateTime" TO "update_time";

ALTER TABLE "ext_plugin_interface" RENAME COLUMN "updateuser" TO "update_user";
ALTER TABLE "ext_plugin_interface" ADD COLUMN "deleted" int4 DEFAULT 0;
ALTER TABLE "dev_page_template"
    ALTER COLUMN "when_created" TYPE varchar(20) USING "when_created"::varchar(20),
ALTER COLUMN "when_modified" TYPE varchar(20) USING "when_modified"::varchar(20);
ALTER TABLE "sys_logic_history"
    ADD COLUMN "version_tag" varchar(50);

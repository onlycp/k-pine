CREATE TABLE if not exists "wf_ext_category" (
                                            "id" varchar(36) COLLATE "pg_catalog"."default" NOT NULL,
                                            "category_name" varchar(50) COLLATE "pg_catalog"."default",
                                            "order_num" varchar(16) COLLATE "pg_catalog"."default",
                                            "when_created" varchar(20) COLLATE "pg_catalog"."default",
                                            "when_modified" varchar(20) COLLATE "pg_catalog"."default",
                                            "who_created" varchar(36) COLLATE "pg_catalog"."default",
                                            "who_modified" varchar(36) COLLATE "pg_catalog"."default",
                                            CONSTRAINT "wf_ext_category_pkey" PRIMARY KEY ("id")
);

CREATE TABLE if not exists  "wf_ext_comment" (
                                           "id" varchar(36) COLLATE "pg_catalog"."default" NOT NULL,
                                           "message" varchar(255) COLLATE "pg_catalog"."default",
                                           "proc_inst_id" varchar(36) COLLATE "pg_catalog"."default",
                                           "task_id" varchar(36) COLLATE "pg_catalog"."default",
                                           "task_name" varchar(50) COLLATE "pg_catalog"."default",
                                           "type" varchar(20) COLLATE "pg_catalog"."default",
                                           "user_id" varchar(36) COLLATE "pg_catalog"."default",
                                           "when_created" varchar(20) COLLATE "pg_catalog"."default",
                                           "parent_inst_id" varchar(50) COLLATE "pg_catalog"."default",
                                           CONSTRAINT "wf_ext_comment_pkey" PRIMARY KEY ("id")
)
;

CREATE TABLE if not exists "wf_ext_node_attribute" (
                                                  "id" varchar(36) COLLATE "pg_catalog"."default" NOT NULL,
                                                  "actions" varchar(100) COLLATE "pg_catalog"."default",
                                                  "exec_mode" varchar(50) COLLATE "pg_catalog"."default",
                                                  "flow_id" varchar(100) COLLATE "pg_catalog"."default",
                                                  "form_attribute" text COLLATE "pg_catalog"."default",
                                                  "msg_send_rule" varchar(100) COLLATE "pg_catalog"."default",
                                                  "next_user" varchar(500) COLLATE "pg_catalog"."default",
                                                  "node_id" varchar(100) COLLATE "pg_catalog"."default",
                                                  "node_type" varchar(50) COLLATE "pg_catalog"."default",
                                                  "pass_ok" varchar(50) COLLATE "pg_catalog"."default",
                                                  "person" varchar(50) COLLATE "pg_catalog"."default",
                                                  "time_out" varchar(50) COLLATE "pg_catalog"."default",
                                                  "back_node" varchar(500) COLLATE "pg_catalog"."default",
                                                  "when_created" varchar(50) COLLATE "pg_catalog"."default",
                                                  "when_modified" varchar(50) COLLATE "pg_catalog"."default",
                                                  "who_created" varchar(100) COLLATE "pg_catalog"."default",
                                                  "who_modified" varchar(100) COLLATE "pg_catalog"."default",
                                                  CONSTRAINT "wf_ext_node_attribute_pkey" PRIMARY KEY ("id")
)
;

CREATE TABLE  if not exists  "wf_ext_procdef" (
                                           "id" varchar(36) COLLATE "pg_catalog"."default" NOT NULL,
                                           "category_id" varchar(36) COLLATE "pg_catalog"."default",
                                           "content" text COLLATE "pg_catalog"."default",
                                           "deploy_md5" varchar(255) COLLATE "pg_catalog"."default",
                                           "deploy_status" int2,
                                           "deploy_time" varchar(20) COLLATE "pg_catalog"."default",
                                           "form_key" varchar(50) COLLATE "pg_catalog"."default",
                                           "form_page_id" varchar(36) COLLATE "pg_catalog"."default",
                                           "icon" varchar(50) COLLATE "pg_catalog"."default",
                                           "inst_desc" varchar(255) COLLATE "pg_catalog"."default",
                                           "order_num" int4,
                                           "proc_definition_id" varchar(36) COLLATE "pg_catalog"."default",
                                           "proc_definition_key" varchar(100) COLLATE "pg_catalog"."default",
                                           "proc_name" varchar(100) COLLATE "pg_catalog"."default",
                                           "proc_version" int4,
                                           "when_created" varchar(20) COLLATE "pg_catalog"."default",
                                           "when_modified" varchar(20) COLLATE "pg_catalog"."default",
                                           "who_created" varchar(36) COLLATE "pg_catalog"."default",
                                           "who_modified" varchar(36) COLLATE "pg_catalog"."default",
                                           "work_num" varchar(100) COLLATE "pg_catalog"."default",
                                           CONSTRAINT "wf_ext_procdef_pkey" PRIMARY KEY ("id")
)
;
CREATE TABLE  if not exists  "wf_ext_procdef" (
                                                  "id" varchar(36) COLLATE "pg_catalog"."default" NOT NULL,
                                                  "category_id" varchar(36) COLLATE "pg_catalog"."default",
                                                  "content" text COLLATE "pg_catalog"."default",
                                                  "deploy_md5" varchar(255) COLLATE "pg_catalog"."default",
                                                  "deploy_status" int2,
                                                  "deploy_time" varchar(20) COLLATE "pg_catalog"."default",
                                                  "form_key" varchar(50) COLLATE "pg_catalog"."default",
                                                  "form_page_id" varchar(36) COLLATE "pg_catalog"."default",
                                                  "icon" varchar(50) COLLATE "pg_catalog"."default",
                                                  "inst_desc" varchar(255) COLLATE "pg_catalog"."default",
                                                  "order_num" int4,
                                                  "proc_definition_id" varchar(36) COLLATE "pg_catalog"."default",
                                                  "proc_definition_key" varchar(100) COLLATE "pg_catalog"."default",
                                                  "proc_name" varchar(100) COLLATE "pg_catalog"."default",
                                                  "proc_version" int4,
                                                  "when_created" varchar(20) COLLATE "pg_catalog"."default",
                                                  "when_modified" varchar(20) COLLATE "pg_catalog"."default",
                                                  "who_created" varchar(36) COLLATE "pg_catalog"."default",
                                                  "who_modified" varchar(36) COLLATE "pg_catalog"."default",
                                                  "work_num" varchar(100) COLLATE "pg_catalog"."default",
                                                  CONSTRAINT "wf_ext_procdef_pkey" PRIMARY KEY ("id")
)
;

CREATE TABLE if not exists  "wf_ext_procinst" (
                                            "id" varchar(36) COLLATE "pg_catalog"."default" NOT NULL,
                                            "bill_code" varchar(50) COLLATE "pg_catalog"."default",
                                            "bill_title" varchar(100) COLLATE "pg_catalog"."default",
                                            "form_data" text COLLATE "pg_catalog"."default",
                                            "main_inst_id" varchar(50) COLLATE "pg_catalog"."default",
                                            "main_task_id" varchar(50) COLLATE "pg_catalog"."default",
                                            "proc_inst_id" varchar(36) COLLATE "pg_catalog"."default",
                                            "starter" varchar(36) COLLATE "pg_catalog"."default",
                                            "when_created" varchar(20) COLLATE "pg_catalog"."default",
                                            CONSTRAINT "wf_ext_procinst_pkey" PRIMARY KEY ("id")
)
;

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
CREATE TABLE if not exists "dev_page_template" (
                                                   "id" varchar(36) COLLATE "pg_catalog"."default" NOT NULL,
                                                   "when_created" varchar(20) COLLATE "pg_catalog"."default",
                                                   "when_modified" varchar(20) COLLATE "pg_catalog"."default",
                                                   "who_created" varchar(36) COLLATE "pg_catalog"."default",
                                                   "who_modified" varchar(36) COLLATE "pg_catalog"."default",
                                                   "deleted" int2,
                                                   "app_id" varchar(36) COLLATE "pg_catalog"."default",
                                                   "name" varchar(255) COLLATE "pg_catalog"."default",
                                                   "description" varchar(255) COLLATE "pg_catalog"."default",
                                                   "app_type" varchar(100) COLLATE "pg_catalog"."default",
                                                   "page_json" text COLLATE "pg_catalog"."default",
                                                   "tags" varchar(255) COLLATE "pg_catalog"."default",
                                                   "module_id" varchar(36) COLLATE "pg_catalog"."default",
                                                   CONSTRAINT "dev_page_template_pkey" PRIMARY KEY ("id")
)
;
ALTER TABLE "dev_page_template"
    ALTER COLUMN "when_created" TYPE varchar(20) USING "when_created"::varchar(20),
ALTER COLUMN "when_modified" TYPE varchar(20) USING "when_modified"::varchar(20);
ALTER TABLE "sys_logic_history"
    ADD COLUMN "version_tag" varchar(50);
ALTER TABLE "sys_logic_history"
    ADD COLUMN "version_tag_time" varchar(50);
ALTER TABLE "dev_faas_node" ALTER COLUMN "config" TYPE text;

ALTER TABLE "dev_faas_node"
    ALTER COLUMN "config" TYPE text COLLATE "pg_catalog"."default" USING "config"::text,
ALTER COLUMN "template" TYPE text COLLATE "pg_catalog"."default" USING "template"::text;

INSERT INTO "dev_faas_node"("id", "name", "code", "type_id", "config", "template", "icon", "pub_status", "order_num", "when_created", "who_created", "when_modified", "who_modified") VALUES ('2dc3aa0ca085438790ef0020d2df2cc7', '编辑', 'curd_update', '107d4c31f6ce4cf69a467d1c2df3bc94', '{
  "form": [
    {
      "label": "数据源",
      "field": "sourceName",
      "editor": "select",
      "source": "flow:d4ae54a989f04dba9e0af1b9815161f2"
    },
    {
      "label": "表名",
      "field": "table",
      "editor": "input"
    },
    {
      "label": "ID列",
      "field": "idCol",
      "editor": "input",
      "default": "id"
    }
  ]
}', '
// 表名称
var tableName = {{table}};
// 数据源名称
var sourceName = {{sourceName}};
// ID列
var idCol = {{idCol}};
// 调用通用编辑
const result = curdUpdate(sourceName, tableName, idCol, {})
// 写入上下文
setResult(''result'', result);', 'k-curd-update', 1, '2', '2023-01-30 15:13:40', '94123ca363dc4dfaa62a6bb5dcd3bf50', '2023-02-09 09:49:11', '94123ca363dc4dfaa62a6bb5dcd3bf50');
INSERT INTO "dev_faas_node"("id", "name", "code", "type_id", "config", "template", "icon", "pub_status", "order_num", "when_created", "who_created", "when_modified", "who_modified") VALUES ('5d2e5853dab241ac931c7d26eaa9fb48', '新增', 'curd_add', '107d4c31f6ce4cf69a467d1c2df3bc94', '{
  "form": [
    {
      "label": "数据源",
      "field": "sourceName",
      "editor": "select",
      "source": "flow:d4ae54a989f04dba9e0af1b9815161f2"
    },
    {
      "label": "表名",
      "field": "table",
      "editor": "input"
    }
  ]
}', '// 表名称
var tableName = {{table}};
// 数据源名称
var sourceName = {{sourceName}};
// 调用通用新增
const result = curdAdd(sourceName, tableName, {});
// 写入上下文
setResult(''result'', result);', 'k-curd-add', 1, '1', '2023-01-29 10:40:36', '94123ca363dc4dfaa62a6bb5dcd3bf50', '2023-02-09 09:49:40', '94123ca363dc4dfaa62a6bb5dcd3bf50');
INSERT INTO "dev_faas_node"("id", "name", "code", "type_id", "config", "template", "icon", "pub_status", "order_num", "when_created", "who_created", "when_modified", "who_modified") VALUES ('640aee70854247d8a20216af547baa33', '下载任务', 'offline-downlad', '1418ab1b9dc24275bbf951d90a512185', '{
  "form": [
    {
      "label": "数据源",
      "field": "sourceName",
      "editor": "select",
      "source": "flow:d4ae54a989f04dba9e0af1b9815161f2"
    },
    {
      "label": "文件名称",
      "field": "fileName",
      "editor": "input"
    },
        {
          "label": "任务名称",
          "field": "taskName",
          "editor": "input"
        },
    {
      "label": "查询语句",
      "field": "content",
      "editor": "code"
    }
  ]
}', '
// 数据源名称
var sourceName = {{sourceName}};
// 值属性
var contennt = {{content}};
// 文件名称
var contennt = {{content}};
// 调用通用编辑
const result = sysOfflineDownload(sourceName, contennt, fileName, {})
// 写入上下文
setResult(''result'', JSON.stringify(result))', 'download', 1, '1', '2023-02-27 15:51:04', '94123ca363dc4dfaa62a6bb5dcd3bf50', '2023-02-27 16:25:28', '94123ca363dc4dfaa62a6bb5dcd3bf50');
INSERT INTO "dev_faas_node"("id", "name", "code", "type_id", "config", "template", "icon", "pub_status", "order_num", "when_created", "who_created", "when_modified", "who_modified") VALUES ('6cf81a5268444bd6b28ace29995df12a', '查询单条数据', 'curd-one', '107d4c31f6ce4cf69a467d1c2df3bc94', '{
    "form": [
        {
            "label": "数据源",
            "field": "sourceName",
            "editor": "select",
            "source": "flow:d4ae54a989f04dba9e0af1b9815161f2"
        },
        {
            "label": "查询语句",
            "field": "content",
            "editor": "code"
        }
    ]
}', '
// 数据源名称
var sourceName = {{sourceName}};
// 值属性
var contennt = {{content}};
// 调用通用编辑
const result = curdGetOne(sourceName, contennt, {})
// 写入上下文
setResult(''result'', JSON.stringify(result))', 'k-curd-one', 1, '5', '2023-02-08 17:56:09', '94123ca363dc4dfaa62a6bb5dcd3bf50', '2023-02-18 11:56:07', '94123ca363dc4dfaa62a6bb5dcd3bf50');
INSERT INTO "dev_faas_node"("id", "name", "code", "type_id", "config", "template", "icon", "pub_status", "order_num", "when_created", "who_created", "when_modified", "who_modified") VALUES ('713b6a1a35d74371aea23d54140566d4', '删除', 'curd_delete', '107d4c31f6ce4cf69a467d1c2df3bc94', '{
  "form": [
    {
      "label": "数据源",
      "field": "sourceName",
      "editor": "select",
      "source": "flow:d4ae54a989f04dba9e0af1b9815161f2"
    },
    {
      "label": "表名",
      "field": "table",
      "editor": "input"
    },
    {
      "label": "ID列",
      "field": "idCol",
      "editor": "input",
      "default": "id"
    },
      {
        "label": "值属性名",
        "field": "valueField",
        "editor": "input",
        "default": "id"
      }
  ]
}', '
// 表名称
var tableName = {{table}};
// 数据源名称
var sourceName = {{sourceName}};
// ID列
var idCol = {{idCol}};
// 值属性
var valueField = {{valueField}};
// 调用通用编辑
const result = curdDelete(sourceName, tableName, idCol, valueField, {})
// 写入上下文
setResult(''result'', result);', 'k-curd-delete', 1, '3', '2023-02-07 10:39:50', '94123ca363dc4dfaa62a6bb5dcd3bf50', '2023-02-09 09:47:01', '94123ca363dc4dfaa62a6bb5dcd3bf50');
INSERT INTO "dev_faas_node"("id", "name", "code", "type_id", "config", "template", "icon", "pub_status", "order_num", "when_created", "who_created", "when_modified", "who_modified") VALUES ('c0611398a04e40b7bb51651186214c97', '分页查询', 'curd_page', '107d4c31f6ce4cf69a467d1c2df3bc94', '{
  "form": [
    {
      "label": "数据源",
      "field": "sourceName",
      "editor": "select",
      "source": "flow:d4ae54a989f04dba9e0af1b9815161f2"
    },
    {
      "label": "查询语句",
      "field": "content",
      "editor": "code"
    }
  ]
}', '
// 数据源名称
var sourceName = {{sourceName}};
// 值属性
var contennt = {{content}};
// 调用通用编辑
const result = curdPage(sourceName, contennt, {})
// 写入上下文
setResult(''result'', JSON.stringify(result))', 'k-curd-page', 1, '4', '2023-02-07 17:51:48', '94123ca363dc4dfaa62a6bb5dcd3bf50', '2023-02-09 09:46:39', '94123ca363dc4dfaa62a6bb5dcd3bf50');


INSERT INTO "dev_faas_node_type"("id", "name", "pub_status", "icon", "when_created", "who_created", "when_modified", "who_modified") VALUES ('107d4c31f6ce4cf69a467d1c2df3bc94', '关系数据库', 1, 'database', '2023-01-28 16:54:28', '94123ca363dc4dfaa62a6bb5dcd3bf50', '2023-01-28 16:54:28', '94123ca363dc4dfaa62a6bb5dcd3bf50');
INSERT INTO "dev_faas_node_type"("id", "name", "pub_status", "icon", "when_created", "who_created", "when_modified", "who_modified") VALUES ('1418ab1b9dc24275bbf951d90a512185', '系统工具', 1, 'tools', '2023-02-27 15:48:57', '94123ca363dc4dfaa62a6bb5dcd3bf50', '2023-02-27 15:48:57', '94123ca363dc4dfaa62a6bb5dcd3bf50');
INSERT INTO "dev_faas_node_type"("id", "name", "pub_status", "icon", "when_created", "who_created", "when_modified", "who_modified") VALUES ('2e512cbbfede45218dd6ab3f1c2a72e2', 'NoSql', 1, 'NoSql', '2023-01-28 16:57:47', '94123ca363dc4dfaa62a6bb5dcd3bf50', '2023-01-28 16:58:30', '94123ca363dc4dfaa62a6bb5dcd3bf50');
INSERT INTO "dev_faas_node_type"("id", "name", "pub_status", "icon", "when_created", "who_created", "when_modified", "who_modified") VALUES ('8fc3ea0e4c884bf4a5e5e5bfa3a79c88', '消息队列', 1, 'MQ', '2023-01-28 16:58:48', '94123ca363dc4dfaa62a6bb5dcd3bf50', '2023-01-28 16:58:48', '94123ca363dc4dfaa62a6bb5dcd3bf50');
INSERT INTO "dev_faas_node_type"("id", "name", "pub_status", "icon", "when_created", "who_created", "when_modified", "who_modified") VALUES ('c94977f8e74240d8927f9fde864be80b', '脚本语言', 1, 'SCRIPT', '2023-01-28 16:59:13', '94123ca363dc4dfaa62a6bb5dcd3bf50', '2023-01-28 16:59:13', '94123ca363dc4dfaa62a6bb5dcd3bf50');


INSERT INTO sys_config(id, app_id, code, is_sys, name, note, value, value_type, when_created, when_modified, who_created, who_modified) VALUES ('4982e27d84914b559a2a5aed7953b934', NULL, 'app.k-flow.debug-ws-server', 1, 'FAAS调试WS服务器', NULL, '10.11.1.77:9229', 0, '2023-03-31 17:24:55', '2023-03-31 17:24:55', '94123ca363dc4dfaa62a6bb5dcd3bf50', '94123ca363dc4dfaa62a6bb5dcd3bf50');
ALTER TABLE "dev_page_history"
    ADD COLUMN "version_tag" varchar(50);
ALTER TABLE "dev_page_history"
    ADD COLUMN "version_tag_time" varchar(50);

INSERT INTO dev_module
(id, app_id, has_path, is_sys, name, parent_id, path, sort, when_created, when_modified, who_created, who_modified)
VALUES('0913bc0b384c44d99e384b992cb7fe40', '064b3b44b85a45fe87fcce88d72b2519', 1, 1, '开发平台', NULL, '/dev', 1, '2023-03-29 10:43:48', '2023-03-30 09:55:00', '7aed8c297a6940f681c26eb6ab68893d', '7aed8c297a6940f681c26eb6ab68893d');
INSERT INTO dev_module
(id, app_id, has_path, is_sys, name, parent_id, path, sort, when_created, when_modified, who_created, who_modified)
VALUES('09e4ec197da14de3844b6b04c4fa5ee9', '064b3b44b85a45fe87fcce88d72b2519', 0, 1, '开发平台管理', NULL, NULL, 3, '2023-03-29 10:48:15', '2023-03-29 10:48:15', '7aed8c297a6940f681c26eb6ab68893d', '7aed8c297a6940f681c26eb6ab68893d');
INSERT INTO dev_module
(id, app_id, has_path, is_sys, name, parent_id, path, sort, when_created, when_modified, who_created, who_modified)
VALUES('5a10d15671704b6cbc01cc3a8bed365a', '064b3b44b85a45fe87fcce88d72b2519', 0, 1, '公共库', NULL, NULL, 4, '2022-05-31 15:18:08', '2022-05-31 15:24:40', '7aed8c297a6940f681c26eb6ab68893d', '7aed8c297a6940f681c26eb6ab68893d');
INSERT INTO dev_module
(id, app_id, has_path, is_sys, name, parent_id, path, sort, when_created, when_modified, who_created, who_modified)
VALUES('6e2c61bac14a42ca9b24bbab853022a0', '0b00821bab714d8299d07a68c25873ed', 0, 0, '接口', 'bea8f78657644fa5808a863cce3c1182', NULL, 1, '2023-04-11 14:18:15', '2023-04-11 14:21:21', '056fb0eeb9a44cb0953534b4c0ca01fa', '056fb0eeb9a44cb0953534b4c0ca01fa');
INSERT INTO dev_module
(id, app_id, has_path, is_sys, name, parent_id, path, sort, when_created, when_modified, who_created, who_modified)
VALUES('90f2c2ea0f9942d181388e24fd6ee936', '064b3b44b85a45fe87fcce88d72b2519', 1, 1, 'v3开发', '0913bc0b384c44d99e384b992cb7fe40', '/dev/v3', 1, '2023-03-29 17:37:35', '2023-03-30 09:55:12', '7aed8c297a6940f681c26eb6ab68893d', '7aed8c297a6940f681c26eb6ab68893d');
INSERT INTO dev_module
(id, app_id, has_path, is_sys, name, parent_id, path, sort, when_created, when_modified, who_created, who_modified)
VALUES('9bbe33574d0547e78f72f5982bea26cd', '064b3b44b85a45fe87fcce88d72b2519', 0, 1, '子页面', '0913bc0b384c44d99e384b992cb7fe40', NULL, 0, '2023-03-29 10:46:23', '2023-03-30 09:55:08', '7aed8c297a6940f681c26eb6ab68893d', '7aed8c297a6940f681c26eb6ab68893d');
INSERT INTO dev_module
(id, app_id, has_path, is_sys, name, parent_id, path, sort, when_created, when_modified, who_created, who_modified)
VALUES('d4820db91eab4cdc9c82703cf1d4df83', '064b3b44b85a45fe87fcce88d72b2519', 0, 0, '流程关联表单页', '1e329b86988b4dd79b49887b774b0879', NULL, 0, '2023-03-29 16:28:46', '2023-03-29 16:28:46', '7aed8c297a6940f681c26eb6ab68893d', '7aed8c297a6940f681c26eb6ab68893d');
INSERT INTO dev_module
(id, app_id, has_path, is_sys, name, parent_id, path, sort, when_created, when_modified, who_created, who_modified)
VALUES('e0047b3716fa48789d8d2377b1d23195', '064b3b44b85a45fe87fcce88d72b2519', 0, 1, '系统配置', NULL, NULL, 2, '2023-03-29 09:27:04', '2023-03-29 09:27:04', '7aed8c297a6940f681c26eb6ab68893d', '7aed8c297a6940f681c26eb6ab68893d');
INSERT INTO dev_module
(id, app_id, has_path, is_sys, name, parent_id, path, sort, when_created, when_modified, who_created, who_modified)
VALUES('fbe9d1e36a37423aa9ab4711c569093f', '064b3b44b85a45fe87fcce88d72b2519', 0, 1, '基础功能', NULL, NULL, 2, '2023-03-29 09:26:54', '2023-03-29 09:26:54', '7aed8c297a6940f681c26eb6ab68893d', '7aed8c297a6940f681c26eb6ab68893d');

ALTER TABLE "ext_plugin_tree"
    ADD COLUMN "clazz_name" varchar(255);

DROP INDEX "public"."idx_sys_dict_id_name_uindex_sys_dict_item";

DROP INDEX "public"."idx_sys_dict_id_value_uindex_sys_dict_item";

CREATE INDEX "idx_sys_dict_id_name_uindex_sys_dict_item" ON "public"."sys_dict_item" USING btree (
    "name" COLLATE "pg_catalog"."default" "pg_catalog"."text_ops" ASC NULLS LAST,
    "sys_dict_id" COLLATE "pg_catalog"."default" "pg_catalog"."text_ops" ASC NULLS LAST
    );

CREATE INDEX "idx_sys_dict_id_value_uindex_sys_dict_item" ON "public"."sys_dict_item" USING btree (
    "sys_dict_id" COLLATE "pg_catalog"."default" "pg_catalog"."text_ops" ASC NULLS LAST,
    "value" COLLATE "pg_catalog"."default" "pg_catalog"."text_ops" ASC NULLS LAST
    );
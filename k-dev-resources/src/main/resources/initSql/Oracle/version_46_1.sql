ALTER TABLE dev_application
    ADD app_namespace VARCHAR2(255);
COMMENT ON COLUMN dev_application.app_namespace IS '应用命名空间';

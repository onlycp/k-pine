ALTER TABLE dev_application
    ADD COLUMN depend_datasources text NULL,
    ADD COLUMN depend_apps text NULL;

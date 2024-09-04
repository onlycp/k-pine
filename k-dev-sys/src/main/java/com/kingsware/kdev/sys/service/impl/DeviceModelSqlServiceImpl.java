package com.kingsware.kdev.sys.service.impl;

import com.kingsware.kdev.core.orm.DB;
import com.kingsware.kdev.core.bean.SqlSegment;
import com.kingsware.kdev.sys.model.DevModelSql;
import com.kingsware.kdev.sys.service.DevModelSqlService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 *
 * @author chen peng
 * @version 1.0.0
 * @date 2024/9/3 20:19
 */
@Slf4j
@Service
public class DeviceModelSqlServiceImpl implements DevModelSqlService {
    @Override
    public void execute(String appId) {

    }

    @Override
    public void execute(String appId, String sourceName) {

    }

    @Override
    public void execute(DevModelSql devModelSql) {
        String content =
        DB.byName(devModelSql.getSourceName()).executeUpdateSql()
    }

    private List<SqlSegment> parse(String content) {
        //
    }

//    @Override
//    public void execute(String appId, String sourceName, Integer version) {
//        DevModelSql devModelSql = DB.findOne(DevModelSql.class, "select * from dev_model_sql where app_id=? and source_name=? and version=?", appId, s)
//    }
}

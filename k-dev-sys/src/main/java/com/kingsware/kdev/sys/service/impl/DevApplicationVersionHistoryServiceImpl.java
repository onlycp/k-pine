package com.kingsware.kdev.sys.service.impl;

import com.kingsware.kdev.core.base.BaseServiceImpl;
import com.kingsware.kdev.core.bean.MultiIdArgv;
import com.kingsware.kdev.core.bean.PageDataRet;
import com.kingsware.kdev.core.i18n.I18n;
import com.kingsware.kdev.core.orm.DB;
import com.kingsware.kdev.core.orm.DBChecker;
import com.kingsware.kdev.core.orm.SqlWrapper;
import com.kingsware.kdev.core.util.BeanUtils;
import com.kingsware.kdev.sys.argv.DevApplicationVersionHistoryArgv;
import com.kingsware.kdev.sys.argv.DevApplicationVersionHistoryQueryArgv;
import com.kingsware.kdev.sys.model.DevApplicationVersionHistory;
import com.kingsware.kdev.sys.ret.DevApplicationVersionHistoryRet;
import com.kingsware.kdev.sys.service.DevApplicationVersionHistoryService;
import org.springframework.stereotype.Service;

/**
 * 业务实现类
 * @author AndyZheng
 * @version 1.0.0
 * @date 2022-02-13 10:20
 */
@Service
public class DevApplicationVersionHistoryServiceImpl extends BaseServiceImpl implements DevApplicationVersionHistoryService {

    @Override
    public DevApplicationVersionHistoryRet get(String id) {
        // 查询model
        DevApplicationVersionHistory model = DB.findById(DevApplicationVersionHistory.class, id);
        // 转换成ret对象
        return (DevApplicationVersionHistoryRet) model2Ret(model, DevApplicationVersionHistoryRet.class);
    }

    @Override
    public void add(DevApplicationVersionHistoryArgv argv) {
        DevApplicationVersionHistory model = BeanUtils.copyObject(argv, DevApplicationVersionHistory.class);
        // 保存
        DB.save(model);
    }

    @Override
    public void edit(DevApplicationVersionHistoryArgv argv) {
        DevApplicationVersionHistory model = DB.findById(DevApplicationVersionHistory.class, argv.getId());
        BeanUtils.copyProperties(argv, model);
        // 保存
        DB.update(model);
    }

    @Override
    @SuppressWarnings("unchecked")
    public PageDataRet<DevApplicationVersionHistoryRet> query(DevApplicationVersionHistoryQueryArgv argv) {
        // 拼装sql
        SqlWrapper wrapper = new SqlWrapper("select * from dev_application_version_history where 1=1 ");

        wrapper.sortBy("when_created desc");
        return (PageDataRet<DevApplicationVersionHistoryRet>) query(wrapper.getSql(), wrapper.getParams(), argv, DevApplicationVersionHistory.class, DevApplicationVersionHistoryRet.class);
    }

    @Override
    public void delete(MultiIdArgv argv) {
        for (String id: argv.getIds()) {
            DB.delete(DevApplicationVersionHistory.class, id);
        }
    }
}

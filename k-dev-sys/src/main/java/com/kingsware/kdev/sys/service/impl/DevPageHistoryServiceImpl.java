package com.kingsware.kdev.sys.service.impl;

import com.kingsware.kdev.core.base.BaseServiceImpl;
import com.kingsware.kdev.core.bean.MultiIdArgv;
import com.kingsware.kdev.core.bean.PageDataRet;
import com.kingsware.kdev.core.orm.DB;
import com.kingsware.kdev.core.orm.SqlWrapper;
import com.kingsware.kdev.core.orm.expression.Op;
import com.kingsware.kdev.core.util.BeanUtils;
import com.kingsware.kdev.core.util.StringUtils;
import com.kingsware.kdev.sys.argv.DevPageHistoryArgv;
import com.kingsware.kdev.sys.argv.DevPageHistoryQueryArgv;
import com.kingsware.kdev.core.model.DevPage;
import com.kingsware.kdev.sys.model.DevPageHistory;
import com.kingsware.kdev.sys.ret.DevPageHistoryRet;
import com.kingsware.kdev.sys.service.DevPageHistoryService;
import org.springframework.stereotype.Service;

/**
 * 业务实现类
 * @author AndyZheng
 * @version 1.0.0
 * @date 2022-02-13 10:20
 */
@Service
public class DevPageHistoryServiceImpl extends BaseServiceImpl implements DevPageHistoryService {

    @Override
    public DevPageHistoryRet get(String id) {
        // 查询model
        DevPageHistory model = DB.findById(DevPageHistory.class, id);
        // 转换成ret对象
        return (DevPageHistoryRet) model2Ret(model, DevPageHistoryRet.class);
    }

    @Override
    public void add(DevPageHistoryArgv argv) {
        DevPageHistory model = BeanUtils.copyObject(argv, DevPageHistory.class);
        // 保存
        DB.save(model);
    }

    @Override
    public void edit(DevPageHistoryArgv argv) {
        DevPageHistory model = DB.findById(DevPageHistory.class, argv.getId());
        BeanUtils.copyProperties(argv, model);
        // 保存
        DB.update(model);
    }

    @Override
    public void rollback(DevPageHistoryArgv argv) {
        DevPage model = DB.findById(DevPage.class, argv.getPageId());
        if (model != null) {
            model.setPageJson(argv.getPageJson());
            // 保存
            DB.update(model);
        }
    }

    @Override
    @SuppressWarnings("unchecked")
    public PageDataRet<DevPageHistoryRet> query(DevPageHistoryQueryArgv argv) {
        // 拼装sql
        SqlWrapper wrapper = new SqlWrapper("select dph.*, su.real_name as created_user_name, su.avatar as created_user_avatar from dev_page_history dph left join sys_user su on su.id = dph.who_created where 1=1   ");
        if (StringUtils.isNotEmpty(argv.getPageId())) {
            wrapper.addCondition("dph.page_id", Op.EQ, argv.getPageId());
        }
        wrapper.sortBy("dph.when_created desc");
        return (PageDataRet<DevPageHistoryRet>) query(wrapper.getSql(), wrapper.getParams(), argv, DevPageHistoryRet.class);
    }

    @Override
    public void delete(MultiIdArgv argv) {
        for (String id: argv.getIds()) {
            DB.delete(DevPageHistory.class, id);
        }
    }
}

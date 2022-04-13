package com.kingsware.kdev.sys.service.impl;

import com.kingsware.kdev.core.base.BaseServiceImpl;
import com.kingsware.kdev.core.bean.MultiIdArgv;
import com.kingsware.kdev.core.bean.PageDataRet;
import com.kingsware.kdev.core.orm.DB;
import com.kingsware.kdev.core.orm.SqlWrapper;
import com.kingsware.kdev.core.orm.expression.Op;
import com.kingsware.kdev.core.util.BeanUtils;
import com.kingsware.kdev.core.util.StringUtils;
import com.kingsware.kdev.sys.argv.DevTopologicalArgv;
import com.kingsware.kdev.sys.argv.DevTopologicalQueryArgv;
import com.kingsware.kdev.sys.model.DevTopological;
import com.kingsware.kdev.sys.ret.DevTopologicalRet;
import com.kingsware.kdev.sys.service.DevTopologicalService;
import org.springframework.stereotype.Service;

/**
 * 业务实现类
 * @author AndyZheng
 * @version 1.0.0
 * @date 2022-02-13 10:20
 */
@Service
public class DevTopologicalServiceImpl extends BaseServiceImpl implements DevTopologicalService {

    @Override
    public DevTopologicalRet get(String id) {
        // 查询model
        DevTopological model = DB.findById(DevTopological.class, id);
        // 转换成ret对象
        return (DevTopologicalRet) model2Ret(model, DevTopologicalRet.class);
    }

    @Override
    public void add(DevTopologicalArgv argv) {
        DevTopological model = BeanUtils.copyObject(argv, DevTopological.class);
        // 保存
        DB.save(model);
    }

    @Override
    public void edit(DevTopologicalArgv argv) {
        DevTopological model = DB.findById(DevTopological.class, argv.getId());
        BeanUtils.copyProperties(argv, model);
        // 保存
        DB.update(model);
    }

    @Override
    @SuppressWarnings("unchecked")
    public PageDataRet<DevTopologicalRet> query(DevTopologicalQueryArgv argv) {
        // 拼装sql
        SqlWrapper wrapper = new SqlWrapper("select * from dev_topological where 1=1 and deleted=0 ");
        if (StringUtils.isNotEmpty(argv.getName())) {
            wrapper.addCondition("name", Op.LIKE, "%" + argv.getName() + "%");
        }
        if (StringUtils.isNotEmpty(argv.getAppId())) {
            wrapper.appendSql(" and (app_id = ? or app_id is null)", argv.getAppId());
        }
        wrapper.sortBy("when_created desc");
        return (PageDataRet<DevTopologicalRet>) query(wrapper.getSql(), wrapper.getParams(), argv, DevTopological.class, DevTopologicalRet.class);
    }

    @Override
    public void delete(MultiIdArgv argv) {
        for (String id: argv.getIds()) {
            DB.delete(DevTopological.class, id);
        }
    }
}

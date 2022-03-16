package com.kingsware.kdev.sys.service.impl;

import com.kingsware.kdev.core.base.BaseServiceImpl;
import com.kingsware.kdev.core.bean.MultiIdArgv;
import com.kingsware.kdev.core.bean.PageDataRet;
import com.kingsware.kdev.core.i18n.I18n;
import com.kingsware.kdev.core.orm.DB;
import com.kingsware.kdev.core.orm.DBChecker;
import com.kingsware.kdev.core.orm.SqlWrapper;
import com.kingsware.kdev.core.orm.expression.Op;
import com.kingsware.kdev.core.util.BeanUtils;
import com.kingsware.kdev.core.util.StringUtils;
import com.kingsware.kdev.sys.argv.DevApplicationArgv;
import com.kingsware.kdev.sys.argv.DevApplicationQueryArgv;
import com.kingsware.kdev.sys.model.DevApplication;
import com.kingsware.kdev.sys.ret.DevApplicationRet;
import com.kingsware.kdev.sys.service.DevApplicationService;
import org.springframework.stereotype.Service;

/**
 * 业务实现类
 * @author AndyZheng
 * @version 1.0.0
 * @date 2022-02-13 10:20
 */
@Service
public class DevApplicationServiceImpl extends BaseServiceImpl implements DevApplicationService {

    @Override
    public DevApplicationRet get(String id) {
        // 查询model
        DevApplication model = DB.findById(DevApplication.class, id);
        // 转换成ret对象
        return (DevApplicationRet) model2Ret(model, DevApplicationRet.class);
    }

    @Override
    public void add(DevApplicationArgv argv) {
        DevApplication model = BeanUtils.copyObject(argv, DevApplication.class);
        // 唯一校验
        checkUnique(model);
        // 保存
        DB.save(model);
    }

    @Override
    public void edit(DevApplicationArgv argv) {
        DevApplication model = DB.findById(DevApplication.class, argv.getId());
        BeanUtils.copyProperties(argv, model);
        // 唯一校验
        checkUnique(model);
        // 保存
        DB.update(model);
    }

    private void checkUnique(DevApplication model) {
        // 唯一性校验
        DBChecker<DevApplication> checker =DBChecker.build(model, DevApplication.class);
        // 应用短英文名唯一
        checker.uni("shortName", I18n.t("DevApplication.shortName.unique", "应用短英文名必须唯一"));
        // 执行校验
        checker.checkUnique();
    }

    @Override
    @SuppressWarnings("unchecked")
    public PageDataRet<DevApplicationRet> query(DevApplicationQueryArgv argv) {
        // 拼装sql
        SqlWrapper wrapper = new SqlWrapper("select * from dev_application where deleted=0 ");

        if (StringUtils.isNotEmpty(argv.getKeywords())) {
            wrapper.setSql(wrapper.getSql() + " and (name like ? or short_name like ? or description like ?) ");
            wrapper.getParams().add("%" + argv.getKeywords() + "%");
            wrapper.getParams().add("%" + argv.getKeywords() + "%");
            wrapper.getParams().add("%" + argv.getKeywords() + "%");
        }
        if (argv.getEnableStatus() != null) {
            wrapper.addCondition("enable_status", Op.EQ, argv.getEnableStatus());
        }
        if (argv.getDevStatus() != null) {
            wrapper.addCondition("devStatus", Op.EQ, argv.getDevStatus());
        }
        if (argv.getVersion() != null) {
            wrapper.addCondition("version", Op.EQ, argv.getVersion());
        }
        if (argv.getWhoInCharge() != null) {
            wrapper.addCondition("who_in_charge", Op.EQ, argv.getWhoInCharge());
        }
        if (argv.getAppType() != null) {
            wrapper.addCondition("app_type", Op.EQ, argv.getAppType());
        }
        wrapper.sortBy("when_created desc");

        return (PageDataRet<DevApplicationRet>) query(wrapper.getSql(), wrapper.getParams(), argv, DevApplication.class, DevApplicationRet.class);
    }

    @Override
    public void delete(MultiIdArgv argv) {
        for (String id: argv.getIds()) {
            DB.delete(DevApplication.class, id);
        }
    }
}

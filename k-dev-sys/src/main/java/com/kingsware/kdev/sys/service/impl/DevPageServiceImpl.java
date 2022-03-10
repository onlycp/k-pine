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
import com.kingsware.kdev.sys.argv.DevPageArgv;
import com.kingsware.kdev.sys.argv.DevPageQueryArgv;
import com.kingsware.kdev.sys.model.DevApplication;
import com.kingsware.kdev.sys.model.DevPage;
import com.kingsware.kdev.sys.ret.DevPageRet;
import com.kingsware.kdev.sys.service.DevPageService;
import org.springframework.stereotype.Service;

/**
 * 业务实现类
 * @author AndyZheng
 * @version 1.0.0
 * @date 2022-02-13 10:20
 */
@Service
public class DevPageServiceImpl extends BaseServiceImpl implements DevPageService {

    @Override
    public DevPageRet get(String id) {
        // 查询model
        DevPage model = DB.findById(DevPage.class, id);
        // 转换成ret对象
        return (DevPageRet) model2Ret(model, DevPageRet.class);
    }

    @Override
    public DevPageRet getByPath(String path) {
        return DB.findOne(DevPageRet.class, " select * from dev_page where path = ? ", path);
    }

    @Override
    public void add(DevPageArgv argv) {
        DevPage model = BeanUtils.copyObject(argv, DevPage.class);
        // 唯一校验
        checkUnique(model);
        // 保存
        DB.save(model);
    }

    @Override
    public void edit(DevPageArgv argv) {
        DevPage model = DB.findById(DevPage.class, argv.getId());
        // 唯一校验
        checkUnique(model);
        BeanUtils.copyProperties(argv, model);
        // 保存
        DB.update(model);
    }

    private void checkUnique(DevPage model) {
        // 唯一性校验
        DBChecker<DevPage> checker =DBChecker.build(model, DevPage.class);
        // 页面路径唯一
        checker.uni("path", I18n.t("DevPage.path.unique", "页面路径必须唯一"));
        // 执行校验
        checker.checkUnique();
    }

    @Override
    @SuppressWarnings("unchecked")
    public PageDataRet<DevPageRet> query(DevPageQueryArgv argv) {
        // 拼装sql
        SqlWrapper wrapper = new SqlWrapper("select * from dev_page where 1=1 and deleted=0 ");
        if (StringUtils.isNotEmpty(argv.getAppId())) {
            wrapper.addCondition("app_id", Op.EQ, argv.getAppId());
        }
        if (StringUtils.isNotEmpty(argv.getName())) {
            wrapper.addCondition("name", Op.LIKE, "%" + argv.getName() + "%");
        }
        return (PageDataRet<DevPageRet>) query(wrapper.getSql(), wrapper.getParams(), argv, DevPage.class, DevPageRet.class);
    }

    @Override
    public void delete(MultiIdArgv argv) {
        for (String id: argv.getIds()) {
            DB.delete(DevPage.class, id);
        }
    }
}

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
import com.kingsware.kdev.sys.argv.SysDataResourceArgv;
import com.kingsware.kdev.sys.argv.SysDataResourceQueryArgv;
import com.kingsware.kdev.sys.model.SysDataResource;
import com.kingsware.kdev.sys.ret.SysDataResourceRet;
import com.kingsware.kdev.sys.service.SysDataResourceService;
import org.springframework.stereotype.Service;

/**
 * 数据访问业务层
 *
 * @author chen peng
 * @version 1.0.0
 * @date 2021/12/23 9:36 上午
 */
@Service
public class SysDataResourceServiceImpl extends BaseServiceImpl implements SysDataResourceService {

    @Override
    public SysDataResourceRet get(String id) {
        // 查询model
        SysDataResource model = DB.findById(SysDataResource.class, id);
        // 转换成ret对象
        return (SysDataResourceRet) model2Ret(model, SysDataResourceRet.class);
    }

    @Override
    public void add(SysDataResourceArgv argv) {
        SysDataResource model = BeanUtils.copyObject(argv, SysDataResource.class);
        // 校验
        checkUnique(model);
        // 保存
        DB.save(model);
    }

    @Override
    public void edit(SysDataResourceArgv argv) {
        SysDataResource model = DB.findById(SysDataResource.class, argv.getId());
        model.setName(argv.getName());
        model.setTableName(argv.getTableName());
        model.setStatus(argv.getStatus());
        model.setIsTree(argv.getIsTree());
        model.setIsOnlyLeaf(argv.getIsOnlyLeaf());
        model.setExtraSql(argv.getExtraSql());
        model.setQuerySql(argv.getQuerySql());
        model.setValueField(argv.getValueField());
        // 校验
        checkUnique(model);
        // 保存
        DB.update(model);
    }

    private void checkUnique(SysDataResource model) {
        // 唯一性校验
        DBChecker<SysDataResource> checker =DBChecker.build(model, SysDataResource.class);
        // 名称唯一
        checker.uni("name", I18n.t("SysDataResource.name.unique", "名称必须唯一"));
        // 编码唯一
        checker.uni("tableName", I18n.t("SysDataResource.tableName.unique", "表名必须唯一"));
        // 执行校验
        checker.checkUnique();
    }

    @Override
    @SuppressWarnings("unchecked")
    public PageDataRet<SysDataResourceRet> query(SysDataResourceQueryArgv argv) {
        // 拼装sql
        SqlWrapper wrapper = new SqlWrapper("select * from sys_data_resource where 1=1 ");
        // 拼装查询sql
        if (StringUtils.isNotEmpty(argv.getName())) {
            wrapper.addCondition("name", Op.LIKE, "%" +argv.getName() +"%");
        }
        if (argv.getStatus() != null) {
            wrapper.addCondition("status", Op.EQ, argv.getStatus());
        }
        if (StringUtils.isNotEmpty(argv.getAppId())) {
            wrapper.appendSql(" and (app_id = ? or app_id is null)", argv.getAppId());
        }
        wrapper.sortBy("when_created desc");
        return (PageDataRet<SysDataResourceRet>) query(wrapper.getSql(), wrapper.getParams(), argv, SysDataResource.class, SysDataResourceRet.class);
    }

    @Override
    public void delete(MultiIdArgv argv) {
        for (String id: argv.getIds()) {
            DB.delete(SysDataResource.class, id);
        }
    }
}

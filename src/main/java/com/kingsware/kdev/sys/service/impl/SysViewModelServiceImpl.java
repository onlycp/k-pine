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
import com.kingsware.kdev.sys.argv.SysViewModelArgv;
import com.kingsware.kdev.sys.argv.SysViewModelFieldArgv;
import com.kingsware.kdev.sys.argv.SysViewModelQueryArgv;
import com.kingsware.kdev.sys.model.SysViewModel;
import com.kingsware.kdev.sys.model.SysViewModelField;
import com.kingsware.kdev.sys.ret.SysViewModelRet;
import com.kingsware.kdev.sys.service.SysViewModelService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * 数据访问业务层
 *
 * @author chen peng
 * @version 1.0.0
 * @date 2021/12/23 9:36 上午
 */
@Service
public class SysViewModelServiceImpl extends BaseServiceImpl implements SysViewModelService {

    @Override
    public SysViewModelRet get(String id) {
        // 查询model
        SysViewModel model = DB.findById(SysViewModel.class, id);
        // 转换成ret对象
        return (SysViewModelRet) model2Ret(model, SysViewModelRet.class);
    }

    @Override
    public void add(SysViewModelArgv argv) {
        SysViewModel model = BeanUtils.copyObject(argv, SysViewModel.class);
        // 校验
        checkUnique(model);
        // 保存
        DB.save(model);
        // 保存属性
        saveFields(argv, model.getId());
    }

    @Override
    public void edit(SysViewModelArgv argv) {
        SysViewModel model = DB.findById(SysViewModel.class, argv.getId());
        model.setName(argv.getName());
        model.setTag(argv.getTag());
        model.setTag(argv.getTag());
        // 校验
        checkUnique(model);
        // 保存
        DB.update(model);
        // 保存属性， 简单粗暴，直接先删后加
        DB.executeUpdateSql("delete from sys_view_model_field where view_model_id=?", argv.getId());
        saveFields(argv, model.getId());

    }

    /**
     * 保存属性
     * @param argv
     */
    private void saveFields(SysViewModelArgv argv, String id) {
        if (argv.getFields() != null && !argv.getFields().isEmpty()) {
            List<SysViewModelField> fields = new ArrayList<>(argv.getFields().size());
            for (SysViewModelFieldArgv fieldArgv: argv.getFields()) {
                SysViewModelField field = BeanUtils.copyObject(fieldArgv, SysViewModelField.class);
                field.setViewModelId(id);
                fields.add(field);
            }
            DB.saveAll(fields);
        }
    }

    private void checkUnique(SysViewModel model) {
        // 唯一性校验
        DBChecker<SysViewModel> checker =DBChecker.build(model, SysViewModel.class);
        // 名称唯一
        checker.uni("name", I18n.t("SysViewModel.name.unique", "名称必须唯一"));
        // 执行校验
        checker.checkUnique();
    }

    @Override
    @SuppressWarnings("unchecked")
    public PageDataRet<SysViewModelRet> query(SysViewModelQueryArgv argv) {
        // 拼装sql
        SqlWrapper wrapper = new SqlWrapper("select * from sys_view_model where 1=1 ");
        // 拼装查询sql
        if (StringUtils.isNotEmpty(argv.getName())) {
            wrapper.addCondition("name", Op.LIKE, "%" +argv.getName() +"%");
        }
        if (StringUtils.isNotEmpty(argv.getTag())) {
            wrapper.addCondition("tag", Op.LIKE, "%" +argv.getTag() +"%");
        }
        return (PageDataRet<SysViewModelRet>) query(wrapper.getSql(), wrapper.getParams(), argv, SysViewModel.class, SysViewModelRet.class);
    }

    @Override
    public void delete(MultiIdArgv argv) {
        for (String id: argv.getIds()) {
            DB.delete(SysViewModel.class, id);
            DB.executeUpdateSql("delete from sys_view_model_field where view_model_id=?", id);
        }
    }
}

package com.kingsware.kdev.sys.service.impl;

import com.kingsware.kdev.core.base.BaseServiceImpl;
import com.kingsware.kdev.core.bean.MultiIdArgv;
import com.kingsware.kdev.core.bean.PageDataRet;
import com.kingsware.kdev.core.exception.BusinessException;
import com.kingsware.kdev.core.i18n.I18n;
import com.kingsware.kdev.core.orm.DB;
import com.kingsware.kdev.core.orm.DBChecker;
import com.kingsware.kdev.core.orm.PagedList;
import com.kingsware.kdev.core.orm.SqlWrapper;
import com.kingsware.kdev.core.orm.expression.Op;
import com.kingsware.kdev.core.util.BeanUtils;
import com.kingsware.kdev.core.util.StringUtils;
import com.kingsware.kdev.sys.argv.SysDictArgv;
import com.kingsware.kdev.sys.argv.SysDictQueryArgv;
import com.kingsware.kdev.sys.model.SysDict;
import com.kingsware.kdev.sys.ret.SysDictRet;
import com.kingsware.kdev.sys.service.SysDictService;
import org.springframework.stereotype.Service;

/**
 * 字典类型实现类
 *
 * @author AndyZheng
 * @version 1.0.0
 * @date 2021/12/27 9:36 上午
 */
@Service
public class SysDictServiceImpl extends BaseServiceImpl implements SysDictService {

    @Override
    public SysDictRet get(String id) {
        // 查询model
        SysDict model = DB.findById(SysDict.class, id);
        // 转换成ret对象
        return BeanUtils.copyObject(model, SysDictRet.class);
    }

    @Override
    public void add(SysDictArgv argv) {
        SysDict model = BeanUtils.copyObject(argv, SysDict.class);
        // 唯一性校验
        DBChecker<SysDict> checker =DBChecker.build(model, SysDict.class);
        // 名称唯一
//        checker.uni("name", I18n.t("SysDict.name.unique", "字典名称必须唯一"));
        // 编码唯一
        checker.uni("code", I18n.t("SysDict.code.unique", "字典代码必须唯一"));
        // 执行校验
        checker.checkUnique();
        // 保存
        DB.save(model);
    }

    @Override
    public void edit(SysDictArgv argv) {
        SysDict model = DB.findById(SysDict.class, argv.getId());
        if (model == null) {
            throw new BusinessException("找不到字典类型");
        }
        String oldCode = model.getCode();
        // 修改
        model = BeanUtils.copyObject(argv, SysDict.class);
        // 唯一性校验
        DBChecker<SysDict> checker =DBChecker.build(model, SysDict.class);
        // 编码唯一
        checker.uni("code", I18n.t("SysDict.code.unique", "字典代码必须唯一"));
        // 执行校验
        checker.checkUnique();
        // 保存
        DB.update(model);
        // 更新关联的dict item对应的code
        if (!oldCode.equals(model.getCode())) {
            updateDictItemByCode(oldCode, model.getCode());
        }
    }

    private void updateDictItemByCode(String oldCode, String newCode) {
        // 更新关联的dict item对应的code
        DB.executeUpdateSql("update sys_dict_item set code=? where code=?", newCode, oldCode);
    }

    @Override
    public PageDataRet<SysDictRet> query(SysDictQueryArgv argv) {
        // 拼装sql
        SqlWrapper wrapper = new SqlWrapper(" select * from sys_dict where 1=1 ");
        // 拼装查询sql
        if (StringUtils.isNotEmpty(argv.getId())) {
            wrapper.addCondition("id", Op.EQ, argv.getId());
        }
        if (StringUtils.isNotEmpty(argv.getName())) {
            wrapper.addCondition("name", Op.LIKE, "%" + argv.getName() + "%");
        }
        if (StringUtils.isNotEmpty(argv.getCode())) {
            wrapper.addCondition("code", Op.LIKE, "%" + argv.getCode() + "%");
        }
        if (StringUtils.isNotEmpty(argv.getAppId())) {
            wrapper.appendSql(" and (app_id = ? )", argv.getAppId());
        }
        wrapper.sortBy(" order by when_created desc ");
        // 返回结果
        return (PageDataRet<SysDictRet>) query(wrapper.getSql(), wrapper.getParams(), argv, SysDict.class, SysDictRet.class);
    }

    @Override
    public void delete(MultiIdArgv argv) {
        for (String id: argv.getIds()) {
            DB.delete(SysDict.class, id);
        }
    }
}

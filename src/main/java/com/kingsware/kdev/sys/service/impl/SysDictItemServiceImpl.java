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
import com.kingsware.kdev.sys.argv.SysDictItemArgv;
import com.kingsware.kdev.sys.argv.SysDictItemQueryArgv;
import com.kingsware.kdev.sys.model.SysDict;
import com.kingsware.kdev.sys.model.SysDictItem;
import com.kingsware.kdev.sys.model.SysRole;
import com.kingsware.kdev.sys.ret.SysDictItemRet;
import com.kingsware.kdev.sys.ret.SysDictRet;
import com.kingsware.kdev.sys.service.SysDictItemService;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.util.ArrayList;
import java.util.List;

/**
 * 字典数据实现类
 *
 * @author AndyZheng
 * @version 1.0.0
 * @date 2021/12/27 9:36 上午
 */
@Service
public class SysDictItemServiceImpl extends BaseServiceImpl implements SysDictItemService {

    @Override
    public SysDictItemRet get(String id) {
        // 查询model
        SysDictItem model = DB.findById(SysDictItem.class, id);
        // 转换成ret对象
        return BeanUtils.copyObject(model, SysDictItemRet.class);
    }

    @Override
    public void add(SysDictItemArgv argv) {
        SysDictItem model = BeanUtils.copyObject(argv, SysDictItem.class);
        if (model == null) {
            throw new BusinessException("找不到字典类型");
        }
        // 唯一性校验
        DBChecker<SysDictItem> checker =DBChecker.build(model, SysDictItem.class);
        // 名称唯一
        checker.uni("name", I18n.t("SysDictItem.name.unique", "字典名称必须唯一"));
        // 编码唯一
        checker.uni("code", I18n.t("SysDictItem.code.unique", "字典代码必须唯一"));
        // 执行校验
        checker.checkUnique();
        DB.save(model);
    }

    @Override
    public void edit(SysDictItemArgv argv) {
        SysDictItem model = DB.findById(SysDictItem.class, argv.getId());
        // 修改
        model = BeanUtils.copyObject(argv, SysDictItem.class);
        // 唯一性校验
        DBChecker<SysDictItem> checker =DBChecker.build(model, SysDictItem.class);
        // 名称唯一
        checker.uni("name", I18n.t("SysDictItem.name.unique", "字典名称必须唯一"));
        // 编码唯一
        checker.uni("code", I18n.t("SysDictItem.code.unique", "字典代码必须唯一"));
        // 执行校验
        checker.checkUnique();
        // 保存
        DB.update(model);
    }

    @Override
    public PageDataRet<SysDictItemRet> query(SysDictItemQueryArgv argv) {
        // 拼装sql
        StringBuilder builder = new StringBuilder();
        builder.append(" select sdi.*, sd.name as sys_dict_name from sys_dict_item as sdi ");
        builder.append(" left join sys_dict as sd on sdi.sys_dict_id = sd.id ");
        builder.append(" where 1=1 ");
        SqlWrapper wrapper = new SqlWrapper(builder.toString());
        // 拼装查询sql
        if (StringUtils.isNotEmpty(argv.getSysDictId())) {
            wrapper.addCondition("sys_dict_id", Op.EQ, argv.getSysDictId());
        }
        if (StringUtils.isNotEmpty(argv.getName())) {
            wrapper.addCondition("name", Op.LIKE, "%" +argv.getName() +"%");
        }
        if (StringUtils.isNotEmpty(argv.getCode())) {
            wrapper.addCondition("code", Op.LIKE, "%" +argv.getCode() +"%");
        }
        if (StringUtils.isNotEmpty(argv.getGroupName())) {
            wrapper.addCondition("group_name", Op.LIKE, "%" +argv.getGroupName() +"%");
        }
        // 返回结果
        return (PageDataRet<SysDictItemRet>) query(wrapper.getSql(), wrapper.getParams(), argv, SysDictItem.class, SysDictItemRet.class);
    }

    @Override
    public void delete(MultiIdArgv argv) {
        for (String id: argv.getIds()) {
            DB.delete(SysDictItem.class, id);
        }
    }
}

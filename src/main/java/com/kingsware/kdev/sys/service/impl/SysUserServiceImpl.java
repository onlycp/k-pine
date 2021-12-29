package com.kingsware.kdev.sys.service.impl;

import com.kingsware.kdev.core.base.BaseServiceImpl;
import com.kingsware.kdev.core.bean.MultiIdArgv;
import com.kingsware.kdev.core.bean.PageDataRet;
import com.kingsware.kdev.core.encrypt.EncryptWorker;
import com.kingsware.kdev.core.i18n.I18n;
import com.kingsware.kdev.core.orm.DB;
import com.kingsware.kdev.core.orm.DBChecker;
import com.kingsware.kdev.core.orm.SqlWrapper;
import com.kingsware.kdev.core.orm.expression.Op;
import com.kingsware.kdev.core.util.BeanUtils;
import com.kingsware.kdev.core.util.StringUtils;
import com.kingsware.kdev.sys.argv.SysUserArgv;
import com.kingsware.kdev.sys.argv.SysUserQueryArgv;
import com.kingsware.kdev.sys.model.SysUser;
import com.kingsware.kdev.sys.ret.SysUserRet;
import com.kingsware.kdev.sys.service.SysUserService;
import org.springframework.stereotype.Service;

/**
 * 用户业务实现类
 *
 * @author chen peng
 * @version 1.0.0
 * @date 2021/12/23 9:36 上午
 */
@Service
public class SysUserServiceImpl extends BaseServiceImpl implements SysUserService {

    @Override
    public SysUserRet get(String id) {
        // 查询model
        SysUser model = DB.findById(SysUser.class, id);
        // 转换成ret对象
        return (SysUserRet) model2Ret(model, SysUserRet.class);
    }

    @Override
    public void add(SysUserArgv argv) {
        SysUser model = BeanUtils.copyObject(argv, SysUser.class);
        // 设置密码
        model.setPassword(EncryptWorker.getInstance().encrypt(model.getPassword()));
        // 唯一性校验
        DBChecker<SysUser> checker =DBChecker.build(model, SysUser.class);
        // 名称唯一
        checker.uni("username", I18n.t("SysUser.username.unique", "名称必须唯一"));
        // 执行校验
        checker.checkUnique();
        // 保存
        DB.save(model);
    }

    @Override
    public void edit(SysUserArgv argv) {
        SysUser model = DB.findById(SysUser.class, argv.getId());
        model.setRealName(argv.getRealName());
        model.setMobile(argv.getMobile());
        model.setSex(argv.getSex());
        model.setPost(argv.getPost());
        model.setSysUnitId(argv.getSysUnitId());
        model.setNote(argv.getNote());
        model.setStatus(argv.getStatus());
        model.setStatus(argv.getStatus());
        // 唯一性校验
        DBChecker<SysUser> checker =DBChecker.build(model, SysUser.class);
        // 名称唯一
        checker.uni("username", I18n.t("SysUser.username.unique", "名称必须唯一"));
        // 执行校验
        checker.checkUnique();
        // 保存
        DB.update(model);
    }

    @Override
    @SuppressWarnings("unchecked")
    public PageDataRet<SysUserRet> query(SysUserQueryArgv argv) {
        // 拼装sql
        SqlWrapper wrapper = new SqlWrapper("select * from sys_user where 1=1 ");
        // 拼装查询sql
        if (StringUtils.isNotEmpty(argv.getUsername())) {
            wrapper.addCondition("username", Op.LIKE, "%" +argv.getUsername() +"%");
        }
        if (StringUtils.isNotEmpty(argv.getRealName())) {
            wrapper.addCondition("realName", Op.LIKE, "%" +argv.getRealName() +"%");
        }
        if (StringUtils.isNotEmpty(argv.getMobile())) {
            wrapper.addCondition("mobile", Op.LIKE, "%" +argv.getMobile() +"%");
        }
        if (argv.getStatus() != null) {
            wrapper.addCondition("status", Op.EQ, argv.getStatus());
        }
        return (PageDataRet<SysUserRet>) query(wrapper.getSql(), wrapper.getParams(), argv, SysUser.class, SysUserRet.class);
    }

    @Override
    public void delete(MultiIdArgv argv) {
        for (String id: argv.getIds()) {
            DB.delete(SysUser.class, id);
        }
    }
}

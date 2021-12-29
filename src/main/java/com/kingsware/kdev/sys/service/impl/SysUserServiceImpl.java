package com.kingsware.kdev.sys.service.impl;

import com.kingsware.kdev.core.auth.AppAuthProperties;
import com.kingsware.kdev.core.auth.BaseUserInfo;
import com.kingsware.kdev.core.auth.TokenUtil;
import com.kingsware.kdev.core.base.BaseServiceImpl;
import com.kingsware.kdev.core.bean.MultiIdArgv;
import com.kingsware.kdev.core.bean.PageDataRet;
import com.kingsware.kdev.core.encrypt.EncryptWorker;
import com.kingsware.kdev.core.enums.ApiSystemEnum;
import com.kingsware.kdev.core.exception.BusinessException;
import com.kingsware.kdev.core.i18n.I18n;
import com.kingsware.kdev.core.orm.DB;
import com.kingsware.kdev.core.orm.DBChecker;
import com.kingsware.kdev.core.orm.SqlWrapper;
import com.kingsware.kdev.core.orm.expression.Op;
import com.kingsware.kdev.core.util.BeanUtils;
import com.kingsware.kdev.core.util.StringUtils;
import com.kingsware.kdev.sys.argv.SysUserArgv;
import com.kingsware.kdev.sys.argv.SysUserLoginArgv;
import com.kingsware.kdev.sys.argv.SysUserQueryArgv;
import com.kingsware.kdev.sys.model.SysRole;
import com.kingsware.kdev.sys.model.SysUser;
import com.kingsware.kdev.sys.model.SysUserRole;
import com.kingsware.kdev.sys.ret.SysUserLoginRet;
import com.kingsware.kdev.sys.ret.SysUserRet;
import com.kingsware.kdev.sys.service.SysUserService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * 用户业务实现类
 *
 * @author chen peng
 * @version 1.0.0
 * @date 2021/12/23 9:36 上午
 */
@Service
public class SysUserServiceImpl extends BaseServiceImpl implements SysUserService {

    @Resource
    private AppAuthProperties appAuthProperties;

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

    @Override
    public SysUserLoginRet login(SysUserLoginArgv argv) {
        // 非空检查
        if (argv == null
                || argv.getUsername() == null
                || argv.getPassword() == null) {
            throw BusinessException.serviceThrow("请填写完整的登录信息！");
        }
        // 拼装sql
        SqlWrapper wrapper = new SqlWrapper("select * from sys_user where 1=1 ");
        wrapper.addCondition("username", Op.EQ, argv.getUsername());
        SysUser model = DB.findOne(SysUser.class, wrapper.getSql(), wrapper.getParams().toArray());
        if (model == null) {
            throw BusinessException.serviceThrow("用户名或密码有误！");
        }
        if (!EncryptWorker.getInstance().validate(argv.getPassword(), model.getPassword())) {
            throw BusinessException.serviceThrow("用户名或密码有误！");
        }
        BaseUserInfo userInfo = new BaseUserInfo();
        userInfo = BeanUtils.copyObject(model, BaseUserInfo.class);
        userInfo.setRoleIds(getRoleIds(getRolesByUserId(model.getId())));
        userInfo.setApiSystem(ApiSystemEnum.ADMIN);

        String token = TokenUtil.createToken(appAuthProperties.getTokenSecret(), appAuthProperties.getIss(), argv.getIp(), userInfo);
        SysUserLoginRet ret = new SysUserLoginRet();
        ret.setToken(token);
        return ret;
    }

    /**
     * 把List<SysRole>
     * @param list
     * @return
     */
    private String getRoleIds(List<SysUserRole> list) {
        StringBuilder roleIds = new StringBuilder();
        list.forEach(item -> {
            roleIds.append(item.getId());
        });
        return roleIds.toString();
    }

    private List<SysUserRole> getRolesByUserId(String userId) {
        // 拼装sql
        SqlWrapper wrapper = new SqlWrapper("select * from sys_user_role where 1=1 ");
        wrapper.addCondition("sys_user_id", Op.EQ, userId);
        return DB.findList(SysUserRole.class, wrapper.getSql(), wrapper.getParams().toArray());
    }

}

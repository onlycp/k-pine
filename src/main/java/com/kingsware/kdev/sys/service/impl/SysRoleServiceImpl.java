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
import com.kingsware.kdev.sys.argv.SysRoleArgv;
import com.kingsware.kdev.sys.argv.SysRoleMenuArgv;
import com.kingsware.kdev.sys.argv.SysRoleQueryArgv;
import com.kingsware.kdev.sys.model.SysRole;
import com.kingsware.kdev.sys.model.SysRoleMenu;
import com.kingsware.kdev.sys.model.SysUserRole;
import com.kingsware.kdev.sys.ret.SysRoleRet;
import com.kingsware.kdev.sys.service.SysRoleService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * 角色业务实现类
 *
 * @author chen peng
 * @version 1.0.0
 * @date 2021/12/23 9:36 上午
 */
@Service
public class SysRoleServiceImpl extends BaseServiceImpl implements SysRoleService {

    @Override
    public SysRoleRet get(String id) {
        // 查询model
        SysRole model = DB.findById(SysRole.class, id);
        // 转换成ret对象
        return (SysRoleRet) model2Ret(model, SysRoleRet.class);
    }

    @Override
    public void add(SysRoleArgv argv) {
        SysRole model = BeanUtils.copyObject(argv, SysRole.class);
        // 唯一性校验
        DBChecker<SysRole> checker =DBChecker.build(model, SysRole.class);
        // 名称唯一
        checker.uni("name", I18n.t("SysRole.name.unique", "名称必须唯一"));
        // 编码唯一
        checker.uni("code", I18n.t("SysRole.code.unique", "编码必须唯一"));
        // 执行校验
        checker.checkUnique();
        // 保存
        DB.save(model);
    }

    @Override
    public void edit(SysRoleArgv argv) {
        SysRole model = DB.findById(SysRole.class, argv.getId());
        model.setName(argv.getName());
        model.setNote(argv.getNote());
        model.setStatus(argv.getStatus());
        model.setCode(argv.getCode());
        // 唯一性校验
        DBChecker<SysRole> checker =DBChecker.build(model, SysRole.class);
        // 名称唯一
        checker.uni("name", I18n.t("SysRole.name.unique", "名称必须唯一"));
        // 编码唯一
        checker.uni("code", I18n.t("SysRole.code.unique", "编码必须唯一"));
        // 执行校验
        checker.checkUnique();
        // 保存
        DB.update(model);
    }

    @Override
    @SuppressWarnings("unchecked")
    public PageDataRet<SysRoleRet> query(SysRoleQueryArgv argv) {
        // 拼装sql
        SqlWrapper wrapper = new SqlWrapper("select * from sys_role where 1=1 ");
        // 拼装查询sql
        if (StringUtils.isNotEmpty(argv.getName())) {
            wrapper.addCondition("name", Op.LIKE, "%" +argv.getName() +"%");
        }
        if (StringUtils.isNotEmpty(argv.getCode())) {
            wrapper.addCondition("code", Op.LIKE, "%" +argv.getCode() +"%");
        }
        if (argv.getStatus() != null) {
            wrapper.addCondition("status", Op.EQ, argv.getStatus());
        }
        return (PageDataRet<SysRoleRet>) query(wrapper.getSql(), wrapper.getParams(), argv, SysRole.class, SysRoleRet.class);
    }

    @Override
    public void delete(MultiIdArgv argv) {
        for (String id: argv.getIds()) {
            DB.delete(SysRole.class, id);
        }
    }

    @Override
    public void updatePermission(SysRoleMenuArgv argv) {
        // 先移除所有关联
        if (argv != null && argv.getIds() != null) {
            DB.executeUpdateSql("delete from sys_role_menu where sys_role_id=?", argv.getSysRoleId());
            saveRoleMenus(argv.getSysRoleId(), argv.getIds());
        }
    }

    private void saveRoleMenus(String roleId, Set<String> menuIds) {
        List<SysRoleMenu> models = new ArrayList<>();
        for (String menuId: menuIds) {
            SysRoleMenu roleMenu = new SysRoleMenu();
            roleMenu.setSysMenuId(menuId);
            roleMenu.setSysRoleId(roleId);
            models.add(roleMenu);
        }
        DB.saveAll(models);
    }
}

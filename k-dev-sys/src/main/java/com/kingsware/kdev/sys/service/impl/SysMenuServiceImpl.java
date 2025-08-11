package com.kingsware.kdev.sys.service.impl;

import com.kingsware.kdev.core.base.BaseServiceImpl;
import com.kingsware.kdev.core.bean.MultiIdArgv;
import com.kingsware.kdev.core.bean.PageDataRet;
import com.kingsware.kdev.core.bean.TreeDataRet;
import com.kingsware.kdev.core.mode.AppModeProperties;
import com.kingsware.kdev.core.orm.DB;
import com.kingsware.kdev.core.orm.SqlWrapper;
import com.kingsware.kdev.core.orm.expression.Op;
import com.kingsware.kdev.core.util.BeanUtils;
import com.kingsware.kdev.core.util.PageUtil;
import com.kingsware.kdev.core.util.StringUtils;
import com.kingsware.kdev.sys.argv.SysMenuArgv;
import com.kingsware.kdev.sys.argv.SysMenuQueryArgv;
import com.kingsware.kdev.sys.model.SysMenu;
import com.kingsware.kdev.sys.model.SysRole;
import com.kingsware.kdev.sys.ret.SysMenuRet;
import com.kingsware.kdev.sys.service.SysMenuService;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

/**
 * 菜单实现类
 *
 * @author chen peng
 * @version 1.0.0
 * @date 2021/12/23 9:36 上午
 */
@Service
public class SysMenuServiceImpl extends BaseServiceImpl implements SysMenuService {

    @Resource
    private AppModeProperties appModeProperties;

    @Override
    public SysMenuRet get(String id) {
        // 查询model
        SysMenu model = DB.findById(SysMenu.class, id);
        // 转换成ret对象
        return (SysMenuRet) model2Ret(model, SysMenuRet.class);
    }

    @Override
    public void add(SysMenuArgv argv) {
        SysMenu model = BeanUtils.copyObject(argv, SysMenu.class);
        // 生成path，规则为上级的path + /id
        if(StringUtils.isEmpty(model.getId())) {
            model.setId(StringUtils.getUUID());
        }
        // 设置path
        model.setPath(generateNodePath(model.getId(), argv.getParentId()));
        // 唯一性校验
//        DBChecker<SysMenu> checker =DBChecker.build(model, SysMenu.class);
        // 同级下名称唯一
//        checker.uni(new String[]{"name", "parentId"}, I18n.t("SysMenu.name.unique", "同一级别的名称要求唯一"));
        // 执行校验
//        checker.checkUnique();
        // 保存
        DB.save(model);
    }

    @Override
    public void edit(SysMenuArgv argv) {
        SysMenu model = DB.findById(SysMenu.class, argv.getId());
        model.setName(argv.getName());
        model.setIcon(argv.getIcon());
        model.setCode(argv.getCode());
        model.setRouterPath(argv.getRouterPath());
        model.setComponentPath(argv.getComponentPath());
        model.setHidden(argv.getHidden());
        model.setMenuType(argv.getMenuType());
        model.setApiCodes(argv.getApiCodes());
        model.setOpenMode(argv.getOpenMode());
        model.setKeepAlive(argv.getKeepAlive());
        model.setOrderNum(argv.getOrderNum());
        model.setStatus(argv.getStatus());
        model.setDataType(argv.getDataType());
        model.setMainMode(argv.getMainMode());
        model.setSidebarNavMode(argv.getSidebarNavMode());
        model.setTopNavMode(argv.getTopNavMode());
        model.setPageId(argv.getPageId());
        model.setAffix(argv.getAffix());
        // 处理path， 如果单位有变动时，才需要处理
        boolean parentChanged = !Objects.equals(model.getParentId(), argv.getParentId());
        String hisParentPath = model.getPath().replace("/" + model.getId() +"/", "/");
        model.setParentId(argv.getParentId());
        String fullPath = "/" + argv.getRouterPath();

        SysMenu newParent = DB.findById(SysMenu.class, model.getParentId());
        if (!"F".equals(model.getMenuType())) {
            if (newParent != null && newParent.getPath() != null) {
                String [] pathArr = newParent.getPath().split("/");
                if (pathArr.length > 3) {
                    for (int i = pathArr.length - 3; i > 0; i--) {
                        SysMenu fatherMenu = DB.findById(SysMenu.class, pathArr[i]);
                        fullPath = fatherMenu.getRouterPath() + fullPath;
                    }
                }
                fullPath = newParent.getRouterPath() + fullPath;
            }
            if(StringUtils.isNotEmpty(fullPath) && !fullPath.startsWith("/")){
                fullPath = "/"+ fullPath;
            }
            model.setFullPath(fullPath);
        }
        // 唯一性校验
//        DBChecker<SysMenu> checker =DBChecker.build(model, SysMenu.class);
        // 同级下名称唯一
//        checker.uni(new String[]{"name", "parentId"}, I18n.t("SysMenu.name.unique", "同一级别的名称要求唯一"));
        // 执行校验
//        checker.checkUnique();
        // 保存
        DB.update(model);
        // 更新path
        if (parentChanged) {
            String newParentPath = "/";
            if (newParent != null) {
                newParentPath = newParent.getPath();
            }
            // 新老头
            String updateSql = "update sys_menu set path=replace(path,?,?) where path like ?";
            DB.executeUpdateSql(updateSql, hisParentPath, newParentPath, "%/"+ model.getId() + "/%");

        }
    }

    /**
     * 生成树的path
     * @param id        id
     * @param parentId  上级id
     * @return
     */
    private String generateNodePath(String id, String parentId) {
        // 判断上级节点是否为空
        if (StringUtils.isEmpty(parentId)) {
            return "/" + id + "/";
        }
        // 查找上级i节点
        SysMenu parentNode = DB.findById(SysMenu.class, parentId);
        if (parentNode == null) {
            return "/" + id + "/";
        }
        return parentNode.getPath() + id + "/" ;

    }

    /**
     * 修正path
     */
    private void proofreadPath() {
        List<SysMenu> list = DB.findList(SysMenu.class, "select * from sys_menu where parent_id ='' or parent_id is null");
        // 更新path
        for (SysMenu menu: list) {
            menu.setPath("/" + menu.getId() + "/");
            DB.update(menu);
            // 修正子
            List<SysMenu> c1List = DB.findList(SysMenu.class, "select * from sys_menu where parent_id =?", menu.getId());
            for (SysMenu c1: c1List) {
                c1.setPath(menu.getPath() + c1.getId() + "/");
                DB.update(c1);
                // 修正孙
                List<SysMenu> c2List = DB.findList(SysMenu.class, "select * from sys_menu where parent_id =?", c1.getId());
                for (SysMenu c2: c2List) {
                    c2.setPath(c1.getPath() + c2.getId() + "/");
                    DB.update(c2);
                }

            }
        }
    }


    @Override
    @SuppressWarnings("unchecked")
    public PageDataRet<SysMenuRet> query(SysMenuQueryArgv argv) {

        // 拼装sql
        SqlWrapper wrapper = new SqlWrapper("select * from sys_menu where 1=1 ");
        // 拼装查询sql
        if (StringUtils.isNotEmpty(argv.getName())) {
            wrapper.addCondition("name", Op.LIKE, "%" +argv.getName() +"%");
        }
        if (argv.getStatus() != null) {
            wrapper.addCondition("status", Op.EQ, argv.getStatus());
        }
        if (argv.getWithoutPlat()) {
            wrapper.appendSql("and (app_id != null and app_id !=? and app_id!=?)", "064b3b44b85a45fe87fcce88d72b2519", "e27352b192e446fda818c03e79f28e6b");
        }

        // 查询所有相关的部门
        List<SysMenu> menus = DB.findList(SysMenu.class, wrapper.getSql(), wrapper.getParams().toArray());
        // 然后通过in查询相关的部门(包括上级关系)
        Set<Object> ids = new HashSet<>();
        for (SysMenu unit: menus) {
            String[] splits = unit.getPath().split("/");
            ids.addAll(Arrays.asList(splits));
        }
        // 重新查询自己想要的
        SqlWrapper wantWrapper = new SqlWrapper("select * from sys_menu where 1=1 ");
        // 加一个不可能存在的id进去
        ids.add(StringUtils.getUUID());
        wantWrapper.in("id", ids);
        wantWrapper.sortBy("order by order_num asc");
        List<SysMenu> wantList = DB.findList(SysMenu.class, wantWrapper.getSql(), wantWrapper.getParams().toArray());
        // 将结果转为树
        List<SysMenuRet> retList = new ArrayList<>();
        List<SysMenu> roots = wantList.stream().filter(it -> StringUtils.isEmpty(it.getParentId())).collect(Collectors.toList());
        for (SysMenu root: roots) {
            retList.add(recursiveHandleRet(root, wantList));

        }
        // 转为分页查询结果（内存分页）
        return PageUtil.memoryPage(argv, retList, SysMenuRet.class);


    }

    @Override
    public List<TreeDataRet<Object>> treeOptions(String excludeId, String roleIds, boolean isMobile) {
        // 查找所有
        String[] splits = null;
        if (roleIds != null) {
            splits = roleIds.split(",");
        }
        Set<Object> ids = null;
        boolean isSuperAdmin = true;
        if (splits != null && splits.length > 0) {
            ids = Arrays.stream(splits).collect(Collectors.toSet());
            isSuperAdmin = isSuperAdmin(ids);
        }
        StringBuilder sql = new StringBuilder();
        sql.append(" select sm.id, sm.name, sm.parent_id, sm.icon, sm.code, sm.router_path, sm.component_path, sm.is_hidden, sm.menu_type, sm.api_codes, sm.open_mode, sm.keep_alive, sm.order_num, sm.status,  sm.data_type, sm.theme, sm.page_type, sm.sidebar_nav_mode, sm.top_nav_mode, sm.main_mode, sm.page_id, sm.full_path, sm.is_dev, sm.affix ");
        sql.append(" from sys_menu sm ");
//        if (!isSuperAdmin && ids != null) {
//            sql.append(" right join sys_role_menu srm on srm.sys_menu_id = sm.id ");
//        }
        sql.append(" where sm.status = 1 ");
        SqlWrapper wrapper = new SqlWrapper(sql.toString());
        if (excludeId != null) {
            wrapper.addCondition("sm.path", Op.NOT_LIKE, "%/"+ excludeId + "/%");
        }
        if (isMobile) {
            wrapper.addCondition("sm.data_type", Op.EQ, 3);
        }
        if (!isSuperAdmin) {
            wrapper.appendInSql("sm.id in (select sys_menu_id from sys_role_menu where sys_role_id in (${in})) or sm.id in('fca6ec1df0f042fd8c5fe5f4d37e3e40','78fca36a1e1a4586b4848f918727fa5f','a7081f3df2f74537b5fd0edabfd694b8','3dc07a50e4ad48adae21e65ed3a68143') ", ids);
//            wrapper.in("srm.sys_role_id", ids);
        }
//        if (appModeProperties.getDev()) {
//            wrapper.addCondition("sm.app_id is null", "");
//            if (!isSuperAdmin) {
//                wrapper.addCondition("sm.is_dev", Op.EQ, 1);
//            }
//        }
        wrapper.sortBy(" order by order_num asc ");

        List<SysMenu> list = DB.findList(SysMenu.class, wrapper.getSql(), wrapper.getParams().toArray());
        // 转为树型的返回结构
        List<TreeDataRet<Object>> retList = new ArrayList<>();
        // 先处理根节点
        List<SysMenu> roots = list.stream().filter(it -> StringUtils.isEmpty(it.getParentId())).collect(Collectors.toList());
        for (SysMenu root: roots) {
            retList.add(recursiveHandle(root, list));
        }
        return retList;
    }

    private boolean isSuperAdmin(Set<Object> ids) {
        SqlWrapper wrapper = new SqlWrapper("select * from sys_role where code = 'admin'");
        wrapper.in("id", ids);
        List<SysRole> list = DB.findList(SysRole.class, wrapper.getSql(), wrapper.getParams().toArray());
        return list != null && list.size() > 0;
    }

    /**
     * 将模型转为树ret
     * @param root     根节点
     * @param familyList 列表
     * @return         ret
     */
    private SysMenuRet recursiveHandleRet(SysMenu root, List<SysMenu> familyList) {
        // 创建树节点
        SysMenuRet ret = (SysMenuRet) model2Ret(root, SysMenuRet.class);
        for (SysMenu child: familyList) {
            if (StringUtils.isNotEmpty(child.getParentId()) && child.getParentId().equals(root.getId())) {
                ret.getChildren().add(recursiveHandleRet(child, familyList));

            }
        }
        return ret;
    }

    private TreeDataRet<Object> recursiveHandle(SysMenu root, List<SysMenu> familyList) {
        // 创建树节点
        TreeDataRet<Object> treeDataRet = new TreeDataRet<>();
        treeDataRet.setValue(root.getId());
        treeDataRet.setLabel(root.getName());
        treeDataRet.setExtraData(root);

        for (SysMenu child: familyList) {
            if (StringUtils.isNotEmpty(child.getParentId()) && child.getParentId().equals(root.getId())) {
                treeDataRet.getChildren().add(recursiveHandle(child, familyList));

            }
        }
        return treeDataRet;
    }

    @Override
    public void delete(MultiIdArgv argv) {
        for (String id: argv.getIds()) {
            DB.delete(SysMenu.class, id);
        }
    }
}

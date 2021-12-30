package com.kingsware.kdev.sys.service.impl;

import com.kingsware.kdev.core.base.BaseServiceImpl;
import com.kingsware.kdev.core.bean.MultiIdArgv;
import com.kingsware.kdev.core.bean.PageDataRet;
import com.kingsware.kdev.core.bean.TreeDataRet;
import com.kingsware.kdev.core.i18n.I18n;
import com.kingsware.kdev.core.orm.DB;
import com.kingsware.kdev.core.orm.DBChecker;
import com.kingsware.kdev.core.orm.SqlWrapper;
import com.kingsware.kdev.core.orm.expression.Expr;
import com.kingsware.kdev.core.orm.expression.Op;
import com.kingsware.kdev.core.util.BeanUtils;
import com.kingsware.kdev.core.util.StringUtils;
import com.kingsware.kdev.sys.argv.SysMenuArgv;
import com.kingsware.kdev.sys.argv.SysMenuQueryArgv;
import com.kingsware.kdev.sys.model.SysMenu;
import com.kingsware.kdev.sys.ret.SysMenuRet;
import com.kingsware.kdev.sys.service.SysMenuService;
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
        model.setId(StringUtils.getUUID());
        // 设置path
        model.setPath(generateNodePath(model.getId(), argv.getParentId()));
        // 唯一性校验
        DBChecker<SysMenu> checker =DBChecker.build(model, SysMenu.class);
        // 同级下名称唯一
        checker.uni(new String[]{"name", "parentId"}, I18n.t("SysMenu.name.unique", "同一级别的名称要求唯一"));
        // 执行校验
        checker.checkUnique();
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
        // 处理path， 如果单位有变动时，才需要处理
        boolean parentChanged = !Objects.equals(model.getParentId(), argv.getParentId());
        String hisParentPath = model.getPath().replace("/" + model.getId() +"/", "/");
        model.setParentId(argv.getParentId());
        // 唯一性校验
        DBChecker<SysMenu> checker =DBChecker.build(model, SysMenu.class);
        // 同级下名称唯一
        checker.uni(new String[]{"name", "parentId"}, I18n.t("SysMenu.name.unique", "同一级别的名称要求唯一"));
        // 执行校验
        checker.checkUnique();
        // 保存
        DB.update(model);
        // 更新path
        if (parentChanged) {
            SysMenu newParent = DB.findById(SysMenu.class, model.getParentId());
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
        PageDataRet<SysMenuRet> pageDataRet = new PageDataRet<>();
        pageDataRet.setPageSize(argv.getPageSize());
        // 计算页数
        int pageCount = retList.size() / argv.getPageSize();
        if ( retList.size() % argv.getPageSize() != 0) {
            pageCount ++;
        }
        pageDataRet.setPageCount(pageCount);
        pageDataRet.setPage(argv.getPage());
        pageDataRet.setTotal(retList.size());
        // 计算截取的起始序号
        int fromIndex = (argv.getPage()-1) * argv.getPageSize();
        int toIndex = (argv.getPage()-1) * argv.getPageSize();
        // 如果结果数量小于from
        if (retList.size() < fromIndex) {
            pageDataRet.setList(new ArrayList<>());
        }
        else if (retList.size() < toIndex) {
            pageDataRet.setList(retList.subList(fromIndex, retList.size()));
        }
        else {
            pageDataRet.setList(retList);
        }
        return pageDataRet;


    }

    @Override
    public List<TreeDataRet<Object>> treeOptions(String excludeId) {
        // 查找所有
        String sql = "select * from sys_menu where path not like ? order by order_num asc";
        List<SysMenu> list = DB.findList(SysMenu.class, sql, "%/"+ excludeId + "/%");
        // 转为树型的返回结构
        List<TreeDataRet<Object>> retList = new ArrayList<>();
        // 先处理根节点
        List<SysMenu> roots = list.stream().filter(it -> StringUtils.isEmpty(it.getParentId())).collect(Collectors.toList());
        for (SysMenu root: roots) {
            retList.add(recursiveHandle(root, list));
        }
        return retList;
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

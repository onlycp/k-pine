package com.kingsware.kdev.sys.service.impl;

import com.kingsware.kdev.core.base.BaseServiceImpl;
import com.kingsware.kdev.core.bean.BaseManageRet;
import com.kingsware.kdev.core.bean.MultiIdArgv;
import com.kingsware.kdev.core.bean.PageDataRet;
import com.kingsware.kdev.core.bean.TreeDataRet;
import com.kingsware.kdev.core.i18n.I18n;
import com.kingsware.kdev.core.orm.DB;
import com.kingsware.kdev.core.orm.DBChecker;
import com.kingsware.kdev.core.orm.PagedList;
import com.kingsware.kdev.core.orm.SqlWrapper;
import com.kingsware.kdev.core.orm.expression.Expr;
import com.kingsware.kdev.core.orm.expression.Op;
import com.kingsware.kdev.core.util.BeanUtils;
import com.kingsware.kdev.core.util.StringUtils;
import com.kingsware.kdev.sys.argv.SysUnitArgv;
import com.kingsware.kdev.sys.argv.SysUnitQueryArgv;
import com.kingsware.kdev.sys.model.SysUnit;
import com.kingsware.kdev.sys.ret.SysUnitRet;
import com.kingsware.kdev.sys.ret.SysUserRoleName;
import com.kingsware.kdev.sys.service.SysUnitService;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

/**
 * 角色业务实现类
 *
 * @author chen peng
 * @version 1.0.0
 * @date 2021/12/23 9:36 上午
 */
@Service
public class SysUnitServiceImpl extends BaseServiceImpl implements SysUnitService {

    @Override
    public SysUnitRet get(String id) {
        // 查询model
        SysUnit model = DB.findById(SysUnit.class, id);
        // 转换成ret对象
        return (SysUnitRet) model2Ret(model, SysUnitRet.class);
    }

    @Override
    public void add(SysUnitArgv argv) {
        SysUnit model = BeanUtils.copyObject(argv, SysUnit.class);
        // 生成path，规则为上级的path + /id
        if(StringUtils.isEmpty(model.getId())) {
            model.setId(StringUtils.getUUID());
        }
        // 设置path
        model.setPath(generateNodePath(model.getId(), argv.getParentId()));
        // 唯一性校验
        DBChecker<SysUnit> checker =DBChecker.build(model, SysUnit.class);
        // 同级下名称唯一
        checker.uni(new String[]{"name", "parentId"}, I18n.t("SysUnit.name.unique", "同一级别的名称要求唯一"));
        // 执行校验
        checker.checkUnique();
        // 保存
        DB.save(model);
    }

    @Override
    public void edit(SysUnitArgv argv) {
        SysUnit model = DB.findById(SysUnit.class, argv.getId());
        model.setName(argv.getName());
        model.setNote(argv.getNote());
        model.setStatus(argv.getStatus());
        model.setEmail(argv.getEmail());
        model.setLeader(argv.getLeader());
        model.setMobile(argv.getMobile());
        model.setOrderNum(argv.getOrderNum());
        // 处理path， 如果单位有变动时，才需要处理
        boolean parentChanged = !Objects.equals(model.getParentId(), argv.getParentId());
        String hisParentPath = model.getPath().replace("/" + model.getId() +"/", "/");
        model.setParentId(argv.getParentId());
        // 唯一性校验
        DBChecker<SysUnit> checker =DBChecker.build(model, SysUnit.class);
        // 同级下名称唯一
        checker.uni(new String[]{"name", "parentId"}, I18n.t("SysUnit.name.unique", "同一级别的名称要求唯一"));
        // 执行校验
        checker.checkUnique();
        // 保存
        DB.update(model);
        // 更新path
        if (parentChanged) {
            SysUnit newParent = DB.findById(SysUnit.class, model.getParentId());
            String newParentPath = "/";
            if (newParent != null) {
                newParentPath = newParent.getPath();
            }
            // 新老头
            String updateSql = "update sys_unit set path=replace(path,?,?) where path like ?";
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
        SysUnit parentNode = DB.findById(SysUnit.class, parentId);
        if (parentNode == null) {
            return "/" + id + "/";
        }
        return parentNode.getPath() + id + "/" ;

    }

    @Override
    @SuppressWarnings("unchecked")
    public PageDataRet<SysUnitRet> query(SysUnitQueryArgv argv) {

        // 拼装sql
        SqlWrapper wrapper = new SqlWrapper("select sun.id, sun.name, sun.parent_id, sun.path, sun.mobile, sun.email, sun.status, sun.note, sun.order_num, sun.who_created, sun.when_created, sun.who_modified, sun.when_modified, sun.app_id" +
                ", su.id as leader_id, su.real_name as leader from sys_unit sun left join sys_user su on sun.leader = su.id where 1=1 ");

//        SqlWrapper wrapper = new SqlWrapper("select * from sys_unit where 1=1 ");
        // 拼装查询sql
        if (StringUtils.isNotEmpty(argv.getName())) {
            wrapper.addCondition("sun.name", Op.LIKE, "%" +argv.getName() +"%");
        }
        if (argv.getStatus() != null) {
            wrapper.addCondition("sun.status", Op.EQ, argv.getStatus());
        }
        if (StringUtils.isNotEmpty(argv.getAppId())) {
            wrapper.appendSql(" and (sun.app_id = ?)", argv.getAppId());
        }
        // 查询所有相关的部门
        List<SysUnit> units = DB.findList(SysUnit.class, wrapper.getSql(), wrapper.getParams().toArray());
        // 然后通过in查询相关的部门(包括上级关系)
        Set<Object> ids = new HashSet<>();
        for (SysUnit unit: units) {
            String[] splits = unit.getPath().split("/");
            ids.addAll(Arrays.asList(splits));
        }
        // 重新查询自己想要的
        SqlWrapper wantWrapper = new SqlWrapper("select sun.id, sun.name, sun.parent_id, sun.path, sun.mobile, sun.email, sun.status, sun.note, sun.order_num, sun.who_created, sun.when_created, sun.who_modified, sun.when_modified, sun.app_id"
                + " , su.id as leader_id, su.real_name as leader from sys_unit sun left join sys_user su on sun.leader = su.id where 1=1 ");
        // 加一个不可能存在的id进去
        ids.add(StringUtils.getUUID());
        wantWrapper.in("sun.id", ids);
        List<SysUnit> wantList = DB.findList(SysUnit.class, wantWrapper.getSql(), wantWrapper.getParams().toArray());
        // 将结果转为树
        List<SysUnitRet> retList = new ArrayList<>();
        List<SysUnit> roots = wantList.stream().filter(it -> StringUtils.isEmpty(it.getParentId())).collect(Collectors.toList());
        for (SysUnit root: roots) {
            retList.add(recursiveHandleRet(root, wantList));

        }
        // 转为分页查询结果（内存分页）
        PageDataRet<SysUnitRet> pageDataRet = new PageDataRet<>();
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
        List<SysUnit> list = DB.findList(SysUnit.class, Expr.builder().add("path", "not like", "%/"+ excludeId + "/%").build());
        // 转为树型的返回结构
        List<TreeDataRet<Object>> retList = new ArrayList<>();
        // 先处理根节点
        List<SysUnit> roots = list.stream().filter(it -> StringUtils.isEmpty(it.getParentId())).collect(Collectors.toList());
        for (SysUnit root: roots) {
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
    private SysUnitRet recursiveHandleRet(SysUnit root, List<SysUnit> familyList) {
        // 创建树节点
        SysUnitRet ret = (SysUnitRet) model2Ret(root, SysUnitRet.class);
        for (SysUnit child: familyList) {
            if (StringUtils.isNotEmpty(child.getParentId()) && child.getParentId().equals(root.getId())) {
                ret.getChildren().add(recursiveHandleRet(child, familyList));

            }
        }
        return ret;
    }

    private TreeDataRet<Object> recursiveHandle(SysUnit root, List<SysUnit> familyList) {
        // 创建树节点
        TreeDataRet<Object> treeDataRet = new TreeDataRet<>();
        treeDataRet.setValue(root.getId());
        treeDataRet.setLabel(root.getName());

        for (SysUnit child: familyList) {
            if (StringUtils.isNotEmpty(child.getParentId()) && child.getParentId().equals(root.getId())) {
                treeDataRet.getChildren().add(recursiveHandle(child, familyList));

            }
        }
        return treeDataRet;
    }

    @Override
    public void delete(MultiIdArgv argv) {
        for (String id: argv.getIds()) {
            DB.delete(SysUnit.class, id);
        }
    }
}

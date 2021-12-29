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
import com.kingsware.kdev.sys.argv.SysUnitArgv;
import com.kingsware.kdev.sys.argv.SysUnitQueryArgv;
import com.kingsware.kdev.sys.model.SysUnit;
import com.kingsware.kdev.sys.ret.SysUnitRet;
import com.kingsware.kdev.sys.service.SysUnitService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
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
        model.setId(StringUtils.getUUID());
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
        // 唯一性校验
        DBChecker<SysUnit> checker =DBChecker.build(model, SysUnit.class);
        // 同级下名称唯一
        checker.uni(new String[]{"name", "parentId"}, I18n.t("SysUnit.name.unique", "同一级别的名称要求唯一"));
        // 执行校验
        checker.checkUnique();
        // 保存
        DB.update(model);
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
        SqlWrapper wrapper = new SqlWrapper("select * from sys_unit where 1=1 ");
        // 拼装查询sql
        if (StringUtils.isNotEmpty(argv.getName())) {
            wrapper.addCondition("name", Op.LIKE, "%" +argv.getName() +"%");
        }
        if (argv.getStatus() != null) {
            wrapper.addCondition("status", Op.EQ, argv.getStatus());
        }
        PageDataRet<SysUnitRet> pageDataRet = (PageDataRet<SysUnitRet>) query(wrapper.getSql(), wrapper.getParams(), argv, SysUnit.class, SysUnitRet.class);
        // 将结果转为树
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

    private TreeDataRet<Object> recursiveHandle(SysUnit root, List<SysUnit> familyList) {
        // 创建树节点
        TreeDataRet<Object> treeDataRet = new TreeDataRet<>();
        treeDataRet.setKey(root.getId());
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

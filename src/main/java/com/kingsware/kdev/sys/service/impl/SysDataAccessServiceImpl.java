package com.kingsware.kdev.sys.service.impl;

import com.kingsware.kdev.core.base.BaseServiceImpl;
import com.kingsware.kdev.core.bean.BaseRelationArgv;
import com.kingsware.kdev.core.bean.MultiIdArgv;
import com.kingsware.kdev.core.bean.PageDataRet;
import com.kingsware.kdev.core.i18n.I18n;
import com.kingsware.kdev.core.orm.DB;
import com.kingsware.kdev.core.orm.DBChecker;
import com.kingsware.kdev.core.orm.SqlWrapper;
import com.kingsware.kdev.core.orm.expression.Op;
import com.kingsware.kdev.core.util.BeanUtils;
import com.kingsware.kdev.core.util.StringUtils;
import com.kingsware.kdev.sys.argv.SysDataAccessArgv;
import com.kingsware.kdev.sys.argv.SysDataAccessQueryArgv;
import com.kingsware.kdev.sys.model.SysDataAccess;
import com.kingsware.kdev.sys.model.SysDataAccessResource;
import com.kingsware.kdev.sys.model.SysDataAccessUser;
import com.kingsware.kdev.sys.model.SysDataResource;
import com.kingsware.kdev.sys.ret.SysDataAccessRet;
import com.kingsware.kdev.sys.service.SysDataAccessService;
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
public class SysDataAccessServiceImpl extends BaseServiceImpl implements SysDataAccessService {

    @Override
    public SysDataAccessRet get(String id) {
        // 查询model
        SysDataAccess model = DB.findById(SysDataAccess.class, id);
        // 转换成ret对象
        return (SysDataAccessRet) model2Ret(model, SysDataAccessRet.class);
    }

    @Override
    public void add(SysDataAccessArgv argv) {
        SysDataAccess model = BeanUtils.copyObject(argv, SysDataAccess.class);
        // 校验
        checkUnique(model);
        // 保存
        DB.save(model);
    }

    @Override
    public void edit(SysDataAccessArgv argv) {
        SysDataAccess model = DB.findById(SysDataAccess.class, argv.getId());
        model.setName(argv.getName());
        model.setStatus(argv.getStatus());
        model.setNote(argv.getNote());
        // 校验
        checkUnique(model);
        // 保存
        DB.update(model);
    }

    private void checkUnique(SysDataAccess model) {
        // 唯一性校验
        DBChecker<SysDataAccess> checker =DBChecker.build(model, SysDataAccess.class);
        // 名称唯一
        checker.uni("name", I18n.t("SysDataAccess.name.unique", "名称必须唯一"));
        // 执行校验
        checker.checkUnique();
    }

    @Override
    @SuppressWarnings("unchecked")
    public PageDataRet<SysDataAccessRet> query(SysDataAccessQueryArgv argv) {
        // 拼装sql
        SqlWrapper wrapper = new SqlWrapper("select * from sys_data_access where 1=1 ");
        // 拼装查询sql
        if (StringUtils.isNotEmpty(argv.getName())) {
            wrapper.addCondition("name", Op.LIKE, "%" +argv.getName() +"%");
        }
        return (PageDataRet<SysDataAccessRet>) query(wrapper.getSql(), wrapper.getParams(), argv, SysDataAccess.class, SysDataAccessRet.class);
    }

    @Override
    public void delete(MultiIdArgv argv) {
        for (String id: argv.getIds()) {
            DB.delete(SysDataAccess.class, id);
        }
    }

    @Override
    public List<String> querySelectedUserIds(String id) {
        SqlWrapper wrapper = new SqlWrapper("select sys_user_id from sys_data_access_user where 1=1 ");
        wrapper.addCondition("sys_data_access_id", Op.EQ, id);
        return DB.findSingleAttributeList(String.class, wrapper.getSql(), wrapper.getParams().toArray());
    }

    @Override
    public void saveDataAccessUser(BaseRelationArgv baseRelationArgv) {
        // 先移除旧的数据
        SqlWrapper wrapper = new SqlWrapper("delete from sys_data_access_user where 1=1 ");
        wrapper.addCondition("sys_data_access_id", Op.EQ, baseRelationArgv.getId());
        DB.executeUpdateSql(wrapper.getSql(), wrapper.getParams().toArray());
        // 保存新的数据
        if (baseRelationArgv.getRelationIds() != null && !baseRelationArgv.getRelationIds().isEmpty()) {
            List<SysDataAccessUser> dataAccessUsers = new ArrayList<>();
            for (String rid: baseRelationArgv.getRelationIds()) {
                SysDataAccessUser da = new SysDataAccessUser();
                da.setSysDataAccessId(baseRelationArgv.getId());
                da.setSysUserId(rid);
                dataAccessUsers.add(da);
            }
            DB.saveAll(dataAccessUsers);
        }

    }

    @Override
    public List<String> querySelectedDataIds(String resourceId, String id) {
        // 获取数据配置定义
        SysDataResource resource = DB.findById(SysDataResource.class, resourceId);
        // 查询相关数据
        SqlWrapper wrapper = new SqlWrapper("select data_id from sys_data_access_resource where 1=1 ");
        wrapper.addCondition("access_id", Op.EQ, id);
        wrapper.addCondition("table_name", Op.EQ, resource.getTableName());
        return DB.findSingleAttributeList(String.class, wrapper.getSql(), wrapper.getParams().toArray());
    }

    @Override
    public void saveDataAccessResource(String resourceId, BaseRelationArgv baseRelationArgv) {
        // 获取数据配置定义
        SysDataResource resource = DB.findById(SysDataResource.class, resourceId);
        // 先移除旧的数据
        SqlWrapper wrapper = new SqlWrapper("delete from sys_data_access_resource where 1=1 ");
        wrapper.addCondition("access_id", Op.EQ, baseRelationArgv.getId());
        wrapper.addCondition("table_name", Op.EQ, Op.EQ, resource.getTableName());
        DB.executeUpdateSql(wrapper.getSql(), wrapper.getParams().toArray());
        // 保存新的数据
        if (baseRelationArgv.getRelationIds() != null && !baseRelationArgv.getRelationIds().isEmpty()) {
            List<SysDataAccessResource> dataAccessResources = new ArrayList<>();
            for (String rid: baseRelationArgv.getRelationIds()) {
                SysDataAccessResource da = new SysDataAccessResource();
                da.setAccessId(baseRelationArgv.getId());
                da.setDataId(rid);
                da.setTableName(resource.getTableName());
                dataAccessResources.add(da);
            }
            DB.saveAll(dataAccessResources);
        }
    }
}

package com.kingsware.kdev.sys.service.impl;

import com.kingsware.kdev.core.base.BaseServiceImpl;
import com.kingsware.kdev.core.bean.MultiIdArgv;
import com.kingsware.kdev.core.bean.PageDataRet;
import com.kingsware.kdev.core.cache.api.ApiInfo;
import com.kingsware.kdev.core.cache.api.ApiManager;
import com.kingsware.kdev.core.i18n.I18n;
import com.kingsware.kdev.core.orm.DB;
import com.kingsware.kdev.core.orm.DBChecker;
import com.kingsware.kdev.core.orm.SqlWrapper;
import com.kingsware.kdev.core.orm.expression.Op;
import com.kingsware.kdev.core.util.BeanUtils;
import com.kingsware.kdev.core.util.StringUtils;
import com.kingsware.kdev.sys.argv.SysApiArgv;
import com.kingsware.kdev.sys.argv.SysApiQueryArgv;
import com.kingsware.kdev.sys.model.SysApi;
import com.kingsware.kdev.sys.ret.SysApiRet;
import com.kingsware.kdev.sys.service.SysApiService;
import org.springframework.stereotype.Service;

/**
 * 角色业务实现类
 *
 * @author chen peng
 * @version 1.0.0
 * @date 2021/12/23 9:36 上午
 */
@Service
public class SysApiServiceImpl extends BaseServiceImpl implements SysApiService {

    @Override
    public SysApiRet get(String id) {
        // 查询model
        SysApi model = DB.findById(SysApi.class, id);
        // 转换成ret对象
        return (SysApiRet) model2Ret(model, SysApiRet.class);
    }

    @Override
    public void add(SysApiArgv argv) {
        SysApi model = BeanUtils.copyObject(argv, SysApi.class);
        // 唯一校验
        checkUnique(model);
        // 保存
        DB.save(model);
        // 缓存api
        cacheApi(model.getId());
    }

    @Override
    public void edit(SysApiArgv argv) {
        SysApi model = DB.findById(SysApi.class, argv.getId());
        BeanUtils.copyProperties(argv, model);
        // 唯一校验
        checkUnique(model);
        // 保存
        DB.update(model);
        // 缓存api
        cacheApi(model.getId());

    }

    /**
     * 同步api到缓存
     * @param id id
     */
    private void cacheApi(String id) {
        // 同步到缓存中
        String sql = "select t0.*, t1.in_argv, t1.out_argv from sys_api t0 left join sys_logic_flow t1 on t1.flow_id=t0.api_flow_id where t0.api_url is not null and t0.api_method is not null and t0.id=?";
        ApiInfo apiInfo = DB.findOne(ApiInfo.class, sql, id);
        if (apiInfo != null) {
            ApiManager.getInstance().addOrUpdateApi(apiInfo);
        }

    }

    private void checkUnique(SysApi model) {
        // 唯一性校验
        DBChecker<SysApi> checker =DBChecker.build(model, SysApi.class);
        // 名称唯一
        checker.uni("apiCode", I18n.t("SysApi.apiCode.unique", "接口编码必须唯一"));
        // 执行校验
        checker.checkUnique();
    }

    @Override
    @SuppressWarnings("unchecked")
    public PageDataRet<SysApiRet> query(SysApiQueryArgv argv) {
        // 拼装sql
        SqlWrapper wrapper = new SqlWrapper("select * from sys_api where 1=1 ");
        // 拼装查询sql
        if (StringUtils.isNotEmpty(argv.getApiName())) {
            wrapper.addCondition("api_name", Op.LIKE, "%" +argv.getApiName() +"%");
        }
        if (StringUtils.isNotEmpty(argv.getApiCode())) {
            wrapper.addCondition("api_code", Op.LIKE, "%" +argv.getApiCode() +"%");
        }
        if (StringUtils.isNotEmpty(argv.getApiFlowId())) {
            wrapper.addCondition("api_flow_id", Op.LIKE, "%" +argv.getApiFlowId() +"%");
        }
        if (StringUtils.isNotEmpty(argv.getApiMethod())) {
            wrapper.addCondition("api_method", Op.LIKE, "%" +argv.getApiMethod() +"%");
        }
        if (StringUtils.isNotEmpty(argv.getApiUrl())) {
            wrapper.addCondition("api_url", Op.LIKE, "%" +argv.getApiUrl() +"%");
        }
        if (StringUtils.isNotEmpty(argv.getApiTags())) {
            wrapper.addCondition("api_tags", Op.LIKE, "%" +argv.getApiTags() +"%");
        }
        if (StringUtils.isNotEmpty(argv.getAppId())) {
//            wrapper.addCondition("app_id", Op.EQ, argv.getAppId());
            wrapper.appendSql(" and (app_id = ? or app_id is null)", argv.getAppId());
        }
        if (argv.getCallType() != null) {
            wrapper.addCondition("call_type", Op.EQ, argv.getCallType());
        }
        wrapper.sortBy("when_created desc");
        return (PageDataRet<SysApiRet>) query(wrapper.getSql(), wrapper.getParams(), argv, SysApi.class, SysApiRet.class);
    }

    @Override
    public void delete(MultiIdArgv argv) {
        for (String id: argv.getIds()) {
            DB.delete(SysApi.class, id);
            ApiManager.getInstance().removeApi(id);
        }
    }
}

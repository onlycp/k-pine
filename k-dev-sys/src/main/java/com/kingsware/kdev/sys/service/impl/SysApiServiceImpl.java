package com.kingsware.kdev.sys.service.impl;

import com.kingsware.kdev.core.auth.TokenUtil;
import com.kingsware.kdev.core.base.BaseServiceImpl;
import com.kingsware.kdev.core.bean.BaseRet;
import com.kingsware.kdev.core.bean.MultiIdArgv;
import com.kingsware.kdev.core.bean.PageDataRet;
import com.kingsware.kdev.core.cache.api.ApiInfo;
import com.kingsware.kdev.core.cache.api.ApiManager;
import com.kingsware.kdev.core.cache.instance.InstanceManager;
import com.kingsware.kdev.core.context.SpringContext;
import com.kingsware.kdev.core.exception.BusinessException;
import com.kingsware.kdev.core.i18n.I18n;
import com.kingsware.kdev.core.kflow.bean.KdbRetFile;
import com.kingsware.kdev.core.model.SysLogicFlow;
import com.kingsware.kdev.core.orm.DB;
import com.kingsware.kdev.core.orm.DBChecker;
import com.kingsware.kdev.core.orm.SqlWrapper;
import com.kingsware.kdev.core.orm.expression.Op;
import com.kingsware.kdev.core.util.*;
import com.kingsware.kdev.sys.argv.CopyContextArgv;
import com.kingsware.kdev.sys.argv.SysApiArgv;
import com.kingsware.kdev.sys.argv.SysApiQueryArgv;
import com.kingsware.kdev.sys.bean.CopyProcessData;
import com.kingsware.kdev.sys.manager.CopyAppManager;
import com.kingsware.kdev.sys.manager.UniOpsTokenStore;
import com.kingsware.kdev.sys.model.SysApi;
import com.kingsware.kdev.sys.ret.SysApiRet;
import com.kingsware.kdev.sys.service.SysApiService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * 角色业务实现类
 *
 * @author chen peng
 * @version 1.0.0
 * @date 2021/12/23 9:36 上午
 */
@Service
public class SysApiServiceImpl extends BaseServiceImpl implements SysApiService {

    @Value("${app.mode.dev:false}")
    private boolean modeDev;

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
    @Override
    public void cacheApi(String id) {
        // 同步到缓存中
        String sql = "select t0.*, t1.in_argv, t1.out_argv from sys_api t0 left join sys_logic_flow t1 on t1.flow_id=t0.api_flow_id where t0.api_url is not null and t0.api_method is not null and t0.id=?";
        ApiInfo apiInfo = DB.findOne(ApiInfo.class, sql, id);
        if (apiInfo != null) {
            InstanceManager.getInstance().broadMessage("api-add-update", JsonUtil.toJson(apiInfo));
        }

    }

    @Override
    public void checkUnique(SysApi model) {
        // 唯一性校验
        DBChecker<SysApi> checker =DBChecker.build(model, SysApi.class);
        // 名称唯一
        if (StringUtils.isNotEmpty(model.getApiCode()) && !model.getApiCode().trim().equals(":open")) {
            checker.uni("apiCode", I18n.t("SysApi.apiCode.unique", model.getApiCode() + "接口编码必须唯一"));
        }

        checker.uni(new String[]{"apiUrl", "apiMethod"}, I18n.t("SysApi.url.unique", model.getApiUrl() + "请求路径已存在"));
        // 执行校验
        checker.checkUnique();
    }

    @Override
    @SuppressWarnings("unchecked")
    public PageDataRet<SysApiRet> query(SysApiQueryArgv argv) {
        // 拼装sql
        SqlWrapper wrapper = new SqlWrapper("select sa.id, sa.api_name,sa.api_url,sa.api_note,sa.api_tags,sa.api_method,sa.api_argv_type," +
                "sa.api_req_argv,sa.api_rsp_argv,sa.api_result_handler,sa.who_created,sa.when_created,sa.who_modified,sa.when_modified," +
                "lf.flow_id api_flow_id,sa.api_code,sa.call_type,sa.app_id,sa.module_id,sa.cache_enable, sa.cache_cron, sa.cache_expire_time " +
                " from sys_api sa left join sys_logic_flow lf  on lf.flow_id=sa.api_flow_id where 1=1 ");
        // 拼装查询sql
        if (StringUtils.isNotEmpty(argv.getApiName())) {
            wrapper.addCondition("sa.api_name", Op.LIKE, "%" +argv.getApiName() +"%");
        }
        if (StringUtils.isNotEmpty(argv.getApiCode())) {
            wrapper.addCondition("sa.api_code", Op.LIKE, "%" +argv.getApiCode() +"%");
        }
        if (StringUtils.isNotEmpty(argv.getApiFlowId())) {
            wrapper.addCondition("sa.api_flow_id", Op.LIKE, "%" +argv.getApiFlowId() +"%");
        }
        if (StringUtils.isNotEmpty(argv.getApiMethod())) {
            wrapper.addCondition("sa.api_method", Op.LIKE, "%" +argv.getApiMethod() +"%");
        }
        if (StringUtils.isNotEmpty(argv.getApiUrl())) {
            wrapper.addCondition("sa.api_url", Op.LIKE, "%" +argv.getApiUrl() +"%");
        }
        if (StringUtils.isNotEmpty(argv.getApiTags())) {
            wrapper.addCondition("sa.api_tags", Op.LIKE, "%" +argv.getApiTags() +"%");
        }
        if (StringUtils.isNotEmpty(argv.getAppId())) {
//            wrapper.addCondition("app_id", Op.EQ, argv.getAppId());
            wrapper.appendSql(" and (sa.app_id = ?)", argv.getAppId());
        }
        if (argv.getCallType() != null) {
            wrapper.addCondition("sa.call_type", Op.EQ, argv.getCallType());
        }
        wrapper.sortBy("sa.when_created desc");
        return (PageDataRet<SysApiRet>) query(wrapper.getSql(), wrapper.getParams(), argv, SysApiRet.class);
    }

    @Override
    public void delete(MultiIdArgv argv) {
        for (String id: argv.getIds()) {
            DB.delete(SysApi.class, id);
            InstanceManager.getInstance().broadMessage("api-delete", id);
        }
    }



    /**
     * 调用uniops接口
     *
     * @return 调用数据
     */
    @Override
    public BaseRet<?> callUniops(Map<String, Object> params) {
        HttpServletRequest request = ServletUtil.request();
        HttpServletResponse response = ServletUtil.response();
        String username = SpringContext.getProperties("uniops.user", "admin");
        String password = SpringContext.getProperties("uniops.pwd", "WzcwLDIwNiwxMTUsNTksNjUsMTk1LDIzMiw5OSwxMDksOTAsMTM3LDcyLDYsMTQxLDkxLDE1OF0=");
        String uniopsServer = SpringContext.getProperties("uniops.server", "http://localhost:8080");
        // 获取令牌
        String token = null;
        if(modeDev) {
            token = UniOpsUtil.getUniOpsToken(uniopsServer, username, password);
        }
        else {
            token = UniOpsTokenStore.getInstance().getUniOpsToken(TokenUtil.getTokenString(request));
        }
        // 获取uniops地址
        if (!params.containsKey("url")) {
            throw BusinessException.serviceThrow(I18n.t("SysApiServiceImpl.urlEmptyTip", "缺少url参数"));
        }
        // 获取请求路径和方法
        String url = params.get("url").toString();
        String method = "get";
        String callUrl = url;
        String[] arr = url.split(":");
        if (arr.length == 2) {
            method = arr[0];
            callUrl = arr[1];
        }
        String apiUrl = uniopsServer + callUrl;
        // 封装请求头
        Map<String, String> headers = new HashMap<>();
        headers.put("token", token);
        String responseBody = null;
        if ("get".equalsIgnoreCase(method)) {
            responseBody = HttpUtil.get(apiUrl, headers);
        }
        else if ("post".equalsIgnoreCase(method)) {
            // 移除url
            Map<String, Object> bodyMap = new HashMap<>(params);
            bodyMap.remove("url");
            // 发起请求
            responseBody = HttpUtil.postBody(apiUrl, JsonUtil.toJson(bodyMap), headers, true);
        }
        else {
            throw BusinessException.serviceThrow(I18n.t("SysApiServiceImpl.onlyGetPostTip", "当前仅支持get和post请求!"));
        }
        Map<String, Object> retMap = JsonUtil.toMap(responseBody);
        int errorCode = (int)retMap.get("errorCode");
        if (errorCode != 0) {
            Object msg = retMap.getOrDefault("message", I18n.t("SysApiServiceImpl.uniopsCallFail", "uniops接口调用失败"));
            throw BusinessException.serviceThrow(msg == null ? I18n.t("SysApiServiceImpl.uniopsCallFail", "uniops接口调用失败"): msg.toString());
        }
        else {
            Object msg = retMap.getOrDefault("message", I18n.t("common.success", "成功"));
            if (msg == null) {
                msg = I18n.t("common.success", "成功");
            }
            if (retMap.containsKey("responseBody")) {
                return BaseRet.success(retMap.get("responseBody"), msg.toString());
            }
            else {
                return BaseRet.successMessage(msg.toString());
            }
        }
    }



    @Override
    public void copyData(String id, CopyContextArgv context) {
        // 拷贝
        CopyProcessData copyProcessData = new CopyProcessData();
        // 拷贝
        CopyAppManager.getInstance().copyApiData(id, context, copyProcessData);
        // 开始
        CopyAppManager.getInstance().action(copyProcessData, context);
    }

    @Override
    public void exportPine(MultiIdArgv argv) {

        CopyContextArgv contextArgv = new CopyContextArgv();
        contextArgv.setDeepCopy(1);
        contextArgv.setUrlSuffix("v1");
        contextArgv.setCodeSuffix("v1");
        contextArgv.setTargetAppId("hello-world");
        contextArgv.setSourceAppId("hello-world");
        contextArgv.setWithSystemData(1);
        contextArgv.setNameSuffix("hello-world");
        CopyProcessData copyProcessData = new CopyProcessData();
        for (String id: argv.getIds()) {
            CopyAppManager.getInstance().copyApiData(id, contextArgv, copyProcessData);
        }
        KdbRetFile retFile = CopyAppManager.getInstance().exportPine(copyProcessData);
        ServletUtil.responseFile(ServletUtil.response(), "Api" + DateUtils.formatDate(new Date(), DateUtils.DATE_TIME_1) + ".pine", retFile.getData());
    }


}

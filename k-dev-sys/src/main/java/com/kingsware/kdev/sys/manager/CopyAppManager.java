package com.kingsware.kdev.sys.manager;

import com.jayway.jsonpath.DocumentContext;
import com.jayway.jsonpath.JsonPath;
import com.kingsware.kdev.core.bean.JsonPathSearchResult;
import com.kingsware.kdev.core.cache.api.ApiInfo;
import com.kingsware.kdev.core.cache.api.ApiManager;
import com.kingsware.kdev.core.context.SpringContext;
import com.kingsware.kdev.core.exception.BusinessException;
import com.kingsware.kdev.core.i18n.I18n;
import com.kingsware.kdev.core.kflow.KFlowContext;
import com.kingsware.kdev.core.kflow.KdbFlowExecutor;
import com.kingsware.kdev.core.kflow.bean.KdbFlowResult;
import com.kingsware.kdev.core.kflow.bean.KdbRetFile;
import com.kingsware.kdev.core.model.DevPage;
import com.kingsware.kdev.core.model.SysLogicFlow;
import com.kingsware.kdev.core.model.SysTask;
import com.kingsware.kdev.core.orm.DB;
import com.kingsware.kdev.core.orm.kdb.AddFlowInfo;
import com.kingsware.kdev.core.orm.kdb.FlowInfo;
import com.kingsware.kdev.core.orm.kdb.KdbApi;
import com.kingsware.kdev.core.util.BeanUtils;
import com.kingsware.kdev.core.util.JsonUtil;
import com.kingsware.kdev.core.util.StringUtils;
import com.kingsware.kdev.sys.argv.CopyContextArgv;
import com.kingsware.kdev.sys.bean.ApplicationConfig;
import com.kingsware.kdev.sys.bean.CopyProcessData;
import com.kingsware.kdev.sys.bean.ExportData;
import com.kingsware.kdev.sys.bean.ExportRootData;
import com.kingsware.kdev.sys.model.*;
import com.kingsware.kdev.sys.service.DevPageService;
import com.kingsware.kdev.sys.service.SysApiService;
import lombok.extern.slf4j.Slf4j;
import net.minidev.json.JSONArray;

import java.nio.charset.StandardCharsets;
import java.util.*;
import java.util.concurrent.atomic.AtomicReference;

import com.kingsware.kdev.core.config.SysConst;

import static com.kingsware.kdev.core.config.SysConst.pineAppId;

/**
 * 应用拷贝统一管理类
 *
 * @author chenp
 * @date 2023/3/20
 */
@Slf4j
public class CopyAppManager {

    private static CopyAppManager instance;

    public static CopyAppManager getInstance() {
        if (instance == null) {
            instance = new CopyAppManager();
        }
        return instance;
    }

    private CopyAppManager() {
    }

    /**
     * 拷贝逻辑编排
     *
     * @param id          逻辑编排id
     * @param copyContext 拷贝参数
     */

    public void copyFlowData(String id, CopyContextArgv copyContext, CopyProcessData copyProcessData) {


        // 查找逻辑编排
        SysLogicFlow sysLogicFlow = DB.findById(SysLogicFlow.class, id);
        if (sysLogicFlow == null) {
            return;
        }
        // 获取faas的逻辑编排
        KdbApi kdbApi = (KdbApi) (DB.getDefault());
        // 查询逻辑编排数据
        FlowInfo faasFlow = kdbApi.get(sysLogicFlow.getFlowId());
        if (faasFlow == null) {
            return;
        }
        // 查询子流程
        if(copyContext.getDeepCopy() == 1){
            String faasContent = faasFlow.getContent();
            List<String> childFaasFlowIds = JsonPath.read(faasContent, "$.node_definition[*].flowID");

            for (String childFaasId : childFaasFlowIds) {
                // 查找子流程
                SysLogicFlow childLogicFlow = DB.findOne(SysLogicFlow.class, "select * from sys_logic_flow where flow_id=?", childFaasId);
                // 如果是个青松应用，当前是不拷贝基础数据，那就跳过
                if (childLogicFlow == null ||  pineAppId.equalsIgnoreCase(childLogicFlow.getApplicationId()) && copyContext.getWithSystemData() == 0) {
                    continue;
                }
                // 如果已存在，则跳过
                if (copyProcessData.getFaasFlowIds().contains(childLogicFlow.getId())) {
                    continue;
                }
                this.copyFlowData(childLogicFlow.getId(), copyContext, copyProcessData);
            }
        }
        // 加入到待拷贝列表
        String logicDbName = sysLogicFlow.getName();
        if (StringUtils.isNotEmpty(copyContext.getNameSuffix())) {
            logicDbName = String.format("%s-%s", sysLogicFlow.getName(), copyContext.getNameSuffix());
        }
        // 只有不唯一，并且不参与关联的字段
        sysLogicFlow.setName(logicDbName);
        copyProcessData.addCopyObject(sysLogicFlow);
        copyProcessData.addMapping(sysLogicFlow.getId(), StringUtils.getUUID());
        // 拷贝FAAS里的逻辑编排
        String logicFaasName = faasFlow.getName();
        if (StringUtils.isNotEmpty(copyContext.getNameSuffix())) {
            logicFaasName = String.format("%s-%s", faasFlow.getName(), copyContext.getNameSuffix());
        }
        AddFlowInfo addFlowInfo = BeanUtils.copyObject(faasFlow, AddFlowInfo.class);
        addFlowInfo.setName(logicFaasName);
        copyProcessData.addCopyObject(addFlowInfo);
        copyProcessData.addMapping(addFlowInfo.getFlowId(), StringUtils.getUUID());

    }

    /**
     * 拷贝逻辑编排
     *
     * @param id          逻辑编排id
     * @param copyContext 拷贝参数
     */

    public void copyApiData(String id, CopyContextArgv copyContext, CopyProcessData copyProcessData) {
        // 查找逻辑编排
        SysApi sysApi = DB.findById(SysApi.class, id);
        if (sysApi == null) {
            return;
        }
        // 新名称
        String apiName = sysApi.getApiName();
        if (StringUtils.isNotEmpty(copyContext.getNameSuffix())) {
            apiName = String.format("%s-%s", sysApi.getApiName(), copyContext.getNameSuffix());
        }
        sysApi.setApiName(apiName);
        if (StringUtils.isNotEmpty(sysApi.getApiCode()) && !":open".equalsIgnoreCase(sysApi.getApiCode())) {
            sysApi.setApiCode(String.format("%s-%s", sysApi.getApiCode(), copyContext.getCodeSuffix()));
        }
        if (StringUtils.isNotEmpty(sysApi.getApiUrl())) {
            sysApi.setApiUrl(String.format("%s-%s", sysApi.getApiUrl(), copyContext.getUrlSuffix()));
        }
        // 拷贝FAAS里的逻辑编排
        copyProcessData.addCopyObject(sysApi);
        copyProcessData.addMapping(sysApi.getId(), StringUtils.getUUID());
        if (copyContext.getDeepCopy() == 1) {
            // 拷贝逻辑编排
            List<SysLogicFlow> flows = DB.findList(SysLogicFlow.class, "select * from sys_logic_flow where flow_id=?", sysApi.getApiFlowId());
            for (SysLogicFlow flow: flows) {
                if (pineAppId.equalsIgnoreCase(flow.getApplicationId()) && copyContext.getWithSystemData() == 0) {
                    continue;
                }

                this.copyFlowData(flow.getId(), copyContext, copyProcessData);
            }

        }


    }


    /**
     * 拷贝页面
     *
     * @param id          逻辑编排id
     * @param copyContext 拷贝参数
     */

    public void copyPageData(String id, CopyContextArgv copyContext, CopyProcessData copyProcessData) {
        // 查找逻辑编排
        DevPage devPage = DB.findById(DevPage.class, id);
        if (devPage == null) {
            return;
        }
        // 新名称
        String pageName = devPage.getName();
        if (StringUtils.isNotEmpty(copyContext.getNameSuffix())) {
            pageName = String.format("%s-%s", devPage.getName(), copyContext.getNameSuffix());
        }
        devPage.setName(pageName);
        if (StringUtils.isNotEmpty(devPage.getPath())) {
            devPage.setPath(String.format("%s-%s", devPage.getPath(), copyContext.getCodeSuffix()));
        }
        // 拷贝FAAS里的逻辑编排
        copyProcessData.addCopyObject(devPage);
        copyProcessData.addMapping(devPage.getId(), StringUtils.getUUID());
        if (copyContext.getDeepCopy() == 1 && StringUtils.isNotEmpty(devPage.getPageJson())) {
            DocumentContext documentContext = JsonPath.parse(devPage.getPageJson());
            // region 接口
            {
                String apiMatchTags = SpringContext.getBootProperties("app.amis.api-tags", "api,initApi,schemaApi,addApi,editApi,deleteApi, deferApi,searchApi,quickSaveApi,checkApi,source,url");
                // 查找所有的接口段
                Set<JsonPathSearchResult> apiList = this.parseByPath(documentContext, apiMatchTags);
                // 查找对应的api
                for (JsonPathSearchResult segment: apiList) {
                    // 如果是字符串
                    String apiMethod = "get";
                    String apiUrl = "";
                    String url = segment.getValue().get(segment.getField()).toString();
                    // 只有包含/才认为是有效的url
                    if (!url.contains("/")) {
                        continue;
                    }
                    apiUrl = url;
                    if (url.contains(":")) {
                        String[] arr = url.split(":");
                        apiUrl = arr[1];
                        apiMethod = arr[0];
                    }
                    if (segment.getValue().containsKey("method")) {
                        apiMethod = segment.getValue().get("method").toString();
                    }
                    // 通过url查找接口信息
                    if (StringUtils.isNotEmpty(apiUrl)) {
                        if (apiUrl.contains("?")) {
                            apiUrl = apiUrl.split("\\?")[0];
                        }
                        apiUrl = apiUrl.replace("{rootPath}","");
                        // 查找api
                        ApiInfo apiInfo = ApiManager.getInstance().getApi(apiMethod, apiUrl);
                        if (apiInfo != null) {
                            if (pineAppId.equalsIgnoreCase(apiInfo.getAppId()) && copyContext.getWithSystemData() == 0) {
                                continue;
                            }
                            log.info("拷贝api:{}, name:{}, url:{}", apiInfo.getId(), apiInfo.getApiName(), apiInfo.getApiUrl());
                            this.copyApiData(apiInfo.getId(), copyContext, copyProcessData);
                            String url1 = String.format("%s-%s", apiUrl, copyContext.getUrlSuffix());
                            String afterUrl = apiUrl.replace(url, url1);
                            Map<String, Object> finalMap = JsonUtil.toMap(JsonUtil.toJson(segment.getValue()));
                            finalMap.put(segment.getField(), afterUrl);
                            try {
                                documentContext.set(segment.getPath(), finalMap);
                            }
                            catch (Exception e) {
                                log.warn("warn", e);
                            }

                        }
                    }
                }
            }
            // endregion 接口
            // region 字典
            {
                String dictMatchTags = SpringContext.getBootProperties("app.amis.dict-tags", "source");
                // 查找所有用到的
                Set<JsonPathSearchResult> list = this.parseByPath(documentContext, dictMatchTags);
                for (JsonPathSearchResult segment: list) {
                    // 只有字符串类型才处理
                    String body = segment.getValue().get(segment.getField()).toString();
                    if (body.contains("$") && body.contains("|") &&body.contains("filterDictList") && body.contains("filterDictValue")) {
                        String cleanBody = body.replace("$", "")
                                .replace("{", "")
                                .replace("}", "")
                                .replace("'", "");
                        String dictCode = null;
                        if (cleanBody.contains("filterDictList")) {
                            dictCode = cleanBody.split("\\|")[0].trim();
                        }
                        else {
                            dictCode = cleanBody.split(":")[1].trim();
                        }
                        if (StringUtils.isEmpty(dictCode)) {
                            continue;
                        }
                        // 查找字典管理
                        SysDict dict = DB.findOne(SysDict.class, "select * from sys_dict where code=?", dictCode);
                        if (dict == null) {
                            continue;
                        }
                        if (pineAppId.equalsIgnoreCase(dict.getAppId()) && copyContext.getWithSystemData() == 0) {
                            continue;
                        }
                        this.copyDictData(dict.getId(), copyContext, copyProcessData);
                    }
                }
            }
            devPage.setPageJson(documentContext.jsonString());
            // endregion 字典

        }
    }


    /**
     * 拷贝字典
     *
     * @param id          字典id
     * @param copyContext 拷贝参数
     */

    public void copyDictData(String id, CopyContextArgv copyContext, CopyProcessData copyProcessData) {

//        SysDict sysDict = DB.findById(SysDict.class, id);
//        if (sysDict == null) {
//            return;
//        }
//        //String newCode = String.format("%s-%s", sysDict.getCode(), copyContext.getCodeSuffix());
//        //String oldCode = sysDict.getCode();
//        // sysDict.setCode(String.format("%s-%s", sysDict.getCode(), copyContext.getCodeSuffix()));
//        // 拷贝FAAS里的逻辑编排
//        copyProcessData.addCopyObject(sysDict);
//        copyProcessData.addMapping(sysDict.getId(), StringUtils.getUUID());
//        // copyProcessData.addMapping(oldCode, newCode);
//        // 拷贝字典项
//        List<SysDictItem> sysDictItems = DB.findList(SysDictItem.class, "select * from sys_dict_item where sys_dict_id=?", id);
//        for (SysDictItem dictItem: sysDictItems) {
//            copyProcessData.addCopyObject(dictItem);
//            copyProcessData.addMapping(dictItem.getId(), StringUtils.getUUID());
//
//        }

    }

    /**
     * 拷贝系统配置
     *
     * @param id          系统配置id
     * @param copyContext 拷贝参数
     */

    public void copyConfigData(String id, CopyContextArgv copyContext, CopyProcessData copyProcessData) {
        if (StringUtils.isNotEmpty(copyContext.getSourceAppId()) && StringUtils.isNotEmpty(copyContext.getTargetAppId()) && (!copyContext.getSourceAppId().equals(copyContext.getTargetAppId()))) {
            SysConfig sysConfig = DB.findById(SysConfig.class, id);
            if (sysConfig == null) {
                return;
            }
            // 拷贝FAAS里的逻辑编排
            copyProcessData.addCopyObject(sysConfig);
            copyProcessData.addMapping(sysConfig.getId(), StringUtils.getUUID());
        }
    }

    /**
     * 拷贝任务调试
     *
     * @param id          任务id
     * @param copyContext 拷贝参数
     */

    public void copyTaskData(String id, CopyContextArgv copyContext, CopyProcessData copyProcessData) {
        SysTask sysTask = DB.findById(SysTask.class, id);
        if (sysTask == null) {
            return;
        }
        // 拷贝FAAS里的逻辑编排
        copyProcessData.addCopyObject(sysTask);
        copyProcessData.addMapping(sysTask.getId(), StringUtils.getUUID());
    }


    /**
     * 拷贝应用
     *
     * @param id          字典id
     * @param copyContext 拷贝参数
     */

    public void copyAppData(String id, String appName,CopyContextArgv copyContext, CopyProcessData copyProcessData) {

        try {
            DevApplication application = DB.findById(DevApplication.class, id);
            application.setName(appName + "-" + copyContext.getNameSuffix());
            String currentShortName = StringUtils.isEmpty(application.getShortName()) ?"": application.getShortName();
            String currentUrl = StringUtils.isEmpty(application.getDefaultPath()) ?"": application.getDefaultPath();
            application.setShortName(currentShortName + "-" + copyContext.getCodeSuffix());
            application.setDefaultPath(currentUrl + "/" + copyContext.getUrlSuffix());
            // 拷贝应用
            String newAppId = StringUtils.getUUID();
            copyProcessData.addCopyObject(application);
            copyProcessData.addMapping(application.getId(), newAppId);
            copyContext.setSourceAppId(id);
            copyContext.setTargetAppId(newAppId);
            copyContext.setDeepCopy(1);
            copyContext.setWithSystemData(0);
            // 拷贝所有页面
            List<String> pageIds = DB.findSingleAttributeList(String.class, "select id from dev_page where app_id=? and deleted = 0", id);
            for (String pageId: pageIds) {
                this.copyPageData(pageId, copyContext, copyProcessData);
            }
            // 拷贝所有的逻辑编排
            List<String> apiIds = DB.findSingleAttributeList(String.class, "select id from sys_api where app_id=?", id);
            for (String apiId: apiIds) {
                this.copyApiData(apiId, copyContext, copyProcessData);
            }
            List<String> logicIds = DB.findSingleAttributeList(String.class, "SELECT lf.id FROM sys_logic_flow lf LEFT JOIN sys_api sa ON sa.api_flow_id = lf.flow_id WHERE lf.application_id = ? and sa.id is null", id);
            for (String logicId: logicIds) {
                this.copyFlowData(logicId, copyContext, copyProcessData);
            }

            // 拷贝所有字典
            List<String> dictIds = DB.findSingleAttributeList(String.class, "select id from sys_dict where app_id=?", id);
            for (String dictId: dictIds) {
                this.copyDictData(dictId, copyContext, copyProcessData);
            }
            // 拷贝所有系统配置
            List<String> configIds = DB.findSingleAttributeList(String.class, "select id from sys_config where app_id=?", id);
            for (String configId: configIds) {
                this.copyConfigData(configId, copyContext, copyProcessData);
            }
            // 拷贝任务调度
            List<String> taskIds = DB.findSingleAttributeList(String.class, "select id from sys_task where application_id=?", id);
            for (String taskId: taskIds) {
                this.copyTaskData(taskId, copyContext, copyProcessData);
            }

        }
        catch (Exception e) {
            e.printStackTrace();
            throw e;
        }

    }

    /**
     * 根据路径解析数据
     * @param documentContext  文档
     * @param tags  标签
     * @return
     */
    @SuppressWarnings("all")
    private Set<JsonPathSearchResult> parseByPath(DocumentContext documentContext, String tags) {
        String[] findTags = tags.trim().split(",");
        // 查找所有的接口段
        Set<JsonPathSearchResult> jsonPathSearchResults = new HashSet<>();
        for (String tag: findTags) {
            String trimTag = tag.trim();
            String searchTag = "$..*.." + trimTag;
            List<Object> apiList = documentContext.read(searchTag);

            for (Object api: apiList) {

                if (api instanceof String) {

                        String value = (String) api;
                        if (StringUtils.isEmpty(value)) {
                            continue;
                        }
                        if(value.contains("'")) {
                            value = value.replace("'", "\\'");
                        }
                        String path = String.format("$..[?(@.%s == '%s')]", tag.trim(), value);
                        String[] arr = trimTag.split("\\.");
                        try {
                            JSONArray jsonArray = documentContext.read(path);

                            for (int i=0; i < jsonArray.size(); i++) {
                                JsonPathSearchResult searchResult  = new JsonPathSearchResult();
                                searchResult.setField(arr[arr.length-1]);
                                Map<String, Object> node = (Map<String, Object>) jsonArray.get(i);
                                List<String> paths = new ArrayList<>();
                                node.forEach((k, v) -> {
                                    if (v instanceof String) {
                                        String str = (String) v;
                                        if(str.contains("'")) {
                                            str = str.replace("'", "\\'");
                                        }
                                        String pa = String.format("@.%s == '%s'", k, str);
                                        paths.add(pa);
                                    }
                                });
                                searchResult.setPath("$..[?(" + StringUtils.joinToString(paths, " && ") + ")]");
                                searchResult.setValue(node);
                                jsonPathSearchResults.add(searchResult);
                            }
                        }
                        catch (Exception e) {
                            log.warn("json-path匹配出错，path:{}", path);

                    }

                }
            }
        }
        return jsonPathSearchResults;
    }

    /**
     * 替换所有的数据
     *
     * @param copyProcessData 拷贝数据
     */
    public void action(CopyProcessData copyProcessData, CopyContextArgv context) {

        int total = copyProcessData.getToCopySet().size();
        log.info("数据拷贝---开始： 总拷贝数:{}", copyProcessData.getToCopySet().size());
        // 依次替换数据
        try {
            for (int i = 0; i < copyProcessData.getToCopySet().size(); i++) {
                Object obj = copyProcessData.getToCopySet().get(i);
                // FAAS编辑编排
                if (obj instanceof AddFlowInfo) {
                    AddFlowInfo addFlowInfo = replaceObj(copyProcessData, obj, AddFlowInfo.class);
                    DB.kdbApi().addFlow(addFlowInfo);
                    copyProcessData.getFaasFlowIds().add(addFlowInfo.getFlowId());
                    log.info("数据拷贝---FAAS逻辑编排：{}, 进度:{}/{}", addFlowInfo.getName(), i + 1, total);
                } else if (obj instanceof SysLogicFlow) {
                    SysLogicFlow sysLogicFlow = replaceObj(copyProcessData, obj, SysLogicFlow.class);
                    sysLogicFlow.cleanAuthor();
                    sysLogicFlow.setApplicationId(context.getTargetAppId());

                    DB.save(sysLogicFlow);
                    copyProcessData.getDbFlowIds().add(sysLogicFlow.getId());
                    log.info("数据拷贝---数据库逻辑编排：{}, 进度:{}/{}", sysLogicFlow.getName(), i + 1, total);
                } else if (obj instanceof SysApi) {
                    SysApi sysApi = replaceObj(copyProcessData, obj, SysApi.class);
                    sysApi.setAppId(context.getTargetAppId());
                    sysApi.cleanAuthor();
                    {
                        SysApi checkObj = BeanUtils.copyObject(sysApi, SysApi.class);
                        checkObj.setId(null);
                        SysApiService sysApiService = SpringContext.getBean(SysApiService.class);
//                        sysApiService.checkUnique(checkObj);
//                        DB.save(sysApi);
                        try {
                            sysApiService.checkUnique(checkObj);
                            DB.save(sysApi);
                        }
                        catch (BusinessException e) {
//                            throw new BusinessException(I18n.t("CopyAppManager.cpApiFail", "接口保存异常，拷贝失败") + ":"+ checkObj.getApiName());
                            log.warn("接口保存失败:{}", e.getMessage());
                        }


                    }
                    copyProcessData.getApiIds().add(sysApi.getId());
                    log.info("数据拷贝---接口编排：{}, 进度:{}/{}", sysApi.getApiName(), i + 1, total);

                }
                else if (obj instanceof DevPage) {
                    DevPage devPage = replaceObj(copyProcessData, obj, DevPage.class);
                    devPage.setAppId(context.getTargetAppId());
                    devPage.cleanAuthor();
                    {
                        DevPage checkObj = BeanUtils.copyObject(devPage, DevPage.class);
                        checkObj.setId(null);
                        DevPageService devPageService = SpringContext.getBean(DevPageService.class);
                        devPageService.checkUnique(checkObj);
                    }
                    DB.save(devPage);
                    copyProcessData.getPageIds().add(devPage.getId());
                    log.info("数据拷贝---页面管理：{}, 进度:{}/{}", devPage.getName(), i + 1, total);
                }
                else if (obj instanceof SysDict) {
                    SysDict sysDict = replaceObj(copyProcessData, obj, SysDict.class);
                    sysDict.setAppId(context.getTargetAppId());
                    sysDict.cleanAuthor();
                    DB.save(sysDict);
                    copyProcessData.getDictIds().add(sysDict.getId());
                    log.info("数据拷贝---字典管理：{}, 进度:{}/{}", sysDict.getName(), i + 1, total);
                }
                else if (obj instanceof SysDictItem) {
                    SysDictItem sysDictItem = replaceObj(copyProcessData, obj, SysDictItem.class);
                    sysDictItem.setAppId(context.getTargetAppId());
                    sysDictItem.cleanAuthor();
                    DB.save(sysDictItem);
                    copyProcessData.getDictItemIds().add(sysDictItem.getId());
                    log.info("数据拷贝---字典项管理：{}, 进度:{}/{}", sysDictItem.getName(), i + 1, total);
                }
                else if (obj instanceof SysConfig) {
                    SysConfig sysConfig = replaceObj(copyProcessData, obj, SysConfig.class);
                    sysConfig.setAppId(context.getTargetAppId());
                    sysConfig.cleanAuthor();
                    DB.save(sysConfig);
                    copyProcessData.getConfigIds().add(sysConfig.getId());
                    log.info("数据拷贝---系统配置管理：{}, 进度:{}/{}", sysConfig.getName(), i + 1, total);
                }
                else if (obj instanceof SysTask) {
                    SysTask sysTask = replaceObj(copyProcessData, obj, SysTask.class);
                    sysTask.setApplicationId(context.getTargetAppId());
                    sysTask.cleanAuthor();
                    DB.save(sysTask);
                    copyProcessData.getTaskIds().add(sysTask.getId());
                    log.info("数据拷贝---任务调度管理：{}, 进度:{}/{}", sysTask.getName(), i + 1, total);
                }
                else if (obj instanceof DevApplication) {
                    DevApplication devApplication = replaceObj(copyProcessData, obj, DevApplication.class);
                    devApplication.cleanAuthor();
                    DB.save(devApplication);
                    copyProcessData.getAppIds().add(devApplication.getId());
                    log.info("数据拷贝---应用管理：{}, 进度:{}/{}", devApplication.getName(), i + 1, total);

                }
            }
        } catch (Exception e) {

            log.warn("数据拷贝---应用拷贝异常，将进行回滚...{}", e);
            rollback(copyProcessData);
            throw e;
        }
        log.info("数据拷贝---完成： 总拷贝数:{}", copyProcessData.getToCopySet().size());

    }

    private <T> T replaceObj(CopyProcessData copyProcessData, Object obj, Class<T> tClass) {
        AtomicReference<String> json = new AtomicReference<>(JsonUtil.toJson(obj));
        // 替换
        for (Map.Entry<String, String> entry : copyProcessData.getUidMap().entrySet()) {
            if (entry != null) {
                String k = entry.getKey();
                String v = entry.getValue();
                json.set(json.get().replace(k, v));
            }

        }
        return JsonUtil.toBean(json.get(), tClass);
    }

    /**
     * 回滚
     *
     * @param copyProcessData
     */
    private void rollback(CopyProcessData copyProcessData) {
        log.info("数据拷贝---开始回滚");
        if (!copyProcessData.getFaasFlowIds().isEmpty()) {
            log.info("数据拷贝---开始回滚FAAS逻辑编排");
            for (String id : copyProcessData.getFaasFlowIds()) {
                try {
                    log.info("数据拷贝---回滚FAAS逻辑编排:{}", id);
                    DB.kdbApi().deleteFlow(id);
                } catch (Exception ignored) {
                }
            }
        }
        if (!copyProcessData.getDbFlowIds().isEmpty()) {
            log.info("数据拷贝---开始回滚DB逻辑编排");
            for (String id : copyProcessData.getDbFlowIds()) {
                try {
                    log.info("数据拷贝---回滚DB逻辑编排:{}", id);
                    DB.delete(SysLogicFlow.class, id);
                } catch (Exception ignored) {
                }
            }
        }
        if (!copyProcessData.getApiIds().isEmpty()) {
            log.info("数据拷贝---开始回滚API");
            for (String id : copyProcessData.getApiIds()) {
                try {
                    log.info("数据拷贝---回滚API:{}", id);
                    DB.delete(SysApi.class, id);
                } catch (Exception ignored) {
                }
            }
        }
        if (!copyProcessData.getPageIds().isEmpty()) {
            log.info("数据拷贝---开始回滚Page");
            for (String id : copyProcessData.getPageIds()) {
                try {
                    log.info("数据拷贝---回滚Page:{}", id);
                    DB.delete(DevPage.class, id);
                } catch (Exception ignored) {
                }
            }
        }
        if (!copyProcessData.getDictIds().isEmpty()) {
            log.info("数据拷贝---开始回滚字典管理");
            for (String id : copyProcessData.getDictIds()) {
                try {
                    log.info("数据拷贝---回滚Dict:{}", id);
                    DB.delete(SysDict.class, id);
                } catch (Exception ignored) {
                }
            }
        }
        if (!copyProcessData.getDictItemIds().isEmpty()) {
            log.info("数据拷贝---开始回滚字典项管理");
            for (String id : copyProcessData.getDictItemIds()) {
                try {
                    log.info("数据拷贝---回滚DictItem:{}", id);
                    DB.delete(SysDictItem.class, id);
                } catch (Exception ignored) {
                }
            }
        }
        if (!copyProcessData.getConfigIds().isEmpty()) {
            log.info("数据拷贝---开始回滚字系统配置管理");
            for (String id : copyProcessData.getConfigIds()) {
                try {
                    log.info("数据拷贝---回滚Config:{}", id);
                    DB.delete(SysConfig.class, id);
                } catch (Exception ignored) {
                }
            }
        }
        if (!copyProcessData.getTaskIds().isEmpty()) {
            log.info("数据拷贝---开始回滚字系统配置管理");
            for (String id : copyProcessData.getTaskIds()) {
                try {
                    log.info("数据拷贝---回滚Task:{}", id);
                    DB.delete(SysTask.class, id);
                } catch (Exception ignored) {
                }
            }
        }
        if (!copyProcessData.getAppIds().isEmpty()) {
            log.info("数据拷贝---开始回滚字APP管理");
            for (String id : copyProcessData.getAppIds()) {
                try {
                    log.info("数据拷贝---回滚App:{}", id);
                    DB.delete(DevApplication.class, id);
                } catch (Exception ignored) {
                }
            }
        }
        log.info("数据拷贝---完成回滚");

    }


    /**
     * 导出pine
     * @param copyProcessData
     * @return
     */
    public KdbRetFile exportPine(CopyProcessData copyProcessData) {
        // 应用信息直接使用青松信息
        ExportRootData rootData = new ExportRootData();
        rootData.setDownloadSys(false);
        // 初始化应用数据, 这里直接使用青松开发平台，因为可能存在导多个不同应用的数据
        {
            DevApplication application = DB.findById(DevApplication.class, pineAppId);
            ApplicationConfig config = new ApplicationConfig();
            config.setName(application.getName());
            config.setId(application.getId());
            config.setPinePort(10082);
            config.setFaasPort(10081);
            config.setDataSource("");
            config.setVersion("1.0");
            config.setVersionType("linux");
            rootData.setApplicationConfig(config);
        }
        // 初如化其他数据
        {

            List<ExportData> exportDataList = new ArrayList<>();
            exportDataList.add(ExportData.createData("page", copyProcessData.getPageIdsFromObjects()));
            exportDataList.add(ExportData.createData("api", copyProcessData.getApiIdsFromObjects()));
            exportDataList.add(ExportData.createData("task"));
            exportDataList.add(ExportData.createData("dict"));
            exportDataList.add(ExportData.createData("config"));
            exportDataList.add(ExportData.createData("baseMenu"));
            exportDataList.add(ExportData.createData("flow", copyProcessData.getFlowIdsFromObjects()));
            rootData.setExportData(exportDataList);
        }

        // 请求
        // 创建上下文
        KFlowContext context = KFlowContext.createBaseContext("{}", "{}");
        // 调用流程
        String exportFlowId = SpringContext.getProperties("app.export.flowId", "609a655bae59402babe2c1f439849e03");

        KdbFlowResult result = KdbFlowExecutor.getInstance().execute(exportFlowId, "", JsonUtil.beanToMap(rootData), context, true, false, new ArrayList<>());
        KdbRetFile kdbRetFile = (KdbRetFile) result.getData();
        // 转为map
        Map<String, Object> devMap = JsonUtil.toMap(new String(kdbRetFile.getData()));
        devMap.put("powerLinks", Collections.emptyList());
        devMap.put("functions", Collections.emptyList());
        devMap.put("devRoleMenus", Collections.emptyList());
        devMap.put("devPowerTrees", Collections.emptyList());
        devMap.put("devFaasNodes", Collections.emptyList());
        devMap.put("devRoles", Collections.emptyList());
        devMap.put("extPluginInterfaces", Collections.emptyList());
        devMap.put("extPluginTrees", Collections.emptyList());
        devMap.put("devFaasNodeTypes", Collections.emptyList());
        devMap.put("menus", Collections.emptyList());
        devMap.put("tasks", Collections.emptyList());
        devMap.put("sources", Collections.emptyList());
        devMap.put("configs", Collections.emptyList());
        devMap.put("sysLogicTemplates", Collections.emptyList());
        kdbRetFile.setData(JsonUtil.toJson(devMap).getBytes(StandardCharsets.UTF_8));
        return kdbRetFile;
    }


}

package com.kingsware.kdev.core.orm.kdb;

import com.kingsware.kdev.core.bean.BaseRet;
import com.kingsware.kdev.core.config.SysConst;
import com.kingsware.kdev.core.context.SpringContext;
import com.kingsware.kdev.core.exception.BusinessException;
import com.kingsware.kdev.core.exception.HttpClientException;
import com.kingsware.kdev.core.i18n.I18n;
import com.kingsware.kdev.core.kflow.define.FlowDefinition;
import com.kingsware.kdev.core.mode.AppModeProperties;
import com.kingsware.kdev.core.orm.DB;
import com.kingsware.kdev.core.orm.DataBaseProperties;
import com.kingsware.kdev.core.orm.exception.OrmDbException;
import com.kingsware.kdev.core.orm.exception.TransactionException;
import com.kingsware.kdev.core.util.HttpUtil;
import com.kingsware.kdev.core.util.JsonUtil;
import com.kingsware.kdev.core.util.PageUtil;
import com.kingsware.kdev.core.util.StringUtils;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;

import java.io.File;
import java.io.InputStream;
import java.net.URLEncoder;
import java.util.*;

/**
 * kdb抽象类
 *
 * @author chen peng
 * @version 1.0.0
 * @date 2022/1/13 6:05 下午
 */
@Slf4j
@SuppressWarnings("all")
public abstract class KdbApiAbstract implements KdbApi {

    /**
     * 流程相关接口
     **/
    public static final String ADD_FLOW_URL = "/api/flow/add";
    public static final String EDIT_FLOW_URL = "/api/flow/edit";
    public static final String DELETE_FLOW_URL = "/api/flow/delete";
    public static final String QUERY_FLOW_URL = "/api/flow/search";
    public static final String EXCUTE_FLOW_URL = "/api/execute4debug";
    /**
     * 数据源相关接口
     **/
    public static final String ADD_DS_URL = "/api/dataSource/add";
    public static final String EDIT_DS_URL = "/api/dataSource/edit";
    public static final String DELETE_DS_URL = "/api/dataSource/delete";
    public static final String QUERY_DS_URL = "/api/dataSource/search";
    /**
     * 函数相关
     **/
    public static final String QUERY_FUN_URL = "/api/function/search";
    public static final String ADD_FUN_URL = "/api/function/add";
    public static final String EDIT_FUN_URL = "/api/function/edit";
    public static final String DELETE_FUN_URL = "/api/function/delete";
    /**
     * 上传文件
     **/
    public static final String UPLOAD_URL = "/upload";
    /**
     * 下载文件
     **/
    public static final String DOWN_URL = "/download";
    /**
     * 事务
     **/
    public static final String TRAN_URL = "/api/execute/transaction";


    @Override
    public String addFlow(AddFlowInfo flowInfo) {
        KdbRet<AddFlowInfoRet> ret = post(getServer(), flowInfo, ADD_FLOW_URL, AddFlowInfoRet.class, false);
//       log.info("新增逻辑编排响应:{}", JsonUtil.toJson(ret));
        return ret.getResponseBody().getFlowId();
    }

    @Override
    public void editFlow(EditFlowInfo flowInfo) {
        KdbRet ret = post(getServer(), flowInfo, EDIT_FLOW_URL, String.class, false);
//        log.info("编辑逻辑编排响应:{}", JsonUtil.toJson(ret));
        if (ret.getErrorCode() != 0) {
            throw BusinessException.serviceThrow(I18n.t("KdbApiAbstract.error1","保存失败,错误信息:{0}", ret.getMessage()) );
        }
    }

    @Override
    public FlowInfo get(String flowId) {
        if (StringUtils.isEmpty(flowId)) {
            return null;
        }
        // 参数
        KdbFlowQueryArgv argv = new KdbFlowQueryArgv();
        argv.setFlowId(flowId);
        // 查询model
        KdbApi api = DB.kdbApi();
        List<FlowInfo> list = api.query(argv);

        // 转换成ret对象
        if (list == null || list.isEmpty()) {
            return null;
        }
        return list.get(0);
    }

    @Override
    public void deleteFlow(String flowId) {
        Map<String, Object> params = new HashMap<>(1);
        params.put("flowId", flowId);
        KdbRet ret = post(getServer(), params, DELETE_FLOW_URL, String.class, false);
        if (ret.getErrorCode() != 0) {
            throw new OrmDbException(ret.getMessage(), ret.getKlog(), ret.getStackTrace());
        }

    }

    @Override
    public List<FlowInfo> query(KdbFlowQueryArgv flowInfo) {
        KdbRet<List> list = post(getServer(), flowInfo, QUERY_FLOW_URL, List.class, true);
        String json = JsonUtil.toJson(list.getResponseBody());
        return JsonUtil.toListBean(json, FlowInfo.class);
    }

    @Override
    public void addDataSource(DataSourceInfo dataSourceInfo) {
        KdbRet<String> ret = post(getServer(), dataSourceInfo, ADD_DS_URL, String.class, false);
        if (ret.getErrorCode() != 0) {
            throw new OrmDbException(ret.getMessage(), ret.getKlog(), ret.getStackTrace());
        }
        this.refreshBaseFlow();
    }

    @Override
    public void editDataSource(DataSourceInfo dataSourceInfo) {
        KdbRet<String> ret = post(getServer(), dataSourceInfo, EDIT_DS_URL, String.class, false);
        if (ret.getErrorCode() != 0) {
            throw new OrmDbException(ret.getMessage(), ret.getKlog(), ret.getStackTrace());
        }
    }

    @Override
    public void deleteDataSource(String sourceName) {
        Map<String, Object> params = new HashMap<>(1);
        params.put("sourceName", sourceName);
        KdbRet ret = post(getServer(), params, DELETE_DS_URL, String.class, false);
        if (ret.getErrorCode() != 0) {
            throw new OrmDbException(ret.getMessage(), ret.getKlog(), ret.getStackTrace());
        }

    }

    @Override
    public List<DataSourceInfo> queryDataSource(DataSourceQueryArgv dataSourceInfo) {
        KdbRet<List> list = post(getServer(), dataSourceInfo, QUERY_DS_URL, List.class, true);
        log.info("数据源查询响应:{}", JsonUtil.toJson(list));
        String json = JsonUtil.toJson(list.getResponseBody());
        return JsonUtil.toListBean(json, DataSourceInfo.class);
    }

    @Override
    public KdbRet<String> executeFlow(KdbArgv argv, boolean debug, boolean sync) {
        // 加入当前环境变量
        String executeFlowUrl = debug ? "/api/execute4debug" : "/api/execute";
        if (sync) {
            executeFlowUrl = "/api/async/execute";
        }
        String[] servers = getServer();
        List<String> newServers = new ArrayList<>();
        // 如果是系统应用
        String currentAppId;
        if (argv.getVariables().containsKey("_appId")) {
            currentAppId = argv.getVariables().get("_appId") == null ? "" : argv.getVariables().get("_appId").toString();
        } else {
            currentAppId = "";
        }
        AppModeProperties appModeProperties = SpringContext.getBean(AppModeProperties.class);
        if (appModeProperties.getDev()) {
            DataBaseProperties dataBaseProperties = SpringContext.getBean(DataBaseProperties.class);
            String appFaasUrl = "";
            if (dataBaseProperties.getApp2Faas()!= null) {
                Optional<DataBaseProperties.App2Faas> app2Faas = dataBaseProperties.getApp2Faas().stream().filter(item -> item.getId().equalsIgnoreCase(currentAppId)).findFirst();
                if (app2Faas.isPresent()) {
                    appFaasUrl = app2Faas.get().getServer();
                    log.info("应用流程：{}，应用:{}, 服务器:{}", argv.getFlowID(),  app2Faas.get().getServer(), appFaasUrl);
                }
            }
            if (StringUtils.isNotEmpty(appFaasUrl)) {
                newServers.add(appFaasUrl);
            }
            else {
                if (SysConst.pineAppId.equalsIgnoreCase(currentAppId) || servers.length == 1) {
//                    log.info("系统应用流程：{}", argv.getFlowID());
                    newServers.addAll(Arrays.asList(servers));
                }
                else {
                    // 从第二个服务器开始
                    List<String> toSelectServers = Arrays.asList(servers).subList(1, servers.length);
                    // hashcode 应用id，然后取模数
                    int index = Math.abs(currentAppId.hashCode()) % toSelectServers.size();
                    // 加入到队列中
                    newServers.add(toSelectServers.get(index));
//                    log.info("开发应用流程：{}， 选择：{}, 服务器:{}", argv.getFlowID(), index+1, toSelectServers.get(index) );
                }
            }

        }
        else {
            newServers.addAll(Arrays.asList(servers));
        }
        return post(newServers.toArray(new String[0]), argv, executeFlowUrl, String.class, true);
    }

    @Override
    public KdbRet<String> executeScript(String script) {
        KdbArgv argv = new KdbArgv();
        argv.setFlowID("faas_script");
        argv.getVariables().put("script", script);
        argv.setDebugger(new ArrayList<>());
        KdbRet<String> ret = this.executeFlow(argv, false, false);
        return ret;
    }


    @Override
    public void addFun(AddFunctionInfo argv) {
        KdbRet ret = post(getServer(), argv, ADD_FUN_URL, String.class, false);
        if (ret.getErrorCode() != 0) {
            throw new OrmDbException(ret.getMessage(), ret.getKlog(), ret.getStackTrace());
        }
    }

    @Override
    public void editFun(EditFunctionInfo argv) {
        KdbRet ret = post(getServer(), argv, EDIT_FUN_URL, String.class, false);
        if (ret.getErrorCode() != 0) {
            throw new OrmDbException(ret.getMessage(), ret.getKlog(), ret.getStackTrace());
        }
    }

    @Override
    public void deleteFun(String funId) {
        Map<String, Object> params = new HashMap<>(1);
        params.put("id", funId);
        KdbRet ret = post(getServer(), params, DELETE_FUN_URL, String.class, false);
        if (ret.getErrorCode() != 0) {
            throw new OrmDbException(ret.getMessage(), ret.getKlog(), ret.getStackTrace());
        }
    }

    @Override
    @SuppressWarnings("all")
    public List<Functions> queryFunction(FunctionQueryArgv argv) {
        KdbRet<List> list = post(getServer(), argv, QUERY_FUN_URL, List.class, true);
        String json = JsonUtil.toJson(list.getResponseBody());
        return JsonUtil.toListBean(json, Functions.class);
    }

    /**
     * http post 请求
     *
     * @param params 参数
     * @return body
     */
    @SuppressWarnings("all")
    private <T> KdbRet<T> post(String[] severs, Object params, String api, Class<T> tClass, boolean anyone) {
        try {
            // 转为json
            String requestBody = JsonUtil.toJson(params);
            // 拼接请求
            List<String> urls = new ArrayList<>();
            for (String a: severs) {
                urls.add(a +  api);
            }
            String url = StringUtils.joinToString(urls, ";");
            long t1 = System.currentTimeMillis();
            if (api.contains("sync")) {
                System.currentTimeMillis();
            }
            String responseBody = HttpUtil.postBody(url, requestBody, Collections.emptyMap(), anyone);
            KdbRet<T> ret = JsonUtil.toBean(responseBody, KdbRet.class, tClass);
            if (ret == null) {
                throw new OrmDbException(I18n.t("KdbApiAbstract.error2", "响应数据不合法" )+ responseBody);
            }
            return ret;
        } catch (HttpClientException e) {
            log.error("接口调用，响应码:{}, 响应信息：{}，接口:{}, 参数:{}", e.getCode(), e.getMessage(), e.getUrl(), e.getParams());
            throw new OrmDbException(e.getMessage());
        }
    }

    @Override
    @SuppressWarnings("all")
    public KdbRet<String> uploadFile(InputStream inputStream, String fileName, String path) {
        try {
            String url = chooseServer()[0] + UPLOAD_URL;
            // 发起请求上传文件
            String responseBody = HttpUtil.uploadFile(url, fileName, "file", inputStream, path);
            KdbRet<String> ret = JsonUtil.toBean(responseBody, KdbRet.class, String.class);
            if (ret == null) {
                throw new OrmDbException(I18n.t("KdbApiAbstract.kdb", "kdb响应数据不合法，响应内容:") + responseBody);
            }
            return ret;
        } catch (HttpClientException e) {
            log.error("接口调用，响应码:{}, 响应信息：{}，接口:{}, 参数:{}", e.getCode(), e.getMessage(), e.getUrl(), e.getParams());
            throw new OrmDbException(e.getMessage());
        }
    }

    @Override
    @SneakyThrows
    public File downloadFile(String path, String fileName) {
        String url = chooseServer()[0] + DOWN_URL + "/" + URLEncoder.encode(fileName, "utf-8") + "?path=" + URLEncoder.encode(path, "utf-8");
        return HttpUtil.downloadFile(url, path);
    }

    @Override
    @SneakyThrows
    public void downloadStream(String path, String fileName, String userFileName) {
        String url = chooseServer()[0] + DOWN_URL + "/" + URLEncoder.encode(fileName, "utf-8") + "?path=" + URLEncoder.encode(path, "utf-8");
        String outFileName = userFileName;
        if (StringUtils.isEmpty(userFileName)) {
            outFileName = fileName;
        }
        HttpUtil.downloadStream(url, path, outFileName);
    }

    @Override
    @SneakyThrows
    public File downloadFile(String path, String fileName, String prefix, String suffix) {
        String url = chooseServer()[0] + DOWN_URL + "/" + URLEncoder.encode(fileName, "utf-8") + "?path=" + URLEncoder.encode(path, "utf-8");
        return HttpUtil.downloadFile(url, path, prefix, suffix);
    }




    /**
     * 事务接口
     *
     * @param transactionInfo
     */
    @Override
    public String transaction(TransactionInfo transactionInfo) {
        try {
            // todo 应该为同一个url
            KdbRet<TransactionRet> ret = post(chooseServer(), transactionInfo, TRAN_URL, TransactionRet.class, true);
            if (ret.getErrorCode() != 0) {
                throw new TransactionException(ret.getMessage(), ret.getKlog(), ret.getStackTrace());
            }
            return ret.getResponseBody().getTransactionUuid();
        } catch (OrmDbException | HttpClientException e) {
            throw new TransactionException(e.getMessage());
        }


    }

    @Override
    public KdbDataRet<FlowInfo> queryFlow(KdbFlowQueryArgv flowInfo) {
        KdbRet<List> list =  post(chooseServer(),flowInfo, QUERY_FLOW_URL, List.class, true);
        String json = JsonUtil.toJson(list.getResponseBody());
        List<FlowInfo> flowInfoList = JsonUtil.toListBean(json, FlowInfo.class);
        KdbDataRet<FlowInfo> flowDataRet = new KdbDataRet<>();
        flowDataRet.setList(flowInfoList);
        flowDataRet.setTotal(list.getTotal());
        return flowDataRet;
    }

    @Override
    public void refreshBaseFlow() {
        // 2. 修改基础流程
        FlowInfo flowInfo = DB.kdbApi().get("base_flow");
        // 转为流程定义
        FlowDefinition flowDefinition = JsonUtil.toBean(flowInfo.getContent(), FlowDefinition.class);
        if (flowDefinition != null) {
            // 获取所有的数据源
            List<DataSourceInfo> dataSourceInfos = DB.kdbApi().queryDataSource(new DataSourceQueryArgv());
            // 根据数据源生成流程
            for (DataSourceInfo info: dataSourceInfos) {
                // 判断是否已存在，不存在才会加进去
                boolean has = flowDefinition.getNodeDefinitions().stream().anyMatch(it -> it.getId().equals(info.getSourceName()));
                if (!has) {
                    String expr = "compare(${nodeId}=" + info.getSourceName() + ")";
                    flowDefinition.resetCurrentNode("数据源分支节点").toSqlWithId(info.getSourceName(), info.getSourceName(), info.getSourceName(), "select version()", expr).toEnd();
                }
            }
            // 重新保存
            EditFlowInfo editFlowInfo = new EditFlowInfo();
            editFlowInfo.setFlowId(flowInfo.getFlowId());
            editFlowInfo.setName("基础流程");
            editFlowInfo.setContent(flowDefinition.toJson());
            editFlowInfo.setDescription(flowInfo.getDescription());
            DB.kdbApi().editFlow(editFlowInfo);
        }
    }

    /**
     * 设置接口地址
     **/
    abstract String[] getServer();

    /**
     * 获取服务器
     * @return
     */
    private String[] chooseServer() {
        int index = new Random().nextInt(getServer().length);
        return new String[] {getServer()[index]};
    }
}

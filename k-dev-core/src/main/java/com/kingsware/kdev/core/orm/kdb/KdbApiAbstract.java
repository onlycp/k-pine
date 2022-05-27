package com.kingsware.kdev.core.orm.kdb;

import com.kingsware.kdev.core.context.SpringContext;
import com.kingsware.kdev.core.exception.HttpClientException;
import com.kingsware.kdev.core.orm.DB;
import com.kingsware.kdev.core.orm.exception.OrmDbException;
import com.kingsware.kdev.core.util.HttpUtil;
import com.kingsware.kdev.core.util.JsonUtil;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;

import java.io.File;
import java.io.InputStream;
import java.net.URLEncoder;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * kdb抽象类
 *
 * @author chen peng
 * @version 1.0.0
 * @date 2022/1/13 6:05 下午
 */
@Slf4j
public abstract class KdbApiAbstract implements  KdbApi {

    /** 流程相关接口 **/
    public static final String ADD_FLOW_URL = "/api/flow/add";
    public static final String EDIT_FLOW_URL = "/api/flow/edit";
    public static final String DELETE_FLOW_URL = "/api/flow/delete";
    public static final String QUERY_FLOW_URL = "/api/flow/search";
    public static final String EXCUTE_FLOW_URL = "/api/execute4debug";
    /** 数据源相关接口 **/
    public static final String ADD_DS_URL = "/api/dataSource/add";
    public static final String EDIT_DS_URL = "/api/dataSource/edit";
    public static final String DELETE_DS_URL = "/api/dataSource/delete";
    public static final String QUERY_DS_URL = "/api/dataSource/search";
    /** 函数相关 **/
    public static final String QUERY_FUN_URL = "/api/function/search";
    public static final String ADD_FUN_URL = "/api/function/add";
    public static final String EDIT_FUN_URL = "/api/function/edit";
    public static final String DELETE_FUN_URL = "/api/function/delete";
    /** 上传文件 **/
    public static final String UPLOAD_URL = "/upload";
    /** 下载文件 **/
    public static final String DOWN_URL = "/download";


    @Override
    public String addFlow(AddFlowInfo flowInfo) {
       KdbRet<AddFlowInfoRet> ret = post(flowInfo, ADD_FLOW_URL, AddFlowInfoRet.class);
       return ret.getResponseBody().getFlowId();
    }

    @Override
    public void editFlow(EditFlowInfo flowInfo) {
        post(flowInfo, EDIT_FLOW_URL, String.class);
    }

    @Override
    public FlowInfo get(String flowId) {
        // 参数
        KdbFlowQueryArgv argv = new KdbFlowQueryArgv();
        argv.setFlowId(flowId);
        // 查询model
        KdbApi api = DB.kdbApi();
        List<FlowInfo> list = api.query(argv);

        // 转换成ret对象
        if (list == null ||list.isEmpty()) {
            return null;
        }
        return list.get(0);
    }

    @Override
    public void deleteFlow(String flowId) {
        Map<String, Object> params = new HashMap<>(1);
        params.put("flowId", flowId);
        post(params, DELETE_FLOW_URL, String.class);
    }

    @Override
    public List<FlowInfo> query(KdbFlowQueryArgv flowInfo) {
        KdbRet<List> list =  post(flowInfo, QUERY_FLOW_URL, List.class);
        String json = JsonUtil.toJson(list.getResponseBody());
        return JsonUtil.toListBean(json, FlowInfo.class);
    }

    @Override
    public void addDataSource(DataSourceInfo dataSourceInfo) {
        post(dataSourceInfo, ADD_DS_URL, String.class);
    }

    @Override
    public void editDataSource(DataSourceInfo dataSourceInfo) {
        post(dataSourceInfo, EDIT_DS_URL, String.class);
    }

    @Override
    public void deleteDataSource(String sourceName) {
        Map<String, Object> params = new HashMap<>(1);
        params.put("sourceName", sourceName);
        post(params, DELETE_DS_URL, String.class);
    }

    @Override
    public List<DataSourceInfo> queryDataSource(DataSourceQueryArgv dataSourceInfo) {
        KdbRet<List> list =  post(dataSourceInfo, QUERY_DS_URL, List.class);
        String json = JsonUtil.toJson(list.getResponseBody());
        return JsonUtil.toListBean(json, DataSourceInfo.class);
    }

    @Override
    public KdbRet<String> executeFlow(KdbArgv argv, boolean debug) {
        // 加入当前环境变量
        String executeFlowUrl = debug ? "/api/execute4debug" : "/api/execute";
        return post(argv, executeFlowUrl, String.class);
    }

    @Override
    public void addFun(AddFunctionInfo argv) {
        post(argv, ADD_FUN_URL, String.class);
    }

    @Override
    public void editFun(EditFunctionInfo argv) {
        post(argv, EDIT_FUN_URL, String.class);
    }

    @Override
    public void deleteFun(String funId) {
        Map<String, Object> params = new HashMap<>(1);
        params.put("id", funId);
        post(params, DELETE_FUN_URL, String.class);
    }

    @Override
    @SuppressWarnings("all")
    public List<Functions> queryFunction(FunctionQueryArgv argv) {
        KdbRet<List> list =  post(argv, QUERY_FUN_URL, List.class);
        String json = JsonUtil.toJson(list.getResponseBody());
        return JsonUtil.toListBean(json, Functions.class);
    }

    /**
     * http post 请求
     * @param params    参数
     * @return      body
     */
    @SuppressWarnings("all")
    private <T> KdbRet<T> post(Object params, String api, Class<T> tClass)  {
        try {
            // 转为json
            String requestBody = JsonUtil.toJson(params);
            // 拼接请求
            String url = getServer() +  api;
            // 发起进攻，杀

            long t1 = System.currentTimeMillis();
            // 杀敌一万，满身是血
            String responseBody = HttpUtil.postBody(url, requestBody, Collections.emptyMap());
            // 洗洗，换身好衣服
            KdbRet<T> ret = JsonUtil.toBean(responseBody, KdbRet.class, tClass);
            // 看看死了没
            // 灰都没了
            if (ret == null) {
                throw new OrmDbException("kdb响应数据不合法，响应内容:" + responseBody);
            }
//            else if (ret.getErrorCode() != 0) {
//                throw new OrmDbException("kdb响应数据不合法，响应内容:" + ret.getMessage());
//            }
            return ret;
        }
        catch (HttpClientException e) {
            log.error("接口调用，响应码:{}, 响应信息：{}，接口:{}, 参数:{}", e.getCode(), e.getMessage(), e.getUrl(), e.getParams());
            throw new OrmDbException(e.getMessage());
        }
    }

    @Override
    @SuppressWarnings("all")
    public KdbRet<String> uploadFile(InputStream inputStream, String fileName, String path) {
        try {
            String url = getServer() + UPLOAD_URL;
            // 发起请求上传文件
            String responseBody = HttpUtil.uploadFile(url, fileName, "file", inputStream, path);
            KdbRet<String> ret = JsonUtil.toBean(responseBody, KdbRet.class, String.class);
            if (ret == null) {
                throw new OrmDbException("kdb响应数据不合法，响应内容:" + responseBody);
            }
            return ret;
        }
        catch (HttpClientException e) {
            log.error("接口调用，响应码:{}, 响应信息：{}，接口:{}, 参数:{}", e.getCode(), e.getMessage(), e.getUrl(), e.getParams());
            throw new OrmDbException(e.getMessage());
        }
    }

    @Override
    @SneakyThrows
    public File downloadFile(String path, String fileName) {
        String url = getServer() + DOWN_URL + "/" + URLEncoder.encode(fileName, "utf-8") +"?path=" + URLEncoder.encode(path, "utf-8");
        return HttpUtil.downloadFile(url, path);
    }

    /** 设置接口地址 **/
    abstract String getServer();
}

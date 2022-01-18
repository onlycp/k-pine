package com.kingsware.kdev.core.orm.kdb;

import com.kingsware.kdev.core.context.KClientContext;
import com.kingsware.kdev.core.context.SpringContext;
import com.kingsware.kdev.core.exception.HttpClientException;
import com.kingsware.kdev.core.orm.exception.OrmDbException;
import com.kingsware.kdev.core.util.DateUtils;
import com.kingsware.kdev.core.util.HttpUtil;
import com.kingsware.kdev.core.util.JsonUtil;
import com.kingsware.kdev.core.util.StringUtils;
import lombok.extern.slf4j.Slf4j;

import java.util.*;

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
    public static final String EXCUTE_FLOW_URL = "/api/execute";
    /** 数据源相关接口 **/
    public static final String ADD_DS_URL = "/api/dataSource/add";
    public static final String EDIT_DS_URL = "/api/dataSource/edit";
    public static final String DELETE_DS_URL = "/api/dataSource/delete";
    public static final String QUERY_DS_URL = "/api/dataSource/search";

    @Override
    public void addFlow(FlowInfo flowInfo) {
       post(flowInfo, ADD_FLOW_URL, String.class);
    }

    @Override
    public void editFlow(FlowInfo flowInfo) {
        post(flowInfo, EDIT_FLOW_URL, String.class);

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
    public List<Map<String, Object>> executeFlow(KdbArgv argv) {
        // 加入当前环境变量

        KdbRet<String> ret = post(argv, EXCUTE_FLOW_URL, String.class);
        List<Map<String, Object>> result = new ArrayList<>();
        if (StringUtils.isNotEmpty(ret.getResponseBody())) {
            List<Map> list = JsonUtil.snakeCaseToListBean(ret.getResponseBody(), Map.class);
            for (Map<?,?> map: list) {
                Map<String, Object> tmpMap = new HashMap<>();
                map.forEach((k, v) -> tmpMap.put(StringUtils.lineToHump(k.toString().toLowerCase()), v));
                result.add(tmpMap);
            }
        }

        return result;
    }

    /**
     * http post 请求
     * @param params    参数
     * @return      body
     */
    private <T> KdbRet<T> post(Object params, String api, Class<T> tClass)  {
        try {
            // 转为json
            String requestBody = JsonUtil.toJson(params);
            // 拼接请求
            String url = getServer() +  api;
            // 发起进攻，杀

            long t1 = System.currentTimeMillis();
            // 杀敌一万，满身是血
            String  responseBody = HttpUtil.postBody(url, requestBody, Collections.emptyMap());
            // 洗洗，换身好衣服
            KdbRet<T> ret = JsonUtil.toBean(responseBody, KdbRet.class, tClass);
            // 看看死了没
            // 灰都没了
            if (ret == null) {
                throw new OrmDbException("kdb响应数据不合法，响应内容:" + responseBody);
            }
            if (ret.getErrorCode() == 0) {
                // 没死，高高兴兴回家
                return ret;
            }
            else {
                // 死了， 通知准备后事
                throw new OrmDbException(ret.getMessage());
            }
        }
        catch (HttpClientException e) {
            log.error("接口调用，响应码:{}, 响应信息：{}，接口:{}, 参数:{}", e.getCode(), e.getMessage(), e.getUrl(), e.getParams());
            throw new OrmDbException(e.getMessage());
        }
    }

    /** 设置接口地址 **/
    abstract String getServer();
}

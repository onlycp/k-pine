package com.kingsware.kdev.core.orm.channel;

import com.kingsware.kdev.core.exception.HttpClientException;
import com.kingsware.kdev.core.orm.DBConnectConfig;
import com.kingsware.kdev.core.orm.exception.OrmDbException;
import com.kingsware.kdev.core.orm.kdb.KDBConnectConfig;
import com.kingsware.kdev.core.orm.kdb.KdbArgv;
import com.kingsware.kdev.core.orm.kdb.KdbRet;
import com.kingsware.kdev.core.orm.kdb.StepArgv;
import com.kingsware.kdev.core.util.HttpUtil;
import com.kingsware.kdev.core.util.JsonUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * KBD的http通道
 *
 * @author chen peng
 * @version 1.0.0
 * @date 2021/12/22 9:07 上午
 */
public class KDBHttpChannel implements DbChannel{

    /** 日志打印 **/
    private static final Logger logger  = LoggerFactory.getLogger(KDBHttpChannel.class);

    /** 配置 **/
    private KDBConnectConfig kdbConnectConfig;
    /** 透传sql的流程id **/
    private final static String passThroughFlowId = "base_flow";
    /** 透传sql的流程id **/
    private final static String executeStep = "execute";
    /** 透传sql的流程id **/
    private final static String executeResult = "result@execute";

    @Override
    public String name() {
        return "kdbHttp";
    }

    @Override
    public void setConfig(DBConnectConfig config) {
        this.kdbConnectConfig = (KDBConnectConfig)config;
    }

    @Override
    public <T> T queryForObject(String sql, Class<T> tClass, List<Object> objects) {
        // 从kdb请求数据
        KdbRet ret = send(makePassThrough(sql, objects));
        // 由于kdb响应结果始终为list，所以得先将为list
        String executeRequest = ret.getResponseBody().get(executeResult);
        List<T> list = JsonUtil.transformJson2List(executeRequest, tClass);
        // 返回结果
        if (list == null || list.isEmpty()) {
            return null;
        }
        else if (list.size() > 1){
            throw new OrmDbException(String.format("期望查询的结果数量为%d，但发现了%d条数据", 1, list.size()));
        }
        return list.get(0);
    }

    @Override
    public long queryForCount(String sql, List<Object> objects) {
        // 从kdb请求数据
        KdbRet ret = send(makePassThrough(sql, objects));
        // 由于kdb响应结果始终为list，所以得先将为list
        String executeRequest = ret.getResponseBody().get(executeResult);
        List<Map> list = JsonUtil.toListBean(executeRequest, Map.class);
        // 返回结果
        if (list == null || list.size() != 1) {
            throw new OrmDbException("查询数量时，应保持只有一条记录");
        }
        Map<Object, Object> firstMap = (Map<Object, Object>)list.get(0);
        long count = 0L;
        for (Map.Entry<Object, Object> entry: firstMap.entrySet()) {
            count = Long.parseLong(entry.getValue().toString());
        }
        return count;
    }

    @Override
    public void executeSql(String sql, List<Object> objects) {
        // 从kdb请求数据
        KdbRet ret = send(makePassThrough(sql, objects));
        if (ret.getErrorCode() != 0) {
            throw new OrmDbException("sql执行失败，错误信息:" + ret.getMessage());
        }
    }

    public <T> List<T> queryForList(String sql, Class<T> tClass, List<Object> objects) {
        // 从kdb请求数据
        KdbRet ret = send(makePassThrough(sql, objects));
        // 返回结果
        String executeRequest = ret.getResponseBody().get(executeResult);
        return JsonUtil.transformJson2List(executeRequest, tClass);
    }

    /**
     * 组装sql透传的参数
     * @param sql       sql
     * @param objects   参数
     * @return          透传参数
     */
    private KdbArgv makePassThrough(String sql, List<Object> objects) {
        KdbArgv argv = new KdbArgv();
        argv.setFlowID(passThroughFlowId);
        argv.addStep(executeStep, sql, kdbConnectConfig.getInnerType(), objects);
        return argv;

    }

    /**
     *  从kdb请求获取数据
     * @param kdbArgv   kdb参数
     * @return          响应结果
     */
    private  KdbRet send(KdbArgv kdbArgv) {
        try {
            // 转为json
            String requestBody = JsonUtil.toJson(kdbArgv);
            // 拼接请求
            String url = kdbConnectConfig.getServer() +  kdbConnectConfig.getExecuteSqlApi();
            // 发起进攻，杀
            logger.info("url:{} ,请求体: {}", url, requestBody);
            // 杀敌一万，满身是血
            String  responseBody = HttpUtil.postBody(url, requestBody, Collections.emptyMap());
            // 洗洗，换身好衣服
            KdbRet ret = JsonUtil.toBean(responseBody, KdbRet.class);
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
            logger.error("sql执行失败，响应码:{}, 响应信息：{}，接口:{}, 参数:{}", e.getCode(), e.getMessage(), e.getUrl(), e.getParams());
            throw new OrmDbException(e.getMessage());
        }
    }
}

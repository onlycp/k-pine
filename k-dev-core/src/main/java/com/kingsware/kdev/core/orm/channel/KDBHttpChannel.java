package com.kingsware.kdev.core.orm.channel;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.kingsware.kdev.core.exception.HttpClientException;
import com.kingsware.kdev.core.i18n.I18n;
import com.kingsware.kdev.core.orm.DBConnectConfig;
import com.kingsware.kdev.core.orm.exception.OrmDbException;
import com.kingsware.kdev.core.orm.kdb.*;
import com.kingsware.kdev.core.util.HttpUtil;
import com.kingsware.kdev.core.util.JsonUtil;
import com.kingsware.kdev.core.util.StringUtils;
import lombok.SneakyThrows;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;

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

    private static final ObjectMapper objectMapper = new ObjectMapper();

    static {
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
    }


    /** 配置 **/
    private KDBConnectConfig kdbConnectConfig;
    /** 透传sql的流程id **/
    private final static String passThroughFlowId = "base_flow";
    /** 透传sql的流程id **/
    private final static String executeStep = "execute";

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
        String executeResponse =  send(makePassThrough(sql, objects));
        List<T> list = JsonUtil.transformJson2List(executeResponse, tClass);
        // 返回结果
        if (list == null || list.isEmpty()) {
            return null;
        }
        else if (list.size() > 1){
            throw new OrmDbException(I18n.t("KDBHttpChannel.error1","期望查询的结果数量为{0}，但发现了{1}条数据, sql:{2}, 参数:{3}", 1, list.size(), sql, JsonUtil.toJson(objects)));
        }
        return list.get(0);
    }

    @Override
    public long queryForCount(String sql, List<Object> objects) {
        // 从kdb请求数据
        String executeResponse =  send(makePassThrough(sql, objects));
        List<Map> list = JsonUtil.snakeCaseToListBean(executeResponse, Map.class);
        // 返回结果
        if (list == null || list.size() != 1) {
            throw new OrmDbException(I18n.t("KDBHttpChannel.error2", "查询数量时，应保持只有一条记录"));
        }
        Map<Object, Object> firstMap = (Map<Object, Object>)list.get(0);
        long count = 0L;
        for (Map.Entry<Object, Object> entry: firstMap.entrySet()) {
            count = Long.parseLong(entry.getValue().toString());
        }
        return count;
    }

    @Override
    public Long executeSql(String sql, List<Object> objects) {
        String executeResponse = send(makePassThrough(sql, objects));
        if (executeResponse == null) {
            return 0L;
        }
        return Long.parseLong(executeResponse);
    }

    public <T> List<T> queryForList(String sql, Class<T> tClass, List<Object> objects) {
        // 从kdb请求数据
        String executeResponse =  send(makePassThrough(sql, objects));
//        logger.info("sql:{}, result:{}", sql, executeResponse);
        return JsonUtil.transformJson2List(executeResponse, tClass);
    }

    @Override
    public <T> List<T> queryForAttribute(String sql, Class<T> tClass, List<Object> objects) {
        // 从kdb请求数据
        String executeResponse =  send(makePassThrough(sql, objects));
        List<Map> list = JsonUtil.snakeCaseToListBean(executeResponse, Map.class);
        assert list != null;
        List<T> result = new ArrayList<>(list.size());
        for (int i = 0; i < list.size(); i++) {
            Map<?, ?> map = list.get(i);
            if (map.size() == 0) {
                return new ArrayList<>();
            }
            else if (map.size() > 1) {
                throw new OrmDbException(I18n.t("KDBHttpChannel.error3", "查询单个属性，但返回不等于1的结果") );
            }
            map.forEach((key, value) -> {
                result.add((T)value);
            });
        }
        return result;
    }

    @Override
    public String httpPost(String url, Object params) {
        return null;
    }

    /**
     * 组装sql透传的参数
     * @param sql       sql
     * @param objects   参数
     * @return          透传参数
     */
    @SneakyThrows
    private KdbArgv makePassThrough(String sql, List<Object> objects) {
        KdbArgv argv = new KdbArgv();
        argv.setFlowID(passThroughFlowId);
        TransactionCache cache = TransactionManager.getInstance().getTransactionCache();
        if (cache != null) {
            logger.info("FAAS事务任务，签名:{}, ID:{}", cache.getSignName(), cache.getId());
            argv.setTransactionUuid(cache.getId());
        }


        // 将数据源名称作为节点id
        argv.addStep(kdbConnectConfig.getDataSource(), sql, kdbConnectConfig.getDataSource(), objects);
//        argv.addStep(executeStep, sql, kdbConnectConfig.getDataSource(), objects);
        // 将数据源名作为变量名加入进去
        argv.getVariables().put("nodeId", kdbConnectConfig.getDataSource());
        return argv;

    }


    /**
     *  从kdb请求获取数据
     * @param kdbArgv   kdb参数
     * @return          响应结果
     */
    private String send(KdbArgv kdbArgv) {
        try {
            // 转为json
            String requestBody = JsonUtil.toJson(kdbArgv);
            // 拼接请求
            List<String> urls = new ArrayList<>();
            String[] arr = kdbConnectConfig.getServer().split(";");
            String syncValue = SyncValueManager.getInstance().getSyncValue();
            boolean isAsync = syncValue != null;
            if (isAsync) {
                SyncValueManager.getInstance().clearSyncValue();
            }
            for (String a: arr) {
                if (!isAsync) {
                    urls.add(a +  "/api/execute");
                }
                else {
                    urls.add(a +  "/api/async/execute");
                }

            }
            String url = StringUtils.joinToString(urls, ";");
            long t1 = System.currentTimeMillis();
            // 发起请求
            String  responseBody = HttpUtil.postBody(url, requestBody, Collections.emptyMap(), true);
            if (isAsync) {
                responseBody = syncValue;
            }
            //logger.info("ResponseBody:{}", responseBody);

            long takeTime = System.currentTimeMillis() - t1;
//            if (takeTime < 3000) {
//                logger.debug("url:{} , Take: {} ,请求体: {}", url, takeTime ,requestBody);
//            }
//            else {
//                logger.warn("url:{} , Take: {} ,请求体: {}", url, takeTime ,requestBody);
//            }
            KdbRet<String> ret = JsonUtil.toBean(responseBody, KdbRet.class, String.class);
            if (ret == null) {
                throw new OrmDbException(I18n.t("KDBHttpChannel.error4", "kdb响应数据不合法，响应内容:{0}", responseBody) );
            }
            if (ret.getErrorCode() != 0) {
                logger.error(responseBody);
                throw new OrmDbException(ret.getMessage(), ret.getKlog(), ret.getStackTrace());
            }
            return ret.getResponseBody();

        }
        catch (HttpClientException e) {
            if (e.getMessage().contains("502") && e.getMessage().contains("HTTP POST request")) {
                return send(kdbArgv);
            }
            else {
                logger.error("sql执行失败，响应码:{}, 响应信息：{}，接口:{}, 参数:{}", e.getCode(), e.getMessage(), e.getUrl(), e.getParams());
                throw new OrmDbException(e.getMessage());
            }

        }
    }

}

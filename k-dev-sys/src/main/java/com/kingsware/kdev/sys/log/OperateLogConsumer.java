package com.kingsware.kdev.sys.log;

import com.kingsware.kdev.core.context.SpringContext;
import com.kingsware.kdev.core.kmq.KmqConsumer;
import com.kingsware.kdev.core.model.SysOperateLog;
import com.kingsware.kdev.core.orm.DB;
import com.kingsware.kdev.core.orm.kdb.SyncValueManager;
import com.kingsware.kdev.core.util.HttpUtil;
import com.kingsware.kdev.core.util.JsonUtil;
import com.kingsware.kdev.core.util.StringUtils;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 操作日志消费类
 *
 * @author chen peng
 * @version 1.0.0
 * @date 2021/12/31 1:48 下午
 */
@Slf4j
public class OperateLogConsumer implements KmqConsumer {
    @Override
    public void onMessage(List<String> payloads) throws Exception {
        long t1 = System.currentTimeMillis();
        String enableLog = SpringContext.getProperties("app.log.enable", "true");
        String toUniops = SpringContext.getProperties("uniops.log.enable", "false");
        String uniopsLogUrl = SpringContext.getProperties("uniops.log.url", "http://10.11.2.96:8083/mdb/log/batchSave");
        List<Map<String,Object>> rows  = new ArrayList<>();
        if ("true".equalsIgnoreCase(enableLog)) {
            List<SysOperateLog> sysOperateLogs = new ArrayList<>();
            for (String payload: payloads) {
                SysOperateLog sysOperateLog = JsonUtil.toBean(payload, SysOperateLog.class);
                if ("true".equalsIgnoreCase(toUniops)) {
                    Map<String, Object> row = new HashMap<>();
                    row.put("type", sysOperateLog.getModule());
                    row.put("content", sysOperateLog.getAction());
                    row.put("ip", sysOperateLog.getIp());
                    row.put("path", sysOperateLog.getUrl());
                    row.put("user", sysOperateLog.getOperator());
                    row.put("time", sysOperateLog.getOperateTime().getTime());
                    Map<String, Object> params = JsonUtil.toMap(sysOperateLog.getRequestBody());
                    if (params == null) {
                        params = new HashMap<>();
                        params.put("requestBody", sysOperateLog.getRequestBody());
                    }
                    else {
                        params.remove("request");
                        params.remove("requestBody");
                    }
                    row.put("param", params);
                    rows.add(row);
                }
                else {
                    if (StringUtils.isNotEmpty(sysOperateLog.getResponseBody()) && sysOperateLog.getResponseBody().length() > 1000) {
                        sysOperateLog.setResponseBody(sysOperateLog.getResponseBody().substring(0,1000));
                    }
                    // 处理请求参数
                    String requestBody = sysOperateLog.getRequestBody();
                    if (StringUtils.isNotEmpty(requestBody) && requestBody.length() > 1000) {
                        sysOperateLog.setRequestBody(requestBody.substring(0,1000));
                    }
                    sysOperateLogs.add(sysOperateLog);
                }

            }
            try {
                String resultValue = String.format("{\"errorCode\":0,\"message\":\"成功\",\"responseBody\":\"%d\",\"time\":1709277360835,\"total\":0}", sysOperateLogs.size());
                SyncValueManager.getInstance().setSyncValue(resultValue);
                if ("true".equalsIgnoreCase(toUniops)) {
                    Map<String, Object> requestBody = new HashMap<>();
                    requestBody.put("operateLogList", rows);
                    try {
                        String resp = HttpUtil.post(uniopsLogUrl, JsonUtil.toJson(requestBody), new HashMap<>());
                         log.info("日志推送：{}", JsonUtil.toJson(requestBody));
                        Map<String, Object> respMap = JsonUtil.toMap(resp);
                        if (respMap == null || respMap.get("errorCode") == null || (int)respMap.get("errorCode") != 0) {
                            log.warn("日志推送uniops失败：{}", resp);
                        }
                    }
                    catch (Exception e) {
                        log.error("error", e);
                    }

                }
                else {
                    DB.saveAll(sysOperateLogs);
                }

            }
            catch (Exception e) {
                log.error("error", e);
            }
            finally {
                SyncValueManager.getInstance().clearSyncValue();
            }
            long t2 = System.currentTimeMillis();
            //log.info("[{}]- consumer: {}, consume {} records, consume time: {} ms",md5, topic(), payloads.size(), t2 - t1);
        }




    }

    @Override
    public String topic() {
        return "t_operate_log";
    }
}

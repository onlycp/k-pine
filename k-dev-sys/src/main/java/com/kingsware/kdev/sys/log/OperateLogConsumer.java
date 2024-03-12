package com.kingsware.kdev.sys.log;

import com.kingsware.kdev.core.context.SpringContext;
import com.kingsware.kdev.core.kmq.KmqConsumer;
import com.kingsware.kdev.core.orm.DB;
import com.kingsware.kdev.core.orm.kdb.SyncValueManager;
import com.kingsware.kdev.core.util.JsonUtil;
import com.kingsware.kdev.core.model.SysOperateLog;
import com.kingsware.kdev.core.util.MD5Utils;
import com.kingsware.kdev.core.util.StringUtils;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;

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
        String md5 = MD5Utils.md5(JsonUtil.toJson(payloads));
        String enableLog = SpringContext.getProperties("app.log.enable", "true");
        if ("true".equalsIgnoreCase(enableLog)) {
            List<SysOperateLog> sysOperateLogs = new ArrayList<>();
            for (String payload: payloads) {
                SysOperateLog sysOperateLog = JsonUtil.toBean(payload, SysOperateLog.class);
                if (StringUtils.isNotEmpty(sysOperateLog.getResponseBody()) && sysOperateLog.getResponseBody().length() > 1000) {
                    sysOperateLog.setResponseBody(sysOperateLog.getResponseBody().substring(0,1000));
                }
                sysOperateLogs.add(sysOperateLog);
            }
            try {
                String resultValue = String.format("{\"errorCode\":0,\"message\":\"成功\",\"responseBody\":\"%d\",\"time\":1709277360835,\"total\":0}", sysOperateLogs.size());
                SyncValueManager.getInstance().setSyncValue(resultValue);
                DB.saveAll(sysOperateLogs);
            }
            catch (Exception e) {
                log.error("error", e);
            }
            finally {
                SyncValueManager.getInstance().clearSyncValue();
            }
            long t2 = System.currentTimeMillis();
            log.info("[{}]- consumer: {}, consume {} records, consume time: {} ms",md5, topic(), payloads.size(), t2 - t1);
        }




    }

    @Override
    public String topic() {
        return "t_operate_log";
    }
}

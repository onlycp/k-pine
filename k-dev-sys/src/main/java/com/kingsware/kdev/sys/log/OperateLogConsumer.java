package com.kingsware.kdev.sys.log;

import com.kingsware.kdev.core.kmq.KmqConsumer;
import com.kingsware.kdev.core.orm.DB;
import com.kingsware.kdev.core.util.JsonUtil;
import com.kingsware.kdev.core.model.SysOperateLog;
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
        List<SysOperateLog> sysOperateLogs = new ArrayList<>();
        for (String payload: payloads) {
            SysOperateLog sysOperateLog = JsonUtil.toBean(payload, SysOperateLog.class);
            if (StringUtils.isNotEmpty(sysOperateLog.getResponseBody()) && sysOperateLog.getResponseBody().length() > 1000) {
                sysOperateLog.setResponseBody(sysOperateLog.getResponseBody().substring(0,1000));
            }
            sysOperateLogs.add(sysOperateLog);
        }
        DB.saveAll(sysOperateLogs);


    }

    @Override
    public String topic() {
        return "t_operate_log";
    }
}

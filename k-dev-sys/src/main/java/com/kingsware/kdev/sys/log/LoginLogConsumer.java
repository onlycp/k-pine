package com.kingsware.kdev.sys.log;

import com.kingsware.kdev.core.kmq.KmqConsumer;
import com.kingsware.kdev.core.orm.DB;
import com.kingsware.kdev.core.util.JsonUtil;
import com.kingsware.kdev.sys.model.SysLoginLog;
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
public class LoginLogConsumer implements KmqConsumer {

    @Override
    public void onMessage(List<String> payloads) throws Exception {
        List<SysLoginLog>  sysLoginLogs = new ArrayList<>();
        for (String payload: payloads) {
            SysLoginLog sysLoginLog = JsonUtil.toBean(payload, SysLoginLog.class);
            sysLoginLogs.add(sysLoginLog);
        }
        DB.saveAll(sysLoginLogs);
    }

    @Override
    public String topic() {
        return WebLogAspect.TOPIC_LOGIN_LOG;
    }

}

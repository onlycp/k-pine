package com.kingsware.kdev.core.log;

import com.kingsware.kdev.core.kmq.KmqConsumer;
import com.kingsware.kdev.core.orm.DB;
import com.kingsware.kdev.core.util.JsonUtil;
import com.kingsware.kdev.sys.model.SysLoginLog;
import com.kingsware.kdev.sys.model.SysOperateLog;
import lombok.extern.slf4j.Slf4j;

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
    public void onMessage(String payload) throws Exception {
        SysLoginLog sysLoginLog = JsonUtil.toBean(payload, SysLoginLog.class);
        DB.save(sysLoginLog);


    }

    @Override
    public String topic() {
        return WebLogAspect.TOPIC_LOGIN_LOG;
    }
}

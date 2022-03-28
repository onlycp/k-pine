package com.kingsware.kdev.core.cache.session;

import com.kingsware.kdev.core.auth.AppAuthProperties;
import com.kingsware.kdev.core.cache.dict.DictItemInfo;
import com.kingsware.kdev.core.cache.dict.DictManager;
import com.kingsware.kdev.core.context.SpringContext;
import com.kingsware.kdev.core.cron.KTask;
import com.kingsware.kdev.core.model.SysOnlineUser;
import com.kingsware.kdev.core.orm.DB;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;

/**
 * 会话任务
 * @author chen peng
 * @version 1.0.0
 * @date 2022/1/6 9:41 上午
 */
@Slf4j
public class SessionTask implements KTask {

    public SessionTask() {
        // 启动时加载
        this.execute();
    }

    /**
     * 定时拉取字典项
     */
    @Override
    public void execute() {
        AppAuthProperties appAuthProperties = SpringContext.getBean(AppAuthProperties.class);
        // 移除过期的会话
        DB.executeUpdateSql("delete from sys_online_user where expire_time < now()");
        // 移除心跳超时的会话
        Set<TokenSession> sessions = SessionManager.getInstance().sessions();
        // 心跳过期的方案
        List<TokenSession> pingExpiredSessions = new ArrayList<>();
        for (TokenSession session: sessions) {
            // 心跳为空的不算进去，为了兼容没升级的前端
            if (session.getPingTime() == null) {
                continue;
            }
            // 计算超时秒
            int seconds = (int)Math.abs(session.getPingTime().getTime() - System.currentTimeMillis())/1000;
            if (seconds > appAuthProperties.getPingExpireSeconds()) {
                pingExpiredSessions.add(session);
            }
        }
        for (TokenSession session: pingExpiredSessions) {
            SysOnlineUser onlineUser = DB.findById(SysOnlineUser.class, session.getId());
            if (onlineUser != null) {
                SessionManager.getInstance().removeSession(onlineUser.getUserId(), onlineUser.getLoginToken());
                DB.delete(onlineUser);
                log.info("用户名：{} 退出，原因是心跳超时", onlineUser.getUserId());

            }

        }
        // 重置加载
        SessionManager.getInstance().reloadSessions();
    }

    @Override
    public String cron() {
        return "0/5 * * * * ?";
    }

    @Override
    public String name() {
        return "SessionTask";
    }
}

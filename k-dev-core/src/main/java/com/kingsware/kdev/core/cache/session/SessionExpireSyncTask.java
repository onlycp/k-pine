package com.kingsware.kdev.core.cache.session;

import com.kingsware.kdev.core.auth.AppAuthProperties;
import com.kingsware.kdev.core.context.SpringContext;
import com.kingsware.kdev.core.cron.KTask;
import com.kingsware.kdev.core.orm.DB;
import com.kingsware.kdev.core.util.DateUtils;

import java.util.Date;
import java.util.Map;
import java.util.Set;

/**
 * 会话过期时间处理
 * @author chen peng
 * @version 1.0.0
 * @date 2022/1/6 9:41 上午
 */
public class SessionExpireSyncTask implements KTask {

    public SessionExpireSyncTask() {
    }

    /**
     * 定时拉取字典项
     */
    @Override
    public void execute() {
        AppAuthProperties appAuthProperties = SpringContext.getBean(AppAuthProperties.class);
        // 只有是session模式才处理
        if (appAuthProperties.getMockSessionExpireMinutes() > 0) {
            Set<TokenSession> tokenSessions = SessionManager.getInstance().getChanged();
            for (TokenSession ts: tokenSessions) {
                DB.executeUpdateSql("update sys_online_user set expire_time=? where id=?", DateUtils.formatDate(new Date(ts.getExpireTime().getTime()), DateUtils.DATE_TIME), ts.getId());
            }
        }

    }

    @Override
    public String cron() {
        return "0/10 * * * * ?";
    }

    @Override
    public String name() {
        return "SessionExpireSyncTask";
    }
}

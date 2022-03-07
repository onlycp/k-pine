package com.kingsware.kdev.core.cache.session;

import com.kingsware.kdev.core.cache.dict.DictItemInfo;
import com.kingsware.kdev.core.cache.dict.DictManager;
import com.kingsware.kdev.core.cron.KTask;
import com.kingsware.kdev.core.model.SysOnlineUser;
import com.kingsware.kdev.core.orm.DB;

import java.util.Collections;
import java.util.List;

/**
 * 会话任务
 * @author chen peng
 * @version 1.0.0
 * @date 2022/1/6 9:41 上午
 */
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
        // 更新会话有效期

        // 移除过期的会话
        DB.executeUpdateSql("delete from sys_online_user where expire_time < now()");
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

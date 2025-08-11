package com.kingsware.kdev.core.cache.permssion;

import com.kingsware.kdev.core.cron.KRunner;
import com.kingsware.kdev.core.cron.KTask;

/**
 * 字典定时任务，从数据库定时加载
 *
 * @author chen peng
 * @version 1.0.0
 * @date 2022/1/6 9:41 上午
 */
public class PermissionTask implements KTask, KRunner {

    public PermissionTask() {
    }

    /**
     * 定时刷新权限
     */
    @Override
    public void execute() {
        PermissionManager.getInstance().refreshAll();
    }

    @Override
    public String cron() {
        return "0 0/2 * * * ?";
    }

    @Override
    public String name() {
        return "系统用户权限任务";
    }

    @Override
    public String note() {
        return "定时刷新用户权限";
    }

    @Override
    public void runNow() {
        this.execute();
    }
}

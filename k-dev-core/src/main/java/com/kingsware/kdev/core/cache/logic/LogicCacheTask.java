package com.kingsware.kdev.core.cache.logic;

import com.kingsware.kdev.core.cache.open.OpenAccount;
import com.kingsware.kdev.core.cache.open.OpenAccountApiCode;
import com.kingsware.kdev.core.cache.open.OpenAccountInfo;
import com.kingsware.kdev.core.cache.open.OpenApiManager;
import com.kingsware.kdev.core.cron.KRunner;
import com.kingsware.kdev.core.cron.KTask;
import com.kingsware.kdev.core.orm.DB;
import com.kingsware.kdev.core.util.JsonUtil;
import com.kingsware.kdev.core.util.StringUtils;
import lombok.extern.slf4j.Slf4j;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 字典定时任务，从数据库定时加载
 *
 * @author chen peng
 * @version 1.0.0
 * @date 2022/1/6 9:41 上午
 */
@Slf4j
public class LogicCacheTask implements KTask, KRunner {

    public LogicCacheTask() {
    }

    /**
     * 定时拉取字典项
     */
    @Override
    public void execute() {

        List<LogicCache> logicCaches = DB.findList(LogicCache.class, "select flow_id, app_id as tran_ctrl from sys_logic_flow where flow_id is not null and app_id = ?", '1');
        LogicFlowManager.getInstance().setFlowCache(logicCaches);
    }

    @Override
    public String cron() {
        return "0/10 * * * * ?";
    }

    @Override
    public String name() {
        return "系统事务缓存任务";
    }

    @Override
    public String note() {
        return "系统事务缓存任务，定时拉取逻辑编排的事务配置";
    }

    @Override
    public void runNow() {
        this.execute();
    }
}

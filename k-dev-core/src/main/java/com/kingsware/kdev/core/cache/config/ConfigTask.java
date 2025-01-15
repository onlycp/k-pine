package com.kingsware.kdev.core.cache.config;

import com.kingsware.kdev.core.cache.access.AccessCmdRunner;
import com.kingsware.kdev.core.context.SpringContext;
import com.kingsware.kdev.core.cron.KRunner;
import com.kingsware.kdev.core.cron.KTask;
import com.kingsware.kdev.core.orm.DB;
import com.kingsware.kdev.core.util.FaasInvoke;
import com.kingsware.kdev.core.util.StringUtils;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

/**
 * 系统参数配置定时任务，从数据库定时加载
 *
 * @author chen peng
 * @version 1.0.0
 * @date 2022/1/6 9:41 上午
 */
@Slf4j
public class ConfigTask implements KTask, KRunner {

    public ConfigTask() {

    }

    /**
     * 定时拉取字典项
     */
    @Override
    public void execute() {
        // 查找所有字典
        String sql = "select name, code, value_type, value, is_sys,app_id from sys_config";
        if (DB.getDefault().getConfig().getInnerType().equalsIgnoreCase("h2")) {
            sql = "select name, code, value_type, `value`, is_sys,app_id from sys_config";
        }
        List<SysConfigInfo> list = DB.findList(SysConfigInfo.class, sql);
        for (SysConfigInfo data: list) {
            if(data.getCode().contains("t-")) {
                System.currentTimeMillis();
            }
            ConfigManager.getInstance().addItem(data.getCode(), data);
            if (StringUtils.isNotEmpty(data.getAppId())) {
                ConfigManager.getInstance().addItem(data.getAppId() + "." + data.getCode(), data);
            }
        }
        // 加载数据权限

        try {
            AccessCmdRunner accessCmdRunner = SpringContext.getBean(AccessCmdRunner.class);
            accessCmdRunner.run();
        }
        catch (Exception e) {
            log.info("加载数据权限配置失败:" + e);
        }
    }

    @Override
    public String cron() {
        return "0/10 * * * * ?";
    }

    @Override
    public String name() {
        return "系统参数配置定时任务";
    }

    @Override
    public String note() {
        return "系统参数配置定时任务，从数据库定时加载";
    }

    @Override
    public void runNow() {
        this.execute();
    }
}

package com.kingsware.kdev.core.cache.config;

import com.kingsware.kdev.core.cron.KRunner;
import com.kingsware.kdev.core.cron.KTask;
import com.kingsware.kdev.core.orm.DB;

import java.util.List;

/**
 * 系统参数配置定时任务，从数据库定时加载
 *
 * @author chen peng
 * @version 1.0.0
 * @date 2022/1/6 9:41 上午
 */
public class ConfigTask implements KTask, KRunner {

    public ConfigTask() {

    }

    /**
     * 定时拉取字典项
     */
    @Override
    public void execute() {
        // 查找所有字典
        List<SysConfigInfo> list = DB.findList(SysConfigInfo.class, "select * from sys_config");
        for (SysConfigInfo data: list) {
            ConfigManager.getInstance().addItem(data.getCode(), data);
        }
    }

    @Override
    public String cron() {
        return "0/10 * * * * ?";
    }

    @Override
    public String name() {
        return "ConfigTask";
    }

    @Override
    public void runNow() {
        this.execute();
    }
}

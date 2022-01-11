package com.kingsware.kdev.biz.kw.scheduled;

import com.kingsware.kdev.biz.kw.service.impl.KwAbnormalServiceImpl;
import com.kingsware.kdev.core.cron.KTask;

import java.util.ResourceBundle;

/**
 * 检查余额异常
 */
public class WaterCheckBalanceTask implements KTask {

    private String cron;

    public WaterCheckBalanceTask(){
        ResourceBundle res = ResourceBundle.getBundle("application");
        this.cron = res.getString("schedule.WaterCheckBalanceTask.cron");
    }

    @Override
    public void execute() throws Exception {
        new KwAbnormalServiceImpl().checkBalance();
    }

    @Override
    public String cron() {
        return this.cron;
    }

    @Override
    public String name() {
        return "WaterCheckBalanceTask";
    }
}

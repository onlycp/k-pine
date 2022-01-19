package com.kingsware.kdev.biz.kw.scheduled;

import com.kingsware.kdev.biz.kw.service.KwAbnormalService;
import com.kingsware.kdev.biz.kw.service.impl.KwAbnormalServiceImpl;
import com.kingsware.kdev.core.context.SpringContext;
import com.kingsware.kdev.core.cron.KTask;
import com.kingsware.kdev.core.util.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ResourceBundle;

/**
 * 检查余额异常
 */
public class WaterCheckBalanceTask implements KTask {

    private static String cron;

    private static KwAbnormalService kwAbnormalService;

    public WaterCheckBalanceTask(){
    }

    @Override
    public void execute() {
        if (kwAbnormalService==null)
            this.kwAbnormalService = SpringContext.getBean(KwAbnormalService.class);// kwAbnormalService = new KwAbnormalServiceImpl();
        kwAbnormalService.checkBalance();
    }

    @Override
    public String cron() {
        if (this.cron==null || StringUtils.isEmpty(this.cron));
            this.cron=SpringContext.getProperties("schedule.WaterCheckBalanceTask.cron","0 15 0/2 * * ?");
        return this.cron;
    }

    @Override
    public String name() {
        return "WaterCheckBalanceTask";
    }
}

package com.kingsware.kdev.biz.kw.task;

import com.kingsware.kdev.biz.kw.service.KwRPABankViewService;
import com.kingsware.kdev.core.context.SpringContext;
import com.kingsware.kdev.core.cron.KTask;
import com.kingsware.kdev.core.util.BeanUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * @author andyzheng
 * @version 1.0.0
 * @description:
 * @date 2022/1/19TODO 16:49
 */
public class UpdateBankByViewTask implements KTask {
    /** 日志打印 **/
    private static final Logger logger  = LoggerFactory.getLogger(UpdateBankByViewTask.class);

    @Override
    public void execute() throws Exception {
        KwRPABankViewService kwRPABankViewService = SpringContext.getBean("kwRPABankViewServiceImpl");
        logger.info("开始自动任务：", this.name(), System.currentTimeMillis());
        kwRPABankViewService.updateBankAccountAllByView();
        logger.info("结束自动任务：", this.name(), System.currentTimeMillis());
    }

    @Override
    public String cron() {
        // 每天凌晨1点执行一次
        return "0 0 1 * * ?";
//        return "0/30 * * * * ?";  // 自测用的
    }

    @Override
    public String name() {
        return "UpdateBankByViewTask";
    }
}

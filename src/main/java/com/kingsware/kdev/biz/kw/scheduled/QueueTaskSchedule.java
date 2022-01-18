package com.kingsware.kdev.biz.kw.scheduled;

import com.kingsware.kdev.biz.kw.enums.QueueTaskStatusEnum;
import com.kingsware.kdev.biz.kw.enums.QueueTaskTypeEnum;
import com.kingsware.kdev.biz.kw.ret.KwQueueTaskRet;
import com.kingsware.kdev.biz.kw.service.QueueTaskProcessService;
import com.kingsware.kdev.biz.kw.service.impl.KwQueueTaskServiceImpl;
import com.kingsware.kdev.core.context.SpringContext;
import com.kingsware.kdev.core.cron.KTask;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ResourceBundle;

/**
 * 定时从队列中获取任务处理
 *
 * @author amzc
 * @date 2022/1/11 17:32
 **/
@Slf4j
public class QueueTaskSchedule implements KTask {
    //@Resource
    KwQueueTaskServiceImpl kwQueueTaskService = new KwQueueTaskServiceImpl();

    //@Resource
    QueueTaskProcessService queueTaskProcessService = new QueueTaskProcessService();

    private String cron;

    public QueueTaskSchedule(){

    }

    @Override
    public void execute() {
        //持续获取新任务
        while (true){
            KwQueueTaskRet task = kwQueueTaskService.getNewOne();
            if (task == null){
                break;
            }
            log.info("处理任务：" + task.getName() + " " + task.getId() + " data：" + task.getData());
            kwQueueTaskService.updateStatus(task.getId(), QueueTaskStatusEnum.PROCESSING);
            switch (QueueTaskTypeEnum.get(task.getType())){
                case SINGLE_SCAN:
                    log.info("执行扫描流水");
                    queueTaskProcessService.singleScanTask(task);
                    break;
//                case ALL_SCAN:
//                    queueTaskProcessService.allScanTask(task);
//                    break;
//                case IMPORT_BANK:
//                    queueTaskProcessService.importBank(task);
//                    break;
//                case IMPORT_YEAR_WATER:
//                    queueTaskProcessService.importYearWater(task);
//                    break;
//                case SEND_MBS_WATER:
//                    //不推送流水则注释此语句
//                    queueTaskProcessService.sendMbsReq(task);
//                    break;
//                case SEND_MBS_BALANCE:
//                    queueTaskProcessService.sendMbsBalance(task);
//                    break;
//                case DELETE_WATER_RECEIPT_MACH:
//                    queueTaskProcessService.deleteReceiptAndWater(task);
//                    break;
//                case IMPORT_BANK_MBS:
//                    queueTaskProcessService.importBank2(task);
//                    break;
//                case IMPORT_MBS_SEND:
//                    queueTaskProcessService.updateMbsSend(task);
//                    break;
//                case RESEND_MBS_WATER:
//                    queueTaskProcessService.resendMbsWater(task);
//                    break;
//                default:
//                    logger.error("错误的任务类型：" + task.getName() + " " + task.getId() + " data：" + task.getData() + " type: " + task.getType());
//                    queueTaskService.updateStatus(task.getId(), QueueTaskStatusEnum.ERROR);
//                    break;
            }
        }
    }

    @Override
    public String cron() {
        this.cron = SpringContext.getProperties("schedule.QueueTask.cron",null);
        return cron;
    }

    @Override
    public String name() {
        return "QueueTask";
    }
}

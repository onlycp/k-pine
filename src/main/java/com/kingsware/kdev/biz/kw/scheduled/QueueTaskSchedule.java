package com.kingsware.kdev.biz.kw.scheduled;

import com.kingsware.kdev.biz.kw.enums.QueueTaskStatusEnum;
import com.kingsware.kdev.biz.kw.enums.QueueTaskTypeEnum;
import com.kingsware.kdev.biz.kw.ret.KwQueueTaskRet;
import com.kingsware.kdev.biz.kw.service.QueueTaskProcessService;
import com.kingsware.kdev.biz.kw.service.impl.KwQueueTaskServiceImpl;
import com.kingsware.kdev.core.cron.KTask;
import lombok.extern.slf4j.Slf4j;

import javax.annotation.Resource;

/**
 * @description:
 * @author: amzc
 * @date: 2022-01-11 17:32
 **/
@Slf4j
public class QueueTaskSchedule implements KTask {
    //@Resource
    KwQueueTaskServiceImpl kwQueueTaskService = new KwQueueTaskServiceImpl();
    //todo
    //@Resource
    QueueTaskProcessService queueTaskProcessService = new QueueTaskProcessService();

//    private String cron;
//
//    public QueueTaskSchedule(){
//        ResourceBundle res = ResourceBundle.getBundle("application");
//        this.cron = res.getString("schedule.QueueTask.cron");
//    }

    @Override
    public void execute() {
        //持续获取新任务
        while (true){
            System.out.println("持续获取任务");
            if(kwQueueTaskService == null){
                System.out.println(1232);
            }
            KwQueueTaskRet task = kwQueueTaskService.getNewOne();
            if (task == null){
                System.out.println("没有可用的任务");
                break;
            }
            log.info("处理任务：" + task.getName() + " " + task.getId() + " data：" + task.getData());
            kwQueueTaskService.updateStatus(task.getId(), QueueTaskStatusEnum.PROCESSING);
            System.out.println("QueueTaskTypeEnum.get"+QueueTaskTypeEnum.get(task.getType()));
            switch (QueueTaskTypeEnum.get(task.getType())){
                case SINGLE_SCAN:
                    System.out.println("执行扫描流水");
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
        return "0 0/1 * * * ?";
    }

    @Override
    public String name() {
        return "QueueTask";
    }
}

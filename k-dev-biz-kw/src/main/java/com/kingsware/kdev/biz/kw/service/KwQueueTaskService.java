package com.kingsware.kdev.biz.kw.service;

import com.kingsware.kdev.biz.kw.argv.KwQueueTaskArgv;
import com.kingsware.kdev.biz.kw.enums.QueueTaskStatusEnum;
import com.kingsware.kdev.biz.kw.ret.KwQueueTaskRet;
import com.kingsware.kdev.core.base.BaseService;

/**
 * 队列任务的curd
 *
 * @author amzc
 * @version 1.0.0
 * @date 2022/1/11 15:05
 **/
public interface KwQueueTaskService extends BaseService {

    /**
     * 添加队列任务
     * @param argv
     */
    KwQueueTaskRet addNew(KwQueueTaskArgv argv);

    /**
     * 获取一个未处理的队列任务
     * @return     队列任务信息
     */
    KwQueueTaskRet getNewOne();

    /**
     * 更新状态
     * @param id          队列任务ID
     * @param status      状态
     */
    void updateStatus(String id, QueueTaskStatusEnum status);

}

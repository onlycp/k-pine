package com.kingsware.kdev.biz.kw.service.impl;

import com.kingsware.kdev.biz.kw.argv.KwQueueTaskArgv;
import com.kingsware.kdev.biz.kw.argv.KwWaterQueryArgv;
import com.kingsware.kdev.biz.kw.enums.QueueTaskStatusEnum;
import com.kingsware.kdev.biz.kw.model.KwBankAccountExpand;
import com.kingsware.kdev.biz.kw.model.KwCompany;
import com.kingsware.kdev.biz.kw.model.KwEdition;
import com.kingsware.kdev.biz.kw.model.KwQueueTask;
import com.kingsware.kdev.biz.kw.ret.KwCompanyRet;
import com.kingsware.kdev.biz.kw.ret.KwQueueTaskRet;
import com.kingsware.kdev.biz.kw.ret.KwWaterRet;
import com.kingsware.kdev.biz.kw.service.KwQueueTaskService;
import com.kingsware.kdev.core.base.BaseServiceImpl;
import com.kingsware.kdev.core.bean.BaseSimpleRet;
import com.kingsware.kdev.core.bean.PageDataRet;
import com.kingsware.kdev.core.orm.DB;
import com.kingsware.kdev.core.orm.SqlWrapper;
import com.kingsware.kdev.core.orm.expression.Op;
import com.kingsware.kdev.core.util.BeanUtils;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;

import java.util.UUID;

/**
 * @description: 队列任务业务逻辑
 * @author: amzc
 * @date: 2022-01-11 15:58
 **/
@Service
public class KwQueueTaskServiceImpl extends BaseServiceImpl implements KwQueueTaskService {
    /**
     * 添加队列任务
     * @param argv
     */
    @Override
    public KwQueueTaskRet addNew(KwQueueTaskArgv argv) {
        argv.setStatus(QueueTaskStatusEnum.NEW.getValue());
        KwQueueTask model = BeanUtils.copyObject(argv, KwQueueTask.class);
        DB.save(model);
        return BeanUtils.copyObject(model, KwQueueTaskRet.class);
    }

    /**
     * 获取一个未处理的队列任务
     * @return          队列任务信息
     */
    @Override
    public KwQueueTaskRet getNewOne(){
        //根据时间倒序获取第一个状态是新增的任务
        System.out.println("执行获取业务");
        SqlWrapper wrapper = new SqlWrapper(" select * from kw_queue_task as kqt where 1 = 1");
        wrapper.addCondition("kqt.status", Op.EQ, QueueTaskStatusEnum.NEW.getValue());
        wrapper.sortBy(" ORDER BY kqt.when_created asc limit 1 ");
        return DB.findOne(KwQueueTaskRet.class, wrapper.getSql(), wrapper.getParams().toArray());
    }

    /**
     * 更新状态
     * @param id            队列任务ID
     * @param status        状态
     */
    //TODO: 2022/1/12 事务注解没导包
    //@Transactional
    @Override
    public void updateStatus(String id, QueueTaskStatusEnum status){
        System.out.println("更新状态");
        KwQueueTask model = DB.findById(KwQueueTask.class, id);
        System.out.println("model=="+ model);
        System.out.println("status=="+status);
        model.setStatus(status.getValue());
        DB.update(model);
    }
}

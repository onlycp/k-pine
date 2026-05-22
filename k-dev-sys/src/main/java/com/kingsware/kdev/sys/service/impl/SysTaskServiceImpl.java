package com.kingsware.kdev.sys.service.impl;

import com.kingsware.kdev.core.base.BaseServiceImpl;
import com.kingsware.kdev.core.bean.MultiIdArgv;
import com.kingsware.kdev.core.bean.PageDataRet;
import com.kingsware.kdev.core.config.SysConst;
import com.kingsware.kdev.core.cron.DynamicTask;
import com.kingsware.kdev.core.cron.JavaTaskClassGuard;
import com.kingsware.kdev.core.exception.BusinessException;
import com.kingsware.kdev.core.i18n.I18n;
import com.kingsware.kdev.core.orm.DB;
import com.kingsware.kdev.core.orm.DBChecker;
import com.kingsware.kdev.core.orm.SqlWrapper;
import com.kingsware.kdev.core.orm.expression.Op;
import com.kingsware.kdev.core.util.*;
import com.kingsware.kdev.sys.argv.SysTaskArgv;
import com.kingsware.kdev.sys.argv.SysTaskQueryArgv;
import com.kingsware.kdev.core.model.SysTask;
import com.kingsware.kdev.sys.ret.SysTaskRet;
import com.kingsware.kdev.sys.service.SysTaskService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.support.CronExpression;
import org.springframework.stereotype.Service;

import jakarta.annotation.Resource;
import java.util.Map;

/**
 * 任务调度
 *
 * @author chen peng
 * @version 1.0.0
 * @date 2021/12/23 9:36 上午
 */
@Service
@Slf4j
public class SysTaskServiceImpl extends BaseServiceImpl implements SysTaskService {

    @Resource
    private DynamicTask dynamicTask;

    @Override
    public SysTaskRet get(String id) {
        // 查询model
        SysTask model = DB.findById(SysTask.class, id);
        // 转换成ret对象
        return (SysTaskRet) model2Ret(model, SysTaskRet.class);
    }

    @Override
    public void add(SysTaskArgv argv) {
        validateJavaTaskConfig(argv);
        SysTask model = BeanUtils.copyObject(argv, SysTask.class);
        if (model.getTaskType() == null || model.getTaskType() != 1) {
            model.setClassName(null);
        }
        // 校验
        checkUnique(model);
        this.checkCron(argv.getCron());
        // 保存
        DB.save(model);
        // 注册任务
//        dynamicTask.startTask(model);
    }

    public void checkCron(String cron) {
        if (StringUtils.isEmpty(cron)) {
            throw BusinessException.serviceThrow(I18n.t("SysTask.cron.empty", "cron表达式不能为空"));
        }
        if (NumberUtils.isInteger(cron)) {
            return;
        }
        try {
            CronExpression.parse(cron);
        }
        catch (Exception e) {
            throw BusinessException.serviceThrow(I18n.t("SysTask.cron.error", "cron表达式错误"));
        }

    }

    @Override
    public void edit(SysTaskArgv argv) {
        validateJavaTaskConfig(argv);
        SysTask model = DB.findById(SysTask.class, argv.getId());
        if (isSystemTask(model)) {
            if (argv.getEnable() == 0 && model.getEnable() == 1) {
                throw BusinessException.serviceThrow(I18n.t("SysTask.cron.system-task-disenable", "该任务为系统内置，不允许禁用"));
            }
        }

        model.setName(argv.getName());
        model.setCron(argv.getCron());
        model.setTaskType(argv.getTaskType());
        model.setTaskResourceId(argv.getTaskResourceId());
        if (argv.getTaskType() != null && argv.getTaskType() == 1) {
            model.setClassName(argv.getClassName());
        } else {
            model.setClassName(null);
        }
        model.setEnable(argv.getEnable());
        model.setDistributed(argv.getDistributed());
        model.setNote(argv.getNote());
        model.setTaskArgv(argv.getTaskArgv());
        this.checkCron(argv.getCron());
        // 校验
        checkUnique(model);
        // 保存
        DB.update(model);
        // 更新动态任务
//        dynamicTask.updateTask(model);
    }

    /**
     * 是否为系统任务
     * @param task
     * @return
     */
    private boolean isSystemTask(SysTask task) {
        String appId = task.getAppId();
        if (StringUtils.isEmpty(task.getApplicationId())) {
            appId = task.getApplicationId();
        }
        if (StringUtils.isEmpty(appId)) {
            return false;
        }
        return appId.equals(SysConst.pineAppId);
    }


    private void checkUnique(SysTask model) {
        // 唯一性校验
        DBChecker<SysTask> checker =DBChecker.build(model, SysTask.class);
        // 名称唯一
        checker.uni(new String[]{"name", "applicationId"}, I18n.t("SysTask.name.unique", "同一应用下名称必须唯一"));
        // 执行校验
        checker.checkUnique();
    }

    private void validateJavaTaskConfig(SysTaskArgv argv) {
        if (argv == null || argv.getTaskType() == null || argv.getTaskType() != 1) {
            return;
        }
        if (StringUtils.isEmpty(argv.getClassName())) {
            throw BusinessException.serviceThrow(I18n.t("SysTask.class-name.empty", "Java任务className不能为空"));
        }
        try {
            JavaTaskClassGuard.validateOrThrow(argv.getClassName());
        } catch (IllegalArgumentException e) {
            throw BusinessException.serviceThrow(I18n.t("SysTask.class-name.invalid", "Java任务className不允许: " + e.getMessage()));
        }
    }

    @Override
    @SuppressWarnings("unchecked")
    public PageDataRet<SysTaskRet> query(SysTaskQueryArgv argv) {
        // 拼装sql
        SqlWrapper wrapper = new SqlWrapper("select t.* from sys_task t where 1=1 ");
        // 拼装查询sql
        if (StringUtils.isNotEmpty(argv.getName())) {
            wrapper.addCondition("t.name", Op.LIKE, "%" +argv.getName() +"%");
        }
        if (argv.getEnable() != null) {
            wrapper.addCondition("t.enable", Op.EQ, argv.getEnable());
        }
        if (argv.getDistributed() != null) {
            wrapper.addCondition("t.distributed", Op.EQ, argv.getDistributed());
        }
        if (argv.getTaskType() != null) {
            wrapper.addCondition("t.task_type", Op.EQ, argv.getTaskType());
        }
        if (StringUtils.isNotEmpty(argv.getAppId())) {
            wrapper.appendSql(" and (t.application_id = ?)", argv.getAppId());
        }
        wrapper.sortBy("when_created desc");
        return (PageDataRet<SysTaskRet>) query(wrapper.getSql(), wrapper.getParams(), argv, SysTask.class, SysTaskRet.class);
    }

    @Override
    public void delete(MultiIdArgv argv) {
        for (String id: argv.getIds()) {
            DB.delete(SysTask.class, id);
        }
    }

    /**
     * 任务执行
     *
     * @param taskId   任务id
     */
    @Override
    public void executeTask(String taskId) {
//        log.info("Http触发定时任务，任务id:{}", taskId);
        SysTask sysTask = DB.findById(SysTask.class, taskId);
        new Thread(() -> dynamicTask.executeTask(sysTask)).start();
    }
}

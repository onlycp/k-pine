package com.kingsware.kdev.sys.service.impl;

import com.kingsware.kdev.core.base.BaseServiceImpl;
import com.kingsware.kdev.core.bean.MultiIdArgv;
import com.kingsware.kdev.core.bean.PageDataRet;
import com.kingsware.kdev.core.cron.DynamicTask;
import com.kingsware.kdev.core.i18n.I18n;
import com.kingsware.kdev.core.orm.DB;
import com.kingsware.kdev.core.orm.DBChecker;
import com.kingsware.kdev.core.orm.SqlWrapper;
import com.kingsware.kdev.core.orm.expression.Op;
import com.kingsware.kdev.core.util.AESUtil;
import com.kingsware.kdev.core.util.BeanUtils;
import com.kingsware.kdev.core.util.JsonUtil;
import com.kingsware.kdev.core.util.StringUtils;
import com.kingsware.kdev.sys.argv.SysTaskArgv;
import com.kingsware.kdev.sys.argv.SysTaskQueryArgv;
import com.kingsware.kdev.core.model.SysTask;
import com.kingsware.kdev.sys.ret.SysTaskRet;
import com.kingsware.kdev.sys.service.SysTaskService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
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

    @Value("${encrypt.aes.secret:PsLZlcuUJBUB8yPo}")
    private String aesSecret;

    @Override
    public SysTaskRet get(String id) {
        // 查询model
        SysTask model = DB.findById(SysTask.class, id);
        // 转换成ret对象
        return (SysTaskRet) model2Ret(model, SysTaskRet.class);
    }

    @Override
    public void add(SysTaskArgv argv) {
        SysTask model = BeanUtils.copyObject(argv, SysTask.class);
        // 校验
        checkUnique(model);
        // 保存
        DB.save(model);
        // 注册任务
//        dynamicTask.startTask(model);
    }

    @Override
    public void edit(SysTaskArgv argv) {
        SysTask model = DB.findById(SysTask.class, argv.getId());
        model.setName(argv.getName());
        model.setCron(argv.getCron());
        model.setTaskType(argv.getTaskType());
        model.setTaskResourceId(argv.getTaskResourceId());
        model.setClassName(argv.getClassName());
        model.setEnable(argv.getEnable());
        model.setDistributed(argv.getDistributed());
        model.setNote(argv.getNote());
        model.setTaskArgv(argv.getTaskArgv());
        // 校验
        checkUnique(model);
        // 保存
        DB.update(model);
        // 更新动态任务
//        dynamicTask.updateTask(model);
    }

    private void checkUnique(SysTask model) {
        // 唯一性校验
        DBChecker<SysTask> checker =DBChecker.build(model, SysTask.class);
        // 名称唯一
        checker.uni(new String[]{"name", "applicationId"}, I18n.t("SysTask.name.unique", "同一应用下名称必须唯一"));
        // 执行校验
        checker.checkUnique();
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
            wrapper.appendSql(" and (t.app_id = ?)", argv.getAppId());
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

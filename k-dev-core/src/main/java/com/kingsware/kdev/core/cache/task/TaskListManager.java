package com.kingsware.kdev.core.cache.task;

import com.kingsware.kdev.core.model.SysTask;
import com.kingsware.kdev.core.orm.DB;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * 任务管理器
 *
 * @author chen peng
 * @version 1.0.0
 * @date 2022/3/21 10:54 上午
 */
@Slf4j
public class TaskListManager {
    private static TaskListManager instance;

    // 任务列表()
    private Map<String, SysTask> sysTaskMap = new HashMap<>();

    public static TaskListManager getInstance() {
        if (instance == null) {
            instance = new TaskListManager();
        }
        return instance;
    }

    private TaskListManager() {
    }

    /**
     * 增加任务
     * @param sysTasks  任务列表
     */
    public void addTask(List<SysTask> sysTasks) {
        this.sysTaskMap = sysTasks.stream().collect(Collectors.toMap(SysTask::getId, SysTask->SysTask));
    }

    public void addTask(SysTask task) {
        this.sysTaskMap.put(task.getId(), task);
    }

    /**
     * 获取任务
     * @param taskId 任务id
     * @return  返回任务
     */
    public SysTask getTask(String taskId) {
        // 先从缓存里取，如果缓存不存在，再读取数据库
        SysTask task = sysTaskMap.get(taskId);
        if (task == null) {
            task = DB.findById(SysTask.class, taskId);
            if (task == null) {
                log.warn("任务：{}，不存在，可能已被删除", taskId);
            }
            addTask(task);
        }
        return task;
    }

    /**
     * 获取所有的任务
     * @return  返回所有的启用状态的任务
     */
    public List<SysTask> getAllTask() {
        List<SysTask> tasks = new ArrayList<>();
        sysTaskMap.forEach(((k, sysTask) -> {
            if (sysTask.getEnable() == 1) {
                tasks.add(sysTask);
            }
        }));
        return tasks;
    }

}

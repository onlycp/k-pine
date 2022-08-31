//package com.kingsware.kdev.core.cache.task;
//
//import com.kingsware.kdev.core.model.SysTask;
//import com.kingsware.kdev.core.orm.DB;
//import lombok.extern.slf4j.Slf4j;
//
//import java.util.ArrayList;
//import java.util.HashMap;
//import java.util.List;
//import java.util.Map;
//import java.util.stream.Collectors;
//
///**
// * 任务管理器
// *
// * @author chen peng
// * @version 1.0.0
// * @date 2022/3/21 10:54 上午
// */
//@Slf4j
//public class TaskListManager {
//    private static TaskListManager instance;
//
//    // 任务列表()
//    private Map<String, SysTask> sysTaskMap = new HashMap<>();
//
//    public static TaskListManager getInstance() {
//        if (instance == null) {
//            instance = new TaskListManager();
//        }
//        return instance;
//    }
//
//    private TaskListManager() {
//    }
//
//    /**
//     * 加载所有的任务
//     */
//    public void loadAllTask() {
//        List<SysTask> tasks = DB.findList(SysTask.class, "select * from sys_task");
//        // 解锁任务
//         List<SysTask> lockedTasks = tasks.stream().filter(it -> it.getLockStatus() != null && it.getLockStatus() == 1).collect(Collectors.toList());
//        for (SysTask task: lockedTasks) {
//            Integer lockForMost = task.getLockForMost();
//            if (task.getLockForMost() == null) {
//                lockForMost = 60;
//            }
//            // 如果达到解锁标准了
//            if (task.getLockForTime() != null) {
//                if ((task.getLockForTime().getTime() + lockForMost*1000) < System.currentTimeMillis()) {
//                    DB.executeUpdateSql("update sys_task set lock_status=0 where id=?", task.getId());
//                    log.info("定时任务:{} 自动解锁", task.getName());
//                }
//            }
//        }
//        // 缓存任务
//        List<SysTask> enableTasks = tasks.stream().filter(it -> it.getEnable() != null && it.getEnable() == 1).collect(Collectors.toList());
//        addTask(enableTasks);
//    }
//    /**
//     * 增加任务
//     * @param sysTasks  任务列表
//     */
//    public void addTask(List<SysTask> sysTasks) {
//        this.sysTaskMap = sysTasks.stream().collect(Collectors.toMap(SysTask::getId, SysTask->SysTask));
//    }
//
//    public void addTask(SysTask task) {
//        this.sysTaskMap.put(task.getId(), task);
//    }
//
//    /**
//     * 获取任务
//     * @param taskId 任务id
//     * @return  返回任务
//     */
//    public SysTask getTask(String taskId) {
//        // 先从缓存里取，如果缓存不存在，再读取数据库
//        SysTask task = sysTaskMap.get(taskId);
//        if (task == null) {
//            task = DB.findById(SysTask.class, taskId);
//            if (task == null) {
//                log.warn("任务：{}，不存在，可能已被删除", taskId);
//            }
//            addTask(task);
//        }
//        return task;
//    }
//
//    /**
//     * 获取所有的任务
//     * @return  返回所有的启用状态的任务
//     */
//    public List<SysTask> getAllTask() {
//        List<SysTask> tasks = new ArrayList<>();
//        sysTaskMap.forEach(((k, sysTask) -> {
//            if (sysTask.getEnable() == 1) {
//                tasks.add(sysTask);
//            }
//        }));
//        return tasks;
//    }
//
//}

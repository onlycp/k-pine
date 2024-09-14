package com.kingsware.kdev.core.cron;

import com.kingsware.kdev.core.auth.AppAuthProperties;
import com.kingsware.kdev.core.auth.BaseUserInfo;
import com.kingsware.kdev.core.auth.TokenUtil;
import com.kingsware.kdev.core.cache.api.ApiResultCache;
import com.kingsware.kdev.core.cache.api.ApiResultCacheManager;
import com.kingsware.kdev.core.cache.instance.InstanceManager;
import com.kingsware.kdev.core.cache.session.SessionManager;
import com.kingsware.kdev.core.context.KClientContext;
import com.kingsware.kdev.core.context.SpringContext;
import com.kingsware.kdev.core.exception.UnauthorizedException;
import com.kingsware.kdev.core.kflow.KFlowConstant;
import com.kingsware.kdev.core.kflow.KFlowContext;
import com.kingsware.kdev.core.kflow.KdbFlowExecutor;
import com.kingsware.kdev.core.kflow.bean.ErrorResult;
import com.kingsware.kdev.core.kflow.bean.KdbFlowResult;
import com.kingsware.kdev.core.kflow.tcp.TcpWmMessage;
import com.kingsware.kdev.core.kmq.KmqMessageCenter;
import com.kingsware.kdev.core.kmq.websocket.MessageWebSocket;
import com.kingsware.kdev.core.kmq.websocket.WebsocketConstants;
import com.kingsware.kdev.core.kmq.websocket.WmMessage;
import com.kingsware.kdev.core.kmq.websocket.WmMessageArgv;
import com.kingsware.kdev.core.model.SysLogicFlow;
import com.kingsware.kdev.core.model.SysTask;
import com.kingsware.kdev.core.orm.DB;
import com.kingsware.kdev.core.orm.DBInitialize;
import com.kingsware.kdev.core.util.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.DependsOn;
import org.springframework.core.annotation.Order;
import org.springframework.scheduling.TriggerContext;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;
import org.springframework.scheduling.support.CronTrigger;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.sql.Timestamp;
import java.time.Duration;
import java.util.*;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

/**
 * 动态定时任务
 *
 * @author chen peng
 * @version 1.0.0
 * @date 2022/2/17 9:30 上午
 */
@Component
@Order(1)
@DependsOn("springContext")
@Slf4j
public class DynamicTask implements CommandLineRunner {

    @Value("${schedule.scan-package:com.kingsware.kdev}")
    private String scanPackage;
    /**
     * 是否将结果回写到数据库
     **/
    @Value("${schedule.result-to-db:true}")
    private boolean resultToDb;
    /**
     * 是否自动分布式
     **/
    @Value("${schedule.distributed-auto:true}")
    private boolean distributedAuto;
    /**
     * 是否运行分布式
     **/
    @Value("${schedule.distributed-run:true}")
    private boolean distributedRun;

    /**
     * 是否运行分布式
     **/
    @Value("${schedule.share-cron:true}")
    private boolean shareCron;

    /**
     * 是否异步执行
     **/
    @Value("${schedule.async-execute:true}")
    private boolean asyncExecute;

    /**
     * 是否异步执行
     **/
    @Value("${schedule.pool-size:50}")
    private int poolSize;


    private  ThreadPoolTaskScheduler threadPoolTaskScheduler;

    private  Map<String, ScheduledFutureHolder> scheduledFutureMap;

    private final CopyOnWriteArrayList<SysTask> sysTaskList = new CopyOnWriteArrayList<>();
    private final CopyOnWriteArrayList<SysTask> virtualTaskList = new CopyOnWriteArrayList<>();

    @Resource
    private MessageWebSocket messageWebSocket;

    /**
     * 线程池
     */
    private static final ExecutorService executorService = Executors.newCachedThreadPool();

    public DynamicTask() {

    }

    private void initThreadPool() {
        if(threadPoolTaskScheduler == null) {
            this.scheduledFutureMap = new HashMap<>(1);
            this.threadPoolTaskScheduler = new ThreadPoolTaskScheduler();
            this.threadPoolTaskScheduler.setPoolSize(50);
            this.threadPoolTaskScheduler.initialize();
        }
    }


    /**
     * 修复表达式
     * @param cron
     * @return
     */
    private static String fixedCron(String cron) {
        String trimCron = cron.trim();
        if (NumberUtils.isInteger(trimCron)) {
            return cron;
        }
        String tempCron = cron.trim();
        String[] cronArr = tempCron.split(" ");
        if (cronArr.length == 7) {
            tempCron = tempCron.substring(0,tempCron.lastIndexOf(" ")).trim();
        }
        return tempCron;
    }

//    public static void main(String[] argv) {
//        String c1 = "0 20 05 ? * 2 *";
//        System.out.println(fixedCron(c1));
//        String c2 = "0 20 05 ? * 2 ";
//        System.out.println(fixedCron(c2));
//        String c3 = "0 20 05 ? * 2";
//        System.out.println(fixedCron(c3));
//    }



    public void registerTask(String cron, String cronKey) {

        // 判断是否有
        ScheduledFutureHolder scheduledFutureHolder = scheduledFutureMap.get(cronKey);
        if (scheduledFutureHolder == null) {
            //将任务交给任务调度器执行
            ScheduledFuture<?> schedule = createTaskScheduler( cron, ()-> runTask(cronKey));
            //将任务包装成ScheduledFutureHolder
            scheduledFutureHolder = new ScheduledFutureHolder();
            scheduledFutureHolder.setScheduledFuture(schedule);
            scheduledFutureHolder.setCron(cron);
            scheduledFutureHolder.setCron(cronKey);
            scheduledFutureMap.put(cron, scheduledFutureHolder);
        }

    }

    /***
     * 创建定时执行器
     * @param cron
     * @param runnable
     * @return
     */
    public ScheduledFuture<?> createTaskScheduler(String cron, Runnable runnable) {
        ScheduledFuture<?> schedule = null;
        if (NumberUtils.isInteger(cron)) {
            schedule = threadPoolTaskScheduler.scheduleAtFixedRate(runnable, Duration.ofSeconds(Integer.parseInt(cron)));
        }
        else {
            schedule = threadPoolTaskScheduler.schedule(runnable, new CronTrigger(cron));
        }
        return schedule;
    }

    /**
     * 运行任务
     *
     * @param cronKey 任务便利店
     */
    private void runTask(String cronKey) {
        // 同时通过cron和任务id去查找
        List<SysTask> cronTasks = sysTaskList.stream().filter(it -> it.getEnable() == 1 && StringUtils.isNotEmpty(it.getCron())).filter(it -> fixedCron(it.getCron()).equals(cronKey) || cronKey.contains("@" + it.getId())).collect(Collectors.toList());
        ScheduledFutureHolder scheduledFutureHolder = scheduledFutureMap.get(cronKey);

        // 如果不存在任务，则自毁
        if (cronTasks.isEmpty()) {
            ScheduledFuture<?> scheduledFuture = scheduledFutureHolder.getScheduledFuture();
            scheduledFuture.cancel(true);
            scheduledFutureMap.remove(cronKey);
            log.debug("表达式调度器由于没有可执行的任务，已经进行自毁:{}", cronKey);
        }
        else {
            log.debug("表达式调度器:{} 开始执行任务， 任务数:{}", cronKey, cronTasks.size());
            // 如果是异步执行
            if(asyncExecute) {
                for (SysTask sysTask: cronTasks) {
                    executorService.submit(() -> {
                       toTask(sysTask);
                    });
                }
            }
            else {
                for (SysTask sysTask: cronTasks) {
                    toTask(sysTask);

                }
            }

        }

    }

    private void toTask(SysTask sysTask) {
        // 如果不是分布式任务，直接运行
        if (sysTask.getDistributed() != null && sysTask.getDistributed() == 0) {
            executeTask(sysTask);
            return;
        }
        if (distributedAuto) {
            AtomicInteger atomicInteger = new AtomicInteger(0);
            callTask(sysTask, atomicInteger, "");
        } else {
            if (distributedRun) {
                if (sysTask.getEnable() == 0) {
                    return;
                }
                executeTask(sysTask);
            }
        }
    }

    /**
     * 调用http接口
     *
     * @param sysTask 任务
     */
    public void callTask(SysTask sysTask, AtomicInteger atomicInteger, String excludeInstanceName) {
        if (atomicInteger.intValue() == 3) {
            log.warn("任务:{}执行失败次数为:{}, 调度器将终止任务执行", sysTask.getName(), atomicInteger.intValue());
        }
        // 取主实例作为调度器
//        SysInstance masterInstance = InstanceManager.getInstance().masterInstance();
        // 只有是调度器才执行
        if (InstanceManager.getInstance().isMaster()) {
            atomicInteger.incrementAndGet();
            // 随机取一个实例
//            SysInstance executeInstance = InstanceManager.getInstance().getToExecuteInstance(sysTask.getId(), excludeInstanceName);
            executeTask(sysTask);
//            // 如果是当前实例，直接调用即可，不通过http调用
//            if (executeInstance.instanceName().equalsIgnoreCase(masterInstance.instanceName())) {
////                log.info("本机触发定时任务，任务id:{}", sysTask.getId());
//
//            }
//            // 通过http调用
//            else {
//                boolean b = InstanceManager.getInstance().sendMessage(executeInstance, "task-execute", JsonUtil.toJson(sysTask));
//                if (!b) {
//                    callTask(sysTask, atomicInteger, excludeInstanceName);
//                }
//            }

        }
    }

    /**
     * 运行任务
     *
     * @param myTask
     */
    public void executeTask(SysTask myTask) {
        long t1 = System.currentTimeMillis();
        int executeStatus = 1;
        String errorMessage = "";
        try {
            // 如果是java类
            if (myTask.getTaskType() == 1) {
                runJavaTask(myTask);
            } else if (myTask.getTaskType() == 2) {
                runFlowTask(myTask);
            }
        } catch (CronException e) {
            if (e.getErrorCode() == 1 || e.getErrorCode() == 2) {
                executeStatus = 0;
                errorMessage = e.getMessage();
            }
        } catch (Exception e) {
            log.error("定时任务执行失败, 任务名: {}， 错误信息:{}", myTask.getName(), e.getMessage());
            executeStatus = 0;
            errorMessage = ExceptionUtils.getStackTrace(e);
        } finally {
            long t2 = System.currentTimeMillis();
            log.debug("任务执行完成，名称:{}, 执行结果:{}, 用时:{}, 信息:{}", myTask.getName(), executeStatus == 1 ? "成功" : "失败", (t2 - t1), errorMessage);
            if (resultToDb) {
                String sql = "update sys_task set last_execute_status=?, last_execute_take = ?, last_execute_msg = ?,  last_execute_time=?, next_inst=? where id=?";
                DB.executeUpdateSql(sql, executeStatus, (t2 - t1), errorMessage, DateUtils.formatDate(new Timestamp(t1), DateUtils.DATE_TIME), SystemUtil.getHost().instanceName(), myTask.getId());
            }

        }

    }

    /**
     * 执行java类任务
     *
     * @param sysTask 任务
     */
    private void runJavaTask(SysTask sysTask) throws Exception {
        try {
            KTask kTask = (KTask) Class.forName(sysTask.getClassName()).newInstance();
            kTask.execute();
        } catch (ClassNotFoundException e) {
            throw new CronException("调度Class不存在", 1);
        }


    }

    /**
     * 运行流程任务
     *
     * @param sysTask 流程
     */
    private void runFlowTask(SysTask sysTask) {


        // 先查找一下看流程是否存在
        SysLogicFlow logicFlow = DB.findOne(SysLogicFlow.class, "select in_argv, application_id, out_argv from sys_logic_flow where flow_id=?", sysTask.getTaskResourceId());
        String inArgv = "{}";
        String outArgv = "{}";
        if (logicFlow != null) {
            if (StringUtils.isNotEmpty(logicFlow.getInArgv())) {
                inArgv = logicFlow.getInArgv();
            }
            if (StringUtils.isNotEmpty(logicFlow.getOutArgv())) {
                outArgv = logicFlow.getOutArgv();
            }
        }
        KFlowContext context = KFlowContext.createBaseContext(inArgv, outArgv);
        Map<String, Object> params = new HashMap<>();
        if (StringUtils.isNotEmpty(sysTask.getTaskArgv())) {
            Map<String, Object> taskArgvMap = JsonUtil.toMap(sysTask.getTaskArgv());
            if (taskArgvMap != null) {
                params = taskArgvMap;
            }
        }
        params.put("_expireTime",  getNextTriggerTime(sysTask.getCron()));
        params.put("_appId", sysTask.getApplicationId());
        long t1 = System.currentTimeMillis();
        // 如果是虚拟任务，将改为同步任务
        if (sysTask.getName().startsWith("virtual-api-cache") && hasVirtualTask(sysTask.getId())) {
            context = JsonUtil.toBean(sysTask.getNote(), KFlowContext.class);
            params = JsonUtil.toMap(sysTask.getTaskArgv());
            long ts01 = System.currentTimeMillis();
            KdbFlowResult result = KdbFlowExecutor.getInstance().execute(sysTask.getTaskResourceId(), "", params, context, false, false);
            long ts02 = System.currentTimeMillis();
            if (Objects.equals(result.getType(), KFlowConstant.RESULT_JSON) && !(result.getData() instanceof ErrorResult)) {
                ApiResultCache apiResultCache = ApiResultCacheManager.getInstance().get(sysTask.getId());
                boolean changed = true;
                if (apiResultCache != null) {
                    changed = apiResultCache.update(result, (ts02 - ts01));
                }
                else {
                    apiResultCache = ApiResultCache.create(result,  (ts02 - ts01));
                    if (StringUtils.isNotEmpty(sysTask.getWhoCreated())) {
                        apiResultCache.getTokens().add(sysTask.getWhoCreated());
                    }
                    ApiResultCacheManager.getInstance().put(sysTask.getId(), apiResultCache, (long)( sysTask.getLockStatus() * 1000));
                }
                log.info("虚拟任务:{}, 查询用时:{}, 响应结果:{}", sysTask.getName(), (ts02 - ts01), StringUtils.retrench(JsonUtil.toJson(result.getData()), 200) );
                this.sendDataChange(sysTask, apiResultCache, changed, result.getData());

            }
        }
       else {
            KdbFlowResult kdbFlowResult = KdbFlowExecutor.getInstance().execute(sysTask.getTaskResourceId(), "", params, context, false, asyncExecute);
        }
        long t2 = System.currentTimeMillis();
            log.debug("流程任务完成：{}", sysTask.getName());
    }

    public void sendDataChange(SysTask task, ApiResultCache apiResultCache, boolean changed, Object data) {
        // 同时推送到websocket
        WmMessage toC = new WmMessage();
        toC.setTopic("api-data");
        Map<String, Object> body = new HashMap<>();
        body.put("md5", task.getId());
        body.put("changed", changed);
        body.put("data", data);
//        if (changed) {
//
//        }
        body.put("expire", (Integer.parseInt(task.getCron()) + 10) * 1000);
        toC.setBody(JsonUtil.toJson(body));
        Set<String> removeTokens = new HashSet<>();
        for (String token : apiResultCache.getTokens() ) {
            try {
                AppAuthProperties appAuthProperties = SpringContext.getBean(AppAuthProperties.class);
                TokenUtil.getUserInfoByToken(token, appAuthProperties.getTokenSecret(), appAuthProperties.getIss(),
                        KClientContext.getContext().getIp(), appAuthProperties.getTokenExpireMinutes(), appAuthProperties.getMockSessionExpireMinutes());
                if (messageWebSocket.hasSessionByToken(token)) {
                    WmMessageArgv wmMessageArgv = new WmMessageArgv();
                    wmMessageArgv.setMessage(JsonUtil.toJson(toC));
                    wmMessageArgv.setToken(token);
                    KmqMessageCenter.getInstance().produce(WebsocketConstants.MQ_TO_WEBSOCKET, JsonUtil.toJson(wmMessageArgv));
                }
            }
            catch (UnauthorizedException e) {
                this.sendRemove(token, task.getId());
                removeTokens.add(token);
            }
        }
        apiResultCache.getTokens().removeAll(removeTokens);
    }

    /**
     * 向客户端发送数据移除通知
     * 遍历给定的API结果缓存中的所有token，检查每个token是否在WebSocket会话中存在
     * 如果会话存在，则调用sendRemove方法向客户端发送移除消息
     *
     * @param task         系统任务对象，包含任务ID等信息
     * @param apiResultCache   API结果缓存对象，包含要通知的客户端的token
     */
    public void sendDataRemove(SysTask task, ApiResultCache apiResultCache) {
        for (String token : apiResultCache.getTokens()) {
            if (messageWebSocket.hasSessionByToken(token)) {
                this.sendRemove(token, task.getId());
            }
        }
    }

    /**
     * 准备并发送数据移除消息给特定客户端
     * 创建一个消息，设置其主题为"api-data-remove"，并在消息体中包含要移除的任务的MD5值
     * 然后将此消息转换为JSON格式，并通过消息队列发送给对应的客户端
     *
     * @param token    客户端的唯一标识符，用于定位WebSocket会话
     * @param taskId   要移除的任务的ID
     */
    public void sendRemove(String token, String taskId) {
        WmMessage toC = new WmMessage();
        toC.setTopic("api-data-remove");
        Map<String, Object> body = new HashMap<>();
        body.put("md5", taskId);
        toC.setBody(JsonUtil.toJson(body));

        WmMessageArgv wmMessageArgv = new WmMessageArgv();
        wmMessageArgv.setMessage(JsonUtil.toJson(toC));
        wmMessageArgv.setToken(token);
        KmqMessageCenter.getInstance().produce(WebsocketConstants.MQ_TO_WEBSOCKET, JsonUtil.toJson(wmMessageArgv));
    }

    private long getNextTriggerTime(String cron) {
        if (NumberUtils.isInteger(cron)) {
            return System.currentTimeMillis() + Long.parseLong(cron)*1000;
        }
        else {
            CronTrigger cronTrigger = new CronTrigger(cron); // 设置与任务的调度时间表达式一致的CronTrigger
            Date nextExecutionTime = cronTrigger.nextExecutionTime(new TriggerContext() {
                @Override
                public Date lastScheduledExecutionTime() {
                    return null; // 返回上一次任务调度的时间，如果没有则返回null
                }

                @Override
                public Date lastActualExecutionTime() {
                    return null; // 返回上一次任务实际执行的时间，如果没有则返回null
                }

                @Override
                public Date lastCompletionTime() {
                    return null; // 返回上一次任务完成的时间，如果没有则返回null
                }
            });

            assert nextExecutionTime != null;
            ScheduledFuture<?> scheduledFuture = threadPoolTaskScheduler.schedule(() -> {}, nextExecutionTime);
            long delay = scheduledFuture.getDelay(TimeUnit.MILLISECONDS);
            scheduledFuture.cancel(true);
            return System.currentTimeMillis() + delay;
        }
    }

    /**
     * 扫描Class类
     */
    private void scanJavaClassTask(String scanPackage) {
        // 扫描所有的定时器类
        List<Class<?>> classList = ClassUtils.getClassesByParentClass(scanPackage, KTask.class);
        for (Class<?> tClass : classList) {
            // 生成实例
            try {
                KTask task = (KTask) tClass.newInstance();
                // 查找平台已经是否存在此任务
                SysTask sysTask = DB.findOne(SysTask.class, "select * from sys_task where task_type=1 and class_name=?", tClass.getName());
                // 如果已存在就不处理
                if (sysTask == null) {
                    sysTask = new SysTask();
                    sysTask.setName(task.name());
                    sysTask.setTaskType(1);
                    sysTask.setCron(task.cron());
                    sysTask.setEnable(1);
                    sysTask.setDistributed(0);
                    sysTask.setClassName(tClass.getName());
                    sysTask.setLockForLeast(1);
                    sysTask.setLockForMost(30);
                    sysTask.setLastExecuteStatus(0);
                    sysTask.setLastExecuteTake(0L);
                    sysTask.setNote(task.note());
                    // 保存
                    DB.save(sysTask);
                    log.debug("发现任务，任务名称:{}, cron:{}, Class: {}", sysTask.getName(), sysTask.getCron(), sysTask.getClassName());
                }
                else {
//                    sysTask.setName(task.name());
                    sysTask.setNote(task.note());
                    DB.update(sysTask);
                    log.debug("发现任务，更新任务:{}, cron:{}, Class: {}", sysTask.getName(), sysTask.getCron(), sysTask.getClassName());

                }
                if (task instanceof KRunner) {
                    ((KRunner) task).runNow();
//                    log.info("定时任务启动时即运行:{}", task.name());
                }
            } catch (Exception e) {
                log.error("定时类扫描初始化失败:{}", e.getMessage());
            }
        }
    }


    @Override
    public void run(String... args) throws Exception {
//        if (1 ==1) {
//            return;
//        }
        initThreadPool();
        scanJavaClassTask(scanPackage);
        threadPoolTaskScheduler.schedule(() -> {

            try {
                if (DBInitialize.initCompleted) {
                    List<SysTask> tasks = DB.findList(SysTask.class, "select * from sys_task where enable=1 order by when_created asc");
                    // 移除超时的虚拟任务
                    List<SysTask> expireTasks = new ArrayList<>();
                    int virtualTaskKeepaliveTime = SpringContext.getInt("app.schedule.virtual-task-keepalive-time", 1);
                    for (SysTask task: virtualTaskList) {
                        if ((System.currentTimeMillis() - task.getWhenModified().getTime()) > (long) virtualTaskKeepaliveTime * 1000*60 ) {
                            expireTasks.add(task);
                            ApiResultCache apiResultCache = ApiResultCacheManager.getInstance().get(task.getId());
                            if (apiResultCache != null) {
                                this.sendDataRemove(task, apiResultCache);
                            }
                            ApiResultCacheManager.getInstance().remove(task.getId());
                            log.info("移除超时虚拟任务:{}", task.getName());
                        }
                    }
                    virtualTaskList.removeAll(expireTasks);
                    // 加入虚拟任务
                    tasks.addAll(virtualTaskList);
//                    List<SysTask> apiCacheTasks =
                    log.debug("线程池数量：{}，当前活动：{}， 任务数:{}", threadPoolTaskScheduler.getPoolSize(),  threadPoolTaskScheduler.getActiveCount(),  tasks.size());
                    if (sysTaskList.isEmpty()) {
                        sysTaskList.addAll(tasks);
                    }
                    else {
                        // 删除不存在的任务
                        List<SysTask> removeTasks = new ArrayList<>();
                        sysTaskList.forEach(it -> {
                            Optional<SysTask> optional = tasks.stream().filter(task -> task.getId().equals(it.getId())).findFirst();
                            // 如果找到，就放到替换列表中
                            if (optional.isPresent()) {
                                int index = sysTaskList.indexOf(it);
                                sysTaskList.set(index, optional.get());
                            }
                            else {
                                removeTasks.add(it);
                            }
                        });
                        // 增加新的
                        for (SysTask task: tasks) {
                            if (!sysTaskList.contains(task)) {
                                sysTaskList.add(task);
                            }
                        }
                        // 移除不存在的
                        sysTaskList.removeAll(removeTasks);
                    }
                    // 处理一下表达式
                    for (SysTask task: tasks) {
                        try {

                            String cron = fixedCron(task.getCron());
                            // 判断是否共享表达式
                            String cronKey = shareCron ? cron: task.getCron()+ "@" + task.getId();
                            if (!scheduledFutureMap.containsKey(cron)) {
                                if (task instanceof KRunner) {
                                    ((KRunner)task).runNow();
                                }
                            }

                            task.setCron(cron);
                            registerTask(cron, cronKey);

                        }
                        catch (IllegalArgumentException e) {
                            log.warn("任务表达式注册不成功, 任务名称:{}, 表达式{}", task.getName(), task.getCron());
                            task.setEnable(0);
                            task.setLastExecuteMsg("cron表达式不合法");
                            DB.update(task);
                        }
                        catch (Exception e) {
                            log.warn("任务注册失败：{}， {}", task.getName(), task.getCron());

                        }
                    }
                }
                else {
                    log.info("当前系统未初始化完成....");
                }

            }
            catch (Exception e) {
                log.error("error", e);
            }

            // 解锁任务
            // unlockTask(tasks);


        }, new CronTrigger("0/10 * * * * ?"));

    }

    /**
     * 添加一个虚拟任务到虚拟任务列表中。
     * @param task 要添加的任务，不应为null。
     */
    public void addVirtualTask(SysTask task) {
        virtualTaskList.add(task);
    }

    /**
     * 从虚拟任务列表中移除指定ID的任务。
     * @param taskId 要移除的任务的ID，不应为null或空字符串。
     */
    public void removeVirtualTask(String taskId) {
        // 使用lambda表达式遍历虚拟任务列表，移除与指定ID匹配的任务
        virtualTaskList.removeIf(task -> task.getId().equals(taskId));
    }

    /**
     * 根据任务ID获取虚拟任务。
     *
     * @param taskId 任务的唯一标识符。
     * @return 如果找到匹配的虚拟任务，则返回该任务；如果没有找到，则返回null。
     */
    public SysTask getVirtualTask(String taskId) {
        // 通过流遍历虚拟任务列表，筛选出与给定任务ID匹配的任务，如果存在则返回，否则返回null
        return virtualTaskList.stream().filter(task -> task.getId().equals(taskId)).findFirst().orElse(null);
    }

    /**
     * 检查是否存在指定的虚拟任务。
     *
     * @param taskId 任务的唯一标识符。
     * @return 如果存在匹配的虚拟任务，则返回true；否则返回false。
     */
    public boolean hasVirtualTask(String taskId) {
        // 通过流遍历虚拟任务列表，检查是否存在与给定任务ID匹配的任务
        return virtualTaskList.stream().anyMatch(task -> task.getId().equals(taskId));
    }

    /**
     * 为指定的虚拟任务更新修改时间。
     *
     * @param taskId 任务的唯一标识符。
     * 更新任务的修改时间为当前时间。
     */
    public void virtualHeart(String taskId) {
        // 获取具有指定ID的虚拟任务，如果存在，则更新其修改时间
        SysTask task = getVirtualTask(taskId);
        if (task != null) {
            log.info("虚拟任务心跳：{}", task.getName());
            task.setWhenModified(new Timestamp(System.currentTimeMillis()));
        }
    }
}

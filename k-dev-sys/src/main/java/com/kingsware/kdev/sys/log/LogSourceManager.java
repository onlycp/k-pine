package com.kingsware.kdev.sys.log;

import com.kingsware.kdev.core.util.DateUtils;
import com.kingsware.kdev.sys.argv.LogTailArgv;
import com.kingsware.kdev.sys.argv.StreamingLog;
import lombok.Getter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.PipedOutputStream;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class LogSourceManager {

    private static final Logger log = LoggerFactory.getLogger(LogSourceManager.class);
    // 存储 LogSource 的映射表，使用线程安全的 ConcurrentHashMap
    private final ConcurrentHashMap<String, LogSourceEntry> logSources = new ConcurrentHashMap<>();
    // 定时任务线程池，用于定期检测心跳
    private final ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);

    // 定义心跳超时时间（单位：秒）
    private static final long HEARTBEAT_TIMEOUT = 1;

    /**
     * 私有构造方法，防止直接实例化
     */
    private LogSourceManager() {
        // 定期检查心跳
        scheduler.scheduleAtFixedRate(this::removeInactiveLogSources, HEARTBEAT_TIMEOUT, HEARTBEAT_TIMEOUT, TimeUnit.SECONDS);
    }

    /**
     * 获取 LogSourceManager 的单实例
     *
     * @return 单实例
     */
    public static LogSourceManager getInstance() {
        return SingletonHolder.INSTANCE;
    }


    /**
     * 获取日志
     *
     * @param argv
     */
    public void registerLogSource(LogTailArgv argv, PipedOutputStream out) {
        // 由于前端不支持主动断开，在这里，如果存在，则移除相关的流程

        StreamingLog streamingLog = new StreamingLog(out, argv.getClientId(), argv.getKeyword());
        String id = argv.getApp() + "_" + argv.getLevel();
        LogSourceEntry LogSourceEntry = logSources.get(id);
       if (LogSourceEntry == null) {
           try {
               if ("api".equalsIgnoreCase(argv.getApp())) {
                   String localFilePath = "logs/service.log";
                   if ("error".equalsIgnoreCase(argv.getLevel())) {
                       localFilePath = "logs/error.log";
                   }
                   LogSource logSource = new LocalFileLogSource(argv.getApp(), argv.getLevel(), localFilePath);
                   LogSourceEntry = new LogSourceEntry(logSource);
                   logSources.put(id, LogSourceEntry);
               }
               else if ("faas".equalsIgnoreCase(argv.getApp())) {
                   String localFilePath = "logs/info/kfaas_info.log";
                   if ("error".equalsIgnoreCase(argv.getLevel())) {
                       localFilePath = "logs/error/kfaas_error.log";
                   }
                   else if ("KLog".equalsIgnoreCase(argv.getLevel())) {
                       localFilePath = "logs/klog/info/klog_info.log";
                   }
                   LogSource logSource = new FaasFileLogSource(argv.getApp(), argv.getLevel(), localFilePath);
                   LogSourceEntry = new LogSourceEntry(logSource);
                   logSources.put(id, LogSourceEntry);
               }
           }
           catch (Exception e) {
               throw e;
           }
       }
       this.removeByClientId(argv.getClientId());
        LogSourceEntry.getLogSource().addStreamingLog(streamingLog);

    }

    public void removeByClientId(String clientId) {
        logSources.forEach((id, entry) -> {
            entry.getLogSource().removeStreamByClientId(clientId);
        });
    }



    /**
     * 停止管理器，释放资源
     */
    public void shutdown() {
        scheduler.shutdown();
    }

    /**
     * 定期检测并移除超时的 LogSource
     */
    private void removeInactiveLogSources() {
//        long currentTime = System.currentTimeMillis();
//        log.info("log-" + DateUtils.getNow());
//        logSources.forEach((id, entry) -> {
//            if (currentTime - entry.getLastHeartbeat() > HEARTBEAT_TIMEOUT * 1000) {
//                logSources.remove(id);
//                System.out.println("Removed inactive LogSource: " + id);
//            }
//        });
    }

    /**
     * 静态内部类，用于持有单实例
     */
    private static class SingletonHolder {
        private static final LogSourceManager INSTANCE = new LogSourceManager();
    }

    /**
     * 内部类：用于包装 LogSource，记录其心跳信息
     */
    @Getter
    private static class LogSourceEntry {

        private final LogSource logSource;
        private volatile long lastHeartbeat;

        public LogSourceEntry(LogSource logSource) {
            this.logSource = logSource;
            this.lastHeartbeat = System.currentTimeMillis();
        }

        public void refreshHeartbeat() {
            this.lastHeartbeat = System.currentTimeMillis();
        }
    }
}

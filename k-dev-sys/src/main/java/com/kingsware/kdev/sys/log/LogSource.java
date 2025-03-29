package com.kingsware.kdev.sys.log;

import com.kingsware.kdev.core.util.StringUtils;
import com.kingsware.kdev.sys.argv.StreamingLog;
import com.kingsware.kdev.sys.ret.LogTailRet;
import lombok.Getter;

import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicLong;


/**
 * 日志源类，用于定义日志的来源和读取方式
 */
public abstract class LogSource {

    // 应用程序名称
    protected String app;
    // 日志级别
    protected String level;
    // 日志偏移量，用于记录读取日志的位置
    protected AtomicBoolean isRunning = new AtomicBoolean(false);
    @Getter
    protected AtomicLong offset = new AtomicLong(0);
    // 存储读取的日志行
    @Getter
    protected LinkedBlockingQueue<LogLine> lines = new LinkedBlockingQueue<>();
    @Getter
    protected Set<StreamingLog> streamingLogs = new HashSet<>();


    /**
     * 构造方法，初始化日志源
     *
     * @param app  应用程序名称
     * @param level 日志级别
     */
    public LogSource(String app, String level) {
        this.app = app;
        this.level = level;
    }

    public void addStreamingLog(StreamingLog streamingLog) {
        streamingLogs.removeIf(log -> log.getClientId().equals(streamingLog.getClientId()));
        streamingLogs.add(streamingLog);
    }


    public void removeStreamByClientId(String clientId) {
        Set<StreamingLog> toRemove = new HashSet<>();
        for (StreamingLog log : streamingLogs) {
            if (log.getClientId().equals(clientId)) {
                try {
                    log.getPipedOutputStream().close();
                    toRemove.add(log);

                }
                catch (Exception e) {

                }

            }
        }
        streamingLogs.removeAll(toRemove);
    }

    /**
     * 发布日志消息，将消息发送到所有关联的流式日志输出流中
     */
    public void publish(String message) {
        Set<StreamingLog> toRemove = new HashSet<>();
        System.out.println("publish: " + message);
        for (StreamingLog log : streamingLogs) {
            try {
                String myMessage = message;
                if (!message.endsWith("\n")) {
                    myMessage += "\n";
                }
                if (StringUtils.isNotEmpty(log.getKeyword())) {
                    if (myMessage.contains(log.getKeyword())) {
                        log.getPipedOutputStream().write((myMessage).getBytes());
                    }
                }
                else {
                    log.getPipedOutputStream().write((myMessage).getBytes());
                }
            }
            catch (Exception e) {
                toRemove.add(log);
            }
        }
        streamingLogs.removeAll(toRemove);

    }


    /**
     * 获取日志源的唯一标识符，用于在日志源管理器中定位日志源
     * @return
     */
    public String getId() {
        return app + "-" + level;
    }

    /**
     * 抽象方法，用于实现具体的日志尾部跟踪逻辑
     * 不同的日志源可能有不同的跟踪方式，因此这个方法需要由子类实现
     */
    public abstract void tail();

    public void close() {
        isRunning.set(false);
    }


}

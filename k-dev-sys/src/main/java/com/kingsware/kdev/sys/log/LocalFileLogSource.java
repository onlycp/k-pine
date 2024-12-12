package com.kingsware.kdev.sys.log;

import org.apache.commons.io.input.Tailer;
import org.apache.commons.io.input.TailerListener;
import org.apache.commons.io.input.TailerListenerAdapter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;

public class LocalFileLogSource extends LogSource {

    private static final Logger log = LoggerFactory.getLogger(LocalFileLogSource.class);

    private Tailer tailer;
    /**
     * 日志文件路径
     */
    private String path;
    /**
     * 构造方法，初始化日志源
     *
     * @param app   应用程序名称
     * @param level 日志级别
     */
    public LocalFileLogSource(String app, String level, String path) {
        super(app, level);
        this.path = path;
        this.tail();

    }

    @Override
    public void tail() {
        this.isRunning.set(true);
        // 创建一个监听器
        TailerListener listener = new TailerListenerAdapter() {
            @Override
            public void handle(String line) {
                // 当有新的一行追加时触发
                publish(line);
            }

            @Override
            public void handle(Exception ex) {
                log.error("Error tailing file: " + ex.getMessage(), ex);
                // 发生异常时触发
            }
        };
        tailer = new Tailer(new File(path), listener, 1000, true);
        // 使用单独的线程运行 Tailer
        Thread thread = new Thread(tailer);
        thread.setDaemon(true); // 设置为守护线程
        thread.start();

        // 添加一个关闭钩子，确保退出时停止 Tailer
        Runtime.getRuntime().addShutdownHook(new Thread(tailer::stop));

    }
}

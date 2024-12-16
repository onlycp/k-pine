package com.kingsware.kdev.sys.log;

import com.kingsware.kdev.core.util.ThreadUtils;
import com.kingsware.kdev.sys.bean.LogReadResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

public class LocalFileLogSource extends LogSource {

    private static final Logger log = LoggerFactory.getLogger(LocalFileLogSource.class);

    private Thread tailer;
    private Long lastOffset = -1L;
    private LogFileReader reader;
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


    /**
     * 获取日志最后索引
     * @return
     */
    public Long getLastIndex() {
      return reader.getIndexSize();
    }


    /**
     * 读取日志
     * @param index
     * @return
     */
    public LogReadResult read(Integer index) {
        try {
            LogReadResult lr =  reader.readLogEntries(index, 100);
//            System.out.println("read:" + lr.getEnd() + ",tt:" + lr.getTotal());
            return lr;
        }
        catch (IOException e) {
            log.info("读取日志失败", e);
            throw new RuntimeException(e);
        }
    }

    @Override
    public void close() {
        if (this.reader != null) {
            reader.stop();
        }
        super.close();
    }

    @Override
    public void tail() {
        this.isRunning.set(true);
        this.reader = new LogFileReader(new File(path), StandardCharsets.UTF_8);
        new Thread(reader).start();
        tailer = new Thread(() -> {
            while (this.isRunning.get()) {
                try {
                    if (lastOffset == -1L) {
                        lastOffset = getLastIndex();
                    }
                    LogReadResult logReadResult = read(lastOffset.intValue());
                    for (String line : logReadResult.getList()) {
                        publish(line);
                    }
                    lastOffset = logReadResult.getEnd()+1;
                }
                catch (Exception ignored) {
                    ignored.printStackTrace();;
                }
                finally {
                    ThreadUtils.sleep(500);
                }
            }


        });
        tailer.setDaemon(true);
        tailer.start();
        // 添加一个关闭钩子，确保退出时停止 Tailer
        Runtime.getRuntime().addShutdownHook(new Thread(tailer::stop));

    }
}

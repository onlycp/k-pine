package com.kingsware.kdev.sys.log;

import com.kingsware.kdev.core.exception.BusinessException;
import com.kingsware.kdev.core.orm.DB;
import com.kingsware.kdev.core.orm.kdb.KdbRet;
import com.kingsware.kdev.core.util.JsonUtil;
import com.kingsware.kdev.core.util.ThreadUtils;
import com.kingsware.kdev.sys.bean.LogReadResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;

public class FaasFileLogSource extends LogSource {

    private static final Logger log = LoggerFactory.getLogger(FaasFileLogSource.class);
    private Thread tailer;
    private Long lastOffset = -1L;
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
    public FaasFileLogSource(String app, String level, String path) {
        super(app, level);
        this.path = path;
        this.tail();

    }


    /**
     * 获取日志最后索引
     * @return
     */
    public Long getLastIndex() {
        Map<String, Object> variables = new HashMap<>();
        variables.put("path", path);
        // 定义q语言脚本，调用git.init函数来初始化仓库
        String script = "const res = kutils.getLogLastIndex(context.get('path'))\n" +
                "res.data";
        // 调用数据库API执行脚本，实现仓库初始化
        KdbRet<String> ret = DB.kdbApi().executeScript(script, variables);
        // 如果执行失败，抛出业务异常
        if (ret.getErrorCode() != 0) {
            throw BusinessException.serviceThrow(ret.getMessage());
        }
        String responseBody = ret.getResponseBody();
        return Long.valueOf(responseBody);
    }

    /**
     * 读取日志
     * @param index
     * @return
     */
    public LogReadResult read(Integer index) {
        Map<String, Object> variables = new HashMap<>();
        variables.put("path", path);
        variables.put("startIndex", index);
        // 定义q语言脚本，调用git.init函数来初始化仓库
        String script = "const res = kutils.logTail(context.get('path'),context.get('startIndex'))\n" +
                "res.data";
        // 调用数据库API执行脚本，实现仓库初始化
        KdbRet<String> ret = DB.kdbApi().executeScript(script, variables);
        // 如果执行失败，抛出业务异常
        if (ret.getErrorCode() != 0) {
            throw BusinessException.serviceThrow(ret.getMessage());
        }
        String responseBody = ret.getResponseBody();
        return JsonUtil.toBean(responseBody, LogReadResult.class);
    }

    @Override
    public void tail() {
        this.isRunning.set(true);
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
                    lastOffset = logReadResult.getEnd() + 1;
                }
                catch (Exception ignored) {

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

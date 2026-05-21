package com.kingsware.kdev.core.exception;

import com.kingsware.kdev.core.bean.ExceptionLog;
import com.kingsware.kdev.core.bean.Option;
import com.kingsware.kdev.core.util.DateUtils;
import com.kingsware.kdev.core.util.FileUtils;
import com.kingsware.kdev.core.util.JsonUtil;
import com.kingsware.kdev.core.util.PathSecurityUtils;
import lombok.extern.slf4j.Slf4j;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.regex.Pattern;

/**
 * @author chenp
 * @date 2024/4/16
 */
@Slf4j
public class ExceptionLogManager {
    private static ExceptionLogManager instance = new ExceptionLogManager();
    private static final Pattern DATE_PATTERN = Pattern.compile("^\\d{8}$");
    private static final Pattern ID_PATTERN = Pattern.compile("^[A-Za-z0-9._-]{1,128}$");

    private String today;

    public static ExceptionLogManager getInstance() {
        if (instance == null) {
            instance = new ExceptionLogManager();
        }
        return instance;
    }


    private ExceptionLogManager() {
        today = DateUtils.formatDate(new Date(), "yyyyMMdd");
    }


    /**
     * 将异常日志写入到文件中。
     *
     * @param exceptionLog 异常日志对象，包含异常的详细信息和一个唯一的异常ID。
     *                     异常ID格式为日期_id，其中日期表示异常发生的时间，id为异常的唯一标识符。
     */
    public void write(ExceptionLog exceptionLog) {
        // 解析异常ID为日期和ID两部分
        String exceptionId = exceptionLog.getId();
        String[] arr = exceptionId.split("_");

        // 提取日期和ID
        String date = arr[0];
        String id = arr[1];
        if (!date.equals(today)) {
            // 查找日志文件目录
            Path dir = Paths.get("logs/exception/");
            File currentDir = new File("logs/exception/" + date);
            for(File file: dir.toFile().listFiles()) {
                if (!file.getAbsolutePath().equalsIgnoreCase(currentDir.getAbsolutePath())) {
                    FileUtils.deleteFileOrDirectory(file.getAbsolutePath());
                    log.info("删除异常日志目录：{}", file.getAbsolutePath());

                }
            }
            today = date;
        }

        // 创建日志文件存储目录
        Path path = Paths.get("logs/exception/" + date +"/");
        if (Files.notExists(path)) {
            path.toFile().mkdirs();  // 如果目录不存在，则创建目录
        }
        try {
            // 根据ID创建或获取日志文件
            File file = new File(path.toFile(), id);
            if (!file.exists()) {
                // 将异常日志对象转换为JSON字符串
                String data = JsonUtil.toJson(exceptionLog);

                // 对JSON字符串进行压缩
                byte[] compressData = JsonUtil.compressJSON(data);

                if (compressData != null) {
                    // 将压缩后的数据写入到文件中
                    Files.write(file.toPath(), compressData);
                }
            }
        }
        catch (Exception e) {
            // 如果在写入日志过程中出现异常，则记录警告日志
            log.warn("error", e);
        }

    }

    /**
     * 从文件中读取异常日志。
     *
     * @param exceptionId 要读取的异常日志的ID，格式为日期_id。
     *                    其中日期表示异常发生的时间，id为异常的唯一标识符。
     * @return 读取到的异常日志对象，如果读取失败或文件不存在则返回null。
     */
    public ExceptionLog read(String exceptionId) {
        // 提取日期和ID
        String[] arr = exceptionId.split("_");
        if (arr.length != 2) {
            return null;
        }
        String date = arr[0];
        String id = arr[1];
        if (!DATE_PATTERN.matcher(date).matches() || !ID_PATTERN.matcher(id).matches()) {
            return null;
        }

        // 修复：exception不会自动创建导致多环境异常日志查询不到问题
        new File("logs/exception").mkdirs();
        File logFile;
        try {
            File logRoot = PathSecurityUtils.canonicalFile("logs/exception", "logs.exception");
            File dateDir = PathSecurityUtils.resolveUnderRoot(logRoot, date, "logs.exception.date");
            logFile = PathSecurityUtils.resolveUnderRoot(dateDir, id, "logs.exception.file");
        } catch (IOException e) {
            log.warn("invalid exception log path: {}", exceptionId, e);
            return null;
        }

        // 检查文件是否存在
        if (!logFile.exists()) {
            return null;  // 文件不存在，返回null
        }

        try {
            // 读取文件中的压缩数据
            byte[] compressData = Files.readAllBytes(logFile.toPath());

            // 反压缩JSON字符串
            String jsonData = JsonUtil.decompressJSON(compressData);

            // 将JSON字符串反序列化为异常日志对象
            ExceptionLog exceptionLog = JsonUtil.toBean(jsonData, ExceptionLog.class);

            return exceptionLog;  // 返回读取到的异常日志对象

        } catch (IOException e) {
            log.warn("Error occurred while reading exception log with ID: {}", exceptionId, e);
            return null;  // 读取或反序列化过程中出现异常，返回null
        }
    }


    /**
     * 获取异常日志列表
     * @return
     */
    public List<Option> getExceptionList() {
        List<Option> list = new ArrayList<>();
        Path path = Paths.get("logs/exception/");
        File currentDir = new File("logs/exception/");
        for (File file : currentDir.listFiles()) {
            if (file.isDirectory()) {
                String date = file.getName();
                File[] files = file.listFiles();
                for (File f : files) {
                    String id = f.getName();
                    String exceptionId = date + "_" + id;
                    list.add(new Option(exceptionId, exceptionId));
                }
            }
        }
        return list;
    }


}

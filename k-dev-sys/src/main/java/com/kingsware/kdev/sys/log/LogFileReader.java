package com.kingsware.kdev.sys.log;

import com.kingsware.kdev.core.util.ThreadUtils;
import com.kingsware.kdev.sys.bean.LogReadResult;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

@Slf4j
public class LogFileReader implements Runnable {

    private List<IndexEntry> indexEntries = new CopyOnWriteArrayList<>();
    private final File file;        // 日志文件
    private final Charset charset;  // 文件编码
    private long position = 0;
    private long lineNo = 0;
    private long startPosition = 0;
    private boolean isRunning = false;
    private long OFFSET_LINE_NO = 0;

    /**
     * 构造函数，初始化日志文件读取器并构建索引。
     *
     * @param file    要读取的日志文件
     * @param charset 文件的字符编码
     * @throws IOException 如果读取文件时发生I/O错误
     */
    public LogFileReader(File file, Charset charset) {
        this.file = file;
        this.charset = charset;
        this.isRunning = true;
    }

    /**
     * 重置索引到初始状态
     * 此方法清除所有索引项，并将位置和行号重置为初始值
     */
    public void resetIndex() {
        this.indexEntries.clear();
        this.position = 0;
        this.lineNo = 0;
        OFFSET_LINE_NO = 0;
        this.startPosition = 0;
    }

    public long getIndexSize() {
        return indexEntries.size() + OFFSET_LINE_NO;
    }

    /**
     * 构建日志条目索引（每个条目的起始文件偏移量）。
     *
     * @return 日志条目起始偏移量列表
     * @throws IOException 如果读取文件时发生I/O错误
     */
    private void buildLogEntryIndex() throws Exception {
        try (FileInputStream fis = new FileInputStream(file);
             FileChannel fileChannel = fis.getChannel()) {

            long fileSize = fileChannel.size();
//            System.out.println("fileSize:"+fileSize + ", pos:" + position);
            if (fileSize < position) {
                this.resetIndex();
            }
            // 将整个文件映射到内存中
            MappedByteBuffer buffer = fileChannel.map(FileChannel.MapMode.READ_ONLY, position, fileSize - position);
            int initPosition = (int) position;
            boolean isLineEnd = false;
            StringBuilder sb = new StringBuilder();
            while (position < fileSize) {

                byte b = buffer.get((int) (position -initPosition));
                sb.append((char) b);
                if (b == '\n' ) {
                    isLineEnd = true;
                    lineNo++;
                }
                else {
                   isLineEnd = false;
                }
                if (isLineEnd && position + 1 >= fileSize) {
                    if (indexEntries.size() > 2000) {
                        // 移除前面1000条记录
                        indexEntries = indexEntries.subList(1000, indexEntries.size());
                        OFFSET_LINE_NO += 1000;
                    }
                    IndexEntry indexEntry = new IndexEntry();
                    indexEntry.setBeginPosition(startPosition);
                    indexEntry.setEndPosition(position-1);
                    indexEntry.setEndLine(lineNo);
                    indexEntries.add(indexEntry);
                    startPosition = position;
                }
                position++;
            }
        }
    }

    /**
     * 按指定日志条目号读取多个日志条目。
     *
     * @param startEntryNumber 起始日志条目号（从1开始）
     * @param count       最大读取的日志条目数
     * @return LogReadResult 包含日志条目列表和最后读取的条目号
     * @throws IOException 如果读取文件时发生I/O错误
     */
    public LogReadResult readLogEntries(long startEntryNumber, long count) throws IOException {
        long totalSize = getIndexSize();
        long iStartNumber = startEntryNumber - OFFSET_LINE_NO;

        LogReadResult result = new LogReadResult();
        result.setTotal(indexEntries.size() + OFFSET_LINE_NO);
        result.setStart(startEntryNumber);

//        if (startEntryNumber < 0) {
//            throw new IllegalArgumentException("起始日志条目号必须大于0");
//        }
//        if (count < 1) {
//            throw new IllegalArgumentException("最大读取日志条目数必须大于0");
//        }
        long endEntryNumber = Math.min(iStartNumber + count-1 , indexEntries.size() - 1 );
        List<String> logEntries = new ArrayList<>();

        try (FileInputStream fis = new FileInputStream(file);
             FileChannel fileChannel = fis.getChannel()) {

            for (long index = iStartNumber; index <= endEntryNumber; index++) {
                long startOffset = indexEntries.get((int)index).getBeginPosition();
                long endOffset = indexEntries.get((int)index).getEndPosition();
                int size = (int) (endOffset - startOffset + 1);
                // 为避免映射过大的区域，可按需调整映射范围
                MappedByteBuffer buffer = fileChannel.map(FileChannel.MapMode.READ_ONLY, startOffset, size);
                byte[] bytes = new byte[(int) size];
                buffer.get(bytes);
                String logEntry = new String(bytes, charset).trim();
                logEntries.add(logEntry);
            }
        }
        if (iStartNumber > indexEntries.size()-1) {
            result.setEnd(totalSize - 1);
        }
        else {
            result.setEnd(endEntryNumber + OFFSET_LINE_NO);
        }

        result.setList(logEntries);
        return result;
    }

    /**
     * 重写run方法以执行日志条目索引的后台构建任务
     * 该方法在一个循环中不断尝试构建日志条目索引，即使遇到异常也不中断执行
     * 使用一个标志变量isRunning来控制循环的执行，当调用stop方法时，循环终止
     */
    @Override
    public void run() {
        while (isRunning) {
            try {
                // 尝试构建日志条目索引
                buildLogEntryIndex();
            }
            catch (Exception ignored) {
                // 异常处理：此处忽略所有异常，确保循环能够持续执行
            }
            // 使线程睡眠50毫秒，以减少CPU占用
            ThreadUtils.sleep(50);
        }
    }

    /**
     * 提供停止后台任务的能力
     * 当调用此方法时，设置isRunning标志为false，从而终止run方法中的循环
     */
    public void stop() {
        this.isRunning = false;
    }

    @NoArgsConstructor
    @AllArgsConstructor
    @Data
    public static class IndexEntry {
        private long beginPosition;
        private long endPosition;
        private long endLine;
    }


    /**
     * 示例主方法，用于测试按日志条目号读取日志文件。
     */
    @SneakyThrows
    public static void main(String[] args) {
        // 替换为实际日志文件路径
        File logFile = new File("logs/error/kfaas_error.log");
        // 替换为要读取的起始日志条目号
        int startEntry = 19;
        // 替换为要读取的最大日志条目数
        int maxEntries = 1;
        LogFileReader reader = new LogFileReader(logFile, StandardCharsets.UTF_8);
        new Thread(reader).start();
        ThreadUtils.sleep(2000);
        LogReadResult result = reader.readLogEntries(startEntry, maxEntries);

        List<String> logs = result.getList();
        for (String log : logs) {
            System.out.println(log);
        }
    }
}

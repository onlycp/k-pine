package com.kingsware.kdev.sys.task;

import com.kingsware.kdev.core.context.SpringContext;
import com.kingsware.kdev.core.cron.KTask;
import com.kingsware.kdev.core.model.SysTaskHistory;
import com.kingsware.kdev.core.orm.DB;
import com.kingsware.kdev.core.orm.PagedList;
import com.kingsware.kdev.core.util.DateUtils;
import com.kingsware.kdev.core.util.JsonUtil;
import de.siegmar.fastcsv.writer.CsvWriter;
import lombok.extern.slf4j.Slf4j;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.*;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * 定时清理任务调度历史数据
 */
@Slf4j

public class SysTaskHistoryCleanTask implements KTask {

    private static final String BACKUP_DIR = "backup/sysTaskHistory";
    private static final String CSV_FILE_NAME = "task.csv";
    private static final long MAX_FILE_SIZE = 128 * 1024 * 1024; // 128MB
    private static final List<String> CSV_HEADERS = Arrays.asList(
            "id", "taskId", "taskName", "executeStatus", "executeTake", "executeBeginTime", "executeEndTime",
            "executeMsg", "whoCreated", "whenCreated", "whoModified", "whenModified"
    );

    private static final AtomicBoolean running = new AtomicBoolean(false);

    @Override
    public void execute() throws Exception {
        // 使用compareAndSet原子地“检查并设置”
        if (running.compareAndSet(false, true)) {
            try {
                // 成功获取到锁，执行业务逻辑
                doBusiness();
            } finally {
                // 确保在业务执行完毕或异常后释放锁
                running.set(false);
            }
        } else {
            // 未获取到锁，说明任务已在运行
            log.warn("任务 {} 正在运行，本次调度将被忽略。", name());
        }
    }

    private void doBusiness() throws Exception {
        // 从配置中获取保留天数，默认为7天
        int retentionDays = SpringContext.getInt("sys.task.clean.interval", 7);
        if (retentionDays <= 0) {
            retentionDays = 7;
        }
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DAY_OF_YEAR, -retentionDays);
        String sevenDaysAgo = DateUtils.formatDate(calendar.getTime(), "yyyy-MM-dd HH:mm:ss");

        String countSql = "select count(*) total from sys_task_history where execute_begin_time < ?";
        int total = DB.findSingleAttribute(Integer.class, countSql, sevenDaysAgo);
        if (total == 0) {
            log.info("没有{}天前的任务历史数据需要清理。", retentionDays);
            return;
        }

        // 确保备份目录存在
        new File(BACKUP_DIR).mkdirs();

        Path csvPath = Paths.get(BACKUP_DIR, CSV_FILE_NAME);

        // 批处理
        int pageSize = 1000;
        int totalPage = (int) Math.ceil(total / (double) pageSize);
        String pageSql = "select * from sys_task_history where execute_begin_time < ? order by execute_begin_time asc";
        Object[] params = {sevenDaysAgo};

        for (int i = 1; i <= totalPage; i++) {
            PagedList<SysTaskHistory> pagedList = DB.getDefault().findPagedList(SysTaskHistory.class, i, pageSize, pageSql, params);
            List<SysTaskHistory> histories = pagedList.getList();
            if (histories.isEmpty()) {
                continue;
            }

            // 检查并滚动文件
            rollCsvFile(csvPath);

            // 写入CSV
            try (CsvWriter csvWriter = CsvWriter.builder().build(csvPath, StandardCharsets.UTF_8, StandardOpenOption.CREATE, StandardOpenOption.APPEND)) {
                // 如果是新文件，则写入表头.
                if (Files.size(csvPath) == 0) {
                    csvWriter.writeRow(CSV_HEADERS);
                }

                // 按预定义的列顺序写入所有行
                for (SysTaskHistory history : histories) {
                    Map<String, Object> dataMap = JsonUtil.beanToMap(history);
                    List<String> row = new ArrayList<>();
                    for (String header : CSV_HEADERS) {
                        Object value = dataMap.get(header);
                        if (value instanceof Date) {
                            row.add(DateUtils.formatDate((Date) value, "yyyy-MM-dd HH:mm:ss"));
                        } else {
                            row.add(value == null ? "" : value.toString());
                        }
                    }
                    csvWriter.writeRow(row);
                }
            }

        }
        // 删除已备份的数据
        String deleteSql = "delete from sys_task_history where execute_begin_time < ?";
        DB.executeUpdateSql(deleteSql, sevenDaysAgo);
        log.info("成功备份并删除了 {} 以前的任务历史数据。", sevenDaysAgo);
    }

    private void rollCsvFile(Path csvPath) throws IOException {
        if (Files.exists(csvPath) && Files.size(csvPath) >= MAX_FILE_SIZE) {
            String date = DateUtils.formatDate(new Date(),"yyyy-MM-dd");
            int index = 1;
            Path newPath;
            do {
                newPath = Paths.get(BACKUP_DIR, "task-" + date + "-" + index + ".csv");
                index++;
            } while (Files.exists(newPath));
            Files.move(csvPath, newPath);
            log.info("CSV文件 '{}' 已达到大小限制，已重命名为 '{}'", csvPath.getFileName(), newPath.getFileName());
        }
    }

    @Override
    public String cron() {
        // 每天凌晨1点执行
        return "0 0 1 * * ?";
    }

    @Override
    public String name() {
        return "定时清理并备份任务调度历史数据";
    }

    @Override
    public String note() {
        return "将7天前的任务历史数据从数据库备份到CSV文件，并从数据库中删除。";
    }
}

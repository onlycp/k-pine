package com.kingsware.kdev.sys.initialize;

import com.kingsware.kdev.core.base.SystemInitialize;
import com.kingsware.kdev.core.orm.DB;
import com.kingsware.kdev.core.util.MD5Utils;
import com.kingsware.kdev.core.util.StringUtils;
import com.kingsware.kdev.sys.bean.ExecutionFile;
import com.kingsware.kdev.sys.model.DevSqlRun;
import com.kingsware.kdev.sys.ret.DevSqlRunRet;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.util.ResourceUtils;

import java.io.*;
import java.nio.file.Files;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

/**
 * @author andyzheng
 * @version 1.0.0
 * @description: TODO
 * @date 2022/4/19 07:31
 */
@Component
@Slf4j
public class SysSqlInitialize implements SystemInitialize {

    @Value("${database.initDatasourcePath:.}")
    private String initDatasourcePath;

    @Value("${database.sources.db.innerType:Mysql}")
    private String initDbType;
    @Override
    public void execute() {
        List<ExecutionFile> fileList = getFileList(getMaxExecuteVersion());
        log.info("初始化数据... starting");
        fileList.stream().sorted((Comparator.comparingInt(ExecutionFile::getVersion))).forEach(this::executeSqlFile);
        log.info("初始化数据... end");
    }

    private int getMaxExecuteVersion() {
        int max = 0;
        try {
            DevSqlRunRet ret = DB.findOne(DevSqlRunRet.class, "select max(version) as max from dev_sql_run");
            if (ret != null && ret.getMax() != null && ret.getMax() > max) {
                max = (int) ret.getMax();
            }
        }
        catch (Exception ignored) {
        }

        return max;
    }

    private List<ExecutionFile> getFileList(int maxVersion) {
        List<ExecutionFile> resultList = new ArrayList<>();
        String dbConfigFilePath = initDatasourcePath + "/initSql" + "/" + initDbType;
        File fileList = new File(dbConfigFilePath);
        File[] allFile = fileList.listFiles();
        if (allFile == null || allFile.length == 0) {
            return resultList;
        }
        return Arrays.stream(allFile).map(file -> {
            ExecutionFile executionFile = new ExecutionFile();
            String filename = file.getName();
            // 从initSql目录下读所有version_[版本号]_[是否只执行1次].sql文件
            Pattern pattern = Pattern.compile("version_(\\d+)_([0,1]).sql");
            Matcher matcher = pattern.matcher(filename);
            boolean isOnce = true;
            int version = 0;
            if (matcher.find()) {
                version = Integer.parseInt(matcher.group(1));
                isOnce = "1".equals(matcher.group(2));
            }

            if (file.isFile() && ((!isOnce) || (version > maxVersion))) {
                executionFile.setFile(file);
                executionFile.setName(filename);
                executionFile.setVersion(version);
                executionFile.setOnce(isOnce);
                return executionFile;
            }
            return null;
        }).filter(Objects::nonNull).collect(Collectors.toList());
    }

    public void executeSqlFile(ExecutionFile file) {
        if (file == null) {
            return;
        }
        log.info("运行数据库脚本:" + file.getFile().getAbsolutePath());
        long start = System.currentTimeMillis();
        boolean success = false;
        StringBuilder sqlSumary = new StringBuilder();
        try {
            List<String> sqlList = parseSqlList(file.getFile());
            for (String sql: sqlList) {
                long eachSqlStart = System.currentTimeMillis();
                sqlSumary.append(sql);
                DB.executeUpdateSql(sql);
                long eachSqlEnd = System.currentTimeMillis();
                log.info(String.format("SQL版本：%s，执行SQL: %s，用时：%sms", file.getVersion(), sql, (eachSqlEnd - eachSqlStart)));
            }
            success = true;
        } catch (Exception e) {
            e.printStackTrace();
            success = false;
        } finally {
            long end = System.currentTimeMillis();
            DevSqlRun model = new DevSqlRun();
            model.setExecutionTime(end - start);
            model.setSuccess(success ? 1 : 0);
            model.setMd5(MD5Utils.md5(sqlSumary.toString()));
            if (file != null) {
                model.setVersion(file.getVersion());
            }
            DB.save(model);
        }
    }

    /**
     * 获取所有的sql
     * @param file  文件
     * @return  sql列表
     */
    private List<String> parseSqlList(File file) {

        try (InputStreamReader isr = new InputStreamReader(Files.newInputStream(file.toPath())); BufferedReader br = new BufferedReader(isr)){
            List<String> sqlList = new ArrayList<>();
            StringBuilder sql = new StringBuilder();
            br.lines().forEach(line -> {
                // 去掉空格
                String cleanLine = line.trim();
                // 如果不是空才处理
                if (isSql(cleanLine)) {
                    sql.append(cleanLine).append("\n");
                    if (cleanLine.endsWith(";")) {
                        sqlList.add(sql.toString());
                        // 清空sql
                        sql.setLength(0);
                    }
                }
            });
            if (sql.length() > 0) {
                sqlList.add(sql.toString());
            }
            return sqlList;

        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    public boolean isSql(String content) {

        if (content == null) {
            return false;
        }
        if ("".equals(content.trim())) {
            return false;
        }
        if (content.startsWith("--")) {
            return false;
        }
        if (content.startsWith("/*")) {
            return false;
        }

        return true;
    }

    @Override
    public int sort() {
        return 2;
    }
}

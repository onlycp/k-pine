package com.kingsware.kdev.sys.initialize;

import com.kingsware.kdev.core.base.SystemInitialize;
import com.kingsware.kdev.core.cache.license.LicenseManager;
import com.kingsware.kdev.core.context.SpringContext;
import com.kingsware.kdev.core.kflow.FlowUtils;
import com.kingsware.kdev.core.orm.DB;
import com.kingsware.kdev.core.orm.exception.OrmDbException;
import com.kingsware.kdev.core.orm.kdb.EditFlowInfo;
import com.kingsware.kdev.core.orm.kdb.FlowInfo;
import com.kingsware.kdev.core.orm.kdb.KDBConnectConfig;
import com.kingsware.kdev.core.orm.kdb.KdbFlowQueryArgv;
import com.kingsware.kdev.core.util.FileUtils;
import com.kingsware.kdev.core.util.MD5Utils;
import com.kingsware.kdev.core.util.StringUtils;
import com.kingsware.kdev.sys.bean.ExecutionFile;
import com.kingsware.kdev.sys.model.DevSqlRun;
import com.kingsware.kdev.sys.ret.DevSqlRunRet;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;
import org.springframework.util.ResourceUtils;

import java.io.*;
import java.nio.file.Files;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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

//    @Value("${database.sources.db.innerType:Mysql}")
//    private String initDbType;
    @Override
    public void execute() {

        // 执行sql脚本
        List<ExecutionFile> fileList = getFileList(getMaxExecuteVersion());
        //log.info("初始化数据... starting");
        fileList.stream().sorted((Comparator.comparingInt(ExecutionFile::getVersion))).forEach(this::executeSqlFile);
        //log.info("初始化数据... end");

    }

    private int getMaxExecuteVersion() {
        int max = 0;
        try {
            DevSqlRunRet ret = DB.findOne(DevSqlRunRet.class, "select max(version) as max from dev_sql_run where success=1 ");
            if (ret != null && ret.getMax() != null && ret.getMax() > max) {
                max = ret.getMax();
            }
        }
        catch (Exception ignored) {
        }
        return max;

    }



    private List<ExecutionFile> getFileList(int maxVersion) {
        List<ExecutionFile> resultList = new ArrayList<>();
        boolean isCustomInitSqlPath = Boolean.parseBoolean(SpringContext.getProperties("file.is-custom-init-sql-path", "false"));
        String initDbType = DB.getDefault().getConfig().getInnerType();

        String path = ResourceUtils.CLASSPATH_URL_PREFIX + "initSql/" + initDbType + "/**";
        if (isCustomInitSqlPath) {
            // 在windows环境中，代码版运行./xx会找不到文件，需要改成.\xx
            File fileList = new File("");
            path = "file:" + initDatasourcePath + File.separator + "initSql" + File.separator + initDbType + "/**";
            log.info("[k-pine:SysSqlInitialize isCustomInitSqlPath]: true");
        }
        log.info("数据库脚本准备检查，目录为:" + path);
        Resource[] resources = SpringContext.getResources(path);
//        log.info("[k-pine:SysSqlInitialize resources]" + resources);
        if (resources != null) {
            for(Resource resource : resources) {
                ExecutionFile executionFile = new ExecutionFile();
                String filename = resource.getFilename();
//                log.info("[k-pine:SysSqlInitialize filename]" + filename + "----" + maxVersion);
                if (StringUtils.isEmpty(filename)) {
                    continue;
                }
                // 从initSql目录下读所有version_[版本号]_[是否只执行1次].sql文件
                Pattern pattern = Pattern.compile("version_(\\d+)_([0,1]).sql");
                Matcher matcher = pattern.matcher(filename);
                boolean isOnce = true;
                int version = 0;
                if (matcher.find()) {
                    version = Integer.parseInt(matcher.group(1));
                    isOnce = "1".equals(matcher.group(2));
                }
//                log.info("[k-pine:SysSqlInitialize add resultList]" + version + "----" + maxVersion + "----" + isOnce + "----" + resource.isFile());
                if (((!isOnce) || (version > maxVersion))) {
                    executionFile.setResource(resource);
                    executionFile.setName(filename);
                    executionFile.setVersion(version);
                    executionFile.setOnce(isOnce);
                    resultList.add(executionFile);
                    log.info("数据库文件:" + resource.getFilename() );
                }
            }
        }
        return resultList;
    }

    public void executeSqlFile(ExecutionFile file) {
        if (file == null) {
            return;
        }
        log.info("运行数据库脚本:{}", file.getName());
        long start = System.currentTimeMillis();
        boolean success = false;
        StringBuilder sqlSumary = new StringBuilder();
        try {
            List<String> sqlList = parseSqlList(file.getResource());
            for (String sql: sqlList) {
                long eachSqlStart = System.currentTimeMillis();
                sql = sql.trim();
                if (sql.endsWith(";")) {
                    sql = sql.substring(0, sql.length()-1);
                }
                sqlSumary.append(sql);
//                String tmpSql = sql.toLowerCase().trim();
//                if (tmpSql.startsWith("insert") || tmpSql.startsWith("update")) {
//                    sql = FlowUtils.buildCDATASql(sql);
//                }
                try {
                    DB.executeUpdateSql(FlowUtils.buildCDATASql(sql));
                } catch (OrmDbException e) {
                    if (e.getExceptionTrace().toLowerCase().contains("duplicate") || e.getMessage().toLowerCase().contains("duplicate")
                        || e.getExceptionTrace().toLowerCase().contains("already exists") || e.getMessage().toLowerCase().contains("already exists")
                            || e.getExceptionTrace().toLowerCase().contains("重复") || e.getMessage().toLowerCase().contains("重复")
                            || e.getExceptionTrace().toLowerCase().contains("存在") || e.getMessage().toLowerCase().contains("存在")) {
                        continue;
                    }
                    log.error("sql执行失败: " + sql + ", error: " + e.getExceptionTrace(), e);
                    throw e;
                } catch (RuntimeException e) {
                    if (e.getMessage().toLowerCase().contains("duplicate")
                            || e.getMessage().toLowerCase().contains("already exists")
                            || e.getMessage().toLowerCase().contains("重复")
                            || e.getMessage().toLowerCase().contains("存在")) {
                        continue;
                    }
                    log.error("sql执行失败: " + sql + ", error: " + e.getMessage(), e);
                    throw e;
                }
                long eachSqlEnd = System.currentTimeMillis();
                log.info(String.format("SQL版本：%s，执行SQL: %s，用时：%sms", file.getVersion(), sql, (eachSqlEnd - eachSqlStart)));
            }
            success = true;
        } catch (Exception e) {
            log.error(String.format("SQL版本执行失败：%s", file.getVersion()));

        } finally {
            long end = System.currentTimeMillis();
            DevSqlRun model = new DevSqlRun();
            model.setExecutionTime(end - start);
            model.setSuccess(success ? 1 : 0);
            model.setMd5(MD5Utils.md5(sqlSumary.toString()));
            model.setVersion(file.getVersion());
            DB.save(model);
        }
    }

    /**
     * 获取所有的sql
     * @param file  文件
     * @return  sql列表
     */
    private List<String> parseSqlList(Resource file) {

        try {
            List<String> readList = FileUtils.readAllLine(file.getInputStream());
            List<String> sqlList = new ArrayList<>();
            StringBuffer sql = new StringBuffer();
            readList.forEach(line -> {
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

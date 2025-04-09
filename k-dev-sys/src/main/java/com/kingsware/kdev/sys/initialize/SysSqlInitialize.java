package com.kingsware.kdev.sys.initialize;

import com.kingsware.kdev.core.base.SystemInitialize;
import com.kingsware.kdev.core.bean.SqlSegment;
import com.kingsware.kdev.core.cache.license.LicenseManager;
import com.kingsware.kdev.core.context.SpringContext;
import com.kingsware.kdev.core.kflow.FlowUtils;
import com.kingsware.kdev.core.orm.DB;
import com.kingsware.kdev.core.orm.exception.OrmDbException;
import com.kingsware.kdev.core.util.FileUtils;
import com.kingsware.kdev.core.util.MD5Utils;
import com.kingsware.kdev.core.util.SqlUtils;
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
        log.info("初始化数据... starting");
        fileList.stream().sorted((Comparator.comparingInt(ExecutionFile::getVersion))).forEach(this::executeSqlFile);
        // 加入R文件
        List<ExecutionFile> rFileList = getRFileList();
        rFileList.forEach(this::executeSqlFile);
        log.info("初始化数据... end");

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
        log.info("[k-pine:SysSqlInitialize resultList]{}", resultList);
        return resultList;
    }

    private List<ExecutionFile> getRFileList() {
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
                if (filename.startsWith("R_") && filename.endsWith(".sql")) {
                    executionFile.setResource(resource);
                    executionFile.setName(filename);
                    executionFile.setVersion(0);
                    executionFile.setOnce(true);
                    resultList.add(executionFile);
                }
            }
        }
        log.info("[k-pine:SysSqlInitialize resultList]{}", resultList);
        return resultList;
    }

    /**
     * 执行数据库脚本文件
     *
     * @param file 数据库脚本文件，包含脚本的位置和版本信息
     */
    public void executeSqlFile(ExecutionFile file) {
        // 如果文件为null，则直接返回，不执行任何操作
        if (file == null) {
            return;
        }
        // 记录开始执行的时间
        String md5 = "";
        log.info("运行数据库脚本:{}", file.getName());
        long start = System.currentTimeMillis();
        // 标记执行是否成功
        boolean success = false;
        // 用于拼接所有的SQL语句，以便记录
        StringBuilder sqlSumary = new StringBuilder();
        try {
            // 解析SQL文件，获取所有SQL语句段
            List<SqlSegment> sqlList = parseSqlList(file.getResource());
            md5 = MD5Utils.md5(StringUtils.joinToString(sqlList, "\n"));
            try {
                long cnt = DB.findCount("select count(1) from dev_sql_run where md5=? and success=1", md5);
                if (cnt > 0) {
                    log.info("[k-pine:SysSqlInitialize]跳过重复执行:{}", file.getName());
                    return;
                }

            }
            catch (Exception ignored) {

            }
            // 遍历并执行每条SQL语句
            for (SqlSegment sql: sqlList) {
                // 记录当前SQL语句开始执行的时间
                long eachSqlStart = System.currentTimeMillis();
                // 将当前SQL语句添加到汇总中
                sqlSumary.append(sql);
                // 执行SQL语句
                String curSql = sql.getSql();
                if (DB.getDefault().getConfig().getInnerType().equalsIgnoreCase("Oracle") && curSql.endsWith(";")) {
                    curSql = curSql.substring(0, curSql.length() - 1);
                }
                SqlUtils.executeSql("db", curSql);
                // 记录当前SQL语句执行结束的时间
                long eachSqlEnd = System.currentTimeMillis();
                // 记录SQL语句的版本、语句内容及执行时间
                log.info(String.format("SQL版本：%s，执行SQL: %s，用时：%sms", file.getVersion(), sql, (eachSqlEnd - eachSqlStart)));
            }
            // 如果所有SQL语句都成功执行，则标记为成功
            success = true;
        } catch (Exception e) {
            // 如果有异常抛出，则记录错误日志，并标记执行失败
            log.error(String.format("SQL版本执行失败：%s", file.getVersion()));

        } finally {
            // 记录结束执行的时间
            long end = System.currentTimeMillis();
            // 创建数据库操作记录模型
            DevSqlRun model = new DevSqlRun();
            // 记录执行时间
            model.setExecutionTime(end - start);
            // 记录执行结果，1表示成功，0表示失败
            model.setSuccess(success ? 1 : 0);
//            // 计算并记录所有执行SQL的MD5值，用于后续的校验
//            model.setMd5(MD5Utils.md5(sqlSumary.toString()));
            // 记录SQL版本
            model.setMd5(md5);
            model.setVersion(file.getVersion());
            // 保存数据库操作记录
            DB.save(model);
        }
    }

    /**
     * 获取所有的sql
     * @param file  文件
     * @return  sql列表
     */
    private List<SqlSegment> parseSqlList(Resource file) {

        try {
            List<String> readList = FileUtils.readAllLine(file.getInputStream());
            return SqlUtils.parseSql(readList);

        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }


    @Override
    public int sort() {
        return 2;
    }
}

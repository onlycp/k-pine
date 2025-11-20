package com.kingsware.kdev.sys.initialize;

import com.kingsware.kdev.core.base.SystemInitialize;
import com.kingsware.kdev.core.bean.JdbcUrl;
import com.kingsware.kdev.core.cache.license.LicenseManager;
import com.kingsware.kdev.core.context.SpringContext;
import com.kingsware.kdev.core.encrypt.SM4Utils;
import com.kingsware.kdev.core.kflow.define.FlowDefinition;
import com.kingsware.kdev.core.orm.DB;
import com.kingsware.kdev.core.orm.DBConnectConfig;
import com.kingsware.kdev.core.orm.DataBase;
import com.kingsware.kdev.core.orm.DbContext;
import com.kingsware.kdev.core.orm.kdb.*;
import com.kingsware.kdev.core.util.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;
import org.springframework.util.ResourceUtils;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.*;
import java.util.stream.Collectors;

/**
 * k-faas初始化
 *
 * @author chen peng
 * @version 1.0.0
 * @date 2022/3/28 4:37 下午
 */
@Component
@Slf4j
public class KFaasInitialize implements SystemInitialize {

    @Value("${database.initDatasourcePath:.}")
    private String initDatasourcePath;

    /**
     * faas目录
     **/
    @Value("${database.faas-folder:}")
    private String faasFolder;

    /**
     * faas命令
     **/
    @Value("${database.faas-start-cmd:}")
    private String faasStartCmd;

    /**
     * faas命令
     **/
    @Value("${database.faas-stop-cmd:}")
    private String faasStopCmd;

    /**
     * faas命令
     **/
    @Value("${database.faas-port:10081}")
    private int faasPort;

    @Value("${database.faas-ip:127.0.0.1}")
    private String faasIp;

    @Value("${encrypt.sm4.key:k-pine@kingsware}")
    private String sm4Key;

    @Value("${encrypt.sm4.iv:2024120120991231}")
    private String iv;


    /**
     * 解密数据源信息
     * @param dataSourceInfo    数据源
     */
    private DataSourceInfo decryptDataSourceInfo(DataSourceInfo dataSourceInfo) {
        if (dataSourceInfo.getUserName().startsWith("[SM4-") && dataSourceInfo.getUserName().endsWith("-SM4]")) {
            String str0 = dataSourceInfo.getUserName().substring(5, dataSourceInfo.getUserName().length() - 5);
            String str1 = SM4Utils.decryptData_CBC(str0, sm4Key, iv);
            dataSourceInfo.setUserName(str1);
        }
        if (dataSourceInfo.getPassword().startsWith("[SM4-") && dataSourceInfo.getPassword().endsWith("-SM4]")) {
            String str0 = dataSourceInfo.getPassword().substring(5, dataSourceInfo.getPassword().length() - 5);
            String str1 = SM4Utils.decryptData_CBC(str0, sm4Key, iv);
            dataSourceInfo.setPassword(str1);
        }
        if (dataSourceInfo.getJdbcUrl().startsWith("[SM4-") && dataSourceInfo.getJdbcUrl().endsWith("-SM4]")) {
            String str0 = dataSourceInfo.getJdbcUrl().substring(5, dataSourceInfo.getJdbcUrl().length() - 5);
            String str1 = SM4Utils.decryptData_CBC(str0, sm4Key, iv);
            dataSourceInfo.setJdbcUrl(str1);
        }
        return dataSourceInfo;

    }

    @Override
    public void execute() {
        // 启动faas
        startFaas();
        // 1. 初始化数据源
        // 加入MySql
        DbContext.getInstance().createDataBase("MySql", DbContext.getInstance().getDefault().getConfig());
        // 读取文件
        // 在windows环境中，代码版运行./xx会找不到文件，需要改成.\xx
        String dbConfigFilePath = initDatasourcePath + File.separator + "db.json";
//        log.info("DB-JSON path:{}", dbConfigFilePath);
        File dbConfigFile = new File(dbConfigFilePath);
        if (dbConfigFile.exists()) {
//            log.info("DB-JSON begin");
            String text = FileUtils.readFileText(dbConfigFile);
//            log.info("DB-JSON Content:{}", text);
            if (StringUtils.isNotEmpty(text)) {
                List<DataSourceFileInfo> dataSourceFromFile = JsonUtil.toListBean(text, DataSourceFileInfo.class);
                if (dataSourceFromFile != null) {
                    // 转为标准结构
                    List<DataSourceInfo> targetSources = new ArrayList<>();
                    dataSourceFromFile.forEach(it -> {
                        DataSourceInfo ds = new DataSourceInfo();
                        ds.setUserName(it.getUserName());
                        ds.setPassword(it.getPassword());
                        ds.setJdbcUrl(it.getJdbcUrl());
                        ds.setDriverClass(it.getDriverClass());
                        ds.setSourceName(it.getSourceName());
                        ds.setJson(JsonUtil.toJson(it.getJson()));
                        targetSources.add(decryptDataSourceInfo(ds));
                    });
                    // 获取所有的数据源
                    List<DataSourceInfo> dataSourceInfos = DB.kdbApi().queryDataSource(new DataSourceQueryArgv());
                    for (DataSourceInfo fileSource : targetSources) {
                        // 创建数据库
                        createInitDb(fileSource);
//                        // 查看是否已存在
                        Optional<DataSourceInfo> optional = dataSourceInfos.stream().filter(it -> it.getSourceName().equals(fileSource.getSourceName())).findFirst();
                        // 如果已存储，则修改
                        try {
                            if (optional.isPresent()) {
//                            log.info("数据源初始化修改-001: {}", fileSource);
                                DB.kdbApi().editDataSource(fileSource);
                            } else {
//                            log.info("数据源初始化新增: {}", fileSource);
                                DB.kdbApi().addDataSource(fileSource);
                            }
                        }
                        catch (Exception e) {
                            log.error("数据源初始化失败: {}", fileSource);
                        }

                    }

                }
            }
        }

        // 获取faas上的所有数据源，以获取数据库类型
        List<DataSourceInfo> allDataSources = DB.kdbApi().queryDataSource(new DataSourceQueryArgv());
        // 将所有的数据源，在青松中注册
        for (DataSourceInfo dataSourceInfo : allDataSources) {
            if (DB.getBySourceName(dataSourceInfo.getSourceName()) == null) {
                // 创建数据库
                KDBConnectConfig m = JsonUtil.toBean(JsonUtil.toJson(DB.getDefault().getConfig()), KDBConnectConfig.class);
                m.setDataSource(dataSourceInfo.getSourceName());
                m.setDbName(dataSourceInfo.getSourceName());
                m.setInnerType(getDbTyeName(dataSourceInfo.getJdbcUrl()));
                DbContext.getInstance().createDataBase(dataSourceInfo.getSourceName(), m);
                log.info("自动注册数据源：{}", dataSourceInfo.getSourceName());
            }
        }

        for (DataSourceInfo dataSourceInfo : allDataSources) {
            DataBase dataBase = DB.getBySourceName(dataSourceInfo.getSourceName());
            if (dataBase != null) {
                String dbType = getDbTyeName(dataSourceInfo.getJdbcUrl());
                dataBase.getConfig().setInnerType(dbType);
            }
        }

        try {
            // 初始化内置的逻辑编排
            List<FlowInfo> flowInfoList = getNestFlows();

            // 读取本地的 imported.json，获取哪些文件已经导入过
            File file = new File("imported.json");
            boolean fileExist = file.exists();

            boolean fileChange = false;

            // 已经导入过的文件名称
            List<String> skipFlowIdList = new ArrayList<>();
            if (fileExist) {
                String content = FileUtils.readFile(file);
                Map<String, Object> jsonMap = JsonUtil.toMap(content);
                if (jsonMap != null) {
                    Object flowIds = jsonMap.get("flowIds");
                    if (flowIds != null) {
                        String[] split = flowIds.toString().split(",");
                        skipFlowIdList.addAll(Arrays.asList(split));
                    }
                }
            }


            for (FlowInfo flowInfo: flowInfoList) {

                // 读取本地的 imported.json，判断是否已经导入过
                if(skipFlowIdList.contains(flowInfo.getFlowId())){
                    continue;
                }

                fileChange = true;

                KdbFlowQueryArgv kdbFlowQueryArgv = new KdbFlowQueryArgv();
                kdbFlowQueryArgv.setFlowId(flowInfo.getFlowId());
                List<FlowInfo> functionInfoList = DB.kdbApi().query(kdbFlowQueryArgv);

                // 如果没有，则新增
                if (functionInfoList.isEmpty()) {
                    log.info("[内置逻辑编排]-加载新增:{}", flowInfo.getName());
                    String sql = "insert into flow (flowid,name,content,description) values (?,?,?,?)";
                    DB.byName("kingDB").executeUpdateSql(sql, flowInfo.getFlowId(), flowInfo.getName(), flowInfo.getContent(), flowInfo.getDescription());
                }
                else {
                    log.info("[内置逻辑编排]-加载更新:{}", flowInfo.getName());
                    EditFlowInfo editFlowInfo = new EditFlowInfo();
                    editFlowInfo.setFlowId(flowInfo.getFlowId());
                    editFlowInfo.setContent(flowInfo.getContent());

                    boolean needUpdateFlowName = functionInfoList.get(0).getName().isEmpty();
                    if (needUpdateFlowName) {
                        editFlowInfo.setName(flowInfo.getName());
                    }

                    editFlowInfo.setDescription(flowInfo.getDescription());
                    DB.kdbApi().editFlow(editFlowInfo);
                }

                skipFlowIdList.add(flowInfo.getFlowId());
            }

            if (fileChange) {
                Map<String, Object> resultMap = new HashMap<>();
                resultMap.put("flowIds", String.join(",", skipFlowIdList));
                FileUtils.writeStringToFile(file, JsonUtil.toJson(resultMap), StandardCharsets.UTF_8);
            }
        }
        catch (Exception e) {
            log.error("初始化内置的逻辑编排失败", e);
        }

    }

    /**
     * 获取内置的逻辑编排列表
     * @return
     */
    private List<FlowInfo> getNestFlows() {
        List<FlowInfo> list = new ArrayList<>();
        String path = ResourceUtils.CLASSPATH_URL_PREFIX + "logicFlow/**";
        log.info("[内置逻辑编排]-资源路径:{}", path);
        Resource[] resources = SpringContext.getResources(path);
        if (resources == null) {
            return list;
        }
        for(Resource resource : resources) {
            String filename = resource.getFilename();
            if (StringUtils.isEmpty(filename)) {
                continue;
            }
            log.info("[内置逻辑编排]-获取文件名:{}", filename);
            FlowInfo flow = new FlowInfo();
            String name = filename.split("\\.")[0];
            flow.setFlowId(name);
            flow.setName(name);
            flow.setDescription("内置编排");
            flow.setCreateTime(System.currentTimeMillis());
            flow.setUpdateTime(System.currentTimeMillis());
            try (InputStream inputStream = resource.getInputStream()){
                List<String> lines = FileUtils.readAllLine(inputStream);
                flow.setContent(StringUtils.joinToString(lines, ""));
            } catch (IOException e) {
                flow.setContent("{}");
            }
            list.add(flow);

        }
        return list;
    }

    /**
     * 获取数据库类型
     *
     * @param jdbcUrl
     * @return
     */
    public String getDbTyeName(String jdbcUrl) {
        String tag = jdbcUrl.split(":")[1].trim();
        String dbType = "";
        if (tag.equalsIgnoreCase("mysql")) {
            dbType = "MySql";
        } else if (tag.contains("postgres")) {
            dbType = "Postgresql";
        } else if (tag.contains("h2")) {
            dbType = "H2";
        } else if (tag.contains("dm")) {
            dbType = "DM";
        } else if (tag.contains("oracle")) {
            dbType = "Oracle";
        } else if (tag.contains("sqlserver")) {
            dbType = "SQLServer";
        } else if (tag.contains("kingbase8")) {
            dbType = "Kingbase8";
        } else if (tag.contains("base")) {
            dbType = "gbase";
        } else {
            dbType = tag;
        }
        return dbType;
    }


    /**
     * 创建的数据库初始化
     *
     * @param dataSourceInfo 数据源信息
     */
    private void createInitDb(final DataSourceInfo dataSourceInfo) {
        // 解析url
        JdbcUrl jdbcUrl = JdbcUrlUtils.parseUrl(dataSourceInfo.getJdbcUrl());
        if (jdbcUrl == null) {
            return;
        }
        DataSourceInfo initDs = BeanUtils.copyObject(dataSourceInfo, DataSourceInfo.class);
        String dbName = jdbcUrl.getDbName();
        String sourceName = dataSourceInfo.getSourceName() + "PineInit";
        // 先移除
        try {
            // 创建数据源
            DB.kdbApi().deleteDataSource(sourceName);
        } catch (Exception ignored) {
        }
        try {
            if ("mysql".equalsIgnoreCase(jdbcUrl.getDbType())) {
                // 创建数据库
                try {
                    jdbcUrl.setDbName("mysql");
                    initDs.setJdbcUrl(jdbcUrl.build());
                    initDs.setSourceName(sourceName);
                    // 创建数据源
                    DB.kdbApi().addDataSource(initDs);
                    // 创建本地sql
                    KDBConnectConfig config = new KDBConnectConfig();
                    config.setChannel("kdbHttp");
                    config.setDatabaseType("kdb");
                    config.setInnerType(jdbcUrl.getDbType());
                    config.setDbName(sourceName);
                    config.setDataSource(sourceName);
                    config.setServer(SpringContext.getProperties("database.sources.db.server", ""));
                    config.setExecuteSqlApi(SpringContext.getProperties("database.sources.db.executeSqlApi", ""));
                    DbContext.getInstance().createDataBase(sourceName, config);
                    // 需要先初始化数据源
                    DB.kdbApi().refreshBaseFlow();
                    String createSchemaSql = String.format("CREATE DATABASE IF NOT EXISTS `%s` DEFAULT CHARSET utf8 COLLATE utf8_general_ci;", dbName);
                    DB.byName(initDs.getSourceName()).executeUpdateSql(createSchemaSql);
                }
                catch (Exception ignored) {
                }

            } else if ("postgresql".equalsIgnoreCase(jdbcUrl.getDbType())) {
                // 创建数据库
                try {
                    jdbcUrl.setDbName("postgres");
                    initDs.setJdbcUrl(jdbcUrl.build());
                    initDs.setSourceName(sourceName);
                    // 创建数据源
                    DB.kdbApi().addDataSource(initDs);
                    // 创建本地sql
                    KDBConnectConfig config = new KDBConnectConfig();
                    config.setChannel("kdbHttp");
                    config.setDatabaseType("kdb");
                    config.setInnerType(jdbcUrl.getDbType());
                    config.setDbName(sourceName);
                    config.setDataSource(sourceName);
                    config.setServer(SpringContext.getProperties("database.sources.db.server", ""));
                    config.setExecuteSqlApi(SpringContext.getProperties("database.sources.db.executeSqlApi", ""));
                    DbContext.getInstance().createDataBase(sourceName, config);
                    // 需要先初始化数据源
                    DB.kdbApi().refreshBaseFlow();

                    // 先判断数据库是否存在在
                    long count = DB.byName(initDs.getSourceName()).findCount(String.format("select count(1) from pg_database where datname = '%s'", dbName));
                    if (count == 0) {
                        String createSchemaSql = String.format("CREATE DATABASE \"%s\";", dbName);
                        DB.byName(initDs.getSourceName()).executeUpdateSql(createSchemaSql);
                    }
                } catch (Exception ignored) {
                }
            }
        } finally {
            try {
                DbContext.getInstance().removeDataBase(sourceName);
                DB.kdbApi().deleteDataSource(sourceName);
            } catch (Exception ignored) {
            }

        }


    }

    /**
     * 启动faas
     */
    private void startFaas() {
        if (StringUtils.isEmpty(faasStartCmd)) {
            return;
        }
        // 如果当前已启动，先关闭端口
        if (portOpened(faasIp, faasPort)) {
            log.info("faas已启动");
            return;

        }
        log.info("正在启动faas");
        new Thread(() -> ShellUtils.execAsync(faasFolder, faasStartCmd)).start();
        while (!portOpened(faasIp, faasPort)) {
            ThreadUtils.sleep(1000);
            log.info("正在检测faas状态，请耐心等等...");
        }
        log.info("完成启动faas");
    }

    private boolean portOpened(String ip, int port) {
        try (Socket ignored = new Socket(ip, port)) {
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    @Override
    public int sort() {
        return 0;
    }
}

package com.kingsware.kdev.core.i18n;

import com.kingsware.kdev.core.config.SysConst;
import com.kingsware.kdev.core.context.SpringContext;
import com.kingsware.kdev.core.cron.KRunner;
import com.kingsware.kdev.core.cron.KTask;
import com.kingsware.kdev.core.model.SysI18n;
import com.kingsware.kdev.core.orm.DB;
import com.kingsware.kdev.core.util.JsonUtil;
import com.kingsware.kdev.core.util.StringUtils;
import de.siegmar.fastcsv.reader.CsvReader;
import org.springframework.core.io.Resource;

import java.nio.charset.StandardCharsets;
import java.util.*;
import java.util.stream.Collectors;

/**
 * //todo 描述当前类是干什么用的.
 *
 * @author chen peng
 * @version 1.0.0
 * @date 2024/8/21 14:13
 */
public class I18nTask implements KTask, KRunner {
    @Override
    public void runNow() throws Exception {
        // 获取项目根目录下的i18n文件夹
        String[] paths =  new String[2];
        paths[0] = "classpath:i18n/**";
        paths[1] = "file:i18n/**";
        this.loadI18nFilesByPaths(paths);
        // 从数据库中加载
        this.execute();
    }



    @Override
    public void execute() throws Exception {
        // 查找所有的国际化配置
        List<SysI18n> sysI18ns = DB.findList(SysI18n.class, "select i18n_key, message, app_id from sys_i18n");
        for (SysI18n sysI18n : sysI18ns) {
            String key = sysI18n.getI18nKey();
            if ("公共应用".equals(key)) {
                System.currentTimeMillis();
            }
            if (sysI18n.getI18nKey() == null) {
                continue;
            }
            String message = sysI18n.getMessage();
            if (StringUtils.isNotEmpty(message)) {
                try {
                    Map<String, Object> datas = JsonUtil.toMap(message);
                    if (datas != null) {
                        for (Map.Entry<String, Object> entry : datas.entrySet()) {
                            String lang = entry.getKey();
                            if (entry.getValue() != null) {
                                String value = entry.getValue().toString();
                                I18n.putI18n(sysI18n.getAppId(), key, lang, value);
                            }
                        }
                    }

                }
                catch (Exception ignored) {

                }


            }
        }

    }

    /**
     * 根据指定路径加载国际化文件
     *
     * 此方法首先会查找项目内部的国际化配置目录，按路径获取所有相关资源文件，并对其进行排序
     * 然后逐一解析这些资源文件，以便在应用程序中使用
     *
     * @param paths 可变参数，指定国际化文件的路径
     */
    private void loadI18nFilesByPaths(String... paths) {
        // 初始化资源列表
        List<Resource> resourceList = new ArrayList<>();

        // 遍历所有提供的路径
        for (String path : paths) {
            // 使用SpringContext获取所有匹配的资源
            Resource[] resources = SpringContext.getResources(path);

            // 如果资源不为空，则对其进行排序并添加到资源列表中
            if (resources != null) {
                // 对资源进行排序，不区分大小写
                List<Resource> sortResources = Arrays
                        .stream(resources)
                        .sorted((o1, o2) -> Objects.requireNonNull(o1.getFilename()).compareToIgnoreCase(Objects.requireNonNull(o2.getFilename())))
                        .collect(Collectors.toList());

                // 将排序后的资源添加到总资源列表中
                resourceList.addAll(sortResources);
            }
        }
        // 遍历资源列表，逐一解析国际化文件
        List<SysI18n> sysI18ns = new ArrayList<>();
        for (Resource resource : resourceList) {
            List<SysI18n> subI18ns = this.parseI18nFile(resource);
            for (SysI18n sysI18n : subI18ns) {
                Optional<SysI18n> optional = sysI18ns.stream().filter(it -> it.getI18nKey().equals(sysI18n.getI18nKey())).findFirst();
                if (optional.isPresent()) {
                    optional.get().setMessage(sysI18n.getMessage());
                }
                else {
                    sysI18ns.add(sysI18n);
                }
            }
        }
        // 查找数据里的所有key
        List<String> keys = DB.findSingleAttributeList(String.class,"select i18n_key from sys_i18n");
        List<SysI18n> toAdddList = new ArrayList<>();
        for (SysI18n sysI18n : sysI18ns) {
            if (!keys.contains(sysI18n.getI18nKey())) {
                toAdddList.add(sysI18n);
            }
        }
        // 入库
        DB.saveAll(toAdddList);
    }

    /**
     * 解析国际化文件并加载到I18n实例中
     * 该方法使用CSV格式的国际化文件，第一行为语言标签，后续行为键值对
     *
     * @param resource 国际化文件资源对象，包含文件路径等信息
     */
    private List<SysI18n> parseI18nFile(Resource resource) {
        List<SysI18n> sysI18ns = new ArrayList<>();
        try (CsvReader  csv = CsvReader.builder().build(resource.getFile().toPath(), StandardCharsets.UTF_8)) {
            // 存储所有记录，每条记录为一个字符串列表
            List<List<String>> records = new ArrayList<>();
            // 遍历CSV记录，收集所有字段到records列表中
            csv.forEach(record -> {
                records.add(record.getFields());
            });
            // 第一行是标题，从第二列开始是语言标签
            List<String> langList = records.get(0).subList(1, records.get(0).size());
            // 遍历读取内容，第一列是键，第二列是app_id，后续列是各语言的值
            for (int i = 2; i < records.size(); i++) {
                List<String> record = records.get(i);
                String key = record.get(0);
                String appId = record.get(1);
                if (StringUtils.isEmpty(appId)) {
                    appId = SysConst.pineAppId;
                }
                SysI18n sysI18n = new SysI18n();
                Map<String, Object> lanMap = new HashMap<>();
                sysI18n.setI18nKey(key);
                sysI18n.setAppId(appId);
                // 从第二列开始，为每个语言标签对应的键值对调用I18n.putI18n方法
                for (int j = 1; j < record.size(); j++) {
                    String lang = langList.get(j - 1);
                    String value = record.get(j);
                    lanMap.put(lang, value);
                    // I18n.putI18n(key, lang, value);
                }
                sysI18n.setMessage(JsonUtil.toJson(lanMap));
                sysI18ns.add(sysI18n);

            }

        } catch (Exception e) {
            // 异常处理逻辑可根据实际情况进行补充，此处省略
        }
        return sysI18ns;
    }

    @Override
    public String cron() {
        return "0/30 * * * * ?";
    }

    @Override
    public String name() {
        return "系统任务加载国际化配置";
    }

    @Override
    public String note() {
        return "系统任务加载国际化配置, 定时从数据库加载，不加载本地文件的";
    }
}

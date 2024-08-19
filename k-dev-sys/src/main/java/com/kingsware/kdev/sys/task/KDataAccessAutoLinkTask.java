package com.kingsware.kdev.sys.task;

import com.kingsware.kdev.core.bean.CategoryData;
import com.kingsware.kdev.core.context.SpringContext;
import com.kingsware.kdev.core.cron.KRunner;
import com.kingsware.kdev.core.cron.KTask;
import com.kingsware.kdev.core.orm.DB;
import com.kingsware.kdev.core.orm.SqlWrapper;
import com.kingsware.kdev.core.orm.expression.Op;
import com.kingsware.kdev.core.util.JsonUtil;
import com.kingsware.kdev.core.util.StringUtils;
import com.kingsware.kdev.sys.model.SysDataAccessResource;
import com.kingsware.kdev.sys.model.SysDataResource;
import lombok.extern.slf4j.Slf4j;

import java.util.*;
import java.util.stream.Collectors;

/**
 * @author chenp
 * @date 2024/4/17
 */
@Slf4j
public class KDataAccessAutoLinkTask implements KTask, KRunner {
    /**
     * 马上运行
     */
    @Override
    public void runNow() throws Exception {
        this.execute();
    }

    /**
     * 执行任务
     **/
    @Override
    public void execute() throws Exception {
        String enableAutoLink = SpringContext.getProperties("app.enable-data-link", "false");
        if ("false".equalsIgnoreCase(enableAutoLink)) {
            return;
        }
        Map<String, List<CategoryData>> map = new HashMap<>();
        List<SysDataResource> dataResources = DB.findList(SysDataResource.class, "select * from sys_data_resource where is_tree = 1");
        List<SysDataAccessResource> toAddDataAccessResources = new ArrayList<>();
        for(SysDataResource dataResource: dataResources) {
            SysDataResource resource = dataResource;
            String[] arr = resource.getTableName().trim().split("\\.");
            String dbName = "db";
            String tableName = resource.getTableName();
            if (arr.length > 1) {
                dbName = arr[0];
                tableName = arr[1];
            }
            // 查询数据
            List<CategoryData> categoryDataList = DB.byName(dbName).findList(CategoryData.class, resource.getQuerySql());
            // 查找不是数据的数据
            List<CategoryData> parentDataList  = categoryDataList.stream().filter(it -> it.getIsData() != null && it.getIsData() != 1).collect(Collectors.toList());
            if (parentDataList.isEmpty()) {
                continue;
            }

            // 查找已分配的分类数据

            Set<String> parentIds = parentDataList.stream().map(CategoryData::getId).collect(Collectors.toSet());
            SqlWrapper sqlWrapper = new SqlWrapper("select data_id, access_id from sys_data_access_resource where 1=1");
            sqlWrapper.addCondition("table_name", Op.EQ, tableName);
            sqlWrapper.in("data_id", Arrays.asList(parentIds.toArray()));
            List<SysDataAccessResource> dataAccessResources = DB.findList(SysDataAccessResource.class, sqlWrapper.getSql(), sqlWrapper.getParams().toArray());
            // 获取关联的子数据
            // 查找
            Map<String, Set<String>> accessDataMap = new HashMap<>();
            for (SysDataAccessResource cd: dataAccessResources) {
                if (StringUtils.isEmpty(cd.getAccessId())) {
                    continue;
                }
                Set<String> dataIds = accessDataMap.get(cd.getAccessId());
                if (dataIds == null) {
                    dataIds = new HashSet<>();
                }
                dataIds.add(cd.getDataId());
                accessDataMap.put(cd.getAccessId(), dataIds);
            }

            for (Map.Entry<String, Set<String>> entry: accessDataMap.entrySet()) {
                String accessId = entry.getKey();
                Set<String> existsDataIds = entry.getValue();
                Set<CategoryData> linkDataList = getLinkData(categoryDataList, dataAccessResources.stream().filter(it -> it.getAccessId().equals(accessId)).map(it -> it.getDataId()).collect(Collectors.toSet()));
                if (linkDataList.isEmpty()) {
                    continue;
                }
                Set<String> toAddDataIds = linkDataList.stream().filter(it -> !existsDataIds.contains(it.getId())).map(it -> it.getId()).collect(Collectors.toSet());
                if (toAddDataIds.isEmpty()) {
                    continue;
                }
                for (String dataId: toAddDataIds) {
                    SysDataAccessResource dataAccessResource = new SysDataAccessResource();
                    dataAccessResource.setDataId(dataId);
                    dataAccessResource.setAccessId(accessId);
                    dataAccessResource.setTableName(tableName);
                    toAddDataAccessResources.add(dataAccessResource);
                }

            }
        }
        if (!toAddDataAccessResources.isEmpty()) {
            // log.info("准备新增数据权限组的资源配置:{}", JsonUtil.toJson(toAddDataAccessResources));
            DB.saveAll(toAddDataAccessResources);
        }
    }

    public Set<CategoryData> getLinkData(List<CategoryData> categoryDataList, Set<String> dataIds) {
        Set<CategoryData> matchDataList = new HashSet<>();
        Set<String> children = new HashSet<>();
        for (CategoryData cd: categoryDataList) {
            if(dataIds.contains(cd.getId())) {
                if (!matchDataList.contains(cd)) {
                    matchDataList.add(cd);
                }

            }
            else if(StringUtils.isNotEmpty(cd.getParentId()) && dataIds.contains(cd.getParentId())) {
                children.add(cd.getId());
            }
        }
        if (!children.isEmpty()) {
            matchDataList.addAll(getLinkData(categoryDataList,children));
        }

        return matchDataList;
    }

    /**
     * 表达式
     **/
    @Override
    public String cron() {
        return "0 0/1 * * * ?";
    }

    /**
     * 名称
     **/
    @Override
    public String name() {
        return "数据权限组数据同步";
    }

    @Override
    public String note() {
        return "数据权限组数据同步, 从数据库中加载数据权限组的配置";
    }
}

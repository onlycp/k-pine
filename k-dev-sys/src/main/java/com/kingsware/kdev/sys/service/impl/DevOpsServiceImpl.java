package com.kingsware.kdev.sys.service.impl;

import com.kingsware.kdev.core.auth.Dev;
import com.kingsware.kdev.core.bean.BaseRet;
import com.kingsware.kdev.core.context.SpringContext;
import com.kingsware.kdev.core.orm.DB;
import com.kingsware.kdev.core.orm.kdb.DataSourceInfo;
import com.kingsware.kdev.core.orm.kdb.DataSourceQueryArgv;
import com.kingsware.kdev.core.plugins.file.FileEncryptPlugin;
import com.kingsware.kdev.core.util.Base64Utils;
import com.kingsware.kdev.core.util.HttpUtil;
import com.kingsware.kdev.core.util.JsonUtil;
import com.kingsware.kdev.sys.devops.*;
import com.kingsware.kdev.sys.ret.SysKdbDataSourceRet;
import com.kingsware.kdev.sys.ret.SysUserLoginRet;
import com.kingsware.kdev.sys.service.DevOpsService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.web.client.RestTemplate;

import jakarta.annotation.PostConstruct;
import jakarta.annotation.Resource;
import java.util.*;
import java.util.stream.Collectors;

@org.springframework.stereotype.Service
@Slf4j
public class DevOpsServiceImpl implements DevOpsService {

    @Resource
    private RestTemplate restTemplate;



    @Override
    public Map<String, Object> tasks() {
        Map<String, Object> resultMap = new HashMap<>();
        List<DataCopyTask> tasks = getAllTasks();
        List<DataCopyInfo> copyInfoList = tasks.stream().map(task -> {
            DataCopyInfo copyInfo = new DataCopyInfo();
            copyInfo.setName(task.name());
            copyInfo.setNote(task.note());
            copyInfo.setStatus(task.status());
            copyInfo.setProgress(task.progress());
            copyInfo.setOrder(task.order());
            return copyInfo;
         }).collect(Collectors.toList());
        resultMap.put("tasks", copyInfoList);
        // 获取总任务进度
        resultMap.put("totalProgress", tasks.stream().mapToDouble(DataCopyTask::progress).average().orElse(0));
        return  resultMap;
    }

    private List<DataCopyTask> getAllTasks() {
        List<DataCopyTask> tasks = SpringContext.getBeansOfType(DataCopyTask.class);

        // 排序，升序处理
        tasks.sort((o1, o2) -> {
            Integer order1 = o1.order();
            Integer order2 = o2.order();
            return order1.compareTo(order2);
        });
        return tasks;
    }

    @Override
    public void startAll() {
        // 注册数据源
        List<SysKdbDataSourceRet> dataSourceList = DevOpsManager.getInstance().dataSourceList();
        // 获取所有的数据源
        List<DataSourceInfo> dataSourceInfos = DB.kdbApi().queryDataSource(new DataSourceQueryArgv());
        // 转为标准结构
        List<DataSourceInfo> targetSources = new ArrayList<>();
        dataSourceList.forEach(it -> {
            String sourceName = it.getId();
            if ("MySql".equalsIgnoreCase(it.getId())) {
                sourceName = DataCopyConst.PROD_DATA_SOURCE;
            }
            DataSourceInfo ds = new DataSourceInfo();
            ds.setUserName(it.getUsername());
            ds.setPassword(it.getPassword());
            ds.setJdbcUrl(it.getJdbcUrl());
            ds.setDriverClass(it.getDriverClass());
            ds.setSourceName(sourceName);
            ds.setJson(it.getJson());
            targetSources.add(ds);
        });

        for (DataSourceInfo fileSource : targetSources) {
            // 查看是否已存在
            Optional<DataSourceInfo> optional = dataSourceInfos.stream().filter(it -> it.getSourceName().equals(fileSource.getSourceName())).findFirst();
            try {
                // 如果已存储，则修改
                if (optional.isPresent()) {
                    log.info("数据源初始化修改-001: {}", fileSource);
                    DB.kdbApi().editDataSource(fileSource);
                } else {
                    log.info("数据源初始化新增: {}", fileSource);
                    DB.kdbApi().addDataSource(fileSource);
                }
            }
            catch (Exception e) {
                log.error("数据源初始化失败: {}", fileSource);
            }

        }
        List<DataCopyTask> tasks = getAllTasks();
        DataCopyParam context = DevOpsManager.getInstance().getContext();
        for (DataCopyTask task : tasks) {
            task.start(context);
        }
    }

    @Override
    public void step1(DataCopyParam copyParam) {

        // 向生产平台注入数据，否则无法获取数据源列表
        DevOpsManager.getInstance().login(copyParam);
        // 打开开发模式
        DevOpsManager.getInstance().openDevMode();
        // 重置问题
        List<DataCopyTask> tasks = getAllTasks();
        for (DataCopyTask task : tasks) {
            task.reset();
        }

    }
}

package com.kingsware.kdev.sys.service;

import com.kingsware.kdev.core.base.BaseService;
import com.kingsware.kdev.sys.devops.DataCopyParam;
import com.kingsware.kdev.sys.devops.DataCopyTask;

import java.util.List;
import java.util.Map;

public interface DevOpsService {


    /**
     * 获取任务列表
     * @return
     */
    Map<String, Object> tasks();

    /**
     * 启动所有任务
     */
    void startAll();

    /**
     * 步骤1
     * @param copyParam 步骤1
     */
    void step1(DataCopyParam copyParam);
}

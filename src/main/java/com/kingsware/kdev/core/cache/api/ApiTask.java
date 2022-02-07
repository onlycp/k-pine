package com.kingsware.kdev.core.cache.api;

import com.kingsware.kdev.core.cache.dict.DictManager;
import com.kingsware.kdev.core.cron.KTask;
import com.kingsware.kdev.core.orm.DB;
import com.kingsware.kdev.sys.model.SysApi;
import com.kingsware.kdev.sys.model.SysDictItem;

import java.util.ArrayList;
import java.util.List;

/**
 * 接口定时任务
 *
 * @author chen peng
 * @version 1.0.0
 * @date 2022/1/6 9:41 上午
 */
public class ApiTask implements KTask {

    /**
     * 定时拉取字典项
     */
    @Override
    public void execute() {
        // 查找所有字典
        List<SysApi> apis = DB.findList(SysApi.class, new ArrayList<>());
        ApiManager.getInstance().addApi(apis);
    }

    @Override
    public String cron() {
        return "0/5 * * * * ?";
    }

    @Override
    public String name() {
        return "ApiTask";
    }
}

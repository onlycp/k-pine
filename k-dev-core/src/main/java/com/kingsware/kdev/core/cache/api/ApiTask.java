package com.kingsware.kdev.core.cache.api;

import com.kingsware.kdev.core.cron.KTask;
import com.kingsware.kdev.core.orm.DB;

import java.util.List;

/**
 * 接口定时任务
 *
 * @author chen peng
 * @version 1.0.0
 * @date 2022/1/6 9:41 上午
 */
public class ApiTask implements KTask {

    public ApiTask() {
        this.execute();
    }

    /**
     * 定时拉取字典项
     */
    @Override
    public void execute() {
        // 查找所有字典
        List<ApiInfo> apis = DB.findList(ApiInfo.class, "select t0.*, t1.in_argv, t1.out_argv from sys_api t0 left join sys_logic_flow t1 on t1.flow_id=t0.api_flow_id");
        ApiManager.getInstance().addApi(apis);
    }

    @Override
    public String cron() {
        return "0/10 * * * * ?";
    }

    @Override
    public String name() {
        return "ApiTask";
    }
}

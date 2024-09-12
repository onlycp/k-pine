package com.kingsware.kdev.core.cache.api;

import com.kingsware.kdev.core.bean.DataModified;
import com.kingsware.kdev.core.context.SpringContext;
import com.kingsware.kdev.core.cron.KRunner;
import com.kingsware.kdev.core.cron.KTask;
import com.kingsware.kdev.core.kflow.KflowProperties;
import com.kingsware.kdev.core.orm.DB;
import com.kingsware.kdev.core.orm.SqlWrapper;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

/**
 * 接口定时任务
 *
 * @author chen peng
 * @version 1.0.0
 * @date 2022/1/6 9:41 上午
 */
@Slf4j
public class ApiTask implements KTask, KRunner {



    public ApiTask() {

    }

    /**
     * 定时拉取字典项
     */
    @Override
    public void execute() {

        KflowProperties kFlowProperties = SpringContext.getBean(KflowProperties.class);
        if (!kFlowProperties.isEnable()) {
            return;
        }


        // 查找所有接口
        try {
            SqlWrapper sqlWrapper = new SqlWrapper("select t0.id, t0.api_tags, t0.api_name, t0.api_result_handler, t0.api_rsp_argv,  t0.api_url, t0.api_method, t0.call_type, t0.api_flow_id, t0.api_code, t0.app_id, t0.who_created, t0.when_modified, t0.who_modified,t1.in_argv, t1.out_argv, t1.sub_flow_ids" +
                    ",t0.cache_enable, t0.cache_cron, t0.cache_expire_time, t1.i18n_keys from sys_api t0 ");
            sqlWrapper.appendSql("inner join sys_logic_flow t1 on t1.flow_id=t0.api_flow_id ");
            sqlWrapper.appendSql("where t0.api_url is not null and t0.api_method is not null order by t0.when_modified desc ");
            if (ApiManager.getInstance().getDataModified() == null) {
                List<ApiInfo> apis = DB.findList(ApiInfo.class, sqlWrapper.getSql());
                ApiManager.getInstance().addApi(apis);
            }
            else {
                DataModified modified = DB.findOne(DataModified.class, "select count(1) cnt, max(t0.when_modified) when_modified  from sys_api t0  inner join sys_logic_flow t1 on t1.flow_id=t0.api_flow_id  where t0.api_url is not null and t0.api_method is not null");
                // 没有变动
                if (modified.getWhenModified().equals(ApiManager.getInstance().getDataModified().getWhenModified()) && modified.getCnt() == ApiManager.getInstance().getDataModified().getCnt()) {
                    return;
                }
                List<ApiInfo> apis = DB.findList(ApiInfo.class, sqlWrapper.getSql());
                ApiManager.getInstance().addApi(apis);

            }



        }
        catch (Exception e) {
            log.warn("接口同步出错， 错误信息: {}", e.getMessage());
        }

    }

    @Override
    public String cron() {
        return "0/10 * * * * ?";
    }

    @Override
    public String name() {
        return "系统接口定时任务";
    }

    @Override
    public String note() {
        return "定时同步系统的接口定义";
    }

    @Override
    public void runNow() {
        this.execute();
    }
}

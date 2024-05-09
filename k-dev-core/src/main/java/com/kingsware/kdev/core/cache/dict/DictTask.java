package com.kingsware.kdev.core.cache.dict;

import com.kingsware.kdev.core.bean.SysDictItemRet;
import com.kingsware.kdev.core.bean.SysDictRet;
import com.kingsware.kdev.core.cron.KRunner;
import com.kingsware.kdev.core.cron.KTask;
import com.kingsware.kdev.core.orm.DB;
import com.kingsware.kdev.core.util.DateUtils;
import com.kingsware.kdev.core.util.StringUtils;
import lombok.extern.slf4j.Slf4j;

import java.util.Date;
import java.util.List;

/**
 * 字典定时任务，从数据库定时加载
 *
 * @author chen peng
 * @version 1.0.0
 * @date 2022/1/6 9:41 上午
 */
@Slf4j
public class DictTask implements KTask, KRunner {

    private static String lastQueryTime = null;

    public DictTask() {
    }

    /**
     * 定时拉取字典项
     */
    @Override
    public void execute() {
        // 查找所有字典
        // 查询5分钟之前的
        Date fiveMinuteAgoDate = new Date(new Date().getTime() - 5 * 60 * 1000) ;
        String fiveMinuteAgo = DateUtils.formatDate(fiveMinuteAgoDate, DateUtils.DATE_TIME);
//        String querySql = "select name,sys_dict_id, value, code, order_num from sys_dict_item";
//        if (StringUtils.isNotEmpty(lastQueryTime)) {
//            querySql = "select name,sys_dict_id, value, code, order_num from sys_dict_item where when_modified > '" + lastQueryTime + "'";
//        }
//        List<DictItemInfo> dictItemList = DB.findList(DictItemInfo.class, querySql);
        List<SysDictItemRet> dictItemRetList = DB.findList(SysDictItemRet.class, "select id, name, sys_dict_id, value, code,  order_num  from sys_dict_item");
        for (SysDictItemRet dictItem: dictItemRetList) {
            DictManager.getInstance().addDict(dictItem.getCode(), dictItem.getName(), dictItem.getValue());
        }
        lastQueryTime = fiveMinuteAgo;
        List<SysDictRet> dictList = DB.findList(SysDictRet.class, "select id, name, code from sys_dict");
        DictManager.getInstance().setDictList(dictList);
        DictManager.getInstance().setDictItemList(dictItemRetList);



    }

    @Override
    public String cron() {
        return "0/30 * * * * ?";
    }

    @Override
    public String name() {
        return "DictTask";
    }

    @Override
    public void runNow() {
        this.execute();
    }
}

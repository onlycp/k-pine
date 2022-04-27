package com.kingsware.kdev.core.cache.dict;

import com.kingsware.kdev.core.cron.KTask;
import com.kingsware.kdev.core.orm.DB;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

/**
 * 字典定时任务，从数据库定时加载
 *
 * @author chen peng
 * @version 1.0.0
 * @date 2022/1/6 9:41 上午
 */
@Slf4j
public class DictTask implements KTask {

    public DictTask() {
        this.execute();
    }

    /**
     * 定时拉取字典项
     */
    @Override
    public void execute() {
        // 查找所有字典
        List<DictItemInfo> dictItemList = DB.findList(DictItemInfo.class, "select * from sys_dict_item");
        for (DictItemInfo dictItem: dictItemList) {
            DictManager.getInstance().addDict(dictItem.getCode(), dictItem.getName(), dictItem.getValue());
        }
    }

    @Override
    public String cron() {
        return "0/30 * * * * ?";
    }

    @Override
    public String name() {
        return "DictTask";
    }
}

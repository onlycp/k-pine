package com.kingsware.kdev.core.cache.dict;

import com.kingsware.kdev.core.orm.DB;
import com.kingsware.kdev.sys.model.SysDictItem;
import org.springframework.boot.CommandLineRunner;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * 字典定时任务，从数据库定时加载
 *
 * @author chen peng
 * @version 1.0.0
 * @date 2022/1/6 9:41 上午
 */
@Component
public class DictTask implements CommandLineRunner {

    /**
     * 定时拉取字典项
     */
    @Scheduled(fixedRate=10000)
    public void execute() {
        // 查找所有字典
        List<SysDictItem> dictItemList = DB.findList(SysDictItem.class, new ArrayList<>());
        for (SysDictItem dictItem: dictItemList) {
            DictManager.getInstance().addDict(dictItem.getCode(), dictItem.getName(), dictItem.getValue());
        }
    }

    @Override
    public void run(String... args) throws Exception {
        execute();
    }
}

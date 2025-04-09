package com.kingsware.kdev.sys.task;

import com.kingsware.kdev.core.context.SpringContext;
import com.kingsware.kdev.core.cron.KRunner;
import com.kingsware.kdev.core.cron.KTask;
import com.kingsware.kdev.core.model.SysFile;
import com.kingsware.kdev.sys.initialize.KAppInitialize;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.atomic.AtomicBoolean;

/**
 * @author chenp
 * @date 2023/3/22
 */
@Slf4j
public class KAppAutoImportTask implements KTask, KRunner {

    private static final AtomicBoolean upgrading = new AtomicBoolean(false);
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
        if (upgrading.get()) {
            log.info("当前正在升级，将跳过本次升级");
            return;
        }
        try {
            upgrading.set(true);
            KAppInitialize kAppInitialize = SpringContext.getBean(KAppInitialize.class);
            kAppInitialize.execute();
        }
        catch (Exception e) {
            log.info("系统自动安装Pine包任务执行失败", e);
        }
        finally {
            upgrading.set(false);
        }

    }

    /**
     * 表达式
     **/
    @Override
    public String cron() {
        return  "0/30 * * * * ?";
    }

    /**
     * 名称
     **/
    @Override
    public String name() {
        return "系统自动安装Pine包任务";
    }

    @Override
    public String note() {
        return "系统自动安装Pine包任务，自动扫描api目录下的pine包进行安装";
    }
}

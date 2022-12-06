package com.kingsware.kdev.core.cache.core;

import com.kingsware.kdev.core.cron.KRunner;
import com.kingsware.kdev.core.cron.KTask;

public class SysCacheTask implements KTask, KRunner {
    @Override
    public void runNow() {
        this.execute();
    }

    @Override
    public void execute() {

    }

    @Override
    public String cron() {
        return "0 0 0 * * ?";
    }

    @Override
    public String name() {
        return "SysCacheTask";
    }
}
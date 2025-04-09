package com.kingsware.kdev.sys.devops;

public abstract class TableCopyTask implements DataCopyTask {
    /**
     * 总数
     */
    protected long tatal = 0;
    /**
     * 当前进度
     */
    protected long current = 0;


    @Override
    public Double progress() {
        if (tatal == 0) {
            return 0.0;
        }
        return (double)(current * 100 / tatal);
    }

    @Override
    public Integer status() {
        double progress = progress();
        if (progress >= 100) {
            return 2;
        }
        return progress >= 0 ? 1 : 0;
    }

    @Override
    public void reset() {
        this.tatal = 0;
        this.current = 0;
    }
}

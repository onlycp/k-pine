package com.kingsware.kdev.sys.devops;
import java.util.Map;

public interface DataCopyTask {
    /**
     * 任务名称
     * @return
     */
    String name();

    /**
     * 任务进度
     */
    Double progress();

    /**
     * 任务状态
     */
    Integer status();

    /**
     *  序号
     */
    Integer order();
    /**
     * 任务描述
     */
    String note();

    /**
     * 启动
     */
    void start(DataCopyParam context);

    /**
     * 重置
     */
    void reset();
}

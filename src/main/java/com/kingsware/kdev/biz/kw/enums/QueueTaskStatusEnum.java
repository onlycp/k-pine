package com.kingsware.kdev.biz.kw.enums;

/**
 * 队列任务状态
 */
public enum QueueTaskStatusEnum {
    NEW(0,"新增"),
    PROCESSING(1,"处理中"),
    DONE(2, "完成"),
    ERROR(3, "错误");
    private final Integer code;
    private final String status;
    QueueTaskStatusEnum(Integer code, String status){
        this.code=code;
        this.status=status;
    }
    public Integer getValue(){
        return code;
    }

    public String getName(){return status;}
}

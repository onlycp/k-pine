package com.kingsware.kdev.sys.ret;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.kingsware.kdev.core.bean.BaseSimpleRet;
import lombok.Data;

import java.sql.Timestamp;

/**
 * 函数返回
 *
 * @author chen peng
 * @version 1.0.0
 * @date 2022/1/5 3:06 下午
 */
@Data
public class SysKdbFunRet extends BaseSimpleRet {
    /** id **/
    private String id;
    /** name **/
    private String name;
    /** 脚本 **/
    private String script;
    /** 脚本类型 **/
    private String type;
    /** 描述 **/
    private String desc;
    /** 创建时间 **/
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss", timezone="GMT+8")
    private Timestamp whenCreated;
    /** 更新时间 **/
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss", timezone="GMT+8")
    private Timestamp whenModified;
}

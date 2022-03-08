package com.kingsware.kdev.sys.argv;

import com.kingsware.kdev.core.bean.BaseRet;
import com.kingsware.kdev.core.orm.annotation.Table;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.sql.Timestamp;

/**
 * 页面修改记录表
 * @author AndyZheng
 * @version 1.0.0
 * @date 2022-02-13 10:20
 */
@Data
@Table
public class DevPageHistoryArgv {

    /** id **/
    private String id;

    /** 页面ID */
    private String pageId;

    /** 页面JSON */
    private String pageJson;

    /** 创建人员 **/
    private String whoCreated;

    /** 创建时间 **/
    private Timestamp whenCreated;

}

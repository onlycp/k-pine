package com.kingsware.kdev.sys.argv;

import com.kingsware.kdev.core.orm.annotation.AutoEnum;
import com.kingsware.kdev.core.orm.annotation.Column;
import com.kingsware.kdev.core.orm.annotation.Table;
import lombok.Data;

import java.sql.Timestamp;

/**
 * 应用版本历史表
 * @author AndyZheng
 * @version 1.0.0
 * @date 2022-02-13 10:20
 */
@Data
@Table
public class DevApplicationVersionHistoryArgv {

    /** id **/
    @Column(auto = AutoEnum.ID)
    private String id;

    /** 创建人员 **/
    @Column(auto = AutoEnum.WHO, updatable = false)
    private String whoCreated;

    /** 创建时间 **/
    @Column(auto = AutoEnum.WHEN, updatable = false)
    private Timestamp whenCreated;

    /** 应用ID */
    private String appId;

    /** 当前发布版本 */
    private String version;


}

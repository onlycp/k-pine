package com.kingsware.kdev.core.model;

import com.kingsware.kdev.core.bean.BaseModel;
import com.kingsware.kdev.core.orm.annotation.AutoEnum;
import com.kingsware.kdev.core.orm.annotation.Column;
import com.kingsware.kdev.core.orm.annotation.Table;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 *
 * @author chen peng
 * @version 1.0.0
 * @date 2023/2/20 10:57
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Table
public class SysInstance extends BaseModel {
    /** 主键 **/
    @Column(auto = AutoEnum.ID)
    private String id;
    /** 主键名 **/
    private String hostName;
    /** 端口 **/
    private Integer port;
    /** 心跳时间 **/
    private String heartBeatTime;
    /** 注册时间 **/
    private String regTime;
    /** 在线状态 **/
    private Integer online;
    /** 集群号 **/
    private Integer clusterNo;

    public String instanceName() {
        return hostName + ":" + port;
    }
}

package com.kingsware.kdev.sys.model;

import com.kingsware.kdev.core.bean.BaseModel;
import com.kingsware.kdev.core.orm.annotation.AutoEnum;
import com.kingsware.kdev.core.orm.annotation.Column;
import com.kingsware.kdev.core.orm.annotation.Table;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
@Table
public class DevTeamMember extends BaseModel {
    /** id */
    @Column(auto = AutoEnum.ID)
    private String id ;
    /** 团队ID */
    private String teamId ;
    /** 用户ID */
    private String userId ;
    /** 加入时间 */
    private String whenJoin ;
    /** 邀请人 */
    private String whoInvite ;
    /** 团队角色 */
    private String teamRoleId ;
    /** 是否为团队负责人 */
    private Integer isOwner ;
    /** 关联应用 */
    private String appId ;

}

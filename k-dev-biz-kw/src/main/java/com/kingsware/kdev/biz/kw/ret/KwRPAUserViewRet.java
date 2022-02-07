package com.kingsware.kdev.biz.kw.ret;

import com.kingsware.kdev.core.bean.BaseSimpleRet;
import lombok.Data;

/**
 * @author andyzheng
 * @version 1.0.0
 * @description: 用户视图
 * @date 2022/1/18 17:13
 */
@Data
public class KwRPAUserViewRet extends BaseSimpleRet {
    /** 用户ID */
    private String username;
    /** 用户姓名 */
    private String realName;
    /** 用户状态 */
    private String status;
    /** 创建日期 */
    private String whenCreated;
    /** 组织架构ID */
    private String unitId;
}

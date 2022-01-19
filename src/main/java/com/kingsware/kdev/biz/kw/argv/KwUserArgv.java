package com.kingsware.kdev.biz.kw.argv;

import lombok.Data;

/**
 * @author andyzheng
 * @version 1.0.0
 * @description: 公司信息表
 * @date 2022/1/5 10:13
 */
@Data
public class KwUserArgv {
    /** ID */
    private String id;
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

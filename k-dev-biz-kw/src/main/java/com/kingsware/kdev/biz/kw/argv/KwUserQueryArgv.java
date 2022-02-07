package com.kingsware.kdev.biz.kw.argv;

import com.kingsware.kdev.core.bean.BasePageArgv;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author andyzheng
 * @version 1.0.0
 * @description: 公司信息表列表参数
 * @date 2022/1/4 18:09
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class KwUserQueryArgv extends BasePageArgv {
    /** 用户ID */
    private String username;
}

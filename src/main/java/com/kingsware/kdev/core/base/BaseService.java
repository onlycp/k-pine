package com.kingsware.kdev.core.base;

import com.kingsware.kdev.core.bean.BaseManageModel;
import com.kingsware.kdev.core.bean.BaseManageRet;
import com.kingsware.kdev.core.bean.BasePageArgv;
import com.kingsware.kdev.core.bean.PageDataRet;

import java.util.List;

/**
 * 业务基类
 *
 * @author chen peng
 * @version 1.0.0
 * @date 2021/12/23 9:21 上午
 */
public interface BaseService {
    /**
     * 二次封装查询
     * @param sql           查询sql
     * @param pageArgv      分页查询参数
     * @param inClass       传入class
     * @return              可用的分页结果
     */
    PageDataRet<? extends BaseManageRet> query(String sql, List<Object> params, BasePageArgv pageArgv, Class<? extends BaseManageModel> inClass,  Class<? extends BaseManageRet> outClass);

    /**
     * 将model转为ret
     * @param model 模型
     * @return      返回结果
     */
    BaseManageRet model2Ret(BaseManageModel model, Class<? extends BaseManageRet> outClass);
}

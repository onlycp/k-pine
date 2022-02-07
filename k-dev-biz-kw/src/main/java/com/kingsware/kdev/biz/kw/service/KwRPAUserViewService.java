package com.kingsware.kdev.biz.kw.service;

import com.kingsware.kdev.biz.kw.argv.KwRPAUserViewQueryArgv;
import com.kingsware.kdev.biz.kw.ret.KwRPAUserViewRet;
import com.kingsware.kdev.core.base.BaseService;
import com.kingsware.kdev.core.bean.PageDataRet;

/**
 * 银行账户视图管理业务类
 *
 * @author AndyZheng
 * @version 1.0.0
 * @date 2021/1/5 9:35 上午
 */
public interface KwRPAUserViewService extends BaseService {


    /**
     * 编辑
     * @param argv 编辑
     * @return 查询结果
     */
     PageDataRet<KwRPAUserViewRet> query(KwRPAUserViewQueryArgv argv);

    void updateUsers();
}

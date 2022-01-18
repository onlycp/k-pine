package com.kingsware.kdev.biz.kw.service;

import com.kingsware.kdev.biz.kw.argv.KwRPABankViewQueryArgv;
import com.kingsware.kdev.biz.kw.ret.KwRPABankViewRet;
import com.kingsware.kdev.core.base.BaseService;
import com.kingsware.kdev.core.bean.PageDataRet;

/**
 * 银行账户视图管理业务类
 *
 * @author AndyZheng
 * @version 1.0.0
 * @date 2021/1/5 9:35 上午
 */
public interface KwRPABankViewService extends BaseService {


    /**
     * 编辑
     * @param argv 编辑
     * @return 查询结果
     */
     PageDataRet<KwRPABankViewRet> query(KwRPABankViewQueryArgv argv);

    void updateBankAccountExpand();

    void updateBankAccount();

    void updateBankAccountAllByView();
}

package com.kingsware.kdev.biz.kw.service.impl;

import com.kingsware.kdev.biz.kw.argv.KwWaterQueryArgv;
import com.kingsware.kdev.biz.kw.ret.KwWaterRet;
import com.kingsware.kdev.biz.kw.service.KwAccountHistoryBalanceService;
import com.kingsware.kdev.core.base.BaseServiceImpl;
import com.kingsware.kdev.core.bean.PageDataRet;

/**
 *
 */
public class KwAccountHistoryBalanceServiceImpl extends BaseServiceImpl implements KwAccountHistoryBalanceService {
    @Override
    public KwWaterRet get(String id) {
        return null;
    }

    @Override
    public void add(KwWaterQueryArgv argv) {

    }

    /**
     * 分页查询历史余额（从流水中）
     * @param argv 查询
     * @return
     */
    @Override
    public PageDataRet<KwWaterRet> query(KwWaterQueryArgv argv) {



        return null;
    }

    @Override
    public void export(KwWaterQueryArgv argv) {

    }
}

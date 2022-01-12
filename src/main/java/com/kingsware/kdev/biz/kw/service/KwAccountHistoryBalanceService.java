package com.kingsware.kdev.biz.kw.service;

import com.kingsware.kdev.biz.kw.argv.KwWaterQueryArgv;
import com.kingsware.kdev.biz.kw.ret.KwWaterRet;
import com.kingsware.kdev.core.base.BaseService;
import com.kingsware.kdev.core.bean.PageDataRet;

/**
 * 账户历史余额业务类
 *
 * @author chen peng
 * @version 1.0.0
 * @date 2021/12/23 9:35 上午
 */
public interface KwAccountHistoryBalanceService extends BaseService {

    /**
     * 通过id查询（暂时保留）
     * @param id    id
     * @return      返回结果
     */
    KwWaterRet get(String id);

    /**
     * 新增（暂时保留）
     * @param argv 新增
     */
     void add(KwWaterQueryArgv argv);


    /**
     * 查询
     * @param argv 查询
     * @return 查询结果
     */
     PageDataRet<KwWaterRet> query(KwWaterQueryArgv argv);



    /**
     * 导出
     */
    void export(KwWaterQueryArgv argv);
}

package com.kingsware.kdev.biz.kw.service;

import com.kingsware.kdev.biz.kw.argv.KwAbnormalQueryArgv;
import com.kingsware.kdev.biz.kw.argv.KwWaterQueryArgv;
import com.kingsware.kdev.biz.kw.ret.KwAbnormalRet;
import com.kingsware.kdev.biz.kw.ret.KwBankAccountExpandRet;
import com.kingsware.kdev.biz.kw.ret.KwWaterRet;
import com.kingsware.kdev.core.base.BaseService;
import com.kingsware.kdev.core.bean.MultiIdArgv;
import com.kingsware.kdev.core.bean.PageDataRet;

import java.util.List;

/**
 * 行别管理业务类
 *
 * @author chen peng
 * @version 1.0.0
 * @date 2021/12/23 9:35 上午
 */
public interface KwAbnormalService extends BaseService {

    /**
     * 异常总汇页面 queryAbnormal
     * @param argv
     * @return
     */
    PageDataRet<KwAbnormalRet> queryAbnormal(KwAbnormalQueryArgv argv);

    /**
     * 余额检查 checkBalance
     */
    void checkBalance();

    /**
     * 余额异常页面 queryAbnormalWater
     * @param argv
     * @return
     */
    PageDataRet<KwWaterRet> queryAbnormalWater(KwWaterQueryArgv argv);

    /**
     * 异常余额（流水）详情 getAbnormalWater
     * @param Id
     * @return
     */
    List<KwWaterRet> getWaterContext(String Id);

}

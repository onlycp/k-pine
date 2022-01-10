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
     * 方法目录
     * 1 异常总汇页面 queryAbnormal
     * 2 余额检查 checkbalance
     * 3 余额异常页面 queryAbnormalWater
     * 4 异常余额（流水）详情 getAbnormalWater
     * 5 ...
     *
     *
     */

    PageDataRet<KwAbnormalRet> queryAbnormal(KwAbnormalQueryArgv argv);
    void checkbalance();
    PageDataRet<KwWaterRet> queryAbnormalWater(KwWaterQueryArgv argv);
    List<KwWaterRet> getWaterContext(String Id);

}

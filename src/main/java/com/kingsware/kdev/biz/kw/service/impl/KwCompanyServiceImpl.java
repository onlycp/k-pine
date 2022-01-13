package com.kingsware.kdev.biz.kw.service.impl;

import com.kingsware.kdev.biz.kw.argv.KwCompanyArgv;
import com.kingsware.kdev.biz.kw.argv.KwCompanyQueryArgv;
import com.kingsware.kdev.biz.kw.model.KwCompany;
import com.kingsware.kdev.biz.kw.ret.KwCompanyRet;
import com.kingsware.kdev.biz.kw.service.KwCompanyService;
import com.kingsware.kdev.core.base.BaseServiceImpl;
import com.kingsware.kdev.core.bean.MultiIdArgv;
import com.kingsware.kdev.core.bean.PageDataRet;
import com.kingsware.kdev.core.exception.BusinessException;
import com.kingsware.kdev.core.orm.DB;
import com.kingsware.kdev.core.orm.SqlWrapper;
import com.kingsware.kdev.core.orm.expression.Op;
import com.kingsware.kdev.core.util.BeanUtils;
import com.kingsware.kdev.core.util.StringUtils;
import org.springframework.stereotype.Service;

/**
 * 银行版本管理业务实现类
 *
 * @author AndyZheng
 * @version 1.0.0
 * @date 2021/1/5 9:36 上午
 */
@Service
public class KwCompanyServiceImpl extends BaseServiceImpl implements KwCompanyService {

    @Override
    public KwCompanyRet get(String id) {
        // 查询model
        KwCompany model = DB.findById(KwCompany.class, id);
        // 转换成ret对象
        return (KwCompanyRet) model2Ret(model, KwCompanyRet.class);
    }

    @Override
    public void add(KwCompanyArgv argv) {
        KwCompany model = BeanUtils.copyObject(argv, KwCompany.class);
        // 保存
        DB.save(model);
    }

    @Override
    public void edit(KwCompanyArgv argv) {
        KwCompany model = DB.findById(KwCompany.class, argv.getId());
        if (model == null) {
            throw BusinessException.serviceThrow("当前数据不存在，修改失败。");
        }
        BeanUtils.copyProperties(argv, model);
        // 保存
        DB.update(model);
    }


    @Override
    public PageDataRet<KwCompanyRet> query(KwCompanyQueryArgv argv) {
        // 拼装sql
        SqlWrapper wrapper = new SqlWrapper(" select * from kw_company where 1=1 and deleted=0 ");
        // 拼装查询sql
        if (StringUtils.isNotEmpty(argv.getName())) {
            wrapper.addCondition("name", Op.LIKE, "%" +argv.getName() +"%");
        }
        if (StringUtils.isNotEmpty(argv.getSerialNumber())) {
            wrapper.addCondition("serial_number", Op.LIKE, "%" +argv.getSerialNumber() +"%");
        }
        if (StringUtils.isNotEmpty(argv.getShortName())) {
            wrapper.addCondition("short_name", Op.LIKE, "%" +argv.getShortName() +"%");
        }
        return (PageDataRet<KwCompanyRet>) query(wrapper.getSql(), wrapper.getParams(), argv, KwCompanyRet.class);
    }

    @Override
    public void delete(MultiIdArgv argv) {
        for (String id: argv.getIds()) {
            DB.delete(KwCompany.class, id);
        }
    }
}

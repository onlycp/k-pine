package com.kingsware.kdev.biz.kw.service.impl;

import com.kingsware.kdev.biz.kw.argv.KwUserArgv;
import com.kingsware.kdev.biz.kw.argv.KwUserQueryArgv;
import com.kingsware.kdev.biz.kw.model.KwUser;
import com.kingsware.kdev.biz.kw.ret.KwUserRet;
import com.kingsware.kdev.biz.kw.service.KwUserService;
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
public class KwUserServiceImpl extends BaseServiceImpl implements KwUserService {

    @Override
    public KwUserRet get(String id) {
        // 查询model
        KwUser model = DB.findById(KwUser.class, id);
        // 转换成ret对象
        return (KwUserRet) model2Ret(model, KwUserRet.class);
    }

    @Override
    public void add(KwUserArgv argv) {
        KwUser model = BeanUtils.copyObject(argv, KwUser.class);
        // 保存
        DB.save(model);
    }

    @Override
    public void edit(KwUserArgv argv) {
        KwUser model = DB.findById(KwUser.class, argv.getId());
        if (model == null) {
            throw BusinessException.serviceThrow("当前数据不存在，修改失败。");
        }
        BeanUtils.copyProperties(argv, model);
        // 保存
        DB.update(model);
    }


    @Override
    public PageDataRet<KwUserRet> query(KwUserQueryArgv argv) {
        // 拼装sql
        SqlWrapper wrapper = new SqlWrapper(" select * from kw_user where 1=1  ");
        // 拼装查询sql
        wrapper.addCondition("username", Op.EQ, argv.getUsername());
        return (PageDataRet<KwUserRet>) query(wrapper.getSql(), wrapper.getParams(), argv, KwUserRet.class);
    }

    @Override
    public void delete(MultiIdArgv argv) {
        for (String id: argv.getIds()) {
            DB.delete(KwUser.class, id);
        }
    }
}

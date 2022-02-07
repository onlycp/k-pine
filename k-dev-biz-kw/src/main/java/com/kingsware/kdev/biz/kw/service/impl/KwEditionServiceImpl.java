package com.kingsware.kdev.biz.kw.service.impl;

import com.kingsware.kdev.biz.kw.argv.KwEditionArgv;
import com.kingsware.kdev.biz.kw.argv.KwEditionQueryArgv;
import com.kingsware.kdev.biz.kw.model.KwEdition;
import com.kingsware.kdev.biz.kw.ret.KwEditionRet;
import com.kingsware.kdev.biz.kw.service.KwEditionService;
import com.kingsware.kdev.core.base.BaseServiceImpl;
import com.kingsware.kdev.core.bean.MultiIdArgv;
import com.kingsware.kdev.core.bean.PageDataRet;
import com.kingsware.kdev.core.i18n.I18n;
import com.kingsware.kdev.core.orm.DB;
import com.kingsware.kdev.core.orm.DBChecker;
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
public class KwEditionServiceImpl extends BaseServiceImpl implements KwEditionService {

    @Override
    public KwEditionRet get(String id) {
        // 查询model
        KwEdition model = DB.findById(KwEdition.class, id);
        // 转换成ret对象
        return (KwEditionRet) model2Ret(model, KwEditionRet.class);
    }

    @Override
    public void add(KwEditionArgv argv) {
        KwEdition model = BeanUtils.copyObject(argv, KwEdition.class);
        checkUnique(model);
        // 保存
        DB.save(model);
    }

    @Override
    public void edit(KwEditionArgv argv) {
        KwEdition model = DB.findById(KwEdition.class, argv.getId());
        model.setName(argv.getName());
        model.setBankType(argv.getBankType());
        model.setPasswordMaxRetried(argv.getPasswordMaxRetried());
        model.setUkey(argv.getUkey());
        model.setPath(argv.getPath());
        model.setStatus(argv.getStatus());
        model.setDescription(argv.getDescription());
        model.setMechanismId(argv.getMechanismId());
        checkUnique(model);
        // 保存
        DB.update(model);
    }

    /**
     * 校验唯一性
     * @param model 模型
     */
    private void checkUnique(KwEdition model) {
        // 唯一性校验
        DBChecker<KwEdition> checker =DBChecker.build(model, KwEdition.class);
        // 银行名称
        checker.uni(new String[]{"name", "mechanismId"}, I18n.t("KwEdition.name.unique", "该机构下已经存在该版本名称。"));
        // 执行校验
        checker.checkUnique();
    }


    @Override
    public PageDataRet<KwEditionRet> query(KwEditionQueryArgv argv) {
        // 拼装sql
        StringBuilder builder = new StringBuilder();
        builder.append(" select ke.*, km.bank_name as mechanism_Name from kw_edition as ke ");
        builder.append(" left join kw_mechanism as km on ke.mechanism_id = km.id ");
        builder.append(" where ke.deleted=0 ");
        SqlWrapper wrapper = new SqlWrapper(builder.toString());
        // 拼装查询sql
        if (StringUtils.isNotEmpty(argv.getName())) {
            wrapper.addCondition("ke.name", Op.LIKE, "%" +argv.getName() +"%");
        }
        if (StringUtils.isNotEmpty(argv.getMechanismId())) {
            wrapper.addCondition("ke.mechanism_id", Op.EQ, argv.getMechanismId());
        }
        wrapper.sortBy("km.bank_name desc");

        return (PageDataRet<KwEditionRet>) query(wrapper.getSql(), wrapper.getParams(), argv, KwEditionRet.class);
    }

    @Override
    public void delete(MultiIdArgv argv) {
        for (String id: argv.getIds()) {
            DB.delete(KwEdition.class, id);
        }
    }

    @Override
    public String findIdByName(String name){
        if(name != null && StringUtils.isNotEmpty(name)){
            SqlWrapper wrapper = new SqlWrapper("select ke.* from kw_edition as ke where 1 = 1");
            wrapper.addCondition("ke.name", Op.EQ, name);
            KwEdition kwEdition = DB.findOne(KwEdition.class, wrapper.getSql(), wrapper.getParams().toArray());
            if(kwEdition == null){
                return null;
            }
            return kwEdition.getId();
        }else{
            return null;
        }
    }
}

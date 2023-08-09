package com.kingsware.kdev.sys.service.impl;

import com.kingsware.kdev.core.auth.AppAuthProperties;
import com.kingsware.kdev.core.auth.BaseUserInfo;
import com.kingsware.kdev.core.auth.TokenUtil;
import com.kingsware.kdev.core.base.BaseServiceImpl;
import com.kingsware.kdev.core.bean.MultiIdArgv;
import com.kingsware.kdev.core.bean.PageDataRet;
import com.kingsware.kdev.core.constants.PropertiesConstant;
import com.kingsware.kdev.core.context.KClientContext;
import com.kingsware.kdev.core.context.SpringContext;
import com.kingsware.kdev.core.exception.BusinessException;
import com.kingsware.kdev.core.i18n.I18n;
import com.kingsware.kdev.core.kflow.KFlowContext;
import com.kingsware.kdev.core.orm.DB;
import com.kingsware.kdev.core.orm.DBChecker;
import com.kingsware.kdev.core.orm.SqlWrapper;
import com.kingsware.kdev.core.orm.expression.Op;
import com.kingsware.kdev.core.util.BeanUtils;
import com.kingsware.kdev.core.util.ServletUtil;
import com.kingsware.kdev.core.util.StringUtils;
import com.kingsware.kdev.sys.argv.SysConfigArgv;
import com.kingsware.kdev.sys.argv.SysConfigQueryArgv;
import com.kingsware.kdev.sys.model.SysConfig;
import com.kingsware.kdev.sys.model.SysUser;
import com.kingsware.kdev.sys.ret.SysConfigRet;
import com.kingsware.kdev.sys.service.SysConfigService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * 系统配置实现类
 *
 * @author crb
 * @version 1.0.0
 * @date 2022/1/13 16:35 上午
 */
@Service
public class SysConfigServiceImpl extends BaseServiceImpl implements SysConfigService {

    @Resource
    private AppAuthProperties appAuthProperties;

    @Override
    public SysConfigRet get(String id) {
        // 查询model
        SysConfig model = DB.findById(SysConfig.class, id);
        // 转换成ret对象
        return (SysConfigRet) model2Ret(model, SysConfigRet.class);
    }

    @Override
    public void add(SysConfigArgv argv) {
        SysConfig model = BeanUtils.copyObject(argv, SysConfig.class);
        // 校验
        checkUnique(model);
        // 保存
        DB.save(model);
    }

    @Override
    public void edit(SysConfigArgv argv) {
        SysConfig model = DB.findById(SysConfig.class, argv.getId());
        model.setName(argv.getName());
        model.setCode(argv.getCode());
        model.setValue(argv.getValue());
        model.setIsSys(argv.getIsSys());
        model.setNote(argv.getNote());
        // 校验
        checkUnique(model);
        // 保存
        DB.update(model);
    }

    private void checkUnique(SysConfig model) {
        // 唯一性校验
        DBChecker<SysConfig> checker =DBChecker.build(model, SysConfig.class);
        // 参数名称唯一
        checker.uni(new String[]{"name", "appId"}, I18n.t("SysConfig.name.unique", "参数名称必须唯一"));
        // 参数键名唯一
        checker.uni(new String[]{"code", "appId"}, I18n.t("SysConfig.code.unique", "参数键名必须唯一"));
        // 执行校验
        checker.checkUnique();
    }

    @Override
    @SuppressWarnings("unchecked")
    public PageDataRet<SysConfigRet> query(SysConfigQueryArgv argv) {
        // 拼装sql
        SqlWrapper wrapper = new SqlWrapper("select * from sys_config where 1=1 ");
        // 拼装查询sql
        if (StringUtils.isNotEmpty(argv.getName())) {
            wrapper.addCondition("name", Op.LIKE, "%" +argv.getName() +"%");
        }
        if (StringUtils.isNotEmpty(argv.getCode())) {
            wrapper.addCondition("code", Op.LIKE, "%" +argv.getCode() +"%");
        }
        if (argv.getIsSys()!=null) {
            wrapper.addCondition("is_sys", Op.EQ, argv.getIsSys());
        }
        if (StringUtils.isNotEmpty(argv.getAppId())) {
            wrapper.appendSql(" and (app_id = ?)", argv.getAppId());
        }
        wrapper.sortBy("when_created desc");
        return (PageDataRet<SysConfigRet>) query(wrapper.getSql(), wrapper.getParams(), argv, SysConfig.class, SysConfigRet.class);
    }

    @Override
    public void delete(MultiIdArgv argv) {
        for (String id: argv.getIds()) {
            DB.delete(SysConfig.class, id);
        }
    }

    @Override
    /**
     * 代码码版本已低于逻辑编排版，>2.0.0版本不会进入这里，应该从导出系统数据去使用
     */
    public Map<String, Object> getSysConfig(HttpServletRequest request) {
        String ip = ServletUtil.getClientIp(request);
        String token = TokenUtil.getTokenString(request);
        //BaseUserInfo userInfo = TokenUtil.getUserInfoByToken(token, appAuthProperties.getTokenSecret(), appAuthProperties.getIss(), ip, appAuthProperties.getTokenExpireMinutes(), appAuthProperties.getMockSessionExpireMinutes());

        List<Object> codeList = new ArrayList<>();
        codeList.add("application.name");
        codeList.add("application.logo");
        codeList.add("application.version");
        // 拼装sql
        SqlWrapper wrapper = new SqlWrapper("select * from sys_config where 1=1 ");
//        wrapper.in("code", codeList);
        if (KClientContext.getContext().getUserInfo() != null) {
            wrapper.addCondition("code", Op.LIKE, "application.%");
        }
        if (KFlowContext.isDevMode()) {
            wrapper.appendSql(" and app_id is null");
        }
        Map<String, Object> resultMap = new HashMap<>();
        resultMap.put("app.loginBySM2", SpringContext.getProperties("app.loginBySM2", PropertiesConstant.FALSE));
        List<SysConfigRet> configList = DB.findList(SysConfigRet.class, wrapper.getSql(), wrapper.getParams().toArray());
        if (configList != null) {
            for (SysConfigRet sysConfigRet : configList) {
                resultMap.put(sysConfigRet.getCode().replace("application.", ""), sysConfigRet.getValue());
            }
        }
        return resultMap;
    }

}

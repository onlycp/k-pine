package com.kingsware.kdev.sys.service.impl;

import com.kingsware.kdev.core.auth.AppAuthProperties;
import com.kingsware.kdev.core.auth.BaseUserInfo;
import com.kingsware.kdev.core.auth.TokenUtil;
import com.kingsware.kdev.core.base.BaseServiceImpl;
import com.kingsware.kdev.core.bean.MultiIdArgv;
import com.kingsware.kdev.core.bean.PageDataRet;
import com.kingsware.kdev.core.exception.BusinessException;
import com.kingsware.kdev.core.orm.DB;
import com.kingsware.kdev.core.orm.SqlWrapper;
import com.kingsware.kdev.core.orm.expression.Op;
import com.kingsware.kdev.core.util.BeanUtils;
import com.kingsware.kdev.core.util.DateUtils;
import com.kingsware.kdev.core.util.StringUtils;
import com.kingsware.kdev.sys.argv.SysNoticeArgv;
import com.kingsware.kdev.sys.argv.SysNoticeQueryArgv;
import com.kingsware.kdev.sys.argv.SysNoticeRelationArgv;
import com.kingsware.kdev.sys.model.SysNotice;
import com.kingsware.kdev.sys.model.SysNoticeRecord;
import com.kingsware.kdev.sys.model.SysUser;
import com.kingsware.kdev.sys.ret.SysNoticeRet;
import com.kingsware.kdev.sys.service.SysNoticeService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.sql.Timestamp;
import java.util.Set;

/**
 * @author: crb
 * @version: 1.0.0
 * @date: 2022/01/20/11:36
 * @description:
 */
@Service
public class SysNoticeServiceImpl extends BaseServiceImpl implements SysNoticeService {
    @Resource
    private AppAuthProperties appAuthProperties;

    @Override
    public SysNoticeRet get(String id) {
        // 查询model
        SysNotice model = DB.findById(SysNotice.class, id);
        // 转换成ret对象
        return (SysNoticeRet) model2Ret(model, SysNoticeRet.class);
    }

    @Override
    public void add(SysNoticeArgv argv) {
        SysNotice model = BeanUtils.copyObject(argv, SysNotice.class);
        // 保存
        DB.save(model);
    }

    @Override
    public void edit(SysNoticeArgv argv) {
        SysNotice model = DB.findById(SysNotice.class, argv.getId());
        model.setTitle(argv.getTitle());
        model.setContent(argv.getContent());
        model.setType(argv.getType());
        model.setStatus(argv.getStatus());
        // 保存
        DB.update(model);
    }

    @Override
    @SuppressWarnings("unchecked")
    public PageDataRet<SysNoticeRet> query(SysNoticeQueryArgv argv) {
        // 拼装sql
        SqlWrapper wrapper = new SqlWrapper("select sn.*,(select count(notice_id) from sys_notice_record where notice_id = sn.id) as sends from sys_notice sn where 1=1 and sn.deleted = 0");
        // 拼装查询sql
        if (StringUtils.isNotEmpty(argv.getTitle())) {
            wrapper.addCondition("title", Op.LIKE, "%" +argv.getTitle() +"%");
        }
        if (StringUtils.isNotEmpty(argv.getContent())) {
            wrapper.addCondition("content", Op.LIKE, "%" +argv.getContent() +"%");
        }
        if (argv.getType()!=null) {
            wrapper.addCondition("type", Op.EQ, argv.getType());
        }
        wrapper.sortBy(" order by when_created desc ");
        return (PageDataRet<SysNoticeRet>) query(wrapper.getSql(), wrapper.getParams(), argv,SysNoticeRet.class);
    }

    @Override
    public void delete(MultiIdArgv argv) {
        for (String id: argv.getIds()) {
            DB.delete(SysNotice.class, id);
        }
    }

    @Override
    public void sendNotice(SysNoticeRelationArgv argv, String token, String ip) {
        BaseUserInfo userInfo = TokenUtil.getUserInfoByToken(token, appAuthProperties.getTokenSecret(), appAuthProperties.getIss(), ip, appAuthProperties.getTokenExpireMinutes());
        SysUser model = DB.findById(SysUser.class, userInfo.getId());
        if (model == null) {
            throw BusinessException.serviceThrow("登录凭证已失效，请重新登录！");
        }
        SysNotice modelNotice = DB.findById(SysNotice.class, argv.getNoticeId());
        Set<String> relationIds = argv.getRelationIds();
        for (String relationId : relationIds) {
            SysNoticeRecord modelRecord = BeanUtils.copyObject(argv, SysNoticeRecord.class);
            modelRecord.setFromWho(userInfo.getId());
            modelRecord.setFromWhoName(userInfo.getRealName());
            SysUser modelUser = DB.findById(SysUser.class, relationId);
            modelRecord.setToWho(relationId);
            modelRecord.setToWhoName(modelUser.getRealName());
            modelRecord.setNoticeId(argv.getNoticeId());
            modelRecord.setIsRead(0);
            modelRecord.setNoticeTime(Timestamp.valueOf(DateUtils.getNow()));
            modelRecord.setTitle(modelNotice.getTitle());
            modelRecord.setContent(modelNotice.getContent());
            // 保存
            DB.save(modelRecord);
        }


    }

}

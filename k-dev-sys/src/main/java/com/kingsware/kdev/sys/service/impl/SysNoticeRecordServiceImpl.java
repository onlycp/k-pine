package com.kingsware.kdev.sys.service.impl;

import com.kingsware.kdev.core.base.BaseServiceImpl;
import com.kingsware.kdev.core.bean.MultiIdArgv;
import com.kingsware.kdev.core.bean.PageDataRet;
import com.kingsware.kdev.core.context.KClientContext;
import com.kingsware.kdev.core.orm.DB;
import com.kingsware.kdev.core.orm.SqlWrapper;
import com.kingsware.kdev.core.orm.expression.Op;
import com.kingsware.kdev.core.util.DateUtils;
import com.kingsware.kdev.core.util.StringUtils;
import com.kingsware.kdev.sys.argv.SysNoticeRecordQueryArgv;
import com.kingsware.kdev.core.model.SysNoticeRecord;
import com.kingsware.kdev.sys.ret.SysNoticeRecordRet;
import com.kingsware.kdev.sys.service.SysNoticeRecordService;
import org.springframework.stereotype.Service;

/**
 * @author: crb
 * @version: 1.0.0
 * @date: 2022/01/20/11:36
 * @description:
 */
@Service
public class SysNoticeRecordServiceImpl extends BaseServiceImpl implements SysNoticeRecordService {
    @Override
    public SysNoticeRecordRet get(String id) {
        // 查询model
        SysNoticeRecord model = DB.findById(SysNoticeRecord.class, id);
        // 转换成ret对象
        return (SysNoticeRecordRet) model2Ret(model, SysNoticeRecordRet.class);
    }

    @Override
    @SuppressWarnings("unchecked")
    public PageDataRet<SysNoticeRecordRet> query(SysNoticeRecordQueryArgv argv) {
        // 拼装sql
        SqlWrapper wrapper = new SqlWrapper("select * from sys_notice_record where 1=1");
        // 拼装查询sql
        if (StringUtils.isNotEmpty(argv.getFromWhoName())) {
            wrapper.addCondition("from_who_name", Op.EQ, argv.getFromWhoName());
        }
        if (StringUtils.isNotEmpty(argv.getFromWho())) {
            wrapper.addCondition("from_who", Op.EQ, argv.getFromWho());
        }
        if (StringUtils.isNotEmpty(argv.getToWhoName())) {
            wrapper.addCondition("to_who_name", Op.EQ, argv.getToWhoName());
        }
        if (StringUtils.isNotEmpty(argv.getToWho())) {
            wrapper.addCondition("to_who", Op.EQ, argv.getToWho());
        }
        if (StringUtils.isNotEmpty(argv.getTitle())) {
            wrapper.addCondition("title", Op.LIKE, "%" +argv.getTitle() +"%");
        }
        if (StringUtils.isNotEmpty(argv.getContent())) {
            wrapper.addCondition("content", Op.LIKE, "%" +argv.getContent() +"%");
        }
        if (StringUtils.isNotEmpty(argv.getNoticeId())) {
            wrapper.addCondition("notice_id", Op.EQ, argv.getNoticeId());
        }
        if (StringUtils.isNotEmpty(argv.getAppId())) {
            wrapper.appendSql(" and (app_id = ?)", argv.getAppId());
        }
        if (argv.getIsRead() != null) {
            wrapper.addCondition("is_read", Op.EQ, argv.getIsRead());
        }
        wrapper.sortBy(" order by notice_time desc ");
        return (PageDataRet<SysNoticeRecordRet>) query(wrapper.getSql(), wrapper.getParams(), argv,SysNoticeRecord.class, SysNoticeRecordRet.class);
    }

    @Override
    public void delete(MultiIdArgv argv) {
        for (String id: argv.getIds()) {
            DB.delete(SysNoticeRecord.class, id);
        }
    }

    @Override
    public PageDataRet<SysNoticeRecordRet> myUnRead() {
        SysNoticeRecordQueryArgv queryArgv = new SysNoticeRecordQueryArgv();
        queryArgv.setPageQuery(true);
        queryArgv.setPage(1);
        queryArgv.setPageSize(5);
        queryArgv.setToWho(KClientContext.getContext().getUserInfo().getId());
        queryArgv.setIsRead(0);
        // 拼装sql
        return this.query(queryArgv);
    }

    @Override
    public void willReadAll() {
        String sql = "update sys_notice_record set is_read=1, read_time=? where is_read=0 and to_who=?";
        DB.executeUpdateSql(sql, DateUtils.getNow(), KClientContext.getContext().getUserInfo().getId());
    }

}

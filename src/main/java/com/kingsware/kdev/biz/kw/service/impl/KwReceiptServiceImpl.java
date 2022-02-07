package com.kingsware.kdev.biz.kw.service.impl;

import com.kingsware.kdev.biz.kw.argv.KwReceiptArgv;
import com.kingsware.kdev.biz.kw.argv.KwReceiptQueryArgv;
import com.kingsware.kdev.biz.kw.argv.KwWaterArgv;
import com.kingsware.kdev.biz.kw.argv.KwWaterQueryArgv;
import com.kingsware.kdev.biz.kw.model.KwReceipt;
import com.kingsware.kdev.biz.kw.model.KwWater;
import com.kingsware.kdev.biz.kw.ret.KwReceiptRet;
import com.kingsware.kdev.biz.kw.ret.KwWaterRet;
import com.kingsware.kdev.biz.kw.service.KwReceiptService;
import com.kingsware.kdev.biz.kw.service.KwWaterService;
import com.kingsware.kdev.core.base.BaseServiceImpl;
import com.kingsware.kdev.core.bean.BaseSimpleRet;
import com.kingsware.kdev.core.bean.PageDataRet;
import com.kingsware.kdev.core.excel.ExcelWorker;
import com.kingsware.kdev.core.excel.KExcel;
import com.kingsware.kdev.core.excel.RegionDefine;
import com.kingsware.kdev.core.orm.DB;
import com.kingsware.kdev.core.orm.SqlWrapper;
import com.kingsware.kdev.core.orm.expression.Op;
import com.kingsware.kdev.core.util.BeanUtils;
import com.kingsware.kdev.core.util.StringUtils;
import org.springframework.stereotype.Service;

import javax.print.attribute.standard.RequestingUserName;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


@Service
public class KwReceiptServiceImpl extends BaseServiceImpl implements KwReceiptService {


    @Override
    public KwWaterRet get(String id) {
        return null;
    }

    @Override
    public String add(KwReceiptArgv argv) {
        KwReceipt model = BeanUtils.copyObject(argv, KwReceipt.class);
        DB.save(model);
        return model.getId();
    }

    @Override
    public void edit(KwReceiptArgv argv) {
        if (StringUtils.isEmpty(argv.getId()))
            throw new RuntimeException("编辑回单ID不存在");
        KwReceipt model = DB.findById(KwReceipt.class, argv.getId().trim());

        if (argv.getHasWater() != null) // 有无流水
            model.setHasWater(argv.getHasWater());

        if (StringUtils.isNotEmpty(argv.getOtherData())) // 无流水的处理意见
            model.setOtherData(argv.getOtherData());

        DB.update(model);

    }

    /**
     * 回单分页查询
     *
     * @param argv 查询
     * @return
     */
    @Override
    public PageDataRet<KwReceiptRet> query(KwReceiptQueryArgv argv) {
//   String sql = "SELECT kba.bank_deposit, kea.bank_account as edition_account, kbae.pro_name," +
//                " km.bank_name as mechanism_name, km.id as mechanism_id, ke.name as edition_name,ke.id as edition_id, " +
//                " kba.id as bank_account_id, kw.id as water_id, " +
//                " kr.* " +
//                " FROM kw_receipt kr " +
//                " LEFT JOIN sys_file kf on kf.id = kr.file_id " + //todo sys_file 没有软删除字段
//                " LEFT JOIN kw_water kw on kw.receipt_id = kr.id and kw.deleted = 0 " +
//                " LEFT JOIN kw_bank_account kba on kba.account = kr.self_account and kba.deleted = 0 " +
//                " LEFT JOIN kw_bank_account_expand kbae on kba.account = kbae.account " + //todo kbae没有软删除字段
//                " LEFT JOIN kw_edition ke on kba.edition_id = ke.id and ke.deleted = 0 " +
//                " LEFT JOIN kw_edition_account kea on kea.id = kba.edition_account_id and kea.deleted = 0 " +
//                " LEFT JOIN kw_mechanism km on ke.mechanism_id = km.id and km.deleted = 0 " +
//                " where kr.deleted = 0 ";
        StringBuffer sbsql = new StringBuffer(" SELECT kba.bank_deposit, kea.bank_account as edition_account, kbae.pro_name, ");
        sbsql.append(" km.bank_name as mechanism_name, km.id as mechanism_id, ke.name as edition_name,ke.id as edition_id, ");
        sbsql.append(" kba.id as bank_account_id, kw.id as water_id, ");
        sbsql.append(" kr.* ");
        sbsql.append(" FROM kw_receipt kr ");
        sbsql.append(" LEFT JOIN sys_file kf on kf.id = kr.file_id ");
        sbsql.append(" LEFT JOIN kw_water kw on kw.receipt_id = kr.id and kw.deleted = 0 ");
        sbsql.append(" LEFT JOIN kw_bank_account kba on kba.account = kr.self_account and kba.deleted = 0 ");
        sbsql.append(" LEFT JOIN kw_bank_account_expand kbae on kbae.account = kr.self_account ");
        sbsql.append(" LEFT JOIN kw_edition ke on kba.edition_id = ke.id and ke.deleted = 0 ");
        sbsql.append(" LEFT JOIN kw_edition_account kea on kea.id = kba.edition_account_id and kea.deleted = 0 ");
        sbsql.append(" LEFT JOIN kw_mechanism km on ke.mechanism_id = km.id and km.deleted = 0 ");
        sbsql.append(" where kr.deleted = 0 ");

        SqlWrapper wrapper = new SqlWrapper(sbsql.toString());

        if (StringUtils.isNotEmpty(argv.getEditionId())) {
            wrapper.addCondition("ke.id", Op.EQ, argv.getEditionId());
        }
        if (StringUtils.isNotEmpty(argv.getEditionName())) {
            wrapper.addCondition("ke.name", Op.LIKE, "%" + argv.getEditionName() + "%");
        }
        if (StringUtils.isNotEmpty(argv.getAccount())) {
            wrapper.addCondition("kr.self_account", Op.LIKE, "%" + argv.getAccount() + "%");
        }
        if (StringUtils.isNotEmpty(argv.getStartDate())) {
            wrapper.addCondition("kr.book_date", Op.BETWEEN, argv.getStartDate(), argv.getEndDate());
        }
        if (argv.getIds() != null) {
            wrapper.in("kr.id", Arrays.asList(argv.getIds().split(",")));
        }

        // 数据权限
        wrapper.withAuthority("kw_bank_account", "kba");
        // 排序
        wrapper.sortBy("ORDER BY kr.book_date desc");
        // 执行查询
        PageDataRet<? extends BaseSimpleRet> query = query(wrapper.getSql(), wrapper.getParams(), argv, KwReceiptRet.class);

        return (PageDataRet<KwReceiptRet>) query;
    }


    @Override
    public void export(KwReceiptQueryArgv argv) {

        // 直接调用查询方法
        argv.setPageQuery(false);
        PageDataRet<KwReceiptRet> pageDataRet = this.query(argv);


        // 定义标题
        List<RegionDefine> defineList = new ArrayList<>();
        defineList.add(RegionDefine.textDefine("proName", "项目名称"));
        defineList.add(RegionDefine.textDefine("bankDeposit", "开户行"));
        defineList.add(RegionDefine.dateTimeDefine("bookDate", "交易日期"));
        defineList.add(RegionDefine.textDefine("draweeName", "付款人名称"));
        defineList.add(RegionDefine.textDefine("draweeAccountNumber", "付款人账户"));
        defineList.add(RegionDefine.textDefine("payeeName", "收款人名称"));
        defineList.add(RegionDefine.textDefine("payeeAccountNumber", "收款人账户"));
        defineList.add(RegionDefine.textDefine("amount", "交易金额"));
        defineList.add(RegionDefine.dateDefine("hasWater", "有无流水", "kw_receipt_has_water"));
        defineList.add(RegionDefine.textDefine("selfAccount", "归属账户"));

        // 导出
        KExcel kExcel = KExcel.fromDataList("回单查询.xls", "Sheet1", defineList, pageDataRet.getList());
        ExcelWorker.getInstance().writeToWeb(kExcel);
    }
}

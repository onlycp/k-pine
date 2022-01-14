package com.kingsware.kdev.biz.kw.service.impl;

import com.kingsware.kdev.biz.kw.argv.KwWaterQueryArgv;
import com.kingsware.kdev.biz.kw.model.KwWater;
import com.kingsware.kdev.biz.kw.ret.KwWaterRet;
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

import java.util.*;


@Service
public class KwWaterServiceImpl extends BaseServiceImpl implements KwWaterService {

    /**
     * 流水详情
     * @param id    id
     * @return
     */
    @Override
    public KwWaterRet get(String id) {
        // 查询model
        KwWater model = DB.findById(KwWater.class, id);
        // 转换成ret对象
        return (KwWaterRet) model2Ret(model, KwWaterRet.class);
    }

    /**
     * （暂时保留）
     * @param argv 新增
     */
    @Override
    public void add(KwWaterQueryArgv argv) {
    }

    /**
     * 校验唯一性
     * @param model 模型
     */
    private void checkUnique(KwWater model) {
    }

    /**
     * 流水分页查询
     * @param argv 编辑
     * @return
     */
    @Override
    @SuppressWarnings("unchecked")
    public PageDataRet<KwWaterRet> query(KwWaterQueryArgv argv) {
        // 基础sql
        SqlWrapper wrapper = new SqlWrapper(" SELECT kba.bank_deposit, kea.bank_account as edition_account, kbae.pro_name, km.bank_name as mechanism_name, ke.name as edition_name, " +
                " kw.* FROM kw_water kw " +
                " LEFT JOIN kw_receipt kr on kw.receipt_id = kr.id" +
                " LEFT JOIN kw_bank_account kba on kw.account = kba.account " +
                " LEFT JOIN kw_bank_account_expand kbae on kba.account = kbae.account " +
                " LEFT JOIN kw_edition ke on kba.edition_id = ke.id " +
                " LEFT JOIN kw_edition_account kea on kea.edition_id = ke.id " +
                " LEFT JOIN kw_mechanism km on ke.mechanism_id = km.id " +
                " where kba.deleted = 0 " +
                " and ke.deleted = 0 ");

        // 拼装查询sql,并注入参数
        if (argv.getEditionId() != null) {
            wrapper.addCondition("kba.edition_id", Op.EQ, argv.getEditionId());
        }
        if (argv.getEditionName()!=null&&StringUtils.isNotEmpty(argv.getEditionName())) {
            wrapper.addCondition("ke.name", Op.LIKE, "%"+argv.getEditionName() +"%");
        }
        if (argv.getAccount()!=null&&StringUtils.isNotEmpty(argv.getAccount())) {
            wrapper.addCondition("kw.account", Op.LIKE, "%"+argv.getAccount() +"%");
        }
        if (argv.getStartDate()!=null&&StringUtils.isNotEmpty(argv.getStartDate())) {
            wrapper.addCondition("kw.transaction_date", Op.BETWEEN, argv.getStartDate(),argv.getEndDate());
        }
        if (argv.getIds() != null) {
            wrapper.in("kw.id", Arrays.asList(argv.getIds().split(",")));
        }
        // 数据权限
        wrapper.withAuthority("kw_bank_account", "kba");
        // 排序
        wrapper.sortBy("ORDER BY kw.transaction_date desc,date_index desc");
        // 执行查询
        PageDataRet<? extends BaseSimpleRet> query = query(wrapper.getSql(), wrapper.getParams(), argv, KwWaterRet.class);

        return (PageDataRet<KwWaterRet>) query;
    }

    @Override
    public void exportImportTemplate() {
        // 定义标题
        List<RegionDefine> defineList = new ArrayList<>();
        defineList.add(RegionDefine.builder().labelName("交易日期").example("2021-10-21").build());
        defineList.add(RegionDefine.builder().labelName("交易时间").example("00:58:14").build());
        defineList.add(RegionDefine.builder().labelName("交易类型").example("").build());
        defineList.add(RegionDefine.builder().labelName("账户").example("337060100100211315").build());
        defineList.add(RegionDefine.builder().labelName("户名").example("深圳同创伟业资产管理股份有限公司").build());
        defineList.add(RegionDefine.builder().labelName("本方行名").example("").build());
        defineList.add(RegionDefine.builder().labelName("收支方向").example("支出").build());
        defineList.add(RegionDefine.builder().labelName("交易金额").example("10.1").build());
        defineList.add(RegionDefine.builder().labelName("账户余额").example("10.1").build());
        defineList.add(RegionDefine.builder().labelName("对方账号").example("").build());
        defineList.add(RegionDefine.builder().labelName("对方名称").example("").build());
        defineList.add(RegionDefine.builder().labelName("对方行名").example("").build());
        defineList.add(RegionDefine.builder().labelName("用途").example("").build());
        defineList.add(RegionDefine.builder().labelName("摘要").example("贷款回收").build());
        defineList.add(RegionDefine.builder().labelName("流水号").example("12345").build());
        defineList.add(RegionDefine.builder().labelName("备注").example("").build());
        // 导出
        KExcel kExcel = KExcel.fromHeaderList("标准流水.xls", "Sheet1", defineList);
        ExcelWorker.getInstance().writeToWeb(kExcel);
    }

    @Override
    public void export(KwWaterQueryArgv argv) {
        // 直接调用查询方法
        argv.setPageQuery(false);
        PageDataRet<KwWaterRet> pageDataRet = this.query(argv);
        // 定义标题
        List<RegionDefine> defineList = new ArrayList<>();
        defineList.add(RegionDefine.dateDefine("transactionDate", "交易日期"));
        defineList.add(RegionDefine.timeDefine("transactionTime", "交易时间"));
        defineList.add(RegionDefine.textDefine("mechanismName","机构名称"));
        defineList.add(RegionDefine.textDefine("editionName", "版本名称"));
        defineList.add(RegionDefine.textDefine("account","账户"));
        defineList.add(RegionDefine.textDefine("accountName","账户名称"));
        defineList.add(RegionDefine.textDefine("proName","项目名称"));
        defineList.add(RegionDefine.textDefine("bankDeposit","开户行"));
        // 借
        defineList.add(RegionDefine.builder().propName("transactionAmount").labelName("借").format((value, model) -> {
            // 收支方向
            Integer revenue = (Integer) BeanUtils.getFieldValue("revenue", model);
            if (revenue != null) {
                if (revenue == 0) {
                    return value;
                }
                else if (revenue == 1) {
                    return "--";
                }
            }
            return "异常";

        }).build());
        // 贷
        defineList.add(RegionDefine.builder().propName("transactionAmount").labelName("贷").format((value, model) -> {
            // 收支方向
            Integer revenue = (Integer) BeanUtils.getFieldValue("revenue", model);
            if (revenue != null) {
                if (revenue == 0) {
                    return "--";
                }
                else if (revenue == 1) {
                    return value;
                }
            }
            return "异常";

        }).build());
        // 余额
        defineList.add(RegionDefine.textDefine("accountBalance", "余额"));
        // 来源
        defineList.add(RegionDefine.builder().propName("dataSource").labelName("数据来源").format((value, model) -> {
            if (value != null) {
                int intValue = Integer.parseInt(value.toString());
                if (intValue == 0) {
                    return "自动";
                }
                else if (intValue == 1) {
                    return "手动";
                }
            }
            return "异常";
        }).build());
        // 导出
        KExcel kExcel = KExcel.fromDataList("流水查询.xls", "Sheet1", defineList, pageDataRet.getList());
        ExcelWorker.getInstance().writeToWeb(kExcel);


    }

}

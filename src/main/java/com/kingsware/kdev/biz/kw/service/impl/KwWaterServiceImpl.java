package com.kingsware.kdev.biz.kw.service.impl;

import cn.hutool.poi.excel.ExcelReader;
import cn.hutool.poi.excel.ExcelUtil;
import com.kingsware.kdev.biz.kw.argv.KwWaterArgv;
import com.kingsware.kdev.biz.kw.argv.KwWaterQueryArgv;
import com.kingsware.kdev.biz.kw.exception.DuplicateReceiptWaterException;
import com.kingsware.kdev.biz.kw.exception.WrongFormatReceiptWaterException;
import com.kingsware.kdev.biz.kw.model.KwWater;
import com.kingsware.kdev.biz.kw.ret.KwWaterRet;
import com.kingsware.kdev.biz.kw.service.KwWaterService;
import com.kingsware.kdev.biz.kw.util.AmountUtil;
import com.kingsware.kdev.biz.kw.util.EnumSelectionUtil;
import com.kingsware.kdev.biz.kw.util.TimeUtil;
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
import com.kingsware.kdev.core.util.DateUtils;
import com.kingsware.kdev.core.util.StringUtils;
import org.springframework.stereotype.Service;


import java.io.File;
import java.sql.Timestamp;
import java.util.*;
import java.util.stream.Collectors;


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
    public void add(KwWaterArgv argv) {
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
        SqlWrapper wrapper = new SqlWrapper(" SELECT kba.bank_deposit, kea.bank_account as edition_account, kbae.pro_name, km.bank_name as mechanism_name, ke.name as edition_name,kr.file_id, " +
                " kw.* " +
                " FROM kw_water kw " +
                " LEFT JOIN kw_receipt kr on kw.receipt_id = kr.id and kr.deleted = 0 " +
                " LEFT JOIN kw_bank_account kba on kw.account = kba.account and kba.deleted = 0 " +
                " LEFT JOIN kw_bank_account_expand kbae on kba.account = kbae.account and kba.deleted = 0 " +
                " LEFT JOIN kw_edition ke on kba.edition_id = ke.id and ke.deleted = 0  " +
                " LEFT JOIN kw_edition_account kea on kea.id = kba.edition_account_id and kea.deleted = 0 " +
                " LEFT JOIN kw_mechanism km on ke.mechanism_id = km.id and km.deleted = 0  " +
                " where kw.deleted=0 ");

        // 拼装查询sql,并注入参数
        if (argv.getEditionId() != null && StringUtils.isNotEmpty(argv.getEditionName())) {
            wrapper.addCondition("kba.edition_id", Op.EQ, argv.getEditionId());
        }
        if (argv.getEditionName() != null && StringUtils.isNotEmpty(argv.getEditionName())) {
            wrapper.addCondition("ke.name", Op.LIKE, "%" + argv.getEditionName() + "%");
        }
        if (argv.getAccount() != null && StringUtils.isNotEmpty(argv.getAccount())) {
            wrapper.addCondition("kw.account", Op.LIKE, "%" + argv.getAccount() + "%");
        }
        if (argv.getStartDate() != null && StringUtils.isNotEmpty(argv.getStartDate())) {
            wrapper.addCondition("kw.transaction_date", Op.BETWEEN, argv.getStartDate(), argv.getEndDate());
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
        defineList.add(RegionDefine.textDefine("mechanismName", "机构名称"));
        defineList.add(RegionDefine.textDefine("editionName", "版本名称"));
        defineList.add(RegionDefine.textDefine("account", "账户"));
        defineList.add(RegionDefine.textDefine("accountName", "账户名称"));
        defineList.add(RegionDefine.textDefine("proName", "项目名称"));
        defineList.add(RegionDefine.textDefine("bankDeposit", "开户行"));
        // 借
        defineList.add(
                RegionDefine.builder().propName("transactionAmount").labelName("借").format((value, model) -> {
                    // 收支方向
                    Integer revenue = (Integer) BeanUtils.getFieldValue("revenue", model);
                    if (revenue != null) {
                        if (revenue == 0) {
                            return value;
                        } else if (revenue == 1) {
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
                } else if (revenue == 1) {
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
                } else if (intValue == 1) {
                    return "手动";
                }
            }
            return "异常";
        }).build());
        // 导出
        KExcel kExcel = KExcel.fromDataList("流水查询.xls", "Sheet1", defineList, pageDataRet.getList());
        ExcelWorker.getInstance().writeToWeb(kExcel);
    }

    /**
     * 流水导入和匹配业务：查询
     * @param account       账号
     * @param date          日期
     * @return              流水列表
     */
    @Override
    public List<KwWater> findByDateAndAccount(String account, String date){
        SqlWrapper wrapper = new SqlWrapper(" select * from kw_water kw where 1 = 1 ");
        wrapper.addCondition("kw.account", Op.EQ, account);
        wrapper.addCondition("kw.transaction_date", Op.EQ, date);
        List<KwWater> list = DB.findList(KwWater.class, wrapper.getSql(), wrapper.getParams().toArray());
        return list;
    }

    /**
     * 流水导入和匹配业务：批量插入新的流水
     * @param waterDtoList      流水列表
     * @return                  新增的流水列表
     */
    // TODO: 2022/1/12 事务注解没导包
    @Override
    //@Transactional
    public List<KwWater> batchUpdateNewWater(List<KwWater> waterDtoList){
        List<KwWater> newWaterEntityList = new ArrayList<>();
        for (KwWater waterDto: waterDtoList){
            newWaterEntityList.add(waterDto);
            DB.save(waterDto);
        }
        return newWaterEntityList;
    }

    /**
     * 流水导入和匹配业务：检测流水是否一样
     * @param waterDtoList          新的流水列表
     * @param waterEntityList       已存在的流水列表
     * @return                      是否一样
     */
    @Override
    public Boolean checkWaterMatch(List<KwWater> waterDtoList, List<KwWater> waterEntityList){
        if (waterEntityList.size() != waterDtoList.size()){
            return false;
        }

        for (int i=0;i<waterDtoList.size();i++){
            if(!isSameWater(waterDtoList.get(i), waterEntityList.get(i))){
                return false;
            }
        }

        return true;
    }

    /**
     * 流水导入和匹配业务：判断流水是否相同
     * @param waterDto      新流水信息
     * @param waterEntity   已存在流水信息
     * @return              是否相同
     */
    @Override
    public Boolean isSameWater(KwWater waterDto, KwWater waterEntity){
        Boolean result = false;
        result = waterDto.getDateIndex() == waterDto.getDateIndex();
        if (!org.apache.commons.lang3.StringUtils.isEmpty(waterDto.getSerialNumber()) && !org.apache.commons.lang3.StringUtils.isEmpty(waterEntity.getSerialNumber())){
            return result && waterDto.getSerialNumber().equals(waterEntity.getSerialNumber())
                    && waterDto.getAccount().equals(waterEntity.getAccount());
        }else {
            return result && waterDto.getTransactionDate().equals(waterEntity.getTransactionDate())
                    && waterDto.getTransactionAmount().equals(waterEntity.getTransactionAmount())
                    && waterDto.getAccount().equals(waterEntity.getAccount())
                    && waterDto.getRevenue() == waterEntity.getRevenue();
        }
    }

    /**
     * 流水导入和匹配业务：批量更新有差异的流水列表
     * @param waterDtoList          新的流水列表
     * @param waterEntityList       已存在的流水列表
     * @return                      更新后的流水列表
     */
    // TODO: 2022/1/12 事务注解没导包
    //@Transactional
    @Override
    public List<KwWater> batchUpdateDiffWater(List<KwWater> waterDtoList, List<KwWater> waterEntityList){
        List<KwWater> newWaterEntityList = new ArrayList<>();
        for (KwWater waterDto: waterDtoList){
            //流水号会重复
//            var currentWaterEntity = matchWaterWithSerNumber(waterDto, waterEntityList);
//            if (currentWaterEntity == null){
//                currentWaterEntity = matchWaterWithMultiColumns(waterDto, waterEntityList);
//            }
            KwWater currentWaterEntity = matchWaterWithMultiColumns(waterDto, waterEntityList);
            if (currentWaterEntity == null){
                newWaterEntityList.add(addNew(waterDto));
            }else {
                newWaterEntityList.add(update(waterDto, currentWaterEntity));
                waterEntityList.removeIf(w -> w.getId().equals(currentWaterEntity.getId()));
            }
        }
        return newWaterEntityList;
    }

    /**
     * 更新相同的流水列表
     * @param waterDtoList      新的流水列表
     * @param waterEntityList   已存在的流水列表
     * @return                  更新的流水列表
     */
    // TODO: 2022/1/12 事务注解没导包
    //@Transactional
    public List<KwWater> batchUpdateSameWater(List<KwWater> waterDtoList, List<KwWater> waterEntityList){
        List<KwWater> newWaterEntityList = new ArrayList<>();
        for (int i=0;i<waterDtoList.size();i++){
            KwWater waterEntity = waterDtoList.get(i);
            KwWater currentWaterEntity = waterEntityList.get(i);
            newWaterEntityList.add(update(waterEntity, currentWaterEntity));
        }
        return newWaterEntityList;
    }

    /**
     * 流水导入和匹配业务：使用多个字段匹配流水
     * @param waterDto              流水信息
     * @param waterEntityList       流水列表
     * @return                      流水信息
     */
    private KwWater matchWaterWithMultiColumns(KwWater waterDto, List<KwWater> waterEntityList){
        List<KwWater> list = waterEntityList.stream()
                .filter(w -> {
                    Boolean matched = w.getAccount().equals(waterDto.getAccount())
                            && w.getTransactionAmount().equals(waterDto.getTransactionAmount())
                            && w.getRevenue() == waterDto.getRevenue()
                            && w.getTransactionDate().equals(waterDto.getTransactionDate());
                    if (w.getTransactionTime() != null && waterDto.getTransactionTime() != null){
                        matched = matched && w.getTransactionTime().equals(waterDto.getTransactionTime());
                    }else {
                        matched = matched && w.getTransactionTime() == w.getTransactionTime();
                    }
                    return matched;
                }).collect(Collectors.toList());
        if (list.size() == 1){
            return list.get(0);
        }

        list = waterEntityList.stream()
                .filter(w -> {
                    return w.getAccount().equals(waterDto.getAccount())
                            && w.getTransactionAmount().equals(waterDto.getTransactionAmount())
                            && w.getRevenue() == waterDto.getRevenue()
                            && w.getDateIndex() == waterDto.getDateIndex();
                }).collect(Collectors.toList());
        if (list.size() > 1){
            throw new DuplicateReceiptWaterException("有重复的流水记录 " + waterDto.toString());
        }
        if (list.size() == 1){
            return list.get(0);
        }
        return null;
    }


    /**
     * 流水导入和匹配业务：添加流水
     * @param waterEntity       流水信息
     * @return                  新增的流水信息
     */
    // TODO: 2022/1/12 事务注解没导包
    //@Transactional
    public KwWater addNew(KwWater waterEntity){
        waterEntity.setRegisterTime(new Timestamp(new Date().getTime()));
        waterEntity.setReceiptId(null);
        waterEntity.setHasReceipt(0);
        waterEntity.setAbnormalStatus(0);
        waterEntity.setAbnormal(0);
        DB.save(waterEntity);
        return waterEntity;
    }

    /**
     * 更新流水信息
     * @param waterEntity           新的流水信息
     * @param currentWaterEntity    已存在的流水信息
     * @return                      更新的流水
     */
    // TODO: 2022/1/12 事务注解没导包
    //@Transactional
    public KwWater update(KwWater waterEntity, KwWater currentWaterEntity){
        waterEntity.setReceiptId(currentWaterEntity.getReceiptId());
        waterEntity.setHasReceipt(currentWaterEntity.getHasReceipt());
        waterEntity.setAbnormal(currentWaterEntity.getAbnormal());
        waterEntity.setAbnormalStatus(currentWaterEntity.getAbnormalStatus());
        waterEntity.setRegisterTime(new Timestamp(new Date().getTime()));
        waterEntity.setId(currentWaterEntity.getId());
        DB.update(waterEntity);
        return waterEntity;
    }

    /**
     * @author:  amzc
     * @methodsName: excel2WaterDto
     * @description: excel数据转流水单数据
     * @param:  MultipartFile file
     * @return: List<WaterDto>
     * @throws:
     */
    @Override
    public List<KwWater> excel2WaterDto(File file){
        if (file==null)return new ArrayList<KwWater>();
        List<Map<String,Object>> readAllMap = null;
        try(ExcelReader reader = ExcelUtil.getReader(file)){
            readAllMap = reader.readAll();
        }

        List<KwWater> res = new ArrayList<>();

        for (Map<String, Object> map : readAllMap) {
            KwWater waterDto = new KwWater();

            Object transactionAmount = Optional.ofNullable(map.get("交易金额")).orElse("");
            String standardTransactiondAmount = AmountUtil.standardAmount(transactionAmount);
            if (org.apache.commons.lang3.StringUtils.isEmpty(standardTransactiondAmount)){
                throw new WrongFormatReceiptWaterException("错误的流水交易金额格式：" + transactionAmount);
            }
            Object balance = Optional.ofNullable(map.get("账户余额")).orElse("");
            String standardBalance = AmountUtil.standardAmount(balance);

            String account = org.apache.commons.lang3.StringUtils.trim(Optional.ofNullable(map.get("账户")).orElse("").toString());
            if (org.apache.commons.lang3.StringUtils.isBlank(account)){
                throw new WrongFormatReceiptWaterException("本方账号不能为空：");
            }
            String otherAccount = org.apache.commons.lang3.StringUtils.trim(Optional.ofNullable(map.get("对方账号")).orElse("").toString());

            String strDate = Optional.ofNullable(map.get("交易日期")).orElse("").toString();
            if (org.apache.commons.lang3.StringUtils.isBlank(strDate)){
                throw new WrongFormatReceiptWaterException("交易日期不能为空");
            }

            String strRevenue = Optional.ofNullable(map.get("收支方向")).orElse("").toString();
            if (org.apache.commons.lang3.StringUtils.isBlank(strRevenue)){
                throw new WrongFormatReceiptWaterException("收支方向不能为空");
            }

            waterDto.setTransactionDate(new Timestamp(TimeUtil.strToDate(strDate).getTime()));//交易日期  非空
            String dateString = String.valueOf(map.get("交易日期"));
            String timeString = String.valueOf(map.get("交易时间"));
            if(map.get("交易时间") != null && !map.get("交易时间").equals("")
                && dateString != null && !"".equals(dateString)){
                String concatDatetime = dateString + " " + timeString;
                Date transactionTime = DateUtils.toDate(concatDatetime, "yyyy-MM-dd HH:mm:ss");
                waterDto.setTransactionTime(new Timestamp(transactionTime.getTime()));//交易时间  可为空
            }
            waterDto.setTransactionType(Optional.ofNullable(map.get("交易类型")).orElse("").toString());//交易类型      可为空
            waterDto.setCurrency(EnumSelectionUtil.getCurrency(Optional.ofNullable(map.get("币种")).orElse("").toString()));//币种 非空
            waterDto.setAccount(account);//账户  非空
            waterDto.setAccountName(Optional.ofNullable(map.get("户名")).orElse("").toString());//户名  可为空
            waterDto.setRevenue(EnumSelectionUtil.getRevenue(strRevenue));//收支方向   非空
            waterDto.setTransactionAmount(standardTransactiondAmount);//交易金额   可为空
            waterDto.setAccountBalance(standardBalance);// 账户余额  非空
            waterDto.setOtherAccount(otherAccount);// 对方帐号  非空
            waterDto.setOtherName(Optional.ofNullable(map.get("对方名称")).orElse("").toString());// 对方名称   非空
            waterDto.setOtherBankName(Optional.ofNullable(map.get("对方行名")).orElse("").toString());//对方行名  可为空
            waterDto.setPurpose(Optional.ofNullable(map.get("用途")).orElse("").toString());// 用途  可为空
            waterDto.setAbstractInfo(Optional.ofNullable(map.get("摘要")).orElse("").toString());// 摘要   可为空
            waterDto.setSerialNumber(Optional.ofNullable(map.get("流水号")).orElse("").toString()); // 流水号  可为空
            waterDto.setRemark(Optional.ofNullable(map.get("备注")).orElse("").toString());// 备注  可为空
            waterDto.setRegisterTime(new Timestamp(new Date().getTime())); //插入时间
            //todo 暂时设默认值，后续根据业务调整
            waterDto.setDeleted(0); //逻辑删除
            waterDto.setStatus(0); //数据状态
            waterDto.setDataSource(0); //数据来源
            waterDto.setAbnormal(0); //是否异常
            waterDto.setAbnormalStatus(0); //异常状态
            waterDto.setCashTransfer(0); //收支方式
            res.add(waterDto);
        }
        if (TimeUtil.hasWaterTime(res)){
            res.sort((w1, w2) -> (int) (w1.getTransactionTime().getTime() - w2.getTransactionTime().getTime()));
        }
        int index = 1;
        for (KwWater water : res){
            water.setDateIndex(index * 100);
            index++;
        }
        return res;
    }

}

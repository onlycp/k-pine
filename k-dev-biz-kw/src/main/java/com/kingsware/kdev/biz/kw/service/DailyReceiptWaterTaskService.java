package com.kingsware.kdev.biz.kw.service;

import cn.hutool.core.lang.Tuple;
import com.kingsware.kdev.biz.kw.exception.WrongFormatReceiptWaterException;
import com.kingsware.kdev.biz.kw.model.KwReceipt;
import com.kingsware.kdev.biz.kw.model.KwWater;
import com.kingsware.kdev.biz.kw.service.impl.KwWaterServiceImpl;
import com.kingsware.kdev.biz.kw.util.TimeUtil;
import com.kingsware.kdev.core.orm.DB;
import org.apache.juli.logging.Log;
import org.apache.juli.logging.LogFactory;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;;
import java.nio.file.Paths;
import java.sql.Timestamp;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * 回单流水录入业务逻辑
 *
 * @author amzc
 * @version 1.00
 * @Date 2022/1/14
 */
@Service
public class DailyReceiptWaterTaskService {
    private Log logger = LogFactory.getLog(this.getClass());

    //@Resource
    KwWaterService waterService = new KwWaterServiceImpl();

    /**
     * 构造昨天的目录路径
     * @param path
     * @return
     */
    public  String getYearAndMountAndDayPath(String path){
        //       E:\同创伟业RPA\流水及回单\日流水及回单\
        //       2020年\2020年09月银行流水及回单\2020年09月21日银行流水及回单
        //       \6119梦工场\华兴银行(805880100041458)\6119梦工场-2020年09月21日-华兴银行(805880100041458).xlsx
        //2020年
        String nowYearStr = TimeUtil.getEndDayOfYesterdayIsString().substring(0,5);
        //2020年12月银行流水及回单
        String yearAndMount=TimeUtil.getEndDayOfYesterdayIsString().substring(0,8)+"银行流水及回单";
        //2020年12月28日银行流水及回单
        String yearAndMountAndDay= TimeUtil.getEndDayOfYesterdayIsString()+"银行流水及回单";
        //D:\同创伟业RPA\流水及回单\日流水及回单/2020年/2020年12月银行流水及回单/2020年12月28日银行流水及回单
        String newPath= Paths.get(path).resolve(nowYearStr+"/"+yearAndMount+"/"+yearAndMountAndDay).normalize().toAbsolutePath().toString();
        return newPath;
    }

    /**
     * 目录是否存在
     * @param path      目录路径
     * @return          目录对象
     */
    public File getExistDir(String path){
        File file = new File(path);
        if (file.exists() && file.isDirectory()){
            return file;
        }
        return null;
    }

    /**
     * 是否是目录
     * @param path      目录路径
     * @return          是否是目录
     */
    public Boolean IsDir(String path){
        //获取目录返回目录
        File f = new File(path);
        return f.exists() && f.isDirectory();
    }

    /**
     * 递归获取所有包含回单和流水文件的目录
     * @param file          上级目录
     * @param allFile       有回单流水文件的目录列表
     */
    public void recursiveAcquisitionReceiptAndWater(File file, List<File> allFile){
        File[] fs = file.listFiles();
        boolean existedReceipt = false;
        boolean existedWater = false;
        for(File f:fs){
            if(f.isDirectory())
                //若是目录，则递归打印该目录下的文件
                recursiveAcquisitionReceiptAndWater(f,allFile);
            if(f.isFile()&&f.getName().contains("xlsx")){//若是excel文件，这目录下存在回单excel和流水excel
                if (f.getName().contains("回单")){
                    existedReceipt = true;
                }else if (f.getName().contains("流水")){
                    existedWater = true;
                }

                //将这个父目录添加到allFile,再统一处理
                if (existedReceipt && existedWater){
                    allFile.add(f.getParentFile());
                    //直接返回，不需要再遍历后续文件
                    return;
                }
            }
        }
    }


    /**
     * 获取所有流水标准文件
     * @param allFile       回单流水目录列表
     * @return              流水文件列表
     */
    private List<File> getAllWaterExcel(List<File> allFile) {
        List<File> allWaterExcel = new ArrayList<>();
        allFile.forEach(c->{
            File[] fs = c.listFiles();
            for (File f : fs) {
                //TODO 匹配流水excel原则
                if(f.getName().contains("xlsx")&&f.getName().contains("流水")){
                    allWaterExcel.add(f);
                }
            }
        });
        return allWaterExcel;
    }

    /**
     * 查找指定回单对象
     * @param receiptDto        回单信息
     * @return                  回单数据库对象
     */

    /**
     * 保存和绑定回单和流水
     * @param receiptDto    回单信息
     * @param waterDto      流水信息
     */


    /**
     * 保存流水，并绑定回单
     * @param waterDto              流水对象
     * @param newReceiptEntity      回单数据库对象
     * @return                      流水数据库对象
     */

    /**
     * 保存回单信息
     * @param receiptDto        回单信息
     * @return                  回单数据库对象
     */


    /**
     * 通过流水号和交易金额来判断回单流水是否匹配
     * @param waterDto
     * @param receiptDto
     * @return
     */

    /**
     * 匹配回单流水的日期和时间，如果有具体时间匹配具体时间，如果没有只匹配日期
     * @param waterDto      流水信息
     * @param receiptDto    回单信息
     * @return              是否匹配
     */


    /**
     * 多字段匹配水单流水
     * @param waterDto      流水信息
     * @param receiptDto    回单信息
     * @return              是否匹配
     */

    /**
     * 匹配收付放账号
     * @param waterDto      流水信息
     * @param receiptDto    回单信息
     * @return              是否匹配
     */

    /**
     * 多种情况匹配回单流水
     * @param waterDto          流水信息
     * @param receiptDtoList    回单列表
     * @param matchSerNumber    是否使用流水号匹配
     * @return                  匹配到的回单信息
     */

    /**
     * 保存全部回单流水
     * @param receiptDtoList    回单列表
     * @param waterDtoList      流水列表
     */

    /**
     * 判断是否字段重复
     * @param list          对象列表
     * @param serNumber     字段
     * @param amount        字段
     * @param <T>           对象类型
     * @param <R>           字段类型
     * @param <S>           字段类型
     * @return              是否重复
     */

    /**
     * 按日保存回单流水
     * @param receiptDtoList        回单列表
     * @param waterDtoList          流水列表
     * @throws IOException          IO异常
     */
    public void saveReceiptWaterByDate(List<KwReceipt> receiptDtoList, List<KwWater> waterDtoList) throws IOException {
        KwWater waterDto = waterDtoList.stream().findFirst().orElse(null);
        KwReceipt receiptDto = receiptDtoList.stream().findFirst().orElse(null);

        // 获取日期
        Timestamp date = null;
        if (waterDto != null){
            date = waterDto.getTransactionDate();
        }else if (receiptDto != null) {
            //date = TimeUtil.getDate(receiptDto.getBookDate());
        }
        System.out.println("waterDto === "+ waterDto);
        String finalDate = date.toString();
        List<KwWater> notMatchWater = waterDtoList.stream()
                .filter(w -> !w.getTransactionDate().toString().equals(finalDate)).collect(Collectors.toList());

        if (notMatchWater.size() > 0){
            throw new WrongFormatReceiptWaterException("流水数据有错误的日期"); // 存在不同的日期
        }

        List<KwReceipt> notMatchReceipt = receiptDtoList.stream()
                .filter(r -> !TimeUtil.getDate(r.getBookDate()).equals(finalDate)).collect(Collectors.toList());
        if (notMatchReceipt.size() > 0){
            throw new WrongFormatReceiptWaterException("回单数据有错误的日期"); // 存在不同的日期
        }

        List<KwWater> waterEntityList = new ArrayList<>();
        List<KwReceipt> receiptEntityList = new ArrayList<>();

        //找出当天所有关联的数据
        if (waterDto != null){
            waterEntityList = waterService.findByDateAndAccount(waterDto.getAccount(), finalDate);
            System.out.println("当天关联的数据 === " + waterEntityList.toString());
        }
        if (receiptDto != null){
            //receiptEntityList = receiptService.findByDateAndAccount(receiptDto.getSelfAccount(), date);
        }

        //找出RPA录入的数据
        List<KwWater> autoWaterList = waterEntityList.stream().filter(w -> w.getDateIndex() % 100 == 0).collect(Collectors.toList());
        List<KwReceipt> autoReceiptList = receiptEntityList.stream().filter(r -> r.getDateIndex() % 100 == 0).collect(Collectors.toList());

        List<KwWater> newWaterList = new ArrayList<>();
        List<KwReceipt> newReceiptList = new ArrayList<>();

        if (autoWaterList.size() == 0){
            newWaterList.addAll(waterService.batchUpdateNewWater(waterDtoList));
        }else if(!waterService.checkWaterMatch(waterDtoList, autoWaterList)){
            newWaterList.addAll(waterService.batchUpdateDiffWater(waterDtoList, autoWaterList));
        }else {
            newWaterList.addAll(waterService.batchUpdateSameWater(waterDtoList, autoWaterList));
        }

        if (autoReceiptList.size() == 0) {
        }

        // 绑定回单流水
        bindReceiptWater(newWaterList, newReceiptList);
    }

    /**
     * 绑定回单流水
     * @param waterEntityList       流水列表
     * @param receiptEntityList     回单列表
     */
    public void bindReceiptWater(List<KwWater> waterEntityList, List<KwReceipt> receiptEntityList){

        // 判断流水号能否用作匹配
        boolean matchSerNumber = !hasDuplicateItemProperty(waterEntityList, KwWater::getSerialNumber, KwWater::getTransactionAmount)
                && !hasDuplicateItemProperty(receiptEntityList, KwReceipt::getSerialNumber, KwReceipt::getAmount);

        for (int i=0;i<waterEntityList.size();i++){
            KwWater waterDto = waterEntityList.get(i);
            //var waterEntity = waterEntityList.get(i);
            if (waterDto.getReceiptId() != null){
                //如果流水以绑定回单，跳过
                continue;
            }

            // 使用流水信息匹配回单
            //var receiptDto = getMatchedReceiptDto(waterDto, receiptDtoList, matchSerNumber);
            String receiptDto = null;
            if (receiptDto != null){

            }else {
                String receiptId = waterDto.getReceiptId();
                KwReceipt currentReceiptEntity = DB.findById(KwReceipt.class, receiptId);
                if (currentReceiptEntity == null || currentReceiptEntity.getDateIndex() % 100 == 0){
                    waterDto.setReceiptId(null);
                    waterDto.setHasReceipt(0);
                    DB.update(waterDto);
                }
            }
        }
    }

    /**
     * excel文件转回单流水
     * @param folder        回单流水目录
     * @return              账号和日期
     */
    public Tuple convertExcel2ReceiptWater(File folder){
        logger.info("导入流水回单：" + folder.toPath().toAbsolutePath().toString());
        File[] fileList = folder.listFiles((d, s) -> {
            return s.toLowerCase().endsWith("xlsx");
        });
        List<KwWater> waterDtoList = new ArrayList<>();
        List<KwReceipt> receiptDtoList = new ArrayList<>();
        Tuple info = null;
        try{
            for(File f : fileList){
                if(f.getName().contains("回单")){
                    logger.info("从回单标准文件获取回单信息：" + f.getAbsolutePath());
                    //receiptDtoList.addAll(receiptService.excel2ReceiptDto(f));
                }else if (f.getName().contains("流水")){
                    logger.info("从流水标准文件获取流水信息：" + f.getAbsolutePath());
                    waterDtoList.addAll(waterService.excel2WaterDto(f));
                }
            }
            saveReceiptWaterByDate(receiptDtoList, waterDtoList);

            if (receiptDtoList.size() > 0){
                String account = receiptDtoList.get(0).getSelfAccount();
                info = new Tuple(account, TimeUtil.getDate(receiptDtoList.get(0).getBookDate()));
            }else if(waterDtoList.size() > 0) {
                String account = waterDtoList.get(0).getAccount();
                info = new Tuple(account, TimeUtil.getDate(waterDtoList.get(0).getTransactionDate()));
            }
            //waterService.checkAbalanceWater(waterDtoList);

            //todo 流水导入结果记录
            //waterImportResultService.addNew(folder.getAbsolutePath(), 0, null);
        }catch(Exception ex){
            logger.error("转换流水回单失败", ex);
            //waterImportResultService.addNew(folder.getAbsolutePath(), 1, ex.toString());
        }

        return info;
    }

    /**
     * 导入所有银行单位信息
     * @param excelFile
     */

    /**
     * 导入所有银行单位信息，包含MBS信息
     * @param excelFile
     */

    /**
     * 一次性导入一年流水，临时一次性使用
     * @param folder        流水文件目录
     */

    /**
     * 获取流水的日期
     * @param waterDtoList      流水列表
     * @return                  日期列表
     */

    /**
     * 按日期给瑞穗加入日期索引
     * @param dateSet           日期列表
     * @param waterDtoList      流水列表
     * @throws IOException      IO异常
     */

    /**
     * 判断是否字段重复
     * @param list          对象列表
     * @param serNumber     字段
     * @param amount        字段
     * @param <T>           对象类型
     * @param <R>           字段类型
     * @param <S>           字段类型
     * @return              是否重复
     */
    private static <T, R, S> Boolean hasDuplicateItemProperty(List<T> list, Function<T, ? extends R> serNumber, Function<T, ? extends S> amount){
        Set<String> items = new HashSet<>();
        for (T item : list){
            items.add(serNumber.apply(item).toString() + "||" + amount.apply(item).toString());
        }
        return items.size() != list.size();
    }
}

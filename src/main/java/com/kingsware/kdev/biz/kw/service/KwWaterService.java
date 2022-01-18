package com.kingsware.kdev.biz.kw.service;

import com.kingsware.kdev.biz.kw.argv.KwMechanismArgv;
import com.kingsware.kdev.biz.kw.argv.KwMechanismQueryArgv;
import com.kingsware.kdev.biz.kw.argv.KwWaterArgv;
import com.kingsware.kdev.biz.kw.argv.KwWaterQueryArgv;
import com.kingsware.kdev.biz.kw.model.KwWater;
import com.kingsware.kdev.biz.kw.ret.KwMechanismRet;
import com.kingsware.kdev.biz.kw.ret.KwWaterRet;
import com.kingsware.kdev.core.base.BaseService;
import com.kingsware.kdev.core.bean.MultiIdArgv;
import com.kingsware.kdev.core.bean.PageDataRet;
import com.kingsware.kdev.sys.argv.SysLoginLogQueryArgv;

import java.io.File;
import java.io.IOException;
import java.text.ParseException;
import java.util.Date;
import java.util.List;

/**
 * 流水管理业务类
 *
 * @author zxw
 * @version 1.0.0
 * @date 2022/01/04
 */
public interface KwWaterService extends BaseService {

    /**
     * 通过id查询
     * @param id    id
     * @return      返回结果
     */
    KwWaterRet get(String id);

    /**
     * 新增（暂时保留）
     * @param argv 新增
     */
     void add(KwWaterArgv argv);


    /**
     * 查询
     * @param argv 查询
     * @return 查询结果
     */
     PageDataRet<KwWaterRet> query(KwWaterQueryArgv argv);


    /**
     * 导出导入模板
     */
    void exportImportTemplate();

    /**
     * 导出
     */
    void export(KwWaterQueryArgv argv);

    /**
     * 流水导入和匹配业务：查询
     *
     * @param account       账号
     * @param date          日期
     * @return              流水列表
     */
    List<KwWater> findByDateAndAccount(String account, String date);

    /**
     * 流水导入和匹配业务：批量插入新的流水
     * @param waterDtoList      流水列表
     * @return                  新增的流水列表
     */
    List<KwWater> batchUpdateNewWater(List<KwWater> waterDtoList);

    /**
     * 流水导入和匹配业务：判断流水是否相同
     * @param waterDto      新流水信息
     * @param waterEntity   已存在流水信息
     * @return              是否相同
     */
    Boolean isSameWater(KwWater waterDto, KwWater waterEntity);

    /**
     * 流水导入和匹配业务：检测流水是否一样
     * @param waterDtoList          新的流水列表
     * @param waterEntityList       已存在的流水列表
     * @return                      是否一样
     */
    Boolean checkWaterMatch(List<KwWater> waterDtoList, List<KwWater> waterEntityList);

    /**
     * 流水导入和匹配业务：批量更新有差异的流水列表
     * @param waterDtoList          新的流水列表
     * @param waterEntityList       已存在的流水列表
     * @return                      更新后的流水列表
     */
    List<KwWater> batchUpdateDiffWater(List<KwWater> waterDtoList, List<KwWater> waterEntityList);

    /**
     * 更新相同的流水列表
     * @param waterDtoList      新的流水列表
     * @param waterEntityList   已存在的流水列表
     * @return                  更新的流水列表
     */
    List<KwWater> batchUpdateSameWater(List<KwWater> waterDtoList, List<KwWater> waterEntityList);

    /**
     * @author:  amzc
     * @methodsName: excel2WaterDto
     * @description: excel数据转流水单数据
     * @param:  MultipartFile file
     * @return: List<WaterDto>
     * @throws:
     */
    List<KwWater> excel2WaterDto(File file);
}

package com.kingsware.kdev.core.excel;

import com.kingsware.kdev.core.context.SpringContext;
import com.kingsware.kdev.core.encrypt.EncryptProperties;
import com.kingsware.kdev.core.encrypt.EncryptWorker;
import com.kingsware.kdev.core.encrypt.inst.AESInstance;
import com.kingsware.kdev.core.encrypt.inst.Base64Instance;
import com.kingsware.kdev.core.encrypt.inst.MD5Instance;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

/**
 * Excel处理工具类
 *
 * @author chen peng
 * @version 1.0.0
 * @date 2022/1/4 3:15 下午
 */
public class KExcel {

    /** 私有构造函数 **/
    private KExcel() {}

    /** 保存当前的所有的sheet **/
    private List<KSheet> sheetList = new ArrayList<>();

    /**
     * 创建sheet
     * @param name  sheet名称
     * @return  sheet
     */
    public KSheet createSheet(String name) {
        // 先判断一下有没有
        Optional<KSheet> optional = sheetList.stream().filter(it -> it.getName().equals(name)).findFirst();
        if (optional.isPresent()) {
            return optional.get();
        }
        else {
            KSheet sheet = new KSheet();
            sheet.setName(name);
            sheet.setIndex(sheetList.size());
            sheetList.add(sheet);
            return sheet;
        }
    }
}

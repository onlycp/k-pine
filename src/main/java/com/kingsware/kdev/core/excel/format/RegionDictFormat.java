package com.kingsware.kdev.core.excel.format;

import com.kingsware.kdev.core.cache.dict.DictManager;
import com.kingsware.kdev.core.util.DateUtils;
import com.kingsware.kdev.core.util.StringUtils;

import java.sql.Timestamp;
import java.util.Date;

/**
 * 字典格式
 *
 * @author chen peng
 * @version 1.0.0
 * @date 2022/1/6 8:56 上午
 */
public class RegionDictFormat implements RegionFormat {
    /** 字典码 **/
    private String dictCode;

    /** 字典码处理 **/
    public RegionDictFormat(String dictCode) {
        this.dictCode = dictCode;
    }

    @Override
    public Object format(Object value) {
        // 值为空，就返回空值
        if (value == null) {
            return null;
        }
        String strValue = value.toString();
        return DictManager.getInstance().getDict(dictCode, strValue);
    }
}

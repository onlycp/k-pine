package com.kingsware.kdev.core.util;

import lombok.Data;
import lombok.experimental.FieldNameConstants;
import org.junit.jupiter.api.Test;
import org.springframework.util.Assert;

import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;



/**
 * BeanUtils单元测试
 *
 * @author chen peng
 * @version 1.0.0
 * @date 2022/1/27 9:10 上午
 */
class BeanUtilsTest {

    @Data
    static
    @FieldNameConstants
    class BeanUtilsTestBean {
        private String id;
        private String strValue;
        private Integer intValue;
        private Long longValue;
        private Float floatValue;
        private Boolean boolValue;
        private BigDecimal bigDecimalValue;
        private Timestamp timestampValue;
        private Date dateValue;

    }

    @Test
    void setField() {
        BeanUtilsTestBean bean = new BeanUtilsTestBean();
        Field dateField = BeanUtils.getField(BeanUtilsTestBean.class, BeanUtilsTestBean.Fields.dateValue);
        BeanUtils.setField(dateField, bean, "1643247837000");
        // 字符串属性
        Field strField = BeanUtils.getField(BeanUtilsTestBean.class, BeanUtilsTestBean.Fields.strValue);
        BeanUtils.setField(strField, bean, "str");
        Assert.isTrue(bean.strValue.equals("str"), "string值设置失败");
        // 整型属性
        Field intField = BeanUtils.getField(BeanUtilsTestBean.class, BeanUtilsTestBean.Fields.intValue);
        BeanUtils.setField(intField, bean, "2");
        Assert.isTrue(bean.intValue == 2, "integer值设置失败");
        BeanUtils.setField(intField, bean, "true");
        Assert.isTrue(bean.intValue == 1, "boolean字符 -> integer值设置失败");
        BeanUtils.setField(intField, bean, false);
        Assert.isTrue(bean.intValue == 0, "boolean值 -> integer值设置失败");
        // 长整型
        Field longField = BeanUtils.getField(BeanUtilsTestBean.class, BeanUtilsTestBean.Fields.longValue);
        BeanUtils.setField(longField, bean, "111111");
        Assert.isTrue(bean.longValue == 111111, "long值设置失败");
        // 浮点型
        Field floatField = BeanUtils.getField(BeanUtilsTestBean.class, BeanUtilsTestBean.Fields.floatValue);
        BeanUtils.setField(floatField, bean, "52.22");
        Assert.isTrue(bean.floatValue == 52.22f, "float值设置失败");
        // 布尔型
        Field boolField = BeanUtils.getField(BeanUtilsTestBean.class, BeanUtilsTestBean.Fields.boolValue);
        BeanUtils.setField(boolField, bean, "true");
        Assert.isTrue(bean.boolValue, "bool值设置失败");
        BeanUtils.setField(boolField, bean, "1");
        Assert.isTrue(bean.boolValue, "bool值设置失败");
        // BigDecimal
        Field bigDecimalField = BeanUtils.getField(BeanUtilsTestBean.class, BeanUtilsTestBean.Fields.bigDecimalValue);
        BeanUtils.setField(bigDecimalField, bean, "12.1");
        Assert.isTrue(bean.bigDecimalValue.equals(new BigDecimal("12.1")), "bigDecimal值设置失败");
        BeanUtils.setField(bigDecimalField, bean, 12.1f);
        Assert.isTrue(bean.bigDecimalValue.equals(new BigDecimal("12.1")), "float -> bigDecimal值设置失败");
        BeanUtils.setField(bigDecimalField, bean, 12);
        Assert.isTrue(bean.bigDecimalValue.equals(new BigDecimal("12")), "int -> bigDecimal值设置失败");
        // Timestamp
        Field timestampField = BeanUtils.getField(BeanUtilsTestBean.class, BeanUtilsTestBean.Fields.timestampValue);
        BeanUtils.setField(timestampField, bean, "1643247837000");
        Assert.isTrue(bean.timestampValue.getTime() == 1643247837000L, "string -> timestamp值设置失败");
        BeanUtils.setField(timestampField, bean, "2022-01-27 09:43:57");
        Assert.isTrue(bean.timestampValue.getTime() == 1643247837000L, "dateString -> timestamp值设置失败");
        BeanUtils.setField(timestampField, bean, 1643247837000L);
        Assert.isTrue(bean.timestampValue.getTime() == 1643247837000L, "long -> timestamp值设置失败");
        // Date

        Assert.isTrue(bean.dateValue.getTime() == 1643247837000L, "string -> date值设置失败");
        BeanUtils.setField(dateField, bean, "2022-01-27 09:43:57");
        Assert.isTrue(bean.dateValue.getTime() == 1643247837000L, "dateString -> date值设置失败");
        BeanUtils.setField(dateField, bean, 1643247837000L);
        Assert.isTrue(bean.dateValue.getTime() == 1643247837000L, "long -> timestamp值设置失败");


    }
}

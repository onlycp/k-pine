package com.kingsware.kdev.biz.kw.util;


import com.kingsware.kdev.biz.kw.enums.CashTransferEnum;
import com.kingsware.kdev.biz.kw.enums.CurrencyEnum;
import com.kingsware.kdev.biz.kw.enums.RevenueEnum;

/**
 * 功能描述：枚举转化工具类
 *
 * @author 林贤钦
 * @version 1.00
 * @Date 2020/11/27
 */
public class EnumSelectionUtil {
    /**
     * @author:  lxq
     * @methodsName: getCurrency
     * @description: 传入中文或者英文字符的币种，获取相对于的数据库int类型字段
     * @param:  String
     * @return: int
     * @throws:
     */
    public static int getCurrency(String currency){
        if ("人民币".equals(currency)||"RMB".equals(currency)){
            return CurrencyEnum.RMB.getValue();//人民币币种
        }else if ("美元".equals(currency)||"USD".equals(currency)){
            return CurrencyEnum.USD.getValue();//美元币种
        }else if ("欧元".equals(currency)||"EUR".equals(currency)){
            return CurrencyEnum.EUR.getValue();//欧元币种
        }else if ("澳元".equals(currency)||"AUD".equals(currency)){
            return CurrencyEnum.AUD.getValue();//欧元币种
        }else if ("英元".equals(currency)||"GBP".equals(currency)){
            return CurrencyEnum.GBP.getValue();//英元币种
        }else if ("日元".equals(currency)||"JPY".equals(currency)){
            return CurrencyEnum.JPY.getValue();//英元币种
        }else{
            return CurrencyEnum.RMB.getValue();//默认人民币币种
        }
    }
    public static int getCashTransfer(String cashTransfer){
        if ("现".equals(cashTransfer)||"现金".equals(cashTransfer)){
            return CashTransferEnum.CASH.getValue();//现金
        }else if ("转".equals(cashTransfer)||"转账".equals(cashTransfer)){
            return CashTransferEnum.TRANSFER.getValue();//转账
        }else {
            return CashTransferEnum.CASH.getValue();//现金
        }
    }
    public static int getRevenue(String revenue){
        if ("收入".equals(revenue)||"收".equals(revenue)){
            return RevenueEnum.INCOME.getValue();//收入
        }else if ("支出".equals(revenue)||"支".equals(revenue)){
            return RevenueEnum.PAY.getValue();//支出
        }else {
            return RevenueEnum.INCOME.getValue();//收入
        }
    }
}

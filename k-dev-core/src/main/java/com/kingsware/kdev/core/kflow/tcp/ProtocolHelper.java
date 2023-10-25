package com.kingsware.kdev.core.kflow.tcp;

import lombok.extern.slf4j.Slf4j;

import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;

/**
 * @author chenp
 * @date 2023/10/24
 */
@Slf4j
public class ProtocolHelper {

    /**
     * 将byte转为int
     * @param val
     * @return
     */
    public static int byteToInt(byte val) {
        return val & 0xFF;
    }


    /**
     * 将byte转为int
     * @param len
     * @return
     */
    public static int bytesToInt(byte[] data, int index,int len) {
        int res = 0;
        for (int i = 0; i < len; i++) {
            res += byteToInt(data[index + i]) << ((i * 8));
        }
        return res;
    }

    /**
     * 将int转为byte数组
     * @param len
     * @return
     */
    public static byte[] intToBytes(int val,int len) {
        byte[] desc = new byte[len];
        for (int i = len-1; i >=0 ; i--) {
            desc[len-1-i] = (byte) ((val>> ((len-1-i)*8)) & 0x000000FF);
        }
        return desc;
    }

    /**
     * 解析获取实际浮点数值
     * @param value
     * @param factor
     * @return
     */
    public static String getRealValue(int value, String factor){
        return new BigDecimal(value+"").multiply(new BigDecimal(factor)).toString();
    }

    /**
     * 将bytes转为字符串
     * @param len
     * @return
     */
    public static String bytesToStr(byte[] data, int index,int len) {

        byte[] desc = new byte[len];
        System.arraycopy(data, index, desc, 0, len);
        String val = new String(desc);
        int zeroPos = -1;
        for (int i =0 ; i< len; i++){
            zeroPos = i;
            if (desc[i] == 0x00){
                return val.substring(0, zeroPos);
            }
        }
        if (val.length()> len){
            return val.substring(0, len);
        }
        else {
            return val;
        }



    }


    /**
     * 将字符串转为byte
     * @param str   字符串
     * @param len   长度
     * @return
     */
    public static byte[] str2Bytes(String str, int len) {

        byte[] desc = new byte[len];
        String val = "";
        try {
            byte[] strBytes = str.getBytes("gbk");
            System.arraycopy(strBytes,0, desc,0, strBytes.length);
            if (strBytes.length< len){
                for (int i = strBytes.length; i< len; i++){
                    desc[i] = 0;
                }
            }

        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return desc;
    }

    /**
     * 打印输出byte数组
     *
     * @param bytes
     */
    public static void printBytes(byte[] bytes, int size){

        log.info(getPrintString(bytes, size));
    }

    /**
     * 打印输出byte数组
     *
     * @param bytes
     */
    public static String getPrintString(byte[] bytes, int size){
        return getPrintString(bytes, 0 , size);
    }

    /**
     * 打印输出byte数组
     *
     * @param bytes
     */
    public static String getPrintString(byte[] bytes, int index, int size){

        StringBuffer sb = new StringBuffer();
        for (int i=index ;i< size; i++){
            sb.append(String.format("%02X ", bytes[i]));
        }
        return sb.toString();
    }


}

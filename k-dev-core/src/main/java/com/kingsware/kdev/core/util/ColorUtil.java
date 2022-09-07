package com.kingsware.kdev.core.util;

import java.awt.*;

public class ColorUtil {
    /**
     * Color对象转换成十六进制字符串
     * @param color Color对象
     * @return 16进制颜色字符串
     * */
    public static String toHexFromColor(Color color){
        String r,g,b;
        StringBuilder su = new StringBuilder();
        r = Integer.toHexString(color.getRed());
        g = Integer.toHexString(color.getGreen());
        b = Integer.toHexString(color.getBlue());
        r = r.length() == 1 ? "0" + r : r;
        g = g.length() ==1 ? "0" +g : g;
        b = b.length() == 1 ? "0" + b : b;
        r = r.toUpperCase();
        g = g.toUpperCase();
        b = b.toUpperCase();
        //su.append("0xFF");
        su.append("#");
        su.append(r);
        su.append(g);
        su.append(b);
        //#0000FF
        return su.toString();
    }
    /**
     * 十六进制字符串转换成Color对象
     * @param colorStr 16进制颜色字符串
     * @return Color对象
     * */
    public static Color toColorFromString(String colorStr){
        int length = colorStr.length();
        //需要处理一下那些缩写的，例如#ff0，我们要将他转为#ffff00
        if(length <= 4){
            char c1 = colorStr.charAt(1);
            char c2 = colorStr.charAt(2);
            char c3 = colorStr.charAt(3);
            colorStr = "#"+c1+c1+c2+c2+c3+c3;
        }
        String str1 = colorStr.substring(1, 3);
        String str2 = colorStr.substring(3, 5);
        String str3 = colorStr.substring(5, 7);
        int red = Integer.parseInt(str1, 16);
        int green = Integer.parseInt(str2, 16);
        int blue = Integer.parseInt(str3, 16);
        //java.awt.Color[r=0,g=0,b=255]
        return new Color(red,green,blue);
    }

    public static void main(String[] args) {
        String colorStr = "#ff0";
        Color color =  toColorFromString(colorStr) ;
        System.out.println(color);
    }
}

package com.kingsware.kdev.core.util;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.Random;

public class VerifyCodeUtils {

    //对图片进行处理的类和方法
    public static String drawRandomText(int width, int height, BufferedImage verifyImg, int interferenceLines, int noisePoints) {
        // 最小值校验
        interferenceLines = Math.max(interferenceLines, 20);
        noisePoints = Math.max(noisePoints, 50);

        Graphics2D graphics = (Graphics2D) verifyImg.getGraphics();
        // 设置抗锯齿渲染
        graphics.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        graphics.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);

        graphics.setColor(Color.WHITE);//设置画笔颜色-验证码背景色
        graphics.fillRect(0, 0, width, height);//填充背景
        graphics.setFont(new Font("微软雅黑", Font.BOLD, 36));

        //数字和字母的组合
        String baseNumLetter = "123456789abcdefghijklmnopqrstuvwxyzABCDEFGHJKLMNPQRSTUVWXYZ";
        StringBuffer sBuffer = new StringBuffer();
        int x = 15;  //旋转原点的 x 坐标
        String ch = "";
        Random random = new Random();

        for (int i = 0; i < 4; i++) {
            graphics.setColor(getRandomColor());
            //设置字体旋转角度（限制在±15度以内，减少变形）
            int degree = random.nextInt(31) - 15;  // -15 到 15 度
            int dot = random.nextInt(baseNumLetter.length());
            ch = baseNumLetter.charAt(dot) + "";
            sBuffer.append(ch);

            //正向旋转
            graphics.rotate(degree * Math.PI / 180, x, height / 2 + 5);
            graphics.drawString(ch, x, height / 2 + 5);

            //反向旋转
            graphics.rotate(-degree * Math.PI / 180, x, height / 2 + 5);
            x += 60;
        }

        //画干扰线
        for (int i = 0; i < interferenceLines; i++) {
            // 设置随机颜色
            graphics.setColor(getRandomColor());
            // 随机画线
            graphics.drawLine(random.nextInt(width), random.nextInt(height),
                    random.nextInt(width), random.nextInt(height));
        }

        //添加噪点
        for (int i = 0; i < noisePoints; i++) {
            int x1 = random.nextInt(width);
            int y1 = random.nextInt(height);
            graphics.setColor(getRandomColor());
            graphics.fillRect(x1, y1, 2, 2);
        }

        graphics.dispose();
        return sBuffer.toString();
    }

    /**
     * 对图片进行处理的类和方法（兼容旧版本，使用默认值）
     */
    public static String drawRandomText(int width, int height, BufferedImage verifyImg) {
        return drawRandomText(width, height, verifyImg, 25, 80);
    }

    /**
     * 随机取色
     */
    private static Color getRandomColor() {
        Random ran = new Random();
        Color color = new Color(ran.nextInt(256),
                ran.nextInt(256), ran.nextInt(256));
        return color;
    }
}

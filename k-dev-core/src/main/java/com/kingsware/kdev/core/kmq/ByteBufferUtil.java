package com.kingsware.kdev.core.kmq;

import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

/**
 * @author chenp
 * @date 2024/2/29
 */
public class ByteBufferUtil {

    /**
     * 将字符串转换为ByteBuffer
     * @param str 字符串
     * @return ByteBuffer对象
     */
    public static ByteBuffer getByteBuffer(String str) {
        return ByteBuffer.wrap(str.getBytes(StandardCharsets.UTF_8));
    }

    /**
     * 将ByteBuffer转换为字符串
     * @param buffer ByteBuffer对象
     * @return 字符串
     */
    public static String getString(ByteBuffer buffer) {

        try {
            Charset charset = StandardCharsets.UTF_8;
            java.nio.charset.CharsetDecoder decoder = charset.newDecoder();
            // 将ByteBuffer转换为只读缓冲区
            ByteBuffer readOnlyBuffer = buffer.asReadOnlyBuffer();

            // 使用解码器解码缓冲区
            CharBuffer charBuffer = decoder.decode(readOnlyBuffer);

            // 将解码得到的字符缓冲区转换为字符串并返回
            return charBuffer.toString();
        } catch (Exception ex) {
            ex.printStackTrace();
            return "error";
        }
    }
}


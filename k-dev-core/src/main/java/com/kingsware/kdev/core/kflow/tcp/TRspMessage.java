package com.kingsware.kdev.core.kflow.tcp;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;

import java.io.*;

/**
 * <p>
 *
 * </p>
 *
 * @package: com.kingsware.socket
 * @description:
 * @author: moojn
 * @date: Created in 2023/10/9 18:01
 * @copyright: Copyright (c) 2023
 * @version: V1.0
 * @modified: moojn
 */
@Data
@Slf4j
public class TRspMessage {
    /**
     * 消息头，包含消息类型和长度
     */
    private short type;
    private short length;
    /**
     * 消息体
     */
    private String data;

    public TRspMessage() {
    }

    public TRspMessage(SocketHeadType type, String data) {
        this.type = (short) type.getNumber();
        this.data = data;
        this.length = (short) (3 + data.getBytes().length);
    }

    /**
     * 序列化方法，将消息转换为字节数组
     * @return
     */
    public byte[] toByteArray() {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        DataOutputStream dos = new DataOutputStream(baos);
        try {
            // 写入消息头
            dos.writeShort(type);
            dos.writeShort(length);
            // 写入消息体
            dos.writeUTF(data);

        } catch (IOException e) {
            log.error(e.getMessage(), e);
        } finally {
            try {
                dos.close();
            } catch (IOException e) {
                log.error(e.getMessage(), e);
            }
        }
        return baos.toByteArray();
    }

    /**
     * 反序列化方法，根据字节数组创建消息对象
     * @param bytes
     * @return
     */
    public static TRspMessage parseFrom(byte[] bytes) {
        ByteArrayInputStream bais = new ByteArrayInputStream(bytes);
        DataInputStream dis = new DataInputStream(bais);
        try {
            // 读取消息头
            short type = dis.readShort();
            int length = dis.readInt();
            // 读取消息体
            String data = dis.readUTF();
            return new TRspMessage(SocketHeadType.valueOf(type), data);
        } catch (IOException e) {
            log.error(e.getMessage(), e);
        } finally {
            try {
                dis.close();
            } catch (IOException e) {
                log.error(e.getMessage(), e);
            }
        }
        return null;
    }

}

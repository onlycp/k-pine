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
 * @date: Created in 2023/10/9 17:59
 * @copyright: Copyright (c) 2023
 * @version: V1.0
 * @modified: moojn
 */
@Data
@Slf4j
public class TReqMessage {


    // 起始符
    private byte[] start = {0x23, 0x23};
    /**
     * 消息头，包含消息类型和长度
     */
    private short type;
    private short length;
    /**
     * 消息体，包含用户ID和数据
     */
    private String uid;
    private String data;
    //


    public TReqMessage() {
    }

    public TReqMessage(SocketHeadType type, String uid, String data) {
        this.type = (short) type.getNumber();
        this.uid = uid;
        this.data = data;
        this.length = (short) (3 + uid.getBytes().length + data.getBytes().length);
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
            dos.writeUTF(uid);
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
    public static TReqMessage parseFrom(byte[] bytes) {
        ByteArrayInputStream bais = new ByteArrayInputStream(bytes);
        DataInputStream dis = new DataInputStream(bais);
        try {
            // 读取消息头
            short type = dis.readShort();
            short length = dis.readShort();
            // 读取消息体
            String uid = dis.readUTF();
            String data = dis.readUTF();
            return new TReqMessage(SocketHeadType.valueOf(type), uid, data);
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

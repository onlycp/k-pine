package com.kingsware.kdev.core.kmq.channel;

/**
 * 通道接口
 *
 * @author chen peng
 * @version 1.0.0
 * @date 2021/12/22 5:48 下午
 */
public interface MqChannel {

    /**
     * 写入数据
     * @param payload 写入数据
     */
    void write(String payload) throws Exception ;

    /**
     * 读取数据
     * @return  数据
     */
    String read() throws Exception ;
}

package com.kingsware.kdev.core.cache.instance;

import com.kingsware.kdev.core.context.SpringContext;
import com.kingsware.kdev.core.model.SysInstance;
import com.kingsware.kdev.core.util.ThreadUtils;

/**
 * @author chenp
 * @date 2024/4/24
 */
public class MessageQueueManager {
    private static MessageQueueManager instance = new MessageQueueManager();

    private MessageQueueServer server;
    private MessageQueueClient client;


    public static MessageQueueManager getInstance() {
        if (instance == null) {
            instance = new MessageQueueManager();
        }
        return instance;
    }

    private MessageQueueManager() {
        init();
    }


    public void init() {
        new Thread(() -> {
            while (true) {
                ThreadUtils.sleep(1000);
                if (client != null) {
                    client.heart();
                }
            }
        }).start();
    }
    /**
     * 启动一个线程，周期性地进行系统检查。
     * 该方法会不断地查询当前系统实例状态，并基于此进行相应的检查服务调用。
     * 无参数和返回值。
     */
    public void doCheck() {
        // 创建并启动一个新线程，用于周期性执行检查任务
        // 获取当前的主系统实例
        SysInstance master = InstanceManager.getInstance().masterInstance();
        // 读取应用的MQ端口号
        int appMqPort = Integer.parseInt(SpringContext.getProperties("app.mq-port", "19999"));
        // 执行检查服务，传入当前是否为主实例、主实例的主机名和应用的MQ端口号
        this.checkService(InstanceManager.getInstance().isMaster(), master.getHostName(), appMqPort );
    }


    /**
     * 根据提供的角色和服务器信息，启动或关闭服务器和客户端。
     * 如果isMaster为true，则该方法将尝试启动一个服务器（如果尚未启动）并确保客户端连接到指定的主服务器。
     * 如果isMaster为false，则关闭服务器并确保客户端重新连接到新的主服务器。
     *
     * @param isMaster 表示当前实例的角色，为true时为master，为false时为slave。
     * @param masterIp 主服务器的IP地址。
     * @param masterPort 主服务器的端口号。
     */
    public void checkService(boolean isMaster, String masterIp, int masterPort) {
        if (isMaster) {
            // 如果当前是主服务器角色，且服务器未启动，则启动服务器
            if (server == null) {
                server = new MessageQueueServer(masterPort);
                server.start();
            }
        }
        else {
            // 如果当前是slave角色，且服务器已启动，则关闭服务器
            if (server != null) {
                server.close();
                server = null;
            }
        }
        // 初始化或重新初始化客户端，确保客户端连接到正确的主服务器
        if (client == null) {
            client = new MessageQueueClient(masterIp, masterPort);
            client.start();
        }
        else {
            // 如果客户端已初始化但连接的服务器信息不一致，则关闭并重新初始化客户端
            if (!client.getHost().equals(masterIp) || client.getPort() != masterPort) {
                client.close();
                client = new MessageQueueClient(masterIp, masterPort);
                client.start();
            }
        }
    }

    /**
     * 向指定主题发送消息。
     * @param topic 消息的主题，用于标识消息的类别或目标。
     * @param message 要发送的消息内容。
     */
    public void broadMessage(String topic, String message) {
        // 创建一个新的消息项并设置主题和消息数据
        MessageItem msg = new MessageItem();
        msg.setTopic(topic);
        msg.setData(message);

        // 如果客户端已连接，则发送消息
        if (client != null) {
            client.send(msg);
        }
    }







}

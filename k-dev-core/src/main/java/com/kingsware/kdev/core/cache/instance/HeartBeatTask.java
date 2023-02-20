package com.kingsware.kdev.core.cache.instance;

import com.kingsware.kdev.core.context.SpringContext;
import com.kingsware.kdev.core.cron.KRunner;
import com.kingsware.kdev.core.cron.KTask;
import com.kingsware.kdev.core.model.SysInstance;
import com.kingsware.kdev.core.orm.DB;
import com.kingsware.kdev.core.util.DateUtils;
import com.kingsware.kdev.core.util.StringUtils;
import lombok.extern.slf4j.Slf4j;

import java.net.InetAddress;

/**
 * 心跳任务
 * @author chen peng
 * @version 1.0.0
 * @date 2023/2/20 11:02
 */
@Slf4j
public class HeartBeatTask implements KTask, KRunner {

    private static String INSTANCE_ID = "";

    @Override
    public void runNow() throws Exception {
        HostInfo hostInfo = getHost();
        // 判断是否注册过
        SysInstance instance = DB.findOne(SysInstance.class, "select * from sys_instance where host_name=? and port=?", hostInfo.getHostName(), hostInfo.getPort());
        if (instance == null) {
            instance = new SysInstance();
            instance.setPort(hostInfo.getPort());
            instance.setHostName(hostInfo.getHostName());
            instance.setId(StringUtils.getUUID());
            instance.setHeartBeatTime(DateUtils.getNow());
            instance.setRegTime(DateUtils.getNow());
            DB.save(instance);
        }
        else {
            instance.setHeartBeatTime(DateUtils.getNow());
            instance.setRegTime(DateUtils.getNow());
            DB.update(instance);
        }
        // 设置实例id
        HeartBeatTask.INSTANCE_ID = instance.getId();


    }

    @Override
    public void execute() throws Exception {
        DB.executeUpdateSql("update sys_instance set heart_beat_time=? where id=?", DateUtils.getNow(), INSTANCE_ID);

    }

    @Override
    public String cron() {
        return "0/10 * * * * ?";
    }

    @Override
    public String name() {
        return this.getClass().getSimpleName();
    }

    /**
     * 获取主机信息
     * @return
     */
    public HostInfo getHost() {
        HostInfo hostInfo = new HostInfo();
        try {
            if (System.getenv("COMPUTERNAME") != null) {
                hostInfo.setHostName(System.getenv("COMPUTERNAME"));
            }
            else {
                InetAddress inetAddress = InetAddress.getLocalHost();
                hostInfo.setHostName(inetAddress.getHostName());
            }

        }
        catch (Exception e) {
            hostInfo.setHostName("localhost");
        }
        hostInfo.setPort(Integer.parseInt(SpringContext.getProperties("server.port", "8080")));
        return hostInfo;

    }
}

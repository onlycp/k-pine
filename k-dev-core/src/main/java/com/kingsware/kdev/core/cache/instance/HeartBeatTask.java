package com.kingsware.kdev.core.cache.instance;

import com.kingsware.kdev.core.context.SpringContext;
import com.kingsware.kdev.core.cron.KRunner;
import com.kingsware.kdev.core.cron.KTask;
import com.kingsware.kdev.core.model.SysInstance;
import com.kingsware.kdev.core.orm.DB;
import com.kingsware.kdev.core.util.DateUtils;
import com.kingsware.kdev.core.util.StringUtils;
import com.kingsware.kdev.core.util.SystemUtil;
import lombok.extern.slf4j.Slf4j;

import java.net.InetAddress;
import java.util.Date;
import java.util.List;

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
        HostInfo hostInfo = SystemUtil.getHost();
        // 判断是否注册过
        SysInstance instance = DB.findOne(SysInstance.class, "select * from sys_instance where host_name=? and port=?", hostInfo.getHostName(), hostInfo.getPort());
        if (instance == null) {
            instance = new SysInstance();
            instance.setPort(hostInfo.getPort());
            instance.setHostName(hostInfo.getHostName());
            instance.setId(StringUtils.getUUID());
            instance.setHeartBeatTime(DateUtils.getNow());
            instance.setRegTime(DateUtils.getNow());
            instance.setOnline(1);
            DB.save(instance);
        }
        else {
            instance.setHeartBeatTime(DateUtils.getNow());
            instance.setOnline(1);
            DB.update(instance);
        }
        // 设置实例id
        HeartBeatTask.INSTANCE_ID = instance.getId();
        this.refreshInstances();


    }

    @Override
    public void execute() throws Exception {
        DB.executeUpdateSql("update sys_instance set heart_beat_time=?, online = 1 where id=?", DateUtils.getNow(), INSTANCE_ID);
        this.refreshInstances();
    }

    private void refreshInstances() {
        // 查找所有会话
        List<SysInstance> instanceList = DB.findList(SysInstance.class, "select * from sys_instance where online = 1 order by reg_time asc ");
        InstanceManager.getInstance().setInstances(instanceList);
        long instTimeoutSecond = Long.parseLong(SpringContext.getProperties("app.inst-time-out-second", "30"));
        Date date = new Date(new Date().getTime() - (1000*instTimeoutSecond));
        // 设置超时
        DB.executeUpdateSql("update sys_instance set  online = 0 where heart_beat_time < ?", DateUtils.formatDate(date, DateUtils.DATE_TIME));
    }

    @Override
    public String cron() {
        return "0/10 * * * * ?";
    }

    @Override
    public String name() {
        return this.getClass().getSimpleName();
    }


}

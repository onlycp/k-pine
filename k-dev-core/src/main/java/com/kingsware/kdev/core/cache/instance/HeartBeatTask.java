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
import java.util.stream.Collectors;

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
        SysInstance instance = DB.findOne(SysInstance.class, "select * from sys_instance where id=?", INSTANCE_ID);
        instance.setOnline(1);
        instance.setHeartBeatTime(DateUtils.getNow());
        DB.update(instance);
        this.refreshInstances();
    }

    private void refreshInstances() {
        // 查找所有会话
        List<SysInstance> instanceList = DB.findList(SysInstance.class, "select * from sys_instance order by reg_time asc ");
        instanceList = instanceList.stream().filter(it -> it.getOnline() == 1).collect(Collectors.toList());
        InstanceManager.getInstance().setInstances(instanceList);
        String mqChannel = SpringContext.getProperties("app.mq-channel", "http");
        if ("tcp".equalsIgnoreCase(mqChannel)) {
            // 刷新消息队列
            MessageQueueManager.getInstance().doCheck();
        }
        // 设置超时
        long instTimeoutSecond = Long.parseLong(SpringContext.getProperties("app.inst-time-out-second", "30"));
        Date date = new Date(new Date().getTime() - (1000*instTimeoutSecond));
        for (SysInstance instance : instanceList) {
            if (instance.getHeartBeatTime().compareTo(DateUtils.formatDate(date, DateUtils.DATE_TIME)) < 0) {
                instance.setOnline(0);
                DB.update(instance);
            }
        }
    }

    @Override
    public String cron() {
        return "0/10 * * * * ?";
    }

    @Override
    public String name() {
        return "系统心跳任务";
    }

    @Override
    public String note() {
        return "系统心跳任务，定时更新应用实例的活动时间";
    }


}

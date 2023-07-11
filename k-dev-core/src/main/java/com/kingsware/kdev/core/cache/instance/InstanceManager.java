package com.kingsware.kdev.core.cache.instance;

import com.kingsware.kdev.core.auth.AuthToken;
import com.kingsware.kdev.core.auth.TokenUtil;
import com.kingsware.kdev.core.cache.session.TokenSession;
import com.kingsware.kdev.core.model.SysInstance;
import com.kingsware.kdev.core.model.SysOnlineUser;
import com.kingsware.kdev.core.orm.DB;
import com.kingsware.kdev.core.util.BeanUtils;
import com.kingsware.kdev.core.util.SystemUtil;
import lombok.extern.slf4j.Slf4j;

import java.sql.Timestamp;
import java.util.*;

/**
 * // 会话管理 单实例.
 * @author chen peng
 * @version 1.0.0
 * @date 2022/1/6 9:25 上午
 */
@Slf4j
public class InstanceManager {
    /** 实例 **/
    private static InstanceManager instance;
    /** 实例列表 **/
    private List<SysInstance> instances = Collections.synchronizedList(new ArrayList<>());
    /** 上次执行节点 **/
    private Map<String, String> lastInstanceNameMap = new HashMap<>();

    public static InstanceManager getInstance() {
        if (instance == null) {
            instance = new InstanceManager();
        }
        return instance;
    }

    private InstanceManager() {
    }

    /**
     * 设置实例
     * @param sysInstances  实例列表
     */
    public void setInstances(List<SysInstance> sysInstances) {
        this.instances = sysInstances;
    }

    /**
     * 获取下个实例
     * @param instanceName  实例名称
     * @return  实例
     */
    public SysInstance nextInstance(String instanceName) {
        int index = 0;
        for (int i=0; i<instances.size(); i++) {
            SysInstance inst = instances.get(i);
            if (inst.instanceName().equals(instanceName)) {
                index = i;
                break;
            }
        }
        if ((index+1) >= instances.size()) {
            index = 0;
        }
        return instances.get(index);
    }

    /**
     * 获取第一个实例
     * @return  返回实例
     */
    public SysInstance masterInstance() {
        return this.instances.get(0);
    }

    /**
     * 判断是否master
     * @return
     */
    public boolean isMaster() {
        return SystemUtil.getHost().instanceName().equalsIgnoreCase(this.masterInstance().instanceName());
    }


    /**
     * 获取运行任务的实例
     * 目前暂采用随机策略
     * @param excludeInstanceName   排除的实例名
     * @return
     */
    public SysInstance getToExecuteInstance(String taskId, String excludeInstanceName) {
        List<SysInstance> copyInstances = new ArrayList<>();
        for (SysInstance sysInstance: instances) {
            String lastInstanceName =  lastInstanceNameMap.get(taskId);
            if (!sysInstance.instanceName().equalsIgnoreCase(excludeInstanceName) && !sysInstance.instanceName().equalsIgnoreCase(lastInstanceName)) {
                copyInstances.add(sysInstance);
            }
        }
        SysInstance chooseInstance = instances.get(0);
        if (!copyInstances.isEmpty()) {
            int randIndex = new Random().nextInt(copyInstances.size());
            chooseInstance = copyInstances.get(randIndex);
        }
        this.lastInstanceNameMap.put(taskId, chooseInstance.instanceName());
        return chooseInstance;
    }

}

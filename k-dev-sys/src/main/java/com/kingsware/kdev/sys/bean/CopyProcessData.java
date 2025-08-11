package com.kingsware.kdev.sys.bean;

import com.kingsware.kdev.core.model.DevPage;
import com.kingsware.kdev.core.model.SysLogicFlow;
import com.kingsware.kdev.core.model.SysTask;
import com.kingsware.kdev.core.orm.kdb.AddFlowInfo;
import com.kingsware.kdev.core.util.StringUtils;
import com.kingsware.kdev.sys.model.SysApi;
import com.kingsware.kdev.sys.model.SysConfig;
import com.kingsware.kdev.sys.model.SysDict;
import com.kingsware.kdev.sys.model.SysDictItem;
import lombok.Data;

import java.util.*;

/**
 * @author chenp
 * @date 2023/3/17
 */
@Data
public class CopyProcessData {
    /** 数据库逻辑编排id集合 **/
    private Set<String> dbFlowIds = new HashSet<>();
    /** FAAS逻辑编排id集合 **/
    private Set<String> faasFlowIds = new HashSet<>();
    /** 接口id集合 **/
    private Set<String> apiIds = new HashSet<>();
    /** 页面id集合 **/
    private Set<String> pageIds = new HashSet<>();
    /** 字典id集合 **/
    private Set<String> dictIds = new HashSet<>();
    /** 字典项id集合 **/
    private Set<String> dictItemIds = new HashSet<>();
    /** 系统配置 **/
    private Set<String> configIds = new HashSet<>();
    /** 系统配置 **/
    private Set<String> taskIds = new HashSet<>();
    /** 应用ids **/
    private Set<String> appIds = new HashSet<>();
    /** 数据拷贝列表 **/
    private List<Object> toCopySet = new ArrayList<>();

    /** 映射记录 **/
    private Map<String, String> uidMap = new HashMap<>();


    public void addMapping(String key, String value) {
        this.uidMap.put(key, value) ;
    }


    /**
     * 增加数据
     * @param addData 数据
     */
    public void addData(CopyProcessData addData) {
        dbFlowIds.addAll(addData.getDbFlowIds());
        faasFlowIds.addAll(addData.getFaasFlowIds());
        apiIds.addAll(addData.getApiIds());
        pageIds.addAll(addData.getPageIds());
        dictIds.addAll(addData.getDictIds());
        dictItemIds.addAll(addData.getDictItemIds());
        configIds.addAll(addData.getConfigIds());
    }

    /**
     * 增加拷贝对象
     * @param obj   对象
     */
    public void addCopyObject(Object obj) {
        if (!existCopyObject(obj)) {
            this.toCopySet.add(obj);
        }
    }

    /**
     * 获取流程id
     * @return
     */
    public Set<String> getFlowIdsFromObjects() {
        Set<String> strings = new HashSet<>();
        for (Object curObj: toCopySet) {
            if (curObj instanceof SysLogicFlow) {
                strings.add(((SysLogicFlow)curObj).getId());
            }
        }
        return strings.isEmpty()? null: strings;
    }

    /**
     * 获取接口id
     * @return
     */
    public Set<String> getApiIdsFromObjects() {
        Set<String> strings = new HashSet<>();
        for (Object curObj: toCopySet) {
            if (curObj instanceof SysApi) {
                strings.add(((SysApi)curObj).getId());
            }
        }
        return strings.isEmpty()? null: strings;
    }

    /**
     * 获取页面id
     * @return
     */
    public Set<String> getPageIdsFromObjects() {
        Set<String> strings = new HashSet<>();
        for (Object curObj: toCopySet) {
            if (curObj instanceof DevPage) {
                strings.add(((DevPage)curObj).getId());
            }
        }
        return strings.isEmpty()? null: strings;
    }


    /**
     * 判断拷贝对象是否已存在
     * @param obj   对象
     * @return      结果
     */
    public boolean existCopyObject(Object obj) {

        boolean exist = false;
        for (Object curObj: toCopySet) {
            if (!curObj.getClass().getSimpleName().equalsIgnoreCase(obj.getClass().getSimpleName())) {
                continue;
            }
            // 处理faas的逻辑编排
            if (obj instanceof AddFlowInfo) {
                AddFlowInfo o1 = (AddFlowInfo) curObj;
                AddFlowInfo o2 = (AddFlowInfo) obj;
                if (o1.getFlowId().equals(o2.getFlowId())) {
                    exist = true;
                    break;
                }
            }
            // 处理faas的逻辑编排
            if (obj instanceof SysLogicFlow) {
                SysLogicFlow o1 = (SysLogicFlow) curObj;
                SysLogicFlow o2 = (SysLogicFlow) obj;
                if (o1.getId().equals(o2.getId())) {
                    exist = true;
                    break;
                }
            }
            // 处理接口
            if (obj instanceof SysApi) {
                SysApi o1 = (SysApi) curObj;
                SysApi o2 = (SysApi) obj;
                if (o1.getId().equals(o2.getId()) || StringUtils.isEmpty(o1.getApiUrl()) || StringUtils.isEmpty(o1.getApiFlowId()) || StringUtils.isEmpty(o1.getApiMethod())   || (o1.getApiUrl().equals(o2.getApiUrl()) && o1.getApiMethod().equals(o2.getApiMethod()) && o1.getApiFlowId().equals(o2.getApiFlowId()))) {
                    exist = true;
                    break;
                }
            }
            // 处理页面
            if (obj instanceof DevPage) {
                DevPage o1 = (DevPage) curObj;
                DevPage o2 = (DevPage) obj;
                if (o1.getId().equals(o2.getId())) {
                    exist = true;
                    break;
                }
            }
            // 处理字典
            if (obj instanceof SysDict) {
                SysDict o1 = (SysDict) curObj;
                SysDict o2 = (SysDict) obj;
                if (o1.getId().equals(o2.getId())) {
                    exist = true;
                    break;
                }
            }
            // 处理字典项
            if (obj instanceof SysDictItem) {
                SysDictItem o1 = (SysDictItem) curObj;
                SysDictItem o2 = (SysDictItem) obj;
                if (o1.getId().equals(o2.getId())) {
                    exist = true;
                    break;
                }
            }
            // 处理系统配置
            if (obj instanceof SysConfig) {
                SysConfig o1 = (SysConfig) curObj;
                SysConfig o2 = (SysConfig) obj;
                if (o1.getId().equals(o2.getId())) {
                    exist = true;
                    break;
                }
            }
            // 任务调度
            if (obj instanceof SysTask) {
                SysTask o1 = (SysTask) curObj;
                SysTask o2 = (SysTask) obj;
                if (o1.getId().equals(o2.getId())) {
                    exist = true;
                    break;
                }
            }
        }
        return exist;

    }


}

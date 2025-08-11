package com.kingsware.kdev.core.cache.api;

import com.kingsware.kdev.core.bean.DataModified;
import com.kingsware.kdev.core.util.StringUtils;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 *
 * @author chen peng
 * @version 1.0.0
 * @date 2022/1/18 6:46 下午
 */
@Slf4j
public class ApiManager {

    private   DataModified dataModified;
    private static ApiManager instance;

    /** 编码api **/
    private List<ApiInfo> apis = new CopyOnWriteArrayList<>();

    public static ApiManager getInstance() {
        if (instance == null) {
            instance = new ApiManager();
        }
        return instance;
    }

    private ApiManager() {
    }


    /**
     * 获取所有api
     * @return
     */
    public List<ApiInfo> getAllApis() {
        return apis;
    }

    /**
     * 增加api
     * @param apis  api
     */
    public void addApi(List<ApiInfo> apis) {
        synchronized (ApiManager.class) {
            replaceList(apis);
            reloadModified();
        }
    }

    public void reloadModified() {
        if (this.apis.size() > 0) {
            DataModified modified = new DataModified();
            String whenModified = this.apis.stream().max((Comparator.comparing(ApiInfo::getWhenModified))).get().getWhenModified();
            modified.setWhenModified(whenModified);
            modified.setCnt(apis.size());
            this.dataModified = modified;
        }

    }

    /**
     * 安全替换list
     * @param newList
     */
    public synchronized void replaceList(List<ApiInfo> newList) {
        this.apis.clear();
        this.apis.addAll(newList);
    }

    public DataModified getDataModified() {
        return dataModified;
    }

    /**
     * 增加api
     * @param apiInfo api信息
     */
    public void addOrUpdateApi(ApiInfo apiInfo) {
        // 先查询一下看当前无
        boolean isReplaced = false;
        for (int i = 0; i< this.apis.size(); i++) {
            // 如果找到，就直接替换掉
            if (this.apis.get(i).getId().equals(apiInfo.getId())) {
                this.apis.set(i, apiInfo);
                isReplaced = true;
                log.info("接口替换成功, 接口信息:{}", apiInfo);
                break;
            }
        }
        // 否则，将加到列表
        if (!isReplaced) {
            this.apis.add(apiInfo);
            log.info("接口新增成功, 接口信息:{}", apiInfo);
        }
        reloadModified();
    }

    /**
     * 移除api信息
     * @param apiId apiId
     */
    public void removeApi(String apiId) {
        for (int i = 0; i< this.apis.size(); i++) {
            if (this.apis.get(i).getId().equals(apiId)) {
                this.apis.remove(i);
                log.info("接口移除成功, 接口id:{}", apiId);
                break;
            }
        }
        reloadModified();

    }

    /**
     * 获取接口定义
     * @param apiMethod 接口方式
     * @param url       接口路径
     * @return      返回接口信息
     */
    public ApiInfo getApi(String apiMethod, String url) {
        try {
            // 如果url为空
            List<ApiInfo> myApis = new ArrayList<>();
            for (ApiInfo api : apis) {
                if (StringUtils.isNotEmpty(api.getApiUrl()) && StringUtils.isNotEmpty(api.getApiMethod())) {
                    myApis.add(api);
                }
            }
            // 先匹配相等的
            Optional<ApiInfo> optional = myApis.stream().filter(f -> f.getApiMethod().equalsIgnoreCase(apiMethod)).filter(api -> api.getApiUrl().equals(url)).findFirst();
            if (optional.isPresent()) {
                return optional.get();
            }
            // 匹配正则
            optional = apis.stream().filter(f -> f.getApiMethod().equalsIgnoreCase(apiMethod)).filter(api -> {
                // url路径
                String[] reqArr = url.split("/");
                String[] restArr = api.getApiUrl().split("/");
                if (reqArr.length == restArr.length) {
                    for (int i = 0; i < reqArr.length; i++) {
                        // segment部分跳过
                        if (restArr[i].startsWith("{") && restArr[i].endsWith("}")) {
                            continue;
                        }
                        if (!reqArr[i].equals(restArr[i])) {
                            return false;
                        }
                    }
                    return true;
                }
                return false;

            }).findFirst();
            return optional.orElse(null);
        } catch (Exception e) {
            log.error("error", e);
            return null;

        }
    }



}

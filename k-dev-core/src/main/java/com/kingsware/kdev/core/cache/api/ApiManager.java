package com.kingsware.kdev.core.cache.api;

import com.kingsware.kdev.core.util.StringUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 *
 * @author chen peng
 * @version 1.0.0
 * @date 2022/1/18 6:46 下午
 */
public class ApiManager {

    private static ApiManager instance;

    /** 编码api **/
    private List<ApiInfo> apis = new ArrayList<>();

    public static ApiManager getInstance() {
        if (instance == null) {
            instance = new ApiManager();
        }
        return instance;
    }

    private ApiManager() {
    }

    /**
     * 增加api
     * @param apis  api
     */
    public void addApi(List<ApiInfo> apis) {
        this.apis = apis;
    }

    /**
     * 获取接口定义
     * @param apiMethod 接口方式
     * @param url       接口路径
     * @return      返回接口信息
     */
    public ApiInfo getApi(String apiMethod, String url) {
        // 如果url为空
        List<ApiInfo> myApis = new ArrayList<>();
        for (ApiInfo api: apis) {
            if (StringUtils.isNotEmpty(api.getApiUrl())) {
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
    }


}

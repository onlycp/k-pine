package com.kingsware.kdev.core.cache.api;

import com.kingsware.kdev.sys.model.SysApi;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * //todo 描述当前类是干什么用的.
 *
 * @author chen peng
 * @version 1.0.0
 * @date 2022/1/18 6:46 下午
 */
public class ApiManager {

    private static ApiManager instance;

    /** 编码api **/
    private List<SysApi> apis = new ArrayList<>();

    public static ApiManager getInstance() {
        if (instance == null) {
            synchronized (ApiManager.class) {
                if (instance == null) {
                    instance = new ApiManager();
                }
            }
        }
        return instance;
    }

    private ApiManager() {
    }

    /**
     * 增加api
     * @param apis  api
     */
    public void addApi(List<SysApi> apis) {
        this.apis = apis;
    }

    /**
     * 获取接口定义
     * @param apiMethod 接口方式
     * @param url       接口路径
     * @param contextPath   上下文路径
     * @return      返回接口信息
     */
    public SysApi getApi(String apiMethod, String url, String contextPath) {
        String path = url.replace(contextPath, "");
        // 先匹配相等的
        Optional<SysApi> optional0 = apis.stream().filter(f -> f.getApiMethod().equalsIgnoreCase(apiMethod)).filter(api -> api.getApiUrl().equals(path)).findFirst();
        if (optional0.isPresent()) {
            return optional0.get();
        }
        return null;
//
//        Optional<RestfulInfo> optional = apis.stream().filter(f -> f.getSegmentCount() > 0).filter(f -> f.getMethod().name().equalsIgnoreCase(method)).filter(api -> {
//
//            String[] reqArr = url.split("/");
//            String[] restArr = api.getUrl().split("/");
//            if (reqArr.length == restArr.length) {
//                for (int i = 0; i < reqArr.length; i++) {
//                    // segment部分跳过
//                    if (restArr[i].contains("{")) {
//                        continue;
//                    }
//                    if (!reqArr[i].equals(restArr[i])) {
//                        return false;
//                    }
//                }
//                return true;
//            }
//            return false;
//
//        }).findFirst();
//        return optional.orElse(null);
    }


}

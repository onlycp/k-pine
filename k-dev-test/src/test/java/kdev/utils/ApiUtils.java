package kdev.utils;

import com.kingsware.kdev.core.auth.UselessApi;
import com.kingsware.kdev.core.util.StringUtils;
import com.kingsware.kdev.sys.argv.SysApiArgv;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Api工具
 *
 * @author chen peng
 * @version 1.0.0
 * @date 2022/1/21 9:49 上午
 */
public class ApiUtils {

    private ApiUtils() {}

    /**
     *  扫描所有的接口
     * @param clazz 类
     * @param <T>   泛型
     * @return      返回所有接口
     */
    public static <T> List<SysApiArgv> scanApis(Class<T> clazz) {
        Api api =  (Api) clazz.getAnnotation(Api.class);
        RequestMapping parentRequestMapping = (RequestMapping)clazz.getAnnotation(RequestMapping.class);
        List<SysApiArgv> argvs = new ArrayList<>();
        // 获取所有的方法
        Method[] methods = clazz.getMethods();
        for (Method method: methods) {
            if (!method.isAnnotationPresent(ApiOperation.class)) {
                continue;
            }
            if (method.isAnnotationPresent(UselessApi.class)) {
                continue;
            }
            ApiOperation operation = (ApiOperation) method.getDeclaredAnnotation(ApiOperation.class);
            String subUrl = "";
            String apiMethod = "";
            Integer apiArgvType = 1;
            if (method.isAnnotationPresent(GetMapping.class)) {
                GetMapping mapping = method.getAnnotation(GetMapping.class);
                String value[] = mapping.value();
                if (value.length > 0) {
                    subUrl = value[0];
                }
                apiMethod = "get";
                apiArgvType = 1;
            }
            else if (method.isAnnotationPresent(PostMapping.class)) {
                PostMapping mapping = method.getAnnotation(PostMapping.class);
                String value[] = mapping.value();
                if (value.length > 0) {
                    subUrl = value[0];
                }
                apiMethod = "post";
                apiArgvType = 3;
            }
            else if (method.isAnnotationPresent(PutMapping.class)) {
                PutMapping mapping = method.getAnnotation(PutMapping.class);
                String value[] = mapping.value();
                if (value.length > 0) {
                    subUrl = value[0];
                }
                apiMethod = "put";
                apiArgvType = 3;
            }
            else if (method.isAnnotationPresent(DeleteMapping.class)) {
                DeleteMapping mapping = method.getAnnotation(DeleteMapping.class);
                String value[] = mapping.value();
                if (value.length > 0) {
                    subUrl = value[0];
                }
                apiMethod = "delete";
                apiArgvType = 1;
            }
            // 接口路径
            String apiUrl =  parentRequestMapping.value()[0] + subUrl;
            // 接口名称
            String apiName = api.value() + "-" + operation.value();
            // 接口描述
            String apiNotes = operation.notes();
            // 标签
            String tags = StringUtils.joinToString(Arrays.asList(api.tags()), "," );
            // 组装参数
            SysApiArgv argv = new SysApiArgv();
            argv.setApiName(apiName);
            argv.setApiUrl(apiUrl);
            argv.setApiNote(apiNotes);
            argv.setApiTags(tags);
            argv.setApiMethod(apiMethod);
            argv.setApiArgvType(apiArgvType);
            argv.setCallType(1);
            // 增加到返回结果列表
            argvs.add(argv);
        }
        return argvs;
    }
}

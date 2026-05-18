package com.kingsware.kdev.core.cron;

import com.kingsware.kdev.core.context.SpringContext;
import com.kingsware.kdev.core.util.ClassUtils;
import com.kingsware.kdev.core.util.StringUtils;

import java.lang.reflect.Modifier;
import java.util.*;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

/**
 * Java任务类安全校验
 */
public class JavaTaskClassGuard {

    private static final String SCAN_PACKAGE_KEY = "schedule.scan-package";
    private static final String ALLOWED_CLASSES_KEY = "schedule.java-task.allowed-classes";
    private static final String ALLOWED_PACKAGES_KEY = "schedule.java-task.allowed-packages";

    private static final String DEFAULT_SCAN_PACKAGE = "com.kingsware.kdev";
    private static final String DEFAULT_ALLOWED_PACKAGES = "com.kingsware.kdev.core.,com.kingsware.kdev.sys.task.";

    private static final long CACHE_TTL_MS = 60_000L;
    private static final Pattern CLASS_NAME_PATTERN = Pattern.compile("^[A-Za-z_$][A-Za-z\\d_$]*(\\.[A-Za-z_$][A-Za-z\\d_$]*)+$");

    private static volatile long lastLoadTs = 0L;
    private static volatile Set<String> discoveredTaskClasses = Collections.emptySet();

    private JavaTaskClassGuard() {}

    /**
     * 校验类名是否允许作为Java任务类。
     */
    public static void validateOrThrow(String className) {
        String denyReason = getDenyReason(className);
        if (denyReason != null) {
            throw new IllegalArgumentException(denyReason);
        }
    }

    /**
     * 获取拒绝原因，null代表允许。
     */
    public static String getDenyReason(String className) {
        if (StringUtils.isEmpty(className)) {
            return "className不能为空";
        }
        String trimClassName = className.trim();
        if (!CLASS_NAME_PATTERN.matcher(trimClassName).matches()) {
            return "className格式非法";
        }
        if (!ClassUtils.isClassExists(trimClassName)) {
            return "class不存在";
        }

        Class<?> taskClass;
        try {
            taskClass = Class.forName(trimClassName);
        } catch (Exception e) {
            return "class加载失败";
        }

        if (!KTask.class.isAssignableFrom(taskClass)) {
            return "class未实现KTask接口";
        }
        if (taskClass.isInterface() || Modifier.isAbstract(taskClass.getModifiers())) {
            return "class不能为接口或抽象类";
        }

        Set<String> explicitAllowed = parseCsvToSet(readProperty(ALLOWED_CLASSES_KEY, ""));
        if (!explicitAllowed.isEmpty()) {
            if (!explicitAllowed.contains(trimClassName)) {
                return "未命中白名单配置(" + ALLOWED_CLASSES_KEY + ")";
            }
            return null;
        }

        List<String> allowedPackages = parseCsvToList(readProperty(ALLOWED_PACKAGES_KEY, DEFAULT_ALLOWED_PACKAGES));
        boolean packageAllowed = allowedPackages.stream().anyMatch(trimClassName::startsWith);
        if (!packageAllowed) {
            return "不在允许包前缀内(" + ALLOWED_PACKAGES_KEY + ")";
        }

        if (!getDiscoveredTaskClasses().contains(trimClassName)) {
            return "不在扫描注册任务列表内";
        }
        return null;
    }

    private static Set<String> getDiscoveredTaskClasses() {
        long now = System.currentTimeMillis();
        Set<String> localCache = discoveredTaskClasses;
        if (now - lastLoadTs <= CACHE_TTL_MS && !localCache.isEmpty()) {
            return localCache;
        }
        synchronized (JavaTaskClassGuard.class) {
            if (now - lastLoadTs <= CACHE_TTL_MS && !discoveredTaskClasses.isEmpty()) {
                return discoveredTaskClasses;
            }
            String scanPackage = readProperty(SCAN_PACKAGE_KEY, DEFAULT_SCAN_PACKAGE);
            List<Class<?>> classList = ClassUtils.getClassesByParentClass(scanPackage, KTask.class);
            Set<String> classNames = classList.stream()
                    .filter(Objects::nonNull)
                    .map(Class::getName)
                    .collect(Collectors.toCollection(HashSet::new));
            discoveredTaskClasses = classNames;
            lastLoadTs = now;
            return discoveredTaskClasses;
        }
    }

    private static String readProperty(String key, String defaultValue) {
        try {
            return SpringContext.getProperties(key, defaultValue);
        } catch (Exception e) {
            return defaultValue;
        }
    }

    private static Set<String> parseCsvToSet(String value) {
        return new LinkedHashSet<>(parseCsvToList(value));
    }

    private static List<String> parseCsvToList(String value) {
        if (StringUtils.isEmpty(value)) {
            return Collections.emptyList();
        }
        String[] arr = value.split(",");
        List<String> result = new ArrayList<>();
        for (String item : arr) {
            if (StringUtils.isNotEmpty(item)) {
                result.add(item.trim());
            }
        }
        return result;
    }
}


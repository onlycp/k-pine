package com.kingsware.kdev.core.util;

import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.util.regex.Pattern;

/**
 * 安全工具类 - 提供各种安全相关的验证和过滤功能
 *
 * @author claude
 * @version 1.0.0
 * @date 2026/04/07
 */
public class SecurityUtil {

    // SQL注入检测模式
    private static final Pattern SQL_INJECTION_PATTERN = Pattern.compile(
        "(?i)(union\\s+select|drop\\s+|delete\\s+from|insert\\s+into|update\\s+|exec\\s+|execute\\s+|truncate\\s+|alter\\s+table|create\\s+|xp_|sp_|waitfor\\s+delay|benchmark\\(|extractvalue\\(|updatexml\\(|floor\\(|sleep\\()",
        Pattern.CASE_INSENSITIVE | Pattern.MULTILINE | Pattern.DOTALL
    );

    // XSS攻击检测模式
    private static final Pattern XSS_PATTERN = Pattern.compile(
        "(?i)<script[^>]*>[\\s\\S]*?</script>|<img[^>]*src[\\s]*=[\\s]*['\"]javascript:|<iframe[^>]*>|onload\\s*=|onclick\\s*=|onerror\\s*=|onmouseover\\s*=",
        Pattern.CASE_INSENSITIVE | Pattern.MULTILINE | Pattern.DOTALL
    );

    // 文件路径遍历检测模式
    private static final Pattern PATH_TRAVERSAL_PATTERN = Pattern.compile(
        "\\.\\.(/|\\\\)",
        Pattern.CASE_INSENSITIVE
    );

    // 基本命令注入检测模式 - 匹配特殊符号后紧跟系统命令的模式
    private static final Pattern COMMAND_INJECTION_PATTERN = Pattern.compile(
        "(?i)(;|\\||&|\\$\\(|`)\\s*(whoami|ls|dir|cat|rm|del|exec|eval|shell_exec)(\\s|;|\\)|`|$)",
        Pattern.CASE_INSENSITIVE
    );

    /**
     * 检查输入是否包含SQL注入特征
     *
     * @param input 输入字符串
     * @return 如果包含SQL注入特征返回true
     */
    public static boolean containsSqlInjection(String input) {
        if (input == null) {
            return false;
        }
        return SQL_INJECTION_PATTERN.matcher(input).find();
    }

    /**
     * 检查输入是否包含XSS攻击特征
     *
     * @param input 输入字符串
     * @return 如果包含XSS攻击特征返回true
     */
    public static boolean containsXssAttack(String input) {
        if (input == null) {
            return false;
        }
        return XSS_PATTERN.matcher(input).find();
    }

    /**
     * 检查输入是否包含路径遍历特征
     *
     * @param input 输入字符串
     * @return 如果包含路径遍历特征返回true
     */
    public static boolean containsPathTraversal(String input) {
        if (input == null) {
            return false;
        }
        if (PATH_TRAVERSAL_PATTERN.matcher(input).find()) {
            return true;
        }
        String normalized = normalizeForPathTraversalCheck(input);
        return PATH_TRAVERSAL_PATTERN.matcher(normalized).find();
    }

    private static String normalizeForPathTraversalCheck(String input) {
        String normalized = input;
        // Multi-encoding is common in traversal probes (%252e%252e%252f -> ../).
        for (int i = 0; i < 3; i++) {
            try {
                String decoded = URLDecoder.decode(normalized, StandardCharsets.UTF_8.name());
                if (decoded.equals(normalized)) {
                    break;
                }
                normalized = decoded;
            } catch (Exception e) {
                break;
            }
        }
        return normalized.replace('\\', '/');
    }

    /**
     * 检查输入是否包含命令注入特征
     *
     * @param input 输入字符串
     * @return 如果包含命令注入特征返回true
     */
    public static boolean containsCommandInjection(String input) {
        if (input == null) {
            return false;
        }
        return COMMAND_INJECTION_PATTERN.matcher(input).find();
    }

    /**
     * 验证输入是否安全
     *
     * @param input 输入字符串
     * @return 如果输入安全返回true
     */
    public static boolean isInputSafe(String input) {
        if (input == null) {
            return true;
        }

        return !(containsSqlInjection(input) ||
                 containsXssAttack(input) ||
                 containsPathTraversal(input) ||
                 containsCommandInjection(input));
    }

    /**
     * 清理XSS内容（简单清理，仅用于展示目的）
     *
     * @param input 原始输入
     * @return 清理后的字符串
     */
    public static String sanitizeXssContent(String input) {
        if (input == null) {
            return null;
        }

        String sanitized = input;
        // 替换潜在危险的HTML标签
        sanitized = sanitized.replaceAll("(?i)<script[^>]*>[\\s\\S]*?</script>", "&lt;script&gt;...&lt;/script&gt;");
        sanitized = sanitized.replaceAll("(?i)<iframe[^>]*>", "[BLOCKED_IFRAME]");
        sanitized = sanitized.replaceAll("(?i)<link[^>]*>", "[BLOCKED_LINK]");
        sanitized = sanitized.replaceAll("(?i)<meta[^>]*>", "[BLOCKED_META]");

        // 替换事件处理器
        sanitized = sanitized.replaceAll("(?i)onload\\s*=", "onload_blocked=");
        sanitized = sanitized.replaceAll("(?i)onclick\\s*=", "onclick_blocked=");
        sanitized = sanitized.replaceAll("(?i)onerror\\s*=", "onerror_blocked=");
        sanitized = sanitized.replaceAll("(?i)onmouseover\\s*=", "onmouseover_blocked=");

        return sanitized;
    }
}
